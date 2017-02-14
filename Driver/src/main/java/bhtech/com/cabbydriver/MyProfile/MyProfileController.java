package bhtech.com.cabbydriver.MyProfile;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/6/16.
 */
public class MyProfileController extends SlidingMenuController implements MyProfileInterface.Listener {
    private Context context = this;
    private MyProfileModel model = new MyProfileModel(context);
    private MyProfileView view = new MyProfileView();
    private DatePickerView datePickerView = new DatePickerView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setView(view, getString(R.string.driver_profile));
        setView(datePickerView, getString(R.string.driver_profile));
        view.setListener(this);
        view.setDatasource(model);
        datePickerView.setListener(this);
        datePickerView.setDatasource(model);

        model.getDriverInformation(new MyProfileModel.OnGetDriverInformation() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void chooseDateRangeForWorkingHour() {
        model.getWorkingHourInDateRange(new MyProfileModel.OnGetWorkingHour() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void chooseDateRangeForMileage() {
        model.getMileageInDateRange(new MyProfileModel.OnGetMileage() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void chooseFromDateFinish() {
        datePickerView.showDateTimePicker();
    }

    @Override
    public void onButtonChooseDateRangeClick() {
        datePickerView.showDateTimePicker();
    }

    @Override
    public void onWorkingHourListMonthItemClick() {
        model.getWorkingHourByMonth(new MyProfileModel.OnGetWorkingHour() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onMileageListMonthItemClick() {
        model.getMileageByMonth(new MyProfileModel.OnGetMileage() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onWorkingHourListWeekItemClick() {
        model.getWorkingHourByWeek(new MyProfileModel.OnGetWorkingHour() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onMileageListWeekItemClick() {
        model.getMileageByWeek(new MyProfileModel.OnGetMileage() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }
}

