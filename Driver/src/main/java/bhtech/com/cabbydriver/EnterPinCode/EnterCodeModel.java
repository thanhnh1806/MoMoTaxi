package bhtech.com.cabbydriver.EnterPinCode;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.SharedPreference;

/**
 * Created by thanh_nguyen on 21/01/2016.
 */
public class EnterCodeModel implements EnterCodeInterface.Datasource {
    private String pinCode = "";
    private Context context;
    private boolean rememberMe;
    private boolean enableButtonLogin = true;
    public ArrayList<String> permissions;
    private boolean isUserAcceptAllPermission = true;

    public boolean isUserAcceptAllPermission() {
        return isUserAcceptAllPermission;
    }

    public EnterCodeModel(Context context) {
        this.context = context;
    }

    @Override
    public String getPinCode() {
        return pinCode;
    }

    @Override
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public void setRememberMe(boolean b) {
        rememberMe = b;
    }

    public boolean checkAutoLogin() {
        pinCode = SharedPreference.get(context, ContantValuesObject.PIN_CODE, null);
        if (pinCode != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setEnableButtonLogin(boolean b) {
        enableButtonLogin = b;
    }

    public boolean isEnableButtonLogin() {
        return enableButtonLogin;
    }

    public void isUserAcceptAllPermission(boolean b) {
        isUserAcceptAllPermission = b;
    }

    public interface OnCheckPinCode {
        void Success();

        void Failure(ErrorObj error);
    }

    public void checkPinCode(Context context, final OnCheckPinCode onFinish) {
        final ErrorObj error = new ErrorObj();

        String deviceToken = SharedPreference.get(context, ContantValuesObject.GCM_DEVICE_TOKEN, "");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Android"));
        headers.put("Device-Token", Arrays.asList(deviceToken));

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("pin_code", Arrays.asList(pinCode));

        NetworkObject.callAPI(context, ContantValuesObject.CHECK_PIN_CODE_ENDPOINT, NetworkObject.POST,
                "PinCode", headers, parameters, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.SUCCESS) {
                                JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                CarDriverObj.getInstance().parseJsonToObject(result);
                                if (rememberMe) {
                                    savePinCodeForAutoLogin();
                                }
                                onFinish.Success();
                            } else {
                                error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                onFinish.Failure(error);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            error.setError(ErrorObj.UNKNOWN_ERROR);
                            onFinish.Failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    private void savePinCodeForAutoLogin() {
        SharedPreference.set(context, ContantValuesObject.PIN_CODE,
                CarDriverObj.getInstance().getDriver().getPin_code());
    }
}

