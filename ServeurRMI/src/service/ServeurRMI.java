package service;
import java.io.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.lang.String;
import java.util.Collections;
import java.util.Scanner;

public class ServeurRMI extends Thread {

    long genererInt(long leftLimit, long rightLimit) {
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }



    long nbrAlea = genererInt(1000L, 3000L);


    public ServeurRMI(){

    }
    public static void main(String[] args) throws IOException, InterruptedException {

        // pour récuperer les commentaires de la base de donnée
        ArrayList<Commentaire> commentaire = new ArrayList<>();
        // pour récuperer les commentairesdescommentaires  de la base de donnée
        ArrayList<Commentaire> Subcomment = new ArrayList<>();
        ArrayList<String> resultats = new ArrayList<>();
        ArrayList<Message> message= new ArrayList<>();

        try {
// Ici on démarre l'annuaire ds le serveur car on  doit tjrs démarrer l'annuaire avt le serveur
            LocateRegistry.createRegistry(1099);
// Création d'un objet distant qui crée autquement un objet UnicastRemoteObject

            // transmission de l'objet

            ServiceRMI od = new ServiceRMI();
            System.out.println(od.toString());

//pr afficher la référence de  l'objet distant
            System.out.println(od.toString());
//pour publier l'objet distant ie afficher la référence de l'od(qsq est publié ds l'annuaire)
            Naming.rebind("rmi://localhost:1099/BK", od);




        // pour récuperer les messages de la base de donnée

        ServeurRMI ser = new ServeurRMI();

        Thread run = new Thread(() -> {
            String t;
            BufferedReader prIN;
            System.getProperties().put("java.protocol.handler.pkgs", "service");
            try {
                FpContentHandlerFactory Factory = new FpContentHandlerFactory();
                URLConnection.setContentHandlerFactory(Factory);
                if (args.length != 1)
                    error("Usage: java fileHandler file://localhost/chemainAcces");
                URL url = new URL(args[0]);
                URLConnection con = url.openConnection();
                prIN = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                FpContent ftpContent = (FpContent) url.getContent();
                //System.out.println("file content : " + ftpContent.getFile());
                //System.out.println("URL :"+ftpContent.getHost()+""+ftpContent.getFileName());

                // try avec ressources s'occupe de la fermeture de bufferReader automatiquement donc pas besoin de close

                // chaque ligne de reseauSocial est une chaine de caractéres


                while ((t = prIN.readLine()) != null) {
                    //System.out.println(t);
                /* si la limite est negatif on prend en consédiration toutes les chaines vides donc faut mettre -1
                         pour prendre en conséderation les chaines vides aprés la barre */
                    // pour que la lecture soit plus correcte

                    String[] line = t.split("\\|", -1);
                    if (line[4].isEmpty() && line[5].isEmpty()) {
                        Message m = new Message(Integer.parseInt(line[0]),
                                Integer.parseInt(line[1]), line[2], line[3]);
                        m.decreasedScore();
                        message.add(m);
                    }
                    // le commentaire d un message
                    else if (line[4].isEmpty() && !line[5].isEmpty()) {
                        Commentaire c = new Commentaire(Integer.parseInt(line[0]), Integer.parseInt(line[1]),
                                line[2], line[3], -1, Integer.parseInt(line[5]));
                        c.addMyEventListener(() -> {

                            for (int i = 0; i < message.size(); i++) {
                                if (message.get(i).getIdMessage() == c.getPidMessage()) {
                                    // j ai besoin de savoir de combien ce commentaire a ete decrimenté
                                    message.get(i).decreasedScore();
                                    if (message.get(i).getScoreMes() <= 0) {
                                        message.remove(message.get(i));
                                    }
                                }
                            }

                        });

                        c.decreasedScore();
                        commentaire.add(c);
                   /*for (service.Commentaire e:commentaire) {
                        System.out.println(e.toString());

                    }*/
                        for (int i = 0; i < message.size(); i++) {
                            if (message.get(i).getIdMessage() == c.getPidMessage()) {
                                message.get(i).increasedScore(c.getScoreCom());
                            }
                        }
                        // ordoner la liste des messages selon un ordre de score décroissant grace a la methode
                        Collections.sort(message, Collections.reverseOrder());

                    } else if (!line[4].isEmpty() && line[5].isEmpty()) {
                        Commentaire Subcom = new Commentaire(Integer.parseInt(line[0]), Integer.parseInt(line[1]),
                                line[2], line[3], Integer.parseInt(line[4]), -1);
                        Subcom.decreasedScore();
                        commentaire.add(Subcom);
                        for (Commentaire m : commentaire) {
                            if (m.getIdCommentaire() == Integer.parseInt(line[4])) {
                                // ici on incremente le score  d' un commentaire si on lui trouve des subComments
                                //donc faut mettre a jour le score du message en lui ajoutant le score de ce subcoment
                                //sur ce commentaire
                                // on ajoute un ecouteur pour savoir si le commentaire d un message a decrimente son score
                                // pour mettre a jour son score
                                Subcom.addEventListener(() -> {
                                    // a ameliorer  l idee est de la faire avec un thread

                                    for (int i = 0; i < message.size(); i++) {
                                        if (message.get(i).getIdMessage() == m.getPidMessage()) {
                                            // j ai besoin de savoir de combien ce commentaire a ete decrimenté
                                            message.get(i).increasedScore(Subcom.getScoreCom());

                                        }
                                    }

                                });


                                m.increasedScore(Subcom.getScoreCom());
                            }

                        }

                    }
                    try {
                        Thread.sleep(ser.nbrAlea);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // pour passer à la ligne suivante
                    try {
                        prIN.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            } catch (MalformedURLException ex) {
                System.out.println(ex);
                error("Bad URL");
            } catch (IOException ex) {
                System.out.println(ex);
                error("IOException occurred.");
            }

            for (Message e : message) {
                // System.out.println(e.toString());
            }

        });
        run.start();
        while (run.isAlive()) {
            //String bestMessage = od.get_BestMessage(message);
            od.setAllMessages(message);
             //System.out.println(bestMessage);
            //resultats.add(bestMessage);
           // System.out.println(bestMessage);
        }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void error(String s) {
        System.out.println(s);
        System.exit(1);
    }

}







