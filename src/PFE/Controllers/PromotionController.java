package PFE.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.sql.*;

import PFE.Config.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by Mohamed on 04/05/2017.
 */
public class PromotionController implements Initializable {
    public JFXComboBox CycleCombo;
    public JFXComboBox NiveauCombo;
    public JFXTextField nbr_etudtxt;
    public JFXButton BtnSuivant;
    public JFXComboBox OptionPromoCombo;
    public Hyperlink AddOptionBtn;
    public AnchorPane PaneData;
    Connection Con;
    ResultSet rs;
    PreparedStatement pst, pst1, pst2;
    Rectangle2D rec2;
    Double w, h;
    Stage stage;
    ObservableList<String> OptionList;
    ObservableList<String> CycleList = FXCollections.observableArrayList("Cycle Préparatoire", "Cycle Supérieur");
    ObservableList<String> NiveauCPIList = FXCollections.observableArrayList("Deuxieme année Cycle Préparatoire");
    ObservableList<String> NiveauCSList = FXCollections.observableArrayList("Premiére Année Cycle Supérieur", "Deuxiéme Année Cycle Supérieur", "Troisiéme Année Cycle Supérieur");
    ObservableList<String> year;
    public String CodeAnnee;
    Config2 Conf = new Config2();
    @FXML
    private Label lblClose;
    public static String ID_Option;


    public void setCodeAnnee(String a) {
        CodeAnnee = a;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        Platform.runLater(() -> {
            lblClose.setOnMouseClicked((MouseEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        CycleCombo.setItems(CycleList);
        NiveauCombo.setItems(NiveauCPIList);
        OptionPromoCombo.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT ID FROM Options_Table WHERE Designation_Option='" + OptionPromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs.next()) {
                    ID_Option = rs.getString("ID");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        NiveauCombo.setOnAction(event -> {
            if (NiveauCombo.getValue().equals("Deuxieme année Cycle Préparatoire")) {
                LoadComboOption("Deuxieme année Cycle Préparatoire");
            } else if (NiveauCombo.getValue().equals("Premiére Année Cycle Supérieur")) {
                LoadComboOption("Premiére Année Cycle Supérieur");
            } else if (NiveauCombo.getValue().equals("Deuxiéme Année Cycle Supérieur")) {
                LoadComboOption("Deuxiéme Année Cycle Supérieur");
            } else if (NiveauCombo.getValue().equals("Troisiéme Année Cycle Supérieur")) {
                LoadComboOption("Troisiéme Année Cycle Supérieur");
            }

        });

    }

    @FXML
    private void NiveauChoice() {
        if (CycleCombo.getValue().equals("Cycle Préparatoire")) {
            NiveauCombo.setValue("Deuxieme année Cycle Préparatoire");
            NiveauCombo.setItems(NiveauCPIList);
        } else if (CycleCombo.getValue().equals("Cycle Supérieur")) {
            NiveauCombo.setValue("Premiére Année Cycle Supérieur");
            NiveauCombo.setItems(NiveauCSList);
        }
    }

    public void LoadComboOption(String x) {
        try {
            OptionList = FXCollections.observableArrayList();

            rs = Con.createStatement().executeQuery("SELECT Designation_Option FROM Options_Table WHERE Niveau ='" + x + "'");
            while (rs.next()) {
                OptionList.add(rs.getString("Designation_Option"));
            }
            OptionPromoCombo.setItems(null);
            OptionPromoCombo.setItems(OptionList);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    @FXML
    public void Save(ActionEvent actionEvent) {
        try {

            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            String yearInString = String.valueOf(year);
            String yearpasse = String.valueOf(year - 1);
            String querry = "INSERT INTO Promotion(Designation,Nombre_etudiants,Cycle,Niveau,Annee_Code,Option_Code) VALUES(?,?,?,?,?,?)";
            pst = Con.prepareStatement(querry);
            System.out.println(NiveauCombo.getSelectionModel().getSelectedItem().toString() + " " + ID_Option + " " + yearInString + "/" + yearpasse);
            pst.setString(1, NiveauCombo.getSelectionModel().getSelectedItem().toString() + " " + yearInString + "/" + yearpasse);
            pst.setString(2, nbr_etudtxt.getText());
            pst.setString(3, CycleCombo.getSelectionModel().getSelectedItem().toString());
            pst.setString(4, NiveauCombo.getSelectionModel().getSelectedItem().toString());
            pst.setString(5, yearInString + "/" + yearpasse);
            pst.setString(6, ID_Option);
            pst.execute();
            Conf.newStage(stage, lblClose, "/PFE/View/SuivantPromo.fxml", "Test App", true, StageStyle.UNDECORATED, false);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddOption(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/Option.fxml");
    }
}
