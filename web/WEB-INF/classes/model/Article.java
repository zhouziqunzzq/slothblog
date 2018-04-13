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
    private List<Tag> tags;
    private List<Comment> comments;

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
            Article article;
            while (resultSet.next()) {
                article = new Article(resultSet);
                article.setTags(getTagsByArticleId(resultSet.getInt("id")));
                articles.add(article);
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

    public Article getArticleById(int id) {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "SELECT * FROM `articles` WHERE `id`=?");
            sql.setInt(1, id);
            ResultSet rs = sql.executeQuery();
            if(rs.next()) {
                Article article = new Article(rs);
                article.setTags(getTagsByArticleId(id));
                article.setComments(getCommentsByArticleId(rs.getInt("id"), 1, 10));
                return article;
            } else
                return null;
        } catch (SQLException e) {
            System.out.println("Failed to get article");
            e.printStackTrace();
            return null;
        }
    }

    public List<Article> getArticlesByUserId(int uid, int curpage, int perpage) {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "SELECT * FROM `articles` WHERE `user_id`=?" +
                    " ORDER BY `created_at` DESC LIMIT ?,?");
            sql.setInt(1, uid);
            sql.setInt(2, (curpage - 1) * perpage);
            sql.setInt(3, perpage);
            ResultSet resultSet = sql.executeQuery();
            List<Article> articles = new ArrayList<>();
            Article article;
            while (resultSet.next()) {
                article = new Article(resultSet);
                article.setTags(getTagsByArticleId(resultSet.getInt("id")));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            System.out.println("Failed to get articles");
            e.printStackTrace();
            return null;
        }
    }

    public List<Tag> getTagsByArticleId(int articleId) {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "SELECT tags.* FROM `articles_tags`, `tags`" +
                            "WHERE `article_id`=? AND articles_tags.tag_id=tags.id");
            sql.setInt(1, articleId);
            ResultSet rs = sql.executeQuery();
            List<Tag> tags = new ArrayList<>();
            while (rs.next()) {
                tags.add(new Tag(rs));
            }
            System.out.println("successful");
            return tags;
        } catch (SQLException e) {
            System.out.println("Failed to get tags");
            e.printStackTrace();
            return null;
        }
    }

    public List<Comment> getCommentsByArticleId(int articleId, int curpage, int perpage) {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "SELECT comments.* FROM `comments`" +
                            "WHERE `article_id`=?" + " ORDER BY `created_at` DESC LIMIT ?,?");
            sql.setInt(1, articleId);
            sql.setInt(2, (curpage - 1) * perpage);
            sql.setInt(3, perpage);
            ResultSet rs = sql.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                comments.add(new Comment(rs));
            }
            return comments;
        } catch (SQLException e) {
            System.out.println("Failed to get comments");
            e.printStackTrace();
            return null;
        }
    }

    public boolean link (List<Integer> tagIdList) {
        try {
            boolean flag = true;
            for(int i = 0; i < tagIdList.size(); i++) {
                int tagId = tagIdList.get(i);
                PreparedStatement sql = getConn().prepareStatement(
                        "INSERT INTO `articles_tags` ( `article_id`, `tag_id`) " +
                                "VALUES ( ?, ? )", Statement.RETURN_GENERATED_KEYS);
                sql.setInt(1, this.id);
                sql.setInt(2, tagId);
                if(sql.executeUpdate() <= 0) flag = false;
            }
            return flag;
        } catch (SQLException e) {
            System.out.println("Failed to link tags");
            e.printStackTrace();
            return false;
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Timestamp getCreated_at() { return created_at; }

    public void setCreated_at(Timestamp timestamp) { this.created_at = timestamp; }

    public List<Tag> getTags() { return tags; }

    public void setTags(List<Tag> tags) { this.tags = tags; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}
