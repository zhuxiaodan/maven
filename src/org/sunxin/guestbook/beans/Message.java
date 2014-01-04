package org.sunxin.guestbook.beans;

import java.io.Serializable;

public class Message implements Serializable
{
    //���Ե��û�����
    private String username;
    //���Ե����⡣
    private String title;
    //���Ե����ݡ�
    private String content;
    //�����û���IP��ַ��
    private String ip;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getUsername()
    {
        return username;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public String getIp()
    {
        return ip;
    }
}
