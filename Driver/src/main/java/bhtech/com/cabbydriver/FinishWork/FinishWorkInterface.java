package bhtech.com.cabbydriver.FinishWork;

/**
 * Created by duongpv on 4/5/16.
 */
public class FinishWorkInterface {
    public interface Delegate {
        void finishButtonOnClick(String mileage);
    }

    public interface DataSource {
        String getTodayDate ();
        String getNumberOfPassengers ();
        String getTotalDistance ();
        String getTotalHours ();
        String getSales ();
    }
}
