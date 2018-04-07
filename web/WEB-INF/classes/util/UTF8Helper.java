package util;

import java.io.UnsupportedEncodingException;

public class UTF8Helper {
    public static String parseUTF8(String s) throws UnsupportedEncodingException {
        return new String(s.getBytes("iso-8859-1"), "utf-8");
    }
}
