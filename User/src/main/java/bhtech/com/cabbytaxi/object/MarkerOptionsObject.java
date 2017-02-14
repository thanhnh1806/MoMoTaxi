package bhtech.com.cabbytaxi.object;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thanh_nguyen on 24/12/2015.
 */
public class MarkerOptionsObject {
    private static MarkerOptions markerOptions;

    public static MarkerOptions addMarker(Context context, View marker, LatLng latLng, String title) {
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).flat(true).anchor(0.5f, 0.5f).title(title);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker)));
        return markerOptions;
    }

    public static MarkerOptions addMarker2(Context context, View marker, LatLng latLng, String title) {
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(title);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker)));
        return markerOptions;
    }

    public static MarkerOptions addMarker(LatLng latLng, int icon) {
        markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(icon));
        markerOptions.position(latLng).flat(true).anchor(0.5f, 0.5f);
        return markerOptions;
    }

    public static MarkerOptions addMarker(LatLng latLng, int icon, String snippet) {
        markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(icon));
        markerOptions.position(latLng).flat(true).anchor(0.5f, 0.5f).snippet(snippet);
        return markerOptions;
    }


    public static MarkerOptions addMarker(int icon, String snippet, LatLng latLng, String title) {
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(title)
                .icon(BitmapDescriptorFactory.fromResource(icon)).snippet(snippet);
        return markerOptions;
    }

    public static MarkerOptions addMarker(Bitmap image, String snippet, LatLng latLng, String title) {
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(image)).snippet(snippet);
        return markerOptions;
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static View customLayoutMarker(Context context, int marker_layout) {
        View v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(marker_layout, null);
        return v;
    }
}
