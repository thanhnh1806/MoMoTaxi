package bhtech.com.cabbydriver.DrivingToCustomer;

/**
 * Created by thanh_nguyen on 13/04/2016.
 */
public class DrivingToCustomerInterface {
    public interface Listener {

        void buttonPickUpUserClick();

        void buttonShowNaviClick();

        void buttonCallClick();

        void buttonMessageClick();

        void buttonChangeLocation();

        void buttonStartClick();

        void withoutNaviViewCreated();
    }

    public interface Datasource {

        String getCustomerName();

        String getStartAddress();
    }
}
