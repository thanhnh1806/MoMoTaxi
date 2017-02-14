package bhtech.com.cabbytaxi.FutureBooking;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.StringObject;

public class FutureBookingController extends SlidingMenuController implements FutureBookingInterface.Listener {
    private Context context;
    private FutureBookingModel model;
    private FutureBookingView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_future_booking_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.future_bookings));

        context = FutureBookingController.this;
        model = new FutureBookingModel(context);

        view = new FutureBookingView();
        view.setListener(this);
        view.setDatasource(model);

        if (savedInstanceState == null) {
            addFragment(R.id.container, view);
        } else {
            //Do nothing
        }
    }

    @Override
    public void onCreateViewFinish() {
        try {
            model.setListSortBy(context);
            model.setListReceipt(context, new FutureBookingModel.onGetListReceipt() {
                @Override
                public void onSuccess() {
                    view.reloadView();
                }

                @Override
                public void onFailure(bhtech.com.cabbytaxi.object.Error error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListSortByItemClick() {
        model.sortReceiptList();
        view.reloadView();
    }

    @Override
    public void onListMonthItemClick() {
        //TODO wait comment Customer
    }

    @Override
    public void onItemButtonCancelClick() {
        model.userCancelFutureBooking(new FutureBookingModel.onUserCancelFutureBooking() {
            @Override
            public void onSuccess() {
                onCreateViewFinish();
            }

            @Override
            public void onFailure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onItemButtonCallClick() {
        if (StringObject.isNullOrEmpty(model.getDriverPhoneNumber())) {
            showAlertDialog(getString(R.string.can_not_get_driver_phone_number)).show();
        } else {
            startActivity(PhoneObject.makePhoneCall(model.getDriverPhoneNumber()));
        }
    }

    @Override
    public void onItemButtonMessageClick() {
        if (StringObject.isNullOrEmpty(model.getDriverPhoneNumber())) {
            showAlertDialog(getString(R.string.can_not_get_driver_phone_number)).show();
        } else {
            startActivity(PhoneObject.sendSMS(model.getDriverPhoneNumber()));
        }
    }
}
