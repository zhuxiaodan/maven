package org.sunxin.guestbook.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;

public class AdminLogonProcessor implements Processor
{

    public void perform(HttpServletRequest req,
                        HttpServletResponse resp,
                        ServletContext sc)
        throws IOException, ServletException,UserException
    {
        //����������б���manager���ԣ�XsltServlet������������Ե�ֵ���ж�
        //�Ƿ���ʽ�������user��ֵ����Ϊ��manager����
        req.setAttribute("manager","manager");

        String action = req.getParameter("action");
        //�ж��û��Ƿ��ʹ���Ա��¼ҳ�滹���ύ��¼��Ϣ��
        if (null == action || !"logon".equals(action))
        {
            try
            {
                StringBuffer sb = new StringBuffer(64);
                sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
                sb.append("<page name=\"logon\">");
                sb.append("<logon/></page>");
                req.setAttribute("page", sb.toString());
            }
            catch (Exception e)
            {
                throw new ServletException("ҳ���������!");
            }
        }
        else
        {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (null == username || null == password ||
                "".equals(username) || "".equals(password))
            {
                throw new UserException(
                    "�û��������벻��Ϊ�գ���<a href=\"admin_logon.jsp\">����</a>��������!");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            GuestbookDB gstDB = (GuestbookDB) sc.getAttribute("gstdb");

            boolean b = gstDB.validateManager(user);

            if (false==b)
            {
                throw new UserException(
                    "�û��������������<a href=\"admin_logon.jsp\">����</a>��������!");
            }
            else
            {
                HttpSession session = req.getSession();
                session.setAttribute("manager", username);
                String strMsg ="��¼�ɹ���<a href=\"admin_index.jsp\">�鿴����</a>";
                req.setAttribute("success", strMsg);
                new SuccessProcessor().perform(req, resp, sc);
            }
        }
    }
}
