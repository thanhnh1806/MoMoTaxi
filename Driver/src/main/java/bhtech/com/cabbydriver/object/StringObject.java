package bhtech.com.cabbydriver.object;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by thanh_nguyen02 on 23/12/2015.
 */
public class StringObject {
    public static boolean isNullOrEmpty(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+");
        Pattern pattern2 = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+\\.+[a-z]+");
        if (pattern.matcher(email).matches() || pattern2.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static DecimalFormat getDecimalFormat(int numberAfterDot) {
        String pattern = "#.";
        for (int i = 1; i <= numberAfterDot; i++) {
            pattern += "0";
        }
        return new DecimalFormat(pattern);
    }
}
