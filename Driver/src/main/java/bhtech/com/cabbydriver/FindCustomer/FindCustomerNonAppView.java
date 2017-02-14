package bhtech.com.cabbydriver.FindCustomer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SupportClasses.GoogleAutoCompleteInterface;
import bhtech.com.cabbydriver.SupportClasses.GooglePlacesAutoCompleteAdapter;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.PlaceAutocomplete;

/**
 * Created by thanh_nguyen02 on 22/01/2016.
 */
public class FindCustomerNonAppView extends Fragment implements View.OnClickListener,
        GoogleAutoCompleteInterface, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private FindCustomerInterface.Listener listener;
    private FindCustomerInterface.DataSource dataSource;
    private FragmentActivity fragmentActivity;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    private Context context;
    private ImageView ivClose, ivNearbyLocation;
    private TextView tvCurrentAddress, btnStartNonApp;
    private AutoCompleteTextView tvAddressTo, etNearbyLocation;
    private static final String LOG_TAG = "FindCustomerNonAppView";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private GooglePlacesAutoCompleteAdapter googlePlacesAutoCompleteAdapter;
    private FrameLayout btnNearbyLocation;
    private ProgressBar pbNearbyLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_driver_find_customer_non_app_view, container, false);
        ivClose = (ImageView) v.findViewById(R.id.ivClose);
        ivNearbyLocation = (ImageView) v.findViewById(R.id.ivNearbyLocation);
        tvCurrentAddress = (TextView) v.findViewById(R.id.tvCurrentAddress);
        tvAddressTo = (AutoCompleteTextView) v.findViewById(R.id.tvAddressTo);
        etNearbyLocation = (AutoCompleteTextView) v.findViewById(R.id.etNearbyLocation);
        btnStartNonApp = (TextView) v.findViewById(R.id.btnStartNonApp);
        btnNearbyLocation = (FrameLayout) v.findViewById(R.id.btnNearbyLocation);
        pbNearbyLocation = (ProgressBar) v.findViewById(R.id.pbNearbyLocation);

        try {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .enableAutoManage(fragmentActivity, GOOGLE_API_CLIENT_ID, this)
                    .build();
        } catch (Exception e) {
        }

        googlePlacesAutoCompleteAdapter = new GooglePlacesAutoCompleteAdapter(context, null, this);

        btnNearbyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbNearbyLocation.setVisibility(View.VISIBLE);
                ivNearbyLocation.setVisibility(View.GONE);
                PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                        .getCurrentPlace(mGoogleApiClient, null);
                result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                    @Override
                    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                        ArrayList<LocationObject> listNearbyLocation = new ArrayList<>();
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            LocationObject location = new LocationObject();
                            location.setAddress(placeLikelihood.getPlace().getName().toString()
                                    + " - " + placeLikelihood.getPlace().getAddress());
                            location.setLatLng(placeLikelihood.getPlace().getLatLng());
                            listNearbyLocation.add(location);
                            if (listNearbyLocation.size() >= 5) {
                                break;
                            }
                        }
                        likelyPlaces.release();
                        dataSource.setListNearbyLocation(listNearbyLocation);
                        pbNearbyLocation.setVisibility(View.GONE);
                        ivNearbyLocation.setVisibility(View.VISIBLE);
                        listener.onIconLocationClick();
                    }
                });
            }
        });

        tvAddressTo.setAdapter(googlePlacesAutoCompleteAdapter);
        tvAddressTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        dataSource.setToAddress(place.getAddress().toString());
                        dataSource.setToLocation(place.getLatLng());
                    }
                });
            }
        });

        ivClose.setOnClickListener(this);
        btnStartNonApp.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        tvCurrentAddress.setText(dataSource.getCurrentAddress());
    }

    public void reloadListNearby() {
        final ArrayList<LocationObject> list = dataSource.getListNearbyLocation();
        if (list != null) {
            FindCustomerDropDownAdapter nearbyLocationAdapter = new FindCustomerDropDownAdapter(context, list);
            etNearbyLocation.setAdapter(nearbyLocationAdapter);
            etNearbyLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvCurrentAddress.setText(list.get(position).getAddress());
                    dataSource.setFromAddress(list.get(position).getAddress());
                    dataSource.setFromLocation(list.get(position).getLatLng());
                }
            });
            etNearbyLocation.showDropDown();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                tvAddressTo.setText("");
                listener.OnButtonCloseDialogClick();
                break;
            case R.id.btnStartNonApp:
                listener.OnButtonStartNonAppClick();
                break;
        }
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
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }
}
