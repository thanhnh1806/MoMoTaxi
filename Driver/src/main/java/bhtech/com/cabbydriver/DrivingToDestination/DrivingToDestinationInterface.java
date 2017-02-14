package bhtech.com.cabbydriver.DrivingToDestination;

/**
 * Created by thanh_nguyen on 14/04/2016.
 */
public class DrivingToDestinationInterface {
    public interface Listener {
        void showNaviClick();

        void buttonFinishClick();
    }

    public interface Datasource {
        String getCustomerName();

        String getEndAddress();
    }
}
