package org.sunxin.guestbook.beans;

import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

import org.sunxin.guestbook.*;

public class GuestbookDB implements Serializable
{
    private DataSource ds=null;

    public GuestbookDB()
    {
        try
        {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/d13ad91748cf346f1b50017e7e1b6cc6d");
        }
        catch (NamingException ne)
        {
            System.err.println(ne.toString());
        }
    }

    /**
     * 得到数据库连接。
     */
    public Connection getConnection() throws SQLException
    {
       return ds.getConnection();
    }

    /**
     * 关闭连接对象。
     */
    protected void closeConnection(Connection conn)
    {
        if(conn!=null)
        {
            try
            {
                conn.close();
                conn=null;
            }
            catch (SQLException ex)
            {
                System.err.println(ex.toString());
            }
        }
    }

    /**
     * 关闭Statement对象。
     */
    protected void closeStatement(Statement stmt)
    {
        if(stmt!=null)
        {
            try
            {
                stmt.close();
                stmt=null;
            }
            catch (SQLException ex)
            {
                System.err.println(ex.toString());
            }
        }
    }

    /**
     * 关闭PreparedStatement对象。
     */
    protected void closePreparedStatement(PreparedStatement pstmt)
    {
        if(pstmt!=null)
        {
            try
            {
                pstmt.close();
                pstmt=null;
            }
            catch (SQLException ex)
            {
                System.err.println(ex.toString());
            }
        }
    }
    /**
     * 关闭ResultSet对象。
     */
    protected void closeResultSet(ResultSet rs)
    {
        if(rs!=null)
        {
            try
            {
                rs.close();
                rs=null;
            }
            catch (SQLException ex)
            {
                System.err.println(ex.toString());
            }
        }
    }
    /**
     * 注册用户。
     */
    public void registerUser(User user) throws UserException
    {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstmt=null;
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                "select username from users where username=?");
            pstmt.setString(1,user.getUsername());
            rs=pstmt.executeQuery();
            if(rs.next())
                throw new UserException("用户名已经存在");
            pstmt = conn.prepareStatement(
                "insert into users values(?,?,?,?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getStyle());
            pstmt.executeUpdate();
        }
        catch(SQLException se)
        {
            System.err.println(se.toString());
            throw new UserException("用户注册失败！");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }
    /**
     * 对登录用户进行验证。
     */
    public String validateUser(User user) throws UserException
    {
        Connection conn=null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                "select style from users where username=? and password=?");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
            else
                return "";
        }
        catch (SQLException se)
        {
            System.err.println(se.toString());
            throw new UserException("服务器忙，无法验证用户，请联系管理员。");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * 保存留言。
     */
    public void saveMessage(Message msg) throws GuestbookException
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                "insert into gst(gst_user,gst_title,gst_content,gst_ip) values(?,?,?,?)");
            pstmt.setString(1, msg.getUsername());
            pstmt.setString(2, msg.getTitle());
            pstmt.setString(3, msg.getContent());
            pstmt.setString(4, msg.getIp());
            pstmt.executeUpdate();
        }
        catch (SQLException se)
        {
           System.err.println(se.toString());
           throw new GuestbookException("保存留言失败！");
        }
        finally
        {
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }
    /**
     * 获取所有留言。
     * @return ResultSet
     */
    public ResultSet getMessages() throws GuestbookException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs=null;
        try
        {
            conn = getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery("select * from gst order by gst_time desc");
            return rs;
        }
        catch (SQLException se)
        {
           System.err.println(se.toString());
           throw new GuestbookException("获取留言操作失败，请联系管理员。");
        }
    }
    /**
     * 对管理员登录进行验证。
     */
    public boolean validateManager(User user) throws UserException
    {
        Connection conn=null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                "select username from managers where username=? and password=?");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            rs = pstmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        }
        catch (SQLException se)
        {
            System.err.println(se.toString());
            throw new UserException("服务器忙，无法验证用户。");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * 删除指定的留言。
     */
    public void deleteMessage(int id) throws GuestbookException
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                "delete from gst where gst_id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException se)
        {
           System.err.println(se.toString());
           throw new GuestbookException("删除留言失败！");
        }
        finally
        {
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

}
