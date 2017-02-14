package bhtech.com.cabbytaxi.FindTaxi;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbytaxi.SupportClass.OnMapViewListener;
import bhtech.com.cabbytaxi.object.CarDriverObj;

/**
 * Created by thanh_nguyen on 25/12/2015.
 */
public interface OnFindTaxiMapViewListener extends OnMapViewListener {

    @Override
    void onGoogleMapReady();

    @Override
    void onMyLocationChange();

    @Override
    void onInfoWindowClick(Marker marker);
}
