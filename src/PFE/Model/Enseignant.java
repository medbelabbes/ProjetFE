package PFE.Model;

import java.util.Date;

/**
 * Created by Mohamed on 12/05/2017.
 */
public class Enseignant {
    public String MatriculeEnseignant;
    public String NomEnseignant;
    public String PrénomEnseignant;
    public String SexeEnseignant;
    public Date DateNaissanceEnseignant;
    public String LieuNaissanceEnseignant;
    public String EmailEnseignant;
    public String AdresseEnseignant;
    public String WillayaEnseignant;
    public String SpécialitéEnseignant;
    public String GradeEnseignant;
    public String UserEnseignant;
    public String PasswordEnseignant;
    public String Etat;


    public Enseignant(String matriculeEnseignant, String nomEnseignant, String prénomEnseignant, String sexeEnseignant, Date dateNaissanceEnseignant, String lieuNaissanceEnseignant, String emailEnseignant, String adresseEnseignant, String willayaEnseignant, String spécialitéEnseignant, String gradeEnseignant, String usernameEnseignant, String passwordEnseignant, String e) {
        MatriculeEnseignant = matriculeEnseignant;
        NomEnseignant = nomEnseignant;
        PrénomEnseignant = prénomEnseignant;
        SexeEnseignant = sexeEnseignant;
        DateNaissanceEnseignant = dateNaissanceEnseignant;
        LieuNaissanceEnseignant = lieuNaissanceEnseignant;
        EmailEnseignant = emailEnseignant;
        AdresseEnseignant = adresseEnseignant;
        WillayaEnseignant = willayaEnseignant;
        SpécialitéEnseignant = spécialitéEnseignant;
        GradeEnseignant = gradeEnseignant;
        UserEnseignant = usernameEnseignant;
        PasswordEnseignant = passwordEnseignant;
        Etat = e;
    }

    public Enseignant(String matriculeEnseignant, String nomEnseignant, String prénomEnseignant, String sexeEnseignant, Date dateNaissanceEnseignant, String lieuNaissanceEnseignant, String emailEnseignant, String adresseEnseignant, String willayaEnseignant, String spécialitéEnseignant, String gradeEnseignant, String userEnseignant, String etat) {
        MatriculeEnseignant = matriculeEnseignant;
        NomEnseignant = nomEnseignant;
        PrénomEnseignant = prénomEnseignant;
        SexeEnseignant = sexeEnseignant;
        DateNaissanceEnseignant = dateNaissanceEnseignant;
        LieuNaissanceEnseignant = lieuNaissanceEnseignant;
        EmailEnseignant = emailEnseignant;
        AdresseEnseignant = adresseEnseignant;
        WillayaEnseignant = willayaEnseignant;
        SpécialitéEnseignant = spécialitéEnseignant;
        GradeEnseignant = gradeEnseignant;
        UserEnseignant = userEnseignant;
        Etat = etat;
    }

    public Enseignant(String matriculeEnseignant, String nomEnseignant, String prénomEnseignant, String sexeEnseignant, Date dateNaissanceEnseignant, String lieuNaissanceEnseignant, String emailEnseignant, String adresseEnseignant, String willayaEnseignant, String spécialitéEnseignant, String gradeEnseignant, String usernameEnseignant) {
        MatriculeEnseignant = matriculeEnseignant;
        NomEnseignant = nomEnseignant;
        PrénomEnseignant = prénomEnseignant;
        SexeEnseignant = sexeEnseignant;
        DateNaissanceEnseignant = dateNaissanceEnseignant;
        LieuNaissanceEnseignant = lieuNaissanceEnseignant;
        EmailEnseignant = emailEnseignant;
        AdresseEnseignant = adresseEnseignant;
        WillayaEnseignant = willayaEnseignant;
        SpécialitéEnseignant = spécialitéEnseignant;
        GradeEnseignant = gradeEnseignant;
        UserEnseignant = usernameEnseignant;


    }

    public String getUserEnseignant() {
        return UserEnseignant;
    }

    public void setUserEnseignant(String userEnseignant) {
        UserEnseignant = userEnseignant;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public String getMatriculeEnseignant() {
        return MatriculeEnseignant;
    }

    public void setMatriculeEnseignant(String matriculeEnseignant) {
        MatriculeEnseignant = matriculeEnseignant;
    }

    public String getNomEnseignant() {
        return NomEnseignant;
    }

    public void setNomEnseignant(String nomEnseignant) {
        NomEnseignant = nomEnseignant;
    }

    public String getPrénomEnseignant() {
        return PrénomEnseignant;
    }

    public void setPrénomEnseignant(String prénomEnseignant) {
        PrénomEnseignant = prénomEnseignant;
    }

    public String getSexeEnseignant() {
        return SexeEnseignant;
    }

    public void setSexeEnseignant(String sexeEnseignant) {
        SexeEnseignant = sexeEnseignant;
    }

    public Date getDateNaissanceEnseignant() {
        return DateNaissanceEnseignant;
    }

    public void setDateNaissanceEnseignant(Date dateNaissanceEnseignant) {
        DateNaissanceEnseignant = dateNaissanceEnseignant;
    }

    public String getLieuNaissanceEnseignant() {
        return LieuNaissanceEnseignant;
    }

    public void setLieuNaissanceEnseignant(String lieuNaissanceEnseignant) {
        LieuNaissanceEnseignant = lieuNaissanceEnseignant;
    }

    public String getEmailEnseignant() {
        return EmailEnseignant;
    }

    public void setEmailEnseignant(String emailEnseignant) {
        EmailEnseignant = emailEnseignant;
    }

    public String getAdresseEnseignant() {
        return AdresseEnseignant;
    }

    public void setAdresseEnseignant(String adresseEnseignant) {
        AdresseEnseignant = adresseEnseignant;
    }

    public String getWillayaEnseignant() {
        return WillayaEnseignant;
    }

    public void setWillayaEnseignant(String willayaEnseignant) {
        WillayaEnseignant = willayaEnseignant;
    }

    public String getSpécialitéEnseignant() {
        return SpécialitéEnseignant;
    }

    public void setSpécialitéEnseignant(String spécialitéEnseignant) {
        SpécialitéEnseignant = spécialitéEnseignant;
    }

    public String getGradeEnseignant() {
        return GradeEnseignant;
    }

    public void setGradeEnseignant(String gradeEnseignant) {
        GradeEnseignant = gradeEnseignant;
    }

    public String getUsernameEnseignant() {
        return UserEnseignant;
    }

    public void setUsernameEnseignant(String usernameEnseignant) {
        UserEnseignant = usernameEnseignant;
    }

    public String getPasswordEnseignant() {
        return PasswordEnseignant;
    }

    public void setPasswordEnseignant(String passwordEnseignant) {
        PasswordEnseignant = passwordEnseignant;
    }


}
