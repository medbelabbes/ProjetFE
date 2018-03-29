package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Enseignant;
import PFE.Model.Etudiant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Mohamed on 12/05/2017.
 */
public class EnseignantController implements Initializable {
    public JFXComboBox ChercherParCombo;
    public JFXTextField ChercherTXt;
    public JFXButton SendMailBtn;
    public JFXButton ImportBtn;
    public MenuItem SupprimeMenu;
    public MenuItem BloquerMenu;
    public MenuItem AfficherMenu;

    @FXML
    private AnchorPane PaneEnseignants;

    @FXML
    private TableView<Enseignant> tableData;

    @FXML
    private TableColumn<Enseignant, String> MatriculEnsCol;

    @FXML
    private TableColumn<Enseignant, String> NomEnsCol;

    @FXML
    private TableColumn<Enseignant, String> PrenomEnsCol;

    @FXML
    private TableColumn<Enseignant, String> SexeEnsCol;

    @FXML
    private TableColumn<Enseignant, String> DateNEnsCol;

    @FXML
    private TableColumn<Enseignant, String> LieuNEnsCol;

    @FXML
    private TableColumn<Enseignant, String> EmailEnsCol;

    @FXML
    private TableColumn<Enseignant, String> AdresseEtdCol;

    @FXML
    private TableColumn<Enseignant, String> WillayaEnsCol;

    @FXML
    private TableColumn<Enseignant, String> SpécialitéEnsCol;

    @FXML
    private TableColumn<Enseignant, String> GradensCol;

    @FXML
    private TableColumn<Enseignant, String> UsernameEnsCol;
    @FXML
    public TableColumn<Enseignant, String> EtatEnsCol;


    @FXML
    private JFXButton AddBtn;

    @FXML
    private JFXButton DeleteBtn;

    @FXML
    private JFXButton EditBtn;
    private ObservableList<Enseignant> listData;
    Connection Con;
    PreparedStatement pst;
    ResultSet rs, rs1, rs2;
    Config2 Conf = new Config2();
    Stage stage;
    Stage stage2;
    Stage stage3;
    File file;
    public static boolean b;
    public static String MatriculeEns;
    public static String NomEns;
    public static String PrenomEns;
    public static String SexeEns;
    public static java.util.Date DateNEns;
    public static String LieuNEns;
    public static String EmailEns;
    public static String AdresseEns;
    public static String WillayaEns;
    public static String GradEns;
    public static String SpecialitéEns;
    public static String EtatEns;
    private FileChooser fileChooser;
    ObservableList<String> DataEnseignants = FXCollections.observableArrayList(
            "Matricule",
            "Nom",
            "Prénom",
            "Sexe",
            "Date de naissance",
            "Lieu de naissance",
            "Email",
            "Adresse",
            "Willaya",
            "Spécialité",
            "Grade",
            "Nom d'utilisateur");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();

        ChercherParCombo.setItems(DataEnseignants);
        MatriculEnsCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEnseignant"));
        NomEnsCol.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        PrenomEnsCol.setCellValueFactory(new PropertyValueFactory<>("PrénomEnseignant"));
        SexeEnsCol.setCellValueFactory(new PropertyValueFactory<>("SexeEnseignant"));
        DateNEnsCol.setCellValueFactory(new PropertyValueFactory<>("DateNaissanceEnseignant"));
        LieuNEnsCol.setCellValueFactory(new PropertyValueFactory<>("LieuNaissanceEnseignant"));
        EmailEnsCol.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        AdresseEtdCol.setCellValueFactory(new PropertyValueFactory<>("AdresseEnseignant"));
        WillayaEnsCol.setCellValueFactory(new PropertyValueFactory<>("WillayaEnseignant"));
        SpécialitéEnsCol.setCellValueFactory(new PropertyValueFactory<>("SpécialitéEnseignant"));
        GradensCol.setCellValueFactory(new PropertyValueFactory<>("GradeEnseignant"));
        UsernameEnsCol.setCellValueFactory(new PropertyValueFactory<>("UserEnseignant"));
        EtatEnsCol.setCellValueFactory(new PropertyValueFactory<>("Etat"));
        ChangeButtonStatue();
        initCol();
        Update_Table();
    }

    public void Update_Table() {

        try {
            listData = FXCollections.observableArrayList();

            rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant");
            while (rs.next()) {
                listData.add(new Enseignant(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(14)
                ));
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        initCol();

    }

    public static boolean isB() {
        return b;
    }

    public static void setB(boolean b) {
        EnseignantController.b = b;
    }

    public void Ajouter(ActionEvent actionEvent) {
        b = true;
        Conf.loadAnchorPane(PaneEnseignants, "/PFE/View/DataEnseignants.fxml");
    }

    public void Modifier(ActionEvent actionEvent) {
        b = false;
        Conf.loadAnchorPane(PaneEnseignants, "/PFE/View/DataEnseignants.fxml");
    }

    public void ImportCSV(ActionEvent actionEvent) {
        try {
            String path;
            stage2 = new Stage();
            fileChooser = new FileChooser();
            fileChooser.setTitle("Selectionner Le fichier à insérer");
            file = fileChooser.showOpenDialog(stage2);
            path = file.getAbsolutePath();


            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] value = line.split(",");
                    String sql = "INSERT INTO Enseignant(Matricule,Nom,Prenom,Sexe,Date_Naissance,Lieu_Naissance,Email,Adresse,Willaya,Specialite,Grade,Username,Password,Etat) "
                            + "VALUES('" + value[0] + "','" + value[1] + "','" + value[2] + "','" + value[3] + "','" + value[4] + "','" + value[5] + "','" + value[6] + "','" + value[7] + "','" + value[8] + "','" + value[9] + "','" + value[10] + "','" + value[11] + "','" + value[12] + "','" + value[13] + "')";
                    pst = Con.prepareStatement(sql);
                    pst.executeUpdate();
                }
                Conf.dialog(Alert.AlertType.INFORMATION, "Insertion terminé avec Succée");
                Update_Table();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception e) {
            System.out.println(e + "");
        }
    }

    public void SendMail(ActionEvent actionEvent) {
        try {
            String host = "smtp.gmail.com";
            String user = "m.belabbes@esi-sba.dz";
            String pass = "079777mydarling4033";
            String from = "m.belabbes@esi-sba.dz";

            rs = Con.createStatement().executeQuery("SELECT Email,Username,Password FROM Enseignant ");
            while (rs.next()) {
                String to = rs.getString("Email");
                String subject = "Platform de gestion des projets: Username et Mot de passe";
                String messageText = "Ci joint le nom d'utilisateur et le mot de passe pour accéder à votre compte dans la platforme :" + "Username: " + rs.getString("Username") + "|  Mot de passe: " + rs.getString("Password");
                boolean sessionDebug = false;
                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "25");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.required", "true");

                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(sessionDebug);
                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                InternetAddress[] address = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new java.util.Date());
                msg.setText(messageText);

                Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, user, pass);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();

            }

            Conf.dialog(Alert.AlertType.INFORMATION, "sent succesful ");

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void ChercherEnseignant(KeyEvent keyEvent) {
        if (ChercherTXt.getText().equals("")) {
            Update_Table();
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Matricule")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Matricule like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Nom")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Nom like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Prénom")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Prenom like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Sexe")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Sexe like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Date de naissance")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Date_Naissance like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Lieu de naissance")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Lieu_Naissance like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Email")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Email like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Adresse")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Adresse like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Willaya")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Willaya like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Spécialité")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Specialite like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Grade")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Grade like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Nom d'utilisateur")) {
            try {
                listData = FXCollections.observableArrayList();

                rs = Con.createStatement().executeQuery("SELECT * FROM Enseignant WHERE Username like'%" + ChercherTXt.getText() + "%'");
                while (rs.next()) {
                    listData.add(new Enseignant(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDate(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(12)
                    ));
                }
                tableData.setItems(null);
                tableData.setItems(listData);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        initCol();
    }


    public void ClickTableEquipeItem(MouseEvent mouseEvent) {
        try {
            Enseignant e = tableData.getSelectionModel().getSelectedItem();
            MatriculeEns = e.getMatriculeEnseignant();
            NomEns = e.getNomEnseignant();
            PrenomEns = e.getPrénomEnseignant();
            SexeEns = e.getSexeEnseignant();
            DateNEns = e.getDateNaissanceEnseignant();
            LieuNEns = e.getLieuNaissanceEnseignant();
            EmailEns = e.getEmailEnseignant();
            AdresseEns = e.getAdresseEnseignant();
            WillayaEns = e.getWillayaEnseignant();
            GradEns = e.getGradeEnseignant();
            SpecialitéEns = e.getSpécialitéEnseignant();
            EtatEns = e.getEtat();
            ChangeButtonStatue();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getMatriculeEns() {
        return MatriculeEns;
    }

    public static String getNomEns() {
        return NomEns;
    }

    public static String getPrenomEns() {
        return PrenomEns;
    }

    public static String getSexeEns() {
        return SexeEns;
    }

    public static Date getDateNEns() {
        return DateNEns;
    }

    public static String getLieuNEns() {
        return LieuNEns;
    }

    public static String getEmailEns() {
        return EmailEns;
    }

    public static String getAdresseEns() {
        return AdresseEns;
    }

    public static String getWillayaEns() {
        return WillayaEns;
    }

    public static String getGradEns() {
        return GradEns;
    }

    public static String getSpecialitéEns() {
        return SpecialitéEns;
    }

    public static String getEtatEns() {
        return EtatEns;
    }

    public void ChangeButtonStatue() {
        try {
            rs = Con.createStatement().executeQuery("SELECT Etat FROM Enseignant where Matricule='" + MatriculeEns + "' ");
            if (rs.next()) {
                if (rs.getString("Etat").equals("Bloqué")) {
                    BloquerMenu.setText("Debloquer");
                } else if (rs.getString("Etat").equals("Autorisé")) {
                    BloquerMenu.setText("Bloquer");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SupprimerEnseignant(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer  l'etudiant ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                String sql4 = "DELETE FROM Enseignant where Matricule=?";
                pst = Con.prepareStatement(sql4);
                pst.setString(1, MatriculeEns);
                pst.execute();
                Update_Table();
                Conf.dialog(Alert.AlertType.INFORMATION, "L'enseignant:  est supprimé  ");
            } catch (Exception e) {
                System.out.println(e + " Exception delete enseignant");
            }

        }

    }

    public void BloquerEnseignant(ActionEvent actionEvent) {
        if (BloquerMenu.getText().equals("Bloquer")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Bloqué L'ensignant " + PrenomEns + " " + NomEns + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String x = "Bloqué";
                    String sql = "UPDATE Enseignant set Etat = '" + x + "' where Matricule=?";
                    pst = Con.prepareStatement(sql);
                    pst.setString(1, MatriculeEns);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Bloqué");
                    Update_Table();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        } else if (BloquerMenu.getText().equals("Debloquer")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Debloqué L'étudiant " + PrenomEns + " " + NomEns + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String x = "Autorisé";
                    String sql = "UPDATE Enseignant set Etat = '" + x + "' where Matricule=?";
                    pst = Con.prepareStatement(sql);
                    pst.setString(1, MatriculeEns);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Bloqué");
                    Update_Table();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void AfficherEnseignant(ActionEvent actionEvent) {
        newStage(stage3, "/PFE/View/InfoEnseignant.fxml", "Information d'étudiant", true, StageStyle.UNDECORATED, false);

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

    private void initCol() {
        MatriculEnsCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEnseignant"));
        NomEnsCol.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        PrenomEnsCol.setCellValueFactory(new PropertyValueFactory<>("PrénomEnseignant"));
        SexeEnsCol.setCellValueFactory(new PropertyValueFactory<>("SexeEnseignant"));
        DateNEnsCol.setCellValueFactory(new PropertyValueFactory<>("DateNaissanceEnseignant"));
        LieuNEnsCol.setCellValueFactory(new PropertyValueFactory<>("LieuNaissanceEnseignant"));
        EmailEnsCol.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        AdresseEtdCol.setCellValueFactory(new PropertyValueFactory<>("AdresseEnseignant"));
        WillayaEnsCol.setCellValueFactory(new PropertyValueFactory<>("WillayaEnseignant"));
        SpécialitéEnsCol.setCellValueFactory(new PropertyValueFactory<>("SpécialitéEnseignant"));
        GradensCol.setCellValueFactory(new PropertyValueFactory<>("GradeEnseignant"));
        UsernameEnsCol.setCellValueFactory(new PropertyValueFactory<>("UserEnseignant"));
        EtatEnsCol.setCellValueFactory(new PropertyValueFactory<>("Etat"));

        EtatEnsCol.setCellFactory(column ->  {
            return new TableCell<Enseignant, String>() {

                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");

                    } else if (item.equals("Bloqué")) {

                        setStyle("-fx-background-color: #f44336 ");
                        setText("Bloqué");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    } else {
                        setStyle("-fx-background-color: #26a69a");
                        setText("Autorisé");
                        setTextFill(Color.WHITE);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

    }

}
