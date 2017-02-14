package bhtech.com.cabbytaxi.InTaxi;


import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.PlusShare;

import java.util.List;

import bhtech.com.cabbytaxi.EndTaxi.EndTaxiController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;

public class InTaxiController extends SlidingMenuController implements OnInTaxiDialogListerner,
        InTaxiView.OnFragmentInteractionListener, InTaxiInterface.Listener {

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
                    model.setStatusRequest(context, data);
                    if (model.getStatusRequest() == ContantValuesObject.TaxiRequestStatusPaid) {
                        startActivity(new Intent(context, EndTaxiController.class));
                        finishActivity();
                    }
                }
            }
        }
    };
    private Context context;
    private InTaxiView inTaxiView;
    private InTaxiShareLocationDialog shareLocationDialog;
    private InTaxiModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getLayoutInflater().inflate(R.layout.activity_in_taxi_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.driving_to_destination));
        inTaxiView = new InTaxiView();
        inTaxiView.setListener(this);

        shareLocationDialog = new InTaxiShareLocationDialog(this);
        model = new InTaxiModel(context);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.intaxi_controller, inTaxiView).commit();
        } else {
            //Do nothing
        }
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
    public void OnCreateViewDone() {
        model.getCurrentRequest(new InTaxiModel.onGetCurrentRequest() {
            @Override
            public void Success() {
                inTaxiView.updateDistance(model.getDistance());
                inTaxiView.updateTimeETA(model.getEstimatedTime());
                inTaxiView.updateAddress(model.getAddress());
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void OnButtonNavigationViewClick() {
        try {
            LatLng latLng = TaxiRequestObj.getInstance().getToLocation();
            startActivity(LocationObject.showNavigationOnGoogleMap(latLng));
        } catch (Exception e) {
            showAlertDialog(context.getString(R.string.your_google_map_is_out_of_date)).show();
        }
    }

    @Override
    public void OnButtonShareTaxiClick() {
        shareLocationDialog.show();
    }

    @Override
    public void OnClickShareFacebook() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String location = address;

                        String url = "https://www.google.com/maps/place/" + location.replaceAll(" ", "+");

                        shareIntent.putExtra(Intent.EXTRA_TEXT, model.getAddress());
                        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                        PackageManager pm = getPackageManager();
                        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                        boolean facebookInstalled = false;
                        for (final ResolveInfo app : activityList) {
                            if ((app.activityInfo.name).contains("facebook")) {
                                facebookInstalled = true;
                                final ActivityInfo activity = app.activityInfo;
                                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                shareIntent.setComponent(name);
                                startActivityForResult(shareIntent, 0);
                                break;
                            }
                        }
                        if (!facebookInstalled) {
                            showAlertDialog(context.getString(R.string.facebook_not_installed)).show();
                        }
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });

    }

    @Override
    public void OnClickShareWhatsApp() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        PackageManager pm = getPackageManager();
                        try {
                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");
                            String text = address;
                            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                            waIntent.setPackage("com.whatsapp");
                            waIntent.putExtra(Intent.EXTRA_TEXT, text);
                            startActivity(Intent.createChooser(waIntent, "Share WhatsApp"));
                        } catch (PackageManager.NameNotFoundException e) {
                            showAlertDialog(context.getString(R.string.whatsapp_not_installed)).show();
                        }
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });

    }

    @Override
    public void OnClickShareTwitter() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "My Location: " + address);
                        PackageManager pm = getPackageManager();
                        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
                        boolean twitterInstalled = false;
                        for (final ResolveInfo app : activityList) {
                            if ((app.activityInfo.name).contains("twitter")) {
                                twitterInstalled = true;
                                final ActivityInfo activity = app.activityInfo;
                                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                shareIntent.setComponent(name);
                                startActivityForResult(shareIntent, 0);
                                break;
                            }
                        }
                        if (!twitterInstalled) {
                            showAlertDialog(context.getString(R.string.twitter_not_installed)).show();
                        }
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
    }

    @Override
    public void OnClickShareGooglePlus() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        Intent shareIntent = new PlusShare.Builder(context)
                                .setType("text/plain")
                                .setText("My Location: " + address)
                                .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                                .getIntent();
                        startActivityForResult(shareIntent, 0);
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
    }

    @Override
    public void OnClickShareEmail() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        Intent gmailIntent = new Intent();
                        gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Location");
                        gmailIntent.putExtra(Intent.EXTRA_TEXT, address);
                        try {
                            startActivityForResult(gmailIntent, 0);
                        } catch (ActivityNotFoundException ex) {
                            showAlertDialog(context.getString(R.string.gmail_not_installed)).show();
                        }
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });

    }

    @Override
    public void OnClickShareMessage() {
        LocationObject.getCompleteAddressString(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCompleteAddress() {
                    @Override
                    public void Success(String address) {
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.putExtra("sms_body", address);
                        sendIntent.setType("vnd.android-dir/mms-sms");
                        startActivityForResult(sendIntent, 0);
                    }

                    @Override
                    public void Failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });

    }

    @Override
    public void OnClickShareCancel() {
        shareLocationDialog.dismiss();
    }

    @Override
    public void onButtonEndTripClick() {
        startActivity(new Intent(this, EndTaxiController.class));
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}