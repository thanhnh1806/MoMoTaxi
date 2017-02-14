package bhtech.com.cabbydriver.object;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.BaseModelInterface;

public class DriverObj extends BaseUserObject {
    private String pin_code;
    private float rate;
    private DriverCompanyObj driver_company;
    private ArrayList<WorkingResultObj> working_results;
    private int first_login;
    // glasses // experience // hometown // violation_career // maximum_work_time


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

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pin_code", getPin_code());
            jsonObject.put("rate", getRate());
            jsonObject.put("driver_company", getDriver_company().parseObjectToJson());
            ArrayList<WorkingResultObj> workingResultObjs = getWorking_results();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < workingResultObjs.size(); i++) {
                JSONObject jsonObject1 = workingResultObjs.get(i).parseObjectToJson();
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("working_results", jsonArray);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            setPin_code(jsonObject.getString("pin_code"));
            setRate((float) jsonObject.getDouble("rate"));
            DriverCompanyObj driverCompanyObj = new DriverCompanyObj();
            driverCompanyObj.parseJsonToObject(jsonObject.getJSONObject("driver_company"));

            //  Save company profile to Singleton
            DriverCompanyObj.getInstance().parseJsonToObject(jsonObject.getJSONObject("driver_company"));

            setDriver_company(driverCompanyObj);
            ArrayList<WorkingResultObj> workingResultObjs = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("working_results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                WorkingResultObj workingResultObj = new WorkingResultObj();
                workingResultObj.parseJsonToObject(jsonObject1);
                workingResultObjs.add(workingResultObj);
            }
            setWorking_results(workingResultObjs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public void setType(BaseEnums.UserType type) {
        super.setType(type);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public void setBirthday(Date birthday) {
        super.setBirthday(birthday);
    }

    @Override
    public void setPhoto(String photo) {
        super.setPhoto(photo);
    }

    @Override
    public void setLocation(LatLng location) {
        super.setLocation(location);
    }

    @Override
    public void setUser_token(String user_token) {
        super.setUser_token(user_token);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public BaseEnums.UserType getType() {
        return super.getType();
    }

    @Override
    public String getUser_token() {
        return super.getUser_token();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public Date getBirthday() {
        return super.getBirthday();
    }

    @Override
    public String getPhoto() {
        return super.getPhoto();
    }

    @Override
    public LatLng getLocation() {
        return super.getLocation();
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public DriverCompanyObj getDriver_company() {
        return driver_company;
    }

    public void setDriver_company(DriverCompanyObj driver_company) {
        this.driver_company = driver_company;
    }

    public ArrayList<WorkingResultObj> getWorking_results() {
        return working_results;
    }

    public void setWorking_results(ArrayList<WorkingResultObj> working_results) {
        this.working_results = working_results;
    }

    public int getFirst_login() {
        return first_login;
    }

    public void setFirst_login(int first_login) {
        this.first_login = first_login;
    }

    public void getProfile() {
    }

    public void updateProfile() {
    }

    public interface OnGetTripHistoryFinish extends BaseModelInterface {
        void success(JSONArray jsonArray);
    }

    public interface OnGetAlertListFinish extends BaseModelInterface {
        void success(JSONArray jsonArray);
    }

    public interface OnGetFutureBookingFinish extends BaseModelInterface {
        void success(JSONArray jsonArray);
    }

    public void getTripHistory(Context context, String fromDate, String toDate, final OnGetTripHistoryFinish onFinish) {
        final ErrorObj error = new ErrorObj();

        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));
        headers.put("User-Agent", Arrays.asList(ContantValuesObject.USER_AGENT));

        Map<String, List<String>> params = new HashMap<>();
        params.put("start_date", Arrays.asList(fromDate));
        params.put("end_date", Arrays.asList(toDate));

        String URLString = ContantValuesObject.GET_DRIVER_TRIP_HISTORY_ENDPOINT;

        NetworkObject.callAPI(context, URLString,
                NetworkObject.POST, "Trip History",
                headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        if (!jsonObject.isNull(ContantValuesObject.RESULTS)) {
                            try {
                                onFinish.success(jsonObject.getJSONArray(ContantValuesObject.RESULTS));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.errorMessage = e.getLocalizedMessage();
                                error.errorCode = e.hashCode();
                                onFinish.failure(error);
                            }
                        } else {
                            error.setError(1);
                            onFinish.failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj errorObj) {
                        onFinish.failure(error);
                    }
                });
    }

    public void getAlertList(Context context, String fromDate, String toDate, final OnGetAlertListFinish onFinish) {
        final ErrorObj errorObj = new ErrorObj();

        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));
        headers.put("User-Agent", Arrays.asList(ContantValuesObject.USER_AGENT));

        Map<String, List<String>> params = new HashMap<>();
        params.put("start_date", Arrays.asList(fromDate));
        params.put("end_date", Arrays.asList(toDate));

        NetworkObject.callAPI(context, ContantValuesObject.GET_ALERT_LIST_ENDPOINT,
                NetworkObject.POST, "Get Alerts",
                headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        if (jsonObject.has(ContantValuesObject.RESULTS) && !jsonObject.isNull(ContantValuesObject.RESULTS)) {
                            try {
                                onFinish.success(jsonObject.getJSONArray(ContantValuesObject.RESULTS));
                            } catch (JSONException e) {
                                errorObj.errorMessage = e.getLocalizedMessage();
                                errorObj.errorCode = e.hashCode();
                                onFinish.failure(errorObj);
                            }
                        } else {
                            errorObj.setError(1);
                            onFinish.failure(errorObj);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj errorObj) {
                        onFinish.failure(errorObj);
                    }
                });
    }

    public void getFutureBookings(Context context, String fromDate, String toDate, final OnGetFutureBookingFinish onFinish) {
        final ErrorObj errorObj = new ErrorObj();

        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));
        headers.put("User-Agent", Arrays.asList(ContantValuesObject.USER_AGENT));

        Map<String, List<String>> params = new HashMap<>();
        params.put("start_date", Arrays.asList(fromDate));
        params.put("end_date", Arrays.asList(toDate));

        NetworkObject.callAPI(context, ContantValuesObject.GET_FUTURE_BOOKING_ENDPOINT,
                NetworkObject.POST, "Get Future Bookings",
                headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        if (jsonObject.has(ContantValuesObject.RESULTS) && !jsonObject.isNull(ContantValuesObject.RESULTS)) {
                            try {
                                onFinish.success(jsonObject.getJSONArray(ContantValuesObject.RESULTS));
                            } catch (JSONException e) {
                                errorObj.errorMessage = e.getLocalizedMessage();
                                errorObj.errorCode = e.hashCode();
                                onFinish.failure(errorObj);
                            }
                        } else {
                            errorObj.setError(1);
                            onFinish.failure(errorObj);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj errorObj) {
                        onFinish.failure(errorObj);
                    }
                });
    }
}
