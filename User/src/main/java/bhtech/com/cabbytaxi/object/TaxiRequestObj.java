package bhtech.com.cabbytaxi.object;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.services.NetworkServices;

public class TaxiRequestObj extends BaseObject {
    private int requestId = -1;
    private int status;
    private BaseEnums.CarClass carClass;
    private UserObj requestUser;
    private CarDriverObj responseDriver;
    private String from_name;
    private String fromLocationAddress;
    private LatLng fromLocation;
    private String toName;
    private LatLng toLocation;
    private String toLocationAddress;
    private Date requestPickupTime;
    private Date start_time;
    private Date end_time;
    private float start_mileage;
    private float end_mileage;
    private float price;
    private float extra_price;
    private float rate;
    private float est_time;
    private float est_mileage;
    private float est_price;
    private BaseEnums.PaymentMode pay_mode;
    private CardObj cardInfor;
    static String utcTimeFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    private static TaxiRequestObj mInstance = null;

    public static TaxiRequestObj getInstance() {
        if (mInstance == null) {
            mInstance = new TaxiRequestObj();
        }
        return mInstance;
    }

    @Override
    public int getObjectID() {
        return super.getObjectID();
    }

    @Override
    public void setObjectID(int objectID) {
        super.setObjectID(objectID);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void setEmpty(boolean empty) {
        super.setEmpty(empty);
    }

    @Override
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public Date getUpdatedDate() {
        return super.getUpdatedDate();
    }

    public float getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(float extra_price) {
        this.extra_price = extra_price;
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    public float getEst_mileage() {
        return est_mileage;
    }

    public void setEst_mileage(float est_mileage) {
        this.est_mileage = est_mileage;
    }

    public float getEst_price() {
        return est_price;
    }

    public void setEst_price(float est_price) {
        this.est_price = est_price;
    }

    public float getEst_time() {
        return est_time;
    }

    public void setEst_time(float est_time) {
        this.est_time = est_time;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                setRequestId(jsonObject.getInt("id"));
            } else {
                Log.w("Status", "Dont have id in json");
            }
            if (jsonObject.has("status")) {
                setStatus(jsonObject.getInt("status"));
            } else {
                setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
            }

            if (!jsonObject.isNull("pickup_time")) {
                try {
                    setRequestPickupTime(parseDate(jsonObject.getString("pickup_time"), utcTimeFormat));
                } catch (Exception e) {
                    requestPickupTime = null;
                }
            } else {
                requestPickupTime = null;
            }

            if (jsonObject.has("price")) {
                setPrice(jsonObject.getInt("price"));
            }

            if (jsonObject.has("extra_price")) {
                setPrice(jsonObject.getInt("extra_price"));
            }

            if (jsonObject.has("from_address")) {
                setFromLocationAddress(jsonObject.getString("from_address"));
            }

            if (jsonObject.has("from_latitude") && (jsonObject.has("from_longitude"))) {
                setFromLocation(new LatLng(jsonObject.getDouble("from_latitude"),
                        jsonObject.getDouble("from_longitude")));
            }

            if (jsonObject.has("to_latitude") && (jsonObject.has("to_longitude"))) {
                setToLocation(new LatLng(jsonObject.getDouble("to_latitude"), jsonObject.getDouble("to_longitude")));
            }

            if (jsonObject.has("to_address")) {
                setToLocationAddress(jsonObject.getString("to_address"));
            }

            if (jsonObject.has("est_time") && !jsonObject.isNull("est_time")) {
                setEst_time((float) jsonObject.getDouble("est_time"));
            }

            if (jsonObject.has("est_mileage") && !jsonObject.isNull("est_mileage")) {
                setEst_mileage((float) jsonObject.getDouble("est_mileage"));
            }

            if (jsonObject.has("est_price") && !jsonObject.isNull("est_price")) {
                setEst_price((float) jsonObject.getDouble("est_price"));
            }

            if (jsonObject.has("user")) {
                try {
                    UserObj userObj = new UserObj();
                    userObj.parseJsonToObject(jsonObject.getJSONObject("user"));
                    setRequestUser(userObj);
                } catch (Exception e) {
                    Log.e("User", "Null");
                }
            }

            if (getStatus() > 1) {
                CarDriverObj carDriverObj = new CarDriverObj();
                DriverObj driverObj = new DriverObj();
                if (jsonObject.has("driver")) {
                    JSONObject driverJsonObject = jsonObject.getJSONObject("driver");
                    if (driverJsonObject.has("id") && !driverJsonObject.isNull("id")) {
                        driverObj.setObjectID(driverJsonObject.getInt("id"));
                    }
                    if (driverJsonObject.has("username") && !driverJsonObject.isNull("username")) {
                        driverObj.setUsername(driverJsonObject.getString("username"));
                    }
                    if (driverJsonObject.has("email") && !driverJsonObject.isNull("email")) {
                        driverObj.setEmail(driverJsonObject.getString("email"));
                    }
                    if (driverJsonObject.has("photo") && !driverJsonObject.isNull("photo")) {
                        driverObj.setPhoto(driverJsonObject.getString("photo"));
                    }
                    if (driverJsonObject.has("phone_number") && !driverJsonObject.isNull("phone_number")) {
                        driverObj.setPhoneNumber(driverJsonObject.getString("phone_number"));
                    }
                    if (driverJsonObject.has("latitude") && driverJsonObject.has("longitude")) {
                        try {
                            carDriverObj.setLocation(new LatLng(Double.parseDouble(driverJsonObject.getString("latitude")),
                                    Double.parseDouble(driverJsonObject.getString("longitude"))));
                        } catch (Exception e) {
                            Log.e("Driver_Location", "Null");
                        }
                    }
                }

                CarObj carObj = new CarObj();
                if (jsonObject.has("car")) {
                    JSONObject carJsonObject = jsonObject.getJSONObject("car");
                    if (carJsonObject.has("id")) {
                        carObj.setObjectID(carJsonObject.getInt("id"));
                    }
                    if (carJsonObject.has("number")) {
                        carObj.setNumber(carJsonObject.getString("number"));
                    }
                }

                carDriverObj.setCar(carObj);
                carDriverObj.setDriver(driverObj);
                if (jsonObject.has("driver_car_id")) {
                    carDriverObj.setObjectID(jsonObject.getInt("driver_car_id"));
                }

                setResponseDriver(carDriverObj);
            }
        } catch (Exception e) {
            try {
                throw new Exception(e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestId", getRequestId());
            jsonObject.put("status", getStatus());
            jsonObject.put("carClass", getCarClass());
            jsonObject.put("requestUser", getRequestUser().parseObjectToJson());
            jsonObject.put("responseDriver", getResponseDriver().parseObjectToJson());
            jsonObject.put("from_name", getFrom_name());
            jsonObject.put("fromLocationAddress", getFromLocationAddress());
            jsonObject.put("from_lat", getFromLocation().latitude);
            jsonObject.put("from_lng", getFromLocation().longitude);
            jsonObject.put("toName", getToName());
            jsonObject.put("to_lat", getToLocation().latitude);
            jsonObject.put("to_lng", getToLocation().longitude);
            jsonObject.put("toLocationAddress", getToLocationAddress());
            jsonObject.put("requestPickupTime", getRequestPickupTime());
            jsonObject.put("start_time", getStart_time());
            jsonObject.put("end_time", getEnd_time());
            jsonObject.put("start_mileage", getStart_mileage());
            jsonObject.put("end_mileage", getEnd_mileage());
            jsonObject.put("price", getPrice());
            jsonObject.put("rate", getRate());
            jsonObject.put("pay_mode", getRate());
            jsonObject.put("cardInfor", getCardInfor().parseObjectToJson());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseEnums.CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(BaseEnums.CarClass carClass) {
        this.carClass = carClass;
    }

    public UserObj getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(UserObj requestUser) {
        this.requestUser = requestUser;
    }

    public CarDriverObj getResponseDriver() {
        return responseDriver;
    }

    public void setResponseDriver(CarDriverObj responseDriver) {
        this.responseDriver = responseDriver;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFromLocationAddress() {
        return fromLocationAddress;
    }

    public void setFromLocationAddress(String fromLocationAddress) {
        this.fromLocationAddress = fromLocationAddress;
    }

    public LatLng getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(LatLng fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public LatLng getToLocation() {
        return toLocation;
    }

    public void setToLocation(LatLng toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocationAddress() {
        return toLocationAddress;
    }

    public void setToLocationAddress(String toLocationAddress) {
        this.toLocationAddress = toLocationAddress;
    }

    public Date getRequestPickupTime() {
        return requestPickupTime;
    }

    public void setRequestPickupTime(Date requestPickupTime) {
        this.requestPickupTime = requestPickupTime;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public float getStart_mileage() {
        return start_mileage;
    }

    public void setStart_mileage(float start_mileage) {
        this.start_mileage = start_mileage;
    }

    public float getEnd_mileage() {
        return end_mileage;
    }

    public void setEnd_mileage(float end_mileage) {
        this.end_mileage = end_mileage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public BaseEnums.PaymentMode getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(BaseEnums.PaymentMode pay_mode) {
        this.pay_mode = pay_mode;
    }

    public CardObj getCardInfor() {
        return cardInfor;
    }

    public void setCardInfor(CardObj cardInfor) {
        this.cardInfor = cardInfor;
    }

    public interface onGetCurrentRequest {
        void Success();

        void Failure(Error error);
    }

    public void getCurrentRequest(Context context, final onGetCurrentRequest onFinish) {
        final Error error = new Error();
        if (getRequestId() > 0) {
            if (NetworkObject.isNetworkConnect(context)) {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

                Log.w("RequestAuthToken", UserObj.getInstance().getUser_token());
                Log.w("RequestId", String.valueOf(getRequestId()));

                NetworkServices.callAPI(context, ContantValuesObject.GET_CURRENT_REQUEST_ENDPOINT,
                        NetworkServices.GET, headers, null, new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getInt(ContantValuesObject.CODE) == Error.NO_ERROR) {
                                        JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                        parseJsonToObject(results);
                                        onFinish.Success();
                                    } else {
                                        error.setError(Error.UNKNOWN_ERROR);
                                        error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                        onFinish.Failure(error);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR);
                                    error.errorMessage = e.toString();
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

    // User action

    public void userCreateRequest() {
    }

    public void userConfirmDriverAcceptRequest() {
    }

    public void userAcceptPayment() {
    }

    public void userGetListReceipt() {
    }

    public void userDeleteReceipt() {
    }

    public void userVoteForDriver() {
    }


    // Driver action
    public void driverFindUserRequest() {
    }

    public void driverAcceptUserRequest() {
    }

    public void driverChangePickUpLocation() {
    }

    public void driverChangeStatusDriving() {
    }

    public void driverCreatePayment() {
    }
}
