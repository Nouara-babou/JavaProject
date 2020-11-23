package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URL;
import java.net.URLConnection;


public class FpContentHandler extends ContentHandler {

    public String login = "";
    public String passwd = "";
    public String host = "";
    public String fileName = "";
    public String file = "";
    public FpContentHandler(){}
    public Object getContent(URLConnection urlc) throws IOException {
        URL url = urlc.getURL();
        //String[] userInfo = url.getUserInfo().split(":");
        //login = userInfo[0];
        //passwd = userInfo[1];
        host = url.getHost();
        //fileName = url.getQuery();
        fileName=url.getFile();
        setFile();
        return new service.FpContent(login ,passwd ,host ,fileName ,file);
    }

    public  void setFile() throws IOException {
        //file="C:\\Users\\mhend\\Documents\\PARTIESERVEUR\\src\\service\\reseauSocial.txt";
        InputStream in = new FileInputStream(fileName);
        int b;
        while ((b = in.read()) != -1) {
            file += (char) b;
        };

        in.close();
    }
    public static void error(String s){
        System.out.println(s);
        System.exit(1);
    }

}




