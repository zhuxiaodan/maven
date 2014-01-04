package org.sunxin.guestbook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

public class ErrorProcessor implements Processor
{
    public void perform(HttpServletRequest req,
                          HttpServletResponse resp,
                          ServletContext sc)
    {
        String uri=(String)req.getAttribute("javax.servlet.error.request_uri");
        Exception excep=(Exception)req.getAttribute("javax.servlet.error.exception");
        StringBuffer sb=new StringBuffer(128);
        sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        sb.append("<page name=\"error\">");
        sb.append("<error>");
        sb.append("<uri>"+uri+"</uri>");
        sb.append("<msg>"+excep.getMessage()+"</msg>");
        sb.append("</error>");
        sb.append("</page>");
        req.setAttribute("page", sb.toString());
    }
}
