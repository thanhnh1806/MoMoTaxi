package bhtech.com.cabbytaxi.FindTaxi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.StringObject;

public class FindTaxiFoundView extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;

    public void setListener(FindTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FindTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private GoogleMap map;
    private SupportMapFragment mapFragment = null;
    private TextView tvEstimate, tvNumberTaxiFound;
    private Button btnCancelRequestTaxi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_taxi_found_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        tvEstimate = (TextView) v.findViewById(R.id.tvEstimate);
        btnCancelRequestTaxi = (Button) v.findViewById(R.id.btnCancelRequestTaxi);
        tvNumberTaxiFound = (TextView) v.findViewById(R.id.tvNumberTaxiFound);

        mapFragment.getMapAsync(this);
        btnCancelRequestTaxi.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        map.clear();
        showFromLocation();
        showToLocation();
        showEstimate();
        showNumberDriverFound();
        showListDriverAround();
    }

    private void showEstimate() {
        tvEstimate.setText(getString(R.string.estimated)
                + " " + StringObject.getDecimalFormat(2).format(datasource.getEstMileage())
                + " " + getString(R.string.km).toLowerCase()
                + " - " + StringObject.getDecimalFormat(2).format(datasource.getEstPrice()) + " $");
    }

    private void showNumberDriverFound() {
        if (datasource.getListDriversAround() != null) {
            int numberTaxiFound = datasource.getListDriversAround().size();
            tvNumberTaxiFound.setText(String.valueOf(numberTaxiFound) + " ");
        }
    }

    int estimatedTime = 0;

    private void showListDriverAround() {
        ArrayList<CarDriverObj> list = datasource.getListDriversAround();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                estimatedTime = list.get(i).getEstimatetime(datasource.getFromLatitude(),
                        datasource.getFromLongitude());
                listener.onDrawDriverMaker(list.get(i));
            }
            calculateZoomAndCamera();
        } else {
            calculateZoomAndCamera();
        }
    }

    public void calculateZoomAndCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            ArrayList<LatLng> latLngs = datasource.getListLatLng();
            if (latLngs.size() > 0) {
                for (int i = 0; i < latLngs.size(); i++) {
                    builder.include(latLngs.get(i));
                }
            } else {
                Log.w("ZoomAndCamera", "latLngs.size() < 0");
            }

            LatLngBounds bounds = builder.build();
            int mapPadding = 100;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, mapPadding);
            map.animateCamera(cameraUpdate);
        } catch (Exception e) {
            Log.w("Exception cameraUpdate", e.toString());
        }
    }

    public void calculateZoomAndCamera(LatLng latLng) {
        if (latLng != null) {
            try {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                map.animateCamera(cameraUpdate);
            } catch (Exception e) {
                Log.e("Exception cameraUpdate", e.toString());
            }
        } else {
            Log.w("calculateZoomAndCamera", "LatLng null");
        }
    }

    private void showFromLocation() {
        map.addMarker(datasource.getFromMarkerOptions());
    }

    private void showToLocation() {
        map.addMarker(datasource.getDestinationMarkerOptions());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        datasource = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelRequestTaxi:
                listener.onButtonCancelRequestTaxiClick();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setInfoWindowAdapter(datasource.getGoogleMapInfoWindowAdapter());
            map.setOnMarkerClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearMap() {
        map.clear();
    }

    public void addDriverMarker(MarkerOptions markerOptions) {
        markerOptions.title(String.valueOf(estimatedTime));
        map.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng fromLatLng = new LatLng(datasource.getFromLatitude(), datasource.getFromLongitude());
        LatLng toLatLng = new LatLng(datasource.getToLatitude(), datasource.getToLongitude());
        if (marker.getPosition().equals(fromLatLng) || marker.getPosition().equals(toLatLng)) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return true;
    }
}
