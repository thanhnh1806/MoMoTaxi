package bhtech.com.cabbydriver.Alert;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbydriver.ChooseRouteToCustomer.ChooseRouteToCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by duongpv on 4/6/16.
 */
public class AlertController extends SlidingMenuController implements AlertInterface.Delegate {
    private Context context = this;
    private AlertModel model = new AlertModel(context);
    private AlertView view = new AlertView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setView(view, getString(R.string.alert_title));

        view.setContext(context);
        view.setDelegate(this);
        view.setDataSource(model);

        model.setMonth(TimeObject.getCurrentMonth());
        updateAlertAndFutureBookingList();
    }

    @Override
    public void alertOnClickAtIndex(int i) {

    }

    @Override
    public void futureBookingOnCancelAtIndex(int i) {
        model.driverCancelFutureBooking(i, new AlertModel.OnDriverCancelFutureBooking() {
            @Override
            public void Success() {

            }

            @Override
            public void Failure(ErrorObj error) {

            }
        });
    }

    @Override
    public void futureBookingOnCallAtIndex(String customerPhoneNumber) {
        try {
            startActivity(PhoneObject.makePhoneCall(customerPhoneNumber));
        } catch (Exception e) {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    @Override
    public void futureBookingOnClickAtIndex(final int requestId) {
        model.startPickUp(requestId, new AlertModel.OnStartPickUp() {
            @Override
            public void Success() {
                SharedPreference.set(context, ContantValuesObject.REQUEST_ID, String.valueOf(requestId));
                startActivity(new Intent(context, ChooseRouteToCustomerController.class));
                finishActivity();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void filterByMonth(int position) {
        model.setFromDateString("1");
        model.setToDateString("" + TimeObject.getLastDayOfMonth(position, model.getThisYear()));
        model.setMonth(position);

        updateAlertAndFutureBookingList();
    }

    @Override
    public void filterByDay(int position) {
        model.setDay(position);
        updateAlertAndFutureBookingList();
    }

    @Override
    public void groupOnClickAtPosition(int groupPosition) {

    }

    @Override
    public void groupExpandedAtPosition(int groupPosition) {
        model.setExpandedGroupPosition(groupPosition);
        if (model.getGroupNeedCollapsedPosition() != groupPosition && model.getGroupNeedCollapsedPosition() != -1) {
            view.collapseGroupAtPosition(model.getGroupNeedCollapsedPosition());
            model.clearGroupNeedToCollapse();
        } else {
            //  Do nothing
        }
        view.reloadView();
    }

    @Override
    public void groupCollapseAtPosition(int groupPosition) {
        model.clearGroupNeedToExpand();
        view.reloadView();
    }

    @Override
    public void futureBookingOnMessageAtIndex(String customerPhoneNumber) {
        try {
            startActivity(PhoneObject.sendSMS(customerPhoneNumber));
        } catch (Exception e) {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    private void updateAlertAndFutureBookingList() {
        model.requestGetAlertList(new AlertModel.OnGetAlertListFinish() {
            @Override
            public void success() {
                model.requestGetFutureBookingList(new AlertModel.OnGetFutureBookingListFinish() {
                    @Override
                    public void success() {
                        view.reloadView();
                    }

                    @Override
                    public void failure(ErrorObj errorObj) {
                        view.reloadView();
                    }
                });
            }

            @Override
            public void failure(ErrorObj errorObj) {
                model.requestGetFutureBookingList(new AlertModel.OnGetFutureBookingListFinish() {
                    @Override
                    public void success() {
                        view.reloadView();
                    }

                    @Override
                    public void failure(ErrorObj errorObj) {
                        view.reloadView();
                    }
                });
            }
        });
    }
}
