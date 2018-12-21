package Projet_Java_mud;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface MServerInt extends Remote{

    public String GetNewMsgs (String NomJ , Room R, int size) throws RemoteException;
    public int WriteMsg (String Msg, String NomJ, Room R) throws RemoteException;
}
