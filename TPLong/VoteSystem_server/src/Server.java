
import global.Candidate;
import global.StubServer;
import global.User;

import javax.lang.model.type.NullType;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class Server{
    public Dictionary<Integer, Integer> Votes;
    public static ArrayList<Candidate> candidates=new ArrayList<>();
    public static HashMap<Integer,String> users=new HashMap<Integer,String>();

    public static void retrieveCandidates(ObjectInputStream ois) throws Exception {
        while (true) {
            try {
                Candidate candidat = (Candidate) ois.readObject();
                candidates.add(candidat);
            } catch (EOFException e) {
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FileInputStream fichierEntree = new FileInputStream("C:\\Users\\elias\\OneDrive\\Bureau\\Tp_vote_2_2\\Tp_vote_2\\Tp_vote\\Tp vote\\TPLong (1)\\TPLong 2\\src\\candidat.txt");
        ObjectInputStream ois = new ObjectInputStream(fichierEntree);
        User voteur1 =new User(223,"v1");
        User Vero =new User(224,"v2");

        users.put(voteur1.StudentNumber,voteur1.password);
        users.put(Vero.StudentNumber,Vero.password);
        retrieveCandidates(ois);
        for(Candidate c:candidates){
            users.put(c.StudentNumber,c.password);
        }
        Registry reg = LocateRegistry.createRegistry(2002);
        StubServer s=new StubS(candidates,users);

        reg.rebind("Vote", s);
        s.startVotingSession();

    }
}
