package bhtech.com.cabbydriver.ChooseRouteToCustomer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

import bhtech.com.cabbydriver.SupportClasses.Map.MapView;

public class ChooseRouteToCustomerMapView extends MapView implements GoogleMap.OnMapClickListener {
    private ChooseRouteToCustomerInterface.Listener listener;
    private ChooseRouteToCustomerInterface.Datasource datasource;

    public void setListener(ChooseRouteToCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChooseRouteToCustomerInterface.Datasource datasource) {
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
        getMap().setOnMapClickListener(this);
        super.listener.onGoogleMapReady();
    }

    private void addPolyline() {
        try {
            getMap().clear();
            ArrayList<PolylineOptions> listPolylineOptions = datasource.getListPolylineOptions();
            if (listPolylineOptions.size() > 0) {
                ArrayList<Polyline> listPolyline = new ArrayList<>();
                for (int i = 0; i < listPolylineOptions.size(); i++) {
                    Polyline p = getMap().addPolyline(listPolylineOptions.get(i));
                    listPolyline.add(p);
                }
                datasource.setListPolyline(listPolyline);
            } else {
                Log.e("List_PolylineOptions", "null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        try {
            ArrayList<PolylineOptions> list = datasource.getListPolylineOptions();
            for (int i = 0; i < list.size(); i++) {
                if (PolyUtil.isLocationOnPath(latLng, list.get(i).getPoints(), true, 100)) {
                    datasource.setPolylineUserClick(i);
                    listener.onPolylineClick();
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    private void drawFromToMarker() {
        super.addMarker(datasource.getFromMarkerOptions());
        super.addMarker(datasource.getToMarkerOptions());
        zoomCamera();
    }

    private void zoomCamera() {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(datasource.getFromLatLng());
            builder.include(datasource.getToLatLng());
            if (datasource.getCustomerLatLng() != null) {
                builder.include(datasource.getCustomerLatLng());
            }

            int mapPadding = 120;
            getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), mapPadding));
            getMap().setOnCameraChangeListener(null);
        } catch (Exception e) {
            Log.d("Exception cameraUpdate", e.toString());
        }
    }

    public void reloadView() {
        addPolyline();
        drawFromToMarker();
    }

    public void showCustomerLocation() {
        super.addMarker(datasource.getCustomerMarkerOptions());
        zoomCamera();
    }
}
