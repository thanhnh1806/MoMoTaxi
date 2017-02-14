package bhtech.com.cabbydriver.DrivingToDestination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import bhtech.com.cabbydriver.ChargeCustomer.ChargeCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class DrivingToDestinationController extends SlidingMenuController implements
        DrivingToDestinationInterface.Listener {
    Context context = this;
    DrivingToDestinationModel model = new DrivingToDestinationModel(context);
    DrivingToDestinationWithoutNaviView withoutNaviView = new DrivingToDestinationWithoutNaviView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(withoutNaviView, getString(R.string.driving_to_destination));

        withoutNaviView.setDatasource(model);
        withoutNaviView.setListener(this);
    }

    @Override
    public void showNaviClick() {
        try {
            LatLng latLng = TaxiRequestObj.getInstance().getToLocation();
            startActivity(LocationObject.showNavigationOnGoogleMap(latLng));
        } catch (Exception e) {
            showAlertDialog(context.getString(R.string.your_google_map_is_out_of_date)).show();
        }
    }

    @Override
    public void buttonFinishClick() {
        if (TaxiRequestObj.getInstance().getRequestUser() == null) {
            startActivity(new Intent(context, ChargeCustomerController.class));
            finishActivity();
        } else {
            model.drivingToDestinationFinish(new DrivingToDestinationModel.DrivingToDestinationFinish() {
                @Override
                public void Success() {
                    startActivity(new Intent(context, ChargeCustomerController.class));
                    finishActivity();
                }

                @Override
                public void Failure(ErrorObj error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    }
}
