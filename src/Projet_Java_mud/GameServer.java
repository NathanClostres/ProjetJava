package Projet_Java_mud;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;


public class GameServer extends UnicastRemoteObject implements GServerInt {
    private static final long serialVersionUID = 2674880711467464646L;
    //private static Vector classes = new Vector();
    // Gestion des joueurs loggés
    public ArrayList<Player> Players = new ArrayList<>();
    public ArrayList<Dungeon> Dungs = new ArrayList<>();
    protected GameServer() throws RemoteException {

        super();

    }

    public static void main (String args[]) {
        try {
            Registry reg = LocateRegistry.createRegistry(1010);
            //Naming.rebind("rmi://127.0.0.1:1099/server", reg);
            GameServer GS = new GameServer();
            //MsgServer MS = new MsgServer(MS);
            reg.rebind("rmi://127.0.0.78:1010/server", GS);
            System.out.println("Server started ! ");
            GS.CreateDung();
            //GS.test();
        } catch (Exception e) {
            System.out.println(" Catch GS main  : "+ e);
        }
    }

    // Demande de connexion d'un joueur avec son pseudo
    // + Verif d'existance du pseudo
    public void test ()
    {
        Dungs.get(0).DMap[1][1].Msg.add("HELLO");
    }
    public int ConnectToServ(String nomJ) throws RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int rtr=0;
        rtr=Addplayer(nomJ);
        //System.out.println("le rtr !: "+rtr);
        if (rtr==1)
        {
            System.out.println("~Player "+nomJ +" is connected");
            Player tmp =new Player(nomJ);
            Players.add(tmp);
            return 1; // OK
        }
        else if (rtr==2) return 2; // NOK
        else
            return 0;
    }

    private int Addplayer(String nomJ) {
        int test=0;
        //System.out.println("Dans le Addplayer");
        if (!Players.isEmpty())
        {
            for (int i = 0; i < Players.size() ; i++) {
                System.out.println("ProjectPackage.GameServer.Addplayer()"+Players.get(i).getNomJ()+"nomj :"+nomJ);
                if (nomJ.equals(Players.get(i).getNomJ()))
                {
                    System.out.println("~Name already exists");
                    test=1;
                    return 2;

                }
            }
            if (test==0)
            {        System.out.println("New player is created : "+nomJ);
                Player tmp = new Player(nomJ);
                Players.add(tmp);
                return 1;
            }
        }
        else if (Players.isEmpty())
        {
            //System.out.println("Ajout du joueur : tableau empty");
            Player tmp = new Player(nomJ);
            Players.add(tmp);
            return 1;
        }

        return 0; // Erreur
    }

    /* IMPORTANT => Surrement à utiliser plus tard pour utiliser un ibjet joureur A MODIFIER OFC */
   /* public static Player getInstance(String attribut1, String attribut2) {

                System.out.println("ATTT 1 /" +attribut1 +" ATTT 2 / "+attribut2);
		Player tmp = new Room(attribut1, attribut2);
		if (classes.contains(tmp)) {
			// on doit retrouver l'element
			//Enumeration enume = classes.elements();
			while (enume.hasMoreElements()) {
				Room element = (Room) enume.nextElement();
				if (element.equals(tmp)) {
					return element;
				}
			}
		}
		else {
			classes.add(tmp);
			return tmp;
		}
                                // si on arrive là, c'est qu'il y à un problème
                                return null;
	}*/

    private void CreateDung () {
        int size;
        size= Dungs.size();

        Dungeon tmp =new Dungeon(Integer.toString(size+1));
        Dungs.add(tmp);
        System.out.println("#Creation of a new Dungeon Number : " +tmp.getNomD());
        tmp.InitDungMap();

    }
    @Override
    public String ShowDungs() throws RemoteException {
        String tmp="";
        String Newligne=System.getProperty("line.separator");
        for (int i = 0; i < Dungs.size(); i++) {
            if (Dungs.get(i).getNPlayers()!=4)
            {
                tmp = "--Dung "+Dungs.get(i).getNomD()+ " Number of players Wainting : "+Dungs.get(i).getNPlayers() +Newligne+ tmp;
            }
        }

        return tmp;
    }

    @Override
    public int ChooseDung(int NDung, String NomJ) throws RemoteException {
        //System.out.println("PLPLP : "+Dungs.get(NDung).getNPlayers());
        int np=Dungs.get(NDung-1).getNPlayers();
        //System.out.println("NDung Given : "+NDung );
        // System.out.println("ProjectPackage.GameServer.ChooseDung() Dungs size : "+Dungs.size() );
        if (NDung > Dungs.size())
        {return 2;}
        else if(np==4)
            return 2;
        else
        {
            for (int i = 0; i < Players.size(); i++) {
                if(Players.get(i).getNomJ().equals(NomJ)){
                    // System.out.println("Adding Player"+Players.get(i)+ " to Ndung number :"+(NDung));
                    Dungs.get(NDung-1).addPlayer(Players.get(i));
                    Dungs.get(NDung-1).DMap[0][0].AddPlayer(Players.get(i));
                    int j = Dungs.get(NDung-1).getNPlayers();
                    if (j==3)
                    {
                        String convert = Integer.toString(Dungs.size()+1);
                        System.out.println("Creating new Dung Number : "+convert);
                        Dungeon tmp = new Dungeon(convert);
                        Dungs.add(tmp);
                        return 1;
                    }
                    return 1;

                }
                //System.out.println("Pas sensé etre là !!");
            }
            return 1;

        }

    }

    @Override

    public String ShowPofDung(int NDung) throws RemoteException {
        return Dungs.get(NDung-1).showplayers();
    }

    @Override
    public int ExitDung(int Ndung, String NomJ) throws RemoteException {
        System.out.println("Dans le ExitDung");
        for (int i = 0; i < Dungs.size(); i++) {
            System.out.println("Sur dung : "+Dungs.get(i).getNomD());
            if(Dungs.get(i).getNomD().equals(Integer.toString(Ndung)))
            {
                for (int j = 0; j < Dungs.get(i).DungPlayers.size(); j++) {
                    System.out.println("NomJ du tab"+Dungs.get(i).DungPlayers.get(j).getNomJ());
                    if(Dungs.get(i).DungPlayers.get(j).getNomJ().equals(NomJ))
                    {
                        System.out.println("Second if nomj: "+Dungs.get(i).DungPlayers.get(j).getNomJ());
                        Dungs.get(i).DungPlayers.remove(j);
                        System.out.println("Suppression du joueur du Dung");
                        return 1;
                    }

                }
                Iterator<Player> itr = Dungs.get(i).DungPlayers.iterator();

                while(itr.hasNext()) {
                    Player p = itr.next();
                    if(p.getNomJ().equals(NomJ)){
                        itr.remove();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String GetDunMap(String NomJ, String NomD) {
        String rtr="";
        for (int i = 0; i < Dungs.size(); i++) {
            if(Dungs.get(i).getNomD().equals(NomD))
                rtr=Dungs.get(i).printRow(Dungs.get(i).DMap);
            return rtr;
        }
        return rtr;
    }

    @Override
    public int IsDungComplete(String NomD) throws RemoteException {
        int rtr;
        for (int i = 0; i < Dungs.size(); i++) {
            if (Dungs.get(i).getNomD().equals(NomD))
            {
                rtr=Dungs.get(i).DungPlayers.size();

                if(rtr==4)
                {
                    return 1; // OK
                }
                else return 2; // NOK Wait..
            }
        }
        return 0; // We should neever get here.
    }

    @Override
    public synchronized String WhereIam(String NomJ) {
        String Position="ErRor";
        for (int i = 0; i < Dungs.size(); i++) {
            for (int j = 0; j < Dungs.get(i).DungPlayers.size(); j++) {
                if (Dungs.get(i).DungPlayers.get(j).getNomJ().equals(NomJ))
                {
                    //System.out.println("1er if "+i+","+j);
                    for (int x = 0; x <Dungs.get(i).getX() ; x++)
                    {//System.out.println("x: "+x);
                        for (int y = 0; y <Dungs.get(i).getY() ; y++)
                        {//System.out.println("y: "+y);

                            for (int h=0; h<Dungs.get(i).DMap[x][y].RoomPlayers.size();h++)
                            {
                                if(Dungs.get(i).DMap[x][y].RoomPlayers.get(h).getNomJ().equals(NomJ))
                                {
                                    Position=(x+","+y);
                                    //System.out.println("WhereIam() POSITION :"+Position);
                                    return Position;
                                }
                            }
                        }
                    }
                }
            }
        }

        return Position;
    }

    @Override
    public int MaxValDungMap(String NomD) throws RemoteException {
        for (int i = 0; i < Dungs.size(); i++) {
            if(Dungs.get(i).NomD.equals(NomD))
            {
                //System.out.println("MAX :" +Dungs.get(i).getX());
                return Dungs.get(i).getX();
            }

        }
        return 0; // ERROR
    }

    public int SwitchRoom(String NomD, String NomJ, int x, int y) {
        System.out.println("##SwitchRoom de "+NomJ+ " Destination "+x+","+y);

        for (int i = 0; i < Dungs.size(); i++) {
            //System.out.println("NomD Geti "+Dungs.get(i).NomD);
            if(Dungs.get(i).NomD.equals(NomD))
                for (int j = 0; j < Players.size(); j++) {
                    // System.out.println("NomJ geti "+Players.get(j).getNomJ()+" NomJGiven "+NomJ);
                    if(Players.get(j).getNomJ().equals(NomJ))
                    {

                        for (int h = 0; h < Dungs.get(i).getX(); h++) {
                            for (int k = 0; k < Dungs.get(i).getX(); k++) {
                                for (int o=0;o<Dungs.get(i).DMap[h][k].RoomPlayers.size();o++){
                                    if(Dungs.get(i).DMap[h][k].RoomPlayers.get(o).getNomJ().equals(NomJ))
                                    {
                                        //System.out.println("NomJ a changer de room : "+Dungs.get(i).DMap[h][k].RoomPlayers.get(o).getNomJ()+" Position :" +h+" , "+k);

                                        Dungs.get(i).DMap[h][k].RoomPlayers.remove(o);
                                        // System.out.println("PLayer removed from room "+Dungs.get(i).DMap[h][k].getNomR());
                                        int p = Dungs.get(i).DMap[x][y].AddPlayer(Players.get(j));
                                        //System.out.println("PLayer added to room "+Dungs.get(i).DMap[x][y].getNomR());
                                        return p;
                                    }
                                }

                            }
                        }

                    }


                }
        }
        return 0; // ERROR
    }

    @Override
    public int IsRoomEmpty(int x, int y, String NomD) throws RemoteException {

        for (int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                if(Dungs.get(i).DMap[x][y].RoomPlayers.size()>1)
                    return 1;
                else
                    return 0;
            }
        }
        return 0;
    }


    public synchronized Room GetRoom(String NomR, String NomD) throws RemoteException {
        String[] parts = NomR.split(",");
        //System.out.println("1 GET ROOM : "+NomR);
        String part1 = parts[0]; // x
        String part2 = parts[1]; //y
        int x = Integer.parseInt(part1);
        int y = Integer.parseInt(part2);
        for(int i=0; i<Dungs.size(); i++)
        {
            // System.out.println("2 GetROOM : FOR");
            if(Dungs.get(i).NomD.equals(NomD))
            {   //System.out.println("3 GetROOM : IF RETURN : x&y"+x+y);
                return Dungs.get(i).DMap[x][y];
            }
        }
        System.out.println("###########GETROOM ON NE DOIT PAS ETRE LA");
        return Dungs.get(0).DMap[x][y];
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  int WriteMsg(String Msg, String NomJ, int x,int y, String NomD) throws RemoteException {
        for(int i=0; i<Dungs.size(); i++)
        {
            if(Dungs.get(i).NomD.equals(NomD))
            {
                String msg=NomJ+":"+Msg;
                System.out.println("#Ajout du message :--"+msg+" -- Dnas la room : "+Dungs.get(i).DMap[x][y].getNomR());
                // System.out.println("Le size avant est : "+Dungs.get(i).DMap[x][y].Msg.size());
                Dungs.get(i).DMap[x][y].Msg.add(msg);
                // System.out.println("Le size mntnt est : "+Dungs.get(i).DMap[x][y].Msg.size());
                return Dungs.get(i).DMap[x][y].Msg.size();
            }
        }
        System.out.println("###WRTIE MSG : ERROR");
        return 0;

    }

    public  String GetNewMsgs(String nomJ,String NomD, int x, int y, int size) throws RemoteException {
        String rtr=null;

        System.out.println("#GetNEWmsgs pour le joueur : "+nomJ);
        if(1==1)
        {
            for(int i=0;i<Dungs.size();i++)
            {
                if(Dungs.get(i).getNomD().equals(NomD))
                {
                    rtr=Dungs.get(i).DMap[x][y].GiveAllMsg(nomJ, size);
                    //System.out.println("Getnewmsg pr la room : "+Dungs.get(i).DMap[x][y].getNomR()+"rtr : "+rtr+"Pour le joueur :"+nomJ );
                }
            }


            return rtr;
        }
        else
            return rtr;
    }

    public int Getsize(String NomD, int x, int y) {

        for(int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                return Dungs.get(i).DMap[x][y].Msg.size();
            }

        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int IsMonsterAlive(String NomD, int x, int y) throws RemoteException {
        for(int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                return Dungs.get(i).DMap[x][y].m.getPv();
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void PlayerAttacked(String NomJ, int damage) throws RemoteException {
        for(int i=0; i<Players.size();i++)
        {
            if(Players.get(i).getNomJ().equals(NomJ))
            { Players.get(i).setPvie( (Players.get(i).getPvie())-damage); }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public int MonsterAttacked(String NomD, int x, int y) throws RemoteException {
        for(int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                int pv=Dungs.get(i).DMap[x][y].m.getPv();
                System.out.println("#PV DU MONSTRE : "+pv+" De la room :"+Dungs.get(i).DMap[x][y].getNomR());
                pv=pv-1;
                if(pv<0) {pv=0;}
                Dungs.get(i).DMap[x][y].m.setPv(pv);
                return Dungs.get(i).DMap[x][y].m.getPv();
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int MyLife(String NomJ) throws RemoteException {
        for(int i=0; i<Players.size();i++)
        {
            if(Players.get(i).getNomJ().equals(NomJ))
                return Players.get(i).getPvie();
        }

        throw new UnsupportedOperationException("Mylife() => Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetMonsterPv(String NomD, int x, int y) throws RemoteException {
        for(int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                return Dungs.get(i).DMap[x][y].m.getPv();
            }

        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeletePlayer(String NomJ, String NomD, int x, int y) throws RemoteException {

        for(int i=0; i<Dungs.size();i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            { System.out.println("if1");
                for(int j=0; j<Dungs.get(i).DungPlayers.size();j++)
                {
                    if(Dungs.get(i).DungPlayers.get(j).getNomJ().equals(NomJ))
                    {System.out.println("if2");
                        Dungs.get(i).DungPlayers.remove(j);
                    }
                }
                for (int k=0; k<Dungs.get(i).DMap[x][y].RoomPlayers.size(); k++)
                {
                    if(Dungs.get(i).DMap[x][y].RoomPlayers.get(k).getNomJ().equals(NomJ))
                    {System.out.println("if3");
                        Dungs.get(i).DMap[x][y].RoomPlayers.remove(k);
                    }
                }
            }
/*        for (int h=0; h<Players.size();h++)
        {
            if(Players.get(h).getNomJ().equals(NomJ))
            {System.out.println("if4");
                Players.remove(h);

            }
        }*/
            Iterator<Player> itr = Players.iterator();

            while(itr.hasNext()) {
                Player p = itr.next();

                if(p.getNomJ().equals(NomJ)){
                    itr.remove();
                }
            }
        }
        System.out.println("#Player "+NomJ+" Deleted From the server !");
    }

    @Override
    public String WhoIsInMyRoom(String NomJ, String NomD, int x, int y) throws RemoteException {
        String rtr="";
        String hash="";
        for (int i=0; i<Dungs.size(); i++)
        {
            if(Dungs.get(i).getNomD().equals(NomD))
            {
                for (int j=0; j<Dungs.get(i).DMap[x][y].RoomPlayers.size(); j++)
                {
                    hash="Palyer :"+Dungs.get(i).DMap[x][y].RoomPlayers.get(j).getNomJ()+" With : "+Dungs.get(i).DMap[x][y].RoomPlayers.get(i).getPvie()+" PV";
                    rtr=hash+"\n"+rtr;
                }
            }
        }
        return rtr;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void GiveLife(int whois, String NomJ, String NomD, int x, int y) throws RemoteException {

        if(whois==1) // PLAYER
        {
            for(int i=0; i<Players.size(); i++)
            {
                if(Players.get(i).getNomJ().equals(NomJ))
                {
                    Players.get(i).setPvie((Players.get(i).getPvie())+2);
                }
            }

        }
        else if(whois==2) // MONSTRE
        {
            for(int j=0; j<Dungs.size();j++)
            {
                for(int g=0; g<Dungs.size();g++)
                {
                    if(Dungs.get(j).DMap[x][y].RoomPlayers.get(g).getNomJ().equals(NomJ))
                    {
                        Dungs.get(j).DMap[x][y].m.setPv((Dungs.get(j).DMap[x][y].m.getPv())+2);
                    }

                }
            }

        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SimpleDeletePlayer(String NomJ) throws RemoteException {
        Iterator<Player> itr = Players.iterator();

        while(itr.hasNext()) {
            Player p = itr.next();

            if(p.getNomJ().equals(NomJ)){
                itr.remove();
            }

            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        System.out.println("#Simple Delete of : "+NomJ);
    }
}


