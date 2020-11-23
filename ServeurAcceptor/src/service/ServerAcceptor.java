package service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;
public class ServerAcceptor implements Runnable {
    int nbClients;
    ArrayList<String> name = new ArrayList<>();


    public ServerAcceptor(){
    }

    @Override
    public void run() {

        try {
            ServerSocket ss = new ServerSocket(80);
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

    /* Connexion vers l'annuaire ie lui envoyer une requête avec lookup
      lookup recupère un objet de type object donc, on'a besoin de faire un sous-casting
     vers interRemote, pr le stub on utilise seulement l'interface car les méthodes dont on a besoin
      se trouve ds l'interface. L'objet qu'on veut appeler se trouve ds une autre machine.
     */
        public static void main(String[] args) {
            ArrayList<String>resultats = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ServerAcceptor ser= new ServerAcceptor();

            try {
                InterRemote stub = (InterRemote)Naming.lookup("rmi://localhost:1099/BK");

                while (true) {
                    System.out.println("Insert client name : ");
                    Scanner sc = new Scanner(System.in);
                    String clientName = sc.nextLine();
                    // la liste des clients connectés
                    name.add( clientName);
                    String bestMessage =stub.get_BestMessage(stub.getAllMessages());
                    resultats.add(bestMessage);
                    if(clientName.equals("exit"))
                        break;
                    System.out.println(bestMessage);

                    try {
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                        // création de l'élément racine
                        Document doc = docBuilder.newDocument();
                        Element rootElement = doc.createElement("messages");
                        doc.appendChild(rootElement);

                        for (int j = 0; j < resultats.size(); j++) {
                            // création des elements message
                            Element staff = doc.createElement("message");
                            // Définition des attributs pour chaque message
                            Attr attr = doc.createAttribute("id");
                            staff.setAttributeNode(attr);
                            //nom des clients
                            Element name_node = doc.createElement("nomClient");
                            attr.setValue(String.valueOf(j+1));
                                name_node.appendChild(doc.createTextNode(name.get(j)));
                                staff.appendChild(name_node);
                                rootElement.appendChild(staff);
                            // Message
                            Element message_node = doc.createElement("message");
                            message_node.appendChild(doc.createTextNode(resultats.get(j)));
                            staff.appendChild(message_node);
                            // Ecriture de contenu dans un fichier XML
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult save_file = new StreamResult(new File("messages.xml"));
                            transformer.transform(source, save_file);
                            System.out.println("Fichier enregistré au format messages.xml!!!");

                        }

                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

        // tant que le thread execute le run donc la lecture et le calcul













