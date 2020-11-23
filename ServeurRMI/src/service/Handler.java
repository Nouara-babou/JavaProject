package service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;


public class Handler extends URLStreamHandler{
    @Override
    public FpURLConnexion openConnection(URL url) throws MalformedURLException, IOException {
        return new FpURLConnexion(url);
    }

}



