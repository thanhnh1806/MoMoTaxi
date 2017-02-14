package bhtech.com.cabbytaxi.WaitTaxi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.ByteArrayOutputStream;

import bhtech.com.cabbytaxi.FindTaxi.FindTaxiController;
import bhtech.com.cabbytaxi.InTaxi.InTaxiController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.StringObject;

public class WaitingTaxiController extends SlidingMenuController implements WaitTaxiInterface.Listener {
    private Context context;
    private WaitingTaxiView waitingTaxiView;
    private WaitTaxiModel model;
    private Handler handler;
    private WaitTaxiRunable waitTaxiRunable;
    int tenSecond = 10000;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(ContantValuesObject.GCM_INTENT_FILTER_ACTION)) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                String messageType = gcm.getMessageType(intent);
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                    Log.e("GoogleCloudMessaging", "MESSAGE_TYPE_SEND_ERROR");
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                    Log.e("GoogleCloudMessaging", "MESSAGE_TYPE_DELETED");
                } else {
                    String msg = intent.getStringExtra("message");
                    String data = intent.getStringExtra("data");
                    Log.e("msg", msg);
                    Log.e("data", data);

                    model.setStatusRequest(data);
                    if (model.getStatusRequest() == ContantValuesObject.TaxiRequestStatusWithPassenger) {
                        startActivity(new Intent(context, InTaxiController.class));
                        finish();
                    } else if (model.getStatusRequest() == ContantValuesObject.TaxiRequestStatusDrivingToPassenger) {
                        waitingTaxiView.reloadView();
                    } else {
                        handler.removeCallbacks(waitTaxiRunable);
                        waitingTaxiView.reloadView();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_waiting_taxi_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.cab_request));
        context = WaitingTaxiController.this;
        model = new WaitTaxiModel(context);

        waitingTaxiView = new WaitingTaxiView();
        waitingTaxiView.setListener(this);
        waitingTaxiView.setDatabase(model);

        if (savedInstanceState == null) {
            addFragmentv4(R.id.other_container, waitingTaxiView);
        }

        showWaitingTime();
        model.getCurrentRequest(new WaitTaxiModel.onGetCurrentRequest() {
            @Override
            public void Success() {
                waitingTaxiView.reloadView();
                waitTaxi();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {

            }
        });

        waitTaxiRunable = new WaitTaxiRunable();
        handler = new Handler();
        handler.removeCallbacks(waitTaxiRunable);
        handler.postDelayed(waitTaxiRunable, tenSecond);
    }

    private void showWaitingTime() {
        final int start = model.getNumberSecondInDay();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted() && model.getStatusRequest() !=
                        ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger) {
                    try {
                        int current = model.getNumberSecondInDay();
                        final int minute = (current - start) / 60;
                        if (minute == 10) {
                            onBackPressed();
                        }
                        final int second = (current - start) % 60;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                waitingTaxiView.updateWaitingTime(minute, second);
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class WaitTaxiRunable implements Runnable {

        @Override
        public void run() {
            if (model.getStatusRequest() != ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger) {
                waitTaxi();
                handler.postDelayed(waitTaxiRunable, tenSecond);
            }
        }
    }

    private void waitTaxi() {
        model.waitTaxi(new WaitTaxiModel.onWaitTaxi() {
            @Override
            public void Success() {
                waitingTaxiView.reloadMapView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onGoogleMapReady() {

    }

    @Override
    public void onButtonCancelClick() {
        waitingTaxiView.setEnableButtonCancel(false);
        model.cancelRequestTaxi(context, new WaitTaxiModel.onCancelRequestTaxi() {
            @Override
            public void Success() {
                startActivity(new Intent(context, FindTaxiController.class));
                finishActivity();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                waitingTaxiView.setEnableButtonCancel(true);
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    private static final int REQUEST_IMAGE_CAPTURE = 100;

    @Override
    public void onButtonCameraClick() {
        Intent i = PhoneObject.takeCameraPicture(context);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onButtonMessagesClick() {
        try {
            String phoneNumber = model.getDriverPhoneNumber();
            if (!StringObject.isNullOrEmpty(phoneNumber)) {
                startActivity(PhoneObject.sendSMS(phoneNumber));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonCallClick() {
        try {
            String phoneNumber = model.getDriverPhoneNumber();
            if (!StringObject.isNullOrEmpty(phoneNumber)) {
                startActivity(PhoneObject.makePhoneCall(phoneNumber));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                //imageView.setImageBitmap(resize);
                //TODO get image from Camera send to Driver
            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }
    }

    @Override
    public void onBackPressed() {
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
    public void onButtonTaxiArrivedClick() {
        waitingTaxiView.reloadView();
    }

    @Override
    public void onButtonInTaxiClick() {
        startActivity(new Intent(context, InTaxiController.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        handler.removeCallbacks(waitTaxiRunable);
    }
}
