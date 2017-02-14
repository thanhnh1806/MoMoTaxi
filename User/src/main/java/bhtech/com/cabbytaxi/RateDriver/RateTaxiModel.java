package bhtech.com.cabbytaxi.RateDriver;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen02 on 06/01/2016.
 */
public class RateTaxiModel implements RateTaxiInterface.Datasource {
    int rate = 1;
    private ArrayList<CarDriverObj> listCarDriver;

    public int getRate() {
        return rate;
    }

    public interface onRateTaxi {
        void Success();

        void Failure(Error error);
    }

    public void rateTaxi(Context context, final onRateTaxi onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {

            Map<String, List<String>> header = new HashMap<>();
            header.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Log.d("RateTaxi", String.valueOf(rate));
            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("rate", Arrays.asList(String.valueOf(getRate())));

            NetworkServices.callAPI(context, ContantValuesObject.RATE_TAXI_ENDPOINT
                            + TaxiRequestObj.getInstance().getRequestId(),
                    NetworkServices.PUT, header, parameters, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                switch (jsonObject.getInt("Code")) {
                                    case Error.NO_ERROR:
                                        onFinish.Success();
                                        break;
                                    case Error.ERROR_AUTHTOKEN:
                                        error.setError(Error.ERROR_AUTHTOKEN);
                                        error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                        onFinish.Failure(error);
                                        break;
                                    default:
                                        error.setError(Error.UNKNOWN_ERROR);
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
            onFinish.Failure(error);
        }
    }

    @Override
    public String getDriverName() {
        try {
            return TaxiRequestObj.getInstance().getResponseDriver().getDriver().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getCarNumber() {
        try {
            return TaxiRequestObj.getInstance().getResponseDriver().getCar().getNumber();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void setRateDriver(int rateDriver) {
        rate = rateDriver;
    }

    @Override
    public String getUserFacebookName() {
        try {
            return UserObj.getInstance().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public int getDriverId() {
        try {
            return TaxiRequestObj.getInstance().getResponseDriver().getDriver().getObjectID();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String getauthToken() {
        try {
            return UserObj.getInstance().getUser_token();
        } catch (Exception e) {
            return "";
        }
    }

    public interface OnAddFavouriteDriver {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void addFavouriteDriver(Context context, final OnAddFavouriteDriver onFinish) {
        final Error error = new Error();
        String token = UserObj.getInstance().getUser_token();

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("driver_id", Arrays.asList(String.valueOf(getDriverId())));

        Map<String, List<String>> header = new HashMap<>();
        header.put("authToken", Arrays.asList(token));
        header.put("User-Agent", Arrays.asList("Android"));

        NetworkServices.callAPI(context, ContantValuesObject.ADD_FAVOURITE_DRIVER_ENDPOINT,
                NetworkServices.POST, header, parameters,
                new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        try {

                            switch (jsonObject.getInt(ContantValuesObject.CODE)) {
                                case Error.NO_ERROR:
                                    onFinish.Success();
                                    break;
                                case Error.FAVOURITE_DRIVER_ALREADY_EXISTED:
                                    error.setError(Error.FAVOURITE_DRIVER_ALREADY_EXISTED);
                                    error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                    onFinish.Failure(error);
                                    break;
                                default:
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.setError(Error.UNKNOWN_ERROR);
                            onFinish.Failure(error);
                        }
                    }
                });
    }
}
