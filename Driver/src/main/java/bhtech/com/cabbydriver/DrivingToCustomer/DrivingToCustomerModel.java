package bhtech.com.cabbydriver.DrivingToCustomer;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 13/04/2016.
 */
public class DrivingToCustomerModel implements DrivingToCustomerInterface.Datasource {
    Context context;

    public DrivingToCustomerModel(Context context) {
        this.context = context;
    }

    @Override
    public String getCustomerName() {
        try {
            return TaxiRequestObj.getInstance().getRequestUser().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getStartAddress() {
        return TaxiRequestObj.getInstance().getFromLocationAddress();
    }

    public String getCustomerPhoneNumber() {
        try {
            return TaxiRequestObj.getInstance().getRequestUser().getPhoneNumber();
        } catch (Exception e) {
            return "";
        }
    }

    public interface DriverPickUpCustomer {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverPickUpCustomer(final DriverPickUpCustomer onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(
                ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger)));

        final String request_id = String.valueOf(TaxiRequestObj.getInstance().getRequestId());
        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + request_id,
                NetworkObject.PUT, "DriverChooseCustomer", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                onFinish.Success();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface StartGoToDestination {
        void Success();

        void Failure(ErrorObj error);
    }

    public void startGoToDestination(final StartGoToDestination onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(
                ContantValuesObject.TaxiRequestStatusWithPassenger)));

        final String request_id = String.valueOf(TaxiRequestObj.getInstance().getRequestId());
        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + request_id, NetworkObject.PUT,
                "DriverChooseCustomer", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                onFinish.Success();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface OnGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getCurrentRequest(final OnGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }
}
