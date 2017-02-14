package bhtech.com.cabbydriver.CarStatus;

import java.util.Date;

/**
 * Created by duongpv on 4/7/16.
 */
public class CarStatusInterface {
    public interface DataSource {
        String getCarImageUrl ();
        String getCarNumber ();
        String getCarType ();
        double getTotalDrove ();
        int getCarAge ();
        Date getNextMaintenanceDate ();
        double getRemainingKm ();
        int getNumberOfBreakDown ();
        int getNumberOfAccident ();
    }
}
