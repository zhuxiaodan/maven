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
    //��ʾpage.xsl�ļ�·���ĳ�ʼ�������Ĳ�������
    private static final String XSL_FILE = "xsltfile";
    //��ʾheader.xml�ļ�·���ĳ�ʼ�������Ĳ�������
    private static final String XML_HEADER_FILE = "headerfile";
    //��ʾfooter.xml�ļ�·���ĳ�ʼ�������Ĳ�������
    private static final String XML_FOOTER_FILE = "footerfile";
    //��ʾstyle.xml�ļ�·���������Ĳ����Ĳ�������
    private static final String XML_STYLE_FILE = "stylefile";

    private Templates xsltTemplate;
    private String headerPath;
    private String footerPath;
    private Element root;

    public void init() throws ServletException
    {
        TransformerFactory tFactory=TransformerFactory.newInstance();
        ServletContext sc=getServletContext();

        //�õ���ʼ��������ֵ��
        String xsltFile=getInitParameter(XSL_FILE);
        String xmlHeaderFile=getInitParameter(XML_HEADER_FILE);
        String xmlFooterFile=getInitParameter(XML_FOOTER_FILE);

        //�õ���ʽ���ļ�������������
        InputStream is=sc.getResourceAsStream(xsltFile);

        try
        {
            //����Templates���󣬶�ת��ָ������Ż���
            xsltTemplate=tFactory.newTemplates(new StreamSource(is));
            //�õ�header.xml�ļ���URL·����
            headerPath=sc.getResource(xmlHeaderFile).toString();
            //�õ�footer.xml�ļ���URL·����
            footerPath=sc.getResource(xmlFooterFile).toString();

            //��������ķ�ʽ�õ�header.xml��footer.xml�ļ�����ʵ·��Ҳ�ǿ��Եġ�
            /*String contextRealPath = sc.getRealPath("/");
            headerPath=contextRealPath + xmlHeaderFile;
            footerPath=contextRealPath + xmlFooterFile;*/
        }
        catch(TransformerConfigurationException e)
        {
            System.err.println(e.getMessage());
            throw new UnavailableException("ϵͳ���ô���");
        }
        catch(MalformedURLException me)
        {
            System.err.println(me.getMessage());
            throw new UnavailableException("ϵͳ���ô���");
        }

        //ʹ��dom4j API����style.xml�ĵ���
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
            throw new UnavailableException("ϵͳ���ô���");
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

            //��Session������ȡ���ѵ�¼�û�ѡ�����ʾ���
            String style=(String)req.getSession().getAttribute("style");
            if(null!=style && !"".equals(style))
            {
                //�����ڴ�����ʹ����XPath���ʽ�������ڱ���ʱҪ������jaxen���μ���3�µ�3.5.2С�ڣ���
                List l=root.selectNodes("//style[@name='"+style+"']");
                //���ݵ�¼�û�ѡ�����ʾ��񣬻�ȡstyle.xml�ļ���
                //<style>Ԫ�ص�<bgcolor>��Ԫ�ء�
                Element elt=(Element)l.get(0);
                Element eltBgColor=elt.element("bgcolor");

                //Ϊ����ת�����ö������bgcolor��ֵ��
                transformer.setParameter("bgcolor",eltBgColor.getText());
            }

            //�ж��û������ҳ���Ƿ��ǹ���ҳ�档
            String manager=(String)req.getAttribute("manager");
            if("manager".equals(manager))
            {
                //�����¼�û��ǹ���Ա����Ϊ����ת�����ö������user��ֵ��
                transformer.setParameter("user",manager);
            }
            //�����������ȡ����Ҫת����XML�ĵ���
            String strPageXml=(String)req.getAttribute("page");
            StreamSource source=new StreamSource(new StringReader(strPageXml));

            resp.setContentType("text/html;charset=GB2312");
            StreamResult result=new StreamResult(resp.getWriter());

            //ʹ��ָ������ʽ��ת��XML�ĵ�������ת����Ľ��ҳ��������ͻ��ˡ�
            transformer.transform(source, result);
        }
        catch(TransformerException e){System.err.println(e.getMessage());}
    }
}
