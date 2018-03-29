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
public class InfoEtudiantController implements Initializable {

    public AnchorPane Pane;
    @FXML
    private JFXTextField MatriculeEtd;

    @FXML
    private JFXTextField NomEtd;

    @FXML
    private JFXTextField PrenomEtd;

    @FXML
    private JFXTextField SexeEtd;

    @FXML
    private JFXTextField DateNaissanceEtd;

    @FXML
    private JFXTextField LieuNaissanceEtd;

    @FXML
    private JFXTextField EmailEtd;

    @FXML
    private JFXTextField AdresseEtd;

    @FXML
    private JFXTextField WillayaETD;

    @FXML
    private JFXTextField MoyenneETD;

    @FXML
    private JFXTextField UsernameETD;

    @FXML
    private JFXTextField NumGroupeETD;

    @FXML
    private JFXTextField NumEquipeETD;

    @FXML
    private JFXTextField EtatETD;

    @FXML
    private JFXButton TerminerBtn;
    EtudiantController EC = new EtudiantController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MatriculeEtd.setText(EC.getMatriculeEtd());
        NomEtd.setText(EC.getNomEtd());
        PrenomEtd.setText(EC.getPrénomEtd());
        SexeEtd.setText(EC.getSexeEtd());
        DateNaissanceEtd.setText(String.valueOf(EC.getDateNETD()));
        LieuNaissanceEtd.setText(EC.getLieuNETD());
        EmailEtd.setText(EC.getEmailETD());
        AdresseEtd.setText(EC.getAdresseETD());
        WillayaETD.setText(EC.getWillayaETD());
        MoyenneETD.setText(String.valueOf(EC.getMoyenneETD()));
        UsernameETD.setText(EC.getUsernameETD());
        NumGroupeETD.setText(String.valueOf(EC.getNumGroupeETD()));
        NumEquipeETD.setText(String.valueOf(EC.getNumEquipeETD()));
        EtatETD.setText(EC.getEtatETD());
        MatriculeEtd.setEditable(false);
        NomEtd.setEditable(false);
        PrenomEtd.setEditable(false);
        SexeEtd.setEditable(false);
        DateNaissanceEtd.setEditable(false);
        LieuNaissanceEtd.setEditable(false);
        EmailEtd.setEditable(false);
        AdresseEtd.setEditable(false);
        WillayaETD.setEditable(false);
        MoyenneETD.setEditable(false);
        UsernameETD.setEditable(false);
        NumGroupeETD.setEditable(false);
        NumEquipeETD.setEditable(false);
        EtatETD.setEditable(false);
    }

    public void Close(ActionEvent actionEvent) {
        ((Stage) Pane.getScene().getWindow()).close();
    }
}
