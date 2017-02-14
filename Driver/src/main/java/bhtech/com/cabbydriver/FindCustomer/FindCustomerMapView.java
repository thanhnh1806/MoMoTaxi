package bhtech.com.cabbydriver.FindCustomer;


import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SupportClasses.Map.MapView;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.PhoneObject;

public class FindCustomerMapView extends MapView implements GoogleMap.OnCameraChangeListener {
    private FindCustomerInterface.Listener listener;
    private FindCustomerInterface.DataSource dataSource;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FindCustomerMapView() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        getMap().setInfoWindowAdapter(dataSource.getGoogleMapInfoWindowAdapter(getActivity()));
        super.listener.onGoogleMapReady();
        getMap().setOnCameraChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        super.onMyLocationChange(location);
    }

    @Override
    public Marker addMarker(MarkerOptions markerOptions) {
        return super.addMarker(markerOptions);
    }

    public void calculateZoomAndCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (latLngs.size() > 0) {
            try {
                for (int i = 0; i < latLngs.size(); i++) {
                    builder.include(latLngs.get(i));
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                getMap().animateCamera(cameraUpdate);
            } catch (Exception e) {
                Log.d("Exception cameraUpdate", e.toString());
            }
        }
    }

    @Override
    public void showMyLocation(Location location) {
        super.showMyLocation(location);
    }

    @Override
    public LatLng getMyLocation() {
        return super.getMyLocation();
    }

    @Override
    public void setMyLocationEnabled(boolean isEnabled) {
        super.setMyLocationEnabled(isEnabled);
    }

    @Override
    public void setZoomControlsEnabled(boolean isEnabled) {
        super.setZoomControlsEnabled(isEnabled);
    }

    @Override
    public void setMyLocationButtonEnabled(boolean isEnable) {
        super.setMyLocationButtonEnabled(isEnable);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < listMarkerOptions.size(); i++) {
            Log.w("Marker", marker.getPosition().toString());
            Log.w("Marker", listMarkerOptions.get(i).getPosition().toString());

            if (marker.getPosition().equals(listMarkerOptions.get(i).getPosition())) {
                Log.w("MapView", "trung LatLng");
                dataSource.setListViewPositionClick(i);
            } else {
                Log.w("MapView", "k trung LatLng");
            }
        }
        try {
            if (marker.getSnippet().equalsIgnoreCase(getString(R.string.driver))) {
                //Do nothing
            } else {
                if (dataSource.getListViewPositionClick() != -1) {
                    listener.OnMarkerClick();
                }
            }
        } catch (Exception e) {
            if (dataSource.getListViewPositionClick() != -1) {
                listener.OnMarkerClick();
            }
        }
        return super.onMarkerClick(marker);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            if (latLngs.size() > 0) {
                for (int i = 0; i < latLngs.size(); i++) {
                    builder.include(latLngs.get(i));
                }
                int mapPadding = PhoneObject.convertDPtoPixel(getActivity(), 170);
                getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), mapPadding));
                getMap().setOnCameraChangeListener(null);
            }
        } catch (Exception e) {
            Log.d("Exception cameraUpdate", e.toString());
        }

    }

    private ArrayList<MarkerOptions> listMarkerOptions;

    public void reloadView() {
        try {
            getMap().clear();
            showDriverMarker();
            listMarkerOptions = dataSource.getListMarkerOptions();
            if (listMarkerOptions != null) {
                showListCustomerMaker();
            }
            calculateZoomAndCamera();
        } catch (Exception e) {
        }
    }

    private ArrayList<LatLng> latLngs;

    private void showDriverMarker() {
        try {
            latLngs = new ArrayList<>();
            LatLng latLng = dataSource.getLatLngDriver();
            latLngs.add(latLng);
            addMarker(MarkerOptionsObject.addMarker(latLng, R.drawable.icon_car_arrived_small,
                    getString(R.string.driver)));
        } catch (Exception e) {
        }
    }

    private void showListCustomerMaker() {
        for (int i = 0; i < listMarkerOptions.size(); i++) {
            latLngs.add(listMarkerOptions.get(i).getPosition());
            addMarker(listMarkerOptions.get(i));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }
}
