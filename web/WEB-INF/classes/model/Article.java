package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Article extends BasicModel {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private java.sql.Timestamp created_at;

    public Article() {
        super();
        this.id = -1;
    }

    public Article(ResultSet resultSet) throws SQLException {
        super();
        this.setId(resultSet.getInt("id"));
        this.setUser_id(resultSet.getInt("user_id"));
        this.setTitle(resultSet.getString("title"));
        this.setContent(resultSet.getString("content"));
        this.setCreated_at(resultSet.getTimestamp("created_at"));
    }

    public Article(Properties properties) {
        super(properties);
        this.id = -1;
    }

    public Article(Properties properties, int id, int user_id, String title, String content, Timestamp timestamp) {
        super(properties);
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_at = timestamp;
    }

    /**
     * Get articles order by created_at desc limit by pagination
     *
     * @param curPage current page starting from 1
     * @param perPage articles to be shown per page
     * @return List of Article or null if fails
     */
    public List<Article> getLatestArticles(int curPage, int perPage) {
        try {
            PreparedStatement sql = getConn().prepareStatement("SELECT * FROM `articles` " +
                    "ORDER BY `created_at` DESC LIMIT ?,?");
            sql.setInt(1, (curPage - 1) * perPage);
            sql.setInt(2, perPage);
            ResultSet resultSet = sql.executeQuery();
            List<Article> articles = new ArrayList<>();
            while (resultSet.next()) {
                articles.add(new Article(resultSet));
            }
            return articles;
        } catch (SQLException e) {
            System.out.println("Failed to get articles");
            e.printStackTrace();
            return null;
        }
    }

    public int insert() {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "INSERT INTO `articles` ( `user_id`, `title`, `content`) " +
                            "VALUES ( ?, ?, ? )", Statement.RETURN_GENERATED_KEYS);
            sql.setInt(1, this.user_id);
            sql.setString(2, this.title);
            sql.setString(3, this.content);

            int affectedRows = sql.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = sql.getGeneratedKeys();
                generatedKeys.next();
                this.id = generatedKeys.getInt(1);
                return this.id;
            } else {
                System.out.println(String.format("Failed to post article (uid = %s, title = %s)", this.user_id, this.title));
                return -1;
            }

        } catch (SQLException e) {
            System.out.println(String.format("Failed to post article (uid = %s, title = %s)", this.user_id, this.title));
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp timestamp) {
        this.created_at = timestamp;
    }
}
