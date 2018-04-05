package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class User extends BasicModel {
    private int id;
    private String username;
    private String password;
    private String salt;

    public User() {
        super();
    }

    public User(Properties properties) {
        super(properties);
    }

    public User(ResultSet resultSet) throws SQLException {
        super();
        id = resultSet.getInt("id");
        username = resultSet.getString("username");
        password = resultSet.getString("password");
        salt = resultSet.getString("salt");
    }

    /**
     * Get users in List
     *
     * @return List<User> or null if any error occurs
     */
    public List<User> GetUsers() {
        try {
            PreparedStatement sql = getConn().prepareStatement("SELECT * FROM `users`");
            ResultSet rs = sql.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Failed to get users");
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
