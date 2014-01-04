package org.sunxin.guestbook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

public class SuccessProcessor implements Processor
{
    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
    {
          String msg=(String)req.getAttribute("success");
          StringBuffer sb = new StringBuffer(128);
          sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
          sb.append("<page name=\"success\">");
          sb.append("<success>");
          sb.append("<msg>" + msg + "</msg>");
          sb.append("</success>");
          sb.append("</page>");
          req.setAttribute("page", sb.toString());
    }
}
