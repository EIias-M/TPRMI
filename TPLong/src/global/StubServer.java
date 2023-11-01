package global;

import java.rmi.Remote;
import java.util.ArrayList;

public interface StubServer extends Remote {

    public ArrayList<Candidate> getCandidates() throws Exception ;
    public Vote getVoteMaterial(int snumber, ICandidat stub_votant) throws Exception;


}
