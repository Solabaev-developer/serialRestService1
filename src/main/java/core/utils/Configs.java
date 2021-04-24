package core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {

    private static Properties prop = null;

    private Configs() {
    }

    public static String getLink() {
        return getProps().getProperty("kinonews.url");
    }

    public static Properties getProps() {
        if (prop == null) {
            try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
                prop = new Properties();
                prop.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return prop;
    }

    public static String getDateFormat() {
        return getProps().getProperty("date.format");
    }
}
