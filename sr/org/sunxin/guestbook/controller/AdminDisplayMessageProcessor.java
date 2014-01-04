package org.sunxin.guestbook.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;

public class AdminDisplayMessageProcessor implements Processor
{
    private String index_uri="admin_index.jsp";

    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException, ServletException,GuestbookException
    {
        //�жϹ���Ա�Ƿ��Ѿ���¼��
        HttpSession session=req.getSession();
        String sessionManager=(String)session.getAttribute("manager");
        if(null==sessionManager)
        {
            throw new UserException(
                "����û�е�¼������<a href=\"admin_logon.jsp\">��¼</a>!");
        }

        //����������б���manager���ԣ�XsltServlet������������Ե�ֵ���ж�
        //�Ƿ���ʽ�������user��ֵ����Ϊ��manager����
        req.setAttribute("manager","manager");

        GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
        ResultSet rs=gstDB.getMessages();
        try
        {
            if(null==rs)
            {
                throw new GuestbookException("ϵͳ����");
            }
            //�ƶ��α굽����������һ�С�
            rs.last();
            //�õ���ǰ�е�������Ҳ�͵õ������ݿ������Ե�������
            int rowCount = rs.getRow();
            if (rowCount == 0)
            {
                StringBuffer sb=new StringBuffer(128);
                sb.append("<page name=\"guestbook\">");
                sb.append("<guestbook>");
                sb.append("<link>");
                sb.append("<firstpage>��һҳ</firstpage>");
                sb.append("<prevpage>��һҳ</prevpage>");
                sb.append("<nextpage>��һҳ</nextpage>");
                sb.append("<lastpage>���ҳ</lastpage>");
                sb.append("</link>");
                sb.append("</guestbook>");
                sb.append("</page>");
                req.setAttribute("page", sb.toString());
                return;
            }
            String strCurPage = req.getParameter("page");
            //��ʾ��ǰ��ʾ��ҳ����
            int curPage;
            if (strCurPage == null)
                curPage = 1;
            else
                curPage = Integer.parseInt(strCurPage);
            //����ÿҳ��ʾ����������
            int countPerPage = 5;

            //������ʾ����������Ҫ����ҳ����
            int pageCount = (rowCount + countPerPage - 1) / countPerPage;

            //�ƶ��α굽�������ָ�����С������ʾ���ǵ�һҳ��curPage=1��
            //�α��ƶ�����1�С�
            rs.absolute( (curPage - 1) * countPerPage + 1);

            StringBuffer sb=new StringBuffer(1024);
            sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
//            sb.append(File.separator);
            sb.append("<page name=\"guestbook\">");
            sb.append("<guestbook>");
            sb.append("<link>");

            //����ǵ�1ҳ������ʾ�������ӵ����֣�������ǵ�1ҳ��
            //����û��ṩ��ת����һҳ����һҳ�����ӡ�
            if(curPage==1)
            {
                sb.append("<firstpage>��һҳ</firstpage>");
                sb.append("<prevpage>��һҳ</prevpage>");
            }
            else
            {
                sb.append("<firstpage>");
                sb.append("<a href=\""+index_uri+"?page=1\">��һҳ</a>");
                sb.append("</firstpage>");
                sb.append("<prevpage>");
                sb.append("<a href=\""+index_uri+"?page="+(curPage-1)+"\">��һҳ</a>");
                sb.append("</prevpage>");
            }
            if(curPage==pageCount)
            {
                sb.append("<nextpage>��һҳ</nextpage>");
                sb.append("<lastpage>���ҳ</lastpage>");
            }
            else
            {
                sb.append("<nextpage>");
                sb.append("<a href=\""+index_uri+"?page=" + (curPage + 1) +
                          "\">��һҳ</a>");
                sb.append("</nextpage>");
                sb.append("<lastpage>");
                sb.append("<a href=\""+index_uri+"?page=" + pageCount +
                          "\">���ҳ</a>");
                sb.append("</lastpage>");
            }
            sb.append("</link>");
            int i=0;

            ResultSetMetaData rsMetaData=rs.getMetaData();
            int fieldCount=rsMetaData.getColumnCount();

            sb.append("<record>");
            //��ѭ���ķ�ʽȡ��ÿҳҪ��ʾ�����ݣ���Ϊ��ǰ�����Ҫ��ʾ��ҳ����
            //������rs.absolute((curPage-1)*countPerPage+1);
            //�����Ǵ��α����ڵ�λ��ȡ����ǰҳҪ��ʾ�����ݡ�
            while(i<countPerPage && !rs.isAfterLast())
            {
                sb.append("<row>");
                for(int j=1;j<=fieldCount;j++)
                {
                    String columnName=rsMetaData.getColumnName(j);
                    sb.append("<field name=\""+columnName.toLowerCase()+"\">");
                    if(5==j)
                    {
                        Timestamp ts=rs.getTimestamp("gst_time");
                        long lms=ts.getTime();
                        Date date=new Date(lms);
                        Time time=new Time(lms);
                        sb.append(date+" "+time);
                    }
                    else
                        sb.append(rs.getString(j));
                    sb.append("</field>");
                }
                sb.append("</row>");
                i++;
                rs.next();
            }
            rs.close();
            sb.append("</record>");
            sb.append("</guestbook>");
            sb.append("</page>");
            req.setAttribute("page", sb.toString());
        }
        catch (SQLException sx)
        {
            throw new ServletException("ϵͳ����");
        }
    }
}
