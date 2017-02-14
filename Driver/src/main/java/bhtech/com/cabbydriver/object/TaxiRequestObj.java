package bhtech.com.cabbydriver.object;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import bhtech.com.cabbydriver.R;

public class TaxiRequestObj extends BaseObject {
    private int requestId = 0;
    private int status;
    private int carClass;
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
    public float price;
    public float extra_price;
    private float rate;
    private int pay_mode;
    private CardObj cardInfor;
    private String authToken;
    private double hours;

    private static TaxiRequestObj mInstance = null;
    static String utcTimeFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'";

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

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public float getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(float extra_price) {
        this.extra_price = extra_price;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                setRequestId(jsonObject.getInt("id"));
            } else {
                setRequestId(0);
            }

            if (jsonObject.has("status") && !jsonObject.isNull("status")) {
                setStatus(jsonObject.getInt("status"));
            }

            if (jsonObject.has("from_address") && !jsonObject.isNull("from_address")) {
                setFromLocationAddress(jsonObject.getString("from_address"));
                Double from_latitude = Double.valueOf(jsonObject.getString("from_latitude"));
                Double from_longitude = Double.valueOf(jsonObject.getString("from_longitude"));
                setFromLocation(new LatLng(from_latitude, from_longitude));
            }

            if (jsonObject.has("to_address") && !jsonObject.isNull("to_address")) {
                setToLocationAddress(jsonObject.getString("to_address"));
                Double to_latitude = Double.valueOf(jsonObject.getString("to_latitude"));
                Double to_longitude = Double.valueOf(jsonObject.getString("to_longitude"));
                setToLocation(new LatLng(to_latitude, to_longitude));
            }

            if (jsonObject.has("mileage") && !jsonObject.isNull("mileage")) {
                setEnd_mileage((float) jsonObject.getInt("mileage"));
            }
            if (jsonObject.has("price") && !jsonObject.isNull("price")) {
                setPrice((float) jsonObject.getInt("price"));
            }

            if (jsonObject.has("extra_price") && !jsonObject.isNull("extra_price")) {
                setExtra_price((float) jsonObject.getInt("extra_price"));
            }

            if (jsonObject.has("customers_id") && !jsonObject.isNull("customers_id")) {
                if (requestUser == null) {
                    UserObj userObj = new UserObj();
                    userObj.setObjectID(jsonObject.getInt("customers_id"));
                    setRequestUser(userObj);
                } else {
                    requestUser.setObjectID(jsonObject.getInt("customers_id"));
                }
                if (requestUser.getObjectID() == ContantValuesObject.CUSTOMER_NULL) {
                    setRequestUser(null);
                }
            }

            if (jsonObject.has("driver_car_id") && !jsonObject.isNull("driver_car_id")) {
                CarDriverObj.getInstance().setObjectID(jsonObject.getInt("driver_car_id"));
            }

            if (jsonObject.has("driver_car") && !jsonObject.isNull("driver_car")) {
                JSONObject driverCarJson = jsonObject.getJSONObject("driver_car");
                CarDriverObj.getInstance().setObjectID(driverCarJson.getInt("id"));
                CarDriverObj.getInstance().setStatus(driverCarJson.getInt("status"));
            }

            if (jsonObject.has("car") && !jsonObject.isNull("car")) {
                JSONObject carJson = jsonObject.getJSONObject("car");
                CarObj car = new CarObj();
                car.parseJsonToObject(carJson);
                CarDriverObj.getInstance().setCar(car);
            }

            if (jsonObject.has("user") && !jsonObject.isNull("user")) {
                try {
                    JSONObject userJson = jsonObject.getJSONObject("user");
                    UserObj userObj = new UserObj();
                    userObj.parseJsonToObject(userJson);
                    setRequestUser(userObj);
                } catch (Exception e) {

                }
            }

            if (!jsonObject.isNull("pickup_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("pickup_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setRequestPickupTime(calendar.getTime());
            } else {
                requestPickupTime = null;
            }

            if (!jsonObject.isNull("create_date")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("create_date"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setCreatedDate(calendar.getTime());
            }

            if (!jsonObject.isNull("update_date")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("update_date"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setUpdatedDate(calendar.getTime());
            }

            if (!jsonObject.isNull("start_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("start_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setStart_time(calendar.getTime());
            }

            if (!jsonObject.isNull("end_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("end_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setEnd_time(calendar.getTime());
            }

            if (!jsonObject.isNull("car_class")) {
                setCarClass(jsonObject.getInt("car_class"));
            }

            if (!jsonObject.isNull("hours")) {
                hours = jsonObject.getDouble("hours");
            }

            if (!jsonObject.isNull("payby")) {
                setPay_mode(jsonObject.getInt("payby"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public int getCarClass() {
        return carClass;
    }

    public void setCarClass(int carClass) {
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

    public int getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(int pay_mode) {
        this.pay_mode = pay_mode;
    }

    public CardObj getCardInfor() {
        return cardInfor;
    }

    public void setCardInfor(CardObj cardInfor) {
        this.cardInfor = cardInfor;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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

    public interface onGetRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getRequest(final Context context, final int requestId, final onGetRequest onFinish) {
        final ErrorObj error = new ErrorObj();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("requestid", Arrays.asList(String.valueOf(requestId)));

        NetworkObject.callAPI(context, ContantValuesObject.GET_CURRENT_REQUEST_ENDPOINT,
                NetworkObject.GET, "CurrentRequest", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                parseJsonToObject(results);
                                onFinish.Success();
                            } else {
                                error.errorMessage = context.getString(R.string.json_result_code_wrong);
                                onFinish.Failure(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.errorMessage = context.getString(R.string.have_exception_when_parse_json);
                            onFinish.Failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });

    }

    public interface onGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getCurrentRequest(final Context context, final onGetCurrentRequest onFinish) {
        final ErrorObj error = new ErrorObj();
        if (getRequestId() != 0) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(getAuthToken()));

            NetworkObject.callAPI(context, ContantValuesObject.GET_CURRENT_REQUEST_ENDPOINT,
                    NetworkObject.GET, "CurrentRequest", headers, null, new NetworkObject.onCallApi() {
                        @Override
                        public void Success(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                    JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    parseJsonToObject(results);
                                    if (getRequestId() != 0) {
                                        onFinish.Success();
                                    } else {
                                        error.setError(ErrorObj.REQUEST_ID_NULL);
                                        error.errorMessage = context.getString(R.string.request_id_null);
                                        onFinish.Failure(error);
                                    }
                                } else {
                                    error.errorMessage = context.getString(R.string.json_result_code_wrong);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.errorMessage = context.getString(R.string.have_exception_when_parse_json);
                                onFinish.Failure(error);
                            }
                        }

                        @Override
                        public void Failure(ErrorObj error) {
                            onFinish.Failure(error);
                        }
                    });
        } else {
            error.setError(ErrorObj.REQUEST_ID_NULL);
            error.errorMessage = context.getString(R.string.request_id_null);
            onFinish.Failure(error);
        }
    }

    // Driver action
    public void driverFindUserRequest() {
    }

    public void driverAcceptUserRequest() {

    }

    public interface DriverChangeStatusRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverChangeStatusRequest(Context context, int status, final DriverChangeStatusRequest onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(status)));

        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + requestId, NetworkObject.PUT,
                "DriverChangeStatusRequest", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface DriverChangePickUpLocation {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverChangePickUpLocation(final Context context, final DriverChangePickUpLocation onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("from_latitude", Arrays.asList(String.valueOf(fromLocation.latitude)));
        params.put("from_longitude", Arrays.asList(String.valueOf(fromLocation.longitude)));
        params.put("from_address", Arrays.asList(String.valueOf(fromLocationAddress)));

        final String request_id = String.valueOf(requestId);
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

    public interface DriverChangeDropOffLocation {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverChangeDropOffLocation(Context context, final DriverChangeDropOffLocation onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("to_latitude", Arrays.asList(String.valueOf(toLocation.latitude)));
        params.put("to_longitude", Arrays.asList(String.valueOf(toLocation.longitude)));
        params.put("to_address", Arrays.asList(String.valueOf(toLocationAddress)));

        final String request_id = String.valueOf(requestId);
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

    public interface DriverCreatePayment {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverCreatePayment(Context context, final DriverCreatePayment onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusPaid)));
        params.put("hours", Arrays.asList(String.valueOf(hours)));
        params.put("mileage", Arrays.asList(String.valueOf(end_mileage)));
        params.put("price", Arrays.asList(String.valueOf(price)));
        params.put("extra_price", Arrays.asList(String.valueOf(extra_price)));
        params.put("payby", Arrays.asList(String.valueOf(pay_mode)));

        final String request_id = String.valueOf(requestId);
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
}