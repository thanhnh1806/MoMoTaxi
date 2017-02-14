package bhtech.com.cabbytaxi.SupportClass;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import bhtech.com.cabbytaxi.R;

public class MapView extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private OnMapViewListener listener;

    public MapView() {

    }

    private GoogleMap map;
    private SupportMapFragment mapFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    public GoogleMap getMap() {
        return map;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMapViewListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationChangeListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    public Marker addMarker(MarkerOptions markerOptions) {
        return map.addMarker(markerOptions);
    }

    public void calculateZoomAndCamera() {
    }

    public void setInfoWindowAdapter(GoogleMap.InfoWindowAdapter adapter) {
        map.setInfoWindowAdapter(adapter);
    }

    private Marker currentYourMarker;

    public void addYourMarker(MarkerOptions markerOptions) {
        if (currentYourMarker != null) {
            currentYourMarker.remove();
        }
        currentYourMarker = addMarker(markerOptions);
    }

    public void showMyLocation(Location location) {
    }

    public LatLng getMyLocation() {
        setMyLocationEnabled(true);
        if (map.getMyLocation() != null) {
            return new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
        } else {
            return null;
        }
    }

    public void setMyLocationEnabled(boolean isEnabled) {
        map.setMyLocationEnabled(isEnabled);
    }

    public void setZoomControlsEnabled(boolean isEnabled) {
        map.getUiSettings().setZoomControlsEnabled(isEnabled);
    }

    public void setMyLocationButtonEnabled(boolean isEnable) {
        map.getUiSettings().setMyLocationButtonEnabled(isEnable);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }
}
