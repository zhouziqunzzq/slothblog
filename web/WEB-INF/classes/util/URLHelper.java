package util;

public class URLHelper {
    public static String getRouterParam(String url, int index) {
        int num = 0;
        StringBuilder param = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            if (num == index) param.append(url.charAt(i));
            if (url.charAt(i) == '/') num++;
            if (num > index) break;
        }
        param.deleteCharAt(param.length() - 1);
        return param.toString();
    }
}
