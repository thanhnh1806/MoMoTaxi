package bhtech.com.cabbydriver.SupportClasses.Map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by thanh_nguyen on 25/12/2015.
 */
public interface OnMapViewListener {
    void onGoogleMapReady();

    void onMyLocationChange();

    void onInfoWindowClick(Marker marker);

    void onDrawYourMaker(LatLng latLng);
}
