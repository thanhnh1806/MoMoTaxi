package bhtech.com.cabbydriver.FindCustomer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import bhtech.com.cabbydriver.ChooseRouteToCustomer.ChooseRouteToCustomerController;
import bhtech.com.cabbydriver.ChooseRouteToDestination.ChooseRouteToDestinationController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

public class FindCustomerController extends SlidingMenuController implements FindCustomerInterface.Listener {
    private Context context = this;
    private FindCustomerModel model = new FindCustomerModel(context);
    private FindCustomerView findCustomerView = new FindCustomerView();
    private FindCustomerAcceptView acceptView = new FindCustomerAcceptView();
    private FindCustomerListView listView = new FindCustomerListView();
    private FindCustomerNonAppView nonAppView = new FindCustomerNonAppView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setView(listView, getString(R.string.map_view));
            setView(findCustomerView, getString(R.string.map_view));
            setView(acceptView, getString(R.string.map_view));
            setView(nonAppView, getString(R.string.map_view));

            hideAllFragment();
            showFragmentv4(findCustomerView);
        } else {
            //Do nothing
        }

        findCustomerView.setListener(this);
        findCustomerView.setDataSource(model);
        acceptView.setListener(this);
        acceptView.setDataSource(model);
        listView.setListener(this);
        listView.setDataSource(model);
        nonAppView.setListener(this);
        nonAppView.setDataSource(model);
        nonAppView.setFragmentActivity(this);
    }

    public void hideAllFragment() {
        hideFragmentv4(findCustomerView);
        hideFragment(acceptView);
        hideFragment(listView);
        hideFragment(nonAppView);
    }

    private Handler handler;
    private GetListRequestRunable getListRequestRunable;
    int tenSecond = 10000;

    @Override
    public void onGoogleMapReady() {
        getListRequestRunable = new GetListRequestRunable();
        handler = new Handler();
        handler.removeCallbacks(getListRequestRunable);
        handler.postDelayed(getListRequestRunable, tenSecond);
        OnButtonRefreshClick();
    }

    private class GetListRequestRunable implements Runnable {

        @Override
        public void run() {
            getListRequest();
            handler.postDelayed(getListRequestRunable, tenSecond);
        }
    }

    private void getListRequest() {
        model.getListRequest(context, new FindCustomerModel.onGetListRequest() {
            @Override
            public void Success() {
                findCustomerView.reloadView();
                model.getListDistance(context);
                model.setListMarkerOptions();
                findCustomerView.reloadView();
                listView.reloadView();
            }

            @Override
            public void Falure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void OnMarkerClick() {
        model.setListMarkerOptions();
        findCustomerView.reloadView();
        findCustomerView.showHideButtonNonApp(false);
        showFragment(acceptView);
        acceptView.reloadView();
    }

    @Override
    public void OnListViewItemClick() {
        showFragment(acceptView);
        acceptView.reloadView();
    }

    @Override
    public void OnButtonNonAppClick() {
        findCustomerView.showHideHeader(true);
        findCustomerView.showHideButtonNonApp(false);
        showFragment(nonAppView);
        nonAppView.reloadView();
    }

    @Override
    public void OnButtonListViewClick() {
        hideAllFragment();
        showFragmentv4(findCustomerView);
        findCustomerView.showHideButtonNonApp(true);
        findCustomerView.showHideHeader(false);
        findCustomerView.showHideButtonMapView(true);
        showFragment(listView);
    }

    @Override
    public void OnButtonMapViewClick() {
        hideAllFragment();
        showFragmentv4(findCustomerView);
        findCustomerView.showHideButtonNonApp(true);
        findCustomerView.showHideHeader(true);
        findCustomerView.showHideButtonMapView(false);
    }

    @Override
    public void OnButtonAcceptClick() {
        model.driverChooseCustomer(new FindCustomerModel.OnDriverChooseCustomer() {
            @Override
            public void Success() {
                if (model.driverChooseCustomerFutureBooking(model.getListViewPositionClick())) {
                    model.savePickUpTime();
                    hideAllFragment();
                    showFragmentv4(findCustomerView);
                    OnButtonRefreshClick();
                } else {
                    gotoChooseRouteForPickUpScreen();
                }
            }

            @Override
            public void Falure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void OnButtonRefreshClick() {
        hideFragment(acceptView);
        model.setListViewPositionClick(-1);
        getListRequest();
    }

    @Override
    public void OnButtonCloseDialogClick() {
        findCustomerView.showHideHeader(listView.isHidden());
        findCustomerView.showHideButtonNonApp(true);
        hideFragment(acceptView);
        hideFragment(nonAppView);
    }

    @Override
    public void OnButtonStartNonAppClick() {
        model.startNonAppUserTrip(new FindCustomerModel.StartNonAppUserTrip() {
            @Override
            public void Success() {
                model.driverChooseNonAppCustomer(new FindCustomerModel.OnDriverChooseCustomer() {
                    @Override
                    public void Success() {
                        model.saveDataForNonAppUser();
                        gotoChooseRouteToDestinationScreen();
                    }

                    @Override
                    public void Falure(ErrorObj error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onIconLocationClick() {
        nonAppView.reloadListNearby();
    }

    @Override
    public void zoomToDriverLoation() {
        if (model.isFirstZoom()) {
            findCustomerView.calculateZoomAndCamera(CarDriverObj.getInstance().getLocation());
        }
    }

    @Override
    public void setCurrentAddress() {
        model.setCurrentAddress();
    }

    private void gotoChooseRouteToDestinationScreen() {
        startActivity(new Intent(context, ChooseRouteToDestinationController.class));
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        hideAllFragment();
        showFragmentv4(findCustomerView);
        findCustomerView.showHideHeader(true);
    }

    private void gotoChooseRouteForPickUpScreen() {
        model.saveDataCurrentRequest();
        startActivity(new Intent(context, ChooseRouteToCustomerController.class));
        finishActivity();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(ContantValuesObject.GCM_INTENT_FILTER_ACTION)) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                String messageType = gcm.getMessageType(intent);
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                } else {
                    String msg = intent.getStringExtra(ContantValuesObject.MESSAGE.toLowerCase());
                    String data = intent.getStringExtra(ContantValuesObject.DATA);
                    Log.e(ContantValuesObject.MESSAGE, msg);
                    Log.e(ContantValuesObject.DATA, data);

                    switch (model.checkCustomerStatus(data)) {
                        case ContantValuesObject.FutureBookingUserConfirmed:
                            requestGetFutureBookingList(new GetFutureBookingList() {
                                @Override
                                public void success() {
                                    TaxiRequestObj requestObj = futureBookings.get(futureBookings.size() - 1);
                                    String title = getString(R.string.taxi_booked)
                                            + "\n" + requestObj.getFromLocationAddress();

                                    String description = getString(R.string.taxi_booked)
                                            + " " + requestObj.getFromLocationAddress()
                                            + " " + getString(R.string.to).toLowerCase()
                                            + " " + requestObj.getToLocationAddress();

                                    TimeObject.addReminderInCalendar(context, title, description,
                                            requestObj.getRequestPickupTime());
                                    updateBadgeNumberOfAlert();
                                }

                                @Override
                                public void failure(ErrorObj errorObj) {
                                    showAlertDialog(errorObj.errorMessage).show();
                                }
                            });
                            showAlertDialog(getString(R.string.customer_confirmed)).show();
                            break;
                        case ContantValuesObject.TaxiRequestStatusCancelled:
                            hideAllFragment();
                            showFragmentv4(findCustomerView);
                            showAlertDialog(getString(R.string.user_cancel_request)).show();
                            break;
                    }
                }
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContantValuesObject.GCM_INTENT_FILTER_ACTION);
        filter.addAction(ContantValuesObject.CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION);
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        handler.removeCallbacks(getListRequestRunable);
    }
}
