package org.sunxin.guestbook.controller;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;
import org.sunxin.guestbook.util.*;

public class SayMessageProcessor implements Processor
{
    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException,ServletException,GuestbookException
    {
        HttpSession session=req.getSession(false);
        String username=null;

        //判断用户是否已经登录。
        if(null==session ||  null==(username=(String)session.getAttribute("user")))
        {
            throw new GuestbookException(
                "您还没有登录，请先<a href=\"logon.jsp\">登录</a>!");
        }
        String action=req.getParameter("action");
        //判断用户是访问留言页面还是提交留言。
        if(null==action || !"say".equals(action))
        {
            try
            {
                StringBuffer sb=new StringBuffer();
                sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
                sb.append("<page name=\"say\">");
                sb.append("<say/></page>");
                req.setAttribute("page", sb.toString());

            }
            catch (Exception e)
            {
                throw new ServletException("页面产生错误!");
            }
        }
        else
        {
            String title=req.getParameter("title");
            String content=req.getParameter("content");

            if(null==title || "".equals(title) || null==content)
            {
                throw new GuestbookException(
                    "留言主题不能为空，请<a href=\"say.jsp\">返回</a>重新填写!");
            }
            title=CharacterConvert.toHtml(title.trim());
            content=CharacterConvert.toHtml(content.trim());

            //创建Message对象，保存用户留言的相关信息。
            Message msgBean=new Message();
            msgBean.setUsername(username);
            msgBean.setTitle(title);
            msgBean.setContent(content);
            String fromIP=req.getRemoteAddr();
            msgBean.setIp(fromIP);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
            //将用户留言的相关信息保存到数据库中。
            gstDB.saveMessage(msgBean);

            String strMsg="发送成功，<a href=\"say.jsp\">继续留言</a>，或者<a href=\"index.jsp\">查看留言</a>";
            req.setAttribute("success",strMsg);
            new SuccessProcessor().perform(req, resp,sc);
        }
    }
}
