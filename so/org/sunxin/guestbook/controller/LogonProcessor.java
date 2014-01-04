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
        //判断用户是访问登录页面还是提交登录信息。
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

            //实际应用中，应该在客户端利用JavaScript脚本也做类似的判断。
            if(null==username || null==password ||
               "".equals(username) || "".equals(password))
            {
                throw new UserException(
                    "用户名或密码不能为空，请<a href=\"logon.jsp\">返回</a>重新输入!");
            }

            User user=new User();
            user.setUsername(username);
            user.setPassword(password);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");

            //调用GuestbookDB对象的validateUser()方法对用户进行验证。
            String style=gstDB.validateUser(user);

            if("".equals(style))
            {
                throw new UserException(
                    "用户名或密码错误，请<a href=\"logon.jsp\">返回</a>重新输入!");
            }
            else
            {
                HttpSession session=req.getSession();
                session.setAttribute("user",username);
                session.setAttribute("style",style);
                String strMsg=null;
                strMsg="登录成功，<a href=\"say.jsp\">我要留言</a>或<a href=\"index.jsp\">查看留言</a>";
                req.setAttribute("success",strMsg);
                new SuccessProcessor().perform(req, resp,sc);
            }
        }
    }
}
