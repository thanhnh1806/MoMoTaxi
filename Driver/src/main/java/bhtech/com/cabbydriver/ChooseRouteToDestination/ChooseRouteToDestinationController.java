package bhtech.com.cabbydriver.ChooseRouteToDestination;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbydriver.DrivingToDestination.DrivingToDestinationController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.SupportClasses.Map.OnMapViewListener;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class ChooseRouteToDestinationController extends SlidingMenuController implements OnMapViewListener,
        ChooseRouteToDestinationInterface.Listener {
    Context context = this;
    ChooseRouteToDestinationModel model = new ChooseRouteToDestinationModel(context);
    ChooseRouteToDestinationMapView mapView = new ChooseRouteToDestinationMapView();
    ChooseRouteToDestinationOtherView otherView = new ChooseRouteToDestinationOtherView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(mapView, getString(R.string.choose_route));
        setView(otherView, getString(R.string.choose_route));

        mapView.setListener(this);
        mapView.setDatasource(model);

        otherView.setListener(this);
        otherView.setDatasource(model);

        model.setCloseNavigation(false);

        model.driverChooseRouteToDestination(new ChooseRouteToDestinationModel.DriverChooseRouteToDestination() {
            @Override
            public void Success() {
                Log.d("UpdateDriverStatus", "ChooseRoute");
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onPolylineClick() {
        model.setListPolylineOptions();
        mapView.reloadView();
        otherView.reloadView();
    }

    @Override
    public void onButtonGoClick() {
        if (model.isEnableButtonGo()) {
            model.saveMileage();
            model.drivingToDestination(new ChooseRouteToDestinationModel.DrivingToDestination() {
                @Override
                public void Success() {
                    startActivity(new Intent(context, DrivingToDestinationController.class));
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
    public void onButtonBackClick() {
        if (TaxiRequestObj.getInstance().getRequestUser() == null) {
            model.cancelNonAppUser(new ChooseRouteToDestinationModel.DriverCancelNonAppUser() {
                @Override
                public void Success() {
                    startActivity(new Intent(context, FindCustomerController.class));
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
    public void onGoogleMapReady() {
        model.getCurrentRequest(new ChooseRouteToDestinationModel.OnGetCurrentRequest() {
            @Override
            public void Success() {
                model.getDirections(new ChooseRouteToDestinationModel.OnGetDirections() {
                    @Override
                    public void Success() {
                        model.setListPolylineOptions();
                        mapView.reloadView();
                        otherView.reloadView();
                    }

                    @Override
                    public void Failure(final ErrorObj error) {
                        showAlertDialog(error.errorMessage).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (error.errorCode == ErrorObj.DATA_NULL) {
                                    onButtonBackClick();
                                }
                            }
                        }).show();
                    }
                });
            }

            @Override
            public void Failure(ErrorObj error) {

            }
        });

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
    public void onBackPressed() {
    }
}
