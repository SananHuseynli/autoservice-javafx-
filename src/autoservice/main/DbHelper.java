package autoservice.main;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbHelper {

    public  Connection getConnection() throws Exception {
        try {
            Properties p = new Properties();
            p.load(new FileReader("config.properties"));
            Connection connection = DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.username"), p.getProperty("db.password"));
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
