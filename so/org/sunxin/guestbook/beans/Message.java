package org.sunxin.guestbook.beans;

import java.io.Serializable;

public class Message implements Serializable
{
    //留言的用户名。
    private String username;
    //留言的主题。
    private String title;
    //留言的内容。
    private String content;
    //留言用户的IP地址。
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
