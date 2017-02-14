package bhtech.com.cabbytaxi.SNSSetting;

import com.facebook.login.LoginResult;

/**
 * Created by Le Anh Tuan on 2/2/2016.
 */
public class SNSSettingInterface {

    public interface Listener {
        void onButtonGoogleClick();

        void onButtonFacebookClick();

        void onButtonTwitterClick();

        void onButtonLineClick();

        void onButtonBackClick();
    }

    public interface Datasource {
        void setFacebookLoginResult(LoginResult loginResult);
    }
}
