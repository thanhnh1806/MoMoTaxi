package bhtech.com.cabbydriver.DrivingToCustomer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;

import bhtech.com.cabbydriver.ChangeDropOffLocation.ChangeDropOffLocationController;
import bhtech.com.cabbydriver.ChooseRouteToDestination.ChooseRouteToDestinationController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class DrivingToCustomerController extends SlidingMenuController implements DrivingToCustomerInterface.Listener {
    Context context = this;
    DrivingToCustomerModel model = new DrivingToCustomerModel(context);
    DrivingToCustomerWithoutNaviView withoutNaviView = new DrivingToCustomerWithoutNaviView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(withoutNaviView, getString(R.string.driving_to_customer));
        withoutNaviView.setListener(this);
        withoutNaviView.setDatasource(model);

        model.getCurrentRequest(new DrivingToCustomerModel.OnGetCurrentRequest() {
            @Override
            public void Success() {
                if (TaxiRequestObj.getInstance().getStatus() ==
                        ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger) {
                    withoutNaviView.setEnableButtonStart();
                }
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void buttonPickUpUserClick() {
        model.driverPickUpCustomer(new DrivingToCustomerModel.DriverPickUpCustomer() {
            @Override
            public void Success() {
                withoutNaviView.setEnableButtonStart();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void buttonShowNaviClick() {
        try {
            LatLng latLng = TaxiRequestObj.getInstance().getToLocation();
            startActivity(LocationObject.showNavigationOnGoogleMap(latLng));
        } catch (Exception e) {
            showAlertDialog(context.getString(R.string.your_google_map_is_out_of_date)).show();
        }
    }

    @Override
    public void buttonCallClick() {
        if (!StringObject.isNullOrEmpty(model.getCustomerPhoneNumber())) {
            startActivity(PhoneObject.makePhoneCall(model.getCustomerPhoneNumber()));
        } else {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    @Override
    public void buttonMessageClick() {
        if (!StringObject.isNullOrEmpty(model.getCustomerPhoneNumber())) {
            startActivity(PhoneObject.sendSMS(model.getCustomerPhoneNumber()));
        } else {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    @Override
    public void buttonChangeLocation() {
        startActivity(new Intent(context, ChangeDropOffLocationController.class));
        finishActivity();
    }

    @Override
    public void buttonStartClick() {
        model.startGoToDestination(new DrivingToCustomerModel.StartGoToDestination() {
            @Override
            public void Success() {
                startActivity(new Intent(context, ChooseRouteToDestinationController.class));
                finishActivity();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void withoutNaviViewCreated() {
        if (TaxiRequestObj.getInstance().getStatus() ==
                ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger) {
            withoutNaviView.setEnableButtonStart();
        } else {
            //Do nothing
        }
        withoutNaviView.reloadView();
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
                    String data = intent.getStringExtra(ContantValuesObject.DATA);
                    Log.w("ChooseRouteToCustomer", data);
                    showAlertDialog(getString(R.string.user_cancel_request)).show()
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                                    startActivity(new Intent(context, FindCustomerController.class));
                                    finishActivity();
                                }
                            });

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
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
