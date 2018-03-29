package PFE.Model;

import java.sql.Date;
import java.sql.Time;

public class Soutenance {
    public String Code;
    public String Jour;
    public String Heure_D;
    public String Heure_F;
    public String Salle_Numero;
    public String Equipe_Numero;
    public String Jury_Numero;


    public Soutenance(String code, String jour, String heure_D,String heure_F , String equipe_Numero, String salle_Numero , String jury_Numero) {
        Code=code;
        Jour=jour;
        Heure_D=heure_D;
        Heure_F=heure_F;
        Salle_Numero=salle_Numero;
        Equipe_Numero=equipe_Numero;
        Jury_Numero = jury_Numero;
    }



    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getJour() {
        return Jour;
    }

    public void setJour(String jour) {
        Jour = jour;
    }

    public String getHeure_D() {
        return Heure_D;
    }

    public void setHeure_D(String heure_D) {
        Heure_D = heure_D;
    }

    public String getHeure_F() {
        return Heure_F;
    }

    public void setHeure_F(String heure_F) {
        Heure_F = heure_F;
    }

    public String getSalle_Numero() {
        return Salle_Numero;
    }

    public void setSalle_Numero(String salle_Numero) {
        Salle_Numero = salle_Numero;
    }

    public String getEquipe_Numero() {
        return Equipe_Numero;
    }

    public void setEquipe_Numero(String equipe_Numero) {
        Equipe_Numero = equipe_Numero;
    }

    public String getJury_Numero() {
        return Jury_Numero;
    }

    public void setJury_Numero(String jury_Numero) {
        Jury_Numero = jury_Numero;
    }
}
