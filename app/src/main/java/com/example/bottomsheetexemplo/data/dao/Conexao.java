package com.example.bottomsheetexemplo.data.dao;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conexao(){
        Connection conn = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" +
                    "192.168.0.231;databaseName=PRAP3;user=sa;" +
                    "password=123456;");
            /*conn = DriverManager.getConnection("jdbc:sqlserver://" +
                    "192.168.0.231:1433;databaseName=PRAP3;user=sa;" +
                    "password=123456;");*/
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
