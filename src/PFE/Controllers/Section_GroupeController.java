package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Groupe;
import PFE.Model.Promotion;
import PFE.Model.Section;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 09/05/2017.
 */
public class Section_GroupeController implements Initializable {
    @FXML
    public TableView<Section> SectionTable;
    @FXML
    public TableColumn<Section, String> CodeSectionCol;
    @FXML
    public TableColumn<Section, String> DésignationSectionCol;
    @FXML
    public TableColumn<Section, String> NbrEtdSectionCol;
    @FXML
    public TableColumn<Section, String> NumPromoSectionCol;
    @FXML
    public ContextMenu ContxtmenuSection;
    @FXML
    public MenuItem AddGroupMENU;
    @FXML
    public MenuItem DeletesectionMENU;
    @FXML
    public JFXComboBox promoCombo;
    @FXML
    public JFXTextField ChercherSection;
    @FXML
    public TableView<Groupe> groupeTable;
    @FXML
    public TableColumn<Groupe, String> CodeGroupeCol;
    @FXML
    public TableColumn<Groupe, String> DesignationGroupCol;
    @FXML
    public TableColumn<Groupe, String> nbr_EtdGroupeCol;
    @FXML
    public TableColumn<Groupe, String> CodeSectionGroupeCol;
    @FXML
    public JFXTextField Nbr_Etd_Section;
    @FXML
    public JFXTextField CodePromoSectionTXT;
    @FXML
    public JFXButton AddSectionBTN1;
    @FXML
    public JFXButton UpdateSectionBTN1;
    @FXML
    public JFXButton ViderBtn1;

    public JFXTextField Designation_sectionTXT;

    Stage stage;
    Rectangle2D rec2;
    Double w, h;

    private ObservableList<String> Promo;
    private ObservableList<Section> dataSection;
    private ObservableList<Groupe> datagroupe;
    private ObservableList<Section> ListSection;
    public static String ID_Promo;
    public static int ID_Section;
    Connection Con;
    PreparedStatement pst, pst1, pst2;
    ResultSet rs, rs1, rs2, rs3;
    Label lb;
    Config2 Conf = new Config2();
    GestionScolaritéController GSC = new GestionScolaritéController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        CodeSectionCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DésignationSectionCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
        NbrEtdSectionCol.setCellValueFactory(new PropertyValueFactory<>("Num_Etudiant"));
        NumPromoSectionCol.setCellValueFactory(new PropertyValueFactory<>("Num_Promotion"));
        CodeGroupeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        DesignationGroupCol.setCellValueFactory(new PropertyValueFactory<>("DesignationGroupe"));
        nbr_EtdGroupeCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_etd"));
        CodeSectionGroupeCol.setCellValueFactory(new PropertyValueFactory<>("Code_Section"));
        LoadDataCombo();
        promoCombo.setOnAction(event -> {
            try {

                Promo = FXCollections.observableArrayList();
                rs2 = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + promoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs2.next()) {
                    ID_Promo = rs2.getString("ID");
                    System.out.println(ID_Promo);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            LoadDataTable();
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

    public void LoadDataCombo() {
        try {

            Promo = FXCollections.observableArrayList();
            rs1 = Con.createStatement().executeQuery("SELECT Designation FROM Promotion WHERE Annee_Code='" + GetMaxYear() + "'");
            while (rs1.next()) {
                Promo.add(rs1.getString("Designation"));
            }
            promoCombo.setItems(Promo);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    public void LoadDataTable() {
        try {
            dataSection = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Section where Promotion_Numero ='" + ID_Promo + "' ");
            while (rs.next()) {
                dataSection.add(new Section(Integer.valueOf(rs.getString("ID")),
                        rs.getString("Designation"),
                        (Integer.valueOf(rs.getString("Nombre_etudiants"))),
                        Integer.valueOf(rs.getString("Promotion_Numero"))));
                SectionTable.setItems(null);
                SectionTable.setItems(dataSection);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataTableGroupe() {
        try {
            datagroupe = FXCollections.observableArrayList();
            rs2 = Con.createStatement().executeQuery("SELECT * FROM Groupe where Section_Code ='" + ID_Section + "' ");
            while (rs2.next()) {
                datagroupe.add(new Groupe(Integer.valueOf(rs2.getString("ID")),
                        rs2.getString(2),
                        Integer.valueOf(rs2.getString("Nombre_Etudiants")),
                        Integer.valueOf(rs2.getString("Section_Code"))));
            }
            groupeTable.setItems(null);
            groupeTable.setItems(datagroupe);
        } catch (Exception ex) {
            System.out.println(ex + " Groupe Table");
        }
    }

    public void ClickTableItem(MouseEvent mouseEvent) {
        Section section = SectionTable.getSelectionModel().getSelectedItem();
        ID_Section = section.getCode();
        Designation_sectionTXT.setText(section.getDesignation());
        Nbr_Etd_Section.setText(String.valueOf(section.getNum_Etudiant()));
        CodePromoSectionTXT.setText(String.valueOf(section.getNum_Promotion()));

        LoadDataTableGroupe();
    }

    public void AddSection(ActionEvent actionEvent) {
        if (promoCombo.getSelectionModel().getSelectedItem().toString().equals(null)) {
            Conf.dialog(Alert.AlertType.WARNING, "Vous devez selectionner une promotion");
        } else {
            try {

                String query = "INSERT INTO Section(Designation,Nombre_etudiants,Promotion_Numero) VALUES(?,?,?)";
                pst = Con.prepareStatement(query);
                pst.setString(1, Designation_sectionTXT.getText());
                pst.setString(2, Nbr_Etd_Section.getText());
                pst.setString(3, ID_Promo);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Section Ajoutée");
                LoadDataTable();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public void UpdateSection(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment modifier la section " + Designation_sectionTXT.getText() + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                String sql = "UPDATE Section set Nombre_etudiants ='" + Nbr_Etd_Section.getText() + "',Designation='" + Designation_sectionTXT.getText() + "' where ID='" + ID_Section + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Section Modifié");
                LoadDataTable();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void ChercherSection(KeyEvent keyEvent) {
        try {
            ListSection = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT * FROM Section where Designation  like '" + ChercherSection.getText() + "'");
            while (rs.next()) {
                ListSection.add(new Section(Integer.valueOf(rs.getString("ID")),
                        rs.getString("Designation"),
                        (Integer.valueOf(rs.getString("Nombre_etudiants"))),
                        Integer.valueOf(rs.getString("Promotion_Numero"))));
                SectionTable.setItems(ListSection);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void DeleteSection(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment supprimer la Section " + Designation_sectionTXT.getText() + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                String sql = "DELETE FROM Section where  Code='" + ID_Section + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                LoadDataTable();
                Conf.dialog(Alert.AlertType.INFORMATION, "Section supprimée");
                LoadDataTable();

            } catch (Exception e) {

                System.out.println(e);
            }
        }
    }

    public void Clear(ActionEvent actionEvent) {
        Designation_sectionTXT.clear();
        Nbr_Etd_Section.clear();
        CodePromoSectionTXT.clear();
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

    public void AddGroupeWindow(ActionEvent actionEvent) {
        GroupeController GC = new GroupeController();
        /*GC.CodeSectionTxt.setText(Code_sectionTXT.getText());
        GC.CodeSectionTxt.setEditable(false);*/
        newStage(stage, "/PFE/View/Groupe.fxml", "Groupe", true, StageStyle.UNDECORATED, false);

    }

    public static int getID_Section() {
        return ID_Section;
    }
}
