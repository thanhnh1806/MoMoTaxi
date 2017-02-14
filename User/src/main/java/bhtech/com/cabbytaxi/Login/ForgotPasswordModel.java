package bhtech.com.cabbytaxi.Login;

import android.content.Context;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 05/04/2016.
 */
public class ForgotPasswordModel {
    Context context;
    public String email;

    public ForgotPasswordModel(Context context) {
        this.context = context;
    }

    public interface OnForgotPassword {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void forgotPassword(final OnForgotPassword onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {

            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("email", Arrays.asList(email));

            NetworkServices.callAPI(context, ContantValuesObject.FORGOT_PASSWORD,
                    NetworkServices.POST, null, parameters, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            if (isSuccess) {
                                try {
                                    if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                        onFinish.Success();
                                    } else {
                                        if (jsonObject.getInt(ContantValuesObject.CODE) == Error.INVALID_EMAIL_FORMAT) {
                                            error.setError(Error.INVALID_EMAIL_FORMAT, context.getString(R.string.email_wrong_format));
                                        } else if (jsonObject.getInt(ContantValuesObject.CODE) == Error.USERNAME_NOT_EXISTED) {
                                            error.setError(Error.USERNAME_NOT_EXISTED, context.getString(R.string.username_not_existed));
                                        } else if (jsonObject.getInt(ContantValuesObject.CODE) == Error.EMAIL_NOT_EXISTED) {
                                            error.setError(Error.EMAIL_NOT_EXISTED, context.getString(R.string.email_not_existed));
                                        } else {
                                            error.setError(Error.UNKNOWN_ERROR, context.getString(R.string.unknown_error));
                                        }
                                        onFinish.Failure(error);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error.setError(Error.UNKNOWN_ERROR, context.getString(R.string.unknown_error));
                                    onFinish.Failure(error);
                                }

                            } else {
                                error.setError(Error.UNKNOWN_ERROR, context.getString(R.string.unknown_error));
                                onFinish.Failure(error);
                            }
                        }
                    }
            );
        } else {
            error.setError(Error.NETWORK_DISCONNECT, context.getString(R.string.please_check_your_network));
            onFinish.Failure(error);
        }
    }
}
