package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Parametres;
import PFE.Model.Promotion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ValueRange;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by Mohamed on 04/06/2017.
 */
public class ParamétresController implements Initializable {

    public AnchorPane PaneData;
    public TableView<Parametres> ParametreTable;
    public TableColumn<Parametres, String> IdParametreCol;
    public TableColumn<Parametres, String> PromoParametreCol;
    public TableColumn<Parametres, String> MaxParametreCol;
    public TableColumn<Parametres, String> MinParametreCol;
    public TableColumn<Parametres, String> Mode_AffecParametreCol;
    public TableColumn<Parametres, String> Nbr_CHoixParametreCol;
    public TableColumn<Parametres, String> DuréeParametreCol;
    public TableColumn<Parametres, String> CoeffiParametreCol;
    public JFXComboBox<String> AnneeCombo;
    public JFXComboBox<String> PromoCombo;
    public Spinner Nbr_Max_Spinner;
    public Spinner Nbr_Min_Spinner;
    public Spinner Nbr_Choix_Spinner;
    public Spinner Coeffi_Spinner;
    public JFXComboBox<String> Mode_Affec_Combo;
    public JFXTextField DuréeTxt;
    public JFXButton SaveBtn;
    public JFXButton SupprimerBtn;
    public JFXButton ModifierBtn;
    private String INITAL_VALUE = "0";
    public static int ID_Paramétres;
    public static String ID_Promo;
    Config2 Conf = new Config2();
    Connection Con;
    PreparedStatement pst;
    ResultSet rs, rs1, rs3;
    private ObservableList<String> Année;
    private ObservableList<String> Promo;
    private ObservableList<Parametres> ParamétresList;
    private ObservableList<String> AffectationList = FXCollections.observableArrayList("Fiche de voeux", "Aléatoire");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        IdParametreCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        PromoParametreCol.setCellValueFactory(new PropertyValueFactory<>("Promotion"));
        MaxParametreCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Max"));
        MinParametreCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Min"));
        Mode_AffecParametreCol.setCellValueFactory(new PropertyValueFactory<>("Affectation_Mode"));
        Nbr_CHoixParametreCol.setCellValueFactory(new PropertyValueFactory<>("Nbr_Choix"));
        DuréeParametreCol.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        CoeffiParametreCol.setCellValueFactory(new PropertyValueFactory<>("Coefficient"));
        Nbr_Max_Spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));
        Nbr_Max_Spinner.setEditable(true);

        Nbr_Min_Spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));
        Nbr_Min_Spinner.setEditable(true);
        Nbr_Choix_Spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));
        Nbr_Choix_Spinner.setEditable(true);
        Coeffi_Spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));
        Coeffi_Spinner.setEditable(true);
        Mode_Affec_Combo.setItems(AffectationList);
        LoadDataComboAnnee();
        AnneeCombo.setOnAction(event -> {
                    LoadDataComboPromo();
                    Update_Table();
                }
        );
        PromoCombo.setOnAction(event -> {
            try {
                rs = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs.next()) {
                    ID_Promo = rs.getString("ID");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }


    public void LoadDataComboAnnee() {
        try {

            Année = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Code FROM Annee ");
            while (rs.next()) {
                Année.add(rs.getString("Code"));

            }
            AnneeCombo.setItems(Année);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadDataComboPromo() {
        try {

            Promo = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Designation FROM Promotion WHERE Annee_Code='" + AnneeCombo.getSelectionModel().getSelectedItem().toString() + "' OR Annee_Code='" + GetMaxYear() + "' ");
            while (rs.next()) {
                Promo.add(rs.getString("Designation"));

            }
            PromoCombo.setItems(Promo);
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

    @FXML
    public void Update_Table() {
        ParamétresList = FXCollections.observableArrayList();
        try {
            rs1 = Con.createStatement().executeQuery("SELECT ID FROM Promotion where Annee_Code ='" + AnneeCombo.getSelectionModel().getSelectedItem().toString() + "' OR Annee_Code='" + GetMaxYear() + "'");
            while (rs1.next()) {
                rs = Con.createStatement().executeQuery("SELECT * FROM Parametre where Promotion_Numero ='" + rs1.getString("ID") + "' ");
                while (rs.next()) {
                    ParamétresList.add(new Parametres(Integer.valueOf(rs.getString("ID")),
                            Integer.valueOf(rs.getString("Promotion_Numero")),
                            Integer.valueOf(rs.getString("Nbr_max")),
                            Integer.valueOf(rs.getString("Nbr_min")),
                            rs.getString("Affection_mode"),
                            Integer.valueOf(rs.getString("Nbr_max")),
                            rs.getString("Duree"),
                            Integer.valueOf(rs.getString("Coefficient"))
                    ));
                    ParametreTable.setItems(null);
                    ParametreTable.setItems(ParamétresList);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void Save(ActionEvent actionEvent) {
        try {
            int NbrMax = (Integer) Nbr_Max_Spinner.getValue();
            int NbrMin = (Integer) Nbr_Min_Spinner.getValue();
            int NbrChoix = (Integer) Nbr_Choix_Spinner.getValue();
            int Coeffi = (Integer) Coeffi_Spinner.getValue();
            String querry = "INSERT INTO Parametre(Promotion_Numero,Nbr_max,Nbr_min,Affection_mode,Nombre_Choix,Duree,Coefficient) VALUES(?,?,?,?,?,?,?)";
            pst = Con.prepareStatement(querry);
            pst.setString(1, ID_Promo);
            pst.setString(2, String.valueOf(NbrMax));
            pst.setString(3, String.valueOf(NbrMin));
            pst.setString(4, Mode_Affec_Combo.getSelectionModel().getSelectedItem().toString());
            pst.setString(5, String.valueOf(NbrChoix));
            pst.setString(6, DuréeTxt.getText());
            pst.setString(7, String.valueOf(Coeffi));
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Saved!!");
            Update_Table();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ClickTableItem(MouseEvent mouseEvent) {
        try {
            ObservableList<Integer> Numbers = FXCollections.observableArrayList();
            SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(Numbers);
            SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(Numbers);
            SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(Numbers);
            SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(Numbers);
            Parametres param = ParametreTable.getSelectionModel().getSelectedItem();
            ID_Paramétres = param.getID();
            PromoCombo.setValue(String.valueOf(param.getPromotion()));
            valueFactory1.setValue(param.getNbr_Max());
            Nbr_Max_Spinner.setValueFactory(valueFactory1);
            valueFactory2.setValue(param.getNbr_Min());
            Nbr_Min_Spinner.setValueFactory(valueFactory2);
            valueFactory3.setValue(param.getNbr_Choix());
            Nbr_Choix_Spinner.setValueFactory(valueFactory3);
            valueFactory4.setValue(param.getCoefficient());
            Coeffi_Spinner.setValueFactory(valueFactory4);
            Mode_Affec_Combo.setValue(param.getAffectation_Mode());
            DuréeTxt.setText(param.getDuree());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ModifierParametres(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiement Modifier les paramétres  de la promotion :" + PromoCombo.getSelectionModel().getSelectedItem().toString() + " ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                String sql = "UPDATE Parametre set Promotion_Numero ='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "',Nbr_max='" + Nbr_Max_Spinner.getValue() + "',Nbr_min='" + Nbr_Min_Spinner.getValue() + "',Affection_mode='" + Mode_Affec_Combo.getSelectionModel().getSelectedItem().toString() + "',Nombre_Choix='" + Nbr_Choix_Spinner.getValue() + "', Duree='" + DuréeTxt.getText() + "' WHERE ID='" + ID_Paramétres + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Promotion Modifié");
                Update_Table();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void DeleteParemetres(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous étes sur de supprimer ces paramétres ?");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                String sql = "DELETE FROM Parametre where  ID='" + ID_Paramétres + "'";
                pst = Con.prepareStatement(sql);
                pst.execute();
                Conf.dialog(Alert.AlertType.INFORMATION, "Paramétres supprimée");
                Update_Table();
            } catch (Exception e) {

                System.out.println(e);
            }
        }
    }
}
