package bhtech.com.cabbydriver.ReportIncident;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.LocationObject;

public class ReportIncidentView extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, View.OnClickListener {
    private ReportIncidentInterface.Listener listener;
    private ReportIncidentInterface.Datasource datasource;

    public void setListener(ReportIncidentInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ReportIncidentInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private GoogleMap map;
    private SupportMapFragment mapFragment = null;
    private TextView tvAddress, tvLocation, tvDayInWeek, tvTime, tvDay, btnSend;
    private FrameLayout btnBack, btnCallCompany, btnCallPolice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_incident_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        tvDayInWeek = (TextView) v.findViewById(R.id.tvDayInWeek);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvDay = (TextView) v.findViewById(R.id.tvDay);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        btnCallCompany = (FrameLayout) v.findViewById(R.id.btnCallCompany);
        btnCallPolice = (FrameLayout) v.findViewById(R.id.btnCallPolice);
        btnSend = (TextView) v.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);
        btnCallCompany.setOnClickListener(this);
        btnCallPolice.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        tvDayInWeek.setText(datasource.getCurrentDayInWeek());
        tvTime.setText(datasource.getCurrentTime());
        tvDay.setText(datasource.getCurrentDay());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(final Location location) {
        datasource.setDriverLatLng(LocationObject.locationToLatLng(location));
        map.clear();
        map.addMarker(datasource.getMarkerOptions());
        calculateZoomAndCamera(LocationObject.locationToLatLng(location));
        listener.onMapReady();
    }

    public void calculateZoomAndCamera(LatLng latLng) {
        try {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            map.animateCamera(cameraUpdate);
        } catch (Exception e) {
        }
    }

    public void reloadView() {
        tvAddress.setText(datasource.getAddress());
        tvLocation.setText(getString(R.string.map_code)
                + " : " + datasource.getDriverLatLng().latitude
                + "," + datasource.getDriverLatLng().longitude);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                listener.onButtonBackClick();
                break;
            case R.id.btnSend:
                listener.sendReportIncident();
                break;
            case R.id.btnCallCompany:
                listener.onButtonCallCompanyClick();
                break;
            case R.id.btnCallPolice:
                listener.onButtonCallPoliceClick();
                break;
        }
    }
}
