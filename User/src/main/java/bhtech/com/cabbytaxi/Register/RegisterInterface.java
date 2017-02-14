package bhtech.com.cabbytaxi.Register;

/**
 * Created by thanh_nguyen on 02/03/2016.
 */
public class RegisterInterface {
    public interface Listener {
        void onCreateViewFinish();

        void onForPersonalClick();

        void onForCompanyClick();

        void onChooseProfileNextClick();
    }

    public interface Datasource {
        void setUserChooseProfile(int profile);

        int getUserChooseProfile();
    }
}
