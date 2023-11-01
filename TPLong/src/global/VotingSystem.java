package global;

import java.rmi.Remote;
import java.util.Dictionary;
import java.util.HashMap;

public interface VotingSystem extends Remote {
    public void vote() throws Exception;
    public boolean checkOTP() throws Exception;
    public void printOtp() throws Exception;


}