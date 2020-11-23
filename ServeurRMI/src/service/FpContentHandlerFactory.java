package service;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class FpContentHandlerFactory implements ContentHandlerFactory{
    @Override
    public ContentHandler createContentHandler(String mimeType) {
        return new FpContentHandler();
    }

}







