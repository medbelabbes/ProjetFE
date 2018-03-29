package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Enseignant;
import PFE.Model.Promotion;
import PFE.Model.Section;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.SetAdapterChange;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import sun.security.util.Password;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class DataEnseignantController implements Initializable {


    @FXML
    public AnchorPane PaneData;

    @FXML
    public JFXTextField MatriculEns;


    @FXML
    private JFXTextField PrenomEns;

    @FXML
    private JFXComboBox<String> SexeEns;

    @FXML
    private JFXDatePicker DateNEns;

    @FXML
    private JFXTextField LieuNEns;

    @FXML
    private JFXTextField EmailEns;

    @FXML
    private JFXTextField AdresseEns;

    @FXML
    private JFXComboBox WillayaEns;
    @FXML
    public JFXTextField NomEns;
    @FXML
    public JFXComboBox SpecialiteComboEnseign;
    @FXML
    public JFXComboBox GradeComboEnseign;

    @FXML
    public JFXButton SaveBtn;

    @FXML
    public Button BtnBack;


    Config2 Conf = new Config2();
    Connection Con;
    PreparedStatement pst;
    ResultSet rs;
    LocalDate LD;
    EnseignantController ENS = new EnseignantController();
    ObservableList<String> Sexe = FXCollections.observableArrayList("Masculin", "Féminin");
    ObservableList<String> Willaya = FXCollections.observableArrayList("1- Adrar", "2- Chlef", "3- Laghouat", "4- Oum El Bouaghi", "5- Batna", "6- Béjaïa", "7- Biskra", "8- Béchar", "9- Blida", "10- Bouira", "11- Tamanrasset", "12- Tébessa", "13- Tlemcen", "14- Tiaret", "15- Tizi Ouzou", "16- Alger", "17- Djelfa", "18- Jijel", "19- Sétif", "20- Saïda", "21- Skikda", "22- Sidi Bel Abbèes", "23- Annaba", "24- Guelma", "25- Constantine", "26- Médéa", "27- Mostaganem", "28- M'slia", "29- Mascara", "30- Ouargla", "31- Oran", "32-El Bayadh", "33- Illizi", "34- Bordj Bou Arreridj", "35- Boumerdès", "36- El Tarf", "37- Tindouf", "38- Tissemsilt", "39- El Oued", "40- Khenchela", "41- Souk Ahras", "42- Tipaza", "43- Mila", "44- Aïn Defla", "45- Naâma", "46- Aïn Témouchent", "47- Ghardaia", "48- Relizane");
    ObservableList<String> SpecialitéList = FXCollections.observableArrayList("Génie logiciel", "Systeme information", "Reseaux", "Systemes Embarqués", "Architecture des ordinateurs", "Systéme d'exploitation");
    ObservableList<String> GradeList = FXCollections.observableArrayList("Professeur", "MCA", "MCB", "MAA", "MAB");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        SexeEns.setItems(Sexe);
        WillayaEns.setItems(Willaya);
        SpecialiteComboEnseign.setItems(SpecialitéList);
        GradeComboEnseign.setItems(GradeList);

        if (!(ENS.isB())) {
            MatriculEns.setEditable(false);
            MatriculEns.setDisable(true);
            MatriculEns.setStyle("-fx-text-fill: RED");
            MatriculEns.setText(ENS.getMatriculeEns());
            NomEns.setText(ENS.getNomEns());
            PrenomEns.setText(ENS.getPrenomEns());
            SexeEns.setValue(ENS.getSexeEns());
            LD = Conf.LOCAL_DATE(String.valueOf(ENS.getDateNEns()));
            DateNEns.setValue(LD);
            LieuNEns.setText(ENS.getLieuNEns());
            EmailEns.setText(ENS.getEmailEns());
            AdresseEns.setText(ENS.getAdresseEns());
            WillayaEns.setValue(ENS.getWillayaEns());
            SpecialiteComboEnseign.setValue(ENS.getSpecialitéEns());
            GradeComboEnseign.setValue(ENS.getGradEns());
        }
    }


    public void Back(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/Enseignants.fxml");
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@esi-sba.dz$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void SaveEnseignant(ActionEvent actionEvent) {
        if (ENS.isB()) {
            try {

                String querry = "INSERT INTO Enseignant(Matricule,Nom,Prenom,Sexe,Date_Naissance,Lieu_Naissance,Email,Adresse,Willaya,Specialite,Grade,Username,Password,Etat,Premiere) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = Con.prepareStatement(querry);
                pst.setString(1, MatriculEns.getText());
                pst.setString(2, NomEns.getText());
                pst.setString(3, PrenomEns.getText());
                pst.setString(4, SexeEns.getSelectionModel().getSelectedItem().toString());
                pst.setString(5, DateNEns.getValue().toString());
                pst.setString(6, LieuNEns.getText());
                pst.setString(7, EmailEns.getText());
                pst.setString(8, AdresseEns.getText());
                pst.setString(9, WillayaEns.getSelectionModel().getSelectedItem().toString());
                pst.setString(10, SpecialiteComboEnseign.getSelectionModel().getSelectedItem().toString());
                pst.setString(11, GradeComboEnseign.getSelectionModel().getSelectedItem().toString());
                pst.setString(12, NomEns.getText() + "." + PrenomEns.getText());
                pst.setString(13, MatriculEns.getText());
                pst.setString(14, "Autorisé");
                pst.setString(15, "1");

                if (isValidEmailAddress(EmailEns.getText()) == false) {

                    Conf.dialog(Alert.AlertType.INFORMATION, "Adress Email invalide ");
                } else {
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Enseignant Ajouté");
                }
            } catch (Exception e) {
                Conf.dialog(Alert.AlertType.ERROR, "Matricule déja utilisé par autre enseignantt");
            }
        } else if (!(ENS.isB())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiement modifier L'Enseignant " + PrenomEns.getText() + " " + NomEns.getText() + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String sql = "UPDATE Enseignant set Nom ='" + NomEns.getText() + "',Prenom='" + PrenomEns.getText() + "',Sexe='" + SexeEns.getSelectionModel().getSelectedItem().toString() + "',Date_Naissance='" + DateNEns.getValue().toString() + "',Lieu_Naissance='" + LieuNEns.getText() + "',Email='" + EmailEns.getText() + "',Adresse='" + AdresseEns.getText() + "',Willaya='" + WillayaEns.getSelectionModel().getSelectedItem().toString() + "',Grade='" + GradeComboEnseign.getSelectionModel().getSelectedItem().toString() + "',Specialite='" + SpecialiteComboEnseign.getSelectionModel().getSelectedItem().toString() + "' WHERE Matricule ='" + MatriculEns.getText() + "'";
                    pst = Con.prepareStatement(sql);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Enseignant Modifié");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}

