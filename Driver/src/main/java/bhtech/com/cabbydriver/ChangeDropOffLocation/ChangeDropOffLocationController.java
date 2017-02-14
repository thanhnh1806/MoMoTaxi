package bhtech.com.cabbydriver.ChangeDropOffLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbydriver.DrivingToCustomer.DrivingToCustomerController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;

public class ChangeDropOffLocationController extends SlidingMenuController implements
        ChangeDropOffLocationInterface.Listener {

    private Context context = this;
    private ChangeDropOffLocationModel model = new ChangeDropOffLocationModel(context);
    private ChangeDropOffLocationMapView mapView = new ChangeDropOffLocationMapView();
    private ChangeDropOffLocationView view = new ChangeDropOffLocationView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(mapView, getString(R.string.navigation));
        setView(view, getString(R.string.navigation));

        mapView.setListener(this);
        mapView.setDatasource(model);
        view.setListener(this);
        view.setDatasource(model);
        view.setFragmentActivity(this);
    }

    @Override
    public void onButtonCancelClick() {
        startActivity(new Intent(context, DrivingToCustomerController.class));
        super.finishActivity();
    }

    @Override
    public void onButtonOkClick() {
        model.changeDropOffLocation(new ChangeDropOffLocationModel.ChangeDropOffLocation() {
            @Override
            public void Success() {
                onButtonCancelClick();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void listGooglePlacesItemClick() {
        mapView.showCustomerAndDriverMarker();
    }

    @Override
    public void onGoogleMapReady() {
        mapView.showCustomerAndDriverMarker();
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
                    } else {
                        showAlertDialog(getString(R.string.user_cancel_request)).show()
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
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
    }

    @Override
    public void onBackPressed() {
    }
}
