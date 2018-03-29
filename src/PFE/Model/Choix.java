package PFE.Model;

/**
 * Created by Mohamed on 07/06/2017.
 */
public class Choix {
    public int Numéro_Equipe;
    public int Code_Projet;
    public String Projet;
    public int Ordre;

    public Choix(int numéro_Equipe, int code_Projet, String projet, int ordre) {
        Numéro_Equipe = numéro_Equipe;
        Code_Projet = code_Projet;
        Projet = projet;
        Ordre = ordre;
    }

    public int getNuméro_Equipe() {
        return Numéro_Equipe;
    }

    public void setNuméro_Equipe(int numéro_Equipe) {
        Numéro_Equipe = numéro_Equipe;
    }

    public int getCode_Projet() {
        return Code_Projet;
    }

    public void setCode_Projet(int code_Projet) {
        Code_Projet = code_Projet;
    }

    public String getProjet() {
        return Projet;
    }

    public void setProjet(String projet) {
        Projet = projet;
    }

    public int getOrdre() {
        return Ordre;
    }

    public void setOrdre(int ordre) {
        Ordre = ordre;
    }
}
