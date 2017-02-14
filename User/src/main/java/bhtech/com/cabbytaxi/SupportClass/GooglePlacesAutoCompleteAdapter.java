package bhtech.com.cabbytaxi.SupportClass;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.PlaceAutocomplete;

/**
 * Created by thanh_nguyen on 08/03/2016.
 */
public class GooglePlacesAutoCompleteAdapter extends ArrayAdapter<PlaceAutocomplete>
        implements Filterable {
    private GoogleAutoCompleteInterface listener;
    private static final String TAG = "PlaceArrayAdapter";
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private AutocompleteFilter mPlaceFilter;
    private LatLngBounds mBounds;
    private ArrayList<PlaceAutocomplete> mResultList;


    public GooglePlacesAutoCompleteAdapter(Context context, LatLngBounds latLngBounds, AutocompleteFilter filter,
                                           GoogleAutoCompleteInterface listener) {
        super(context, R.layout.google_place_list_item);
        this.context = context;
        mBounds = latLngBounds;
        mPlaceFilter = filter;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        try {
            return mResultList.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            mGoogleApiClient = null;
        } else {
            mGoogleApiClient = googleApiClient;
        }
    }

    @Override
    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    mResultList = getPredictions(constraint);
                    if (mResultList != null) {
                        // Results
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                    listener.GoogleAutoCompleteLoadDataSuccess();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                    listener.GoogleAutoCompleteLoadDataFailure();
                }
            }
        };
        return filter;
    }

    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {
        if (mGoogleApiClient != null) {
            Log.i(TAG, "Executing autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi
                    .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                            mBounds, mPlaceFilter);
            // Wait for predictions, set the timeout.
            AutocompletePredictionBuffer autocompletePredictions = results.await(300, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Log.e(TAG, "Error getting place predictions: " + status
                        .toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(),
                        prediction.getDescription()));
            }
            // Buffer release
            autocompletePredictions.release();
            return resultList;
        }
        Log.e(TAG, "Google API client is not connected.");
        return null;
    }
}

