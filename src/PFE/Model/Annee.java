package PFE.Model;

/**
 * Created by Mohamed on 05/05/2017.
 */
public class Annee {
    public String Code;
    public String Year;
    public String Etat;

    public Annee(String code, String year, String etat) {
        Code = code;
        Year = year;
        Etat = etat;
    }

    public Annee(String code) {
        Code = code;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }
}
