package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserInfo extends BasicModel {
    public enum Gender {
        MALE, FEMALE
    }

    private int id;
    private int user_id;
    private String email;
    private Gender gender;
    private String nickname;
    private String intro;

    public UserInfo() {
        super();
        this.id = -1;
        this.user_id = -1;
    }

    public UserInfo(Properties properties) {
        super(properties);
        this.id = -1;
        this.user_id = -1;
    }

    public UserInfo(ResultSet rs) throws SQLException {
        this.setId(rs.getInt("id"));
        this.setId(rs.getInt("user_id"));
        this.setEmail(rs.getString("email"));
        this.setGender(Gender.values()[rs.getInt("gender")]);
        this.setNickname(rs.getString("nickname"));
        this.setIntro(rs.getString("intro"));
    }

    public UserInfo getUserInfoByUserId(int uid) {
        try {
            PreparedStatement sql = getConn().prepareStatement(
                    "SELECT * from `user_info` WHERE `user_id`=?"
            );
            sql.setInt(1, uid);
            ResultSet rs = sql.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                return new UserInfo(rs);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Failed to get user_info (user_id = %d)", uid));
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
