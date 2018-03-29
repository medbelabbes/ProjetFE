package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Choix;
import PFE.Model.Equipe;
import PFE.Model.Etudiant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 07/06/2017.
 */
public class AffectationController implements Initializable {

    @FXML
    private AnchorPane PaneData;

    @FXML
    private JFXComboBox<String> PromoCombo;

    @FXML
    private TableView<Equipe> EquipeTable;

    @FXML
    private TableColumn<Equipe, String> NumeroEquipeCol;

    @FXML
    private TableColumn<Equipe, String> NbrEtdEquipeCol;

    @FXML
    private TableColumn<Equipe, String> MoyenneEquipeCol;

    @FXML
    private TableColumn<Equipe, String> Code_ProjetEquipeCol;

    @FXML
    private TableColumn<Equipe, String> ProjetEquipeCol;

    @FXML
    private TableColumn<Equipe, String> PromotionEquipeCol;

    @FXML
    private TableView<Choix> Table_Choix;

    @FXML
    private TableColumn<Choix, String> NumeroChoixCol;

    @FXML
    private TableColumn<Choix, String> CodeProjetChoixCol;

    @FXML
    private TableColumn<Choix, String> ProjetChoixCol;

    @FXML
    private TableColumn<Choix, String> OrdreChoixCol;

    @FXML
    private JFXButton AffecterBtn;

    @FXML
    private TextField ProjetTXT;
    Connection Con;
    PreparedStatement pst, pst1, pst2, pst3, pst4, pst5;
    ResultSet rs, rs1, rs2, rs3, rs4;
    Config2 Conf = new Config2();
    private ObservableList<String> PromoList;
    private ObservableList<Equipe> EquipeList;
    private ObservableList<Choix> ChoixList;
    public static String ID_Promo;
    public static int ID_Equipe;
    boolean b;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        ProjetTXT.setEditable(false);
        NumeroEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        NbrEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Etd"));
        MoyenneEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Moyenne"));
        Code_ProjetEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Projet"));
        ProjetEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Projet"));
        PromotionEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Promo"));

        NumeroChoixCol.setCellValueFactory(new PropertyValueFactory<>("Numéro_Equipe"));
        CodeProjetChoixCol.setCellValueFactory(new PropertyValueFactory<>("Code_Projet"));
        ProjetChoixCol.setCellValueFactory(new PropertyValueFactory<>("Projet"));
        OrdreChoixCol.setCellValueFactory(new PropertyValueFactory<>("Ordre"));

        LoadDataComboPromo();
        PromoCombo.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs.next()) {
                    ID_Promo = rs.getString("ID");

                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            LoadDataTableEquipe();
        });
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
 /*  private void initCol() {
       NumeroEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
       NbrEtdEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Etd"));
       MoyenneEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Moyenne"));
       Code_ProjetEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Projet"));
       ProjetEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Projet"));
       PromotionEquipeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Promo"));

       Code_ProjetEquipeCol.setCellFactory(column -> {
            return new TableCell<Equipe, String>() {

                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");

                    } else if (item.equals("true")) {

                        setStyle("-fx-background-color: #f44336 ");
                        setText("0");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    } else {
                        setStyle("-fx-background-color: #26a69a");
                        setText("LIBRE");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

    }*/

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

    public void LoadDataTableEquipe() {
        try {
            EquipeList = FXCollections.observableArrayList();
            String qr = "SELECT * FROM Equipe where Promotion_Numero ='" + ID_Promo + "' ";
            pst = Con.prepareStatement(qr);
            rs1 = pst.executeQuery();
            while (rs1.next()) {
                if ((rs1.getString(4) == null) && (rs1.getString(6)) == null) {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            Integer.valueOf(rs1.getString(2)),
                            Float.valueOf(rs1.getString(3)),
                            Integer.valueOf(rs1.getString(5))));
                } else {
                    EquipeList.add(new Equipe(Integer.valueOf(rs1.getString(1)),
                            Integer.valueOf(rs1.getString(2)),
                            Float.valueOf(rs1.getString(3)),
                            Integer.valueOf(rs1.getString(4)),
                            rs1.getString(6),
                            Integer.valueOf(rs1.getString(5))));

                }
            }
        } catch (Exception ex) {
            System.out.println(ex + " Table equipe");
        }
        EquipeTable.setItems(null);
        EquipeTable.setItems(EquipeList);
    }

    public void LoadDataTableChoix() {
        try {
            ChoixList = FXCollections.observableArrayList();
            String qr = "SELECT * FROM choisir where Equipe_Numero ='" + ID_Equipe + "'";
            pst = Con.prepareStatement(qr);
            rs = pst.executeQuery();
            while (rs.next()) {
                ChoixList.add(new Choix(Integer.valueOf(rs.getString(1)),
                        Integer.valueOf(rs.getString(2)),
                        rs.getString(3),
                        Integer.valueOf(rs.getString(4))
                ));

            }
        } catch (Exception ex) {
            System.out.println(ex + " Table equipe");
        }
        Table_Choix.setItems(null);
        Table_Choix.setItems(ChoixList);
    }


    public void ClickTableItem(MouseEvent mouseEvent) {
        try {
            Equipe e = EquipeTable.getSelectionModel().getSelectedItem();
            ID_Equipe = e.getNuméro();
            ProjetTXT.setText(e.getProjet());
            LoadDataTableChoix();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Affecter(ActionEvent actionEvent) {
        try {
            String sql = "SELECT * FROM Parametre WHERE Promotion_Numero='" + ID_Promo + "'";
            pst5 = Con.prepareStatement(sql);
            rs3 = pst5.executeQuery();
            if (rs3.next()) {
                if (rs3.getString("Affection_mode").equals("Fiche de voeux")) {
                    int Nbr_Equipe_Possible;
                    String sql1 = "SELECT * FROM Equipe WHERE Promotion_Numero='" + ID_Promo + "' AND Projet_Code IS NULL ORDER BY Moyenne DESC";
                    pst = Con.prepareStatement(sql1);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        b = true;
                        boolean c = true;
                        String sql2 = "SELECT * FROM Choisir WHERE Equipe_Numero='" + rs.getString("Numero") + "' ORDER BY Ordre ASC";
                        pst1 = Con.prepareStatement(sql2);
                        rs1 = pst1.executeQuery();
                        while (rs1.next() && (c)) {
                            String sql3 = "SELECT * FROM Projet WHERE Code='" + rs1.getString("Projet_Code") + "'";
                            pst2 = Con.prepareStatement(sql3);
                            rs2 = pst2.executeQuery();
                            while (rs2.next() && (b)) {
                                if (Integer.valueOf(rs2.getString("Nombre_equipe")) > 0) {
                                    Nbr_Equipe_Possible = Integer.valueOf(rs2.getString("Nombre_equipe"));
                                    Nbr_Equipe_Possible--;
                                    String sql4 = "UPDATE Equipe Set Projet_Code='" + rs2.getString("Code") + "',Projet='" + rs2.getString("Designation") + "' WHERE  Numero='" + rs.getString("Numero") + "'";
                                    pst3 = Con.prepareStatement(sql4);
                                    pst3.execute();
                                    String sql5 = "UPDATE Projet Set Nombre_equipe='" + Nbr_Equipe_Possible + "' WHERE  Code='" + rs2.getString("Code") + "'";
                                    pst4 = Con.prepareStatement(sql5);
                                    pst4.execute();
                                    b = false;
                                }
                            }
                            c = b;
                        }
                    }
                    try {
                        String query1 = "SELECT * FROM Equipe WHERE Promotion_Numero='" + ID_Promo + "' AND Projet_Code IS NULL";
                        pst1 = Con.prepareStatement(query1);
                        rs4 = pst1.executeQuery();
                        while (rs4.next()) {
                            String query2 = "DELETE FROM Choisir WHERE Equipe_Numero='" + rs4.getString("Numero") + "'";
                            pst = Con.prepareStatement(query2);
                            pst.execute();
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        LoadDataTableEquipe();
        LoadDataTableChoix();
        Conf.dialog(Alert.AlertType.INFORMATION, "Affectation Terminé");

    }

}
