package service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commentaire  {
    private ZonedDateTime dateCommentaire;
    private int idCommentaire;
    private int idUser;
    private String commentaire;
    private String user;
    private int pidCommentaire;
    private int pidMessage;
    private int scoreCom=20;
    private List<MyEventListener> eventListeners;
    private List<EventListener> event;

    public Commentaire(int idCommentaire, int idUser, String commentaire, String user, int pidCommentaire, int pidMessage){
        this.idCommentaire=idCommentaire;
        this. idUser= idUser;
        this.commentaire=commentaire;
        this.user=user;
        this. pidCommentaire=pidCommentaire;
        this.pidMessage=pidMessage;
        this.scoreCom=scoreCom;
        this. dateCommentaire=ZonedDateTime.now();
        this.eventListeners = new ArrayList<MyEventListener>();
        this.event=new ArrayList<EventListener>();
    }

    public ZonedDateTime getDateCommentaire(){
        return this.dateCommentaire;
    }
    public int getIdCommentaire(){
        return this.idCommentaire;
    }
    public String getCommentaire(){
        return this.commentaire;
    }
    public String getUser(){
        return this.user;
    }
    public int  getPidCommentaire(){
        return this.pidCommentaire;
    }

    public int  getPidMessage(){
        return this.pidMessage;
    }
    public int getScoreCom(){
        return this.scoreCom;
    }
    public void addMyEventListener(MyEventListener evtListener)
    {
        this.eventListeners.add(evtListener);
    }
    public void addEventListener(EventListener ev)
    {
        this.event.add(ev);
    }



    public void decreasedScore(){
        Thread t = new Thread(() -> {

            while (scoreCom != 0) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scoreCom--;
                eventListeners.forEach((el) -> el.onMyEvent());
            }
        });
        t.start();

    }

    public void increasedScore(int newScore){
        this.scoreCom=scoreCom+newScore;
        event.forEach((e2) -> e2.actionTwo());

    }

    public String toString() {
        return "id_commentaire:"+ this.idCommentaire+" "+
                "idUser :"+ this.idUser+" "+
                "commentaire: " + this.commentaire+" "+
                "commentaire: " + this.user+" "+
                "pidCommentaire: " + this.pidCommentaire+" "+
                "pidMessagee: " + this.pidMessage+" "+
                "DateCréation: "+this.dateCommentaire+
                "le score : "    +this.scoreCom;
    }


}


