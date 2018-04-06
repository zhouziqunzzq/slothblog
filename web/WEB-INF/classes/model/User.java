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
        this.id = -1;
    }

    public User(Properties properties, int id, String username, String password, String salt) {
        super(properties);
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public User(Properties properties) {
        super(properties);
        this.id = -1;
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

    public boolean Update() {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "UPDATE `users` SET `username`=?, `password`=?, `salt`=? " +
                            "WHERE `id`=?");
            sql.setString(1, this.username);
            sql.setString(2, this.password);
            sql.setString(3, this.salt);
            sql.setInt(4, this.id);

            return sql.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(String.format("Failed to update user(id = %d)", this.id));
            e.printStackTrace();
            return false;
        }
    }

    public int Insert() {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "INSERT INTO `users` ( `username`, `password`, `salt` ) " +
                            "VALUES ( ?, ?, ? )");
            sql.setString(1, this.username);
            sql.setString(2, this.password);
            sql.setString(3, this.salt);

            int affectedRows = sql.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = sql.getGeneratedKeys();
                this.id = generatedKeys.getInt(1);
                return this.id;
            } else {
                System.out.println(String.format("Failed to update user(username = %s)", this.username));
                return -1;
            }

        } catch (SQLException e) {
            System.out.println(String.format("Failed to update user(username = %s)", this.username));
            e.printStackTrace();
            return -1;
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
