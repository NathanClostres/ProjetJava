package Projet_Java_mud;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class MsgGetThread extends Thread
{
    GServerInt rmi;
    Client CL;
    public int size;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
    public MsgGetThread (Client C, GServerInt r)
    {
        rmi = r;
        CL = C;
    }
    @Override
    public void run() {
        //System.out.println("DANS LE RUN");
        MServerInt rmiMSG;
        String rtr=null;
        setSize(0);
        try {
            // Registry regMsg = LocateRegistry.getRegistry("127.0.0.79", 2020);

            //LocateRegistry.createRegistry(1099);
            // rmiMSG = (MServerInt) regMsg.lookup("rmi://127.0.0.79:2020/server");
            //System.out.println("THREAD"+this.rmi.GetRoom(this.rmi.WhereIam(this.CL.getName()), Integer.toString(this.CL.getMyDung())).GiveAllMsg());

            // System.out.println("Le size avant le while : "+getSize());
            String wiam= rmi.WhereIam(CL.getName());
            String[] parts = wiam.split(",");
            //System.out.println("1 GET ROOM : "+NomR);
            String part1 = parts[0]; // x
            String part2 = parts[1]; //y
            int x = Integer.parseInt(part1);
            int y = Integer.parseInt(part2);
            setSize(rmi.Getsize(Integer.toString(CL.getMyDung()), x, y));
            // System.out.println("SIZE1 : "+getSize());
            while(1==1){
                rtr=null;
                if(getSize()<rmi.Getsize(Integer.toString(CL.getMyDung()), x, y)){
                    wiam= rmi.WhereIam(CL.getName());
                    parts = wiam.split(",");
                    //System.out.println("1 GET ROOM : "+NomR);
                    part1 = parts[0]; // x
                    part2 = parts[1]; //y
                    x = Integer.parseInt(part1);
                    y = Integer.parseInt(part2);
                    setSize(rmi.Getsize(Integer.toString(CL.getMyDung()), x, y));
                    //System.out.println("SIZE2 : "+getSize());
                    rtr=rmi.GetNewMsgs(CL.getName(),Integer.toString(CL.getMyDung()), x,y, getSize());
                }
                if(rtr!=null) System.out.println(rtr);
            }

            //System.out.println("Taille : " +this.rmi.GetRoom(this.rmi.WhereIam(this.CL.getName()),Integer.toString(this.CL.getMyDung())).Msg.size());
        } catch (RemoteException ex) {
            Logger.getLogger(MsgGetThread.class.getName()).log(Level.SEVERE, null, ex);
        }




    }


}
public class Client {
    private String name;
    private int RefChat;
    private int MyDung;

    public void sendmsg(Client CL, String msg, String NomJ, GServerInt rmi) throws RemoteException, NotBoundException
    {
      /*MServerInt rmiMSG;
      Registry regMsg = LocateRegistry.getRegistry("127.0.0.79", 2020);
      rmi = (MServerInt) regMsg.lookup("rmi://127.0.0.79:2020/server");*/

        String wiam=rmi.WhereIam(NomJ);
        String[] parts = wiam.split(",");
        //System.out.println("1 GET ROOM : "+NomR);
        String part1 = parts[0]; // x
        String part2 = parts[1]; //y
        int x = Integer.parseInt(part1);
        int y = Integer.parseInt(part2);
        rmi.WriteMsg(msg, NomJ, x,y,Integer.toString(CL.getMyDung()));

    }
    public int getMyDung() {
        return MyDung;
    }
    static String Newligne=System.getProperty("line.separator");
    public void setName(String name) {
        //System.out.println("ProjectPackage.Client.setName() OK");
        this.name = name;
    }

    public String getName() {
        return name;
    }
    private int ConnectToServer(Client CL, GServerInt rmi) throws RemoteException {

        int rtr=2; // retour des fonctions interface

        //Naming.rebind("rmi://127.0.0.1:1099/server", reg);
        System.out.println("~WELCOME TO THE MUD !");
        System.out.println("~Please Enter your Nickname :");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (!str.isEmpty())
        {
            //System.out.println("Connected to Server");
            rtr= rmi.ConnectToServ(str);
            if (rtr==1)
            {
                System.out.println("~"+str+ " You're connected !");
                CL.setName(str);
                return 1;
            }
            while (rtr==2) {
                rtr= rmi.ConnectToServ(str);
                if (rtr==1)
                {
                    System.out.println("~"+str+ " You're connected !");
                    CL.setName(str);
                    System.out.println("after the set name");
                    return 1;
                    //System.out.println("after the set name");
                }
                else if (rtr==2)
                {
                    System.out.println("#ERROR#~"+str+ " Already exists Please choose an other nickname !");
                    str = sc.nextLine();
                }
                else
                {
                    System.out.println("##~PAS COOL "+rtr+ " " +str);
                }
            }
        }


        return 0;
    }
    public void ShowOrders ()
    {
        System.out.println("~ORDERS~ To display Dungeon's Please press 1 :");
        System.out.println("~ORDERS~ To choose a Dungeon's Please press 2 :");
        System.out.println("~ORDERS~ To see players of a precise Dungeon press 3 :");
        System.out.println("~ORDERS~ To exit the game press 99 :");
    }
    public  int ShowDungOrders (GServerInt rmi, Client CL, MsgGetThread t1 ) throws RemoteException {
        String MyPosition = rmi.WhereIam(CL.getName());
        String[] parts = MyPosition.split(",");
        String part1 = parts[0]; // x
        String part2 = parts[1]; //y
        int x = Integer.parseInt(part1);
        int y = Integer.parseInt(part2);
        int r=rmi.IsRoomEmpty(x, y, Integer.toString(CL.getMyDung()));
        int m=rmi.IsMonsterAlive(Integer.toString(CL.getMyDung()), x, y);
        if(r==1)
        {
            System.out.println("~CAUTION~ You're not alone in this room ! ");
            System.out.println("~Order~ To see all players of the room press 6: ");

        }
        if(m!=0) // Battle ou fuite
        {
            int pvM=rmi.IsMonsterAlive(Integer.toString(CL.getMyDung()), x, y);
            System.out.println("~CAUTION~ The monster is alive with "+pvM+" ! what you want to do ? ");
            int rtr =CL.MonsterIsHere(rmi, CL);
            if(rtr==0) return 0;
        }
        System.out.println("~ORDERS~ To display the Dungeon's Map Please press 1 :");
        System.out.println("~ORDERS~ To see the stat of the game Please press 2 :");
        System.out.println("~ORDERS~ To see Where I am press 3 :");
        System.out.println("~ORDERS~ To moove to other room press 4 :");
        if(r==1)
        {System.out.println("~ORDERS~ To Send message to the room press 5:");}

        /* System.out.println("~ORDERS~ To see players of a precise Dungeon press 3 :");*/
        System.out.println("~ORDERS~ To exit the Dung please press 98 :");
        return 1;
    }
    public void MyMooves (GServerInt rmi, Client CL) throws RemoteException{

        String MyPosition = rmi.WhereIam(CL.getName());
        String[] parts = MyPosition.split(",");
        int i=1;
        Scanner sc = new Scanner(System.in);
        int choice;
        int retour=5;
        String part1 = parts[0]; // x
        String part2 = parts[1]; // y
        int x = Integer.parseInt(part1);
        int y = Integer.parseInt(part2);
        int max = rmi.MaxValDungMap(Integer.toString(CL.MyDung));
        if((x-1)>=0) { // (x-1),y
            i=1;
            System.out.println("Press "+i+" to go to the '"+(x-1)+"','"+y+"' Room");
        }
        if ((y-1)>=0) // (y-1),x
        {
            i=2;
            System.out.println("Press "+i+" to go to the '"+x+"','"+(y-1)+"' Room");

        }
        if ((x+1<max)) // (x+1),y
        {
            i=3;
            System.out.println("Press "+i+" to go to the '"+(x+1)+"','"+y+"' Room");

        }
        if ((y+1<max)) // (x),(y+1)
        {
            i=4;
            System.out.println("Press "+i+" to go to the '"+x+"','"+(y+1)+"' Room");
            i++;
        }

        choice = sc.nextInt();
        if(choice==1)
        {
            retour=rmi.SwitchRoom(Integer.toString(CL.MyDung), CL.getName(), (x-1), y);
            // System.out.println("Retour : "+ retour);
            if(retour==1)
            {
                System.out.println("D'autres joueurs sont dans la Room !");
                System.out.println("~ORDERS~ To Send message to the room press 5:");
                System.out.println("~Order~ To see all players of the room press 6: ");
            }
        }
        else if(choice==2)
        {
            retour=rmi.SwitchRoom(Integer.toString(CL.MyDung), CL.getName(), x, (y-1));
            System.out.println("Retour : "+ retour);
            if(retour==1)
            {
                System.out.println("D'autres joueurs sont dans la Room !");
                System.out.println("~ORDERS~ To Send message to the room press 5:");
                System.out.println("~Order~ To see all players of the room press 6: ");
            }
        }
        else if(choice==3)
        {
            retour=rmi.SwitchRoom(Integer.toString(CL.MyDung), CL.getName(), (x+1), y);
            //System.out.println("Retour : "+ retour);
            if(retour==1)
            {
                System.out.println("D'autres joueurs sont dans la Room !");
                System.out.println("~ORDERS~ To Send message to the room press 5:");
                System.out.println("~Order~ To see all players of the room press 6: ");
            }
        }
        else if(choice==4)
        {
            retour=rmi.SwitchRoom(Integer.toString(CL.MyDung), CL.getName(), x, (y+1));
            //System.out.println("Retour : "+ retour);
            if(retour==1)
            {
                System.out.println("D'autres joueurs sont dans la Room !");
                System.out.println("~ORDERS~ To Send message to the room press 5:");
                System.out.println("~Order~ To see all players of the room press 6: ");
            }
        }


    }
    public static void main(String[] args) throws NotBoundException {
        GServerInt rmi;

        Scanner sc = new Scanner(System.in);
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.78", 1010);

            //LocateRegistry.createRegistry(1099);
            rmi = (GServerInt) reg.lookup("rmi://127.0.0.78:1010/server");

            int choice=0;
            int rtr=0;
            int selection=0;
            Client CL= new Client();
            MsgGetThread t1 = new MsgGetThread(CL,rmi);
            rtr=CL.ConnectToServer(CL, rmi);
            while (choice!=99)
            {
                System.out.println("~ORDERS~ To display Orders please press 8 :");
                choice = sc.nextInt();
                if(choice==1)
                {
                    System.out.println("~~~ All Dung's ~~~"+Newligne+rmi.ShowDungs());
                }
                if(choice==2)
                {
                    System.out.println("~ORDERS~ Please give the Dungeon number:");
                    selection = sc.nextInt();
                    rtr=rmi.ChooseDung(selection, CL.getName());
                    if (rtr==1)
                    {
                        System.out.println("~OK~ You are on Dungeon number : "+selection);
                        t1.start();
                        CL.MyDung=selection;

                        //.out.println("mydun :" +CL.MyDung);
                        while (choice != 8)
                        {
                            int back =CL.ShowDungOrders(rmi, CL, t1);
                            if (back==0)
                            {
                                System.out.println("~INFO~ Partie terminé Merci d'avoir joué ! ");
                                String wiam= rmi.WhereIam(CL.getName());
                                String[] parts = wiam.split(",");
                                String part1 = parts[0]; // x
                                String part2 = parts[1]; //y
                                int x = Integer.parseInt(part1);
                                int y = Integer.parseInt(part2);
                                rmi.DeletePlayer(CL.getName(), Integer.toString(CL.getMyDung()), x, y);
                                System.exit(0);

                            }
                            choice = sc.nextInt();
                            if(choice ==98)
                            {
                                //System.out.println("in the if 98");
                                //t1 = null;
                                rtr=0;
                                rtr=rmi.ExitDung(CL.MyDung, CL.getName());
                                //System.out.println("after the rmi. rtr =" +rtr);
                                if (rtr==1) {
                                    System.out.println("~OK~ Back to Principal menu...");
                                    choice=8;
                                }
                            }
                            if (choice==1)
                            {
                                String print;
                                print = rmi.GetDunMap(CL.getName(), Integer.toString(CL.MyDung));
                                System.out.println(print);
                            }
                            if (choice==2)
                            {
                                int i;
                                System.out.println(rmi.ShowPofDung(CL.MyDung));
                                i=rmi.IsDungComplete(Integer.toString(CL.MyDung));
                            /*if (i==2)
                                System.out.println("~OK~ Wainting for players");
                            else
                                System.out.println("~OK~ Game is ready");*/
                            }
                            if (choice==3)
                            {
                                System.out.println("~Info~ You are in the room : "+rmi.WhereIam(CL.getName()));
                            }
                            if (choice==4)
                            {
                                CL.MyMooves(rmi, CL);
                            }
                            if (choice==5)
                            {
                                System.out.println("=> Write your message :");
                                Scanner scan = new Scanner(System.in);
                                String str = scan.nextLine();
                                CL.sendmsg(CL, str, CL.getName(), rmi);



                            }
                            if (choice==6)
                            {
                                String print;
                                String wiam= rmi.WhereIam(CL.getName());
                                String[] parts = wiam.split(",");
                                String part1 = parts[0]; // x
                                String part2 = parts[1]; //y
                                int x = Integer.parseInt(part1);
                                int y = Integer.parseInt(part2);
                                print=rmi.WhoIsInMyRoom(CL.getName(), Integer.toString(CL.getMyDung()), x, y);
                                if(!print.equals(""))
                                {
                                    System.out.println(print);
                                }
                            }
                        }
                    }
                    else if(rtr==2)
                    {
                        System.out.println("~ERROR~ Too much players, please choose another Dung");
                    }
                }
                if(choice==3)
                {
                    System.out.println("~ORDERS~ Please give the Dungeon number:");
                    rtr = sc.nextInt();
                    System.out.println("~" +rmi.ShowPofDung(rtr));
                }
                if (choice==8)
                {
                    CL.ShowOrders();
                }
            }
            //rmi.DeletePlayer(CL.getName(), CL.getMyDung(), rtr, rtr);
            rmi.SimpleDeletePlayer(CL.getName());
            System.out.println("Good bye..");

        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int MonsterIsHere(GServerInt rmi, Client CL) throws RemoteException
    {
        System.out.println("~Order~ If you wan't to escape you'll loose 2 points from your total life ! ");
        System.out.println("~Order~ Press 1 to escape ");
        System.out.println("~Order~ Press 2 to fight ! ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice==1)
        {
            rmi.PlayerAttacked(CL.getName(), 2);
            System.out.println("~INFO~ Your actual PV : "+rmi.MyLife(CL.getName()));
            if(rmi.MyLife(CL.getName())==0){return 0; }
            CL.MyMooves(rmi, CL);
        }
        else if (choice==2)
        {
            int rtr =CL.FightMonster(CL,rmi);
            if(rtr==0) return 0;
        }

        return 1;

    }
    public int FightMonster(Client CL,GServerInt rmi) throws RemoteException
    {
        int fuir=0; //NOK
        int tour=1;
        Scanner sc = new Scanner(System.in);

        int choice = (int) (Math.random() * 2 ); // 0 le Jr win 1 le monstre win
        while (fuir==0)
        {
            if(choice==0)
            {


                String wiam= rmi.WhereIam(CL.getName());
                String[] parts = wiam.split(",");
                String part1 = parts[0]; // x
                String part2 = parts[1]; //y
                int x = Integer.parseInt(part1);
                int y = Integer.parseInt(part2);


                int pvM=rmi.MonsterAttacked(Integer.toString(CL.getMyDung()), x,y);
                System.out.println("Tour numero "+tour+ " Le monstre à perdu 1 PV ! ");
                System.out.println("Il ne lui reste que "+pvM+" PV !");
                if(rmi.GetMonsterPv(Integer.toString(CL.getMyDung()), x, y)<=0)
                {
                    fuir=0;
                    System.out.println("~Info~ Vous avez tué le monstre Vous regagnée 2 PV!");
                    rmi.GiveLife(1, CL.getName(), Integer.toString(CL.getMyDung()), x, y);
                    System.out.println("Vos PV :"+rmi.MyLife(CL.getName()));
                    return 3;// monstre dead;
                }
            }
            else if(choice==1)
            {

                System.out.println("Tour numero "+tour+ " Vous avez perdu 1 PV ! ");
                rmi.PlayerAttacked(CL.getName(), 1);
                if(rmi.MyLife(CL.getName())==0)
                {
                    System.out.println("Vous etes Mort ! ");
                    return 0;// Player dead
                }
                System.out.println("~Info~ Il ne vous reste que : "+rmi.MyLife(CL.getName())+" PV !");

            }
            choice = (int) (Math.random() * 2 );
            System.out.println("~Order~ Voulez vous continuer à vous battre ou fuir ? 1 pour continuer, 2 pour fuir! ");
            fuir=sc.nextInt();
            if(fuir!=2) {fuir=0;}
            tour++;
        }
        return 1; // jamais !
    }


}
