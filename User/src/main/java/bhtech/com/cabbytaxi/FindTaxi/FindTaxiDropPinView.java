package bhtech.com.cabbytaxi.FindTaxi;

import android.os.Bundle;
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

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class FindTaxiDropPinView extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {
    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;

    public void setListener(FindTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FindTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private FrameLayout btnBack;
    private TextView tvCompleteAddress;
    private SupportMapFragment mapFragment = null;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_taxi_drop_pin_view, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvCompleteAddress = (TextView) v.findViewById(R.id.tvCompleteAddress);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.dropPinViewButtonBackClick();
            }
        });

        if (UserObj.getInstance().getLocation() != null) {
            datasource.setFromLatitude(UserObj.getInstance().getLocation().latitude);
            datasource.setFromLongitude(UserObj.getInstance().getLocation().longitude);
            LocationObject.getCompleteAddressString(getActivity(),
                    new LatLng(datasource.getFromLatitude(), datasource.getFromLongitude()),
                    new LocationObject.onGetCompleteAddress() {
                        @Override
                        public void Success(String address) {
                            datasource.setFromAddress(address);
                        }

                        @Override
                        public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                            Log.w("onMapLongClick", error.errorMessage);
                        }
                    });
        }

        return v;
    }

    public void setCompleteAddress() {
        tvCompleteAddress.setText(datasource.getFromAddress());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        datasource = null;
    }

    public void drawDropPinMarker() {
        map.clear();
        map.addMarker(datasource.getDropPinMarkerOptions());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);
            map.setOnMapLongClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        datasource.setFromLatitude(latLng.latitude);
        datasource.setFromLongitude(latLng.longitude);
        LocationObject.getCompleteAddressString(getActivity(), latLng, new LocationObject.onGetCompleteAddress() {
            @Override
            public void Success(String address) {
                datasource.setFromAddress(address);
                listener.onMapLongClick();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                Log.w("onMapLongClick", error.errorMessage);
            }
        });
    }

    public void calculateZoomAndCamera(LatLng latLng) {
        if (latLng != null) {
            try {
                Log.w("Screen_DPI", String.valueOf(PhoneObject.getScreenDpi(getActivity())));
                float zoom = (float) (12 * 240 / PhoneObject.getScreenDpi(getActivity()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
                map.animateCamera(cameraUpdate);
            } catch (Exception e) {
                Log.e("Exception cameraUpdate", e.toString());
            }
        } else {
            Log.w("calculateZoomAndCamera", "LatLng null");
        }
    }
}
