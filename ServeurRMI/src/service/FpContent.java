package service;


public class FpContent {
    public String login;
    public String passwd;
    public String  host;
    public String fileName;
    public String file;

    public FpContent(String a,String b,String c ,String d,String e){
        login=a;
        passwd=b;
        host=c;
        fileName=d;
        file=e;
    }
    public void setLogin(String d){
        login=d;
    }
    public String getLogin(){
        return login;
    }
    public void setPasswd(String s){
        passwd=s;
    }
    public String getPasswd(){
        return passwd;
    }
    public void setHost(String g){
        host=g;
    }
    public String getHost(){
        return host;
    }
    public void setFileName(String j){
        fileName=j;
    }
    public String getFileName(){
        return fileName;
    }
    public void setFile(String s){
        file=s;
    }
    public String getFile(){
        return file;
    }

}


