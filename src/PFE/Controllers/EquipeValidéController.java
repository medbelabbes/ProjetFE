package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Equipe;
import PFE.Model.Etudiant;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 10/06/2017.
 */
public class EquipeValidéController implements Initializable {
    public JFXTextField ChercherTXt;
    public JFXComboBox<String> ChercherParCombo;
    @FXML
    private AnchorPane PaneData;

    @FXML
    private Button BtnBack;

    @FXML
    private TableView<Equipe> EquipeNonValidTable;

    @FXML
    private TableColumn<Equipe, String> NumeroEquipeCol;

    @FXML
    private TableColumn<Equipe, String> NbrEtdEquipeCol;

    @FXML
    private TableColumn<Equipe, String> MoyenneEquipeCol;

    @FXML
    private TableColumn<Equipe, String> PromoEquipeCol;

    @FXML
    private TableColumn<Equipe, String> ValiderEquipeCol;

    @FXML
    private MenuItem ValidermenuItem;

    @FXML
    private MenuItem DeleteEquipeMenuItem;

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
    private TableView<Etudiant> TableEtdNonAffecté;

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
    Connection Con;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2;
    Config2 Conf = new Config2();
    private ObservableList<Equipe> EquipeList;
    private ObservableList<Etudiant> EtudiantList;
    private ObservableList<Etudiant> EtudiantNonAffList;
    private ObservableList<String> ChercherPar = FXCollections.observableArrayList(
            "Matricule",
            "Nom",
            "Prénom",
            "Email",
            "Moyenne",
            "Numéro de groupe");

    GestionEquipeController GE = new GestionEquipeController();
    public static int Matricule_Equipe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        ChercherParCombo.setItems(ChercherPar     );
        NumeroEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        NbrEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Etd"));
        MoyenneEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Moyenne"));
        ValiderEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Validation"));
        PromoEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Promo"));

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
        LoadDataTableEquipe();
        LoadDataTableEtudiantNonaffecté();
    }

    @FXML
    void Back(ActionEvent event) {
        Conf.loadAnchorPane(PaneData, "/PFE/View/GestionEquipe.fxml");
    }

    @FXML
    void ClickTableEquipeItem1(MouseEvent event) {
        try {
            Equipe e = EquipeNonValidTable.getSelectionModel().getSelectedItem();
            Matricule_Equipe = e.getNuméro();
            LoadDataTableEtudiantEquipe();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public void LoadDataTableEtudiantNonaffecté() {
        String query1;
        String query2;
        String query3;
        try {
            EtudiantNonAffList = FXCollections.observableArrayList();
            query1 = "SELECT ID From Section WHERE Promotion_Numero='" + GE.getID_Promo() + "'";
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
                        EtudiantNonAffList.add(new Etudiant(rs2.getString(1),
                                rs2.getString(2),
                                rs2.getString(3),
                                rs2.getString(7),
                                Float.valueOf(rs2.getString(10)),
                                Integer.valueOf(rs2.getString(14))));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex + " Exception populate data students to TableView");
        }
        TableEtdNonAffecté.setItems(null);
        TableEtdNonAffecté.setItems(EtudiantNonAffList);
    }

    public void LoadDataTableEquipe() {
        String x = "Non";
        try {
            EquipeList = FXCollections.observableArrayList();
            String qr = "SELECT * FROM Equipe where Promotion_Numero ='" + GE.getID_Promo() + "' AND Validation ='" + x + "'";
            pst1 = Con.prepareStatement(qr);
            rs1 = pst1.executeQuery();
            while (rs1.next()) {
                if ((rs1.getString(2) == null) && (rs1.getString(3) == null) && (rs1.getString(4) == null)) {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            0,
                            0,
                            Integer.valueOf(rs1.getString(5)),
                            rs1.getString(7)));
                } else if ((Integer.valueOf(rs1.getString(2)) == 0) && (Integer.valueOf(rs1.getString(3)) == 0)) {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            0,
                            0,
                            Integer.valueOf(rs1.getString("Promotion_Numero")),
                            rs1.getString(7)));
                } else {

                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            Integer.valueOf(rs1.getString(2)),
                            Float.valueOf(rs1.getString(3)),
                            Integer.valueOf(rs1.getString("Promotion_Numero")),
                            rs1.getString(7)));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex + " Table equipe");
        }
        EquipeNonValidTable.setItems(null);
        EquipeNonValidTable.setItems(EquipeList);
    }

    public void LoadDataTableEtudiantEquipe() {

        try {
            EtudiantList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Equipe_Numero='" + Matricule_Equipe + "' ");
            while (rs.next()) {
                EtudiantList.add(new Etudiant(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(7), Float.valueOf(rs.getString(10)),
                        Integer.valueOf(rs.getString(14))));

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        TableEtudiant.setItems(null);
        TableEtudiant.setItems(EtudiantList);
    }

    public void ChercherEtudiant(KeyEvent keyEvent) {


        if (ChercherTXt.getText().equals("")) {
            LoadDataTableEtudiantNonaffecté();
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Matricule")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Matricule like %'" + ChercherTXt.getText() + "'% AND Equipe_Numero is NULL ");
                        while (rs.next()) {

                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Nom")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Nom like '%" + ChercherTXt.getText() + "%'  AND Equipe_Numero is NULL");
                        while (rs.next()) {
                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);

        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Prénom")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Prenom like '%" + ChercherTXt.getText() + "%'  AND Equipe_Numero is NULL");
                        while (rs.next()) {
                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Email")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Email like'%" + ChercherTXt.getText() + "%'  AND Equipe_Numero is NULL");
                        while (rs.next()) {
                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Moyenne")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Moyenne like '%" + ChercherTXt.getText() + "%'  AND Equipe_Numero is NULL");
                        while (rs.next()) {
                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Numéro de groupe")) {
            try {
                EtudiantNonAffList = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + GE.getID_Promo() + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Groupe_Numero like '%" + ChercherTXt.getText() + "%'  AND Equipe_Numero is NULL");
                        while (rs.next()) {
                            EtudiantNonAffList.add(new Etudiant(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(7),
                                    Float.valueOf(rs.getString(10)),
                                    Integer.valueOf(rs.getString(14))));
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            TableEtdNonAffecté.setItems(null);
            TableEtdNonAffecté.setItems(EtudiantNonAffList);
        }
    }

}
