package util;

import java.io.File;

public class AvatarHelper {
    public String getAvatarByUserId(int uid) {
        if (new File(this.getClass().getClassLoader().getResource("/").getPath() +
            "../../static/img/avatar/" + uid).exists()) {
            return "/static/img/avatar/" + uid;
        } else {
            return "/static/img/avatar/default";
        }
    }
}
