package org.sunxin.guestbook.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;

public class LogonProcessor implements Processor
{

    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException,ServletException,UserException
    {
        String action=req.getParameter("action");
        //�ж��û��Ƿ��ʵ�¼ҳ�滹���ύ��¼��Ϣ��
        if(null==action || !"logon".equals(action))
        {
            StringBuffer sb=new StringBuffer(64);
            sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
            sb.append("<page name=\"logon\">");
            sb.append("<logon/></page>");

            req.setAttribute("page", sb.toString());
        }
        else
        {
            String username=req.getParameter("username");
            String password=req.getParameter("password");

            //ʵ��Ӧ���У�Ӧ���ڿͻ�������JavaScript�ű�Ҳ�����Ƶ��жϡ�
            if(null==username || null==password ||
               "".equals(username) || "".equals(password))
            {
                throw new UserException(
                    "�û��������벻��Ϊ�գ���<a href=\"logon.jsp\">����</a>��������!");
            }

            User user=new User();
            user.setUsername(username);
            user.setPassword(password);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");

            //����GuestbookDB�����validateUser()�������û�������֤��
            String style=gstDB.validateUser(user);

            if("".equals(style))
            {
                throw new UserException(
                    "�û��������������<a href=\"logon.jsp\">����</a>��������!");
            }
            else
            {
                HttpSession session=req.getSession();
                session.setAttribute("user",username);
                session.setAttribute("style",style);
                String strMsg=null;
                strMsg="��¼�ɹ���<a href=\"say.jsp\">��Ҫ����</a>��<a href=\"index.jsp\">�鿴����</a>";
                req.setAttribute("success",strMsg);
                new SuccessProcessor().perform(req, resp,sc);
            }
        }
    }
}
