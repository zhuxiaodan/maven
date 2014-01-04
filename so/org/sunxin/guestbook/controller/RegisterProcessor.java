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
    //��ʾstyle.xml�ļ�·���������Ĳ����Ĳ�������
    private static final String XML_STYLE_FILE = "stylefile";

    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException,ServletException,UserException
    {
        String action=req.getParameter("action");
        //�ж��û��Ƿ���ע��ҳ�滹���ύע����Ϣ��
        if(null==action || !"register".equals(action))
        {
            SAXReader saxReader = new SAXReader();
            //��web.xml�ļ��ж�ȡstyle.xml�ļ���·����
            //style.xml�ļ��б�����ҳ����ʾ�����������ݡ�
            String xmlStyleFile=sc.getInitParameter(XML_STYLE_FILE);
            InputStream is = sc.getResourceAsStream(xmlStyleFile);

            List styleList=null;
            try
            {
                //ʹ��dom4j API����style.xml��
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
                //����RegisterPageParserʵ������User���󣬲��������¼���
                //ͨ��Sax2Xmlʵ������XML�ĵ�����д�뵽StringWriter�����С�
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
                throw new ServletException("ҳ���������!");
            }
        }
        else
        {
            //�õ��û��ύ��ע����Ϣ��
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            String email=req.getParameter("email");
            String style=req.getParameter("styleList");

            //ʵ��Ӧ���У�Ӧ���ڿͻ�������JavaScript�ű�Ҳ�����Ƶ��жϡ�
            if(null==username || null==password || null==email || null==style ||
               "".equals(username) || "".equals(password) ||
               "".equals(email) || "".equals(style))
            {
                throw new UserException(
                     "�û���Ϣ����Ϊ�գ���<a href=\"reg.jsp\">����</a>������д��");
            }
            //����User����
            User userBean=new User();
            userBean.setUsername(username);
            userBean.setPassword(password);
            userBean.setEmail(email);
            userBean.setStyle(style);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
            try
            {
                //���û���ע����Ϣ���浽���ݿ��С�
                gstDB.registerUser(userBean);
            }
            catch (UserException ue)
            {
                throw ue;
            }
            HttpSession session=req.getSession(false);
            //�û�ע��ɹ��󣬼���Ϊ���Ѿ���¼�����û������浽Session�����У�
            //�ڱ��λỰ�У��û�����Ҫ�ٵ�¼��
            session.setAttribute("user",username);
            //���û�ѡ�����ʾ��񱣴浽Session�����У��Ա��ڱ��λỰ�У�
            //ʹ���û�ѡ�����ʾ�����ʾҳ�档
            session.setAttribute("style",style);

            String strMsg="ע��ɹ���<a href=\"say.jsp\">��Ҫ����</a>��<a href=\"index.jsp\">�鿴����</a>";
            //���ɹ���ʾ��Ϣ��Ϊsuccess���Ե�ֵ���浽��������С�
            req.setAttribute("success",strMsg);
            //����SuccessProcessorʵ����perform()���������ɹ�ҳ���XML�ĵ���
            new SuccessProcessor().perform(req, resp,sc);
        }
    }
}
