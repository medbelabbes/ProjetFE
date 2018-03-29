package PFE.Controllers;

import PFE.Config.Config2;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 08/06/2017.
 */
public class OptionPromotionController implements Initializable {
    public AnchorPane PaneData;
    public JFXTextField DesignationOptionTxt;
    public JFXComboBox NiveauCombo;
    public JFXButton ConfimerBtn;
    public JFXComboBox CycleCombo;
    Connection Con;
    PreparedStatement pst;
    ResultSet rs;
    Config2 Conf = new Config2();
    ObservableList<String> CycleList = FXCollections.observableArrayList("Cycle Préparatoire", "Cycle Supérieur");
    ObservableList<String> NiveauCPIList = FXCollections.observableArrayList("Deuxieme année Cycle Préparatoire");
    ObservableList<String> NiveauCSList = FXCollections.observableArrayList("Premiére Année Cycle Supérieur", "Deuxiéme Année Cycle Supérieur", "Troisiéme Année Cycle Supérieur");
GestionScolaritéController GS= new GestionScolaritéController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        CycleCombo.setItems(CycleList);
        NiveauCombo.setItems(NiveauCPIList);
    }

    public void AddOption(ActionEvent actionEvent) {
        try {
            String query = "INSERT INTO Options_Table(Designation_Option,Niveau) VALUES (?,?)";
            pst = Con.prepareStatement(query);
            pst.setString(1, DesignationOptionTxt.getText());
            pst.setString(2, NiveauCombo.getSelectionModel().getSelectedItem().toString());
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Option Ajouté");
            GS.LoadDataComboOption();
            ((Stage) PaneData.getScene().getWindow()).close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void NiveauChoice(ActionEvent actionEvent) {
        if (CycleCombo.getValue().equals("Cycle Préparatoire")) {
            NiveauCombo.setValue("Deuxieme année Cycle Préparatoire");
            NiveauCombo.setItems(NiveauCPIList);
        } else if (CycleCombo.getValue().equals("Cycle Supérieur")) {
            NiveauCombo.setValue("Premiére Année Cycle Supérieur");
            NiveauCombo.setItems(NiveauCSList);

        }
    }

    public void Annuler(ActionEvent actionEvent) {
        ((Stage) PaneData.getScene().getWindow()).close();
    }
}
