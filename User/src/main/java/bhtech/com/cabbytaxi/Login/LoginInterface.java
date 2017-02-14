package bhtech.com.cabbytaxi.Login;

import android.content.Context;

import com.facebook.login.LoginResult;

/**
 * Created by thanh_nguyen on 28/01/2016.
 */
public class LoginInterface {
    public interface OnFinishExecute {
        void onFinish(boolean result);
    }

    public interface Listener {

        void onButtonLoginClick();

        void onForgotPasswordClick();

        void onButtonLoginFacebookClick();

        void onButtonLoginTwitterClick();

        void onButtonLoginGooglePlusClick();

        void onButtonSignUpClick();
    }

    public interface Datasource {
        void setUsername(String username);

        void setPassword(String password);

        void setRememberMe(Context context, boolean isChecked);

        void setFacebookLoginResult(LoginResult loginResult);
    }
}
