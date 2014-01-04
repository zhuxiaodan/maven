package org.sunxin.guestbook.view;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.dom4j.*;
import org.dom4j.io.*;

public class XsltServlet extends HttpServlet
{
    //表示page.xsl文件路径的初始化参数的参数名。
    private static final String XSL_FILE = "xsltfile";
    //表示header.xml文件路径的初始化参数的参数名。
    private static final String XML_HEADER_FILE = "headerfile";
    //表示footer.xml文件路径的初始化参数的参数名。
    private static final String XML_FOOTER_FILE = "footerfile";
    //表示style.xml文件路径的上下文参数的参数名。
    private static final String XML_STYLE_FILE = "stylefile";

    private Templates xsltTemplate;
    private String headerPath;
    private String footerPath;
    private Element root;

    public void init() throws ServletException
    {
        TransformerFactory tFactory=TransformerFactory.newInstance();
        ServletContext sc=getServletContext();

        //得到初始化参数的值。
        String xsltFile=getInitParameter(XSL_FILE);
        String xmlHeaderFile=getInitParameter(XML_HEADER_FILE);
        String xmlFooterFile=getInitParameter(XML_FOOTER_FILE);

        //得到样式表文件的输入流对象。
        InputStream is=sc.getResourceAsStream(xsltFile);

        try
        {
            //创建Templates对象，对转换指令进行优化。
            xsltTemplate=tFactory.newTemplates(new StreamSource(is));
            //得到header.xml文件的URL路径。
            headerPath=sc.getResource(xmlHeaderFile).toString();
            //得到footer.xml文件的URL路径。
            footerPath=sc.getResource(xmlFooterFile).toString();

            //采用下面的方式得到header.xml和footer.xml文件的真实路径也是可以的。
            /*String contextRealPath = sc.getRealPath("/");
            headerPath=contextRealPath + xmlHeaderFile;
            footerPath=contextRealPath + xmlFooterFile;*/
        }
        catch(TransformerConfigurationException e)
        {
            System.err.println(e.getMessage());
            throw new UnavailableException("系统设置错误！");
        }
        catch(MalformedURLException me)
        {
            System.err.println(me.getMessage());
            throw new UnavailableException("系统设置错误！");
        }

        //使用dom4j API解析style.xml文档。
        SAXReader saxReader = new SAXReader();
        String xmlStyleFile=sc.getInitParameter(XML_STYLE_FILE);
        InputStream inputStream = sc.getResourceAsStream(xmlStyleFile);

        try
        {
            Document doc = saxReader.read(inputStream);
            root = doc.getRootElement();
        }
        catch(DocumentException de)
        {
            System.err.println(de.toString());
            throw new UnavailableException("系统设置错误！");
        }
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, java.io.IOException
    {
        try
        {
            Transformer transformer=xsltTemplate.newTransformer();
            transformer.setParameter("headerpath",headerPath);
            transformer.setParameter("footerpath",footerPath);

            //从Session对象中取出已登录用户选择的显示风格。
            String style=(String)req.getSession().getAttribute("style");
            if(null!=style && !"".equals(style))
            {
                //由于在代码中使用了XPath表达式，所以在编译时要先配置jaxen（参见第3章的3.5.2小节）。
                List l=root.selectNodes("//style[@name='"+style+"']");
                //根据登录用户选择的显示风格，获取style.xml文件中
                //<style>元素的<bgcolor>子元素。
                Element elt=(Element)l.get(0);
                Element eltBgColor=elt.element("bgcolor");

                //为本次转换设置顶层参数bgcolor的值。
                transformer.setParameter("bgcolor",eltBgColor.getText());
            }

            //判断用户请求的页面是否是管理页面。
            String manager=(String)req.getAttribute("manager");
            if("manager".equals(manager))
            {
                //如果登录用户是管理员，则为本次转换设置顶层参数user的值。
                transformer.setParameter("user",manager);
            }
            //从请求对象中取出需要转换的XML文档。
            String strPageXml=(String)req.getAttribute("page");
            StreamSource source=new StreamSource(new StringReader(strPageXml));

            resp.setContentType("text/html;charset=GB2312");
            StreamResult result=new StreamResult(resp.getWriter());

            //使用指定的样式表，转换XML文档，并将转换后的结果页面输出到客户端。
            transformer.transform(source, result);
        }
        catch(TransformerException e){System.err.println(e.getMessage());}
    }
}
