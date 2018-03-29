package PFE.Model;

/**
 * Created by Mohamed on 08/06/2017.
 */
public class Option {
    public int Code;
    public String Designation;
    public String Niveau;

    public Option(int ID, String designation, String n) {
        this.Code = ID;
        Designation = designation;
        this.Niveau=n;
    }

    public Option(String designation) {
        Designation = designation;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
