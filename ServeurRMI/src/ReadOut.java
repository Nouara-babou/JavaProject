import service.Commentaire;
import service.Message;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;
import java.util.Collections;
public class ReadOut implements Runnable {
    // pour récuperer les messages de la base de donnée
    ArrayList<Message> message = new ArrayList<>();
    // pour récuperer les commentaires de la base de donnée
    ArrayList<Commentaire> commentaire = new ArrayList<>();
    // pour récuperer les commentairesdescommentaires  de la base de donnée
    ArrayList<Commentaire> Subcomment = new ArrayList<>();

    // le constructeur de la classe
    ReadOut() {
        message = new ArrayList<Message>();
        commentaire = new ArrayList<Commentaire>();

    }

    long genererInt(long leftLimit, long rightLimit) {
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }

    long nbrAlea = genererInt(1000L, 3000L);

    @Override
    public void run() {

        // try avec ressources s'occupe de la fermeture de bufferReader automatiquement donc pas besoin de close
        try (BufferedReader prIN = new BufferedReader(new FileReader("src/reseauSocial.txt"))) {
            // chaque ligne de reseauSocial est une chaine de caractéres
            String t;

            while ((t = prIN.readLine()) != null) {

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
                    Thread.sleep(nbrAlea);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // pour passer à la ligne suivante
                prIN.readLine();


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Message e : message) {
            // System.out.println(e.toString());
        }

    }

    // obtenir tous les messages qui sont trié selon leurs score
    public ArrayList<Message> getAllMessages() {
        return this.message;

    }


    public String get_BestMessage(ArrayList<Message> messages) {
        if (messages.size()>2) {
            return " the best messages :" + messages.get(0).MessagetoSend() + messages.get(1).MessagetoSend() +
                    messages.get(2).MessagetoSend();
        }
        else{ return "wait the messages are coming";}
    }
}





