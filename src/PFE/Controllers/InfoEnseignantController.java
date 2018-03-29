package PFE.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 09/06/2017.
 */
public class InfoEnseignantController implements Initializable {

    public AnchorPane Pane;
    @FXML
    private JFXTextField MatriculeEns;

    @FXML
    private JFXTextField NomEns;

    @FXML
    private JFXTextField PrenomEns;

    @FXML
    private JFXTextField SexeEns;

    @FXML
    private JFXTextField DateNaissanceEns;

    @FXML
    private JFXTextField LieuNaissanceEns;

    @FXML
    private JFXTextField EmailEns;

    @FXML
    private JFXTextField AdresseEns;

    @FXML
    private JFXTextField WillayaEns;

    @FXML
    private JFXTextField SpecialiteEns;

    @FXML
    private JFXTextField GradeEns;

    @FXML
    private JFXButton TerminerBtn;
    EnseignantController ENS = new EnseignantController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MatriculeEns.setText(ENS.getMatriculeEns());
        NomEns.setText(ENS.getNomEns());
        PrenomEns.setText(ENS.getPrenomEns());
        SexeEns.setText(ENS.getSexeEns());
        DateNaissanceEns.setText(String.valueOf(ENS.getDateNEns()));
        LieuNaissanceEns.setText(ENS.getLieuNEns());
        EmailEns.setText(ENS.getEmailEns());
        AdresseEns.setText(ENS.getAdresseEns());
        WillayaEns.setText(ENS.getWillayaEns());
        GradeEns.setText(String.valueOf(ENS.getGradEns()));
        SpecialiteEns.setText(ENS.getSpecialitéEns());
        MatriculeEns.setEditable(false);
        NomEns.setEditable(false);
        PrenomEns.setEditable(false);
        SexeEns.setEditable(false);
        DateNaissanceEns.setEditable(false);
        LieuNaissanceEns.setEditable(false);
        EmailEns.setEditable(false);
        AdresseEns.setEditable(false);
        WillayaEns.setEditable(false);
        SpecialiteEns.setEditable(false);
        GradeEns.setEditable(false);
    }

    public void Close(ActionEvent actionEvent) {
        ((Stage) Pane.getScene().getWindow()).close();
    }
}
