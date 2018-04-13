package model;

import java.sql.*;
import java.util.Properties;

public class Comment extends BasicModel {
    private int id;
    private String content;
    private java.sql.Timestamp created_at;
    private int article_id;
    private int user_id;

    public Comment() {
        super();
        this.id = -1;
    }

    public Comment(ResultSet resultSet) throws SQLException {
        super();
        this.setId(resultSet.getInt("id"));
        this.setContent(resultSet.getString("content"));
        this.setCreated_at(resultSet.getTimestamp("created_at"));
        this.setArticle_id(resultSet.getInt("Article_id"));
        this.setUser_id(resultSet.getInt("user_id"));
    }

    public Comment(Properties properties) {
        super(properties);
        this.id = -1;
    }

    public Comment(Properties properties, int id, String content, Timestamp timestamp, int article_id, int user_id) {
        super(properties);
        this.id = id;
        this.content = content;
        this.created_at = timestamp;
        this.article_id = article_id;
        this.user_id = user_id;
    }

    public int insert() {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "INSERT INTO `comments` ( `content`, `article_id`, `user_id` )" +
                            "VALUES ( ?, ?, ? )", Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, this.content);
            sql.setInt(2, this.article_id);
            sql.setInt(3, this.user_id);

            if (sql.executeUpdate() > 0) {
                return this.id;
            } else {
                System.out.println(String.format("Failed to insert comment(id = %d)", this.id));
                return -1;
            }
        } catch (SQLException e) {
            System.out.println(String.format("Failed to insert comment(id = %d)", this.id));
            e.printStackTrace();
            return -1;
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Timestamp getCreated_at() { return created_at; }

    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }

    public int getArticle_id() { return article_id; }

    public void setArticle_id(int article_id) { this.article_id = article_id; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }
}
