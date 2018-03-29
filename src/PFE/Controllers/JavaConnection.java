package PFE.Controllers;

import java.sql.*;

public class JavaConnection {

    public static Connection con;




        public static void main(String[] args){
           con= ConnectDB();
        }

    public static Connection ConnectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/PFE";
            String user="root";
            String password="root";
            con= DriverManager.getConnection(url,user,password);
            System.out.println("Connection bien établié");
            return con;


        }catch(Exception e){

            System.out.println(e.getMessage());

            return null;
        }
    }
    }

