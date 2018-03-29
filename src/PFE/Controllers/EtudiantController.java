package PFE.Controllers;

import PFE.Config.Config2;
import PFE.Model.Equipe;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Mohamed on 25/04/2017.
 */
public class EtudiantController implements Initializable {


    public TableView<Etudiant> tableData;
    public TableColumn<Etudiant, String> MatriculeEtdCol;
    public TableColumn<Etudiant, String> NomEtdCol;
    public TableColumn<Etudiant, String> PrenomEtdCol;
    public TableColumn<Etudiant, String> SexeEtdCol;
    public TableColumn<Etudiant, String> DateNEtdCol;
    public TableColumn<Etudiant, String> LieuNEtdCol;
    public TableColumn<Etudiant, String> EmailEtdCol;
    public TableColumn<Etudiant, String> AdresseEtdCol;
    public TableColumn<Etudiant, String> WillayaEtdCol;
    public TableColumn<Etudiant, String> MoyenneEtdCol;
    public TableColumn<Etudiant, String> UsernameEtdCol;
    public TableColumn<Etudiant, String> NumGroupeEtdCol;
    public TableColumn<Etudiant, String> NumeEquipeEtdCol;
    public TableColumn<Etudiant, String> EtatEtdCol;
    public TableColumn<Etudiant, String> QualiteEtdCol;
    public TableColumn<Etudiant, String> PromoIDEtdCol;
    public JFXButton AddBtn;
    public JFXButton DeleteBtn;
    public JFXButton EditBtn;
    public JFXComboBox PromoCombo;
    @FXML
    public AnchorPane PaneTableEtd;
    public JFXButton ImportBtn;
    public JFXButton SendMailBtn;
    public JFXTextField ChercherTXt;
    public JFXComboBox ChercherParCombo;
    public MenuItem SuppEtdMenu;
    public MenuItem BloqueETDMenu;
    public MenuItem AfficherEtdDataMenu;

    private ObservableList<Etudiant> listData;
    Connection Con;
    PreparedStatement pst;
    ResultSet rs, rs1, rs2, rs3;
    Config2 Conf = new Config2();
    Stage stage;
    Stage stage2;
    File file;
    Stage stage3;
    private FileChooser fileChooser;
    Rectangle2D rec2;
    Double w, h;
    public static String ID_Promo;
    public static int index_combo;
    public static boolean b;
    public static String MatriculeEtd;
    public static String NomEtd;
    public static String PrénomEtd;
    public static String SexeEtd;
    public static java.util.Date DateNETD;
    public static String LieuNETD;
    public static String EmailETD;
    public static String AdresseETD;
    public static String WillayaETD;
    public static float MoyenneETD;
    public static String NiveauETD;
    public static String UsernameETD;
    public static int NumEquipeETD;
    public static int NumGroupeETD;
    public static String EtatETD;
    public static int PromoID;
    public static String QualiteEtd;


    private ObservableList<String> Promo;
    ObservableList<String> DataEtudiants = FXCollections.observableArrayList(
            "Matricule",
            "Nom",
            "Prénom",
            "Sexe",
            "Date de naissance",
            "Lieu de naissance",
            "Email",
            "Adresse",
            "Willaya",
            "Moyenne",
            "Niveau",
            "Nom d'utilisateur",
            "Numéro de groupe",
            "Numéro d'équipe");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Con = JavaConnection.ConnectDB();
        ChangeButtonStatue();
        MatriculeEtdCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEtudiant"));
        NomEtdCol.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        PrenomEtdCol.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        SexeEtdCol.setCellValueFactory(new PropertyValueFactory<>("SexeEtudiant"));
        DateNEtdCol.setCellValueFactory(new PropertyValueFactory<>("DateNaissanceEtudiant"));
        LieuNEtdCol.setCellValueFactory(new PropertyValueFactory<>("LieuNaissanceEtudiant"));
        EmailEtdCol.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
        AdresseEtdCol.setCellValueFactory(new PropertyValueFactory<>("AdresseEtudiant"));
        WillayaEtdCol.setCellValueFactory(new PropertyValueFactory<>("WillayaEtudiant"));
        MoyenneEtdCol.setCellValueFactory(new PropertyValueFactory<>("MoyenneEtudiant"));
        UsernameEtdCol.setCellValueFactory(new PropertyValueFactory<>("UsernameEtudiant"));
        NumGroupeEtdCol.setCellValueFactory(new PropertyValueFactory<>("NumGroupEtudiant"));
        NumeEquipeEtdCol.setCellValueFactory(new PropertyValueFactory<>("NumEquipEtudiant"));
        EtatEtdCol.setCellValueFactory(new PropertyValueFactory<>("Etat"));
        QualiteEtdCol.setCellValueFactory(new PropertyValueFactory<>("Qualité"));
        PromoIDEtdCol.setCellValueFactory(new PropertyValueFactory<>("Promo_ID"));
        ChercherParCombo.setItems(DataEtudiants);
        PromoCombo.setOnAction(event -> {
            try {
                rs3 = Con.createStatement().executeQuery("SELECT ID FROM Promotion WHERE Designation='" + PromoCombo.getSelectionModel().getSelectedItem().toString() + "'");
                if (rs3.next()) {
                    ID_Promo = rs3.getString("ID");
                    initCol();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
            Update_Table();
        });
        LoadDataCombo();

    }

    public void Update_Table() {

        try {
            listData = FXCollections.observableArrayList();
            rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
            while (rs1.next()) {
                rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                while (rs2.next()) {
                    rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "'");
                    while (rs.next()) {
                        if (rs.getString(15) == null) {
                            listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getDate(5), rs.getString(6),
                                    rs.getString(7), rs.getString(8), rs.getString(9),
                                    Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                    0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                        } else {

                            listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getDate(5), rs.getString(6),
                                    rs.getString(7), rs.getString(8), rs.getString(9),
                                    Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                    Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                        }
                    }

                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        initCol();
        tableData.setItems(null);
        tableData.setItems(listData);
    }


    public void LoadDataCombo() {
        try {

            Promo = FXCollections.observableArrayList();
            rs = Con.createStatement().executeQuery("SELECT Designation FROM Promotion WHERE Annee_Code='" + GetMaxYear() + "'");
            while (rs.next()) {
                Promo.add(rs.getString("Designation"));
            }
            PromoCombo.setItems(null);
            PromoCombo.setItems(Promo);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
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

    public void ClickTableEquipeItem1(MouseEvent mouseEvent) {
        try {
            Etudiant e = tableData.getSelectionModel().getSelectedItem();
            MatriculeEtd = e.getMatriculeEtudiant();
            NomEtd = e.getNomEtudiant();
            PrénomEtd = e.getPrenomEtudiant();
            SexeEtd = e.getSexeEtudiant();
            DateNETD = e.getDateNaissanceEtudiant();
            LieuNETD = e.getLieuNaissanceEtudiant();
            EmailETD = e.getEmailEtudiant();
            AdresseETD = e.getAdresseEtudiant();
            WillayaETD = e.getWillayaEtudiant();
            MoyenneETD = e.getMoyenneEtudiant();
            UsernameETD = e.getUsernameEtudiant();
            NumEquipeETD = e.getNumEquipEtudiant();
            NumGroupeETD = e.getNumGroupEtudiant();
            EtatETD = e.getEtat();
            QualiteEtd = e.getQualité();
            PromoID = e.getPromo_ID();
            ChangeButtonStatue();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getPromoID() {
        return PromoID;
    }

    public static String getQualiteEtd() {
        return QualiteEtd;
    }

    public void Ajouter(ActionEvent actionEvent) {
        b = true;
        index_combo = PromoCombo.getSelectionModel().getSelectedIndex();
        Conf.loadAnchorPane(PaneTableEtd, "/PFE/View/DataEtudiants.fxml");
    }

    public void ModifierETD(ActionEvent actionEvent) {
        b = false;
        index_combo = PromoCombo.getSelectionModel().getSelectedIndex();
        Conf.loadAnchorPane(PaneTableEtd, "/PFE/View/DataEtudiants.fxml");
    }

    public static boolean isB() {
        return b;
    }

    public static void setB(boolean b) {
        EtudiantController.b = b;
    }

    public void ImportCSV(ActionEvent actionEvent) throws FileNotFoundException {
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
                    String sql = "INSERT INTO Etudiant(Matricule,Nom,Prenom,Sexe,Date_Naissance,Lieu_Naissance,Email,Adresse,Willaya,Moyenne,Niveau,Username,Password,Groupe_Numero,Etat) "
                            + "VALUES('" + value[0] + "','" + value[1] + "','" + value[2] + "','" + value[3] + "','" + value[4] + "','" + value[5] + "','" + value[6] + "','" + value[7] + "','" + value[8] + "','" + value[9] + "','" + value[10] + "','" + value[11] + "','" + value[12] + "','" + value[13] + "','" + value[14] + "')";
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

    public static String getID_Promo() {
        return ID_Promo;
    }

    public static int getIndex_combo() {
        return index_combo;
    }

    public void SendMail(ActionEvent actionEvent) {
        try {
            String host = "smtp.gmail.com";
            String user = "m.belabbes@esi-sba.dz";
            String pass = "079777mydarling4033";
            String from = "m.belabbes@esi-sba.dz";
            rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
            while (rs1.next()) {
                rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                while (rs2.next()) {
                    rs = Con.createStatement().executeQuery("SELECT Email,Username,Password FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "'");
                    while (rs.next()) {
                        String to = rs.getString("Email");
                        String subject = "Platform de gestion des projets: Username et Mot de passe";
                        String messageText = "Ci joint le nom d'utilisateur et le mot de passe pour accéder à votre compte dans la platforme :" + "\n" + "Username: " + rs.getString("Username") + "\n" + "  Mot de passe: " + rs.getString("Password");
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
                        msg.setSentDate(new Date());
                        msg.setText(messageText);

                        Transport transport = mailSession.getTransport("smtp");
                        transport.connect(host, user, pass);
                        transport.sendMessage(msg, msg.getAllRecipients());
                        transport.close();

                    }
                }
                Conf.dialog(Alert.AlertType.INFORMATION, "sent succesful ");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ChercherEtudiant(KeyEvent keyEvent) {
        if (ChercherTXt.getText().equals("")) {
            Update_Table();
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Matricule")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Matricule like %'" + ChercherTXt.getText() + "'%");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Nom")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Nom like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);

        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Prénom")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Prenom like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Sexe")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Sexe like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Date de naissance")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Date_Naissance like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Lieu de naissance")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Lieu_Naissance like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Email")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Email like'%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Adresse")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Adresse like'%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Willaya")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Willaya like'%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Moyenne")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Moyenne like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Nom d'utilisateur")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Username like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Numéro de groupe")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Groupe_Numero like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        } else if (ChercherParCombo.getSelectionModel().getSelectedItem().toString().equals("Numéro d'équipe")) {
            try {
                listData = FXCollections.observableArrayList();
                rs1 = Con.createStatement().executeQuery("SELECT ID FROM Section where Promotion_Numero='" + ID_Promo + "'");
                while (rs1.next()) {
                    rs2 = Con.createStatement().executeQuery("SELECT ID FROM Groupe where Section_Code='" + rs1.getString("ID") + "'");
                    while (rs2.next()) {
                        rs = Con.createStatement().executeQuery("SELECT * FROM Etudiant where Groupe_Numero='" + rs2.getString("ID") + "' and Equipe_Numero like '%" + ChercherTXt.getText() + "%'");
                        while (rs.next()) {
                            if (rs.getString(15) == null) {
                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        0, rs.getString(17), Integer.valueOf(rs.getString(18))));
                            } else {

                                listData.add(new Etudiant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                        rs.getDate(5), rs.getString(6),
                                        rs.getString(7), rs.getString(8), rs.getString(9),
                                        Float.valueOf(rs.getString(10)), rs.getString(11), rs.getString(13), Integer.valueOf(rs.getString(14)),
                                        Integer.valueOf(rs.getString(15)), rs.getString(17), Integer.valueOf(rs.getString(18))));
                            }
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            tableData.setItems(null);
            tableData.setItems(listData);
        }
    }

    public static String getMatriculeEtd() {
        return MatriculeEtd;
    }

    public static String getNomEtd() {
        return NomEtd;
    }

    public static String getPrénomEtd() {
        return PrénomEtd;
    }


    public static String getSexeEtd() {
        return SexeEtd;
    }

    public static java.util.Date getDateNETD() {
        return DateNETD;
    }

    public static String getLieuNETD() {
        return LieuNETD;
    }

    public static String getEmailETD() {
        return EmailETD;
    }

    public static String getAdresseETD() {
        return AdresseETD;
    }

    public static String getWillayaETD() {
        return WillayaETD;
    }

    public static float getMoyenneETD() {
        return MoyenneETD;
    }

    public static String getNiveauETD() {
        return NiveauETD;
    }

    public static String getUsernameETD() {
        return UsernameETD;
    }

    public static int getNumEquipeETD() {
        return NumEquipeETD;
    }

    public static int getNumGroupeETD() {
        return NumGroupeETD;
    }

    public static String getEtatETD() {
        return EtatETD;
    }

    public void SupprimerEtudiant(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Supprimer  l'etudiant ");
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            try {
                String sql4 = "DELETE FROM etudiant where Matricule=?";
                pst = Con.prepareStatement(sql4);
                pst.setString(1, MatriculeEtd);
                pst.execute();
                Update_Table();
                Conf.dialog(Alert.AlertType.INFORMATION, "L'etudiant:  est supprimé  ");
            } catch (Exception e) {
                System.out.println(e + " Exception delete etudiant");
            }

        }
    }

    public void BloquerEtudiant(ActionEvent actionEvent) {
        if (BloqueETDMenu.getText().equals("Bloquer")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Bloqué L'étudiant " + PrénomEtd + " " + NomEtd + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String x = "Bloqué";
                    String sql = "UPDATE Etudiant set Etat = '" + x + "' where Matricule=?";
                    pst = Con.prepareStatement(sql);
                    pst.setString(1, MatriculeEtd);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Bloqué");
                    Update_Table();
                    initCol();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else if (BloqueETDMenu.getText().equals("Debloquer")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment Debloqué L'étudiant " + PrénomEtd + " " + NomEtd + " ?");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String x = "Autorisé";
                    String sql = "UPDATE Etudiant set Etat = '" + x + "' where Matricule=?";
                    pst = Con.prepareStatement(sql);
                    pst.setString(1, MatriculeEtd);
                    pst.execute();
                    Conf.dialog(Alert.AlertType.INFORMATION, "Etudiant Bloqué");
                    Update_Table();
                    initCol();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void ChangeButtonStatue() {
        try {
            rs = Con.createStatement().executeQuery("SELECT Etat FROM Etudiant where Matricule='" + MatriculeEtd + "' ");
            if (rs.next()) {
                if (rs.getString("Etat").equals("Bloqué")) {
                    BloqueETDMenu.setText("Debloquer");
                } else if (rs.getString("Etat").equals("Autorisé")) {
                    BloqueETDMenu.setText("Bloquer");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AfficherEtudiant(ActionEvent actionEvent) {
        newStage(stage3, "/PFE/View/InfoEtudiant.fxml", "Information d'étudiant", true, StageStyle.UNDECORATED, false);

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
        MatriculeEtdCol.setCellValueFactory(new PropertyValueFactory<>("MatriculeEtudiant"));
        NomEtdCol.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        PrenomEtdCol.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        SexeEtdCol.setCellValueFactory(new PropertyValueFactory<>("SexeEtudiant"));
        DateNEtdCol.setCellValueFactory(new PropertyValueFactory<>("DateNaissanceEtudiant"));
        LieuNEtdCol.setCellValueFactory(new PropertyValueFactory<>("LieuNaissanceEtudiant"));
        EmailEtdCol.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
        AdresseEtdCol.setCellValueFactory(new PropertyValueFactory<>("AdresseEtudiant"));
        WillayaEtdCol.setCellValueFactory(new PropertyValueFactory<>("WillayaEtudiant"));
        MoyenneEtdCol.setCellValueFactory(new PropertyValueFactory<>("MoyenneEtudiant"));
        UsernameEtdCol.setCellValueFactory(new PropertyValueFactory<>("UsernameEtudiant"));
        NumGroupeEtdCol.setCellValueFactory(new PropertyValueFactory<>("NumGroupEtudiant"));
        NumeEquipeEtdCol.setCellValueFactory(new PropertyValueFactory<>("NumEquipEtudiant"));
        EtatEtdCol.setCellValueFactory(new PropertyValueFactory<>("Etat"));
        QualiteEtdCol.setCellValueFactory(new PropertyValueFactory<>("Qualité"));
        PromoIDEtdCol.setCellValueFactory(new PropertyValueFactory<>("Promo_ID"));

        EtatEtdCol.setCellFactory(column -> {
            return new TableCell<Etudiant, String>() {

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

