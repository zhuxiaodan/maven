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
        //在请求对象中保存manager属性，XsltServlet将根据这个属性的值来判断
        //是否将样式表顶层参数user的值设置为“manager”。
        req.setAttribute("manager","manager");

        String action = req.getParameter("action");
        //判断用户是访问管理员登录页面还是提交登录信息。
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
                throw new ServletException("页面产生错误!");
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
                    "用户名或密码不能为空，请<a href=\"admin_logon.jsp\">返回</a>重新输入!");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            GuestbookDB gstDB = (GuestbookDB) sc.getAttribute("gstdb");

            boolean b = gstDB.validateManager(user);

            if (false==b)
            {
                throw new UserException(
                    "用户名或密码错误，请<a href=\"admin_logon.jsp\">返回</a>重新输入!");
            }
            else
            {
                HttpSession session = req.getSession();
                session.setAttribute("manager", username);
                String strMsg ="登录成功，<a href=\"admin_index.jsp\">查看留言</a>";
                req.setAttribute("success", strMsg);
                new SuccessProcessor().perform(req, resp, sc);
            }
        }
    }
}
