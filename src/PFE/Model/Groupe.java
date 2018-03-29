package PFE.Model;

/**
 * Created by Mohamed on 09/05/2017.
 */
public class Groupe {
    public int Code;
    public String DesignationGroupe;
    public int Nbr_etd;
    public int Code_Section;

    public Groupe(int code, int nbr_etd, int code_Section) {
        Code = code;
        Nbr_etd = nbr_etd;
        Code_Section = code_Section;
    }

    public Groupe(int code, String designationGroupe, int nbr_etd, int code_Section) {
        Code = code;
        DesignationGroupe = designationGroupe;
        Nbr_etd = nbr_etd;
        Code_Section = code_Section;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public int getNbr_etd() {
        return Nbr_etd;
    }

    public void setNbr_etd(int nbr_etd) {
        Nbr_etd = nbr_etd;
    }

    public int getCode_Section() {
        return Code_Section;
    }

    public void setCode_Section(int code_Section) {
        Code_Section = code_Section;
    }
}
