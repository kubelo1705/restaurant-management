package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Common class. It contains functions are not related to any models in project
 */
public class TimeUtils {
    public static String getCurrentFormatedTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = myDateObj.format(myFormatObj);
        return formattedTime;
    }

    public static String getCurrentFormattedDay() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
}
