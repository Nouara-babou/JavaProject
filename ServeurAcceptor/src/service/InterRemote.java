package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterRemote extends Remote{
    public ArrayList<Message> getAllMessages() throws RemoteException;
    public  String get_BestMessage(ArrayList<Message> messages) throws RemoteException;



}
