package bhtech.com.cabbytaxi.FavouriteLocation;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbytaxi.SupportClass.MapView;
import bhtech.com.cabbytaxi.object.LocationObject;

public class FavouriteLocationMapView extends MapView {
    private Context context;
    private FavouriteLocationInterface.Listener listener;
    private FavouriteLocationInterface.Datasource datasource;

    public void setListener(FavouriteLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FavouriteLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(false);

        getMap().setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                datasource.setMyLocation(LocationObject.locationToLatLng(location));
            }
        });

        getMap().setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                datasource.setLatLngMapClick(latLng);
                listener.onMapClick();
            }
        });

        getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                datasource.setLatLngMapClick(latLng);
                listener.onMapClick();
            }
        });
    }

    public void reloadView() {
        getMap().clear();
        showMarker();
        calculateZoomAndCamera();
    }

    public void showMarker() {
        if (datasource.isAddViewVisible()) {
            Marker marker = addMarker(datasource.getMarkerOptionsAddView(getActivity()));
            marker.setDraggable(true);
        } else {
            Marker marker = addMarker(datasource.getMarkerOptionsEditView(getActivity()));
            marker.setDraggable(true);
        }
    }

    public void calculateZoomAndCamera() {
        LatLng latLng = datasource.getLatLngMarker();
        if (latLng != null) {
            try {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
                getMap().animateCamera(cameraUpdate);
            } catch (Exception e) {
                Log.e("Exception cameraUpdate", e.toString());
            }
        }
    }
}
