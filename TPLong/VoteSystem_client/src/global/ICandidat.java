package global;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICandidat extends Remote {
    public String getPassword() throws RemoteException;
    public int getVoterSNumber() throws RemoteException;
}
