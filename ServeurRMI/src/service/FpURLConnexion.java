package service;


import java.io.InputStream;
import java.net.ContentHandlerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FpURLConnexion extends URLConnection{
    public FpURLConnexion(URL url) throws MalformedURLException {
        super(url);
        setContentHandlerFactory(new service.FpContentHandlerFactory());
    }

    public void connect() {
        System.out.println("Connected!");
    }

    public InputStream getInputStream() { return null;  }

	    /*public String getHeaderField(String name) {
	          if (name.equals("content -type")) return "myhundler";
	          return null;
	        }*/
}


