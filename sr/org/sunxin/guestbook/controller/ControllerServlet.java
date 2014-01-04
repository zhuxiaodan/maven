package org.sunxin.guestbook.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.dom4j.*;
import org.dom4j.io.*;
import org.sunxin.guestbook.beans.*;


public class ControllerServlet extends HttpServlet
{
    private ServletContext sc;
    private static final String CONFIG_FILE = "/WEB-INF/guestbook-config.xml";

    private HashMap hm=new HashMap();

    public void init() throws ServletException
    {
        //得到ServletContext对象。
        sc=getServletContext();

        SAXReader saxReader = new SAXReader();
        InputStream is=sc.getResourceAsStream(CONFIG_FILE);
        try
        {
            Document doc = saxReader.read(is);
            Element root = doc.getRootElement();
            Element eltMapping=root.element("processor-mappings");
            List childrenList=eltMapping.elements("processor");
            Iterator it=childrenList.iterator();
            while(it.hasNext())
            {
                Element elt = (Element)it.next();
                hm.put(elt.attributeValue("path"),elt.attributeValue("type"));
            }
        }
        catch(DocumentException de)
        {
            System.err.println(de.toString());
            throw new UnavailableException("系统设置错误！");
        }

        GuestbookDB gstDB=new GuestbookDB();
        sc.setAttribute("gstdb",gstDB);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, java.io.IOException
    {
        req.setCharacterEncoding("GB2312");
        String uri=req.getRequestURI();
        String ctxPath=req.getContextPath();
        uri=uri.substring(ctxPath.length());

        String className=(String)hm.get(uri);

        if(null!=className)
        {
            Processor processor=null;
            try
            {
                ClassLoader classLoader = Thread.currentThread().
                    getContextClassLoader();
                if (classLoader == null)
                {
                    classLoader = ControllerServlet.class.getClassLoader();

                }
                Class pClass = classLoader.loadClass(className);
                processor = (Processor) pClass.newInstance();
            }
            catch (ClassNotFoundException ex)
            {
                System.err.println(ex.toString());
            }
            catch (InstantiationException ie)
            {
                System.err.println(ie.toString());
            }
            catch (IllegalAccessException iae)
            {
                System.err.println(iae.toString());
            }
            try
            {
                if(null!=processor)
                {
                    //对请求进行处理，创建页面XML文档。
                    processor.perform(req, resp, sc);
                }
                else
                {
                    //如果创建处理请求的Processor实例失败，
                    //则抛出“请求页面不存在”的异常信息。
                    ServletException se = new ServletException("您所请求的页面不存在!");
                    ExceptionHandler(req, resp, sc, se);
                }
            }
            catch (Exception e)
            {
                ExceptionHandler(req, resp, sc, e);
            }
        }
        else
        {
            //如果用户访问的页面URI没有在guestbook-config.xml中配置，
            //则抛出“请求页面不存在”的异常信息。
            ServletException se=new ServletException("您所请求的页面不存在!");
            ExceptionHandler(req,resp,sc,se);
        }
        //将请求转发（forward）到XsltServlet，
        //XsltServlet负责产生HTML页面并发送到客户端的。
        req.getRequestDispatcher("/xsltservlet").forward(req, resp);
    }

    private void ExceptionHandler(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  ServletContext sc,
                                  Exception e)
    {
        //将异常对象保存到请求对象中。
        req.setAttribute("javax.servlet.error.exception",e);
        //将客户端请求的URI保存到请求对象中。
        req.setAttribute("javax.servlet.error.request_uri",req.getRequestURI());
        //ErrorProcessor实例负责产生错误页面的XML文档。
        new ErrorProcessor().perform(req,resp,sc);
    }

}
