package bhtech.com.cabbytaxi.FindTaxi;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.MarkerOptionsObject;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.object.VehicleObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen02 on 16/12/2015.
 */
public class FindTaxiModel implements FindTaxiInterface.Datasource {
    private Context context;
    private boolean zoomMapFirstTime = true;
    private DateFormat timeFormat;
    private String fromAddress, toAddress, myLocationAddress;
    private Double fromLatitude, fromLongitude;
    private Double toLatitude, toLongitude;
    private Double myLatitude, myLongitude;
    private LatLng southwest, northeast;
    private Date pickupTime = null;
    private String pickupTimeString = null;
    private int statusRequest;
    private int methodPickupTime = ContantValuesObject.PickupTimeNow;
    private ArrayList<VehicleObj> listVehicle;
    private int carClass = 0;
    private MarkerOptions markerOptionsDriverMarker;
    private FragmentActivity fragmentActivity;
    private ArrayList<LocationObject> listFavouriteLocation = new ArrayList<>();
    private ArrayList<LocationObject> listFromHistoryLocation = new ArrayList<>();
    private ArrayList<LocationObject> listToHistoryLocation = new ArrayList<>();
    private ArrayList<CarDriverObj> listDriversAround = new ArrayList<>();
    private ArrayList<LocationObject> listNearbyLocation = new ArrayList<>();

    private static final int GOOGLE_API_CLIENT_ID = 0;

    private int currentView = ContantValuesObject.OptionView;

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    public FindTaxiModel(Context context) {
        this.context = context;
        timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public LatLng getSouthwest() {
        return southwest;
    }

    public LatLng getNortheast() {
        return northeast;
    }

    public ArrayList<LocationObject> getListNearbyLocation() {
        return listNearbyLocation;
    }

    public void setListNearbyLocation(ArrayList<LocationObject> listNearbyLocation) {
        this.listNearbyLocation = listNearbyLocation;
    }

    public int getCurrentView() {
        return currentView;
    }

    public void setCurrentView(int currentView) {
        this.currentView = currentView;
    }

    public ArrayList<CarDriverObj> getListDriversAround() {
        return listDriversAround;
    }

    public void setListDriversAround(ArrayList<CarDriverObj> list) {
        this.listDriversAround = list;
    }

    public void setListFavouriteLocation(ArrayList<LocationObject> listFavouriteLocation) {
        this.listFavouriteLocation = listFavouriteLocation;
    }

    @Override
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    @Override
    public void setFromLatitude(Double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    @Override
    public void setFromLongitude(Double fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    @Override
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public void setToLatitude(Double toLatitude) {
        this.toLatitude = toLatitude;
    }

    @Override
    public void setToLongitude(Double toLongitude) {
        this.toLongitude = toLongitude;
    }

    public Double getFromLatitude() {
        return fromLatitude;
    }

    public Double getFromLongitude() {
        return fromLongitude;
    }

    public Double getToLatitude() {
        return toLatitude;
    }

    public Double getToLongitude() {
        return toLongitude;
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public void setListFromHistoryLocation(ArrayList<LocationObject> listFromHistoryLocation) {
        this.listFromHistoryLocation = listFromHistoryLocation;
    }

    public void setListToHistoryLocation(ArrayList<LocationObject> listToHistoryLocation) {
        this.listToHistoryLocation = listToHistoryLocation;
    }

    @Override
    public ArrayList<LocationObject> getListFromHistoryLocation() {
        return listFromHistoryLocation;
    }

    @Override
    public ArrayList<LocationObject> getListToHistoryLocation() {
        return listToHistoryLocation;
    }


    public void setFragmentActivity(FragmentActivity activity) {
        fragmentActivity = activity;
    }

    public int getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(int statusRequest) {
        this.statusRequest = statusRequest;
    }

    public void setStatusRequest(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            setStatusRequest(jsonObject.getInt("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPickupTime(Date pickupTime) {
        if (pickupTime != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            setPickupTimeString(dateFormat.format(pickupTime));
        }
        this.pickupTime = pickupTime;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    @Override
    public void setHowtoChoosePickupTime(int method) {
        methodPickupTime = method;
    }

    public int getMethodPickupTime() {
        return methodPickupTime;
    }

    @Override
    public Date getCurrentTime() {
        setPickupTimeString(timeFormat.format(TimeObject.getCurrentDateTime()));
        return TimeObject.getCurrentDateTime();
    }

    public void setPickupTimeString(String pickupTimeString) {
        this.pickupTimeString = pickupTimeString;
    }

    @Override
    public String getPickupTimeString() {
        return pickupTimeString;
    }

    @Override
    public ArrayList<LatLng> getListLatLng() {
        ArrayList<LatLng> list = new ArrayList<>();
        if (myLatitude != null && myLongitude != null) {
            list.add(new LatLng(myLatitude, myLongitude));
        }
        if (fromLatitude != null && fromLongitude != null) {
            list.add(new LatLng(fromLatitude, fromLongitude));
        }
        if (toLatitude != null && toLongitude != null) {
            list.add(new LatLng(toLatitude, toLongitude));
        }

        if (listDriversAround != null) {
            for (int i = 0; i < listDriversAround.size(); i++) {
                list.add(listDriversAround.get(i).getLocation());
            }
        }
        return list;
    }

    @Override
    public MarkerOptions getDropPinMarkerOptions() {
        return MarkerOptionsObject.addMarker(new LatLng(fromLatitude, fromLongitude), R.drawable.icon);
    }

    @Override
    public String getFromAddress() {
        return fromAddress;
    }

    @Override
    public MarkerOptions getDestinationMarkerOptions() {
        return MarkerOptionsObject.addMarker(new LatLng(toLatitude, toLongitude), R.drawable.icon_flag_destination);
    }

    @Override
    public void setCarClass(int carClass) {
        this.carClass = carClass;
    }

    @Override
    public ArrayList<LocationObject> getListFavouriteLocation() {
        return listFavouriteLocation;
    }

    @Override
    public void setMyLocation(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        UserObj.getInstance().setLocation(LocationObject.locationToLatLng(location));
    }

    @Override
    public Location getMyLocation() {
        return LocationObject.latlngToLocation(UserObj.getInstance().getLocation());
    }

    @Override
    public void setMyLocationAddress(Context context) {
        getMyLocationCompleteAddressString(context, new onGetCompleteAddress() {
            @Override
            public void Success(String address) {
                myLocationAddress = address;
            }

            @Override
            public void Failure(Error error) {

            }
        });
    }

    @Override
    public String getMyLocationAddress() {
        return this.myLocationAddress;
    }

    public MarkerOptions getMarkerOptionsDriverMarker() {
        return markerOptionsDriverMarker;
    }

    public void setMarkerOptionsDriverMarker(MarkerOptions markerOptionsDriverMarker) {
        this.markerOptionsDriverMarker = markerOptionsDriverMarker;
    }

    public void setListHistoryLocation(Context context) {
        String fromHistoryLocation = SharedPreference.get(context,
                ContantValuesObject.LIST_FROM_HISTORY_LOCATION, "");
        Log.w("FromHistoryLocation", fromHistoryLocation);
        if (StringObject.isNullOrEmpty(fromHistoryLocation)) {
            setListFromHistoryLocation(null);
        } else {
            setListFromHistoryLocation(JsonArrayToArrayListLocationObject(fromHistoryLocation));
        }

        String toHistoryLocation = SharedPreference.get(context,
                ContantValuesObject.LIST_TO_HISTORY_LOCATION, "");
        Log.w("ToHistoryLocation", toHistoryLocation);
        if (StringObject.isNullOrEmpty(toHistoryLocation)) {
            setListToHistoryLocation(null);
        } else {
            setListToHistoryLocation(JsonArrayToArrayListLocationObject(toHistoryLocation));
        }
    }

    private ArrayList<LocationObject> JsonArrayToArrayListLocationObject(String jsonArray) {
        LocationObject[] arr = gson.fromJson(jsonArray, LocationObject[].class);
        ArrayList<LocationObject> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    public void setCurrentDateTime() {
        pickupTime = getCurrentTime();
    }

    public void setZoomMapFirstTime(boolean b) {
        zoomMapFirstTime = b;
    }

    public boolean isZoomMapFirstTime() {
        return zoomMapFirstTime;
    }

    public void saveFromToLocation() {
        Log.w("From_Address", fromAddress);
        Log.w("From_Address", toAddress);

        SharedPreference.set(context, "from_add", fromAddress);
        SharedPreference.set(context, "to_add", toAddress);
        SharedPreference.set(context, "from_lat", String.valueOf(fromLatitude));
        SharedPreference.set(context, "from_lng", String.valueOf(fromLongitude));
        SharedPreference.set(context, "to_lat", String.valueOf(toLatitude));
        SharedPreference.set(context, "to_lng", String.valueOf(toLongitude));

    }

    public int getEstimatedTimeFoundATaxi() {
        try {
            LatLng driverLatLng = TaxiRequestObj.getInstance().getResponseDriver().getLocation();
            if (driverLatLng != null) {
                float[] results = new float[1];
                Location.distanceBetween(fromLatitude, fromLongitude,
                        driverLatLng.latitude, driverLatLng.longitude, results);
                return (int) (results[0] / 1000) * 60 / ContantValuesObject.Speed;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public interface onGetGeometryBounds {
        void Success();

        void Failure(Error error);
    }

    public void getGeometryBounds(final onGetGeometryBounds onFinish) {
        LocationObject.getCountryFromLatLng(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCountryFromLatLng() {
                    @Override
                    public void Success(String address) {
                        LocationObject.getGeometryBounds(context, address, new LocationObject.onGetGeometryBounds() {
                            @Override
                            public void Success(LatLng southwest, LatLng northeast) {
                                FindTaxiModel.this.southwest = southwest;
                                FindTaxiModel.this.northeast = northeast;
                                onFinish.Success();
                            }

                            @Override
                            public void Failure(Error error) {
                                onFinish.Failure(error);
                            }
                        });
                    }

                    @Override
                    public void Failure(Error error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface OnSearchNearByLocation {
        void Success();

        void Failure(Error error);
    }

    public void getNearbyLocation(Context context, final OnSearchNearByLocation onFinish) {
        final Error error = new Error();
        if (myLatitude != null && myLongitude != null) {

            String lat = String.valueOf(myLatitude);
            String lng = String.valueOf(myLongitude);
            String radius = String.valueOf(200);
            String key = context.getString(R.string.google_server_key);

            final String url = ContantValuesObject.NEARBY_LOCATION_API
                    + "location=" + lat + "," + lng
                    + "&radius=" + radius
                    + "&key=" + key;

            if (NetworkObject.isNetworkConnect(context)) {
                NetworkServices.callAPI(context, url, "NearbyLocation", new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                if (isSuccess) {
                                    listNearbyLocation = new ArrayList<>();
                                    try {
                                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            if (i < 5) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                LocationObject location = new LocationObject();
                                                location.parseJsonToObject(object);
                                                listNearbyLocation.add(location);
                                            }
                                        }
                                        onFinish.Success();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        error.setError(Error.UNKNOWN_ERROR);
                                        onFinish.Failure(error);
                                    }
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            }
                        }

                );
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                error.errorMessage = context.getString(R.string.please_check_your_network);
                onFinish.Failure(error);
            }
        } else {
            error.setError(Error.DATA_NULL);
            error.errorMessage = context.getString(R.string.waiting_for_get_your_location);
            onFinish.Failure(error);
        }
    }

    public interface onCancelRequestFoundTaxi {
        void Success();

        void Failure(Error error);
    }

    public void cancelRequestFoundTaxi(Context context, final onCancelRequestFoundTaxi onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Map<String, List<String>> params = new HashMap<>();
            params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusCancelled)));

            NetworkServices.callAPI(context, ContantValuesObject.CANCEL_REQUEST_FOUND_TAXI +
                            TaxiRequestObj.getInstance().getRequestId(), NetworkServices.PUT, headers,
                    params, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            if (isSuccess) {
                                try {
                                    int code = jsonObject.getInt(ContantValuesObject.CODE);
                                    if (code == 0) {
                                        onFinish.Success();
                                    } else {
                                        error.setError(Error.INVALID_INPUTS);
                                        onFinish.Failure(error);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            } else {
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    });

        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onCancelRequestTaxi {
        void Success();

        void Failure(Error error);
    }

    public void cancelRequestDriver(Context context, final onCancelRequestTaxi onFinish) {
        final Error error = new Error();
        if (TaxiRequestObj.getInstance().getRequestId() < 0) {
            TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(SharedPreference.get(context,
                    ContantValuesObject.CURRENT_REQUEST_ID, ContantValuesObject.REQUEST_ID_NULL)));
        }

        if (TaxiRequestObj.getInstance().getRequestId() > 0) {
            if (NetworkObject.isNetworkConnect(context)) {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

                NetworkServices.callAPI(context, ContantValuesObject.CANCEL_DRIVER +
                                TaxiRequestObj.getInstance().getRequestId(), NetworkServices.PUT, headers,
                        null, new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                try {
                                    int code = jsonObject.getInt(ContantValuesObject.CODE);
                                    if (code == ContantValuesObject.NO_ERROR) {
                                        onFinish.Success();
                                    } else {
                                        error.setError(Error.INVALID_INPUTS);
                                        onFinish.Failure(error);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            }
                        });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                onFinish.Failure(error);
            }
        } else {
            error.setError(Error.REQUEST_ID_NULL);
            error.errorMessage = context.getString(R.string.request_id_null);
            onFinish.Failure(error);
        }
    }

    public interface onGetCurrentRequest {
        void Success();

        void Failure(Error error);
    }

    public void getCurrentRequest(final Context context, final onGetCurrentRequest onFinish) {
        final Error error = new Error();
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                try {
                    if (TaxiRequestObj.getInstance().getFromLocation() != null) {
                        setFromLatitude(TaxiRequestObj.getInstance().getFromLocation().latitude);
                        setFromLongitude(TaxiRequestObj.getInstance().getFromLocation().longitude);
                    }

                    if (TaxiRequestObj.getInstance().getToLocation() != null) {
                        setToLatitude(TaxiRequestObj.getInstance().getToLocation().latitude);
                        setToLongitude(TaxiRequestObj.getInstance().getToLocation().longitude);
                    }
                    onFinish.Success();
                } catch (Exception e) {
                    e.printStackTrace();
                    error.setError(Error.REQUEST_ID_NULL);
                    error.errorMessage = context.getString(R.string.request_id_null);
                    onFinish.Failure(error);
                }
            }

            @Override
            public void Failure(Error error) {
                onFinish.Failure(error);
            }
        });
    }

    public interface onGetListVehicle {
        void Success();

        void Failure(Error error);
    }

    public void getListVehicle(Context context, final onGetListVehicle onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            listVehicle = new ArrayList<>();
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.LIST_VEHICLE_ENDPOINT,
                    NetworkServices.GET, headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == 0) {
                                    JSONArray jsonArray = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        VehicleObj vehicleObj = new VehicleObj();
                                        vehicleObj.parseJsonToObject(object);
                                        listVehicle.add(vehicleObj);
                                    }
                                    onFinish.Success();
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }

                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public GoogleMap.InfoWindowAdapter getGoogleMapInfoWindowAdapter() {
        return new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.custom_info_windows, null);
                TextView tvMinutes = (TextView) v.findViewById(R.id.tvMinutes);
                TextView tvTaxiId = (TextView) v.findViewById(R.id.tvTaxiId);

                tvMinutes.setText(marker.getTitle() + " " + context.getString(R.string.min));
                tvTaxiId.setText(context.getString(R.string.taxi) + " " + marker.getSnippet());
                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
                return null;
            }
        };
    }

    @Override
    public String getDriverPhoto() {
        try {
            return ContantValuesObject.DOMAIN_IMAGE +
                    TaxiRequestObj.getInstance().getResponseDriver().getDriver().getPhoto();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public float getEstMileage() {
        return TaxiRequestObj.getInstance().getEst_mileage();
    }

    @Override
    public float getEstPrice() {
        return TaxiRequestObj.getInstance().getEst_price();
    }

    public MarkerOptions getFromMarkerOptions() {
        return MarkerOptionsObject.addMarker(new LatLng(fromLatitude, fromLongitude), R.drawable.location_avatar);
    }

    public MarkerOptions getFromMarkerOptionsOptionView() {
        return MarkerOptionsObject.addMarker(new LatLng(fromLatitude, fromLongitude), R.drawable.icon);
    }

    public MarkerOptions getToMarkerOptionsOptionView() {
        return MarkerOptionsObject.addMarker(new LatLng(toLatitude, toLongitude), R.drawable.icon);
    }

    public interface OnGetImageBitmap {
        void Success();

        void Failure(Error error);
    }

    public void addDriverMarker(final Context context, final CarDriverObj carDriverObj, final OnGetImageBitmap onFinish) {
        final String snippet = carDriverObj.getCar().getNumber();
        int carClass = getPositionListVehicleClick();
        final String url = ContantValuesObject.DOMAIN_IMAGE + listVehicle.get(carClass).getIcon_marker();
        Ion.with(context).load(url).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
            @Override
            public void onCompleted(Exception e, Bitmap result) {
                int defaultWidth = 118;
                int defaultHeight = 156;

                float phoneWidth = PhoneObject.getDefaultDisplayWidth(context);
                float phoneHeight = PhoneObject.getDefaultDisplayHeight(context);

                Bitmap resized = Bitmap.createScaledBitmap(result, (int) ((phoneWidth / 720) * defaultWidth),
                        (int) ((phoneHeight / 1280) * defaultHeight), true);
                setMarkerOptionsDriverMarker(MarkerOptionsObject.addMarker(resized, snippet,
                        carDriverObj.getLocation(), "8"));
                onFinish.Success();
            }
        });
    }

    @Override
    public ArrayList<VehicleObj> getListVehicle() {
        return listVehicle;
    }

    @Override
    public int getPositionListVehicleClick() {
        return carClass;
    }

    @Override
    public String getEstimatedTime() {
        try {
            if (TaxiRequestObj.getInstance().getResponseDriver() == null) {
                Log.e("getResponseDriver", "null");
                return null;
            } else {
                if (TaxiRequestObj.getInstance().getResponseDriver().getLocation() != null) {
                    LatLng user = TaxiRequestObj.getInstance().getRequestUser().getLocation();
                    return String.valueOf(TaxiRequestObj.getInstance().getResponseDriver()
                            .getEstimatetime(user.latitude, user.longitude));
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDriverName() {
        return TaxiRequestObj.getInstance().getResponseDriver().getDriver().getUsername();
    }

    @Override
    public String getCarNumber() {
        return TaxiRequestObj.getInstance().getResponseDriver().getCar().getNumber();
    }

    public interface onFindTaxi {
        void Success();

        void Failure(Error error);
    }

    public void findTaxi(final Context context, final onFindTaxi onFinish) {
        final Error error = checkValidate(context);
        if (error.errorCode == ContantValuesObject.NO_ERROR) {
            saveHistoryLocationData(context);
            setListHistoryLocation(context);

            String carId = String.valueOf(getListVehicle().get(getPositionListVehicleClick()).getObjectID());

            Log.w("Car Id", carId);
            for (int i = 0; i < listVehicle.size(); i++) {
                if (listVehicle.get(i).getObjectID() == Integer.parseInt(carId)) {
                    Log.w("Car Type", listVehicle.get(i).getName());
                }
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("authToken", UserObj.getInstance().getUser_token());

            Map<String, String> params = new HashMap<>();
            params.put("from_address", String.valueOf(fromAddress));
            params.put("from_latitude", String.valueOf(fromLatitude));
            params.put("from_longitude", String.valueOf(fromLongitude));
            params.put("to_address", String.valueOf(toAddress));
            params.put("to_latitude", String.valueOf(toLatitude));
            params.put("to_longitude", String.valueOf(toLongitude));
            if (methodPickupTime == ContantValuesObject.PickupTimeDateTime) {
                params.put("pickup_time", String.valueOf(pickupTimeString));
            } else {
            }
            params.put("car_class", String.valueOf(carId));

            NetworkServices.makeJsonObjectRequest(context, Request.Method.POST,
                    ContantValuesObject.DOMAIN + ContantValuesObject.FIND_TAXI_ENDPOINT, "FindTaxi",
                    headers, params, new NetworkServices.MakeJsonObjectRequestFinish() {
                        @Override
                        public void Success(JSONObject jsonObject) {
                            try {
                                int code = jsonObject.getInt(ContantValuesObject.CODE);
                                if (code == 0) {
                                    JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    TaxiRequestObj.getInstance().setRequestId(results.getInt("id"));
                                    SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                            String.valueOf(results.getInt("id")));
                                    onFinish.Success();
                                } else if (code == Error.REQUEST_ALREADY_EXIST) {
                                    error.setError(code);
                                    error.errorMessage = context.getString(R.string.request_already_exits);
                                    onFinish.Failure(error);
                                } else if (code == Error.BOOKED_REQUEST_IN_NEXT_HOUR) {
                                    error.setError(code, context.getString(R.string.booked_request_in_next_hour));
                                    onFinish.Failure(error);
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR, context.getString(R.string.unknown_error));
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void Failure(Error error) {
                            onFinish.Failure(error);
                        }
                    });

        } else {
            onFinish.Failure(error);
        }
    }

    public void saveHistoryLocationData(Context context) {
        if (StringObject.isNullOrEmpty(fromAddress) || fromLatitude == null || fromLongitude == null) {
            //Do nothing
        } else {
            LocationObject fromLocation = new LocationObject();
            fromLocation.setAddress(fromAddress);
            fromLocation.setLatLng(new LatLng(fromLatitude, fromLongitude));

            if (listFromHistoryLocation == null) {
                listFromHistoryLocation = new ArrayList<>();
                listFromHistoryLocation.add(fromLocation);
            } else {
                boolean duplicateHistoryLocation = false;
                for (int i = 0; i < listFromHistoryLocation.size(); i++) {
                    if (fromLocation.getAddress() != null) {
                        if (fromLocation.getAddress().equals(listFromHistoryLocation.get(i).getAddress())) {
                            duplicateHistoryLocation = true;
                        }
                    }
                }
                if (!duplicateHistoryLocation) {
                    listFromHistoryLocation.add(fromLocation);
                }
                if (listFromHistoryLocation.size() > 5) {
                    for (int i = 0; i <= listFromHistoryLocation.size() - 5; i++) {
                        listFromHistoryLocation.remove(i);
                    }
                }
            }
            SharedPreference.set(context, ContantValuesObject.LIST_FROM_HISTORY_LOCATION,
                    gson.toJson(listFromHistoryLocation));
        }

        if (StringObject.isNullOrEmpty(toAddress) || toLatitude == null || toLongitude == null) {
            //Do nothing
        } else {
            LocationObject toLocation = new LocationObject();
            toLocation.setAddress(toAddress);
            toLocation.setLatLng(new LatLng(toLatitude, toLongitude));
            if (listToHistoryLocation == null) {
                listToHistoryLocation = new ArrayList<>();
                listToHistoryLocation.add(toLocation);
            } else {
                boolean duplicateHistoryLocation = false;
                for (int i = 0; i < listToHistoryLocation.size(); i++) {
                    if (toLocation.getAddress() != null) {
                        if (toLocation.getAddress().equals(listToHistoryLocation.get(i).getAddress())) {
                            duplicateHistoryLocation = true;
                        }
                    }
                }
                if (!duplicateHistoryLocation) {
                    listToHistoryLocation.add(toLocation);
                }
                if (listToHistoryLocation.size() > 5) {
                    for (int i = 0; i <= listToHistoryLocation.size() - 5; i++) {
                        listToHistoryLocation.remove(i);
                    }
                }
            }
            SharedPreference.set(context, ContantValuesObject.LIST_TO_HISTORY_LOCATION,
                    gson.toJson(listToHistoryLocation));
        }
    }

    public Error checkValidate(Context context) {
        Log.i("FindTaxi", "checkValidate");
        Error error = new Error();
        error.setError(Error.DATA_NULL);

        if (StringObject.isNullOrEmpty(fromAddress)) {
            error.errorMessage = context.getString(R.string.please_input_from_address);
        } else if (fromLatitude == null) {
            error.errorMessage = context.getString(R.string.please_input_from_location);
        } else if (fromLongitude == null) {
            error.errorMessage = context.getString(R.string.please_input_from_location);
        } else if (StringObject.isNullOrEmpty(toAddress)) {
            error.errorMessage = context.getString(R.string.please_input_to_address);
        } else if (toLatitude == null) {
            error.errorMessage = context.getString(R.string.please_input_to_location);
        } else if (toLongitude == null) {
            error.errorMessage = context.getString(R.string.please_input_to_location);
        } else if (pickupTime != null && pickupTime.before(TimeObject.getCurrentDateTime())) {
            if (methodPickupTime == ContantValuesObject.PickupTimeDateTime) {
                error.setError(Error.PAST_PICKUP_TIME);
                error.errorMessage = context.getString(R.string.pick_up_time_in_past);
            } else {
                error.setError(Error.NO_ERROR);
            }
        } else {
            if (methodPickupTime == ContantValuesObject.PickupTimeDateTime) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 1);
                if (pickupTime == null) {
                    error.setError(Error.DATA_NULL);
                    error.errorMessage = context.getString(R.string.you_did_not_choose_time);
                } else {
                    if (pickupTime.before(calendar.getTime())) {
                        error.setError(Error.PICKUP_TIME_LESS_THAN_ONE_HOUR);
                        error.errorMessage = context.getString(R.string.pick_up_time_less_than_one_hour);
                    } else {
                        error.setError(Error.NO_ERROR);
                    }
                }
            } else {
                error.setError(Error.NO_ERROR);
            }
        }
        return error;
    }

    public interface onGetListDriversAround {
        void Success();

        void Failure(Error error);
    }

    public void getListDriversAround(Context context, final onGetListDriversAround onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            String lat = String.valueOf(fromLatitude);
            String lng = String.valueOf(fromLongitude);
            String vehicle = String.valueOf(getListVehicle().get(getPositionListVehicleClick()).getObjectID());

            Log.w("Car Id", vehicle);
            for (int i = 0; i < listVehicle.size(); i++) {
                if (listVehicle.get(i).getObjectID() == Integer.parseInt(vehicle)) {
                    Log.w("Car Type", listVehicle.get(i).getName());
                }
            }

            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.CARS_BY_LOCATION_ENDPOINT + "lat="
                            + lat + "&lng=" + lng + "&vehicle=" + vehicle, NetworkServices.GET,
                    headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == 0) {
                                    listDriversAround = new ArrayList<>();
                                    JSONArray listCar = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    for (int i = 0; i < listCar.length(); i++) {
                                        CarDriverObj obj = new CarDriverObj();
                                        obj.parseJsonToObject(listCar.getJSONObject(i));
                                        listDriversAround.add(obj);
                                    }
                                    onFinish.Success();
                                } else {
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                onFinish.Failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onGetListFavouriteLocation {
        void Success();

        void Failure(Error error);
    }

    public void getListFavouriteLocation(Context context, final onGetListFavouriteLocation onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.GET_LIST_FAVOURITE_LOCATION_ENDPOINT +
                            UserObj.getInstance().getObjectID(), NetworkServices.GET, headers,
                    null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == 0) {
                                    JSONArray jsonArray = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    ArrayList<LocationObject> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        LocationObject locationObject = new LocationObject();
                                        locationObject.parseJsonToObject(object);
                                        list.add(locationObject);
                                    }
                                    setListFavouriteLocation(list);
                                    onFinish.Success();
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onCustomerConfirm {
        void Success();

        void Failure(Error error);
    }

    public void customerConfirm(Context context, final onCustomerConfirm onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
            try {
                Map<String, List<String>> parameters = new HashMap<>();
                if (methodPickupTime == ContantValuesObject.PickupTimeDateTime) {
                    parameters.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusFutureBooking)));
                } else {
                    parameters.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusUserConfirmed)));
                }
                NetworkServices.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH +
                                TaxiRequestObj.getInstance().getRequestId(),
                        NetworkServices.PUT, headers, parameters, new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                try {
                                    int code = jsonObject.getInt(ContantValuesObject.CODE);
                                    if (code == 0) {
                                        onFinish.Success();
                                    } else if (code == 1) {
                                        error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                        onFinish.Failure(error);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                error.setError(Error.INVALID_INPUTS);
                onFinish.Failure(error);
            }
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onGetCompleteAddress {
        void Success(String address);

        void Failure(Error error);
    }

    public void getMyLocationCompleteAddressString(Context context, final onGetCompleteAddress onFinish) {
        if (getMyLocation() == null) {

        } else {
            LatLng latLng = LocationObject.locationToLatLng(getMyLocation());
            LocationObject.getCompleteAddressString(context, latLng, new LocationObject.onGetCompleteAddress() {
                @Override
                public void Success(String address) {
                    onFinish.Success(address);
                }

                @Override
                public void Failure(Error error) {
                    onFinish.Failure(error);
                }
            });
        }
    }
}
