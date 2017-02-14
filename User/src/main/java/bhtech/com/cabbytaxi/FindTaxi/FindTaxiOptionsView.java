package bhtech.com.cabbytaxi.FindTaxi;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.GoogleAutoCompleteInterface;
import bhtech.com.cabbytaxi.SupportClass.GooglePlacesAutoCompleteAdapter;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.PlaceAutocomplete;
import bhtech.com.cabbytaxi.object.VehicleObj;

public class FindTaxiOptionsView extends Fragment implements View.OnClickListener,
        GoogleAutoCompleteInterface, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnFocusChangeListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener {

    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;

    public void setListener(FindTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FindTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Context context;
    private static final String LOG_TAG = "FindTaxiOptionsView";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private ArrayList<LocationObject> listFavouriteLocation = new ArrayList<>();
    private ArrayList<LocationObject> listFromHistoryLocation = new ArrayList<>();
    private ArrayList<LocationObject> listToHistoryLocation = new ArrayList<>();
    private ArrayList<LocationObject> listNearbyLocation = new ArrayList<>();
    private RecyclerView.Adapter adapterRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GoogleApiClient mGoogleApiClient;
    private GooglePlacesAutoCompleteAdapter googlePlacesAutoCompleteAdapter;
    private FindTaxiDropDownAdapter favouriteLocationAdapter;
    private FindTaxiDropDownAdapter historyFromLocationAdapter;
    private FindTaxiDropDownAdapter historyToLocationAdapter;
    private FindTaxiDropDownAdapter nearbyLocationAdapter;
    private LatLngBounds latLngBounds;
    private GoogleMap map;
    private SupportMapFragment mapFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .enableAutoManage(datasource.getFragmentActivity(), GOOGLE_API_CLIENT_ID, this)
                    .build();
        }

        if (datasource.getSouthwest() != null && datasource.getNortheast() != null) {
            latLngBounds = new LatLngBounds(datasource.getSouthwest(), datasource.getNortheast());
        } else {
            latLngBounds = new LatLngBounds(new LatLng(32.6393, -117.004304), new LatLng(44.901184, -67.32254));
        }

        googlePlacesAutoCompleteAdapter = new GooglePlacesAutoCompleteAdapter(context, latLngBounds, null, this);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(R.layout.fragment_find_taxi_options_view, container, false);
            initView(v);
            addListener();
        } catch (Exception e) {
        }
        return v;
    }

    private void addListener() {
        mapFragment.getMapAsync(this);
        layoutOption.setOnClickListener(this);
        tvEnterDestination.setOnClickListener(this);

        ivSpinnerFromDropDown.setOnClickListener(this);
        ivSpinnerToDropDown.setOnClickListener(this);
        ivFromClearText.setOnClickListener(this);
        ivToClearText.setOnClickListener(this);
        ivFromHistoryLocation.setOnClickListener(this);
        ivToHistoryLocation.setOnClickListener(this);

        ivDropPin.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
        tvNow.setOnClickListener(this);
        tvDateTime.setOnClickListener(this);
        tvNowGray.setOnClickListener(this);
        tvDateTimeOrange.setOnClickListener(this);
        btnFind.setOnClickListener(this);

        etFromAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
        etFromAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        datasource.setFromAddress(place.getAddress().toString());
                        datasource.setFromLatitude(place.getLatLng().latitude);
                        datasource.setFromLongitude(place.getLatLng().longitude);
                        mGoogleApiClient.disconnect();
                        listener.onChooseFromLocation();
                    }
                });
            }
        });

        etFromAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onFocusChange(etFromAutoComplete, false);
                    onFocusChange(etToAutoComplete, true);
                    etFromAutoComplete.clearFocus();
                    etToAutoComplete.requestFocus();
                    mGoogleApiClient.connect();
                    return true;
                }
                return false;
            }
        });

        etFromAutoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onFocusChange(etFromAutoComplete, true);
                onFocusChange(etToAutoComplete, false);
                mGoogleApiClient.connect();
                return false;
            }
        });

        etFromAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivFromClearText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                datasource.setFromAddress(etFromAutoComplete.getText().toString());
            }
        });

        etToAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
        etToAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        datasource.setToAddress(place.getAddress().toString());
                        datasource.setToLatitude(place.getLatLng().latitude);
                        datasource.setToLongitude(place.getLatLng().longitude);
                        mGoogleApiClient.disconnect();
                        listener.onChooseToLocation();
                    }
                });
            }
        });

        etToAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    PhoneObject.hiddenSofwareKeyboard(context, etToAutoComplete);
                }
                return true;
            }
        });

        etToAutoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onFocusChange(etFromAutoComplete, false);
                onFocusChange(etToAutoComplete, true);
                mGoogleApiClient.connect();
                return false;
            }
        });

        etToAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivToClearText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                datasource.setToAddress(etToAutoComplete.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        listener.onCreateOptionsViewFinish();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private LinearLayout layoutOption;
    private FrameLayout ivDropPin, tvEnterDestination, ivLocation, ivFromClearText, ivToClearText,
            ivSpinnerFromDropDown, ivSpinnerToDropDown, ivFromHistoryLocation, ivToHistoryLocation, btnFind;
    private TextView tvNow, tvDateTime, tvNowGray, tvDateTimeOrange;
    private ProgressBar pbNearbyLocation;
    private AutoCompleteTextView etFromAutoComplete, etFromHistory, etNearbyLocation,
            etFromFavouriteLocation, etToAutoComplete, etToHistory, etToFavouriteLocation;
    private ImageView ivFromArowUp, ivFromArowDown, ivToArowUp, ivToArowDown, ivNearbyLocation;
    private View bottomBorderFrom, bottomBorderTo;
    private RecyclerView rvVehicle;

    private void initView(View v) {
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        layoutOption = (LinearLayout) v.findViewById(R.id.layoutOption);
        tvEnterDestination = (FrameLayout) v.findViewById(R.id.tvEnterDestination);
        ivDropPin = (FrameLayout) v.findViewById(R.id.ivDropPin);
        ivLocation = (FrameLayout) v.findViewById(R.id.ivLocation);
        ivFromClearText = (FrameLayout) v.findViewById(R.id.ivFromClearText);
        ivToClearText = (FrameLayout) v.findViewById(R.id.ivToClearText);
        ivSpinnerFromDropDown = (FrameLayout) v.findViewById(R.id.ivSpinnerFromDropDown);
        ivSpinnerToDropDown = (FrameLayout) v.findViewById(R.id.ivSpinnerToDropDown);

        ivFromArowUp = (ImageView) v.findViewById(R.id.ivFromArowUp);
        ivFromArowDown = (ImageView) v.findViewById(R.id.ivFromArowDown);
        ivToArowDown = (ImageView) v.findViewById(R.id.ivToArowDown);
        ivToArowUp = (ImageView) v.findViewById(R.id.ivToArowUp);

        ivNearbyLocation = (ImageView) v.findViewById(R.id.ivNearbyLocation);
        pbNearbyLocation = (ProgressBar) v.findViewById(R.id.pbNearbyLocation);
        etFromAutoComplete = (AutoCompleteTextView) v.findViewById(R.id.etFromAutoComplete);
        ivFromHistoryLocation = (FrameLayout) v.findViewById(R.id.ivFromHistoryLocation);
        etFromHistory = (AutoCompleteTextView) v.findViewById(R.id.etFromHistory);
        etNearbyLocation = (AutoCompleteTextView) v.findViewById(R.id.etNearbyLocation);
        etFromFavouriteLocation = (AutoCompleteTextView) v.findViewById(R.id.etFromFavouriteLocation);

        etToAutoComplete = (AutoCompleteTextView) v.findViewById(R.id.etToAutoComplete);
        ivToHistoryLocation = (FrameLayout) v.findViewById(R.id.ivToHistoryLocation);
        etToHistory = (AutoCompleteTextView) v.findViewById(R.id.etToHistory);
        etToFavouriteLocation = (AutoCompleteTextView) v.findViewById(R.id.etToFavouriteLocation);

        bottomBorderFrom = v.findViewById(R.id.bottomBorderFrom);
        bottomBorderTo = v.findViewById(R.id.bottomBorderTo);

        tvNow = (TextView) v.findViewById(R.id.tvNow);
        tvDateTime = (TextView) v.findViewById(R.id.tvDateTime);
        tvNowGray = (TextView) v.findViewById(R.id.tvNowGray);
        tvDateTimeOrange = (TextView) v.findViewById(R.id.tvDateTimeOrange);

        rvVehicle = (RecyclerView) v.findViewById(R.id.rvVehicle);
        rvVehicle.setHasFixedSize(true);
        rvVehicle.setLayoutManager(layoutManager);

        btnFind = (FrameLayout) v.findViewById(R.id.btnFind);

    }

    public void reloadData() {
        listFavouriteLocation = datasource.getListFavouriteLocation();
        favouriteLocationAdapter = new FindTaxiDropDownAdapter(context, listFavouriteLocation,
                context.getString(R.string.favourite_location));

        etFromFavouriteLocation.setAdapter(favouriteLocationAdapter);
        etFromFavouriteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGoogleApiClient.disconnect();
                etFromAutoComplete.setText(listFavouriteLocation.get(position).getAddress());

                datasource.setFromAddress(listFavouriteLocation.get(position).getAddress());
                datasource.setFromLatitude(listFavouriteLocation.get(position).getLatLng().latitude);
                datasource.setFromLongitude(listFavouriteLocation.get(position).getLatLng().longitude);

                ivFromArowDown.setVisibility(View.VISIBLE);
                ivFromArowUp.setVisibility(View.GONE);

                listener.onChooseFromLocation();
            }
        });

        etToFavouriteLocation.setAdapter(favouriteLocationAdapter);
        etToFavouriteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGoogleApiClient.disconnect();
                etToAutoComplete.setText(listFavouriteLocation.get(position).getAddress());

                datasource.setToAddress(listFavouriteLocation.get(position).getAddress());
                datasource.setToLatitude(listFavouriteLocation.get(position).getLatLng().latitude);
                datasource.setToLongitude(listFavouriteLocation.get(position).getLatLng().longitude);

                ivToArowDown.setVisibility(View.VISIBLE);
                ivToArowUp.setVisibility(View.GONE);

                listener.onChooseToLocation();
            }
        });

        listFromHistoryLocation = datasource.getListFromHistoryLocation();
        if (listFromHistoryLocation != null) {
            historyFromLocationAdapter = new FindTaxiDropDownAdapter(context, listFromHistoryLocation,
                    context.getString(R.string.history_location));

            etFromHistory.setAdapter(historyFromLocationAdapter);
            etFromHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mGoogleApiClient.disconnect();
                    etFromAutoComplete.setText(listFromHistoryLocation.get(position).getAddress());

                    datasource.setFromAddress(listFromHistoryLocation.get(position).getAddress());
                    datasource.setFromLatitude(listFromHistoryLocation.get(position).getLatLng().latitude);
                    datasource.setFromLongitude(listFromHistoryLocation.get(position).getLatLng().longitude);

                    ivFromArowDown.setVisibility(View.VISIBLE);
                    ivFromArowUp.setVisibility(View.GONE);

                    listener.onChooseFromLocation();
                }
            });
        } else {
            //Do nothing
        }

        listToHistoryLocation = datasource.getListToHistoryLocation();
        if (listToHistoryLocation != null) {
            historyToLocationAdapter = new FindTaxiDropDownAdapter(context, listToHistoryLocation,
                    context.getString(R.string.history_location));
            etToHistory.setAdapter(historyToLocationAdapter);
            etToHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mGoogleApiClient.disconnect();
                    etToAutoComplete.setText(listToHistoryLocation.get(position).getAddress());

                    datasource.setToAddress(listToHistoryLocation.get(position).getAddress());
                    datasource.setToLatitude(listToHistoryLocation.get(position).getLatLng().latitude);
                    datasource.setToLongitude(listToHistoryLocation.get(position).getLatLng().longitude);

                    ivToArowDown.setVisibility(View.VISIBLE);
                    ivToArowUp.setVisibility(View.GONE);

                    listener.onChooseToLocation();
                }
            });
        } else {
            //Do nothing
        }
    }

    public void reloadListVehicle() {
        try {
            ArrayList<VehicleObj> list = datasource.getListVehicle();
            if (list.size() > 0) {
                adapterRecyclerView = new ListVehicleAdapter(context, list, listener, datasource);
                rvVehicle.setAdapter(adapterRecyclerView);
                rvVehicle.smoothScrollToPosition(datasource.getPositionListVehicleClick());
                rvVehicle.scrollToPosition(datasource.getPositionListVehicleClick());
            } else {

            }
        } catch (Exception e) {
            Log.e("OptionsView", e.toString());
        }
    }

    public void reloadListNearby() {
        listNearbyLocation = datasource.getListNearbyLocation();
        if (listNearbyLocation != null) {
            nearbyLocationAdapter = new FindTaxiDropDownAdapter(context, listNearbyLocation,
                    context.getString(R.string.nearby_location));
            etNearbyLocation.setAdapter(nearbyLocationAdapter);
            etNearbyLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mGoogleApiClient.disconnect();
                    etFromAutoComplete.setText(listNearbyLocation.get(position).getAddress());

                    datasource.setFromAddress(listNearbyLocation.get(position).getAddress());
                    datasource.setFromLatitude(listNearbyLocation.get(position).getLatLng().latitude);
                    datasource.setFromLongitude(listNearbyLocation.get(position).getLatLng().longitude);

                    ivFromArowDown.setVisibility(View.VISIBLE);
                    ivFromArowUp.setVisibility(View.GONE);
                    listener.onChooseFromLocation();
                }
            });
            etNearbyLocation.showDropDown();
        }
    }

    @Override
    public void onClick(View v) {
        PhoneObject.hiddenSofwareKeyboard(context, etFromAutoComplete);
        transparentEnterDestination(false);
        switch (v.getId()) {
            case R.id.layoutOption:
                ivFromArowDown.setVisibility(View.VISIBLE);
                ivFromArowUp.setVisibility(View.GONE);
                ivToArowDown.setVisibility(View.VISIBLE);
                ivToArowUp.setVisibility(View.GONE);
                break;
            case R.id.tvEnterDestination:
                if (datasource.getPickupTime() == null) {
                    chooseButtonNow();
                }
                listener.onEnterDestinationClick();
                break;

            case R.id.ivDropPin:
                listener.dropPinClick();
                break;
            case R.id.ivLocation:
                pbNearbyLocation.setVisibility(View.VISIBLE);
                ivNearbyLocation.setVisibility(View.GONE);
                onFocusChange(etFromAutoComplete, true);
                onFocusChange(etToAutoComplete, false);

                mGoogleApiClient.connect();
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
                        datasource.setListNearbyLocation(listNearbyLocation);
                        pbNearbyLocation.setVisibility(View.GONE);
                        ivNearbyLocation.setVisibility(View.VISIBLE);
                        listener.onIconLocationClick();
                    }
                });
                break;

            case R.id.ivFromClearText:
                etFromAutoComplete.setText("");
                ivFromClearText.setVisibility(View.INVISIBLE);
                onFocusChange(etFromAutoComplete, true);
                onFocusChange(etToAutoComplete, false);
                mGoogleApiClient.connect();
                break;

            case R.id.ivToClearText:
                etToAutoComplete.setText("");
                ivToClearText.setVisibility(View.INVISIBLE);
                onFocusChange(etFromAutoComplete, false);
                onFocusChange(etToAutoComplete, true);
                mGoogleApiClient.connect();
                break;

            case R.id.ivSpinnerFromDropDown:
                ivToArowDown.setVisibility(View.VISIBLE);
                ivToArowUp.setVisibility(View.GONE);
                bottomBorderFrom.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                bottomBorderTo.setBackgroundColor(getResources().getColor(R.color.gray));
                if (ivFromArowDown.isShown()) {
                    etFromFavouriteLocation.showDropDown();
                    ivFromArowDown.setVisibility(View.GONE);
                    ivFromArowUp.setVisibility(View.VISIBLE);
                } else {
                    etFromFavouriteLocation.dismissDropDown();
                    ivFromArowDown.setVisibility(View.VISIBLE);
                    ivFromArowUp.setVisibility(View.GONE);
                }
                listener.clickFavouriteLocation();
                break;

            case R.id.ivSpinnerToDropDown:
                ivFromArowDown.setVisibility(View.VISIBLE);
                ivFromArowUp.setVisibility(View.GONE);
                bottomBorderFrom.setBackgroundColor(getResources().getColor(R.color.gray));
                bottomBorderTo.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                if (ivToArowDown.isShown()) {
                    etToFavouriteLocation.showDropDown();
                    ivToArowDown.setVisibility(View.GONE);
                    ivToArowUp.setVisibility(View.VISIBLE);
                } else {
                    ivToArowDown.setVisibility(View.VISIBLE);
                    ivToArowUp.setVisibility(View.GONE);
                }
                listener.clickFavouriteLocation();
                break;

            case R.id.ivFromHistoryLocation:
                onFocusChange(etFromAutoComplete, true);
                onFocusChange(etToAutoComplete, false);
                etFromHistory.showDropDown();
                break;

            case R.id.ivToHistoryLocation:
                onFocusChange(etFromAutoComplete, false);
                onFocusChange(etToAutoComplete, true);
                etToHistory.showDropDown();
                break;
            case R.id.tvNow:
                chooseButtonNow();
                break;
            case R.id.tvDateTime:
                tvDateTime.setVisibility(View.GONE);
                tvDateTimeOrange.setVisibility(View.VISIBLE);
                tvNow.setVisibility(View.GONE);
                tvNowGray.setVisibility(View.VISIBLE);
                datasource.setHowtoChoosePickupTime(ContantValuesObject.PickupTimeDateTime);
                listener.onButtonDateTimeClick();
                break;
            case R.id.tvNowGray:
                tvNow.setVisibility(View.VISIBLE);
                tvNowGray.setVisibility(View.GONE);
                tvDateTime.setVisibility(View.VISIBLE);
                tvDateTimeOrange.setVisibility(View.GONE);
                setDateTimeForButtonPickUp(false);
                datasource.setHowtoChoosePickupTime(ContantValuesObject.PickupTimeNow);
                datasource.setPickupTime(null);
                break;
            case R.id.tvDateTimeOrange:
                tvNow.setVisibility(View.GONE);
                tvNowGray.setVisibility(View.VISIBLE);
                tvDateTime.setVisibility(View.GONE);
                tvDateTimeOrange.setVisibility(View.VISIBLE);
                datasource.setHowtoChoosePickupTime(ContantValuesObject.PickupTimeDateTime);
                listener.onButtonDateTimeClick();
                break;
            case R.id.btnFind:
                listener.onButtonFindTaxiClick();
                break;
        }
    }

    public void chooseButtonNow() {
        tvDateTime.setVisibility(View.VISIBLE);
        tvDateTimeOrange.setVisibility(View.GONE);
        tvNow.setVisibility(View.VISIBLE);
        tvNowGray.setVisibility(View.GONE);
        setDateTimeForButtonPickUp(false);
        datasource.setHowtoChoosePickupTime(ContantValuesObject.PickupTimeNow);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ivFromArowDown.setVisibility(View.VISIBLE);
        ivFromArowUp.setVisibility(View.GONE);
        ivToArowDown.setVisibility(View.VISIBLE);
        ivToArowUp.setVisibility(View.GONE);

        switch (v.getId()) {
            case R.id.etFromAutoComplete:
                if (hasFocus) {
                    etFromAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
                    etToAutoComplete.setAdapter(null);
                    bottomBorderFrom.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                } else {
                    etFromAutoComplete.setAdapter(null);
                    etToAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
                    bottomBorderFrom.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                break;
            case R.id.etToAutoComplete:
                if (hasFocus) {
                    etFromAutoComplete.setAdapter(null);
                    etToAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
                    bottomBorderTo.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                } else {
                    etFromAutoComplete.setAdapter(googlePlacesAutoCompleteAdapter);
                    etToAutoComplete.setAdapter(null);
                    bottomBorderTo.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                break;
        }
    }

    public void setEnabledButtonFindTaxi(boolean b) {
        btnFind.setEnabled(b);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
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

    public void setTextFromWithDropPin() {
        etFromAutoComplete.setText(datasource.getFromAddress());
    }

    public void setDateTimeForButtonPickUp(boolean b) {
        if (b) {
            DateFormat timeFormat = new SimpleDateFormat("dd MMM yyy hh.mm a");
            Date date = datasource.getPickupTime();
            tvDateTimeOrange.setText(timeFormat.format(date));
        } else {
            tvDateTimeOrange.setText(getString(R.string.datetime));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setOnMyLocationChangeListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        datasource.setMyLocation(location);
        listener.optionsViewMapLocationChange();
    }

    public void calculateZoomAndCamera(LatLng latLng) {
        if (latLng != null) {
            try {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
                map.animateCamera(cameraUpdate);
                datasource.setZoomMapFirstTime(false);
            } catch (Exception e) {
                Log.e("Exception cameraUpdate", e.toString());
                datasource.setZoomMapFirstTime(true);
            }
        } else {
            Log.w("calculateZoomAndCamera", "LatLng null");
        }
    }

    public void drawFromMarker() {
        map.clear();
        map.addMarker(datasource.getFromMarkerOptionsOptionView());
        calculateZoomAndCamera(new LatLng(datasource.getFromLatitude(), datasource.getFromLongitude()));
    }

    public void drawToMarker() {
        map.clear();
        map.addMarker(datasource.getToMarkerOptionsOptionView());
        calculateZoomAndCamera(new LatLng(datasource.getToLatitude(), datasource.getToLongitude()));
    }

    public void transparentEnterDestination(boolean b) {
        if (b) {
            tvEnterDestination.setBackgroundColor(Color.parseColor("#90f7f9f9"));
        } else {
            tvEnterDestination.setBackgroundColor(Color.parseColor("#f7f9f9"));
        }
    }
}
