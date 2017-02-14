package bhtech.com.cabbytaxi.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.About.AboutCabbyController;
import bhtech.com.cabbytaxi.CreditCardSetting.CreditCardSettingController;
import bhtech.com.cabbytaxi.DeveloperInformation.DeveloperInformationController;
import bhtech.com.cabbytaxi.FavouriteDriver.FavouriteDriverController;
import bhtech.com.cabbytaxi.FavouriteLocation.FavouriteLocationController;
import bhtech.com.cabbytaxi.FindTaxi.FindTaxiController;
import bhtech.com.cabbytaxi.FutureBooking.FutureBookingController;
import bhtech.com.cabbytaxi.History.HistoryController;
import bhtech.com.cabbytaxi.Login.LoginController;
import bhtech.com.cabbytaxi.MyProfile.MyProfileController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Receipt.ReceiptController;
import bhtech.com.cabbytaxi.SNSSetting.SNSSettingController;
import bhtech.com.cabbytaxi.SendFeedback.SendFeedbackController;
import bhtech.com.cabbytaxi.SupportClass.BaseActivity;
import bhtech.com.cabbytaxi.TermOfUse.TermOfUseDialog;
import bhtech.com.cabbytaxi.TermOfUse.TermOfUseInterface;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

public class SlidingMenuController extends BaseActivity implements View.OnClickListener, TermOfUseInterface {
    public static final int FUTURE_BOOKING_MENU_INDEX = 3;
    private Context context;
    protected FrameLayout mFrameSlidingMenu;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout smRequestTaxi, smMyProfile, smHistory, smFutureBooing, smFavouriteLocation,
            smFavouriteDriver, smReceipt, smCreditCardSetting, smSendFeedback, smAboutCabby,
            smTermOfUser, smSNSSetting, toolbar, ivToolBarMenu, smDeveloperInformation, smLogOut;
    private TextView tvToolBarTitle, tvBadgeNumber, tvBadgeNumberFutureBooking;
    private TermOfUseDialog termOfUserDialog;
    protected ArrayList<ReceiptObject> listFutureBooking = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingmenu_controller);
        context = SlidingMenuController.this;
        termOfUserDialog = new TermOfUseDialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        termOfUserDialog.setListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mFrameSlidingMenu = (FrameLayout) findViewById(R.id.slide_menu_container);
        toolbar = (FrameLayout) findViewById(R.id.toolbar);
        ivToolBarMenu = (FrameLayout) findViewById(R.id.ivToolBarMenu);
        tvToolBarTitle = (TextView) findViewById(R.id.tvToolBarTitle);
        tvBadgeNumber = (TextView) findViewById(R.id.tvBadgeNumber);
        tvBadgeNumberFutureBooking = (TextView) findViewById(R.id.tvBadgeNumberFutureBooking);
        smRequestTaxi = (FrameLayout) findViewById(R.id.smRequestTaxi);
        smMyProfile = (FrameLayout) findViewById(R.id.smMyProfile);
        smHistory = (FrameLayout) findViewById(R.id.smHistory);
        smFutureBooing = (FrameLayout) findViewById(R.id.smFutureBooing);
        smFavouriteLocation = (FrameLayout) findViewById(R.id.smFavouriteLocation);
        smFavouriteDriver = (FrameLayout) findViewById(R.id.smFavouriteDriver);
        smReceipt = (FrameLayout) findViewById(R.id.smReceipt);
        smCreditCardSetting = (FrameLayout) findViewById(R.id.smCreditCardSetting);
        smSNSSetting = (FrameLayout) findViewById(R.id.smSNSSetting);
        smSendFeedback = (FrameLayout) findViewById(R.id.smSendFeedback);
        smAboutCabby = (FrameLayout) findViewById(R.id.smAboutCabby);
        smTermOfUser = (FrameLayout) findViewById(R.id.smTermOfUser);
        smLogOut = (FrameLayout) findViewById(R.id.smLogOut);
        smDeveloperInformation = (FrameLayout) findViewById(R.id.smDeveloperInformation);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.icon_drawer,
                R.string.about_cabby, R.string.about_cabby) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                PhoneObject.hiddenSofwareKeyboard(context, ivToolBarMenu);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);

        ivToolBarMenu.setOnClickListener(this);
        smRequestTaxi.setOnClickListener(this);
        smMyProfile.setOnClickListener(this);
        smHistory.setOnClickListener(this);
        smFutureBooing.setOnClickListener(this);
        smFavouriteLocation.setOnClickListener(this);
        smFavouriteDriver.setOnClickListener(this);
        smReceipt.setOnClickListener(this);
        smCreditCardSetting.setOnClickListener(this);
        smSNSSetting.setOnClickListener(this);
        smSendFeedback.setOnClickListener(this);
        smAboutCabby.setOnClickListener(this);
        smTermOfUser.setOnClickListener(this);
        smLogOut.setOnClickListener(this);
        smDeveloperInformation.setOnClickListener(this);

        getListFutureBooking(new OnGetListFutureBooking() {
            @Override
            public void Success() {

            }

            @Override
            public void Failure(Error error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        termOfUserDialog.dismiss();
        switch (v.getId()) {
            case R.id.ivToolBarMenu:
                onClickToolBarMenu();
                break;
            default:
                if (hasUserLogin()) {
                    switch (v.getId()) {
                        case R.id.smRequestTaxi:
                            TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
                                @Override
                                public void Success() {
                                    checkUserStatus();
                                }

                                @Override
                                public void Failure(Error error) {
                                    if (error.errorCode == Error.REQUEST_ID_NULL) {
                                        startActivity(new Intent(context, FindTaxiController.class));
                                        finishActivity();
                                    } else {
                                        showAlertDialog(error.errorMessage).show();
                                    }
                                }
                            });
                            break;
                        case R.id.smMyProfile:
                            startActivity(new Intent(context, MyProfileController.class));
                            break;
                        case R.id.smHistory:
                            startActivity(new Intent(context, HistoryController.class));
                            break;
                        case R.id.smFutureBooing:
                            startActivity(new Intent(context, FutureBookingController.class));
                            break;
                        case R.id.smFavouriteLocation:
                            startActivity(new Intent(context, FavouriteLocationController.class));
                            break;
                        case R.id.smFavouriteDriver:
                            startActivity(new Intent(context, FavouriteDriverController.class));
                            break;
                        case R.id.smReceipt:
                            startActivity(new Intent(context, ReceiptController.class));
                            break;
                        case R.id.smCreditCardSetting:
                            startActivity(new Intent(context, CreditCardSettingController.class));
                            break;
                        case R.id.smSNSSetting:
                            startActivity(new Intent(context, SNSSettingController.class));
                            break;
                        case R.id.smSendFeedback:
                            startActivity(new Intent(context, SendFeedbackController.class));
                            break;
                        case R.id.smAboutCabby:
                            startActivity(new Intent(context, AboutCabbyController.class));
                            break;
                        case R.id.smTermOfUser:
                            onClickToolBarMenu();
                            showDialogTermOfUser();
                            break;
                        case R.id.smLogOut:
                            userLogOut();
                            break;
                        case R.id.smDeveloperInformation:
                            startActivity(new Intent(context, DeveloperInformationController.class));
                            break;
                    }

                } else {
                    showAlertDialog(getString(R.string.please_login)).show();
                }
        }
    }

    private void userLogOut() {
        bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            UserObj.getInstance().logout(context, new UserObj.onUserLogOut() {
                @Override
                public void success() {
                    startActivity(new Intent(context, LoginController.class));
                    finishActivity();
                }

                @Override
                public void failure(bhtech.com.cabbytaxi.object.Error error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            showAlertDialog(error.errorMessage).show();
        }
    }

    protected void showDialogTermOfUser() {
        termOfUserDialog.show();
    }

    private boolean hasUserLogin() {
        String username = SharedPreference.get(context, ContantValuesObject.AUTHTOKEN, "");
        return !StringObject.isNullOrEmpty(username);
    }

    public void onClickToolBarMenu() {
        PhoneObject.hiddenSofwareKeyboard(context, ivToolBarMenu);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    protected void updateToolbarTitle(String title) {
        tvToolBarTitle.setText(title);
    }

    @Override
    public void OnButtonOkTermOfUseClick() {
        termOfUserDialog.dismiss();
    }

    protected void updateBadgeNumberOfFutureBooking(int badgeNumer) {
        updateBadgeNumber(FUTURE_BOOKING_MENU_INDEX, badgeNumer);
    }

    private void updateBadgeNumber(int menuIndex, int badgeNumber) {
        tvBadgeNumber.setText(String.valueOf(badgeNumber));
        tvBadgeNumberFutureBooking.setText(String.valueOf(badgeNumber));
        if (badgeNumber > 0) {
            tvBadgeNumber.setVisibility(View.VISIBLE);
            tvBadgeNumberFutureBooking.setVisibility(View.VISIBLE);
        } else {
            tvBadgeNumber.setVisibility(View.GONE);
            tvBadgeNumberFutureBooking.setVisibility(View.GONE);
        }
    }

    public interface OnGetListFutureBooking {
        void Success();

        void Failure(Error error);
    }

    public void getListFutureBooking(final OnGetListFutureBooking onFinish) {
        final bhtech.com.cabbytaxi.object.Error error = new bhtech.com.cabbytaxi.object.Error();
        listFutureBooking = new ArrayList<>();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.HISTORY_ENDPOINT, NetworkServices.GET,
                    headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONArray results = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    for (int i = 0; i < results.length(); i++) {
                                        JSONObject object = results.getJSONObject(i);
                                        ReceiptObject receipt = new ReceiptObject();
                                        receipt.parseJsonToObject(object);
                                        if (receipt.getStatus() == ContantValuesObject.TaxiRequestStatusFutureBooking) {
                                            listFutureBooking.add(receipt);
                                        }
                                    }
                                    updateBadgeNumberOfFutureBooking(listFutureBooking.size());
                                    onFinish.Success();
                                } else {
                                    error.setError(bhtech.com.cabbytaxi.object.Error.INVALID_INPUTS);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            if (onFinish != null) {
                onFinish.Failure(error);
            }
        }
    }
}
