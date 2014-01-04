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
        //�жϹ���Ա�Ƿ��Ѿ���¼��
        HttpSession session = req.getSession();
        Object manager = session.getAttribute("manager");
        if (null == manager)
            throw new UserException(
                "����û�е�¼������<a href=\"admin_logon.jsp\">��¼</a>!");
        else
        {
            String strID = req.getParameter("gst_id");
            if (null == strID || "".equals(strID))
            {
                throw new GuestbookException("������������!");
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
                //ɾ�����Գɹ���������ʾ�������ԡ�
                new AdminDisplayMessageProcessor().perform(req,resp,sc);
            }
        }
    }
}
