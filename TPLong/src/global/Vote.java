package global;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Scanner;

public class Vote implements VotingSystem, Serializable {

    private int realOTP;
    public Vote(){

    }

    public Vote(int realOTP){
        this.realOTP=realOTP;
    }

    @Override
    public void vote() {
        System.out.println();
    }

    public void printOtp(){
        System.out.println("Votre OTP est "+realOTP+". N'hésitez pas a le noter");
    }
    @Override
    public boolean checkOTP() throws Exception {
        Scanner sc=new Scanner(System.in);
        int mdp=sc.nextInt();
        if(mdp==realOTP){
            System.out.println("Authentification réussie");
            return true;
        }
        System.out.println("Mauvais OTP");
        return false;
    }
}
