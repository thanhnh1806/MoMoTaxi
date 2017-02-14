package bhtech.com.cabbydriver.EnterPinCode;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.ChooseCar.ChooseCarController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.services.GetLocationService;

public class EnterCodeController extends BaseActivity implements EnterCodeInterface.Listener {
    private Context context;
    private EnterCodeView enterCodeView;
    private EnterCodeSuccessView successView;
    private EnterWrongCodeDialog dialog;
    private EnterCodeModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = EnterCodeController.this;
        super.onCreate(savedInstanceState);
        enterCodeView = new EnterCodeView();
        successView = new EnterCodeSuccessView();
        dialog = new EnterWrongCodeDialog(this);
        model = new EnterCodeModel(context);

        enterCodeView.setListener(this);
        enterCodeView.setDatasource(model);
        successView.setListener(this);
        dialog.setListener(this);

        if (savedInstanceState == null) {
            addFragment(R.id.base_activity_container, enterCodeView);
            addFragment(R.id.base_activity_container, successView);
            hideFragment(successView);
        } else {
            //Do nothing
        }

        if (model.checkAutoLogin()) {
            checkPinCode();
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasAccessNetworkPermission = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            int hasInternetPermission = checkSelfPermission(Manifest.permission.INTERNET);
            int hasWriteExternalStorePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasAccessCoarsePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCallPhonePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
            int hasSMSPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
            int hasReadCalendarPermission = checkSelfPermission(Manifest.permission.READ_CALENDAR);
            int hasWriteCalendarPermission = checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
            int hasWakeLockPermission = checkSelfPermission(Manifest.permission.WAKE_LOCK);
            int hasVibratePermission = checkSelfPermission(Manifest.permission.VIBRATE);
            int hasReadGServicesPermission = checkSelfPermission("com.google.android.providers.gsf.permission.READ_GSERVICES");
            int hasReceivePermission = checkSelfPermission("com.google.android.c2dm.permission.RECEIVE");

            model.permissions = new ArrayList<>();

            if (hasAccessNetworkPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if (hasInternetPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.INTERNET);
            }

            if (hasAccessCoarsePermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (hasWriteExternalStorePermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.CALL_PHONE);
            }

            if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.SEND_SMS);
            }

            if (hasReadCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.READ_CALENDAR);
            }

            if (hasWriteCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.WRITE_CALENDAR);
            }

            if (hasWakeLockPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.WAKE_LOCK);
            }

            if (hasVibratePermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add(Manifest.permission.VIBRATE);
            }

            if (hasReadGServicesPermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add("com.google.android.providers.gsf.permission.READ_GSERVICES");
            }

            if (hasReceivePermission != PackageManager.PERMISSION_GRANTED) {
                model.permissions.add("com.google.android.c2dm.permission.RECEIVE");
            }

            if (!model.permissions.isEmpty()) {
                requestPermissions(model.permissions.toArray(new String[model.permissions.size()]), 1);
            }
        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
            } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                model.isUserAcceptAllPermission(false);
            }
        }

        if (!model.isUserAcceptAllPermission()) {
            showAlertDialog(context.getString(R.string.application_can_not_work_without_some_permission))
                    .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            checkPermission();
                        }
                    })
                    .setCancelable(false).show();
        }
    }

    private void hideAllFragment() {
        hideFragment(enterCodeView);
        hideFragment(successView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (successView.isVisible()) {
            PhoneObject.hiddenSofwareKeyboard(context, successView.btnOk);
            enterCodeView.etEnterCode.setFocusable(false);
            enterCodeView.etEnterCode.setEnabled(false);
        }
    }

    @Override
    public void checkPinCode() {
        if (model.isEnableButtonLogin()) {
            model.setEnableButtonLogin(false);
            model.checkPinCode(context, new EnterCodeModel.OnCheckPinCode() {
                @Override
                public void Success() {
                    PhoneObject.hiddenSofwareKeyboard(EnterCodeController.this);
                    if (CarDriverObj.getInstance().getDriver().getFirst_login() == ContantValuesObject.FIRST_LOGIN) {
                        hideAllFragment();
                        showFragment(successView);
                    } else {
                        checkDriverCarId();
                    }
                }

                @Override
                public void Failure(ErrorObj error) {
                    dialog.show();
                    model.setPinCode("");
                    model.setEnableButtonLogin(true);
                }
            });
        }
    }

    private void checkDriverCarId() {
        int driverCarId = CarDriverObj.getInstance().getObjectID();
        Log.e("driverCar", String.valueOf(driverCarId));
        if (driverCarId == ContantValuesObject.CAR_DRIVER_NULL) {
            startActivity(new Intent(EnterCodeController.this, ChooseCarController.class));
            finishActivity();
        } else {
            Log.w("checkDriverCarId", "startService");
            startService(new Intent(context, GetLocationService.class));
            if (TaxiRequestObj.getInstance().getRequestId() > 0) {
                String requestId = String.valueOf(TaxiRequestObj.getInstance().getRequestId());
                SharedPreference.set(context, ContantValuesObject.REQUEST_ID, requestId);
            }
            checkDriverCarStatus();
        }
    }

    @Override
    public void OnButtonOkPinCodeSuccessClick() {
        checkDriverCarId();
    }

    @Override
    public void OnButtonClosePinCodeWrongDialogClick() {
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(context.getString(R.string.quite_app_msg))
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishActivity();
                    }
                })
                .show();
    }
}
