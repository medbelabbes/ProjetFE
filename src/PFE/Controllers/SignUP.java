package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Animations.*;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.sql.*;

/**
 * Created by Mohamed on 21/04/2017.
 */
public class SignUP implements Initializable {

    @FXML
    public JFXTextField NomAdmin;
    @FXML
    public JFXTextField PrenomAdmin;
    @FXML
    public JFXTextField EmailAdmin;
    @FXML
    public JFXDatePicker DateNAdmin;
    @FXML
    public JFXTextField UsernameAdmin;
    @FXML
    public JFXPasswordField PasswordAdmin;
    @FXML
    public JFXPasswordField ConfirmPasswordAdmin;
    @FXML
    public JFXButton InscrireButton;
    @FXML
    public Label lblClose;
    Connection Con = null;
    PreparedStatement pst, pst1, pst2;
    Stage stage;
    Config2 c = new Config2();


    ObservableList<String> Sexe = FXCollections.observableArrayList("Masculin", "Féminin");
    ObservableList<String> Willaya = FXCollections.observableArrayList("1- Adrar", "2- Chlef", "3- Laghouat", "4- Oum El Bouaghi", "5- Batna", "6- Béjaïa", "7- Biskra", "8- Béchar", "9- Blida", "10- Bouira", "11- Tamanrasset", "12- Tébessa", "13- Tlemcen", "14- Tiaret", "15- Tizi Ouzou", "16- Alger", "17- Djelfa", "18- Jijel", "19- Sétif", "20- Saïda", "21- Skikda", "22- Sidi Bel Abbèes", "23- Annaba", "24- Guelma", "25- Constantine", "26- Médéa", "27- Mostaganem", "28- M'slia", "29- Mascara", "30- Ouargla", "31- Oran", "32-El Bayadh", "33- Illizi", "34- Bordj Bou Arreridj", "35- Boumerdès", "36- El Tarf", "37- Tindouf", "38- Tissemsilt", "39- El Oued", "40- Khenchela", "41- Souk Ahras", "42- Tipaza", "43- Mila", "44- Aïn Defla", "45- Naâma", "46- Aïn Témouchent", "47- Ghardaia", "48- Relizane");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        new FadeInLeftTransition(NomAdmin).play();
        new FadeInLeftTransition(PrenomAdmin).play();
        new FadeInLeftTransition(EmailAdmin).play();
        new FadeInLeftTransition(DateNAdmin).play();
        new FadeInLeftTransition(UsernameAdmin).play();
        new FadeInLeftTransition(PasswordAdmin).play();
        new FadeInLeftTransition(ConfirmPasswordAdmin).play();
        new FadeInLeftTransition(InscrireButton).play();
        Platform.runLater(() -> {
            lblClose.setOnMouseClicked((MouseEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
    }

    public String getYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return String.valueOf(year);

    }

    public void Signup(ActionEvent actionEvent) {

        try {

            String sql = "INSERT INTO Adminstrateur(Nom,Prenom,Email,Date_naissance,Login,Password) VALUES(?,?,?,?,?,?)";
            pst = Con.prepareStatement(sql);
            pst.setString(1, NomAdmin.getText());
            pst.setString(2, PrenomAdmin.getText());
            pst.setString(3, EmailAdmin.getText());
            pst.setString(4, DateNAdmin.getValue().toString());
            pst.setString(5, UsernameAdmin.getText());
            pst.setString(6, PasswordAdmin.getText());
            if (PasswordAdmin.getText().equals(ConfirmPasswordAdmin.getText())) {
                pst.execute();
                System.out.println("Inscription réussi!!");
                c.newStage(stage, lblClose, "/PFE/View/Login.fxml", "Test App", true, StageStyle.UNDECORATED, false);
            } else {

                c.dialog(Alert.AlertType.ERROR, "Inscriptin échoué");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            String sql1 = "Insert INTO Annee (Code,Year,Etat) VALUES (?,?,?)";
            pst1 = Con.prepareStatement(sql1);
            int lastYear = Integer.valueOf(getYear()) - 1;
            System.out.println(lastYear);
            pst1.setString(1, String.valueOf(lastYear));
            pst1.setString(2, String.valueOf(lastYear));
            pst1.setString(3, "Terminer");
            System.out.println("Année ajouteé");
            System.out.println(lastYear);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
