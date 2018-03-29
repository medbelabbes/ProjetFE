package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Jury;
import PFE.Model.Enseignant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;


public class GestionJuryController implements Initializable {

    public static String MatriculeETD;
    @FXML
    private AnchorPane PaneData;

    @FXML
    private TableView<Jury> JuryTable;

    @FXML
    private TableColumn<Jury, String> NumeroJuryCol;

    @FXML
    private TableColumn<Jury, String> CodeSoutenanceCol;


    @FXML
    private JFXTextField MatriculeJury;

    @FXML
    private JFXButton AddJuryBtn;

    @FXML
    private TableView<Enseignant> TableEnseignant;

    @FXML
    private TableColumn<Enseignant, String> MatriculEnsCol;

    @FXML
    private TableColumn<Enseignant, String> NomEnsCol;

    @FXML
    private TableColumn<Enseignant, String> PrenomEnsCol;

    @FXML
    private TableColumn<Enseignant, String> EmailEnsCol;

    @FXML
    private TableColumn<Enseignant, String> SpécialitéEnsCol;

    @FXML
    private TableColumn<Enseignant, String> GradensCol;

    @FXML
    private TableView<Enseignant> TableEnsJury;

    @FXML
    private TableColumn<Enseignant, String> MatriculeEnsJuryCol;

    @FXML
    private TableColumn<Enseignant, String> NomEnsJuryCol;

    @FXML
    private TableColumn<Enseignant, String> PrenomEnsJuryCol;

    @FXML
    private TableColumn<Enseignant, String> EmailEnsJuryCol;

    @FXML
    private TableColumn<Enseignant, String> SpécialitéEnsJuryCol;

    @FXML
    private TableColumn<Enseignant, String> GradensJuryCol;
    @FXML
    private TableColumn<Enseignant, String> NJEnsJuryCol;

    @FXML
    private JFXTextField ChercherEnseignant;



    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton AffecterBtn;
    public static String nometd;
    public static String prenometd;
    public static String N_Jury;
    public static String code_soutenance;
    public boolean suppr;
    Config2 Conf = new Config2();
    Stage stage;
    Rectangle2D rec2;
    private ObservableList<String> PromoList;
    private ObservableList<Jury> JuryList;
    private ObservableList<Enseignant> EnseignantList;
    private ObservableList<Enseignant> EnseignantJuryList;
    Connection Con, Con1, Con2;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        rec2 = Screen.getPrimary().getVisualBounds();
        NumeroJuryCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        // CodeSoutenanceCol.setCellValueFactory(new PropertyValueFactory<>("Code_Soutenance"));

        MatriculEnsCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEnseignant"));
        NomEnsCol.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        PrenomEnsCol.setCellValueFactory(new PropertyValueFactory<>("PrénomEnseignant"));
        EmailEnsCol.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        SpécialitéEnsCol.setCellValueFactory(new PropertyValueFactory<>("SpécialitéEnseignant"));
        GradensCol.setCellValueFactory(new PropertyValueFactory<>("GradeEnseignant"));

        MatriculeEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEnseignant"));
        NomEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        PrenomEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("PrénomEnseignant"));
        //EmailEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        SpécialitéEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("SpécialitéEnseignant"));
        GradensJuryCol.setCellValueFactory(new PropertyValueFactory<>("GradeEnseignant"));
        // NJEnsJuryCol.setCellValueFactory(new PropertyValueFactory<>("NuméroJuryEnseignant"));





        LoadDataTableJury();
        LoadDataTableEnseignant();
        LoadDataTableEnseignantJury();
    }



    public void LoadDataTableJury() {
        try {
            JuryList = FXCollections.observableArrayList();
            String qr = "SELECT * FROM Jury ORDER BY Numero ";
            pst1 = Con.prepareStatement(qr);
            rs1 = pst1.executeQuery();
            while (rs1.next()) {
                JuryList.add(new Jury(rs1.getString(1)));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        JuryTable.setItems(null);
        JuryTable.setItems(JuryList);
    }

    public void LoadDataTableEnseignant() {
        String query1;

        try {
            EnseignantList = FXCollections.observableArrayList();
            query1 = "SELECT * From Enseignant ";
            pst = Con.prepareStatement(query1);
            rs = pst.executeQuery();

            while (rs.next()) {
                EnseignantList.add(new Enseignant(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                ));
            }
            //  }
            //}
        } catch (Exception ex) {
            System.out.println(ex + " Exception populate data students to TableView");
        }
        TableEnseignant.setItems(null);
        TableEnseignant.setItems(EnseignantList);
    }

    public void LoadDataTableEnseignantJury() {

        try {
            EnseignantJuryList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM enseignant_has_jury where  Jury_Numero='" + N_Jury + "' ");
            while (rs.next()) {
                rs1 = Con.createStatement().executeQuery("SELECT * From Enseignant  where  Matricule='" + rs.getString("Enseignant_Matricule") + "' ");
                while (rs1.next()) {
                    EnseignantJuryList.add(new Enseignant(rs1.getString(1),
                            rs1.getString(2),
                            rs1.getString(3),
                            rs1.getString(4),
                            rs1.getDate(5),
                            rs1.getString(6),
                            rs1.getString(7),
                            rs1.getString(8),
                            rs1.getString(9),
                            rs1.getString(10),
                            rs1.getString(11),
                            rs1.getString(12)
                    ));
                    TableEnsJury.setItems(null);
                    TableEnsJury.setItems(EnseignantJuryList);
                    if (rs == null) {
                        TableEnsJury.setItems(null);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void LoadData(ActionEvent actionEvent) {

        LoadDataTableJury();
        LoadDataTableEnseignant();

    }

    public void AddJury(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment  ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {

                String query = "INSERT INTO Jury(Numero) VALUES(?)";
                pst = Con.prepareStatement(query);
                pst.setString(1, MatriculeJury.getText());

                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "jury créee");


            } catch (Exception e) {
                System.out.println(e);
            }
        }
        LoadDataTableJury();
    }


    public void ClickTableJuryItem1(MouseEvent mouseEvent) {
        try {
            Jury e = JuryTable.getSelectionModel().getSelectedItem();
            MatriculeJury.setText(String.valueOf(e.getNuméro()));
            // code_soutenance =e.getCode_Soutenance();
            N_Jury = e.getNuméro();
            suppr = false;
            LoadDataTableEnseignantJury();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void ClickTableJuryItem3(MouseEvent mouseEvent) {

        try {
            Enseignant en = TableEnsJury.getSelectionModel().getSelectedItem();
            MatriculeETD = en.getMatriculeEnseignant();
            nometd = en.getNomEnseignant();
            prenometd = en.getPrénomEnseignant();
            suppr = true;
            LoadDataTableEnseignantJury();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ClickTableJuryItem2(MouseEvent mouseEvent) {
        try {
            Enseignant et = TableEnseignant.getSelectionModel().getSelectedItem();
            MatriculeETD = et.getMatriculeEnseignant();
            nometd = et.getNomEnseignant();
            //prenometd = et.getNuméroJuryEnseignant();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Affecter(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Affecter l'enseignant " + prenometd + " " + nometd + " " + " à Jury :" + N_Jury + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                String sql = "INSERT INTO  enseignant_has_jury (Jury_Numero,Enseignant_Matricule) VALUE (?,?) ";
                pst = Con.prepareStatement(sql);
                pst.setString(1, N_Jury);
                pst.setString(2, MatriculeETD);
                // pst.setString(3, code_soutenance);
                pst.execute();

                Conf.dialog(Alert.AlertType.INFORMATION, "L'enseignant: " + prenometd + " " + nometd + " est affecté à Jury: " + N_Jury);
                LoadDataTableJury();
                LoadDataTableEnseignant();
                LoadDataTableEnseignantJury();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void Delete(ActionEvent actionEvent) {
        if (suppr == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer Jury ");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == ButtonType.OK) {
                try {
                    String sql4 = "DELETE FROM enseignant_has_jury where Jury_Numero=?";
                    pst = Con.prepareStatement(sql4);
                    pst.setString(1, N_Jury);
                    pst.execute();
                    LoadDataTableEnseignantJury();
                    LoadDataTableJury();
                    String sql3 = "DELETE FROM jury where Numero=? ";
                    pst2 = Con.prepareStatement(sql3);
                    pst2.setString(1, N_Jury);
                    pst2.execute();


                    Conf.dialog(Alert.AlertType.INFORMATION, "L'enseignant: " + prenometd + " " + nometd + " est supprimé de Jury: " + N_Jury);
                } catch (Exception e) {
                    System.out.println(e + " Exception deleteJury");
                }
                LoadDataTableEnseignantJury();
                LoadDataTableJury();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer l'enseignant ");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == ButtonType.OK) {
                try {
                    String sql4 = "DELETE FROM enseignant_has_jury where Jury_Numero=? and Enseignant_Matricule=?";
                    pst = Con.prepareStatement(sql4);
                    pst.setString(1, N_Jury);
                    pst.setString(2, MatriculeETD);
                    pst.execute();
                    LoadDataTableEnseignantJury();
                    LoadDataTableJury();
                    Conf.dialog(Alert.AlertType.INFORMATION, "L'enseignant: " + prenometd + " " + nometd + " est supprimé de Jury: " + N_Jury);
                } catch (Exception e) {
                    System.out.println(e + " Exception deleteEnseJ");
                }
            }

        }
    }


}