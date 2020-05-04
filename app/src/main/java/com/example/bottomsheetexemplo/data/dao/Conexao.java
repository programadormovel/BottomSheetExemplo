package com.example.bottomsheetexemplo.data.dao;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conexao(){
        Connection conn = null;
        StrictMode.ThreadPolicy policy;

        try {
            policy = new StrictMode.ThreadPolicy.Builder()
                    .build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" +
                    "192.168.0.231;databaseName=PRAP3;user=sa;" +
                    "password=123456;");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
