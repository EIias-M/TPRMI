package global;

import global.Execption.RankException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface VotingSystem extends Remote {
    public boolean checkOTP() throws Exception;

    List<VoteInformation> getVoteInfos();

    void manageVote(String OP, List<Candidate> candidates, int studentid,StubServer s) throws RankException, RemoteException;


    void vote(String OP, List<Candidate> candidates, int studentId,StubServer s) throws RankException, RemoteException;

    public void printOtp() throws Exception;



    String getOTP();




    Map<Integer, Integer> getVotes();

}