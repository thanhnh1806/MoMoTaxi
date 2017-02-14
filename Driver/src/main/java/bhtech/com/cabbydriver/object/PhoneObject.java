package bhtech.com.cabbydriver.object;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by thanh_nguyen02 on 23/12/2015.
 */
public class PhoneObject {
    public static Intent makePhoneCall(String phoneNumber) {
        String uri = "tel:" + phoneNumber.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    public static Intent sendSMS(String phoneNumber) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("smsto:"));
        i.setType("vnd.android-dir/mms-sms");
        i.putExtra("address", phoneNumber);
        return i;
    }

    public static Intent takeCameraPicture(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }

    public static void hiddenSofwareKeyboard(Context context, View view) {
        if (view != null) {
            try {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
            }
        }
    }

    public static void hiddenSofwareKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hiddenSofwareKeyboard(Window window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static float getDefaultDisplayWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static float getDefaultDisplayHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int convertDPtoPixel(Context context, int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }
}
