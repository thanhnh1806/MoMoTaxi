package bhtech.com.cabbytaxi.WaitTaxi;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.MarkerOptionsObject;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 24/12/2015.
 */
public class WaitTaxiModel implements WaitTaxiInterface.Database {
    private Context context;
    private LatLng customerLatLng;
    private LatLng driverLatLng;
    private LatLng pickUpLocation;
    private int statusRequest;

    public WaitTaxiModel(Context context) {
        this.context = context;
    }

    public void setCustomerLatLng(LatLng customerLatLng) {
        this.customerLatLng = customerLatLng;
    }

    public void setDriverLatLng(LatLng driverLatLng) {
        this.driverLatLng = driverLatLng;
    }

    public int getStatusRequest() {
        return statusRequest;
    }

    @Override
    public MarkerOptions getUserMarkerOptions() {
        return MarkerOptionsObject.addMarker(customerLatLng, R.drawable.icon_my_location_wait_taxi);
    }

    @Override
    public MarkerOptions getDriverMarkerOptions() {
        return MarkerOptionsObject.addMarker(driverLatLng, R.drawable.icon_driver_location_wait_taxi);
    }

    @Override
    public LatLng getUserLatLng() {
        return customerLatLng;
    }

    @Override
    public LatLng getDriverLatLng() {
        return driverLatLng;
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
    public String getEstimateTime() {
        try {
            float[] results = new float[1];
            Location.distanceBetween(pickUpLocation.latitude, pickUpLocation.longitude,
                    driverLatLng.latitude, driverLatLng.longitude, results);
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            Log.w("Distance_Between", String.valueOf(results[0] / 1000));
            return decimalFormat.format((results[0] / 1000) * 60 / ContantValuesObject.Speed);
        } catch (Exception e) {
            return "";
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

    public interface onGetCurrentRequest {
        void Success();

        void Failure(Error error);
    }

    public void getCurrentRequest(final onGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                try {
                    setCustomerLatLng(TaxiRequestObj.getInstance().getRequestUser().getLocation());
                    setDriverLatLng(TaxiRequestObj.getInstance().getResponseDriver().getLocation());
                    setStatusRequest(TaxiRequestObj.getInstance().getStatus());

                    Double from_lat = Double.valueOf(SharedPreference.get(context, "from_lat", "0"));
                    Double from_lng = Double.valueOf(SharedPreference.get(context, "from_lng", "0"));

                    pickUpLocation = new LatLng(from_lat, from_lng);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onFinish.Success();
            }

            @Override
            public void Failure(Error error) {
                onFinish.Failure(error);
            }
        });
    }

    public interface onWaitTaxi {
        void Success();

        void Failure(Error error);
    }

    public void waitTaxi(final onWaitTaxi onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.WAIT_TAXI_ENDPOINT
                            + TaxiRequestObj.getInstance().getRequestId(),
                    NetworkServices.GET, headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt("Code") == Error.NO_ERROR) {
                                    JSONObject results = jsonObject.getJSONObject("Results");

                                    if (results.has("customer_latitude") && !results.isNull("customer_latitude")
                                            && results.has("customer_longitude") && !results.isNull("customer_longitude")) {
                                        Double userLat = Double.parseDouble(results.getString("customer_latitude"));
                                        Double userLng = Double.parseDouble(results.getString("customer_longitude"));

                                        LatLng userLatLng = new LatLng(userLat, userLng);
                                        TaxiRequestObj.getInstance().getRequestUser().setLocation(userLatLng);
                                        setCustomerLatLng(userLatLng);

                                        if (results.has("driver_latitude") && !results.isNull("driver_latitude")
                                                && results.has("driver_longitude") && !results.isNull("driver_longitude")) {
                                            Double driverLat = Double.parseDouble(results.getString("driver_latitude"));
                                            Double driverLng = Double.parseDouble(results.getString("driver_longitude"));
                                            LatLng driverLatLng = new LatLng(driverLat, driverLng);

                                            TaxiRequestObj.getInstance().getResponseDriver().setLocation(driverLatLng);
                                            setDriverLatLng(driverLatLng);
                                            onFinish.Success();
                                        } else {
                                            error.setError(Error.DATA_NULL, context.getString(R.string.can_not_get_driver_location));
                                            onFinish.Failure(error);
                                        }
                                    } else {
                                        error.setError(Error.DATA_NULL, context.getString(R.string.waiting_for_get_your_location));
                                        onFinish.Failure(error);
                                    }
                                } else {
                                    error.errorMessage = jsonObject.getString("Message");
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.Failure(error);
        }
    }

    public interface onCancelRequestTaxi {
        void Success();

        void Failure(Error error);
    }

    public void cancelRequestTaxi(Context context, final onCancelRequestTaxi onFinish) {
        final Error error = new Error();
        if (TaxiRequestObj.getInstance().getRequestId() > 0) {
            if (NetworkObject.isNetworkConnect(context)) {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

                Map<String, List<String>> params = new HashMap<>();
                params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusCancelled)));

                NetworkServices.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH +
                                TaxiRequestObj.getInstance().getRequestId(), NetworkServices.PUT, headers,
                        params, new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                try {
                                    int code = jsonObject.getInt("Code");
                                    if (code == 0) {
                                        onFinish.Success();
                                    } else {
                                        error.setError(Error.INVALID_INPUTS);
                                        onFinish.Failure(error);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            }
                        });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                error.errorMessage = context.getString(R.string.please_check_your_network);
                onFinish.Failure(error);
            }
        } else {
            error.setError(Error.REQUEST_ID_NULL);
            error.errorMessage = context.getString(R.string.request_id_null);
            onFinish.Failure(error);
        }
    }

    public int getNumberSecondInDay() {
        int hour = TimeObject.getCurrentHour() * 3600;
        int minute = TimeObject.getCurrentMinute() * 60;
        int second = TimeObject.getCurrentSecond();
        return hour + minute + second;
    }

    public String getDriverPhoneNumber() {
        return TaxiRequestObj.getInstance().getResponseDriver().getDriver().getPhoneNumber();
    }
}
