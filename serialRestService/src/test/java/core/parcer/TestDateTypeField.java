package core.parcer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDateTypeField {
    public static void main(String[] args) throws ParseException {
        String strDate = "20.01.2008";
        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    }
}
