package org.sunxin.guestbook.parser;

import java.io.*;
import java.util.*;

import org.sunxin.guestbook.beans.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class RegisterPageParser  implements XMLReader
{
    private ContentHandler contentHandler;
    private User userBean=null;

    public RegisterPageParser(User userBean)
    {
            this.userBean=userBean;
    }

    public void setContentHandler(ContentHandler handler)
    {
        this.contentHandler=handler;
    }

    public ContentHandler getContentHandler()
    {
        return contentHandler;
    }

    public void parse(InputSource input) throws IOException, SAXException
    {
        parse();
    }

    public void parse(String systemId) throws IOException, SAXException
    {
        parse();
    }

    public void parse() throws SAXException
    {
        contentHandler.startDocument();
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute(null,null,"name",null,"register");
        contentHandler.startElement(null,null,"page",attrs);
        contentHandler.startElement(null,null,"register",null);

        contentHandler.startElement(null,null,"styleList",null);
        List list=userBean.getStyleList();
        Iterator it=list.iterator();
        while(it.hasNext())
        {
            String item=(String)it.next();
            contentHandler.startElement(null,null,"item",null);
            contentHandler.characters(item.toCharArray(),0,item.length());
            contentHandler.endElement(null,null,"item");

        }
        contentHandler.endElement(null,null,"styleList");

        contentHandler.endElement(null,null,"register");
        contentHandler.endElement(null,null,"page");
        contentHandler.endDocument();
    }

    public boolean getFeature(String name) throws SAXNotRecognizedException,
        SAXNotSupportedException
    {
        return false;
    }

    public void setFeature(String name, boolean value) throws
        SAXNotRecognizedException, SAXNotSupportedException
    {
    }

    public Object getProperty(String name) throws SAXNotRecognizedException,
        SAXNotSupportedException
    {
        return null;
    }

    public void setProperty(String name, Object value) throws
        SAXNotRecognizedException, SAXNotSupportedException
    {
    }

    public void setEntityResolver(EntityResolver resolver)
    {
    }

    public EntityResolver getEntityResolver()
    {
        return null;
    }

    public void setDTDHandler(DTDHandler handler)
    {
    }
    public DTDHandler getDTDHandler()
    {
        return null;
    }

    public void setErrorHandler(ErrorHandler handler)
    {
    }
    public ErrorHandler getErrorHandler()
    {
        return null;
    }
}
