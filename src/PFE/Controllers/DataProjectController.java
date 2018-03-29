package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Projet;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.sql.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 19/05/2017.
 */
public class DataProjectController implements Initializable {

    @FXML
    private AnchorPane PaneData;

    @FXML
    private Button BtnBack;

    @FXML
    private TextField DesignationProjetTxt;

    @FXML
    private TextField SpecialiteProjetTxt;

    @FXML
    private TextArea ResumeProjetTxt;

    @FXML
    private TextArea TechnoProjetTxt;

    @FXML
    private TextArea OutiliProjetTxt;

    @FXML
    private TextArea PrerequisProjetTxt;

    @FXML
    private TextArea PlanProjetTxt;

    @FXML
    private TextField DureeProjetTxt;

    @FXML
    private TextField NbrProjetTxt;

    @FXML
    private TextField PromoProjetTxt;
    public static String Promo;
    public static String annee;
    public static String x;
    public static int CodeProjet;
    Connection Con;
    ResultSet rs;
    PreparedStatement pst, pst1;
    Config2 Conf = new Config2();
    ProjectController PC = new ProjectController();
    private ObservableList<String> anneeList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        CodeProjet = PC.getCodeprj();
        DesignationProjetTxt.setText(PC.getDesignationprj());
        SpecialiteProjetTxt.setText(PC.getSpeciprj());
        ResumeProjetTxt.setText(PC.getResumeprj());
        TechnoProjetTxt.setText(PC.getTechnoprj());
        OutiliProjetTxt.setText(PC.getOutilprj());
        PrerequisProjetTxt.setText(PC.getPreriquisprj());
        PlanProjetTxt.setText(PC.getPlanprj());
        DureeProjetTxt.setText(PC.getDureeprj());
        NbrProjetTxt.setText(String.valueOf(PC.getNbrprj()));
        PromoProjetTxt.setText(String.valueOf(PC.getPromocodeprj()));
        DesignationProjetTxt.setEditable(false);
        SpecialiteProjetTxt.setEditable(false);
        ResumeProjetTxt.setEditable(false);
        TechnoProjetTxt.setEditable(false);
        OutiliProjetTxt.setEditable(false);
        PrerequisProjetTxt.setEditable(false);
        PlanProjetTxt.setEditable(false);
        DureeProjetTxt.setEditable(false);
        NbrProjetTxt.setEditable(false);
        PromoProjetTxt.setEditable(false);
        Promo = PromoProjetTxt.getText();
    }

    public void Back(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/Projets.fxml");

        DesignationProjetTxt.setText(null);
        SpecialiteProjetTxt.setText(null);
        ResumeProjetTxt.setText(null);
        TechnoProjetTxt.setText(null);
        OutiliProjetTxt.setText(null);
        PrerequisProjetTxt.setText(null);
        PlanProjetTxt.setText(null);
        DureeProjetTxt.setText(null);
        NbrProjetTxt.setText(null);
        PromoProjetTxt.setText(null);

        PC.setCodeprj(0);
        PC.setDesignationprj(null);
        PC.setSpeciprj(null);
        PC.setResumeprj(null);
        PC.setTechnoprj(null);
        PC.setPreriquisprj(null);
        PC.setPlanprj(null);
        PC.setDureeprj(null);
        PC.setOutilprj(null);
        PC.setPromocodeprj(0);


    }

    public void ValiderProjet(ActionEvent actionEvent) {
        try {
            String query = "Select * From Projet WHERE Code='" +CodeProjet + "'";
            rs = Con.createStatement().executeQuery(query);
            if (rs.next()) {
                if ((rs.getString("Validation")).equals("Non")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiement Valider le projet " + DesignationProjetTxt.getText() + " ?");
                    alert.initStyle(StageStyle.UTILITY);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        try {
                            String sql = "UPDATE Projet set Validation ='Oui' WHERE Code='" + PC.getCodeprj() + "'";
                            pst = Con.prepareStatement(sql);
                            pst.execute();
                            Conf.dialog(Alert.AlertType.INFORMATION, "Projet validé");
                            Conf.loadAnchorPane(PaneData, "/PFE/View/Projets.fxml");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                } else {
                    Conf.dialog(Alert.AlertType.INFORMATION, "Ce projet est déja validé");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String ShowMaxYear() {
        String year = null;
        try {
            rs = Con.createStatement().executeQuery("SELECT max(Year) FROM Annee ");
            if (rs.next()) {
                year = rs.getString("max(Year)");
                System.out.println(year);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return year;
    }

    public static String getPromo() {
        return Promo;
    }

    public static String getAnnee() {
        return annee;
    }
}
