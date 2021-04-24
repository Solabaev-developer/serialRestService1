package core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KinoNewsLinkFormer {

    private static final String URL = Configs.getLink();
    private static final Pattern pattern = Pattern.compile("serial_(?<id>\\d+)\\/");

    public static String getSerialId(String url) {
        Matcher matcher = pattern.matcher(url);
        String ret = "";
        while (matcher.find()) {
            ret = matcher.group("id");
        }
        if (ret.isEmpty()) throw new IllegalArgumentException("url не соотвествует паттерну! url:" + url);
        else return ret;
    }

    public static String getSeasonsLink(String serialId) {
        return URL.concat("serial_").concat(serialId).concat("_seasons");
    }

    public static String getAllPersonsLink(String serialId) {
        return URL.concat("serial_").concat(serialId).concat("_allperson");
    }
}
