package bhtech.com.cabbytaxi.MyProfile;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.EmailObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 15/02/2016.
 */
public class MyProfileModel implements MyProfileInterface.Database {
    private Context context;
    private String screenName, phoneNumber, email, country, username, password;
    private boolean userChangePassword = false;

    public MyProfileModel(Context context) {
        this.context = context;
    }

    @Override
    public void setScreenName(String s) {
        this.screenName = s;
    }

    @Override
    public void setPhoneNumber(String s) {
        this.phoneNumber = s;
    }

    @Override
    public void setEmail(String s) {
        this.email = s;
    }

    @Override
    public void setCountry(String s) {
        this.country = s;
    }

    @Override
    public void setUsername(String s) {
        this.username = s;
    }

    @Override
    public void setPassword(String s) {
        this.password = s;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getUserPhoto() {
        return UserObj.getInstance().getPhoto();
    }

    @Override
    public void userChangePassword(boolean b) {
        userChangePassword = b;
    }

    public int checkAllValidate(Context context) {
        if (checkNotNull(context)) {
            if (EmailObject.validateEmail(email)) {
                return Error.NO_ERROR;
            } else {
                return Error.EMAIL_WRONG_FORMAT;
            }
        } else {
            return Error.INVALID_INPUTS;
        }
    }

    private boolean checkNotNull(Context context) {
        if (StringObject.isNullOrEmpty(getScreenName())) {
            Toast.makeText(context, R.string.please_input_your_name, Toast.LENGTH_SHORT).show();
        } else if (StringObject.isNullOrEmpty(getPhoneNumber())) {
            Toast.makeText(context, R.string.please_input_your_phone_number, Toast.LENGTH_SHORT).show();
        } else if (StringObject.isNullOrEmpty(getEmail())) {
            Toast.makeText(context, R.string.please_input_your_email, Toast.LENGTH_SHORT).show();
        } else if (StringObject.isNullOrEmpty(getPassword())) {
            if (userChangePassword) {
                Toast.makeText(context, R.string.please_input_your_password, Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public interface OnCheckCurrentRequest {
        void success(boolean hasCurrentRequest);

        void failure(Error error);
    }

    public void hasCurrentRequest(Context context, final OnCheckCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                if (TaxiRequestObj.getInstance().getRequestId() > 0) {
                    onFinish.success(true);
                } else {
                    onFinish.success(false);
                }
            }

            @Override
            public void Failure(Error error) {
                onFinish.failure(error);
            }
        });
    }

    public interface OnUserLogOut {
        void success();

        void failure(Error error);
    }

    public void userLogOut(final Context context, final OnUserLogOut onFinish) {
        UserObj.getInstance().logout(context, new UserObj.onUserLogOut() {
            @Override
            public void success() {
                onFinish.success();
            }

            @Override
            public void failure(Error error) {
                onFinish.failure(error);
            }
        });
    }

    public void getUserInformation() {
        setUsername(UserObj.getInstance().getUsername());
        if (UserObj.getInstance().getScreenName() != null) {
            setScreenName(UserObj.getInstance().getScreenName());
        } else {
            setScreenName("");
        }
        if (UserObj.getInstance().getCountry() == null) {
            setCountry("");
        } else {
            setCountry(UserObj.getInstance().getCountry());
        }
        setPassword(UserObj.getInstance().getPassword());
        setEmail(UserObj.getInstance().getEmail());
        setPhoneNumber(UserObj.getInstance().getPhoneNumber());
    }

    public interface onUpdateProfile {
        void success();

        void failure(Error error);
    }

    public void updateProfile(Context context, final onUpdateProfile onFinish) {
        Error error = new Error();
        error.setError(checkAllValidate(context));
        if (error.errorCode == Error.NO_ERROR) {
            if (NetworkObject.isNetworkConnect(context)) {
                UserObj.getInstance().setUsername(username);
                UserObj.getInstance().setScreenName(screenName);
                UserObj.getInstance().setPhoneNumber(phoneNumber);
                UserObj.getInstance().setEmail(email);
                UserObj.getInstance().setPassword(password);
                UserObj.getInstance().setCountry(country);

                UserObj.getInstance().updateProfile(context, userChangePassword, new UserObj.onUpdateProfile() {
                    @Override
                    public void success() {
                        onFinish.success();
                    }

                    @Override
                    public void failure(Error error) {
                        onFinish.failure(error);
                    }
                });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                onFinish.failure(error);
            }
        } else {

        }
    }

    public interface OnUploadAvatar {
        void Success();

        void Failure(Error error);
    }

    public void uploadAvatar(File file, final OnUploadAvatar onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

        NetworkServices.uploadImage(context, ContantValuesObject.UPLOAD_AVATAR,
                "Upload Avatar", headers, file, new NetworkServices.OnUploadImage() {
                    @Override
                    public void onFinish(JSONObject jsonObject) {
                        try {
                            switch (jsonObject.getInt(ContantValuesObject.CODE)) {
                                case Error.NO_ERROR:
                                    JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    UserObj.getInstance().setPhoto(result.getString("url_image"));
                                    onFinish.Success();
                                    break;
                                case Error.WRONG_TYPE_FILE:
                                    onFinish.Failure(new Error(Error.WRONG_TYPE_FILE,
                                            jsonObject.getString(ContantValuesObject.MESSAGE)));
                                    break;
                                case Error.FILE_ALREADY_EXIST:
                                    onFinish.Failure(new Error(Error.FILE_ALREADY_EXIST,
                                            jsonObject.getString(ContantValuesObject.MESSAGE)));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
