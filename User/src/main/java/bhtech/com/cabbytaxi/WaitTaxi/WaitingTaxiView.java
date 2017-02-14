package bhtech.com.cabbytaxi.WaitTaxi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.services.NetworkServices;
import de.hdodenhof.circleimageview.CircleImageView;

public class WaitingTaxiView extends Fragment implements View.OnClickListener,
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private WaitTaxiInterface.Listener listener;
    private WaitTaxiInterface.Database database;

    public void setListener(WaitTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(WaitTaxiInterface.Database database) {
        this.database = database;
    }

    private GoogleMap map;
    private SupportMapFragment mapFragment = null;
    private TextView tvArrivingMinutes, tvWaitMinutes, tvWaitSecond, tvTaxiArrived;
    private TextView tvDriverName, tvNumberPlate, tvCancel, tvTaxiStartMoving;
    private FrameLayout ivCamera, ivMessages, ivCall;
    private LinearLayout layoutGetInTheRide, tvArriving;
    private CircleImageView ivDriverAvatar;
    private Button btnTaxiArrived, btnInTaxi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_waiting_taxi_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        tvArriving = (LinearLayout) v.findViewById(R.id.tvArriving);
        tvTaxiArrived = (TextView) v.findViewById(R.id.tvTaxiArrived);
        tvArrivingMinutes = (TextView) v.findViewById(R.id.tvArrivingMinutes);
        tvWaitMinutes = (TextView) v.findViewById(R.id.tvWaitMinutes);
        tvTaxiStartMoving = (TextView) v.findViewById(R.id.tvTaxiStartMoving);
        tvWaitSecond = (TextView) v.findViewById(R.id.tvWaitSecond);
        layoutGetInTheRide = (LinearLayout) v.findViewById(R.id.layoutGetInTheRide);
        ivDriverAvatar = (CircleImageView) v.findViewById(R.id.ivDriverAvatar);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvNumberPlate = (TextView) v.findViewById(R.id.tvNumberPlate);
        tvCancel = (TextView) v.findViewById(R.id.tvCancel);
        ivCamera = (FrameLayout) v.findViewById(R.id.ivCamera);
        ivMessages = (FrameLayout) v.findViewById(R.id.ivMessages);
        ivCall = (FrameLayout) v.findViewById(R.id.ivCall);

        btnTaxiArrived = (Button) v.findViewById(R.id.btnTaxiArrived);
        btnInTaxi = (Button) v.findViewById(R.id.btnInTaxi);

        mapFragment.getMapAsync(this);
        tvCancel.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        ivMessages.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        btnTaxiArrived.setOnClickListener(this);
        btnInTaxi.setOnClickListener(this);

        //For build test
        if (ContantValuesObject.isBuildTest) {
            btnTaxiArrived.setVisibility(View.VISIBLE);
            btnInTaxi.setVisibility(View.GONE);
        } else {
            btnTaxiArrived.setVisibility(View.GONE);
            btnInTaxi.setVisibility(View.GONE);
        }
        return v;
    }

    public void setEnableButtonCancel(boolean b) {
        tvCancel.setEnabled(b);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        database = null;
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                listener.onButtonCancelClick();
                break;
            case R.id.ivCamera:
                listener.onButtonCameraClick();
                break;
            case R.id.ivMessages:
                listener.onButtonMessagesClick();
                break;
            case R.id.ivCall:
                listener.onButtonCallClick();
                break;
            case R.id.btnTaxiArrived:
                btnInTaxi.setVisibility(View.VISIBLE);
                listener.onButtonTaxiArrivedClick();
                break;
            case R.id.btnInTaxi:
                listener.onButtonInTaxiClick();
                break;
        }
    }

    public void reloadView() {
        updateArrivingTime();
        setDriverInformation();
        if (database.getStatusRequest() == ContantValuesObject.TaxiRequestStatusDrivingToPassenger) {
            tvTaxiStartMoving.setVisibility(View.VISIBLE);
            layoutGetInTheRide.setVisibility(View.INVISIBLE);
        } else if (database.getStatusRequest() == ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger) {
            tvTaxiStartMoving.setVisibility(View.INVISIBLE);
            layoutGetInTheRide.setVisibility(View.VISIBLE);
            tvArriving.setVisibility(View.GONE);
            tvTaxiArrived.setVisibility(View.VISIBLE);
            updateWaitingTime(0, 0);
        } else {
            tvTaxiStartMoving.setVisibility(View.VISIBLE);
            layoutGetInTheRide.setVisibility(View.INVISIBLE);
        }
    }

    public void updateWaitingTime(int minutes, int second) {
        tvWaitMinutes.setText(String.valueOf(minutes));
        tvWaitSecond.setText(String.valueOf(second));
    }

    private void setDriverInformation() {
        try {
            if (database.getDriverPhoto() != null) {
                NetworkServices.imageRequest(getActivity(), database.getDriverPhoto(),
                        new NetworkServices.MakeImageRequestFinish() {
                            @Override
                            public void Success(Bitmap bitmap) {
                                ivDriverAvatar.setImageBitmap(bitmap);
                            }

                            @Override
                            public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                            }
                        });
            }
            tvDriverName.setText(database.getDriverName());
            tvNumberPlate.setText(database.getCarNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateArrivingTime() {
        try {
            if (database.getEstimateTime() != null) {
                tvArrivingMinutes.setText(" " + database.getEstimateTime() + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        listener.onGoogleMapReady();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.hideInfoWindow();
        return true;
    }

    public void reloadMapView() {
        map.clear();
        map.addMarker(database.getUserMarkerOptions());
        map.addMarker(database.getDriverMarkerOptions());
        calculateZoomAndCamera();
    }

    private void calculateZoomAndCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        try {
            if (database.getUserLatLng() != null) {
                builder.include(database.getUserLatLng());
            }
            if (database.getDriverLatLng() != null) {
                builder.include(database.getDriverLatLng());
            }

            LatLngBounds bounds = builder.build();
            int mapPadding = 100;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, mapPadding);
            map.animateCamera(cameraUpdate);
        } catch (Exception e) {
            Log.w("Exception cameraUpdate", e.toString());
        }
    }
}
