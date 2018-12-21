package Projet_Java_mud;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;


public class Dungeon {

    public static final int X = 5;
    public static final int Y = X;

    public  int getX() {
        return X;
    }

    public  int getY() {
        return Y;
    }
    public String NomD;
    public ArrayList<Player> DungPlayers = new ArrayList<>();
    public ArrayList<String> Chat = new ArrayList<>();
    public Room[][] DMap= new  Room[X][Y];
    //Room DRooms[5][5];
    public void InitDungMap()
    {
        String Rname;
        System.out.println("#Trying to create the map");
        for (int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                Rname=Integer.toString(i);
                Rname=Rname+","+Integer.toString(j);
                //System.out.println("Rname : "+Rname);
                DMap[i][j]= new Room(Rname);
                if (i==0 && j==0)
                {
                    //System.out.println("ProjectPackage.Dungeon.InitDungMap() DANS LE IF DE DUNG");
                    DMap[i][j].m.setPv(0);
                    //System.out.println("ProjectPackage.Dungeon.InitDungMap() DANS LE IF DE DUNG : "+DMap[i][j].m.getPv());
                }
            }
        }
        System.out.println("#Creation of the Map OK for the Dung");
        // System.out.println(Arrays.deepToString(DMap));
        /* for(Room[] row : DMap) {
            printRow(row);
        }*/


    }

    public  String printRow(Room[][] map) {
        int j=0;
        String Newligne=System.getProperty("line.separator");
        String rtr ="";
        for (Room[] row : map)
        {
            for (Room i : row) {
                System.out.print("\t");
                rtr=rtr+"\t";
                System.out.print("[");
                rtr=rtr+"[";
                System.out.print(i.getNomR());
                rtr=rtr+i.getNomR();
                System.out.print("]");
                rtr=rtr+"]";
                System.out.print("\t");
                rtr=rtr+"\t";
                if(j==4||j==9||j==14||j==19||j==24)
                {
                    System.out.println("");
                    rtr=rtr+Newligne;
                }
                j++;
            }
        }
        System.out.println();
        return rtr;
    }

    public Dungeon(String NomD) {
        this.NomD = NomD;
    }

    public String getNomD() {
        return NomD;
    }

    public void setNomD(String NomD) {
        this.NomD = NomD;
    }
    public int getNPlayers(){
        // System.out.println("size of dungplayers : "+DungPlayers.size() );
        return DungPlayers.size();
    }
    public void addPlayer(Player p) {
        DungPlayers.add(p);
    }
    public String showplayers()
    {
        String Newligne=System.getProperty("line.separator");
        String tmp = "";
        for (int i = 0; i < DungPlayers.size(); i++) {
            tmp = "~Player~ " +DungPlayers.get(i).getNomJ()+" With : "+DungPlayers.get(i).getPvie()+" PV " + Newligne + tmp;
        }
        return tmp;
    }
    public  int DungWriteMsg(String Msg, String NomJ, Room R) {
        String msg=NomJ+":"+Msg;
        System.out.println("Ajout du message :--"+msg+" -- Dnas la room : "+R.NomR);
        System.out.println("Le size avant est : "+R.Msg.size());
        R.Msg.add(msg);
        System.out.println("Le size mntnt est : "+R.Msg.size());
        return R.Msg.size();
    }
}
