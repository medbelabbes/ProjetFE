package PFE.Model;

/**
 * Created by Mohamed on 15/05/2017.
 */
public class Equipe {
    public int Numéro;
    public int Nbr_Etd;
    public float Moyenne;
    public int Code_Projet;
    public int Code_Promo;
    public String Projet;
    public String Validation;
    public String Designation;

    public Equipe(int numéro, int nbr_Etd, float moyenne, int code_Projet, int c) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Code_Projet = code_Projet;
        Code_Promo = c;
    }

    public Equipe(int numéro, int nbr_Etd, float moyenne, int code_Projet, int code_Promo, String projet, String validation) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Code_Projet = code_Projet;
        Code_Promo = code_Promo;
        Projet = projet;
        Validation = validation;
    }

    public String getValidation() {
        return Validation;
    }

    public Equipe(int numéro, int nbr_Etd, float moyenne, int c) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Code_Promo = c;
    }

    public Equipe(int numéro, int nbr_Etd, float moyenne, String v) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Validation = v;
    }


    public Equipe(int numéro, int nbr_Etd, float moyenne, int code_Promo, String validation) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Code_Promo = code_Promo;
        Validation = validation;
    }

    public Equipe(int numéro, int nbr_Etd, float moyenne, int code_Projet, String projet, int code_Promo) {
        Numéro = numéro;
        Nbr_Etd = nbr_Etd;
        Moyenne = moyenne;
        Code_Projet = code_Projet;
        Code_Promo = code_Promo;
        Projet = projet;
    }

    public int getNuméro() {
        return Numéro;
    }

    public void setNuméro(int numéro) {
        Numéro = numéro;
    }

    public int getNbr_Etd() {
        return Nbr_Etd;
    }

    public void setNbr_Etd(int nbr_Etd) {
        Nbr_Etd = nbr_Etd;
    }

    public float getMoyenne() {
        return Moyenne;
    }

    public void setMoyenne(float moyenne) {
        Moyenne = moyenne;
    }

    public int getCode_Projet() {
        return Code_Projet;
    }

    public void setCode_Projet(int code_Projet) {
        Code_Projet = code_Projet;
    }

    public int getCode_Promo() {
        return Code_Promo;
    }

    public void setCode_Promo(int code_Promo) {
        Code_Promo = code_Promo;
    }

    public String getProjet() {
        return Projet;
    }

    public void setProjet(String projet) {
        Projet = projet;
    }
}
