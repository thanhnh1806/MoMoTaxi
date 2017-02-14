package bhtech.com.cabbydriver.ChangePickUpLocation;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SupportClasses.GoogleAutoCompleteInterface;
import bhtech.com.cabbydriver.SupportClasses.GooglePlacesAutoCompleteAdapter;
import bhtech.com.cabbydriver.object.PlaceAutocomplete;

public class ChangePickUpLocationView extends Fragment implements GoogleAutoCompleteInterface,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener {
    private ChangePickUpLocationInterface.Listener listener;
    private ChangePickUpLocationInterface.Datasource datasource;
    private FragmentActivity fragmentActivity;

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void setListener(ChangePickUpLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChangePickUpLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Context context;
    private static final String LOG_TAG = "FindTaxiOptionsView";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private GooglePlacesAutoCompleteAdapter googlePlacesAutoCompleteAdapter;

    FrameLayout btnX, layoutPickUpLocation, layoutType, btnCall, btnMessage, btnReloadLocation, btnGo;
    AutoCompleteTextView actvPickUpLocation;
    TextView btnCancel, btnOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_change_pick_up_location_view, container, false);
        btnX = (FrameLayout) v.findViewById(R.id.btnX);
        layoutPickUpLocation = (FrameLayout) v.findViewById(R.id.layoutPickUpLocation);
        layoutType = (FrameLayout) v.findViewById(R.id.layoutType);
        btnCall = (FrameLayout) v.findViewById(R.id.btnCall);
        btnMessage = (FrameLayout) v.findViewById(R.id.btnMessage);
        btnReloadLocation = (FrameLayout) v.findViewById(R.id.btnReloadLocation);
        btnGo = (FrameLayout) v.findViewById(R.id.btnGo);

        actvPickUpLocation = (AutoCompleteTextView) v.findViewById(R.id.actvPickUpLocation);
        btnCancel = (TextView) v.findViewById(R.id.btnCancel);
        btnOK = (TextView) v.findViewById(R.id.btnOK);

        btnX.setOnClickListener(this);
        layoutPickUpLocation.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnReloadLocation.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .enableAutoManage(fragmentActivity, GOOGLE_API_CLIENT_ID, this)
                    .build();
        }

        googlePlacesAutoCompleteAdapter = new GooglePlacesAutoCompleteAdapter(context, null, this);
        actvPickUpLocation.setAdapter(googlePlacesAutoCompleteAdapter);

        actvPickUpLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceAutocomplete item = googlePlacesAutoCompleteAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);

                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            Log.e(LOG_TAG, "Place query did not complete. Error: " +
                                    places.getStatus().toString());
                            return;
                        }
                        // Selecting the first object buffer.
                        final Place place = places.get(0);
                        datasource.setPickUpAddress(place.getAddress().toString());
                        datasource.setPickUpLocation(place.getLatLng());
                        listener.listGooglePlacesItemClick();
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onConnected(Bundle bundle) {
        googlePlacesAutoCompleteAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        googlePlacesAutoCompleteAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());
    }

    @Override
    public void GoogleAutoCompleteLoadDataSuccess() {
        Log.w("GoogleAutoComplete", "Success");
    }

    @Override
    public void GoogleAutoCompleteLoadDataFailure() {
        Log.w("GoogleAutoComplete", "Failure");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnX:
                layoutType.setVisibility(View.GONE);
                break;
            case R.id.layoutPickUpLocation:
                layoutType.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCancel:
                actvPickUpLocation.setText(null);
                listener.onButtonCancelClick();
                break;
            case R.id.btnOK:
                listener.onButtonOkClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
