package PFE.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Config2 {


    public Config2() {
    }

    public static void dialog(Alert.AlertType alertType, String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    public void loadAnchorPane(AnchorPane ap, String a){
        try {
            AnchorPane p = FXMLLoader.load(getClass().getResource(a));
            ap.getChildren().setAll(p);
        } catch (IOException e) {
            dialog(Alert.AlertType.ERROR, e.getMessage());
        }
    }


    public void newStage(Stage stage, Label lb, String load, String titre, boolean resize, StageStyle style, boolean maximized){
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);

            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(titre);

            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {

            dialog(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void newAnchor(Stage stage, String load, String titre, boolean resize, StageStyle style, boolean maximized){
        try {
            Stage st = new Stage();


            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);

            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(titre);

            st.setScene(scene);
            st.show();

        } catch (Exception e) {

            dialog(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void newStage2(Stage stage, Button lb, String load, String titre, boolean resize, StageStyle style, boolean maximized){
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);
            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(titre);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {
        }
    }

    public void Loadpage(String fxml,AnchorPane AP){
        try {

            URL url = getClass().getResource(fxml);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream());

            AP.getChildren().clear();
            AP.getChildren().add(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ActuelYear(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return String.valueOf(year);

    }

    public static void setModelColumn(TableColumn tb,String a){
        tb.setCellValueFactory(new PropertyValueFactory(a));
    }

  /*  public static Vector read(String fileName)    {
        Vector cellVectorHolder = new Vector();
        try{
            FileInputStream myInput = new FileInputStream(fileName);
            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while(rowIter.hasNext()){
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                //Vector cellStoreVector=new Vector();
                List list = new ArrayList();
                while(cellIter.hasNext()){
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    list.add(myCell);
                }
                cellVectorHolder.addElement(list);
            }
        }catch (Exception e){e.printStackTrace(); }
        return cellVectorHolder;
    }
*/

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
