package org.sunxin.guestbook.util;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class Sax2Xml extends DefaultHandler
{
    private XMLReader xmlReader;
    private Writer out;
    private String line="\n";

    public Sax2Xml()
    {
        line=System.getProperty("line.separator");
    }

    public Sax2Xml(XMLReader xmlReader,Writer w)
    {
        this();
        this.xmlReader=xmlReader;
        this.out=w;
        this.xmlReader.setContentHandler(this);
    }

    public void setParser(XMLReader xmlReader)
    {
        this.xmlReader=xmlReader;
        this.xmlReader.setContentHandler(this);
    }
    public void setWriter(Writer w)
    {
        this.out=w;
    }
    public void print(String str)
    {
        try
        {
            out.write(str);
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
    public void println()
    {
        try
        {
            out.write(line);
            out.flush();
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
    }
    public void startDocument() throws SAXException
    {
        print("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        println();
    }
    public void endDocument() throws SAXException
    {
        try
        {
            out.flush();
        }
        catch (IOException ex)
        {
            System.err.println(ex.toString());
        }

    }
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes)
                      throws SAXException
    {
        print("<");
        print(qName);
        if(attributes!=null)
        {
            int length=attributes.getLength();
            for(int i=0;i<length;i++)
            {
                print(" ");
                print(attributes.getQName(i));
                print("=\"");
                print(attributes.getValue(i));
                print("\"");
            }
        }
        print(">");
    }
    public void endElement(String uri,
                           String localName,
                           String qName)
                    throws SAXException
    {
        print("</"+qName+">");
        println();
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        print(new String(ch,start,length));
    }

    public void parse(InputSource input) throws IOException,SAXException
    {
        xmlReader.parse(input);
    }

    public void parse(String systemId) throws IOException,SAXException
    {
        xmlReader.parse(systemId);
    }
}
