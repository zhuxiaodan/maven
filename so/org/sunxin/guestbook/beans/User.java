package org.sunxin.guestbook.beans;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable
{
    //�û�����
    private String username;
    //���롣
    private String password;
    //�ʼ���ַ��
    private String email;
    //��ʾ����б�
    private List styleList;
    //�û�ѡ�����ʾ���
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
