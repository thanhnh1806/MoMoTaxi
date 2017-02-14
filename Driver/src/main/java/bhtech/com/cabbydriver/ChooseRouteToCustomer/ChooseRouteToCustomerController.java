package bhtech.com.cabbydriver.ChooseRouteToCustomer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbydriver.ChangePickUpLocation.ChangePickUpLocationController;
import bhtech.com.cabbydriver.DrivingToCustomer.DrivingToCustomerController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.SupportClasses.Map.OnMapViewListener;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class ChooseRouteToCustomerController extends SlidingMenuController implements OnMapViewListener,
        ChooseRouteToCustomerInterface.Listener {
    Context context = this;
    ChooseRouteToCustomerModel model = new ChooseRouteToCustomerModel(context);
    ChooseRouteToCustomerMapView mapView = new ChooseRouteToCustomerMapView();
    ChooseRouteToCustomerOtherView otherView = new ChooseRouteToCustomerOtherView();

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
                    if (model.getCustomerStatus(data) == ContantValuesObject.TaxiRequestStatusUserConfirmed) {
                        model.setEnableButtonGo(true);
                        model.getRequest(new ChooseRouteToCustomerModel.OnGetRequest() {
                            @Override
                            public void Success() {
                                mapView.showCustomerLocation();
                            }

                            @Override
                            public void Failure(ErrorObj error) {
                                showAlertDialog(error.errorMessage).show();
                            }
                        });

                    } else {
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setView(mapView, context.getString(R.string.choose_route_for_pick_up));
        super.setView(otherView, context.getString(R.string.choose_route_for_pick_up));

        mapView.setListener(this);
        mapView.setDatasource(model);

        otherView.setListener(this);
        otherView.setDatasource(model);

        model.setCloseNavigation(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContantValuesObject.GCM_INTENT_FILTER_ACTION);
        filter.addAction(ContantValuesObject.CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION);
        this.registerReceiver(receiver, filter);
    }

    @Override
    public void onGoogleMapReady() {
    }

    @Override
    public void onMyLocationChange() {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onDrawYourMaker(LatLng latLng) {

    }

    @Override
    public void onPolylineClick() {
        model.setListPolylineOptions();
        mapView.reloadView();
        otherView.reloadRouteInformation();
    }

    @Override
    public void onButtonCallClick() {
        if (!StringObject.isNullOrEmpty(model.getCustomerPhoneNumber())) {
            startActivity(PhoneObject.makePhoneCall(model.getCustomerPhoneNumber()));
        } else {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    @Override
    public void onButtonMessageClick() {
        if (!StringObject.isNullOrEmpty(model.getCustomerPhoneNumber())) {
            startActivity(PhoneObject.sendSMS(model.getCustomerPhoneNumber()));
        } else {
            showAlertDialog(getString(R.string.can_not_get_customer_phone_number)).show();
        }
    }

    @Override
    public void onButtonReloadLocationClick() {
        startActivity(new Intent(context, ChangePickUpLocationController.class));
        finishActivity();
    }

    @Override
    public void onButtonGoClick() {
        if (model.isEnableButtonGo()) {
            driverStartGoToPickUp();
        } else {
            if (TaxiRequestObj.getInstance().getRequestPickupTime() != null) {
                driverStartGoToPickUp();
            }
        }
    }

    private void driverStartGoToPickUp() {
        model.driverStartGoToPickUp(new ChooseRouteToCustomerModel.OnDriverStartGoToPickUp() {
            @Override
            public void Success() {
                startActivity(new Intent(context, DrivingToCustomerController.class));
                finishActivity();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonChangeLocationClick() {
        if (model.getPolylineUserClick() < model.getListPolylineOptions().size() - 1) {
            model.setPolylineUserClick(model.getPolylineUserClick() + 1);
        } else {
            model.setPolylineUserClick(0);
        }
        model.setListPolylineOptions();
        mapView.reloadView();
        otherView.reloadRouteInformation();
    }

    @Override
    public void onButtonCloseNavi() {
        otherView.btnCloseNavi.setVisibility(View.GONE);
        model.setCloseNavigation(true);
    }

    @Override
    public void otherviewCreateFinish() {
        model.getRequest(new ChooseRouteToCustomerModel.OnGetRequest() {
            @Override
            public void Success() {
                getDirections();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });

    }

    private void getDirections() {
        model.getDirections(new ChooseRouteToCustomerModel.OnGetDirections() {
            @Override
            public void Success() {
                model.setListPolylineOptions();
                mapView.reloadView();
                otherView.reloadRouteInformation();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });

        otherView.reloadRequestInformation();
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
