package org.sunxin.guestbook.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;

public class AdminDeleteMessageProcessor  implements Processor
{

    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException,ServletException,GuestbookException
    {
        //判断管理员是否已经登录。
        HttpSession session = req.getSession();
        Object manager = session.getAttribute("manager");
        if (null == manager)
            throw new UserException(
                "您还没有登录，请先<a href=\"admin_logon.jsp\">登录</a>!");
        else
        {
            String strID = req.getParameter("gst_id");
            if (null == strID || "".equals(strID))
            {
                throw new GuestbookException("错误的请求参数!");
            }
            else
            {
                int id = Integer.parseInt(strID);
                GuestbookDB gstDB = (GuestbookDB) sc.getAttribute("gstdb");
                try
                {
                    gstDB.deleteMessage(id);
                }
                catch (GuestbookException gx)
                {
                    throw gx;
                }
                //删除留言成功后，重新显示所有留言。
                new AdminDisplayMessageProcessor().perform(req,resp,sc);
            }
        }
    }
}
