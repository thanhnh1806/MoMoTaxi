package bhtech.com.cabbytaxi.RateDriver;

/**
 * Created by thanh_nguyen on 19/01/2016.
 */
public class RateTaxiInterface {
    public interface Delegate {
        void onCreateViewFinish();

        void onShareFacebookDialogCreateViewFinish();

        void onButtonAddAsFavouriteDriverClick();

        void onButtonDoneClick();

        void onButtonShareClick();

        void onUserAddComment(String s);

        void onButtonCancelShareFacebookClick();

        void onButtonPostClick();

        void onButtonDownloadAppClick();
    }

    public interface Datasource {
        String getDriverName();

        String getCarNumber();

        void setRateDriver(int rateDriver);

        String getUserFacebookName();

        int getDriverId();

        String getauthToken();
    }
}
