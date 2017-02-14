package bhtech.com.cabbytaxi.object;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
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

    public static DecimalFormat getDecimalFormat(int numberAfterDot) {
        String pattern = "#.";
        for (int i = 1; i <= numberAfterDot; i++) {
            pattern += "0";
        }
        return new DecimalFormat(pattern);
    }
}
