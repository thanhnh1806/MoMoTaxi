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

import bhtech.com.cabbytaxi.Login.LoginInterface;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.services.NetworkServices;

public class UserObj extends BaseUserObject {
    private static UserObj mInstance = null;
    private UserCompanyObj company;
    private float rate;
    private String fbUserId, fbAccessToken, fbExpirationDate, fbRefreshDate;
    private String twToken, twTokenSecret;
    private String gUserId, gEmail;
    private String screenName;
    private String country;
    private int delete;

    public static UserObj getInstance() {
        if (mInstance == null) {
            mInstance = new UserObj();
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

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbExpirationDate() {
        return fbExpirationDate;
    }

    public void setFbExpirationDate(String fbExpirationDate) {
        this.fbExpirationDate = fbExpirationDate;
    }

    public String getFbRefreshDate() {
        return fbRefreshDate;
    }

    public void setFbRefreshDate(String fbRefreshDate) {
        this.fbRefreshDate = fbRefreshDate;
    }

    public String getTwToken() {
        return twToken;
    }

    public void setTwToken(String twToken) {
        this.twToken = twToken;
    }

    public String getTwTokenSecret() {
        return twTokenSecret;
    }

    public void setTwTokenSecret(String twTokenSecret) {
        this.twTokenSecret = twTokenSecret;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgUserId() {
        return gUserId;
    }

    public void setgUserId(String gUserId) {
        this.gUserId = gUserId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                setObjectID(jsonObject.getInt("id"));
            }

            if (jsonObject.has("username") && !jsonObject.isNull("username")) {
                setUsername(jsonObject.getString("username"));
            }

            if (jsonObject.has("phone_number") && !jsonObject.isNull("phone_number")) {
                setPhoneNumber(jsonObject.getString("phone_number"));
            }

            if (jsonObject.has("email") && !jsonObject.isNull("email")) {
                setEmail(jsonObject.getString("email"));
            }
            if (jsonObject.has("full_name") && !jsonObject.isNull("full_name")) {
                setFullName(jsonObject.getString("full_name"));
                setScreenName(jsonObject.getString("full_name"));
            }

            if (jsonObject.has("country") && !jsonObject.isNull("country")) {
                setCountry(jsonObject.getString("country"));
            }
            if (jsonObject.has("user_type") && !jsonObject.isNull("user_type")) {
                switch (jsonObject.getInt("user_type")) {
                    case ContantValuesObject.CardUserTypePersonal:
                        setCompany(null);
                        break;
                    case ContantValuesObject.CardUserTypeCompany:
                        UserCompanyObj userCompanyObj = new UserCompanyObj();
                        if (jsonObject.has("company_email") && !jsonObject.isNull("company_email")) {
                            userCompanyObj.setCompanyEmail(jsonObject.getString("company_email"));
                        }
                        if (jsonObject.has("company_name") && !jsonObject.isNull("company_name")) {
                            userCompanyObj.setCompanyName(jsonObject.getString("company_name"));
                        }
                        setCompany(userCompanyObj);
                        break;
                }
            }

            if (jsonObject.has("photo") && !jsonObject.isNull("photo")) {
                setPhoto(jsonObject.getString("photo"));
            } else {
                setPhoto(null);
            }
            if (jsonObject.has("authToken") && !jsonObject.isNull("authToken")) {
                setUser_token(jsonObject.getString("authToken"));
            }
            if (jsonObject.has("delete") && !jsonObject.isNull("delete")) {
                setDelete(jsonObject.getInt("delete"));
            }

            if (jsonObject.has("latitude") && jsonObject.has("longitude")) {
                if (!jsonObject.isNull("latitude") && !jsonObject.isNull("longitude")) {
                    Double lat = jsonObject.getDouble("latitude");
                    Double lng = jsonObject.getDouble("longitude");
                    setLocation(new LatLng(lat, lng));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", getUsername());
            jsonObject.put("type", getType());
            jsonObject.put("email", getEmail());
            jsonObject.put("address", getAddress());
            jsonObject.put("password", getPassword());
            jsonObject.put("phoneNumber", getPhoneNumber());
            jsonObject.put("birthday", getBirthday());
            jsonObject.put("photo", getPhoto());
            jsonObject.put("lat", getLocation().latitude);
            jsonObject.put("lng", getLocation().longitude);
            jsonObject.put("user_token", getUser_token());
            jsonObject.put("company", getCompany().parseObjectToJson());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public UserCompanyObj getCompany() {
        return company;
    }

    public void setCompany(UserCompanyObj company) {
        this.company = company;
    }


    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public interface OnCancelFutureBooking {
        void success();

        void failure(Error error);
    }

    public void cancelFutureBooking(Context context, int requestId, final OnCancelFutureBooking onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Map<String, List<String>> params = new HashMap<>();
            params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusCancelled)));

            NetworkServices.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH +
                            requestId, NetworkServices.PUT, headers,
                    params, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                int code = jsonObject.getInt("Code");
                                if (code == 0) {
                                    onFinish.success();
                                } else {
                                    error.setError(Error.INVALID_INPUTS);
                                    onFinish.failure(error);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.failure(error);
        }
    }

    public interface OnRegisterCompany {
        void success();

        void failure(Error error);
    }

    public void registerCompany(Context context, final OnRegisterCompany onFinish) {
        final Error error = new Error();
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("username", Arrays.asList(getCompany().getCompanyUserName()));
        parameters.put("company_name", Arrays.asList(getCompany().getCompanyName()));
        parameters.put("full_name", Arrays.asList(getCompany().getFullName()));
        parameters.put("user_type", Arrays.asList(String.valueOf(1)));
        parameters.put("password", Arrays.asList(getCompany().getCompanyPassword()));
        parameters.put("phone_number", Arrays.asList(getCompany().getCompanyPhone()));
        parameters.put("email", Arrays.asList(getCompany().getCompanyEmail()));
        parameters.put("country", Arrays.asList(getCompany().getCountry()));
        parameters.put("company_email", Arrays.asList(getCompany().getCompanyUsageDetail()));

        NetworkServices.callAPI(context, ContantValuesObject.REGISTER_COMPANY_ENDPOINT,
                NetworkServices.POST, null, parameters, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (jsonObject != null) {
                            try {
                                int code = jsonObject.getInt("Code");
                                if (code == 0) {
                                    JSONObject results = jsonObject.getJSONObject("Results");
                                    UserObj.getInstance().getCompany().parseJsonToObject(results);
                                    UserObj.getInstance().setUsername(getCompany().getCompanyUserName());
                                    UserObj.getInstance().setPassword(getCompany().getCompanyPassword());
                                    onFinish.success();
                                } else {
                                    error.errorMessage = jsonObject.getString("Message");
                                    onFinish.failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                onFinish.failure(error);
                            }
                        } else {
                            onFinish.failure(error);
                        }
                    }
                });
    }

    public interface OnRegister {
        void success();

        void failure(Error error);
    }

    public void register(final Context context, final OnRegister register) {
        final Error error = new Error();
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("username", Arrays.asList(getUsername()));
        parameters.put("password", Arrays.asList(getPassword()));
        parameters.put("email", Arrays.asList(getEmail()));
        parameters.put("phone_number", Arrays.asList(getPhoneNumber()));
        parameters.put("country", Arrays.asList(getCountry()));

        NetworkServices.callAPI(context, ContantValuesObject.REGISTER_ENDPOINT,
                NetworkServices.POST, null, parameters, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (jsonObject != null) {
                            try {
                                int code = jsonObject.getInt("Code");
                                if (code == 0) {
                                    JSONObject results = jsonObject.getJSONObject("Results");
                                    UserObj.getInstance().parseJsonToObject(results);
                                    register.success();
                                } else if (code == Error.USERNAME_EXISTED) {
                                    error.errorMessage = context.getString(R.string.username_existed);
                                    register.failure(error);
                                } else {
                                    error.errorMessage = jsonObject.getString("Message");
                                    register.failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                register.failure(error);
                            }
                        } else {
                            register.failure(error);
                        }
                    }
                });
    }


    public void login(final Context context, final LoginInterface.OnFinishExecute onFinishExecute) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("username", Arrays.asList(getUsername()));
        parameters.put("password", Arrays.asList(getPassword()));

        NetworkServices.callAPI(context, ContantValuesObject.LOGIN_ENDPOINT, NetworkServices.POST,
                headers, parameters, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                int i = jsonObject.getInt("Code");
                                if (i == 0) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.USERNAME,
                                            UserObj.getInstance().getUsername());
                                    SharedPreference.set(context, ContantValuesObject.PASSWORD,
                                            UserObj.getInstance().getPassword());
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id") && !jsonObjectResults.isNull("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    }
                                    onFinishExecute.onFinish(true);
                                } else {
                                    onFinishExecute.onFinish(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                onFinishExecute.onFinish(false);
                            }
                        }
                    }
                }
        );
    }

    public interface OnReLogin {
        void Success();

        void Failure(Error error);
    }

    public void reLogin(final Context context, final OnReLogin onFinish) {
        final Error error = new Error();
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));
        headers.put("authToken", Arrays.asList(getUser_token()));

        NetworkServices.callAPI(context, ContantValuesObject.RE_LOGIN,
                NetworkServices.POST, headers, null, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                int i = jsonObject.getInt("Code");
                                if (i == 0) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    } else {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                ContantValuesObject.REQUEST_ID_NULL);
                                    }
                                    onFinish.Success();
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    }
                }
        );
    }

    public interface OnLoginFacebook {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginFacebook(final Context context, final OnLoginFacebook onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("tokenString", Arrays.asList(fbAccessToken));

        Log.w("Device-Token", deviceToken);
        Log.w("tokenString", fbAccessToken);
        NetworkServices.callAPI(context, ContantValuesObject.LOGIN_FACEBOOK_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    } else {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                ContantValuesObject.REQUEST_ID_NULL);
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface OnSettingFacebook {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingFacebook(final Context context, final OnSettingFacebook onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));
        headers.put("authToken", Arrays.asList(getUser_token()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("tokenString", Arrays.asList(fbAccessToken));

        Log.w("Device-Token", deviceToken);
        Log.w("tokenString", fbAccessToken);
        NetworkServices.callAPI(context, ContantValuesObject.SETTING_FACEBOOK_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface OnLoginTwitter {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginTwitter(final Context context, final OnLoginTwitter onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("authToken", Arrays.asList(twToken));
        params.put("authTokenSecret", Arrays.asList(twTokenSecret));

        Log.w("Device-Token", deviceToken);
        Log.w("TwitterToken", twToken);
        Log.w("TwitterTokenSecret", twTokenSecret);
        NetworkServices.callAPI(context, ContantValuesObject.LOGIN_TWITTER_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    } else {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                ContantValuesObject.REQUEST_ID_NULL);
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface OnSettingTwitter {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingTwitter(final Context context, final OnSettingTwitter onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));
        headers.put("authToken", Arrays.asList(getUser_token()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("authToken", Arrays.asList(twToken));
        params.put("authTokenSecret", Arrays.asList(twTokenSecret));

        Log.w("Device-Token", deviceToken);
        Log.w("TwitterToken", twToken);
        Log.w("TwitterTokenSecret", twTokenSecret);
        NetworkServices.callAPI(context, ContantValuesObject.SETTING_TWITTER_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface OnLoginGoogle {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginGoogle(final Context context, final OnLoginGoogle onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("userID", Arrays.asList(gUserId));
        params.put("email", Arrays.asList(gEmail));

        Log.w("Device-Token", deviceToken);
        Log.w("GoogleUserId", gUserId);
        Log.w("GoogleEmail", gEmail);
        NetworkServices.callAPI(context, ContantValuesObject.LOGIN_GOOGLE_PLUS_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    } else {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                ContantValuesObject.REQUEST_ID_NULL);
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface OnSettingGoogle {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingGoogle(final Context context, final OnSettingGoogle onFinish) {
        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));
        headers.put("authToken", Arrays.asList(getUser_token()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("userID", Arrays.asList(gUserId));
        params.put("email", Arrays.asList(gEmail));

        Log.w("Device-Token", deviceToken);
        Log.w("GoogleUserId", gUserId);
        Log.w("GoogleEmail", gEmail);
        NetworkServices.callAPI(context, ContantValuesObject.SETTING_GOOGLE_PLUS_ENDPOINT,
                NetworkServices.PUT, headers, params, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONObject jsonObjectResults = jsonObject.getJSONObject("Results");
                                    parseJsonToObject(jsonObjectResults);
                                    SharedPreference.set(context, ContantValuesObject.AUTHTOKEN,
                                            UserObj.getInstance().getUser_token());
                                    if (jsonObjectResults.has("request_id")) {
                                        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID,
                                                String.valueOf(jsonObjectResults.getInt("request_id")));
                                    }
                                    onFinish.Success();
                                } else {
                                    //OnFinish.Failure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface onUpdateProfile {
        void success();

        void failure(Error error);
    }

    public void updateProfile(Context context, boolean userChangePassword, final onUpdateProfile finish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("username", Arrays.asList(getUsername()));
        parameters.put("phone_number", Arrays.asList(getPhoneNumber()));
        parameters.put("email", Arrays.asList(getEmail()));
        if (userChangePassword) {
            parameters.put("password", Arrays.asList(getPassword()));
        }
        parameters.put("full_name", Arrays.asList(getScreenName()));
        parameters.put("country", Arrays.asList(getCountry()));
        parameters.put("delete", Arrays.asList(String.valueOf(getDelete())));

        NetworkServices.callAPI(context, ContantValuesObject.UPDATE_PROFILE_ENDPOINT +
                        UserObj.getInstance().getObjectID(),
                NetworkServices.PUT, headers, parameters, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            finish.success();
                        } else {
                            //finish.failure();
                        }
                    }
                });
    }

    public interface onUserLogOut {
        void success();

        void failure(Error error);
    }

    public void logout(final Context context, final onUserLogOut finish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

        NetworkServices.callAPI(context, ContantValuesObject.LOGOUT,
                NetworkServices.PUT, headers, null, new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            SharedPreference.set(context, ContantValuesObject.USERNAME, "");
                            SharedPreference.set(context, ContantValuesObject.PASSWORD, "");
                            SharedPreference.set(context, ContantValuesObject.AUTHTOKEN, "");
                            finish.success();
                        } else {
                            //finish.failure();
                        }
                    }
                });
    }

    public interface OnUploadAvatar {
        void Success();

        void Failure(Error error);
    }

    public void uploadAvatar(final Context context, final onUserLogOut finish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
    }
}
