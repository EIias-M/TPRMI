package global;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StubServer extends Remote {

    public ArrayList<Candidate> getCandidates() throws Exception ;
    public Vote getVoteMaterial(int snumber, ICandidat stub_votant) throws Exception;

    void submitVotes(Map<Integer, Integer> userVotes, List<VoteInformation> voteInfos) throws RemoteException;
    Map<Integer, Integer> calculateElectionResults(List<Candidate> candidates) throws  RemoteException;

    boolean isVotingSessionActive() throws RemoteException;

    void endVotingSession() throws RemoteException;

    public void startVotingSession() throws RemoteException ;
    List<Candidate> findWinner(Map<Integer, Integer> votes, List<Candidate> candidates) throws RemoteException;
}

