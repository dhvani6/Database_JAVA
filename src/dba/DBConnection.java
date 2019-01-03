package dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.lang.reflect.InvocationTargetException;

public class DBConnection {

    public static Connection connection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://COT-CIS3365-08:1433;databaseName=newfinalproject;username=sa;password=Dk1085588!";
            conn = DriverManager.getConnection(url);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }


        return conn;
    }
}
