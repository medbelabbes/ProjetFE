package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Promotion;
import PFE.Model.Section;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 11/05/2017.
 */
public class DataEtudiantController implements Initializable {

    @FXML
    public JFXComboBox SectionCombo;
    @FXML
    public AnchorPane PaneData;

    @FXML
    public JFXTextField MatriculeEtd;

    @FXML
    public JFXTextField NomEtd;

    @FXML
    public JFXTextField PrenomEtd;

    @FXML
    public JFXComboBox SexeEtd;

    @FXML
    public JFXDatePicker DateNaissanceEtd;

    @FXML
    public JFXTextField LieuNaissanceEtd;

    @FXML
    public JFXTextField EmailEtd;

    @FXML
    public JFXTextField AdresseEtd;

    @FXML
    public JFXComboBox WillayaEtd;

    @FXML
    public JFXTextField MoyenneEtd;


    @FXML
    public JFXComboBox GroupeCombo;

    @FXML
    public JFXButton SaveBtn;

    @FXML
    public Button BtnBack;
    @FXML
    public JFXComboBox PromoCombo;
    public JFXComboBox QualitéEtdCombo;

    Config2 Conf = new Config2();
    Connection Con;
    PreparedStatement pst;
    ResultSet rs, rs1;
    public static String ID_Section;
    private ObservableList<String> dataPromo;
    private ObservableList<String> dataSection;
    private ObservableList<String> dataGroupe;
    ObservableList<String> QualitéList = FXCollections.observableArrayList("chef", "membre");
    ObservableList<String> Sexe = FXCollections.observableArrayList("Masculin", "Féminin");
    ObservableList<String> Willaya = FXCollections.observableArrayList("1- Adrar", "2- Chlef", "3- Laghouat", "4- Oum El Bouaghi", "5- Batna", "6- Béjaïa", "7- Biskra", "8- Béchar", "9- Blida", "10- Bouira", "11- Tamanrasset", "12- Tébessa", "13- Tlemcen", "14- Tiaret", "15- Tizi Ouzou", "16- Alger", "17- Djelfa", "18- Jijel", "19- Sétif", "20- Saïda", "21- Skikda", "22- Sidi Bel Abbèes", "23- Annaba", "24- Guelma", "25- Constantine", "26- Médéa", "27- Mostaganem", "28- M'slia", "29- Mascara", "30- Ouargla", "31- Oran", "32-El Bayadh", "33- Illizi", "34- Bordj Bou Arreridj", "35- Boumerdès", "36- El Tarf", "37- Tindouf", "38- Tissemsilt", "39- El Oued", "40- Khenchela", "41- Souk Ahras", "42- Tipaza", "43- Mila", "44- Aïn Defla", "45- Naâma", "46- Aïn Témouchent", "47- Ghardaia", "48- Relizane");
    LocalDate LD;
    EtudiantController EC = new EtudiantController();
    public static int CodeSecion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        QualitéEtdCombo.setItems(QualitéList);
        SexeEtd.setItems(Sexe);
        WillayaEtd.setItems(Willaya);
        LoadDataComboSection();
        SectionCombo.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT ID FROM Section WHERE Designation='" + SectionCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs.next()) {
                    ID_Section = rs.getString("ID");
                    System.out.println(ID_Section);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            LoadDataComboGroupe();
        });

        if (!(EC.isB())) {
            MatriculeEtd.setEditable(false);
            MatriculeEtd.setDisable(true);
            MatriculeEtd.setStyle("-fx-text-fill: RED");
            MatriculeEtd.setText(EC.getMatriculeEtd());
            NomEtd.setText(EC.getNomEtd());
            PrenomEtd.setText(EC.getPrénomEtd());
            SexeEtd.setValue(EC.getSexeEtd());
            LD = Conf.LOCAL_DATE(String.valueOf(EC.getDateNETD()));
            DateNaissanceEtd.setValue(LD);
            LieuNaissanceEtd.setText(EC.getLieuNETD());
            EmailEtd.setText(EC.getEmailETD());
            AdresseEtd.setText(EC.getAdresseETD());
            WillayaEtd.setValue(EC.getWillayaETD());
            MoyenneEtd.setText(String.valueOf(EC.getMoyenneETD()));
            GroupeCombo.setValue(EC.getNumGroupeETD());
            QualitéEtdCombo.setValue(EC.getQualiteEtd());
            try {
                rs1 = Con.createStatement().executeQuery("SELECT Section_Code FROM Groupe where ID='" + EC.getNumGroupeETD() + "' ");
                while (rs1.next()) {
                    CodeSecion = Integer.valueOf(rs1.getString("Section_Code"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        SectionCombo.setValue(CodeSecion);
        GroupeCombo.setValue(EC.getNumGroupeETD());
    }


    public void LoadDataComboSection() {
        try {

            dataSection = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Designation FROM Section where Promotion_Numero='" + EC.getID_Promo() + "' ");
            while (rs.next()) {
                dataSection.add(rs.getString("Designation"));

            }
            SectionCombo.setItems(null);
            SectionCombo.setItems(dataSection);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataComboGroupe() {
        try {

            dataGroupe = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + ID_Section + "'");
            while (rs.next()) {
                dataGroupe.add(rs.getString("ID"));

            }
            GroupeCombo.setItems(null);
            GroupeCombo.setItems(dataGroupe);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void SetPromoCombo(JFXComboBox combo) {
        PromoCombo.setValue(combo.getSelectionModel().getSelectedItem().toString());
    }


    public void Back(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/Etudiants.fxml");

    }

    public void SaveEtudiant(ActionEvent actionEvent) {
        if (EC.isB()) {
            try {

                String querry = "INSERT INTO Etudiant(Matricule,Nom,Prenom,Sexe,Date_Naissance,Lieu_Naissance,Email,Adresse,Willaya,Moyenne,Username,Password,Qualite,Groupe_Numero,Etat,Premiere,Promotion_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = Con.prepareStatement(querry);
                pst.setString(1, MatriculeEtd.getText());
                pst.setString(2, NomEtd.getText());
                pst.setString(3, PrenomEtd.getText());
                pst.setString(4, SexeEtd.getSelectionModel().getSelectedItem().toString());
                pst.setString(5, DateNaissanceEtd.getValue().toString());
                pst.setString(6, LieuNaissanceEtd.getText());
                pst.setString(7, EmailEtd.getText());
                pst.setString(8, AdresseEtd.getText());
                pst.setString(9, WillayaEtd.getSelectionModel().getSelectedItem().toString());
                pst.setString(10, MoyenneEtd.getText());
                pst.setString(11, NomEtd.getText() + "." + PrenomEtd.getText());
                pst.setString(12, MatriculeEtd.getText());
                pst.setString(13, QualitéEtdCombo.getSelectionModel().getSelectedItem().toString());
                pst.setString(14, GroupeCombo.getSelectionModel().getSelectedItem().toString());
                pst.setString(15, "Autorisé");
                pst.setString(16, "1");
                pst.setString(17, EC.getID_Promo());
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Ajouté");

            } catch (Exception e) {
                Conf.dialog(Alert.AlertType.ERROR, e + " Matricule déja utilisé par auutre étudiant");
            }
        } else if (!(EC.isB())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiement modifier L'etudiant " + PrenomEtd.getText() + " " + NomEtd.getText() + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String sql = "UPDATE Etudiant set Nom ='" + NomEtd.getText() + "',Prenom='" + PrenomEtd.getText() + "',Sexe='" + SexeEtd.getValue() + "',Date_Naissance='" + DateNaissanceEtd.getValue().toString() + "',Lieu_Naissance='" + LieuNaissanceEtd.getText() + "',Email='" + EmailEtd.getText() + "',Adresse='" + AdresseEtd.getText() + "',Willaya='" + WillayaEtd.getSelectionModel().getSelectedItem().toString() + "',Moyenne='" + MoyenneEtd.getText() + "',Groupe_Numero='" + GroupeCombo.getSelectionModel().getSelectedItem().toString() + "' WHERE Matricule ='" + MatriculeEtd.getText() + "'";
                    pst = Con.prepareStatement(sql);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Modifié");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }


}

