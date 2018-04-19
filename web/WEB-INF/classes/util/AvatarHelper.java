package util;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class AvatarHelper {
    public enum FileType {
        JPEG("FFD8FF"),
        PNG("89504E47"),
        GIF("47494638"),
        TIFF("49492A00"),
        BMP("424D"),
        DWG("41433130"),
        PSD("38425053"),
        RTF("7B5C727466"),
        XML("3C3F786D6C"),
        HTML("68746D6C3E"),
        DBX("CFAD12FEC5FD746F "),
        PST("2142444E"),
        OLE2("0xD0CF11E0A1B11AE1"),
        XLS_DOC("D0CF11E0"),
        MDB("5374616E64617264204A"),
        WPB("FF575043"),
        EPS_PS("252150532D41646F6265"),
        PDF("255044462D312E"),
        PWL("E3828596"),
        ZIP("504B0304"),
        RAR("52617221"),
        WAV("57415645"),
        AVI("41564920"),
        RAM("2E7261FD"),
        RM("2E524D46"),
        MOV("6D6F6F76"),
        ASF("3026B2758E66CF11"),
        MID("4D546864");

        private String value = "";

        private FileType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getAvatarByUserId(int uid) {
        if (new File(this.getClass().getClassLoader().getResource("/").getPath() +
                "../../static/img/avatar/" + uid).exists()) {
            return "/static/img/avatar/" + uid;
        } else {
            return "/static/img/avatar/default";
        }
    }

    public boolean saveAvatarBase64ByUserId(int uid, String imgEncoded) {
        if (imgEncoded == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte b[] = decoder.decodeBuffer(imgEncoded);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // Check file type
            if (!(getFileType(b) == FileType.JPEG || getFileType(b) == FileType.PNG
                    || getFileType(b) == FileType.GIF)) {
                return false;
            }
            String path = this.getClass().getClassLoader().getResource("/").getPath() +
                    "../../static/img/avatar/" + uid;
            File imgFile = new File(path);
            if (imgFile.exists()) {
                if (!imgFile.delete()) {
                    return false;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static FileType getFileType(byte[] bytes) throws IOException {
        String fileHead = getFileHeader(bytes);
        if (fileHead != null && fileHead.length() > 0) {
            fileHead = fileHead.toUpperCase();
            FileType[] fileTypes = FileType.values();
            for (FileType type : fileTypes) {
                if (fileHead.startsWith(type.getValue())) {
                    return type;
                }
            }
        }
        return null;
    }

    private static String getFileHeader(byte[] b) {
        return bytesToHex(Arrays.copyOfRange(b, 0, 29));
    }
}
