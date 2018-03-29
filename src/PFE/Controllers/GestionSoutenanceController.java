package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class GestionSoutenanceController implements Initializable {


    @FXML
    private AnchorPane PaneData;

    @FXML
    private TableView<Soutenance> TableSoutenance;

    @FXML
    private TableColumn<Soutenance, String> Code_SoutenaceCol;

    @FXML
    private TableColumn<Soutenance, String> JourCol;

    @FXML
    private TableColumn<Soutenance, String> Heure_DCol;
    @FXML
    private TableColumn<Soutenance, String> Heure_FCol;
    @FXML
    private TableColumn<Soutenance, String> Equipe_NumeroCol;
    @FXML
    private TableColumn<Soutenance, String> Numero_SalleCol;

    @FXML
    private TableColumn<Soutenance, String> Jury_NumeroCol;
    @FXML
    private TableView<Enseignant> TableEenseigJury;

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

    public JFXTextField MatriculeSout;
    @FXML
    private JFXTimePicker Heure_FSout;

    @FXML
    private JFXButton AddSoutBtn;
    @FXML
    private JFXTextField DesignationSalle;

    @FXML
    private JFXComboBox<String> PromoCombo;

    @FXML
    private JFXButton Deletebtn;
    @FXML
    private JFXTextField CodeSout;
    @FXML
    private JFXTextField NumeroSalle;
    @FXML
    private JFXDatePicker JoureSout;
    @FXML
    private JFXTimePicker Heure_DSout;
    @FXML
    private JFXComboBox Equipe_Numero;
    @FXML
    private JFXComboBox promo_code;
    @FXML
    private JFXComboBox Salle_NumeroSout;
    @FXML
    private JFXButton AjouterSalleBtn1;


    public String JuryCode;
    public static String ID_Salle;
    public static String ID_promo;
    boolean bool;
    Config2 Conf = new Config2();
    Stage stage;
    Rectangle2D rec2;
    private ObservableList<String> JuryList;
    private ObservableList<String> equipeList;
    private ObservableList<String> salleList;
    private ObservableList<String> promoList;
    private ObservableList<Enseignant> EenseignantList;
    private ObservableList<Soutenance> SoutenanceList;
    Connection Con, Con1, Con2;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2, rs3;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        rec2 = Screen.getPrimary().getVisualBounds();

        //Heure_DSout.setIs24HourView(true);
        //  Heure_FSout.setIs24HourView(true);
        MatriculEnsCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEnseignant"));
        NomEnsCol.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        PrenomEnsCol.setCellValueFactory(new PropertyValueFactory<>("PrénomEnseignant"));
        EmailEnsCol.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        SpécialitéEnsCol.setCellValueFactory(new PropertyValueFactory<>("SpécialitéEnseignant"));
        GradensCol.setCellValueFactory(new PropertyValueFactory<>("GradeEnseignant"));

        Code_SoutenaceCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        JourCol.setCellValueFactory(new PropertyValueFactory<>("Jour"));
        Heure_DCol.setCellValueFactory(new PropertyValueFactory<>("Heure_D"));
        Heure_FCol.setCellValueFactory(new PropertyValueFactory<>("Heure_F"));
        Numero_SalleCol.setCellValueFactory(new PropertyValueFactory<>("Salle_Numero"));
        Equipe_NumeroCol.setCellValueFactory(new PropertyValueFactory<>("Equipe_Numero"));
        Jury_NumeroCol.setCellValueFactory(new PropertyValueFactory<>("Jury_Numero"));
        Salle_NumeroSout.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT Numero FROM salle where  Designation='" + Salle_NumeroSout.getSelectionModel().getSelectedItem().toString() + "' ");
                if (rs.next()) {
                    ID_Salle = rs.getString("Numero");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            LoadDataComboSalle_NumeroSout();

        });

        promo_code.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT ID FROM promotion where  Designation='" + promo_code.getSelectionModel().getSelectedItem().toString() + "' ");
                if (rs.next()) {
                    ID_promo = rs.getString("ID");
                    LoadDataComboEquipe_NumeroSout();
                    LoadDataTableSoutenance();
                    System.out.println(ID_promo);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        LoadDataComboJurys();
        //  LoadDataComboEquipe_NumeroSout();
        LoadDataComboSalle_NumeroSout();
        // LoadDataTableSoutenance();
        // LoadDataTableEnseignant();
        LoadDataComboPromo();

    }


    public void LoadDataComboJurys() {
        try {
            JuryList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM jury");
            while (rs.next()) {
                JuryList.add(rs.getString("Numero"));
            }
            PromoCombo.setItems(JuryList);

        } catch (Exception ex) {
            System.out.println(ex + " ComboJurys");
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

    public void LoadDataComboPromo() {
        try {
            promoList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM promotion WHERE Annee_Code='" + GetMaxYear() + "'");
            while (rs.next()) {
                promoList.add(rs.getString("Designation"));
            }
            promo_code.setItems(promoList);
            LoadDataComboEquipe_NumeroSout();
        } catch (Exception ex) {
            System.out.println(ex + " Combopromo");
        }
    }

    public void LoadDataComboEquipe_NumeroSout() {
        try {
            equipeList = FXCollections.observableArrayList();
            //rs1 = Con.createStatement().executeQuery("SELECT ID FROM promotion where Designation='" + promo_code.getSelectionModel().getSelectedItem().toString() + "' ");
            //while (rs1.next()) {
            rs = Con.createStatement().executeQuery("SELECT Numero FROM equipe where Promotion_Numero = '" + ID_promo + "'");
            while (rs.next()) {
                equipeList.add(rs.getString("Numero"));
            }//}
            Equipe_Numero.setItems(equipeList);

        } catch (Exception ex) {
            System.out.println(ex + " ComboEquipe_Numero");
        }
    }

    public void LoadDataComboSalle_NumeroSout() {
        try {
            salleList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM salle");
            while (rs.next()) {
                salleList.add(rs.getString("Designation"));
            }
            Salle_NumeroSout.setItems(salleList);

        } catch (Exception ex) {
            System.out.println(ex + " ComboSalle_NumeroSout");
        }
    }


    public void LoadDataTableSoutenance() {

        try {
            SoutenanceList = FXCollections.observableArrayList();
            //  rs2 = Con.createStatement().executeQuery("SELECT * FROM promotion where  ID ='" + ID_promo + "' ");
            //while (rs2.next()) {
            rs1 = Con.createStatement().executeQuery("SELECT Numero FROM equipe where  Promotion_Numero='" + ID_promo + "' ");
            while (rs1.next()) {
                rs = Con.createStatement().executeQuery("SELECT * From soutenance where Equipe_Numero ='" + rs1.getString("Numero") + "' ");
                while (rs.next()) {
                    SoutenanceList.add(new Soutenance(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                    ));
                }
            }
            //}

        } catch (Exception ex) {
            System.out.println(ex + "tablesouten");
        }
        TableSoutenance.setItems(null);
        TableSoutenance.setItems(SoutenanceList);
    }

    public void LoadDataTableEnseignant() {
        try {
            EenseignantList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM enseignant_has_jury where  Jury_Numero='" + JuryCode + "' ");
            while (rs.next()) {
                rs1 = Con.createStatement().executeQuery("SELECT * From Enseignant  where  Matricule='" + rs.getString("Enseignant_Matricule") + "' ");
                while (rs1.next()) {
                    EenseignantList.add(new Enseignant(
                            rs1.getString(1),
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
                    TableEenseigJury.setItems(null);
                    TableEenseigJury.setItems(EenseignantList);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex + "aaa");
        }

    }

    public int timeString(String time) {
        int minutes = 0;
        String[] split = time.split(":");
        if (split.length == 2) {
            minutes = (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(split[0])) +
                    Integer.parseInt(split[1]));
            //  System.out.println(minutes);
        }
        return minutes;
    }


    public void LoadData(ActionEvent actionEvent) {

        LoadDataTableSoutenance();
        LoadDataComboJurys();
        LoadDataComboEquipe_NumeroSout();
        LoadDataComboSalle_NumeroSout();
        LoadDataTableEnseignant();

    }

    public void Insert() {


        try {
            String query = "INSERT INTO soutenance(Code,Jour,Heure_Debut,Heur_Fin,Salle_Numero,Equipe_Numero,Jury_Numero) VALUES(?,?,?,?,?,?,?)";
            pst = Con.prepareStatement(query);
            pst.setString(1, CodeSout.getText());
            pst.setString(2, JoureSout.getValue().toString());
            pst.setString(3, Heure_DSout.getValue().toString());
            pst.setString(4, Heure_FSout.getValue().toString());
            pst.setString(5, ID_Salle);
            pst.setString(6, Equipe_Numero.getSelectionModel().getSelectedItem().toString());
            pst.setString(7, PromoCombo.getSelectionModel().getSelectedItem().toString());
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Soutenance créee");
        } catch (Exception e) {
            System.out.println(e + "insert");
        }
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public void AddSout(ActionEvent actionEvent) {
        boolean a = true;
        boolean b = true;
        boolean c = true;
        boolean d = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment ....... ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) try {

            int F = timeString(Heure_FSout.getValue().toString());
            int D = timeString(Heure_DSout.getValue().toString());

            if ((D >= F || D + 60 > F)) {
                Conf.dialog(Alert.AlertType.INFORMATION, "erreur A vérifier l'heure de debut et de fin soutenance");
            } else {
                rs = Con.createStatement().executeQuery("SELECT * From soutenance ");
                while (rs.next()) {
                    if (JoureSout.getValue().toString().equals(rs.getString("Jour"))) {
                        if ((Integer.valueOf(D) < Integer.valueOf(timeString(rs.getString("Heur_Fin"))) && Integer.valueOf(D) > Integer.valueOf(timeString(rs.getString("Heure_Debut")))) || (Integer.valueOf(F) > Integer.valueOf(timeString(rs.getString("Heure_Debut"))) && Integer.valueOf(F) < Integer.valueOf(timeString(rs.getString("Heur_Fin"))))) {
                            if ((Integer.valueOf(ID_Salle) == Integer.valueOf(rs.getString("Salle_Numero")))) {
                                Conf.dialog(Alert.AlertType.INFORMATION, "Salle meme impossible");
                                c = false;
                                break;

                            } else {
                                if (Integer.valueOf(PromoCombo.getSelectionModel().getSelectedItem().toString()) == Integer.valueOf(rs.getString("Jury_Numero"))) {
                                    Conf.dialog(Alert.AlertType.INFORMATION, "jury meme");
                                    c = false;
                                    break;
                                } else {
                                    rs2 = Con.createStatement().executeQuery("SELECT * From enseignant_has_jury where Jury_Numero= '" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                                    while (rs2.next() && (d)) {
                                        rs3 = Con.createStatement().executeQuery("SELECT * From enseignant_has_jury where Jury_Numero= '" + rs.getString("Jury_Numero") + "'");
                                        while (rs3.next() && (a)) {
                                            if (Integer.valueOf(rs2.getString("Enseignant_Matricule")) == Integer.valueOf(rs3.getString("Enseignant_Matricule"))) {
                                                Conf.dialog(Alert.AlertType.INFORMATION, "enseig jury impossible");
                                                a = false;
                                                c = false;
                                            }
                                        }
                                        d = a;
                                    }
                                    if (d) {
                                        Conf.dialog(Alert.AlertType.INFORMATION, " okkkk");
                                        a = false;
                                        c = false;
                                    }
                                }
                            }
                        } else if (!((Integer.valueOf(D) < Integer.valueOf(timeString(rs.getString("Heur_Fin"))) && Integer.valueOf(D) > Integer.valueOf(timeString(rs.getString("Heure_Debut"))))) || (!(Integer.valueOf(F) > Integer.valueOf(timeString(rs.getString("Heure_Debut"))) && Integer.valueOf(F) < Integer.valueOf(timeString(rs.getString("Heur_Fin")))))) {
                            Conf.dialog(Alert.AlertType.INFORMATION, " ok inclu");
                            a = false;
                            c = false;
                        }
                    }

                }
                if (c) {
                    Conf.dialog(Alert.AlertType.INFORMATION, "ok Jour");
                }

            }
        } catch (Exception e) {
            System.out.println(e + "bbbb");
        }
        LoadDataTableSoutenance();
    }

    public void AddSalle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment  ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {

                String query = "INSERT INTO salle(Designation) VALUES(?)";
                pst = Con.prepareStatement(query);

                pst.setString(1, DesignationSalle.getText());
                pst.execute();
                // Conf.dialog(Alert.AlertType.INFORMATION, "Salle créee");

                Conf.dialog(Alert.AlertType.INFORMATION, "Salle créee");
                LoadDataComboSalle_NumeroSout();
            } catch (Exception e) {
                System.out.println(e + "add salle");
            }
        }

    }

    public void ClickTableSoutItem2(MouseEvent mouseEvent) {
        try {
            Soutenance et = TableSoutenance.getSelectionModel().getSelectedItem();
            MatriculeSout.setText(String.valueOf(et.getCode()));
            PromoCombo.setPromptText(String.valueOf(et.getJury_Numero()));
            JuryCode = et.getJury_Numero();
            LoadDataTableEnseignant();
        } catch (Exception e) {
            System.out.println(e + "click");
        }
    }

    public void Delete(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer Soutenance ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                  /*  String sql4 = "DELETE FROM enseignant_has_jury where Jury_Soutenance_Code=?";
                    pst = Con.prepareStatement(sql4);
                    pst.setString(1, MatriculeSout.getText());
                    pst.execute();
                    LoadDataTableSoutenance();*/
                String sql3 = "DELETE FROM soutenance where Code=? ";
                pst2 = Con.prepareStatement(sql3);
                pst2.setString(1, MatriculeSout.getText());
                pst2.execute();
                LoadDataTableEnseignant();
                Conf.dialog(Alert.AlertType.INFORMATION, "La Soutenance est supprimier");
            } catch (Exception e) {
                System.out.println(e + " Exception deleteJury");
            }
            LoadDataTableEnseignant();
            LoadDataTableSoutenance();
        }

    }



}