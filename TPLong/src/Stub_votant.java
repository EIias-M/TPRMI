import global.ICandidat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Stub_votant extends UnicastRemoteObject implements ICandidat {
    public Stub_votant() throws RemoteException {
        super();
    }

    @Override
    public String getPassword() {
        Scanner sc= new Scanner(System.in);
        System.out.println("Veuillez entrer votre mot de passe");
        String motDePasse = sc.nextLine();
        return motDePasse;
    }
}
