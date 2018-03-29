package PFE.Model;

/**
 * Created by Mohamed on 05/05/2017.
 */
public class Section {
    public int Code;
    public String Designation;
    public int Num_Etudiant;
    public int Num_Promotion;

    public Section(int code, String designation, int num_Etudiant, int num_Promotion) {
        Code = code;
        Designation = designation;
        Num_Etudiant = num_Etudiant;
        Num_Promotion = num_Promotion;
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

    public int getNum_Etudiant() {
        return Num_Etudiant;
    }

    public void setNum_Etudiant(int num_Etudiant) {
        Num_Etudiant = num_Etudiant;
    }

    public int getNum_Promotion() {
        return Num_Promotion;
    }

    public void setNum_Promotion(int num_Promotion) {
        Num_Promotion = num_Promotion;
    }
}
