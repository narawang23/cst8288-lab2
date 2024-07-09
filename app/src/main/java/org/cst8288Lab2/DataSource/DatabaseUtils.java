package org.cst8288Lab2.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseUtils {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("app/data/database.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String db = props.getProperty("db");
        String name = props.getProperty("name");
        String host = props.getProperty("host");
        String port = props.getProperty("port");
        String user = props.getProperty("user");
        String password = props.getProperty("pass");

        String url = String.format("jdbc:%s://%s:%s/%s", db, host, port, name);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }
}
