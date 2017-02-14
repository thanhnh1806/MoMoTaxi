package bhtech.com.cabbytaxi.Login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.FindTaxi.FindTaxiController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.RegisterController;
import bhtech.com.cabbytaxi.SupportClass.BaseActivity;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.SocialNetworkObj;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;

public class LoginController extends BaseActivity implements LoginInterface.Listener {
    private Context context;
    private LoginView loginView;
    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);
        context = LoginController.this;
        loginModel = new LoginModel(context);

        loginView = new LoginView();
        loginView.setListener(this);
        loginView.setDatasource(loginModel);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.login_container, loginView).commit();
        }

        if (loginModel.isRememberMe(context)) {
            if (loginModel.hasAccountLogin(context)) {
                loginModel.getUserObj(context, new LoginModel.OnGetUserObj() {
                    @Override
                    public void Success() {
                        getCurrentRequest();
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
            }
        } else {

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

            loginModel.permissions = new ArrayList<>();

            if (hasAccessNetworkPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if (hasInternetPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.INTERNET);
            }

            if (hasAccessCoarsePermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (hasWriteExternalStorePermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.CALL_PHONE);
            }

            if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.SEND_SMS);
            }

            if (hasReadCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.READ_CALENDAR);
            }

            if (hasWriteCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.WRITE_CALENDAR);
            }

            if (hasWakeLockPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.WAKE_LOCK);
            }

            if (hasVibratePermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add(Manifest.permission.VIBRATE);
            }

            if (hasReadGServicesPermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add("com.google.android.providers.gsf.permission.READ_GSERVICES");
            }

            if (hasReceivePermission != PackageManager.PERMISSION_GRANTED) {
                loginModel.permissions.add("com.google.android.c2dm.permission.RECEIVE");
            }

            if (!loginModel.permissions.isEmpty()) {
                requestPermissions(loginModel.permissions.toArray(new String[loginModel.permissions.size()]),
                        ContantValuesObject.REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
            } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                loginModel.isUserAcceptAllPermission(false);
            }
        }

        if (!loginModel.isUserAcceptAllPermission()) {
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

    @Override
    public void onButtonLoginClick() {
        if (NetworkObject.isNetworkConnect(context)) {
            if (loginModel.isButtonLoginEnable()) {
                loginModel.setButtonLoginEnable(false);
                loginModel.login(context, new LoginModel.OnFinishExecute() {
                    @Override
                    public void onFinish(boolean result) {
                        if (result) {
                            getCurrentRequest();
                        } else {
                            showAlertDialog(getString(R.string.login_error))
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    loginModel.setButtonLoginEnable(true);
                                                }
                                            })
                                    .show();
                        }
                    }
                });
            }
        } else {
            showAlertDialog(getString(R.string.please_check_your_network)).show();
        }
    }

    @Override
    public void onForgotPasswordClick() {
        startActivity(new Intent(context, ForgotPasswordController.class));
        finish();
    }

    private void getCurrentRequest() {
        if (loginModel.getCurrentRequestId() > Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL)) {
            TaxiRequestObj.getInstance().setRequestId(loginModel.getCurrentRequestId());
            loginModel.getCurrentRequest(context, new LoginModel.OnGetCurrentRequest() {
                @Override
                public void Success() {
                    checkUserStatus();
                }

                @Override
                public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                    if (error.errorCode == Error.REQUEST_ID_NULL) {
                        TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL));
                        TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                        startActivity(new Intent(context, FindTaxiController.class));
                        finishActivity();
                    } else {
                        showAlertDialog(error.errorMessage).show();
                    }
                }
            });
        } else {
            TaxiRequestObj.getInstance().setRequestId(Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL));
            TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
            startActivity(new Intent(context, FindTaxiController.class));
            finishActivity();
        }
    }

    @Override
    public void onButtonLoginFacebookClick() {
        if (NetworkObject.isNetworkConnect(context)) {
            if (loginModel.isButtonLoginEnable()) {
                loginModel.setButtonLoginEnable(false);
                loginModel.loginFacebook(context, new LoginModel.OnLoginFacebook() {
                    @Override
                    public void Success() {
                        getCurrentRequest();
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        showAlertDialog(getString(R.string.login_error))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                loginModel.setButtonLoginEnable(true);
                                            }
                                        })
                                .show();
                    }
                });
            }
        } else {
            showAlertDialog(getString(R.string.please_check_your_network)).show();
        }
    }

    @Override
    public void onButtonLoginTwitterClick() {
        if (NetworkObject.isNetworkConnect(context)) {
            if (loginModel.isButtonLoginEnable()) {
                loginModel.setButtonLoginEnable(false);
                loginModel.loginTwitter(context, new LoginModel.OnLoginTwitter() {
                    @Override
                    public void Success() {
                        getCurrentRequest();
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        showAlertDialog(getString(R.string.login_error))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                loginModel.setButtonLoginEnable(true);
                                            }
                                        })
                                .show();
                    }
                });
            }
        } else {
            showAlertDialog(getString(R.string.please_check_your_network)).show();
        }
    }

    @Override
    public void onButtonLoginGooglePlusClick() {
        if (loginModel.isButtonLoginEnable()) {
            loginModel.setButtonLoginEnable(false);
            Intent signInIntent = SocialNetworkObj.getGoogleSignInIntent(context, this);
            startActivityForResult(signInIntent, SocialNetworkObj.GOOGLE_PLUS_SIGN_IN);
        }
    }

    @Override
    public void onButtonSignUpClick() {
        startActivity(new Intent(context, RegisterController.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SocialNetworkObj.GOOGLE_PLUS_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                UserObj.getInstance().setgUserId(acct.getId());
                UserObj.getInstance().setgEmail(acct.getEmail());
                loginModel.loginGooglePlus(context, new LoginModel.OnLoginGooglePlus() {
                    @Override
                    public void Success() {
                        getCurrentRequest();
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        showAlertDialog(getString(R.string.login_error)).setCancelable(false)
                                .setPositiveButton(getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                loginModel.setButtonLoginEnable(true);
                                            }
                                        })
                                .show();
                    }
                });
            } else {
                loginModel.setButtonLoginEnable(true);
            }
        } else {
            loginModel.setButtonLoginEnable(true);
        }
    }
}
