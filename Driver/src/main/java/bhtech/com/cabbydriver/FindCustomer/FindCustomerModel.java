package bhtech.com.cabbydriver.FindCustomer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.CarObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.CustomerRequestObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;
import bhtech.com.cabbydriver.object.UserObj;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class FindCustomerModel implements FindCustomerInterface.DataSource {
    private Context context;
    private boolean firstZoom = true;

    public FindCustomerModel(Context context) {
        this.context = context;
    }

    private String currentAddress = "";
    private LatLng latLngDriver;
    private String fromAddress, toAddress;
    private LatLng fromLocation, toLocation;
    private String pickupTimeString;
    private ArrayList<CustomerRequestObj> listRequest;
    private ArrayList<Integer> listDistance = new ArrayList<>();
    private ArrayList<MarkerOptions> listMarkerOptions;
    private ArrayList<LocationObject> listNearbyLocation = new ArrayList<>();
    public static final String NULL = "";
    private int positonListViewClick = -1;
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
    DateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.US);

    @Override
    public ArrayList<CustomerRequestObj> getListRequest() {
        return listRequest;
    }

    public LatLng getLatLngDriver() {
        latLngDriver = CarDriverObj.getInstance().getLocation();
        return latLngDriver;
    }

    public void setLatLngDriver(LatLng latLngDriver) {
        this.latLngDriver = latLngDriver;
    }

    @Override
    public String getCurrentAddress() {
        return currentAddress;
    }

    @Override
    public void setToAddress(String s) {
        toAddress = s;
    }

    @Override
    public void setToLocation(LatLng latLng) {
        toLocation = latLng;
    }

    @Override
    public void setListNearbyLocation(ArrayList<LocationObject> listNearbyLocation) {
        this.listNearbyLocation = listNearbyLocation;
    }

    public ArrayList<LocationObject> getListNearbyLocation() {
        return listNearbyLocation;
    }

    @Override
    public void setFromAddress(String address) {
        fromAddress = address;
    }

    @Override
    public void setFromLocation(LatLng latLng) {
        fromLocation = latLng;
    }

    private void setPickupTimeString() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        this.pickupTimeString = TimeObject.getCurrentDateTime(timeFormat, dateFormat);
    }

    public ArrayList<MarkerOptions> getListMarkerOptions() {
        return listMarkerOptions;
    }

    public void setListMarkerOptions() {
        listMarkerOptions = new ArrayList<>();

        for (int i = 0; i < listRequest.size(); i++) {
            View markerLayout;
            if (i == positonListViewClick) {
                markerLayout = MarkerOptionsObject.customLayoutMarker(context, R.layout.custom_marker_layout_green);
            } else {
                if (driverChooseCustomerFutureBooking(i)) {
                    markerLayout = MarkerOptionsObject.customLayoutMarker(context, R.layout.custom_marker_layout_blue);
                } else {
                    markerLayout = MarkerOptionsObject.customLayoutMarker(context, R.layout.custom_marker_layout);
                }
            }

            TextView tvAddress = (TextView) markerLayout.findViewById(R.id.tvAddress);
            Double latitude = Double.parseDouble(listRequest.get(i).getFrom_latitude());
            Double longitude = Double.parseDouble(listRequest.get(i).getFrom_longitude());
            LatLng lng = new LatLng(latitude, longitude);
            String title = listRequest.get(i).getTo_name();
            tvAddress.setText(title);
            listMarkerOptions.add(MarkerOptionsObject.addMarker(context, markerLayout, lng, title));
        }
    }

    public boolean driverChooseCustomerFutureBooking(int positonListViewClick) {
        Date pickupTime = listRequest.get(positonListViewClick).getPickup_time();
        if (pickupTime == null) {
            return false;
        } else {
            return true;
        }
    }

    public void savePickUpTime() {
        Date pickupTime = listRequest.get(positonListViewClick).getPickup_time();
        SharedPreference.set(context, ContantValuesObject.PICK_UP_TIME, String.valueOf(pickupTime));
    }

    public int checkCustomerStatus(String data) {
        try {
            JSONObject statusJson = new JSONObject(data);
            return statusJson.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void saveDataCurrentRequest() {
        Log.w("positonListViewClick", String.valueOf(positonListViewClick));
        if (listRequest.size() > 0) {
            CustomerRequestObj request = listRequest.get(positonListViewClick);
            UserObj user = new UserObj();
            user.setUsername(request.getUsername());

            LatLng latLngFrom = new LatLng(Double.parseDouble(request.getFrom_latitude()),
                    Double.parseDouble(request.getFrom_longitude()));
            LatLng latLngTo = new LatLng(Double.parseDouble(request.getTo_latitude()),
                    Double.parseDouble(request.getTo_longitude()));

            TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusDriverSelected);
            TaxiRequestObj.getInstance().setRequestUser(user);
            TaxiRequestObj.getInstance().setFromLocation(latLngFrom);
            TaxiRequestObj.getInstance().setToLocation(latLngTo);
            TaxiRequestObj.getInstance().setFromLocationAddress(request.getFrom_address());
            TaxiRequestObj.getInstance().setToLocationAddress(request.getTo_address());
        } else {
            Log.w("saveDataCurrentRequest", "listRequest size < 0");
        }
    }

    public void saveDataForNonAppUser() {
        TaxiRequestObj.getInstance().setRequestUser(null);
        TaxiRequestObj.getInstance().setFromLocationAddress(currentAddress);
        TaxiRequestObj.getInstance().setFromLocation(latLngDriver);
        TaxiRequestObj.getInstance().setToLocationAddress(toAddress);
        TaxiRequestObj.getInstance().setToLocation(toLocation);
    }

    public boolean isFirstZoom() {
        return firstZoom;
    }

    public void setFirstZoom(boolean firstZoom) {
        this.firstZoom = firstZoom;
    }

    public void setCurrentAddress() {
        LocationObject.getCompleteAddressString(context, getLatLngDriver(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        currentAddress = address;
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        Log.e("setCurrentAddress", error.errorMessage);
                    }
                });
    }

    public interface StartNonAppUserTrip {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverChooseNonAppCustomer(OnDriverChooseCustomer onFinish) {
        String authToken = TaxiRequestObj.getInstance().getAuthToken();
        String request_id = String.valueOf(TaxiRequestObj.getInstance().getRequestId());
        String driver_car_id = String.valueOf(CarDriverObj.getInstance().getObjectID());

        driverChooseCustomer(authToken, request_id, driver_car_id, onFinish);
    }

    public void startNonAppUserTrip(final StartNonAppUserTrip OnFinish) {
        final ErrorObj error = checkNull();
        if (error.errorCode == ErrorObj.NO_ERROR) {
            CarDriverObj.getInstance().getCar().getStatus(context, new CarObj.OnGetCarStatusFinish() {
                @Override
                public void success() {
                    String carClass = String.valueOf(CarDriverObj.getInstance().getCar().getVehicleId());
                    String driverCarId = String.valueOf(CarDriverObj.getInstance().getObjectID());
                    Map<String, List<String>> headers = new HashMap<>();
                    headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

                    Map<String, List<String>> parameters = new HashMap<>();
                    parameters.put("from_address", Arrays.asList(currentAddress));
                    parameters.put("from_latitude", Arrays.asList(String.valueOf(latLngDriver.latitude)));
                    parameters.put("from_longitude", Arrays.asList(String.valueOf(latLngDriver.longitude)));
                    parameters.put("to_address", Arrays.asList(toAddress));
                    parameters.put("to_latitude", Arrays.asList(String.valueOf(toLocation.latitude)));
                    parameters.put("to_longitude", Arrays.asList(String.valueOf(toLocation.longitude)));
                    parameters.put("car_class", Arrays.asList(carClass));
                    parameters.put("driver_car_id", Arrays.asList(driverCarId));

                    NetworkObject.callAPI(context, ContantValuesObject.FIND_TAXI_ENDPOINT,
                            NetworkObject.POST, "NonAppUserTrip", headers, parameters, new NetworkObject.onCallApi() {
                                @Override
                                public void Success(JSONObject jsonObject) {
                                    try {
                                        int code = jsonObject.getInt(ContantValuesObject.CODE);
                                        switch (code) {
                                            case ErrorObj.NO_ERROR:
                                                JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                                TaxiRequestObj.getInstance().parseJsonToObject(results);
                                                SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                        String.valueOf(results.getInt("id")));
                                                OnFinish.Success();
                                                break;
                                            case ErrorObj.ERROR_AUTHTOKEN:
                                                error.errorCode = ErrorObj.ERROR_AUTHTOKEN;
                                                error.errorMessage = context.getString(R.string.error_authToken_please_login_again);
                                                OnFinish.Failure(error);
                                                break;
                                            default:
                                                error.setError(ErrorObj.UNKNOWN_ERROR);
                                                OnFinish.Failure(error);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        error.setError(ErrorObj.UNKNOWN_ERROR);
                                        OnFinish.Failure(error);
                                    }
                                }

                                @Override
                                public void Failure(ErrorObj error) {
                                    OnFinish.Failure(error);
                                }
                            }
                    );
                }

                @Override
                public void failure(ErrorObj errorObj) {
                    OnFinish.Failure(error);
                }
            });

        } else {
            OnFinish.Failure(error);
        }
    }

    private ErrorObj checkNull() {
        ErrorObj error = new ErrorObj();
        error.setError(ErrorObj.DATA_NULL);

        if (StringObject.isNullOrEmpty(currentAddress)) {
            error.errorMessage = context.getString(R.string.please_input_from_address);
        } else if (latLngDriver == null) {
            error.errorMessage = context.getString(R.string.please_input_from_location);
        } else if (StringObject.isNullOrEmpty(toAddress)) {
            error.errorMessage = context.getString(R.string.please_input_to_address);
        } else if (toLocation == null) {
            error.errorMessage = context.getString(R.string.please_input_to_location);
        } else {
            error.setError(ErrorObj.NO_ERROR);
        }
        return error;
    }

    public interface OnDriverChooseCustomer {
        void Success();

        void Falure(ErrorObj error);
    }

    public void driverChooseCustomer(final OnDriverChooseCustomer onFinish) {
        ErrorObj error = new ErrorObj();
        if (positonListViewClick < 0) {
            error.errorCode = ErrorObj.DATA_NULL;
            error.errorMessage = context.getString(R.string.you_did_not_choose_any_customer);
            onFinish.Falure(error);
        } else {
            String authToken = TaxiRequestObj.getInstance().getAuthToken();
            String request_id = String.valueOf(getListRequest().get(positonListViewClick).getObjectID());
            String driver_car_id = String.valueOf(CarDriverObj.getInstance().getObjectID());

            driverChooseCustomer(authToken, request_id, driver_car_id, onFinish);
        }
    }

    private void driverChooseCustomer(String authToken, final String request_id, String driver_car_id,
                                      final OnDriverChooseCustomer onFinish) {
        Log.w("authToken", authToken);
        Log.w("request_id", request_id);
        Log.w("driver_car_id", driver_car_id);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusDriverSelected)));
        params.put("driver_car_id", Arrays.asList(driver_car_id));

        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + request_id, NetworkObject.PUT,
                "DriverChooseCustomer", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            int code = jsonObject.getInt(ContantValuesObject.CODE);
                            switch (code) {
                                case ErrorObj.NO_ERROR:
                                    TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(request_id));
                                    onFinish.Success();
                                    break;

                                case ErrorObj.USER_CANCELED:
                                    onFinish.Falure(new ErrorObj(code,
                                            context.getString(R.string.this_request_has_been_cancelled_by_user)));
                                    break;
                                default:
                                    onFinish.Falure(new ErrorObj(ErrorObj.UNKNOWN_ERROR));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Falure(error);
                    }
                });
    }

    public interface onGetListRequest {
        void Success();

        void Falure(ErrorObj error);
    }

    public void getListRequest(final Context context, final onGetListRequest onFinish) {
        if (getLatLngDriver() != null) {
            final ErrorObj error = new ErrorObj();
            String authToken = TaxiRequestObj.getInstance().getAuthToken();

            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(authToken));
            headers.put("User-Agent", Arrays.asList("Android"));

            Map<String, List<String>> param = new HashMap<>();
            param.put("latitude", Arrays.asList(String.valueOf(getLatLngDriver().latitude)));
            param.put("longitude", Arrays.asList(String.valueOf(getLatLngDriver().longitude)));

            NetworkObject.callAPI(context, ContantValuesObject.FIND_CUSTOMER_ENDPOINT, NetworkObject.POST,
                    "Find_Customer", headers, param, new NetworkObject.onCallApi() {
                        @Override
                        public void Success(JSONObject jsonObject) {
                            listRequest = new ArrayList<>();
                            try {
                                switch (jsonObject.getInt(ContantValuesObject.CODE)) {
                                    case ErrorObj.NO_ERROR:
                                        JSONArray jsonArray = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            CustomerRequestObj requestObj = new CustomerRequestObj();
                                            requestObj.parseJsonToObject(object);
                                            listRequest.add(requestObj);
                                        }
                                        onFinish.Success();
                                        break;
                                    case ErrorObj.ERROR_AUTHTOKEN:
                                        error.errorCode = ErrorObj.ERROR_AUTHTOKEN;
                                        error.errorMessage = context.getString(R.string.error_authToken_please_login_again);
                                        onFinish.Falure(error);
                                        break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                error.errorMessage = e.toString();
                                onFinish.Falure(error);
                            }
                        }

                        @Override
                        public void Failure(ErrorObj error) {
                            onFinish.Falure(error);
                        }
                    });
        } else {
            //Do nothing
        }
    }

    @Override
    public ArrayList<Integer> getListDistance() {
        return listDistance;
    }

    public void setListDistance(ArrayList<Integer> listDistance) {
        this.listDistance = listDistance;
    }

    public void getListDistance(Context context) {
        ArrayList<Integer> listDistance = new ArrayList<>();
        for (int i = 0; i < getListRequest().size(); i++) {
            Double fromLatitude = Double.parseDouble(getListRequest().get(i).getFrom_latitude());
            Double fromLongitude = Double.parseDouble(getListRequest().get(i).getFrom_longitude());
            LatLng fromLatLng = new LatLng(fromLatitude, fromLongitude);
            listDistance.add((int) LocationObject.calculateDistance(getLatLngDriver(), fromLatLng));
        }
        setListDistance(listDistance);
    }

    @Override
    public String getCurrentDayOfWeek() {
        return String.valueOf(TimeObject.getStringDayOfWeek());
    }

    @Override
    public String getCurrentTime() {
        return TimeObject.getCurrentTime(timeFormat).toLowerCase();
    }

    @Override
    public String getCurrentDate() {
        return TimeObject.getCurrentDate(dateFormat);
    }

    @Override
    public void setListViewPositionClick(int position) {
        positonListViewClick = position;
    }

    @Override
    public int getListViewPositionClick() {
        return positonListViewClick;
    }

    @Override
    public GoogleMap.InfoWindowAdapter getGoogleMapInfoWindowAdapter(final Context context) {
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

    public interface OnGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }
}
