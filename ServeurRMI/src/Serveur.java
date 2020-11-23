import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Serveur extends Thread {

    public Serveur(){
    }

    int nbClients;
    ArrayList<String> name = new ArrayList<>();

        @Override
        public void run() {

            try {
                ServerSocket ss = new ServerSocket(33333);
                // en continue
                while(true) {
                    // la socket du client
                    Socket s =ss.accept();// capable de connecter les clients et attendre
                    ++nbClients;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public static void main(String[] args) throws IOException, InterruptedException {
            Serveur ser = new Serveur();
            ReadOut r = new ReadOut();
            Thread reader = new Thread(r);
            reader.start();

        // tant que le thread execute le run donc la lecture et le calcul
        while (reader.isAlive()) {


            System.out.println("Insert client name : ");
            Scanner sc = new Scanner(System.in);
            String clientName = sc.nextLine();
            // la liste des clients connectés
            ser.name.add( clientName);
           String bestMessage = r.get_BestMessage(r.getAllMessages());
            if(clientName.equals("exit"))
                break;
            System.out.println(bestMessage);
        }

    }

}

















