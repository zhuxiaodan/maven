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

        //�ж��û��Ƿ��Ѿ���¼��
        if(null==session ||  null==(username=(String)session.getAttribute("user")))
        {
            throw new GuestbookException(
                "����û�е�¼������<a href=\"logon.jsp\">��¼</a>!");
        }
        String action=req.getParameter("action");
        //�ж��û��Ƿ�������ҳ�滹���ύ���ԡ�
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
                throw new ServletException("ҳ���������!");
            }
        }
        else
        {
            String title=req.getParameter("title");
            String content=req.getParameter("content");

            if(null==title || "".equals(title) || null==content)
            {
                throw new GuestbookException(
                    "�������ⲻ��Ϊ�գ���<a href=\"say.jsp\">����</a>������д!");
            }
            title=CharacterConvert.toHtml(title.trim());
            content=CharacterConvert.toHtml(content.trim());

            //����Message���󣬱����û����Ե������Ϣ��
            Message msgBean=new Message();
            msgBean.setUsername(username);
            msgBean.setTitle(title);
            msgBean.setContent(content);
            String fromIP=req.getRemoteAddr();
            msgBean.setIp(fromIP);

            GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
            //���û����Ե������Ϣ���浽���ݿ��С�
            gstDB.saveMessage(msgBean);

            String strMsg="���ͳɹ���<a href=\"say.jsp\">��������</a>������<a href=\"index.jsp\">�鿴����</a>";
            req.setAttribute("success",strMsg);
            new SuccessProcessor().perform(req, resp,sc);
        }
    }
}
