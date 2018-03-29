package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Annee;
import PFE.Model.Promotion;
import PFE.Model.Section;
import PFE.Animations.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.Optional;
import java.sql.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Mohamed on 05/05/2017.
 */
public class GestionScolaritéController implements Initializable {
    @FXML
    public TableColumn DesignationPromoCol;
    public JFXTextField DesignationPromo;
    public TableColumn CodeOptionPromoCol;
    public JFXComboBox OptionCombo;
    public AnchorPane PaneTxt;

    @FXML
    private AnchorPane PaneData;
    @FXML
    public TableView<Promotion> PromoTable;
    @FXML
    public TableColumn<Promotion, String> NumPromoCol;
    @FXML
    public TableColumn<Promotion, String> NbrEtdPromoCol;
    @FXML
    public TableColumn<Promotion, String> CyclePromoCol;
    @FXML
    public TableColumn<Promotion, String> NiveauPromoCol;
    @FXML
    public TableColumn<Promotion, String> AnneePromoCol;
    @FXML
    public ContextMenu ContxtmenuPromo;
    @FXML
    public MenuItem AddSectionMENU;
    @FXML
    public MenuItem DeletePromoMENU;
    @FXML
    public JFXComboBox AnneeCombo;
    @FXML
    public JFXTextField Nbr_etdTXT;
    @FXML
    public JFXComboBox cycleCombo;
    @FXML
    public JFXComboBox niveauCombo;
    @FXML
    public JFXButton AddPromoBTN;
    @FXML
    public JFXButton UpdatePromoBTN;
    @FXML
    public TableView<Section> Section_table;
    @FXML
    public TableColumn<Section, String> CodeSectionCol;
    @FXML
    public TableColumn<Section, String> DesignationSectionCol;
    @FXML
    public TableColumn<Section, String> Nbr_etdSectionCol;
    @FXML
    public TableColumn<Section, String> num_promoSectionCol;


    public JFXTextField Chercher_txt;
    public JFXButton ViderBtn;
    public static String CodePromo;
    public static String AnneeCode;
    Config2 Conf = new Config2();
    Stage stage;
    Stage stage3;
    Rectangle2D rec2;
    Double w, h;
    private ObservableList<String> OptionsList;
    private ObservableList<String> Année;
    private ObservableList<Promotion> dataPromo;
    private ObservableList<Section> dataSection;
    private ObservableList<Promotion> ListPromo;
    ObservableList<String> CycleList = FXCollections.observableArrayList("Cycle Préparatoire", "Cycle Supérieur");
    ObservableList<String> NiveauCPIList = FXCollections.observableArrayList("Deuxieme année Cycle Préparatoire");
    ObservableList<String> NiveauCSList = FXCollections.observableArrayList("Premiére Année Cycle Supérieur", "Deuxiéme Année Cycle Supérieur", "Troisiéme Année Cycle Supérieur");


    Connection Con;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2, rs3;
    public String AnnéePromotion;
    public static int Code_Option;
    public static String Designation_Option;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new FadeInLeftTransition(PromoTable);
        new FadeInRightTransition(PaneTxt);
        new FadeInRightTransition(Section_table);

        Con = JavaConnection.ConnectDB();
        LoadDataComboOption();
        DesignationPromo.setEditable(false);
        cycleCombo.setEditable(false);
        niveauCombo.setEditable(false);
        OptionCombo.setEditable(false);
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        cycleCombo.setItems(CycleList);
        niveauCombo.setItems(NiveauCPIList);
        niveauCombo.setItems(NiveauCSList);
        NumPromoCol.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        DesignationPromoCol.setCellValueFactory(new PropertyValueFactory<>("Désignation"));
        NbrEtdPromoCol.setCellValueFactory(new PropertyValueFactory<>("Nombre_etudiant"));
        CyclePromoCol.setCellValueFactory(new PropertyValueFactory<>("Cycle"));
        NiveauPromoCol.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        AnneePromoCol.setCellValueFactory(new PropertyValueFactory<>("Annee_Code"));
        CodeOptionPromoCol.setCellValueFactory(new PropertyValueFactory<>("Option_Code"));
        CodeSectionCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DesignationSectionCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
        Nbr_etdSectionCol.setCellValueFactory(new PropertyValueFactory<>("Num_Etudiant"));
        num_promoSectionCol.setCellValueFactory(new PropertyValueFactory<>("Num_Promotion"));

        LoadDataCombo();
        AnneeCombo.setOnAction(event -> {
            AnneeCode = AnneeCombo.getSelectionModel().getSelectedItem().toString();
            LoadDataTable();
        });
        cycleCombo.setEditable(false);
        niveauCombo.setEditable(false);
        LoadComboOptions();
    }

    public static String getAnneeCode() {
        return AnneeCode;
    }

    @FXML
    private void NiveauChoice() {
        if (cycleCombo.getValue().equals("Cycle Préparatoire")) {
            niveauCombo.setValue("Deuxieme année Cycle Préparatoire");
            niveauCombo.setItems(NiveauCPIList);
        } else {
            niveauCombo.setValue("Premiére Année Cycle Supérieur");
            niveauCombo.setItems(NiveauCSList);
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


    @FXML
    public void LoadDataTable() {
        try {
            dataPromo = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Promotion where Annee_Code ='" + AnneeCombo.getSelectionModel().getSelectedItem().toString() + "' OR Annee_Code ='" + GetMaxYear() + "' ");
            while (rs.next()) {
                dataPromo.add(new Promotion(Integer.valueOf(rs.getString("ID")),
                        rs.getString("Designation"),
                        (Integer.valueOf(rs.getString("Nombre_etudiants"))),
                        rs.getString("Cycle"),
                        rs.getString("Niveau"),
                        rs.getString("Annee_Code"),
                        Integer.valueOf(rs.getString("Option_Code"))));
                PromoTable.setItems(null);
                PromoTable.setItems(dataPromo);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataTableSection() {
        try {
            dataSection = FXCollections.observableArrayList();
            rs2 = Con.createStatement().executeQuery("SELECT * FROM Section where Promotion_Numero ='" + CodePromo + "' ");
            while (rs2.next()) {
                dataSection.add(new Section(Integer.valueOf(rs2.getString("ID")),
                        rs2.getString("Designation"),
                        Integer.valueOf(rs2.getString("Nombre_etudiants")),
                        Integer.valueOf(rs2.getString("Promotion_Numero"))));
                Section_table.setItems(null);
                Section_table.setItems(dataSection);

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadComboOptions() {
        try {
            rs = Con.createStatement().executeQuery("SELECT Designation_Option FROM Options_Table WHERE ID ='" + Code_Option + "'");
            while (rs.next()) {
                Designation_Option = rs.getString("Designation_Option");
                OptionCombo.setValue(Designation_Option);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataComboOption() {
        try {

            OptionsList = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Designation_Option FROM Options_Table ");
            while (rs.next()) {
                OptionsList.add(rs.getString("Designation_Option"));

            }
            OptionCombo.setItems(null);
            OptionCombo.setItems(OptionsList);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataCombo() {
        try {

            Année = FXCollections.observableArrayList();
            rs1 = Con.createStatement().executeQuery("SELECT Code FROM Annee ");
            while (rs1.next()) {
                Année.add(rs1.getString("Code"));

            }
            AnneeCombo.setItems(Année);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ClickTableItem(MouseEvent mouseEvent) {
        try {
            LoadComboOptions();
            Promotion promo = PromoTable.getSelectionModel().getSelectedItem();
            CodePromo = String.valueOf(promo.getNumero());
            DesignationPromo.setText(promo.getDésignation());
            Nbr_etdTXT.setText(String.valueOf(promo.getNombre_etudiant()));
            cycleCombo.setValue(promo.getCycle());
            niveauCombo.setValue(promo.getNiveau());
            AnnéePromotion = promo.getAnnee_Code();
            Code_Option = promo.getOption_Code();
            LoadDataTableSection();
            LoadComboOptions();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddPromo(ActionEvent actionEvent) {
        try {

            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            String yearInString = String.valueOf(year);
            String querry = "INSERT INTO Promotion(Designation,Nombre_etudiants,Cycle,Niveau,Annee_Code,Option_Code) VALUES(?,?,?,?,?,?)";
            pst = Con.prepareStatement(querry);
            pst.setString(1, niveauCombo.getSelectionModel().getSelectedItem().toString() + " " + yearInString);
            pst.setString(2, Nbr_etdTXT.getText());
            pst.setString(3, cycleCombo.getSelectionModel().getSelectedItem().toString());
            pst.setString(4, niveauCombo.getSelectionModel().getSelectedItem().toString());
            pst.setString(5, yearInString);
            pst.setString(6, String.valueOf(Code_Option));
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Promotion Ajoutée");
            LoadDataTable();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void UpdatePromo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiement modifier la promotion " + CodePromo + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                String sql = "UPDATE Promotion set Nombre_etudiants ='" + Nbr_etdTXT.getText() + "',Cycle='" + cycleCombo.getValue() + "',Niveau='" + niveauCombo.getValue() + "' where ID='" + CodePromo + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Promotion Modifié");
                LoadDataTable();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    public void AddSectionPane(ActionEvent actionEvent) {


        Conf.loadAnchorPane(PaneData, "/PFE/View/Section_Groupes.fxml");
    }

    public void ChercherPromo(KeyEvent keyEvent) {
        try {
            ListPromo = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Promotion where Designation  like %'" + Chercher_txt.getText() + "'%");
            while (rs.next()) {
                ListPromo.add(new Promotion(Integer.valueOf(rs.getString("ID")),
                        rs.getString("Designation"),
                        (Integer.valueOf(rs.getString("Nombre_etudiants"))),
                        rs.getString("Cycle"),
                        rs.getString("Niveau"),
                        rs.getString("Annee_Code"),
                        Integer.valueOf(rs.getString("Option_Code"))));
                PromoTable.setItems(ListPromo);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void dialog(Alert.AlertType alertType, String s) {
        Alert alert = new Alert(alertType, s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    public void DeletePromo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous étes sur de supprimer la promotion " + CodePromo + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                String sql = "DELETE FROM Promotion where  ID='" + CodePromo + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                LoadDataTable();
                dialog(Alert.AlertType.INFORMATION, "Promotion supprimée");
                LoadDataTable();
            } catch (Exception e) {

                System.out.println(e);
            }
        }
    }

    public void newStage(Stage stage, String load, String titre, boolean resize, StageStyle style, boolean maximized) {
        try {
            Stage s = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            s.initStyle(style);
            s.setResizable(resize);
            s.setMaximized(maximized);
            s.setTitle(titre);
            s.setScene(scene);
            s.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Clear(ActionEvent actionEvent) {
        DesignationPromo.clear();
        Nbr_etdTXT.clear();
        cycleCombo.setValue("CPI");
        niveauCombo.setValue("2CPI");
    }

    public void AddOptionView(ActionEvent actionEvent) {
        newStage(stage3, "/PFE/View/OptionPromotion.fxml", "Option", true, StageStyle.UNDECORATED, false);

    }

    public void AddElementView(ActionEvent actionEvent) {
        newStage(stage3, "/PFE/View/ElementEvaluation.fxml", "Option", true, StageStyle.UNDECORATED, false);

    }

    public static String getCodePromo() {
        return CodePromo;
    }
}
