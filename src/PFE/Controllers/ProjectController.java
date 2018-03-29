package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 19/05/2017.
 */
public class ProjectController implements Initializable {
    @FXML
    private AnchorPane PaneData;

    @FXML
    private JFXComboBox<String> AnneeCombo;

    @FXML
    private JFXComboBox<String> PromoCombo;

    @FXML
    private TableView<Projet> ProjectTable;

    @FXML
    private TableColumn<Projet, String> CodeProjectCol;

    @FXML
    private TableColumn<Projet, String> DesignationProjectCol;

    @FXML
    private TableColumn<Projet, String> SpecialiteProjectCol;

    @FXML
    private TableColumn<Projet, String> ReseumeProjectCol;

    @FXML
    private TableColumn<Projet, String> TechnoProjectCol;

    @FXML
    private TableColumn<Projet, String> OutilProjectCol;

    @FXML
    private TableColumn<Projet, String> PrerequisProjectCol;

    @FXML
    private TableColumn<Projet, String> PlanProjectCol;

    @FXML
    private TableColumn<Projet, String> DureeProjectCol;

    @FXML
    private TableColumn<Projet, String> NbrEquipeProjectCol;

    @FXML
    private TableColumn<Projet, String> CodePromoProjectCol;

    @FXML
    private TableColumn<Projet, String> ValidationProjectCol;

    @FXML
    private JFXTextField ChercheBtn;

    @FXML
    private JFXButton AfficherProjetBtn;

    public static int Codeprj;
    public static String designationprj;
    public static String speciprj;
    public static String resumeprj;
    public static String technoprj;
    public static String preriquisprj;
    public static String planprj;
    public static String dureeprj;
    public static String outilprj;
    public static int nbrprj;
    public static int promocodeprj;
    public static String validprj;
    public static String ID_Promo;

    Connection Con;
    ResultSet rs, rs3;
    PreparedStatement pst;
    Config2 Conf = new Config2();
    Stage stage;
    Rectangle2D rec2;
    private ObservableList<String> PromoList;
    private ObservableList<String> AnneeList;
    private ObservableList<Projet> ProjetList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        LoadDataComboAnnee();
        initCol();
        CodeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DesignationProjectCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
        SpecialiteProjectCol.setCellValueFactory(new PropertyValueFactory<>("Specialite"));
        ReseumeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Resume"));
        TechnoProjectCol.setCellValueFactory(new PropertyValueFactory<>("Technologie"));
        OutilProjectCol.setCellValueFactory(new PropertyValueFactory<>("Outils"));
        PrerequisProjectCol.setCellValueFactory(new PropertyValueFactory<>("Prerequis"));
        PlanProjectCol.setCellValueFactory(new PropertyValueFactory<>("Plan_travail"));
        DureeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        NbrEquipeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Nombre_Equipe"));
        CodePromoProjectCol.setCellValueFactory(new PropertyValueFactory<>("PromoCode"));
        ValidationProjectCol.setCellValueFactory(new PropertyValueFactory<>("Validation"));
        PromoCombo.setOnAction(event -> {
            try {


                rs = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs.next()) {
                    ID_Promo = rs.getString("ID");
                    initCol();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            Update_table();
        });


    }

    public void LoadDataComboAnnee() {
        try {
            AnneeList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Code FROM Annee ");
            while (rs.next()) {
                AnneeList.add(rs.getString("Code"));

            }
            AnneeCombo.setItems(AnneeList);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataComboPromo(ActionEvent actionEvent) {
        try {
            PromoList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Designation FROM Promotion WHERE Annee_Code='" + AnneeCombo.getSelectionModel().getSelectedItem().toString() + "' OR Annee_Code='" + GetMaxYear() + "') ");
            while (rs.next()) {
                PromoList.add(rs.getString("Designation"));
            }
            PromoCombo.setItems(PromoList);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String GetMaxYear() {
        String x = null;
        try {
            rs3 = Con.createStatement().executeQuery("SELECT Max(Year) FROM Annee ");
            if (rs3.next()) {
                x = rs3.getString("Max(Year)");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(x);
        return x;
    }

    public void Update_table() {
        try {
            ProjetList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Projet where Promotion_Code='" + ID_Promo + "'");
            while (rs.next()) {
                ProjetList.add(new Projet(
                        Integer.valueOf(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        Integer.valueOf(rs.getString(10)),
                        Integer.valueOf(rs.getString(11)),
                        rs.getString(12)));
            }
            ProjectTable.setItems(null);
            ProjectTable.setItems(ProjetList);
        } catch (Exception ex) {
            System.out.println(ex + " Updata table");
        }


    }

    private void initCol() {
        CodeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DesignationProjectCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
        SpecialiteProjectCol.setCellValueFactory(new PropertyValueFactory<>("Specialite"));
        ReseumeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Resume"));
        TechnoProjectCol.setCellValueFactory(new PropertyValueFactory<>("Technologie"));
        OutilProjectCol.setCellValueFactory(new PropertyValueFactory<>("Outils"));
        PrerequisProjectCol.setCellValueFactory(new PropertyValueFactory<>("Prerequis"));
        PlanProjectCol.setCellValueFactory(new PropertyValueFactory<>("Plan_travail"));
        DureeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        NbrEquipeProjectCol.setCellValueFactory(new PropertyValueFactory<>("Nombre_Equipe"));
        CodePromoProjectCol.setCellValueFactory(new PropertyValueFactory<>("PromoCode"));
        ValidationProjectCol.setCellValueFactory(new PropertyValueFactory<>("Validation"));

        ValidationProjectCol.setCellFactory(column -> {
            return new TableCell<Projet, String>() {

                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");

                    } else if (item.equals("")) {

                        setStyle("-fx-background-color: #f44336 ");
                        setText("Non");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    } else {
                        setStyle("-fx-background-color: #26a69a");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

    }

    public void LoadDataTable(ActionEvent actionEvent) {
        Update_table();
    }

    public void ClickTable(MouseEvent mouseEvent) {
        try {
            Projet projet = ProjectTable.getSelectionModel().getSelectedItem();
            Codeprj = projet.getCode();
            designationprj = projet.getDesignation();
            speciprj = projet.getSpecialite();
            resumeprj = projet.getResume();
            technoprj = projet.getTechnologie();
            preriquisprj = projet.getPrerequis();
            planprj = projet.getPlan_travail();
            dureeprj = projet.getDuree();
            outilprj = projet.getOutils();
            nbrprj = projet.getNombre_Equipe();
            promocodeprj = projet.getPromoCode();
            validprj = projet.getValidation();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static int getCodeprj() {
        return Codeprj;
    }

    public static String getDesignationprj() {
        return designationprj;
    }

    public static String getSpeciprj() {
        return speciprj;
    }

    public static String getResumeprj() {
        return resumeprj;
    }

    public static String getTechnoprj() {
        return technoprj;
    }

    public static String getPreriquisprj() {
        return preriquisprj;
    }

    public static String getPlanprj() {
        return planprj;
    }

    public static String getDureeprj() {
        return dureeprj;
    }

    public static String getOutilprj() {
        return outilprj;
    }

    public static int getNbrprj() {
        return nbrprj;
    }

    public static int getPromocodeprj() {
        return promocodeprj;
    }

    public static void setCodeprj(int codeprj) {
        Codeprj = codeprj;
    }

    public static void setDesignationprj(String designationprj) {
        ProjectController.designationprj = designationprj;
    }

    public static void setSpeciprj(String speciprj) {
        ProjectController.speciprj = speciprj;
    }

    public static void setResumeprj(String resumeprj) {
        ProjectController.resumeprj = resumeprj;
    }

    public static void setTechnoprj(String technoprj) {
        ProjectController.technoprj = technoprj;
    }

    public static void setPreriquisprj(String preriquisprj) {
        ProjectController.preriquisprj = preriquisprj;
    }

    public static void setPlanprj(String planprj) {
        ProjectController.planprj = planprj;
    }

    public static void setDureeprj(String dureeprj) {
        ProjectController.dureeprj = dureeprj;
    }

    public static void setOutilprj(String outilprj) {
        ProjectController.outilprj = outilprj;
    }

    public static void setNbrprj(int nbrprj) {
        ProjectController.nbrprj = nbrprj;
    }

    public static void setPromocodeprj(int promocodeprj) {
        ProjectController.promocodeprj = promocodeprj;
    }


    public void DesplayProject(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/DataProject.fxml");
    }

}
