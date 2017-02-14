package bhtech.com.cabbytaxi.FavouriteLocation;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.GoogleAutoCompleteInterface;
import bhtech.com.cabbytaxi.SupportClass.GooglePlacesAutoCompleteAdapter;
import bhtech.com.cabbytaxi.object.PlaceAutocomplete;

public class FavouriteLocationAddView extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleAutoCompleteInterface {
    private Context context;
    private FragmentActivity fragmentActivity;
    private FavouriteLocationInterface.Listener listener;
    private FavouriteLocationInterface.Datasource datasource;

    public void setListener(FavouriteLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FavouriteLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    private GoogleApiClient mGoogleApiClient;
    private GooglePlacesAutoCompleteAdapter googlePlacesAutoCompleteAdapter;
    private FrameLayout btnThuNho, layoutEnterLocation;
    private Button btnAddLocation;
    private ImageView ivPlus;
    private AutoCompleteTextView etEnterLocation;
    private LatLngBounds latLngBounds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .enableAutoManage(fragmentActivity, 0, this)
                    .build();
        }
        if (datasource.getSouthwest() != null && datasource.getNortheast() != null) {
            latLngBounds = new LatLngBounds(datasource.getSouthwest(), datasource.getNortheast());
        } else {
            latLngBounds = new LatLngBounds(new LatLng(32.6393, -117.004304), new LatLng(44.901184, -67.32254));
        }
        googlePlacesAutoCompleteAdapter = new GooglePlacesAutoCompleteAdapter(context, latLngBounds, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_favourite_location_view, container, false);
        btnThuNho = (FrameLayout) v.findViewById(R.id.btnThuNho);
        btnAddLocation = (Button) v.findViewById(R.id.btnAddLocation);
        layoutEnterLocation = (FrameLayout) v.findViewById(R.id.layoutEnterLocation);
        ivPlus = (ImageView) v.findViewById(R.id.ivPlus);
        etEnterLocation = (AutoCompleteTextView) v.findViewById(R.id.etEnterLocation);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        ivPlus.setVisibility(View.GONE);
        btnAddLocation.setOnClickListener(this);
        btnThuNho.setOnClickListener(this);
        etEnterLocation.setAdapter(googlePlacesAutoCompleteAdapter);
        etEnterLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceAutocomplete item = googlePlacesAutoCompleteAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);

                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            return;
                        }
                        // Selecting the first object buffer.
                        final Place place = places.get(0);

                        datasource.setCompleteAddress(place.getAddress().toString());
                        datasource.setLatLngMapClick(place.getLatLng());
                        listener.onChooseLocation();
                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
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
            case R.id.btnAddLocation:
                listener.onButtonAddFavouriteLocationClick();
                break;
            case R.id.btnThuNho:
                if (ivPlus.isShown()) {
                    layoutEnterLocation.setVisibility(View.VISIBLE);
                    ivPlus.setVisibility(View.GONE);
                } else {
                    layoutEnterLocation.setVisibility(View.GONE);
                    ivPlus.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        googlePlacesAutoCompleteAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googlePlacesAutoCompleteAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void GoogleAutoCompleteLoadDataSuccess() {

    }

    @Override
    public void GoogleAutoCompleteLoadDataFailure() {

    }
}
