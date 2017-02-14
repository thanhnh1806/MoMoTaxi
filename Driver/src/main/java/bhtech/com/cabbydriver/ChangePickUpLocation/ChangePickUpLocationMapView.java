package bhtech.com.cabbydriver.ChangePickUpLocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import bhtech.com.cabbydriver.SupportClasses.Map.MapView;

public class ChangePickUpLocationMapView extends MapView {
    private ChangePickUpLocationInterface.Listener listener;
    private ChangePickUpLocationInterface.Datasource datasource;

    public void setListener(ChangePickUpLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChangePickUpLocationInterface.Datasource datasource) {
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
        MarkerOptions driverMO = datasource.getDriverMarkerOptions();
        addMarker(driverMO);
        MarkerOptions customerMO = datasource.getCustomerMarkerOptions();
        addMarker(customerMO);
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
