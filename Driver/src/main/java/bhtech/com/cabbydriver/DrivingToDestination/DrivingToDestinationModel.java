package bhtech.com.cabbydriver.DrivingToDestination;

import android.content.Context;

import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 14/04/2016.
 */
public class DrivingToDestinationModel implements DrivingToDestinationInterface.Datasource {
    Context context;

    public DrivingToDestinationModel(Context context) {
        this.context = context;
    }

    @Override
    public String getCustomerName() {
        try {
            return TaxiRequestObj.getInstance().getRequestUser().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getEndAddress() {
        return TaxiRequestObj.getInstance().getToLocationAddress();
    }

    public interface DrivingToDestinationFinish {
        void Success();

        void Failure(ErrorObj error);
    }

    public void drivingToDestinationFinish(final DrivingToDestinationFinish onFinish) {
        TaxiRequestObj.getInstance().driverChangeStatusRequest(context,
                ContantValuesObject.TaxiRequestStatusCharged,
                new TaxiRequestObj.DriverChangeStatusRequest() {
                    @Override
                    public void Success() {
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }
}
