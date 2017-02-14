package bhtech.com.cabbydriver.EnterPinCode;

import android.app.Activity;

/**
 * Created by thanh_nguyen on 21/01/2016.
 */
public class EnterCodeInterface {
    public interface Listener {
        void checkPinCode();

        void OnButtonOkPinCodeSuccessClick();

        void OnButtonClosePinCodeWrongDialogClick();
    }

    public interface Datasource {
        String getPinCode();

        void setPinCode(String pinCode);

        void setRememberMe(boolean shown);
    }
}
