package global;

import java.io.Serializable;

public class Candidate extends User implements Serializable {
    public  int Rank;
    public static int nbRank=1;
    public int nbVote;

    public String getPitch() {
        return pitch;
    }

    public String pitch;

    public String lastName;
    public String firstName;

    public Candidate(){
        super();
    }
    public Candidate(String pitch,  String nom, String prenom,int snumber,String passw) {
        super(snumber,passw);
        this.pitch=pitch;
        this.nbVote=0;
        this.lastName=prenom;
        this.firstName=nom;
        this.Rank=nbRank;
        nbRank++;

    }
    public int getNbVote() {
        return nbVote;
    }
    public void incrementVote(){
        this.nbVote++;
    }
}
