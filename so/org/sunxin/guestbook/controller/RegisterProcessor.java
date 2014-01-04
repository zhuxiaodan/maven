package org.sunxin.guestbook.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.dom4j.*;
import org.dom4j.io.*;
import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;
import org.sunxin.guestbook.parser.*;
import org.sunxin.guestbook.util.*;

public class RegisterProcessor implements Processor
{
    //表示style.xml文件路径的上下文参数的参数名。
    private static final String XML_STYLE_FILE = "stylefile";

    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException,ServletException,UserException
    {
        String action=req.getParameter("action");
        //判断用户是访问注册页面还是提交注册信息。
        if(null==action || !"register".equals(action))
        {
            SAXReader saxReader = new SAXReader();
            //从web.xml文件中读取style.xml文件的路径。
            //style.xml文件中保存了页面显示风格的配置数据。
            String xmlStyleFile=sc.getInitParameter(XML_STYLE_FILE);
            InputStream is = sc.getResourceAsStream(xmlStyleFile);

            List styleList=null;
            try
            {
                //使用dom4j API访问style.xml。
                Document doc = saxReader.read(is);
                Element root = doc.getRootElement();
                List childrenList = root.elements("style");
                Iterator it = childrenList.iterator();
                styleList = new ArrayList();
                while (it.hasNext())
                {
                    Element elt = (Element) it.next();
                    String attrValue = elt.attributeValue("name");
                    styleList.add(attrValue);
                }
            }
            catch(DocumentException de)
            {
                System.err.println(de.toString());
            }

            User userBean=new User();
            userBean.setStyleList(styleList);
            try
            {
                //调用RegisterPageParser实例解析User对象，产生解析事件，
                //通过Sax2Xml实例创建XML文档，并写入到StringWriter对象中。
                RegisterPageParser regBeanParser = new RegisterPageParser(userBean);
                StringWriter sw = new StringWriter();
                Sax2Xml sax2xml = new Sax2Xml(regBeanParser, sw);
                sax2xml.parse("");
                String regPageXml = sw.toString();
                req.setAttribute("page", regPageXml);
                sw.close();
            }
            catch (Exception e)
            {
                throw new ServletException("页面产生错误!");
            }
        }
        else
        {
            //得到用户提交的注册信息。
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            String email=req.getParameter("email");
            String style=req.getParameter("styleList");

            //实际应用中，应该在客户端利用JavaScript脚本也做类似的判断。
            if(null==username || null==password || null==email || null==style ||
               "".equals(username) || "".equals(password) ||
               "".equals(email) || "".equals(style))
            {
                throw new UserException(
                     "用户信息不能为空，请<a href=\"reg.jsp\">返回</a>重新填写！");
            }
            //创建User对象。
            User userBean=new User();
            userBean.setUsername(username);
            userBean.setPassword(password);
            userBean.setEmail(email);
            userBean.setStyle(style);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
            try
            {
                //将用户的注册信息保存到数据库中。
                gstDB.registerUser(userBean);
            }
            catch (UserException ue)
            {
                throw ue;
            }
            HttpSession session=req.getSession(false);
            //用户注册成功后，即认为他已经登录，将用户名保存到Session对象中，
            //在本次会话中，用户不需要再登录。
            session.setAttribute("user",username);
            //将用户选择的显示风格保存到Session对象中，以便在本次会话中，
            //使用用户选择的显示风格显示页面。
            session.setAttribute("style",style);

            String strMsg="注册成功，<a href=\"say.jsp\">我要留言</a>或<a href=\"index.jsp\">查看留言</a>";
            //将成功提示信息作为success属性的值保存到请求对象中。
            req.setAttribute("success",strMsg);
            //调用SuccessProcessor实例的perform()方法创建成功页面的XML文档。
            new SuccessProcessor().perform(req, resp,sc);
        }
    }
}
