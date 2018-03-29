package PFE.Controllers;

import PFE.Animations.FadeInLeftTransition;
import PFE.Animations.FadeInRightTransition;
import PFE.Model.Etudiant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import PFE.Config.Config2;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.application.Platform;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;


public class Login implements Initializable {
    public JFXTextField Username;
    public JFXPasswordField Password;
    public JFXButton Login;
    Connection Con, Con1, Con2;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1;
    Stage stage;
    Config2 c = new Config2();
    MainController Mc = new MainController();


    @FXML
    private Label lblClose;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new FadeInLeftTransition(Username).play();
        new FadeInRightTransition(Password).play();
        new FadeInLeftTransition(Login).play();
        Con = JavaConnection.ConnectDB();


        Platform.runLater(() -> {
            lblClose.setOnMouseClicked((MouseEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
    }

    @FXML
    public void login(ActionEvent actionEvent) {

        String sql = "SELECT * FROM Adminstrateur WHERE Login=? and Password=?";
        try {
            pst = Con.prepareStatement(sql);
            pst.setString(1, Username.getText());
            pst.setString(2, Password.getText());
            rs = pst.executeQuery();
            if (rs.next()) {

                System.out.println("login :D ");

                String query = "select * from Annee where Etat='Ne pas terminer'";
                pst1 = Con.prepareStatement(query);
                rs1 = pst1.executeQuery();
                if (rs1.next()) {
                    System.out.println("1111");
                    c.newStage(stage, lblClose, "/PFE/View/Main.fxml", "Test App", true, StageStyle.UNDECORATED, false);
                } else if (!(rs1.next())) {

                    String q = "select * from Annee where Etat='Terminer'";
                    pst1 = Con.prepareStatement(q);
                    rs1 = pst1.executeQuery();
                    if (rs1.next()) {
                        System.out.println("2222");
                        System.out.println("3333");
                        try {
                            String qr = "INSERT into Annee values(?,?,?)";
                            Calendar now = Calendar.getInstance();
                            int year = now.get(Calendar.YEAR);
                            String yearInString = String.valueOf(year);
                            String yearpasse = String.valueOf(year - 1);
                            pst2 = Con.prepareStatement(qr);
                            pst2.setString(1, yearInString + "/" + yearpasse);
                            pst2.setString(2, yearInString + "/" + yearpasse);
                            pst2.setString(3, "Ne pas terminer");
                            pst2.execute();
                            System.out.println("year added");
                            c.newStage(stage, lblClose, "/PFE/View/Promotion.fxml", "Test App", true, StageStyle.UNDECORATED, false);
                        } catch (Exception e1) {
                            System.out.println(e1 + "1");
                        }
                    }

                } else {

                    try {
                        String qr1 = "INSERT into Annee values(?,?,?)";
                        Calendar now = Calendar.getInstance();
                        int year = now.get(Calendar.YEAR);
                        String yearInString = String.valueOf(year);
                        String yearpasse = String.valueOf(year - 1);
                        pst2 = Con.prepareStatement(qr1);
                        pst2.setString(1, yearpasse + "/" + yearInString);
                        pst2.setString(2, yearpasse + "/" + yearInString);
                        pst2.setString(3, "Ne pas terminer");
                        pst2.execute();
                        System.out.println("year added");
                        c.newStage(stage, lblClose, "/PFE/View/Promotion.fxml", "Test App", true, StageStyle.UNDECORATED, false);
                        System.out.println("666");
                    } catch (Exception e1) {
                        System.out.println(e1 + "2");
                    }

                }


            } else {
                System.out.println("no Login");
                c.dialog(Alert.AlertType.ERROR, "Error Login, Please Chek Username And Password");


            }
        } catch (Exception e2) {
            System.out.println(e2 + "2");

        }
    }

    public String getYear() {
        Calendar now = Calendar.getInstance();
        DateFormat date = new SimpleDateFormat("yyyy");
        String year = date.format(now);
        return year;
    }

}
