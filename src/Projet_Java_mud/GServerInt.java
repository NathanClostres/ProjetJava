package Projet_Java_mud;

import java.rmi.*;


public interface GServerInt extends Remote{
    public int ConnectToServ (String nomJ) throws RemoteException;
    public String ShowDungs () throws RemoteException;
    public int ChooseDung (int NDung, String NomJ) throws RemoteException;
    public String ShowPofDung (int NDung) throws RemoteException;
    public int ExitDung (int Ndung, String NomJ) throws RemoteException;
    public String GetDunMap (String NomJ, String NomD) throws RemoteException;
    public int IsDungComplete (String NomD) throws RemoteException;
    public String WhereIam (String NomJ) throws RemoteException;
    public int MaxValDungMap (String NomD) throws RemoteException;
    public int SwitchRoom (String NomD, String NomJ, int x, int y) throws RemoteException;
    public int IsRoomEmpty(int x, int y, String NomD) throws RemoteException;
    public Room GetRoom (String NomR, String NomD) throws RemoteException;
    public  int WriteMsg(String Msg, String NomJ, int x,int y, String NomD) throws RemoteException;
    public  String GetNewMsgs(String nomJ,String NomD, int x, int y, int size) throws RemoteException;
    public int Getsize(String NomD, int x, int y) throws RemoteException;
    public int IsMonsterAlive(String NomD, int x, int y) throws RemoteException;
    public void PlayerAttacked(String NomJ, int damage) throws RemoteException;
    public int MonsterAttacked(String NomD, int x, int y) throws RemoteException;
    public int GetMonsterPv(String NomD, int x, int y) throws RemoteException;
    public int MyLife(String NomJ) throws RemoteException;
    public void DeletePlayer(String NomJ, String NomD, int x, int y) throws RemoteException;
    public String WhoIsInMyRoom(String NomJ, String NomD, int x, int y) throws RemoteException;
    public void GiveLife(int whois, String NomJ,String NomD, int x, int y)throws RemoteException;
    public void SimpleDeletePlayer(String NomJ) throws RemoteException;
}

