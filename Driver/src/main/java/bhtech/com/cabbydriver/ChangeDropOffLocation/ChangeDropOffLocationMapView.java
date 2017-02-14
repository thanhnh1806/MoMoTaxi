package bhtech.com.cabbydriver.ChangeDropOffLocation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import bhtech.com.cabbydriver.SupportClasses.Map.MapView;

public class ChangeDropOffLocationMapView extends MapView {
    private ChangeDropOffLocationInterface.Listener listener;
    private ChangeDropOffLocationInterface.Datasource datasource;

    public void setListener(ChangeDropOffLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChangeDropOffLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        listener.onGoogleMapReady();
    }

    public void showCustomerAndDriverMarker() {
        getMap().clear();
        MarkerOptions pickUpMO = datasource.getPickUpMarkerOptions();
        addMarker(pickUpMO);
        MarkerOptions dropOffMO = datasource.getDropOffMarkerOptions();
        addMarker(dropOffMO);
        if (datasource.getListLatLng() != null) {
            calculateZoomAndCamera(120, datasource.getListLatLng());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
