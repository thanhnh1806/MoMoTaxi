package bhtech.com.cabbydriver.ChooseCar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.services.GetLocationService;

public class ChooseCarController extends SlidingMenuController implements ChooseCarInterface.Delegate {

    private Context context;
    private ChooseCarView chooseCarView;
    private ChooseCarModel model;
    private ChooseCarDialogOdoMeter dialogOdometer;
    private ChooseCarDialogError dialogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ChooseCarController.this;
        getLayoutInflater().inflate(R.layout.activity_driver_choose_car_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.choose_your_car));
        chooseCarView = new ChooseCarView();
        model = new ChooseCarModel();
        dialogOdometer = new ChooseCarDialogOdoMeter(this);
        dialogError = new ChooseCarDialogError(this);
        dialogOdometer.setDelegate(this);
        dialogError.setDelegate(this);
        chooseCarView.setDatasource(model);
        chooseCarView.setDelegate(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.driver_choose_car_container, chooseCarView).commit();
        }

        model.getListCar(context, new ChooseCarModel.OnChooseCar() {
            @Override
            public void Success() {
                chooseCarView.reloadView();
                chooseCarView.loadVisiable();
            }

            @Override
            public void Failure(ErrorObj error) {

            }
        });
    }

    @Override
    public void onDialogViewFinish() {
        dialogError.setErrorCarNumber(model.getListCar().get(model.getPosition()).getNumber());
    }

    @Override
    public void onButtonStartClick() {
        if (model.getDriverCarNumber() == null || chooseCarView.getEnterMileage() == 0) {
            showAlertDialog(context.getString(R.string.choose_car_fail)).show();
        } else {
            model.chooseCar(context, new ChooseCarModel.OnChooseCarFinish() {
                @Override
                public void success() {
                    startService(new Intent(context, GetLocationService.class));
                    startActivity(new Intent(context, FindCustomerController.class));
                    finishAffinity();
                }

                @Override
                public void failure(ErrorObj error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        }
    }

    @Override
    public void onItemClick() {
        if (model.checkCarStatus() == false) {
            dialogError.show();
        } else {
            chooseCarView.loadVisiableAfterClick();
        }
    }

    @Override
    public void showPopup() {
        dialogOdometer.show();
    }

    @Override
    public void closePopup() {
        dialogOdometer.cancel();
        dialogError.cancel();
    }

    @Override
    public void changeCar() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        chooseCarView.loadVisiable();
    }
}
