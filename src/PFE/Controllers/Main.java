package PFE.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import PFE.Config.*;

import java.sql.*;

public class Main extends Application {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;


    public Main() {
        con=JavaConnection.ConnectDB();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        String query="Select count(*) from Adminstrateur";
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
          if(rs.next()){
              int x= new Integer(rs.getString("count(*)"));
              if (x>0){
                  Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
                  primaryStage.setScene(new Scene(root, 600, 500));
                  primaryStage.initStyle(StageStyle.UNDECORATED);
              }
              else{
                  Parent root = FXMLLoader.load(getClass().getResource("../View/SignUP.fxml"));
                  primaryStage.setScene(new Scene(root, 433, 696));
                  primaryStage.initStyle(StageStyle.UNDECORATED);
              }
          }

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);}
}
