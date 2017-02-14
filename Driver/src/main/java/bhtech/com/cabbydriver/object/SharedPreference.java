package bhtech.com.cabbydriver.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreference {
    static SharedPreferences sharedPreferences;

    public static String get(Context context, Object key, String defData) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPreferences.getString(String.valueOf(key), defData);
        } catch (Exception e) {
            return defData;
        }
    }

    public static void set(Context context, Object key, String data) {
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(key), data);
            editor.commit();
        } catch (Exception e) {

        }
    }

    public static void set(Context context, Object key, String data, byte[] pass_key_encrypt) {
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor editor = sharedPreferences.edit();
            editor.commit();
        } catch (Exception e) {

        }
    }
}
