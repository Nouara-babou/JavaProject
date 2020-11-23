package service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


//Implémentation de service en l'occurrence les méthodes définies dans l'interface
    public class ServiceRMI extends UnicastRemoteObject implements InterRemote {
        private ArrayList<Message>resultat ;
        public  ServiceRMI() throws RemoteException {
            super();
            this.resultat  = new ArrayList<>();
        }
        @Override
        public ArrayList<Message> getAllMessages() {
            return resultat;
        }
        @Override
        public String get_BestMessage(ArrayList<Message> messages){
            if(messages.size()>2) {
                return " the best messages :" + messages.get(0).MessagetoSend() + messages.get(1).MessagetoSend() +
                        messages.get(2).MessagetoSend();
            }else { return "Wait the messages are coming";
            }
        }
        public void setAllMessages(ArrayList<Message>resultat) {
            this.resultat = resultat;
        }



    }







