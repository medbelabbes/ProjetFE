package PFE.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/**
 * Created by Mohamed on 26/04/2017.
 */
public class Etudiant {

    private String MatriculeEtudiant;
    private String NomEtudiant;
    private String PrenomEtudiant;
    private String SexeEtudiant;
    private Date DateNaissanceEtudiant;
    private String LieuNaissanceEtudiant;
    private String EmailEtudiant;
    private String AdresseEtudiant;
    private String WillayaEtudiant;
    private float MoyenneEtudiant;
    private String UsernameEtudiant;
    private String PasswordEtudiant;
    private String Qualité;
    private int NumGroupEtudiant;
    private int NumEquipEtudiant;
    private String Etat;
    public String Premiere;
    public int Promo_ID;

    public String getQualité() {
        return Qualité;
    }

    public String getPremiere() {
        return Premiere;
    }

    public int getPromo_ID() {
        return Promo_ID;
    }

    public Etudiant(String matriculeEtudiant, String nomEtudiant, String prenomEtudiant, String sexeEtudiant, Date dateNaissanceEtudiant, String lieuNaissanceEtudiant, String emailEtudiant, String adresseEtudiant, String willayaEtudiant, float moyenneEtudiant, String usernameEtudiant, String x, int numGroupEtudiant, int numEquipEtudiant, String etat, int promo_ID) {
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        SexeEtudiant = sexeEtudiant;
        DateNaissanceEtudiant = dateNaissanceEtudiant;
        LieuNaissanceEtudiant = lieuNaissanceEtudiant;
        EmailEtudiant = emailEtudiant;
        AdresseEtudiant = adresseEtudiant;
        WillayaEtudiant = willayaEtudiant;
        MoyenneEtudiant = moyenneEtudiant;
        UsernameEtudiant = usernameEtudiant;
        Qualité = x;
        NumGroupEtudiant = numGroupEtudiant;
        NumEquipEtudiant = numEquipEtudiant;
        Etat = etat;
        Promo_ID = promo_ID;
    }

    public Etudiant() {

    }

    public Etudiant(String matriculeEtudiant, String nomEtudiant, String prenomEtudiant, String sexeEtudiant, Date dateNaissanceEtudiant, String lieuNaissanceEtudiant, String emailEtudiant, String adresseEtudiant, String willayaEtudiant, float moyenneEtudiant, String usernameEtudiant, int numGroupEtudiant, int numEquipEtudiant, String etat) {
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        SexeEtudiant = sexeEtudiant;
        DateNaissanceEtudiant = dateNaissanceEtudiant;
        LieuNaissanceEtudiant = lieuNaissanceEtudiant;
        EmailEtudiant = emailEtudiant;
        AdresseEtudiant = adresseEtudiant;
        WillayaEtudiant = willayaEtudiant;
        MoyenneEtudiant = moyenneEtudiant;
        UsernameEtudiant = usernameEtudiant;
        NumGroupEtudiant = numGroupEtudiant;
        NumEquipEtudiant = numEquipEtudiant;
        Etat = etat;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public Etudiant(String matriculeEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
    }

    public Etudiant(String matriculeEtudiant, String nomEtudiant, String prenomEtudiant, String sexeEtudiant, Date dateNaissanceEtudiant, String lieuNaissanceEtudiant, String emailEtudiant, String adresseEtudiant, String willayaEtudiant, float moyenneEtudiant, String niveauEtudiant, String usernameEtudiant, String passwordEtudiant, int numGroupEtudiant, int numEquipEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        SexeEtudiant = sexeEtudiant;
        DateNaissanceEtudiant = dateNaissanceEtudiant;
        LieuNaissanceEtudiant = lieuNaissanceEtudiant;
        EmailEtudiant = emailEtudiant;
        AdresseEtudiant = adresseEtudiant;
        WillayaEtudiant = willayaEtudiant;
        MoyenneEtudiant = moyenneEtudiant;
        UsernameEtudiant = usernameEtudiant;
        PasswordEtudiant = passwordEtudiant;
        NumGroupEtudiant = numGroupEtudiant;
        this.NumEquipEtudiant = numEquipEtudiant;
    }

    public Etudiant(String matriculeEtudiant, String nomEtudiant, String prenomEtudiant, String sexeEtudiant, Date dateNaissanceEtudiant, String lieuNaissanceEtudiant, String emailEtudiant, String adresseEtudiant, String willayaEtudiant, float moyenneEtudiant, String usernameEtudiant, int numGroupEtudiant, int numEquipEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        SexeEtudiant = sexeEtudiant;
        DateNaissanceEtudiant = dateNaissanceEtudiant;
        LieuNaissanceEtudiant = lieuNaissanceEtudiant;
        EmailEtudiant = emailEtudiant;
        AdresseEtudiant = adresseEtudiant;
        WillayaEtudiant = willayaEtudiant;
        MoyenneEtudiant = moyenneEtudiant;
        UsernameEtudiant = usernameEtudiant;

        NumGroupEtudiant = numGroupEtudiant;
        this.NumEquipEtudiant = numEquipEtudiant;
    }

    public Etudiant(String matriculeEtudiant, String nomEtudiant, String prenomEtudiant, String emailEtudiant, float moyenneEtudiant, int numGroupEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        EmailEtudiant = emailEtudiant;
        MoyenneEtudiant = moyenneEtudiant;
        NumGroupEtudiant = numGroupEtudiant;
    }

    public String getMatriculeEtudiant() {
        return MatriculeEtudiant;
    }

    public void setMatriculeEtudiant(String matriculeEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
    }

    public String getNomEtudiant() {
        return NomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        NomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return PrenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        PrenomEtudiant = prenomEtudiant;
    }

    public String getSexeEtudiant() {
        return SexeEtudiant;
    }

    public void setSexeEtudiant(String sexeEtudiant) {
        SexeEtudiant = sexeEtudiant;
    }

    public Date getDateNaissanceEtudiant() {
        return DateNaissanceEtudiant;
    }

    public void setDateNaissanceEtudiant(Date dateNaissanceEtudiant) {
        DateNaissanceEtudiant = dateNaissanceEtudiant;
    }

    public String getLieuNaissanceEtudiant() {
        return LieuNaissanceEtudiant;
    }

    public void setLieuNaissanceEtudiant(String lieuNaissanceEtudiant) {
        LieuNaissanceEtudiant = lieuNaissanceEtudiant;
    }

    public String getEmailEtudiant() {
        return EmailEtudiant;
    }

    public void setEmailEtudiant(String emailEtudiant) {
        EmailEtudiant = emailEtudiant;
    }

    public String getAdresseEtudiant() {
        return AdresseEtudiant;
    }

    public void setAdresseEtudiant(String adresseEtudiant) {
        AdresseEtudiant = adresseEtudiant;
    }

    public String getWillayaEtudiant() {
        return WillayaEtudiant;
    }

    public void setWillayaEtudiant(String willayaEtudiant) {
        WillayaEtudiant = willayaEtudiant;
    }

    public float getMoyenneEtudiant() {
        return MoyenneEtudiant;
    }

    public void setMoyenneEtudiant(float moyenneEtudiant) {
        MoyenneEtudiant = moyenneEtudiant;
    }

    public String getUsernameEtudiant() {
        return UsernameEtudiant;
    }

    public void setUsernameEtudiant(String usernameEtudiant) {
        UsernameEtudiant = usernameEtudiant;
    }

    public String getPasswordEtudiant() {
        return PasswordEtudiant;
    }

    public void setPasswordEtudiant(String passwordEtudiant) {
        PasswordEtudiant = passwordEtudiant;
    }

    public int getNumGroupEtudiant() {
        return NumGroupEtudiant;
    }

    public void setNumGroupEtudiant(int numGroupEtudiant) {
        NumGroupEtudiant = numGroupEtudiant;
    }

    public int getNumEquipEtudiant() {
        return NumEquipEtudiant;
    }

    public void setNumEquipEtudiant(int numEquipEtudiant) {
        NumEquipEtudiant = numEquipEtudiant;
    }
}
