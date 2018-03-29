package PFE.Controllers;

import com.jfoenix.controls.JFXTreeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import PFE.Config.Config2;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.geometry.Rectangle2D;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
    @FXML
    public Button btnLogout;
    @FXML
    public TreeView TreeData;
    @FXML
    private Button close;
    @FXML
    private Button maximize;
    @FXML
    private Button minimize;
    @FXML
    private Button resize;
    @FXML
    private Button fullscreen;
    @FXML
    private Label title;
    @FXML
    public AnchorPane paneData;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    Config2 Conf = new Config2();
    Stage stage;
    Rectangle2D rec2;
    Double w, h;
    TreeItem<String> Sections_groupes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = JavaConnection.ConnectDB();
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        TreeItem<String> root = new TreeItem<>("root");
        TreeItem<String> Gestion_scolarité = new TreeItem<>("Gestion scoalarité");
        TreeItem<String> Gestion_Projets = new TreeItem<>("Gestion des Projets");
        TreeItem<String> Promotion_Item = new TreeItem<>("Promotion");
        Sections_groupes = new TreeItem<>("Sections et Groupes");
        TreeItem<String> Etudiants = new TreeItem<>("Etudiants");
        TreeItem<String> Equipes = new TreeItem<>("Equipes");
        TreeItem<String> Enseignats = new TreeItem<>("Enseignats");
        TreeItem<String> Projets = new TreeItem<>("Projets");
        TreeItem<String> paramétres = new TreeItem<>("Paramétres");
        TreeItem<String> affectation = new TreeItem<>("Affectation");
        TreeItem<String> Soutenance = new TreeItem<>("Gestion de soutenance");
        TreeItem<String> Jurys = new TreeItem<>("Juries");
        TreeItem<String> Soutenances = new TreeItem<>("Soutenances");

        root.getChildren().setAll(Gestion_scolarité, Gestion_Projets, Soutenance);
        Gestion_scolarité.getChildren().setAll(Promotion_Item, Sections_groupes, Etudiants, Enseignats);
        Gestion_Projets.getChildren().setAll(paramétres, Equipes, Projets, affectation);
        Soutenance.getChildren().setAll(Soutenances, Jurys);
        root.setExpanded(true);
        TreeData.setShowRoot(false);
        TreeData.setRoot(root);
        TreeData.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<TreeItem<String>>() {

                    @Override
                    public void changed(
                            ObservableValue<? extends TreeItem<String>> observable,
                            TreeItem<String> old_val, TreeItem<String> new_val) {
                        TreeItem<String> selectedItem = new_val;
                        System.out.println("Selected Text : " + selectedItem.getValue());
                        if (selectedItem.getValue().equals("Etudiants")) {
                            System.out.println("Etudiants");
                            Conf.loadAnchorPane(paneData, "/PFE/View/Etudiants.fxml");
                        } else if (selectedItem.getValue().equals("Promotion")) {
                            System.out.println("Promotion");
                            Conf.loadAnchorPane(paneData, "/PFE/View/GestionScolarité.fxml");
                        } else if (selectedItem.getValue().equals("Sections et Groupes")) {
                            System.out.println("Promotion");
                            Conf.loadAnchorPane(paneData, "/PFE/View/Section_Groupes.fxml");
                        } else if (selectedItem.getValue().equals("Enseignats")) {
                            System.out.println("Enseignats");
                            Conf.loadAnchorPane(paneData, "/PFE/View/Enseignants.fxml");
                        } else if (selectedItem.getValue().equals("Equipes")) {
                            System.out.println("Equipes");
                            Conf.loadAnchorPane(paneData, "/PFE/View/GestionEquipe.fxml");
                        } else if (selectedItem.getValue().equals("Projets")) {
                            System.out.println("Projets");
                            Conf.loadAnchorPane(paneData, "/PFE/View/Projets.fxml");
                        } else if (selectedItem.getValue().equals("Paramétres")) {
                            Conf.loadAnchorPane(paneData, "/PFE/View/Parametres.fxml");
                        } else if (selectedItem.getValue().equals("Affectation")) {
                            Conf.loadAnchorPane(paneData, "/PFE/View/Affectation.fxml");
                        } else if (selectedItem.getValue().equals("Soutenances")) {
                            System.out.println("Soutenances");
                            Conf.loadAnchorPane(paneData, "/PFE/View/GestionSoutenance.fxml");

                        } else if (selectedItem.getValue().equals("Juries")) {
                            System.out.println("Jurys");
                            Conf.loadAnchorPane(paneData, "/PFE/View/GestionJury.fxml");
                        }
                    }

                });

        Platform.runLater(() -> {

            stage = (Stage) maximize.getScene().getWindow();
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);


        });
        TreeData.getSelectionModel().select(0);

        Conf.loadAnchorPane(paneData, "/PFE/View/GestionScolarité.fxml");
        Gestion_scolarité.setExpanded(true);
        Gestion_Projets.setExpanded(true);
        Soutenance.setExpanded(true);
    }


    @FXML
    private void Resize(ActionEvent event) {
    }

    public void fullscreen(ActionEvent actionEvent) {
        stage = (Stage) fullscreen.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }

    public void Minimize(ActionEvent actionEvent) {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        } else {
            stage.setIconified(true);
        }
    }

    @FXML
    private void Maximize(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
            if (w == rec2.getWidth() && h == rec2.getHeight()) {
                stage.setMaximized(false);
                stage.setHeight(600);
                stage.setWidth(800);
                stage.centerOnScreen();
                maximize.getStyleClass().remove("decoration-button-restore");
                resize.setVisible(true);
            } else {
                stage.setMaximized(false);
                maximize.getStyleClass().remove("decoration-button-restore");
                resize.setVisible(true);
            }

        } else {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);
        }
    }

    @FXML
    private void Close_Window(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void Logout(ActionEvent event) {
        Config2 config = new Config2();
        config.newStage2(stage, btnLogout, "/PFE/View/Login.fxml", "Sample Apps", true, StageStyle.UNDECORATED, false);
    }


}
