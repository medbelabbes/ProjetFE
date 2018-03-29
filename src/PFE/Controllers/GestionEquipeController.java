package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Equipe;
import PFE.Model.Etudiant;
import PFE.Model.Promotion;
import PFE.Model.Section;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 15/05/2017.
 */
public class GestionEquipeController implements Initializable {


    public MenuItem ValidermenuItem;
    public MenuItem DeleteEquipeMenuItem;
    public JFXButton AffichéEquipeNonValidéBtn;
    public JFXTextField DesignationTXT;
    @FXML
    private AnchorPane PaneData;

    @FXML
    private TableView<Equipe> EquipeTable;

    @FXML
    private TableColumn<Equipe, String> NumeroEquipeCol;

    @FXML
    private TableColumn<Equipe, String> NbrEtdEquipeCol;

    @FXML
    private TableColumn<Equipe, String> MoyenneEquipeCol;
    public TableColumn<Equipe, String> ValiderEquipeCol;


    @FXML
    private JFXButton AddEquipeBtn;

    @FXML
    private TableView<Etudiant> TableEtudiant;

    @FXML
    private TableColumn<Etudiant, String> MatriculeEtdCol;

    @FXML
    private TableColumn<Etudiant, String> NomEtdCol;

    @FXML
    private TableColumn<Etudiant, String> PrenomEtdCol;

    @FXML
    private TableColumn<Etudiant, String> EmailEtdCol;

    @FXML
    private TableColumn<Etudiant, String> MoyenneEtdCol;

    @FXML
    private TableColumn<Etudiant, String> GroupeEtdCol;

    @FXML
    private TableView<Etudiant> TableEtdEquipe;

    @FXML
    private TableColumn<Etudiant, String> MatriculeEtdEquipeCol;

    @FXML
    private TableColumn<Etudiant, String> NomEtdEquipeCol;

    @FXML
    private TableColumn<Etudiant, String> PrenomEtdEquipeCol;

    @FXML
    private TableColumn<Etudiant, String> EmailEtdEquipeCol;

    @FXML
    private TableColumn<Etudiant, String> MoyenneEtdEquipeCol;

    @FXML
    private TableColumn<Etudiant, String> GroupeEtdEquipeCol;

    @FXML
    private JFXTextField ChercherEtudiant;

    @FXML
    private JFXComboBox<String> PromoCombo;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton AffecterBtn;
    public static String nometd;
    public static String prenometd;
    public static String Valid;
    int max, min;
    Config2 Conf = new Config2();
    Stage stage;
    Rectangle2D rec2;
    private ObservableList<String> PromoList;
    private ObservableList<Equipe> EquipeList;
    private ObservableList<Etudiant> EtudiantList;
    private ObservableList<Etudiant> EtudiantEquipeList;
    Connection Con, Con1, Con2;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2, rs3, rs5, rs6;
    public static String ID_Promo;
    public static int Matricule_Equipe;
    public static String MatriculeETD;
    public static int nbr_min;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        rec2 = Screen.getPrimary().getVisualBounds();
        NumeroEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        NbrEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Etd"));
        MoyenneEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Moyenne"));
        ValiderEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Validation"));

        MatriculeEtdCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEtudiant"));
        NomEtdCol.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        PrenomEtdCol.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        EmailEtdCol.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
        MoyenneEtdCol.setCellValueFactory(new PropertyValueFactory<>("MoyenneEtudiant"));
        GroupeEtdCol.setCellValueFactory(new PropertyValueFactory<>("NumGroupEtudiant"));

        MatriculeEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEtudiant"));
        NomEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        PrenomEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        EmailEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
        MoyenneEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("MoyenneEtudiant"));
        GroupeEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("NumGroupEtudiant"));
        ChangeButtonStatue();
        LoadDataComboPromo();
        PromoCombo.setOnAction(event -> {
            try {
                rs3 = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs3.next()) {
                    ID_Promo = rs3.getString("ID");
                    initCol();
                }
                LoadDataTableEquipe();
                LoadDataTableEtudiant();
                LoadDataTableEtudiantEquipe();
            } catch (Exception ex) {
                System.out.println(ex);
            }

        });

    }

    public void LoadDataComboPromo() {
        try {
            PromoList = FXCollections.observableArrayList();

            rs1 = Con.createStatement().executeQuery("SELECT Designation FROM Promotion where Annee_Code='" + GetMaxYear() + "'");
            while (rs1.next()) {
                PromoList.add(rs1.getString("Designation"));
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

    public void LoadDataTableEquipe() {
        try {
            EquipeList = FXCollections.observableArrayList();
            String qr = "SELECT * FROM Equipe where Promotion_Numero ='" + ID_Promo + "'";
            pst1 = Con.prepareStatement(qr);
            rs1 = pst1.executeQuery();
            while (rs1.next()) {
                if ((rs1.getString(2) == null) && (rs1.getString(3) == null) && (rs1.getString(4) == null)) {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            0,
                            0,
                            rs1.getString(7)));
                } else if ((Integer.valueOf(rs1.getString(2)) == 0) && (Integer.valueOf(rs1.getString(3)) == 0)) {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            0,
                            0,
                            rs1.getString(7)));
                } else {

                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            Integer.valueOf(rs1.getString(2)),
                            Float.valueOf(rs1.getString(3)),
                            rs1.getString(7)));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex + " Table equipe");
        }
        EquipeTable.setItems(null);
        EquipeTable.setItems(EquipeList);
    }

    public void LoadDataTableEtudiant() {
        String query1;
        String query2;
        String query3;
        try {
            EtudiantList = FXCollections.observableArrayList();
            query1 = "SELECT ID From Section WHERE Promotion_Numero='" + ID_Promo + "'";
            pst = Con.prepareStatement(query1);
            rs = pst.executeQuery();
            while (rs.next()) {
                query2 = "SELECT ID FROM Groupe WHERE Section_Code ='" + rs.getString("ID") + "'";
                pst1 = Con.prepareStatement(query2);
                rs1 = pst1.executeQuery();
                while (rs1.next()) {
                    query3 = "SELECT * FROM Etudiant WHERE Groupe_Numero='" + rs1.getString("ID") + "' AND Equipe_Numero IS NULL ";
                    pst2 = Con.prepareStatement(query3);
                    rs2 = pst2.executeQuery();
                    while (rs2.next()) {
                        EtudiantList.add(new Etudiant(rs2.getString(1), rs2.getString(2),
                                rs2.getString(3), rs2.getString(7), Float.valueOf(rs2.getString(10)),
                                Integer.valueOf(rs2.getString(14))));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex + " Exception populate data students to TableView");
        }
        TableEtudiant.setItems(null);
        TableEtudiant.setItems(EtudiantList);
    }

    public void LoadDataTableEtudiantEquipe() {

        try {
            EtudiantEquipeList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Equipe_Numero='" + Matricule_Equipe + "' ");
            while (rs.next()) {
                EtudiantEquipeList.add(new Etudiant(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(7), Float.valueOf(rs.getString(10)),
                        Integer.valueOf(rs.getString(14))));

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        TableEtdEquipe.setItems(null);
        TableEtdEquipe.setItems(EtudiantEquipeList);
    }


    public void LoadData(ActionEvent actionEvent) {

        LoadDataTableEquipe();
        LoadDataTableEtudiant();

    }

    public void AddEquipe(ActionEvent actionEvent) {

        if (PromoCombo.getSelectionModel().getSelectedItem().toString().equals(null)) {
            Conf.dialog(Alert.AlertType.WARNING, "Vous devez selectionner une promotion");
        } else {
            try {

                String query = "INSERT INTO Equipe(Promotion_Numero,Validation,Designation) VALUES(?,?,?)";
                pst = Con.prepareStatement(query);
                pst.setString(1, ID_Promo);
                pst.setString(2, "Non");
                pst.setString(3, DesignationTXT.getText());
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Equipe ajoutée");


            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println(ID_Promo);
        LoadDataTableEquipe();
    }


    public void ClickTableEquipeItem1(MouseEvent mouseEvent) {
        try {
            Equipe e = EquipeTable.getSelectionModel().getSelectedItem();
            Matricule_Equipe = e.getNuméro();
            nbr_min = e.getNbr_Etd();
            Valid = e.getValidation();
            LoadDataTableEtudiantEquipe();
            LoadDataTableEtudiant();
            ChangeButtonStatue();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ClickTableEquipeItem2(MouseEvent mouseEvent) {
        try {
            Etudiant et = TableEtudiant.getSelectionModel().getSelectedItem();
            MatriculeETD = et.getMatriculeEtudiant();
            nometd = et.getNomEtudiant();
            prenometd = et.getPrenomEtudiant();
            LoadDataTableEtudiantEquipe();
            LoadDataTableEtudiant();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Affecter(ActionEvent actionEvent) {
        int nbr_etd = 0;
        int Nombre_Max = 0;
        int Nombre_Min = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Affecter l'étudiant " + prenometd + " " + nometd + " " + " à l'équipe : " + Matricule_Equipe + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                String query1 = "SELECT * FROM Parametre WHERE Promotion_Numero='" + ID_Promo + "'";
                rs = Con.createStatement().executeQuery(query1);
                if (rs.next()) {
                    Nombre_Max = Integer.valueOf(rs.getString("Nbr_max"));
                    Nombre_Min = Integer.valueOf(rs.getString("Nbr_min"));
                }
                System.out.println(Nombre_Max + "  " + Nombre_Min);
                String query2 = "Select count(*) FROM Etudiant Where Equipe_Numero='" + Matricule_Equipe + "'";
                rs2 = Con.createStatement().executeQuery(query2);
                while (rs2.next()) {
                    nbr_etd = Integer.valueOf(rs2.getString("count(*)"));
                }
                System.out.println("Le nombre des étudiants dans cette equipe est " + nbr_etd);
                if (nbr_etd < Nombre_Max) {
                    String sql = "UPDATE Etudiant set Equipe_Numero ='" + Matricule_Equipe + "'where Matricule='" + MatriculeETD + "'";
                    pst = Con.prepareStatement(sql);
                    pst.execute();
                    float SommemoyenneEquipe = 0;
                    int Nombre_étudiant = 0;
                    float moyenneEquipe = 0;
                    String sql2 = "SELECT Moyenne From Etudiant WHERE Equipe_Numero ='" + Matricule_Equipe + "'";
                    rs1 = Con.createStatement().executeQuery(sql2);
                    while (rs1.next()) {
                        SommemoyenneEquipe = SommemoyenneEquipe + Float.valueOf(rs1.getString("Moyenne"));
                        Nombre_étudiant++;
                    }
                    moyenneEquipe = SommemoyenneEquipe / Nombre_étudiant;
                    String sql3 = "UPDATE Equipe set Nombre_Etudiants ='" + Nombre_étudiant + "',Moyenne= '" + moyenneEquipe + "' where Numero='" + Matricule_Equipe + "'";
                    pst = Con.prepareStatement(sql3);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "L'étudiant: " + prenometd + " " + nometd + " est affecté à l'équipe: " + Matricule_Equipe);
                } else if (Nombre_Max == nbr_etd) {
                    Conf.dialog(Alert.AlertType.WARNING, "Vous avez atteint à la limite d'équipe, vous ne pouvez pas ajouter un notre étudiant ");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        LoadDataTableEquipe();
        LoadDataTableEtudiant();
        LoadDataTableEtudiantEquipe();
    }

    public void Delete(ActionEvent actionEvent) {
        try {
            String q = "SELECT * FROM Etudiant WHERE Matricule='" + MatriculeETD + "'";
            rs5 = Con.createStatement().executeQuery(q);
            while (rs5.next()) {
                String q1 = "SELECT * FROM Equipe WHERE Numero='" + rs5.getString("Equipe_Numero") + "'";
                rs6 = Con.createStatement().executeQuery(q1);
                while (rs6.next()) {
                    if (rs6.getString("Validation").equals("Non")) {
                        int Nbr_Etd_equipe = 0;
                        float moyenne_equipe = 0;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer l'étudiant " + prenometd + " " + nometd + " " + " de l'équipe :" + Matricule_Equipe + " ?");
                        alert.initStyle(StageStyle.UTILITY);
                        Optional<ButtonType> res = alert.showAndWait();
                        if (res.get() == ButtonType.OK) {
                            try {

                                String sql3 = "UPDATE Etudiant set Equipe_Numero = ? where Matricule='" + MatriculeETD + "'";
                                pst = Con.prepareStatement(sql3);
                                pst.setNull(1, Types.INTEGER);
                                pst.execute();
                                String sql = "SELECT count(*) FROM Etudiant WHERE Equipe_Numero ='" + Matricule_Equipe + "'";
                                rs1 = Con.createStatement().executeQuery(sql);
                                while (rs1.next()) {
                                    Nbr_Etd_equipe = Integer.valueOf(rs1.getString("count(*)"));
                                    System.out.println("Nombre etudiants par équipe " + Nbr_Etd_equipe);
                                }
                                String sql2 = "SELECT avg(Moyenne) FROM Etudiant WHERE Equipe_Numero ='" + Matricule_Equipe + "'";
                                rs = Con.createStatement().executeQuery(sql2);
                                while (rs.next()) {
                                    moyenne_equipe = Float.valueOf(rs.getString("avg(Moyenne)"));
                                    System.out.println("la moyenne est " + moyenne_equipe);
                                }

                                System.out.println("update 0");

                                String sql4 = "UPDATE Equipe set Nombre_Etudiants ='" + Nbr_Etd_equipe + "',Moyenne= '" + moyenne_equipe + "' where Numero='" + Matricule_Equipe + "'";
                                pst = Con.prepareStatement(sql4);
                                pst.execute();


                                Conf.dialog(Alert.AlertType.INFORMATION, "L'étudiant: " + prenometd + " " + nometd + " est supprimé de l'équipe: " + Matricule_Equipe);
                            } catch (Exception e) {
                                System.out.println(e + " Exception delete");
                            }
                        }

                        LoadDataTableEquipe();
                        LoadDataTableEtudiant();
                        LoadDataTableEtudiantEquipe();
                    } else if (rs6.getString("Validation").equals("Oui")) {
                        Conf.dialog(Alert.AlertType.WARNING, "Vous ne pouvez pas supprimer ce étudiant");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ChangeButtonStatue() {
        try {
            rs = Con.createStatement().executeQuery("SELECT Validation FROM Equipe where Numero='" + Matricule_Equipe + "' ");
            if (rs.next()) {
                if (rs.getString("Validation").equals("Non")) {
                    ValidermenuItem.setText("Valider");
                } else if (rs.getString("Validation").equals("Oui")) {
                    ValidermenuItem.setText("Invalider");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ClickTableEquipeItem3(MouseEvent mouseEvent) {

        try {
            Etudiant et = TableEtdEquipe.getSelectionModel().getSelectedItem();
            MatriculeETD = et.getMatriculeEtudiant();
            nometd = et.getNomEtudiant();
            prenometd = et.getPrenomEtudiant();
        } catch (Exception e) {
            System.out.println(e);
        }

        LoadDataTableEtudiantEquipe();
        LoadDataTableEtudiant();
    }

    private void initCol() {
        NumeroEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        NbrEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Etd"));
        MoyenneEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Moyenne"));
        ValiderEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Validation"));

        ValiderEquipeCol.setCellFactory(column -> {
            return new TableCell<Equipe, String>() {

                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");

                    } else if (item.equals("Non")) {

                        setStyle("-fx-background-color: #f44336 ");
                        setText("Ne pas validé");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    } else {
                        setStyle("-fx-background-color: #26a69a");
                        setText("Validé");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });
    }

    public void Valider(ActionEvent actionEvent) {
        if (ValidermenuItem.getText().equals("Valider")) {
            String x = "Oui";
            try {
                String sql = "SELECT * FROM Parametre WHERE Promotion_Numero='" + ID_Promo + "'";
                rs = Con.createStatement().executeQuery(sql);
                if (rs.next()) {
                    if (Integer.valueOf(rs.getString("Nbr_min")) <= nbr_min) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Valider cette Equipe  ?");
                        alert.initStyle(StageStyle.UTILITY);
                        Optional<ButtonType> res = alert.showAndWait();
                        if (res.get() == ButtonType.OK) {
                            String sql3 = "UPDATE Equipe set Validation ='" + x + "' where Numero='" + Matricule_Equipe + "'";
                            pst = Con.prepareStatement(sql3);
                            pst.execute();
                            Conf.dialog(Alert.AlertType.INFORMATION, "Equipe Validée");
                        }
                    } else
                        Conf.dialog(Alert.AlertType.WARNING, "Vous ne pouvez pas valider cette Equipe");
                }
                LoadDataTableEquipe();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        if (ValidermenuItem.getText().equals("Invalider")) {
            String x = "Non";
            try {
                String sql = "SELECT * FROM Parametre WHERE Promotion_Numero='" + ID_Promo + "'";
                rs = Con.createStatement().executeQuery(sql);
                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment invalider cette Equipe  ?");
                    alert.initStyle(StageStyle.UTILITY);
                    Optional<ButtonType> res = alert.showAndWait();
                    if (res.get() == ButtonType.OK) {
                        String sql3 = "UPDATE Equipe set Validation ='" + x + "' where Numero='" + Matricule_Equipe + "'";
                        pst = Con.prepareStatement(sql3);
                        pst.execute();
                        Conf.dialog(Alert.AlertType.INFORMATION, "Equipe invalidée");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            LoadDataTableEquipe();
        }

    }

    public void DeleteEquipe(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Valider cette Equipe  ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                String sql = "DELETE FROM Equipe where  Numero='" + Matricule_Equipe + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Equipe supprimée");
                LoadDataTableEquipe();
                LoadDataTableEtudiant();
                LoadDataTableEtudiantEquipe();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void AfficherEquipeBtn(ActionEvent actionEvent) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/EquipesValidés.fxml");
    }

    public static String getID_Promo() {
        return ID_Promo;
    }

    public void ChercherEtudiant(KeyEvent keyEvent) {
    }
}
