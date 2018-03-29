package PFE.Model;

/**
 * Created by Mohamed on 19/05/2017.
 */
public class Projet {
    public int Code;
    public String Designation;
    public String Specialite;
    public String Resume;
    public String Technologie;
    public String Outils;
    public String Prerequis;
    public String Plan_travail;
    public String Duree;
    public int Nombre_Equipe;
    public int PromoCode;
    public String Validation;

    public Projet(int code, String designation, String specialite, String resume, String technologie, String outils, String prerequis, String plan_travail, String duree, int nombre_Equipe, int promoCode, String validation) {
        Code = code;
        Designation = designation;
        Specialite = specialite;
        Resume = resume;
        Technologie = technologie;
        Outils = outils;
        Prerequis = prerequis;
        Plan_travail = plan_travail;
        Duree = duree;
        Nombre_Equipe = nombre_Equipe;
        PromoCode = promoCode;
        Validation = validation;
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

    public String getSpecialite() {
        return Specialite;
    }

    public void setSpecialite(String specialite) {
        Specialite = specialite;
    }

    public String getResume() {
        return Resume;
    }

    public void setResume(String resume) {
        Resume = resume;
    }

    public String getTechnologie() {
        return Technologie;
    }

    public void setTechnologie(String technologie) {
        Technologie = technologie;
    }

    public String getOutils() {
        return Outils;
    }

    public void setOutils(String outils) {
        Outils = outils;
    }

    public String getPrerequis() {
        return Prerequis;
    }

    public void setPrerequis(String prerequis) {
        Prerequis = prerequis;
    }

    public String getPlan_travail() {
        return Plan_travail;
    }

    public void setPlan_travail(String plan_travail) {
        Plan_travail = plan_travail;
    }

    public String getDuree() {
        return Duree;
    }

    public void setDuree(String duree) {
        Duree = duree;
    }

    public int getNombre_Equipe() {
        return Nombre_Equipe;
    }

    public void setNombre_Equipe(int nombre_Equipe) {
        Nombre_Equipe = nombre_Equipe;
    }

    public int getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(int promoCode) {
        PromoCode = promoCode;
    }

    public String getValidation() {
        return Validation;
    }

    public void setValidation(String validation) {
        Validation = validation;
    }
}
