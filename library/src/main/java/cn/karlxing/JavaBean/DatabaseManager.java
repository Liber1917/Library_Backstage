package cn.karlxing.JavaBean;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

import cn.karlxing.controller.IAction;

public class DatabaseManager {
    private Connection cn;
    public  DatabaseManager(String jdbcDriver,String dbUrl,String dbUser,String dbPassword) {
        try{
            Class.forName(jdbcDriver).newInstance();
            cn=DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }catch(Exception e){
            System.out.println("open databse connection error:"+e);
        }

    }


    public Connection getConnection(){
        return cn;
    }

    public void closeConnection(){
        try{
            cn.close();
        }catch (Exception e){
            System.out.println("close databse connection error:"+e);
        }
    }

}
