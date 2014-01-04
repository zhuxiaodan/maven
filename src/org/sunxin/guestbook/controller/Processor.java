package org.sunxin.guestbook.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import org.sunxin.guestbook.GuestbookException;

public interface Processor
{
    /**
     * ��������д���
     */
    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
        throws IOException, ServletException, GuestbookException;
}
