package Projet_Java_mud;


public class Monster {

    private int Pv;

    public Monster() {
        this.Pv=5;
    }
    public Monster(int pv)
    {
        this.Pv=pv;
    }

    public void setPv(int Pv) {
        this.Pv = Pv;
    }

    public int getPv() {
        return Pv;
    }



}

