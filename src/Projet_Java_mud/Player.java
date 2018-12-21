package Projet_Java_mud;

import java.io.Serializable;


public class Player implements Serializable{
    private String NomJ;
    private int Pvie;

    public void setPvie(int Pvie) {
        this.Pvie = Pvie;
    }

    public int getPvie() {
        return Pvie;
    }



    public Player(String NomJ) {
        this.NomJ = NomJ;
        this.Pvie=10;
    }

    public String getNomJ() {
        return NomJ;
    }


}
