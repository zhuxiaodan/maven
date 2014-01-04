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
     * �õ����ݿ����ӡ�
     */
    public Connection getConnection() throws SQLException
    {
       return ds.getConnection();
    }

    /**
     * �ر����Ӷ���
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
     * �ر�Statement����
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
     * �ر�PreparedStatement����
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
     * �ر�ResultSet����
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
     * ע���û���
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
                throw new UserException("�û����Ѿ�����");
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
            throw new UserException("�û�ע��ʧ�ܣ�");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }
    /**
     * �Ե�¼�û�������֤��
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
            throw new UserException("������æ���޷���֤�û�������ϵ����Ա��");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * �������ԡ�
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
           throw new GuestbookException("��������ʧ�ܣ�");
        }
        finally
        {
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }
    /**
     * ��ȡ�������ԡ�
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
           throw new GuestbookException("��ȡ���Բ���ʧ�ܣ�����ϵ����Ա��");
        }
    }
    /**
     * �Թ���Ա��¼������֤��
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
            throw new UserException("������æ���޷���֤�û���");
        }
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * ɾ��ָ�������ԡ�
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
           throw new GuestbookException("ɾ������ʧ�ܣ�");
        }
        finally
        {
            closePreparedStatement(pstmt);
            closeConnection(conn);
        }
    }

}
