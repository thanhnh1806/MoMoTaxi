package bhtech.com.cabbytaxi.FindTaxi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.WaitTaxi.WaitingTaxiController;
import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class FindTaxiController extends SlidingMenuController implements FindTaxiInterface.Listener {

    private Context context = this;
    private FindTaxiModel model;
    private FindTaxiOptionsView optionsView;
    private DateTimePickerView dateTimePickerView;
    private FindTaxiFoundView foundView;
    private FindTaxiFoundATaxiView foundATaxiView;
    private FindTaxiDropPinView dropPinView;
    private Handler handler;
    private GetListRequestRunable getListRequestRunable;
    int tenSecond = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_find_taxi_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.public_cab_locations));
        initMVC();
        addView();

        getListRequestRunable = new GetListRequestRunable();
        handler = new Handler();
        handler.removeCallbacks(getListRequestRunable);
        handler.postDelayed(getListRequestRunable, tenSecond);
    }

    private void initMVC() {
        model = new FindTaxiModel(context);
        model.setFragmentActivity(this);
        model.setCurrentDateTime();

        if (UserObj.getInstance().getLocation() != null) {
            model.getGeometryBounds(new FindTaxiModel.onGetGeometryBounds() {
                @Override
                public void Success() {
                    addOptionsView();
                }

                @Override
                public void Failure(Error error) {
                    showAlertDialog(error.errorMessage);
                }
            });
        } else {
            addOptionsView();
        }

        dateTimePickerView = new DateTimePickerView();
        foundView = new FindTaxiFoundView();
        foundATaxiView = new FindTaxiFoundATaxiView();
        dropPinView = new FindTaxiDropPinView();

        foundView.setListener(this);
        foundView.setDatasource(model);
        dateTimePickerView.setListener(this);
        dateTimePickerView.setDatasource(model);
        foundATaxiView.setListener(this);
        foundATaxiView.setDatasource(model);
        dropPinView.setListener(this);
        dropPinView.setDatasource(model);
    }

    private void addOptionsView() {
        optionsView = new FindTaxiOptionsView();
        optionsView.setListener(FindTaxiController.this);
        optionsView.setDatasource(model);
        addFragmentv4(R.id.find_taxi_option_container, optionsView);
        model.setCurrentView(ContantValuesObject.OptionView);
    }

    private void addView() {
        addFragmentv4(R.id.find_taxi_map_container, foundView);
        addFragment(R.id.find_taxi_option_container, foundATaxiView);
        addFragment(R.id.find_taxi_option_container, dateTimePickerView);
        addFragmentv4(R.id.find_taxi_option_container, dropPinView);
        hideAllFragment();
    }

    public void hideAllFragment() {
        if (optionsView != null) {
            hideFragmentv4(optionsView);
        }
        if (foundView != null) {
            hideFragmentv4(foundView);
        }
        if (foundATaxiView != null) {
            hideFragment(foundATaxiView);
        }
        if (dropPinView != null) {
            hideFragmentv4(dropPinView);
        }
    }

    private void getListFavouriteLocationAndVehicle() {
        model.setListHistoryLocation(context);
        model.getListFavouriteLocation(context, new FindTaxiModel.onGetListFavouriteLocation() {
            @Override
            public void Success() {
                optionsView.reloadData();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });

        model.getListVehicle(context, new FindTaxiModel.onGetListVehicle() {
            @Override
            public void Success() {
                optionsView.reloadListVehicle();
                getCurrentRequest();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
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
                    model.setStatusRequest(data);
                    switch (model.getStatusRequest()) {
                        case ContantValuesObject.TaxiRequestStatusDriverSelected:
                            model.getCurrentRequest(context, new FindTaxiModel.onGetCurrentRequest() {
                                @Override
                                public void Success() {
                                    hideAllFragment();
                                    showFragment(foundATaxiView);
                                    model.setCurrentView(ContantValuesObject.DriverAcceiptView);
                                    foundATaxiView.reloadView();
                                }

                                @Override
                                public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                                    showAlertDialog(error.errorMessage).show();
                                }
                            });
                            break;
                        case ContantValuesObject.TaxiRequestStatusDrivingToPassenger:
                            startActivity(new Intent(context, WaitingTaxiController.class));
                            finishActivity();
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

    private void getCurrentRequest() {
        model.getCurrentRequest(context, new FindTaxiModel.onGetCurrentRequest() {
            @Override
            public void Success() {
                hideAllFragment();
                Log.w("Status_CurrentRequest", String.valueOf(TaxiRequestObj.getInstance().getStatus()));

                switch (TaxiRequestObj.getInstance().getStatus()) {
                    case ContantValuesObject.TaxiRequestStatusPending:
                        showFragmentv4(foundView);
                        foundView.calculateZoomAndCamera(UserObj.getInstance().getLocation());
                        model.setCurrentView(ContantValuesObject.FoundView);
                        getListDriversAround();
                        break;

                    case ContantValuesObject.TaxiRequestStatusCancelled:
                        showFragmentv4(optionsView);
                        optionsViewMapLocationChange();
                        model.setCurrentView(ContantValuesObject.OptionView);
                        break;

                    case ContantValuesObject.TaxiRequestStatusDriverSelected:
                        showFragment(foundATaxiView);
                        foundATaxiView.reloadView();
                        model.setCurrentView(ContantValuesObject.DriverAcceiptView);
                        break;
                }
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL));
                TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                hideAllFragment();
                showFragmentv4(optionsView);
                optionsViewMapLocationChange();
                model.setCurrentView(ContantValuesObject.OptionView);
            }
        });
    }

    private class GetListRequestRunable implements Runnable {

        @Override
        public void run() {
            getListDriversAround();
            handler.postDelayed(getListRequestRunable, tenSecond);
        }
    }

    private void getListDriversAround() {
        if (model.getCurrentView() == ContantValuesObject.FoundView) {
            if (TaxiRequestObj.getInstance().getRequestId() > 0) {
                model.getListDriversAround(context, new FindTaxiModel.onGetListDriversAround() {
                    @Override
                    public void Success() {
                        foundView.reloadView();
                        model.setCurrentView(ContantValuesObject.FoundView);
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        Log.e("getListDriversAround", error.errorMessage);
                    }
                });
            } else {
                Log.w("GetListDriversAround", "RequestId null");
            }
        }
    }

    @Override
    public void onIconLocationClick() {
        optionsView.reloadListNearby();
    }

    @Override
    public void onEnterDestinationClick() {
        if (model.getCurrentView() == ContantValuesObject.OptionView) {
        } else {
            onButtonCancelRequestTaxiClick();
        }
    }

    @Override
    public void onButtonCancelRequestTaxiClick() {
        model.getCurrentRequest(context, new FindTaxiModel.onGetCurrentRequest() {
            @Override
            public void Success() {
                model.cancelRequestFoundTaxi(context, new FindTaxiModel.onCancelRequestFoundTaxi() {
                    @Override
                    public void Success() {
                        foundView.clearMap();
                        TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                        TaxiRequestObj.getInstance().setRequestId(Integer.parseInt
                                (ContantValuesObject.REQUEST_ID_NULL));
                        hideAllFragment();
                        showFragmentv4(optionsView);
                        model.setCurrentView(ContantValuesObject.OptionView);
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                foundView.clearMap();
                TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL));
                showFragmentv4(optionsView);
                model.setCurrentView(ContantValuesObject.OptionView);
            }
        });
    }

    @Override
    public void onListVehicleClick() {
        optionsView.reloadListVehicle();
    }

    @Override
    public void onChooseFromLocation() {
        optionsView.transparentEnterDestination(true);
        optionsView.drawFromMarker();
    }

    @Override
    public void onChooseToLocation() {
        optionsView.transparentEnterDestination(true);
        optionsView.drawToMarker();
    }

    @Override
    public void dropPinClick() {
        hideAllFragment();
        showFragmentv4(dropPinView);
        if (UserObj.getInstance().getLocation() != null) {
            LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                    new LocationObject.onGetCompleteAddress() {
                        @Override
                        public void Success(String address) {
                            dropPinView.calculateZoomAndCamera(UserObj.getInstance().getLocation());
                            model.setFromAddress(address);
                            dropPinView.setCompleteAddress();
                        }

                        @Override
                        public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                        }
                    });
        }

    }

    @Override
    public void dropPinViewButtonBackClick() {
        hideAllFragment();
        showFragmentv4(optionsView);
        optionsView.drawFromMarker();
        optionsView.setTextFromWithDropPin();
        model.setCurrentView(ContantValuesObject.OptionView);
    }

    @Override
    public void onMapLongClick() {
        dropPinView.drawDropPinMarker();
        dropPinView.setCompleteAddress();
    }

    @Override
    public void chooseDateTimePickUpFinish() {
        optionsView.setDateTimeForButtonPickUp(true);
    }

    @Override
    public void optionsViewMapLocationChange() {
        if (UserObj.getInstance().getLocation() != null) {
            if (model.isZoomMapFirstTime()) {
                optionsView.calculateZoomAndCamera(UserObj.getInstance().getLocation());
            } else {
                Log.w("Zoom_Map_First_Time", "false");
            }
        } else {
            Log.w("User_Location", "null");
        }
    }

    @Override
    public void onButtonDateTimeClick() {
        dateTimePickerView.showDateTimePicker();
    }

    @Override
    public void onButtonFindTaxiClick() {
        optionsView.setEnabledButtonFindTaxi(false);
        model.findTaxi(context, new FindTaxiModel.onFindTaxi() {
            @Override
            public void Success() {
                model.getCurrentRequest(context, new FindTaxiModel.onGetCurrentRequest() {
                    @Override
                    public void Success() {
                        model.getListDriversAround(context, new FindTaxiModel.onGetListDriversAround() {
                            @Override
                            public void Success() {
                                hideAllFragment();
                                showFragmentv4(foundView);
                                foundView.calculateZoomAndCamera(UserObj.getInstance().getLocation());
                                foundView.reloadView();
                                model.setCurrentView(ContantValuesObject.FoundView);
                                model.saveFromToLocation();
                            }

                            @Override
                            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                                Log.e("getListDriversAround", error.errorMessage);
                            }
                        });
                        optionsView.setEnabledButtonFindTaxi(true);
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        optionsView.setEnabledButtonFindTaxi(true);
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                optionsView.setEnabledButtonFindTaxi(true);
                showAlertDialog(error.errorMessage).show();
                if (error.errorCode == Error.DATA_NULL &&
                        error.errorMessage.equalsIgnoreCase(getString(R.string.you_did_not_choose_time))) {
                    optionsView.chooseButtonNow();
                }
            }
        });
    }

    @Override
    public void onFoundATaxiButtonOkClick() {
        foundATaxiView.setEnableButtonOk(false);
        model.customerConfirm(context, new FindTaxiModel.onCustomerConfirm() {
            @Override
            public void Success() {
                foundATaxiView.setEnableButtonOk(true);
                if (model.getMethodPickupTime() == ContantValuesObject.PickupTimeDateTime) {
                    hideAllFragment();
                    showFragmentv4(optionsView);
                    model.setCurrentView(ContantValuesObject.OptionView);
                    getListFutureBooking(new OnGetListFutureBooking() {
                        @Override
                        public void Success() {
                            ReceiptObject requestObj = listFutureBooking.get(listFutureBooking.size() - 1);
                            String title = getString(R.string.booking_a_taxi)
                                    + "\n" + requestObj.getFromAddress();

                            String description = getString(R.string.you_booking_a_taxi)
                                    + " " + requestObj.getResponseDriver().getCar().getNumber()
                                    + " " + getString(R.string.to).toLowerCase()
                                    + " " + requestObj.getToAddress();

                            TimeObject.addReminderInCalendar(context, title, description,
                                    requestObj.getPickup_time());
                        }

                        @Override
                        public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                            showAlertDialog(error.errorMessage).show();
                        }
                    });
                } else {
                    startActivity(new Intent(context, WaitingTaxiController.class));
                    finishActivity();
                }
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                foundATaxiView.setEnableButtonOk(true);
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onFoundATaxiButtonCancelClick() {
        foundATaxiView.setEnableButtonCancel(false);
        model.cancelRequestDriver(context, new FindTaxiModel.onCancelRequestTaxi() {

            @Override
            public void Success() {
                foundATaxiView.setEnableButtonCancel(true);
                hideAllFragment();
                showFragmentv4(foundView);
                foundView.calculateZoomAndCamera();
                model.setCurrentView(ContantValuesObject.FoundView);
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                foundATaxiView.setEnableButtonCancel(true);
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onDrawDriverMaker(CarDriverObj carDriver) {
        model.addDriverMarker(context, carDriver, new FindTaxiModel.OnGetImageBitmap() {
            @Override
            public void Success() {
                foundView.addDriverMarker(model.getMarkerOptionsDriverMarker());
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void clickFavouriteLocation() {
        try {
            if (model.getListFavouriteLocation().size() > 0) {

            } else {
                showAlertDialog(context.getString(R.string.you_did_not_save_any_favourite_location)).show();
            }
        } catch (Exception e) {
            showAlertDialog(context.getString(R.string.you_did_not_save_any_favourite_location)).show();
        }
    }

    @Override
    public void onCreateOptionsViewFinish() {
        getListFavouriteLocationAndVehicle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        handler.removeCallbacks(getListRequestRunable);
        model = null;
        optionsView = null;
        dateTimePickerView = null;
        foundView = null;
        foundATaxiView = null;
        dropPinView = null;
    }

    @Override
    public void onBackPressed() {

    }
}