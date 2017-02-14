package bhtech.com.cabbytaxi.object;

import java.util.regex.Pattern;

/**
 * Created by thanh_nguyen on 02/02/2016.
 */
public class EmailObject {
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+");
        Pattern pattern2 = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+\\.+[a-z]+");
        if (pattern.matcher(email).matches() || pattern2.matcher(email).matches()) {
            return true;
        }
        return false;
    }
}
