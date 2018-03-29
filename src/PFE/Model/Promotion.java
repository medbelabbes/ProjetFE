package PFE.Model;

/**
 * Created by Mohamed on 04/05/2017.
 */
public class Promotion {
    public int Numero;
    public String Désignation;
    public int Nombre_etudiant;
    public String Cycle;
    public String Niveau;
    public String Annee_Code;
    public int Option_Code;

    public Promotion(int numero, String désignation, int nombre_etudiant, String cycle, String niveau, String annee_Code) {
        Numero = numero;
        Désignation = désignation;
        Nombre_etudiant = nombre_etudiant;
        Cycle = cycle;
        Niveau = niveau;
        Annee_Code = annee_Code;
    }

    public int getOption_Code() {
        return Option_Code;
    }

    public void setOption_Code(int option_Code) {
        Option_Code = option_Code;
    }

    public Promotion(int numero, String désignation, int nombre_etudiant, String cycle, String niveau, String annee_Code, int option_Code) {
        Numero = numero;
        Désignation = désignation;
        Nombre_etudiant = nombre_etudiant;
        Cycle = cycle;
        Niveau = niveau;
        Annee_Code = annee_Code;
        Option_Code = option_Code;
    }

    public Promotion(int numero, int nombre_etudiant, String cycle, String niveau, String annee_Code) {
        Numero = numero;
        Nombre_etudiant = nombre_etudiant;
        Cycle = cycle;
        Niveau = niveau;
        Annee_Code = annee_Code;
    }

    public String getDésignation() {
        return Désignation;
    }

    public void setDésignation(String désignation) {
        Désignation = désignation;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public int getNombre_etudiant() {
        return Nombre_etudiant;
    }

    public void setNombre_etudiant(int nombre_etudiant) {
        Nombre_etudiant = nombre_etudiant;
    }

    public String getCycle() {
        return Cycle;
    }

    public void setCycle(String cycle) {
        Cycle = cycle;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public String getAnnee_Code() {
        return Annee_Code;
    }

    public void setAnnee_Code(String annee_Code) {
        Annee_Code = annee_Code;
    }
}
