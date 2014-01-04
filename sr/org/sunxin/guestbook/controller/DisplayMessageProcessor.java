package org.sunxin.guestbook.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sunxin.guestbook.*;
import org.sunxin.guestbook.beans.*;

public class DisplayMessageProcessor implements Processor
{
    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException, ServletException,GuestbookException
    {
        GuestbookDB gstDB = (GuestbookDB)sc.getAttribute("gstdb");
        ResultSet rs=gstDB.getMessages();

        try
        {
            if(null==rs)
            {
                throw new GuestbookException("系统出错");
            }
            //移动游标到结果集的最后一行。
            rs.last();
            //得到当前行的行数，也就得到了数据库中留言的总数。
            int rowCount = rs.getRow();
            if (rowCount == 0)
            {
                StringBuffer sb=new StringBuffer(128);
                sb.append("<page name=\"guestbook\">");
                sb.append("<guestbook>");
                sb.append("<link>");
                sb.append("<firstpage>第一页</firstpage>");
                sb.append("<prevpage>上一页</prevpage>");
                sb.append("<nextpage>下一页</nextpage>");
                sb.append("<lastpage>最后页</lastpage>");
                sb.append("</link>");
                sb.append("</guestbook>");
                sb.append("</page>");
                req.setAttribute("page", sb.toString());
                return;
            }
            String strCurPage = req.getParameter("page");
            //表示当前显示的页数。
            int curPage;
            if (strCurPage == null || "".equals(strCurPage))
                curPage = 1;
            else
                curPage = Integer.parseInt(strCurPage);
            //定义每页显示的留言数。
            int countPerPage = 5;

            //计算显示所有留言需要的总页数。
            int pageCount = (rowCount + countPerPage - 1) / countPerPage;

            //移动游标到结果集中指定的行。如果显示的是第一页，curPage=1，
            //游标移动到第1行。
            rs.absolute( (curPage - 1) * countPerPage + 1);

            StringBuffer sb=new StringBuffer(1024);
            sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
            sb.append("<page name=\"guestbook\">");
            sb.append("<guestbook>");
            sb.append("<link>");

            //如果是第1页，则显示不带链接的文字，如果不是第1页，
            //则给用户提供跳转到第一页和上一页的链接。
            if(curPage==1)
            {
                sb.append("<firstpage>第一页</firstpage>");
                sb.append("<prevpage>上一页</prevpage>");
            }
            else
            {
                sb.append("<firstpage>");
                sb.append("<a href=\"index.jsp?page=1\">第一页</a>");
                sb.append("</firstpage>");
                sb.append("<prevpage>");
                sb.append("<a href=\"index.jsp?page="+(curPage-1)+"\">上一页</a>");
                sb.append("</prevpage>");
            }
            //如果当前页是最后一页，则显示不带链接的文字，如果不是最后一页，
			//则给用户提供跳转到最后一页和下一页的链接。
            if(curPage==pageCount)
            {
                sb.append("<nextpage>下一页</nextpage>");
                sb.append("<lastpage>最后页</lastpage>");
            }
            else
            {
                sb.append("<nextpage>");
                sb.append("<a href=\"index.jsp?page=" + (curPage + 1) +
                          "\">下一页</a>");
                sb.append("</nextpage>");
                sb.append("<lastpage>");
                sb.append("<a href=\"index.jsp?page=" + pageCount +
                          "\">最后页</a>");
                sb.append("</lastpage>");
            }
            sb.append("</link>");
            int i=0;

            //得到ResultSetMetaData对象，用于获取数据库表结构的信息。
            ResultSetMetaData rsMetaData=rs.getMetaData();
            //得到结果集中列的总数。
            int fieldCount=rsMetaData.getColumnCount();

            sb.append("<record>");

            //以循环的方式取出每页要显示的数据，因为在前面针对要显示的页数，
            //调用了rs.absolute((curPage-1)*countPerPage+1);
            //所以是从游标所在的位置取出当前页要显示的数据。
            while(i<countPerPage && !rs.isAfterLast())
            {
                sb.append("<row>");
                for(int j=1;j<=fieldCount;j++)
                {
                    //得到列的名字。
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
            throw new ServletException("系统出错");
        }
    }
}
