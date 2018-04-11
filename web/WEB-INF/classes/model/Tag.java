package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Tag extends BasicModel {
    private int id;
    private String name;
    private int count;

    public Tag() {
        super();
        this.id = -1;
    }

    public Tag(Properties properties, int id, String name, int count) {
        super(properties);
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public Tag(Properties properties) {
        super(properties);
        this.id = -1;
    }

    public Tag(ResultSet resultSet) throws SQLException {
        super();
        this.setId(resultSet.getInt("id"));
        this.setName(resultSet.getString("name"));
        this.setCount(resultSet.getInt("count"));
    }

    public int insert() {
        try {
            PreparedStatement sql1 = getConn().prepareStatement(
                    "SELECT * FROM `tags`" + "WHERE `name`=?");
            sql1.setString(1, this.name);
            ResultSet rs = sql1.executeQuery();
            if(!rs.next()) {
                PreparedStatement sql2 = getConn().prepareStatement(
                        "INSERT INTO `tags` (`name`, `count`)" +
                                "VALUES ( ?, ? )", Statement.RETURN_GENERATED_KEYS);
                sql2.setString(1, this.name);
                sql2.setInt(2, 1);

                int affectedRows = sql2.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = sql2.getGeneratedKeys();
                    generatedKeys.next();
                    this.id = generatedKeys.getInt(1);
                    return this.id;
                } else {
                    System.out.println(String.format("1 Failed to insert tag(id = %d)", this.id));
                    return -1;
                }
            } else {
                Tag tag = new Tag(rs);
                PreparedStatement sql3 = getConn().prepareStatement(
                        "UPDATE `tags` SET `count`=? " + "WHERE `id`=?");
                sql3.setInt(1, tag.getCount()+1);
                sql3.setInt(2, tag.getId());
                if(sql3.executeUpdate() > 0)
                    return tag.getId();
                else {
                    System.out.println(String.format("2 Failed to update tag(id = %d)", this.id));
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println(String.format("3 Failed to insert tag(id = %d)", this.id));
            e.printStackTrace();
            return -1;
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCount() { return count; }

    public void setCount(int count) { this.count = count; }
}
