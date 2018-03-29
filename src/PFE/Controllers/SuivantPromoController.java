package PFE.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import PFE.Config.Config2;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Mohamed on 05/05/2017.
 */
public class SuivantPromoController implements Initializable {
    public JFXRadioButton AddRadioBtn;
    public JFXRadioButton NoAddRadioBtn;
    public JFXButton SuivantBtn;
    Connection Con;
    ResultSet rs;
    PreparedStatement pst;
    Config2 Conf = new Config2();

    Rectangle2D rec2;
    Double w,h;
    Stage stage;
    @FXML
    private Label lblClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        Platform.runLater(() -> {
            lblClose.setOnMouseClicked((MouseEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
    }
    public void Save(ActionEvent actionEvent) {
        if(AddRadioBtn.isSelected()){
            Conf.newStage(stage, lblClose, "/PFE/View/Promotion.fxml", "Test App", true, StageStyle.UNDECORATED, false);
        }
        else if (NoAddRadioBtn.isSelected()){
            Conf.newStage(stage, lblClose, "/PFE/View/Main.fxml", "Test App", true, StageStyle.UNDECORATED, false);
        }

    }


}
