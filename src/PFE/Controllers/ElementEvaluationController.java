package PFE.Controllers;

import PFE.Config.Config2;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OMAR-ESI on 11/06/2017.
 */
public class ElementEvaluationController implements Initializable {
    Connection Con;
    ResultSet rs;
    PreparedStatement pst;
    @FXML
    private AnchorPane PaneData;

    @FXML
    private Spinner LogicielSPinner;

    @FXML
    private Spinner AppIndiviSpinner;

    @FXML
    private Spinner AppGLobaleSPinner;

    @FXML
    private Spinner PresentationSpinner;

    @FXML
    private Spinner LivrableSpinner;
    Config2 Conf = new Config2();
    @FXML
    private JFXButton SaveBtn;
    private String INITAL_VALUE = "0";
    GestionScolaritéController G = new GestionScolaritéController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        LogicielSPinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));

        AppIndiviSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));

        AppGLobaleSPinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));

        PresentationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));

        LivrableSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt(INITAL_VALUE)));
    }


    public void SaveEvaluation(ActionEvent actionEvent) {
        try {
            int Logiciel = (Integer) LogicielSPinner.getValue();
            int AppInd = (Integer) AppIndiviSpinner.getValue();
            int AppGlobal = (Integer) AppGLobaleSPinner.getValue();
            int Presentation = (Integer) PresentationSpinner.getValue();
            int Livrable = (Integer) LivrableSpinner.getValue();
            String querry = "INSERT INTO Element_Evaluation(Coe_Logiciel,Coe_Livrable,Coe_Presentation,Coe_AppGlobale,Coe_AppIndividu,Promotion_ID) VALUES(?,?,?,?,?,?)";
            pst = Con.prepareStatement(querry);
            pst.setString(1, String.valueOf(Logiciel));
            pst.setString(2, String.valueOf(Livrable));
            pst.setString(3, String.valueOf(Presentation));
            pst.setString(4, String.valueOf(AppGlobal));
            pst.setString(5, String.valueOf(AppInd));
            pst.setString(6, G.getCodePromo());
            pst.execute();
            Conf.dialog(Alert.AlertType.INFORMATION, "Saved!!");
            ((Stage) PaneData.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Cancel(ActionEvent actionEvent) {
        ((Stage) PaneData.getScene().getWindow()).close();
    }
}
