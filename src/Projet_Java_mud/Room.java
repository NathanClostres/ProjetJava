package Projet_Java_mud;

import java.io.Serializable;
import java.util.ArrayList;


public class Room implements Serializable{
    String NomR;
    Monster m;
    public ArrayList<Player> RoomPlayers = new ArrayList<>();
    public ArrayList<String> Msg = new ArrayList<>();

    public Room(String Nomr) {

        this.NomR = Nomr;
        if(Nomr.equals("0,0"))
        {m=new Monster(0);
            //System.out.println("ProjectPackage.Room.<init>() DANS LE IF");
        }
        else {m=new Monster();}
    }

    public String getNomR() {
        return NomR;
    }

    public void setNomR(String NomR) {
        this.NomR = NomR;
    }

    public int AddPlayer(Player P)
    {
        RoomPlayers.add(P);
        if(RoomPlayers.size()>1)
            return 1;
        else return 2;
    }
    public void DelPlayer (Player P)
    {
        for (int i=0; i<RoomPlayers.size(); i++)
        {
            if(RoomPlayers.get(i).getNomJ().equals(P.getNomJ()))
                RoomPlayers.remove(i);
        }
    }

    public synchronized String GiveAllMsg(String nomJ, int s)
    {
        String rtr="~New Messages~";
        int x=0;
        for (int i=s-1; i<Msg.size(); i++)
        {
            String[] parts = Msg.get(i).split(":");
            String part1 = parts[0]; // x
            //System.out.println("=>Room Giveallmsg Nomj: "+part1+"i:"+i);
            if(!part1.equals(nomJ))
            //if(1==1)
            {
                rtr= rtr +"\n"+Msg.get(i);
                x++;
            }
        }
        rtr=rtr+"\n"+"~End Of Messages~";
        if(x!=0){return rtr;}
        else return null;
    }
    public void WrtieMsg(String msg)
    {
        Msg.add(msg);
    }
}

