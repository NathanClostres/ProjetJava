package Projet_Java_mud;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MsgServer extends UnicastRemoteObject implements MServerInt {


    protected MsgServer() throws RemoteException {
        super();
    }


    public static void main (String args[]) throws NotBoundException {
        GServerInt rmi;

        try {
            Registry reg = LocateRegistry.createRegistry(2020);
            MsgServer MS = new MsgServer();

            reg.rebind("rmi://127.0.0.79:2020/server", MS);
            System.out.println("MSG Server started ! ");
        } catch (Exception e) {
            System.out.println(" Catch MS main  : "+ e);
        }
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.78", 1010);
            rmi = (GServerInt) reg.lookup("rmi://127.0.0.78:1010/server");
        }
        catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized String GetNewMsgs(String nomJ, Room R, int size) throws RemoteException {
        String rtr=null;

        System.out.println("GetNEWmsgs");
        if(1==1)
        {
            rtr=R.GiveAllMsg(nomJ, size);
            System.out.println("Getnewmsg pr la room : "+R.getNomR()+"rtr : "+rtr+"Pour le joueur :"+nomJ );
            return rtr;
        }
        else


            return rtr;
    }

    //@Override
    public synchronized int WriteMsg(String Msg, String NomJ, Room R) throws RemoteException {
        String msg=NomJ+":"+Msg;
        System.out.println("Ajout du message :--"+msg+" -- Dnas la room : "+R.NomR);

        R.Msg.add(msg);
        System.out.println("Le size mntnt est : "+R.Msg.size());
        return R.Msg.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
