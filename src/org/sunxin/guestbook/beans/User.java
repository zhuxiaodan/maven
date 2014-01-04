package org.sunxin.guestbook.beans;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable
{
    //用户名。
    private String username;
    //密码。
    private String password;
    //邮件地址。
    private String email;
    //显示风格列表。
    private List styleList;
    //用户选择的显示风格。
    private String style;

    public void setUsername(String username)
    {

        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setStyleList(List styleList)
    {
        this.styleList = styleList;
    }

    public void setStyle(String style)
    {

        this.style = style;
    }

    public String getUsername()
    {

        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public List getStyleList()
    {
        return styleList;
    }

    public String getStyle()
    {
        return style;
    }
}
