package bhtech.com.cabbydriver.FindCustomer;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;

public class FindCustomerView extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationChangeListener {
    private FindCustomerInterface.Listener listener;
    private FindCustomerInterface.DataSource dataSource;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private GoogleMap map;
    private SupportMapFragment mapFragment = null;
    private FrameLayout header, btnAccept, btnListView, btnMapView, btnRefresh, btnNonApp;
    private TextView tvDayOfWeek, tvTime, tvDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_find_customer_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        header = (FrameLayout) v.findViewById(R.id.header);
        tvDayOfWeek = (TextView) v.findViewById(R.id.tvDayOfWeek);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvDate = (TextView) v.findViewById(R.id.tvDate);

        btnAccept = (FrameLayout) v.findViewById(R.id.btnAccept);
        btnListView = (FrameLayout) v.findViewById(R.id.btnListView);
        btnMapView = (FrameLayout) v.findViewById(R.id.btnMapView);
        btnRefresh = (FrameLayout) v.findViewById(R.id.btnRefresh);
        btnNonApp = (FrameLayout) v.findViewById(R.id.btnNonApp);

        btnNonApp.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnListView.setOnClickListener(this);
        btnMapView.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            tvDayOfWeek.setText(String.valueOf(dataSource.getCurrentDayOfWeek()));
            tvTime.setText(String.valueOf(dataSource.getCurrentTime()));
            tvDate.setText(String.valueOf(dataSource.getCurrentDate()));
        } catch (Exception e) {
        }
    }

    public void showHideButtonNonApp(boolean isShow) {
        if (isShow) {
            btnNonApp.setVisibility(View.VISIBLE);
        } else {
            btnNonApp.setVisibility(View.GONE);
        }
    }

    public void showHideHeader(boolean isShow) {
        if (isShow) {
            header.setVisibility(View.VISIBLE);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNonApp:
                listener.OnButtonNonAppClick();
                break;
            case R.id.btnAccept:
                listener.OnButtonAcceptClick();
                break;
            case R.id.btnListView:
                listener.OnButtonListViewClick();
                break;
            case R.id.btnMapView:
                listener.OnButtonMapViewClick();
                break;
            case R.id.btnRefresh:
                listener.OnButtonRefreshClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }

    public void showHideButtonMapView(boolean isShow) {
        if (isShow) {
            btnMapView.setVisibility(View.VISIBLE);
            btnListView.setVisibility(View.GONE);
        } else {
            btnMapView.setVisibility(View.GONE);
            btnListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.setInfoWindowAdapter(dataSource.getGoogleMapInfoWindowAdapter(getActivity()));
            map.setOnMarkerClickListener(this);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setOnMyLocationChangeListener(this);
            listener.onGoogleMapReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                map.animateCamera(cameraUpdate);
            } catch (Exception e) {
                Log.e("Exception cameraUpdate", e.toString());
            }
        }
    }

    public void calculateZoomAndCamera(LatLng latLng) {
        try {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            map.animateCamera(cameraUpdate);
            dataSource.setFirstZoom(false);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < listMarkerOptions.size(); i++) {
            if (marker.getPosition().equals(listMarkerOptions.get(i).getPosition())) {
                dataSource.setListViewPositionClick(i);
            } else {
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
        return true;
    }

    private ArrayList<MarkerOptions> listMarkerOptions;

    public void reloadView() {
        try {
            map.clear();
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
            map.addMarker(MarkerOptionsObject.addMarker(latLng, R.drawable.icon_car_arrived_small,
                    getString(R.string.driver)));
        } catch (Exception e) {
        }
    }

    private void showListCustomerMaker() {
        for (int i = 0; i < listMarkerOptions.size(); i++) {
            latLngs.add(listMarkerOptions.get(i).getPosition());
            map.addMarker(listMarkerOptions.get(i));
        }
    }

    @Override
    public void onMyLocationChange(final Location location) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    CarDriverObj.getInstance().setLocation(LocationObject.locationToLatLng(location));
                    dataSource.setLatLngDriver(LocationObject.locationToLatLng(location));
                    listener.setCurrentAddress();
                    listener.zoomToDriverLoation();
                } catch (Exception e) {

                }
            }
        }, 3000);
    }
}
