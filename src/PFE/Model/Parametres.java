package PFE.Model;

/**
 * Created by Mohamed on 04/06/2017.
 */
public class Parametres {

    public int ID;
    public int Promotion;
    public int Nbr_Max;
    public int Nbr_Min;
    public String Affectation_Mode;
    public int Nbr_Choix;
    public String Duree;
    public int Coefficient;

    public Parametres(int ID, int promotion, int nbr_Max, int nbr_Min, String affectation_Mode, int nbr_Choix, String duree, int coefficient) {
        this.ID = ID;
        Promotion = promotion;
        Nbr_Max = nbr_Max;
        Nbr_Min = nbr_Min;
        Affectation_Mode = affectation_Mode;
        Nbr_Choix = nbr_Choix;
        Duree = duree;
        Coefficient = coefficient;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPromotion() {
        return Promotion;
    }

    public void setPromotion(int promotion) {
        Promotion = promotion;
    }

    public int getNbr_Max() {
        return Nbr_Max;
    }

    public void setNbr_Max(int nbr_Max) {
        Nbr_Max = nbr_Max;
    }

    public int getNbr_Min() {
        return Nbr_Min;
    }

    public void setNbr_Min(int nbr_Min) {
        Nbr_Min = nbr_Min;
    }

    public String getAffectation_Mode() {
        return Affectation_Mode;
    }

    public void setAffectation_Mode(String affectation_Mode) {
        Affectation_Mode = affectation_Mode;
    }

    public int getNbr_Choix() {
        return Nbr_Choix;
    }

    public void setNbr_Choix(int nbr_Choix) {
        Nbr_Choix = nbr_Choix;
    }

    public String getDuree() {
        return Duree;
    }

    public void setDuree(String duree) {
        Duree = duree;
    }

    public int getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(int coefficient) {
        Coefficient = coefficient;
    }
}
