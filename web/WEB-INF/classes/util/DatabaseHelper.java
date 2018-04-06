package util;

import java.util.Properties;
import java.sql.*;

public class DatabaseHelper {
    /**
     * A static helper method to get DB connection instance.
     *
     * @param properties must contain four properties: DBAddress, DBName, DBUser and DBPassword.
     * @return DB Connection instance.
     */
    public static Connection getConnFromConfig(Properties properties) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?useUnicode=true" +
                            "&characterEncoding=UTF-8", properties.getProperty("DBAddress"),
                    properties.getProperty("DBName")),
                    properties.getProperty("DBUser"),
                    properties.getProperty("DBPassword")
            );
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to connect DB: Mysql driver not loaded");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("Failed to connect DB: Unknown error");
            e.printStackTrace();
            return null;
        }
    }
}
