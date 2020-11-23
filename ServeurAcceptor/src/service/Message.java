package service;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Comparable<Message>, Serializable {

    private ZonedDateTime dateMessage;
    private int idMessage;
    private int idUser;
    private String message;
    private String user;
    private int scoreMes = 20;

    public Message(int idMessage, int idUser, String message, String user) {
        this.idMessage = idMessage;
        this.idUser = idUser;
        this.message = message;
        this.user = user;
        this.scoreMes = scoreMes;
        this.dateMessage = ZonedDateTime.now();

    }

    public ZonedDateTime getDateMessage() {
        return this.dateMessage;
    }

    public int getIdMessage() {
        return this.idMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public String getUser() {
        return this.user;
    }

    public int getIduser() {
        return this.idUser;
    }

    public int getScoreMes() {
        return this.scoreMes;
    }


    // faut faire ca avec un thread car la fonction bloque le programme et on sait pas si elle le decrimente réellement
    public void decreasedScore() {

        Thread t = new Thread(() -> {

            while (scoreMes != 0) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scoreMes--;
            }
        });
        t.start();
    }


    public void increasedScore(int newScore) {
        this.scoreMes = scoreMes + newScore;

    }


    public String toString() {
        return "id_Message:" + this.idMessage + " " +
                "idUser :" + this.idUser + " " +
                "message: " + this.message + "  " +
                "user: " + this.user + "  " +
                "dateCréation" + this.dateMessage + "  " +
                "le nouveau score" + " " + this.getScoreMes();
    }


    // pour préparer le format du message a envoyer
    public String MessagetoSend() {
        return "id_Message:" + this.idMessage + " " + "|" +
                "idUser :" + this.idUser + " " + "|" +this.scoreMes + "    ";

    }


    @Override

    // la redifinition de cette methode permet de trier la liste des messages selon un  ordre décroissant ici
    public int compareTo(Message tmp) {
        return (this.scoreMes - tmp.scoreMes);
    }

}

