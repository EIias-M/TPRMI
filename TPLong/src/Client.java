import global.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Registry reg = LocateRegistry.getRegistry(2002);
        System.out.println(reg.lookup("Vote"));
        StubServer s= (StubServer) reg.lookup("Vote");
        ArrayList<Candidate> candidates =s.getCandidates();
        for(Candidate c:candidates){
            System.out.println("Candidat "+c.Rank+": "+c.firstName+" "+c.lastName+"\npitch:"+c.pitch+"\n");
        }
        ICandidat sv=new Stub_votant();
        VotingSystem v=s.getVoteMaterial(2230,sv);
        v.printOtp();
        boolean start=false;
        while(!start){
            System.out.println("Veuillez entrer votre OTP :");
            start=v.checkOTP();
        }
        System.out.println("Vous pouvez voter");
        v.vote();

    }
}