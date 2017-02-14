package bhtech.com.cabbytaxi.MyProfile;

/**
 * Created by thanh_nguyen on 15/02/2016.
 */
public class MyProfileInterface {
    public interface Listener {
        void onCreateViewFinish();

        void onButtonLogoutClick();

        void onButtonDeleteClick();

        void onAvatarClick();

        void onButtonEditScreenNameClick();

        void onButtonEditPhoneNumberClick();

        void onButtonEditEmailClick();

        void onButtonEditCountryClick();

        void onButtonEditUsernameClick();

        void onButtonEditPasswordClick();

        void onButtonFacebookClick();

        void onButtonGooglePlusClick();

        void onButtonTwitterClick();

        void onButtonBackClick();

        void onButtonSaveClick();

        void onDialogButtonTakePicktureOnCameraClick();

        void onDialogButtonChoosePicktureFromGallery();
    }

    public interface Database {

        void setScreenName(String s);

        void setPhoneNumber(String s);

        void setEmail(String s);

        void setCountry(String s);

        void setUsername(String s);

        void setPassword(String s);

        String getScreenName();

        String getPhoneNumber();

        String getEmail();

        String getUsername();

        String getPassword();

        String getCountry();

        String getUserPhoto();

        void userChangePassword(boolean b);
    }
}
