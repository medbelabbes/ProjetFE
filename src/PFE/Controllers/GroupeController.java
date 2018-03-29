package PFE.Controllers;

import PFE.Config.Config2;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 10/05/2017.
 */
public class GroupeController implements Initializable {
    @FXML
    public JFXTextField CodeGroupeTXT;
    @FXML
    public JFXTextField NbrEtdGroup;

    @FXML
    public JFXButton AddGroupeBtn;
    public AnchorPane Pane;
    public JFXButton AnnulerBtn;
    public JFXTextField DesignationEtdGroup;

    Connection Con;
    PreparedStatement pst;
    ResultSet rs;
    Config2 Conf = new Config2();
    Section_GroupeController SG = new Section_GroupeController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
    }

    public void AddGroupe(ActionEvent actionEvent) {
        try {

            String query = "INSERT INTO Groupe(Designation,Nombre_Etudiants,Section_Code) VALUES(?,?,?)";
            pst = Con.prepareStatement(query);
            pst.setString(1, DesignationEtdGroup.getText());
            pst.setString(2, NbrEtdGroup.getText());
            pst.setString(3, String.valueOf(SG.getID_Section()));
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Groupe Ajouté");
            SG.LoadDataTableGroupe();

            ((Stage) Pane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void Annuler(ActionEvent actionEvent) {
        ((Stage) Pane.getScene().getWindow()).close();
    }
}
