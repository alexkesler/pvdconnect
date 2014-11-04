package org.kesler.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

public abstract class OracleUtil {


    public static Connection createConnection(String serverIp, String  user, String pass) throws SQLException, ClassNotFoundException{
        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", pass);

        String url = "jdbc:oracle:thin:@" + serverIp + ":1521:xe";

        Locale prevLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        Class.forName("oracle.jdbc.driver.OracleDriver");


        Connection connection = DriverManager.getConnection(url, prop);

        Locale.setDefault(prevLocale);

        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException{
        if (connection!=null) connection.close();
    }

}
