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
        //�õ�ServletContext����
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
            throw new UnavailableException("ϵͳ���ô���");
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
                    //��������д�������ҳ��XML�ĵ���
                    processor.perform(req, resp, sc);
                }
                else
                {
                    //����������������Processorʵ��ʧ�ܣ�
                    //���׳�������ҳ�治���ڡ����쳣��Ϣ��
                    ServletException se = new ServletException("���������ҳ�治����!");
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
            //����û����ʵ�ҳ��URIû����guestbook-config.xml�����ã�
            //���׳�������ҳ�治���ڡ����쳣��Ϣ��
            ServletException se=new ServletException("���������ҳ�治����!");
            ExceptionHandler(req,resp,sc,se);
        }
        //������ת����forward����XsltServlet��
        //XsltServlet�������HTMLҳ�沢���͵��ͻ��˵ġ�
        req.getRequestDispatcher("/xsltservlet").forward(req, resp);
    }

    private void ExceptionHandler(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  ServletContext sc,
                                  Exception e)
    {
        //���쳣���󱣴浽��������С�
        req.setAttribute("javax.servlet.error.exception",e);
        //���ͻ��������URI���浽��������С�
        req.setAttribute("javax.servlet.error.request_uri",req.getRequestURI());
        //ErrorProcessorʵ�������������ҳ���XML�ĵ���
        new ErrorProcessor().perform(req,resp,sc);
    }

}
