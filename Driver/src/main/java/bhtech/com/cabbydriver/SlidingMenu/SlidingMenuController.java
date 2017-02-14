package bhtech.com.cabbydriver.SlidingMenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bhtech.com.cabbydriver.AboutUs.AboutCabbyController;
import bhtech.com.cabbydriver.Alert.AlertController;
import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.BaseModelInterface;
import bhtech.com.cabbydriver.CarStatus.CarStatusController;
import bhtech.com.cabbydriver.ChooseCar.ChooseCarController;
import bhtech.com.cabbydriver.DeveloperInformation.DeveloperInformationController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.FinishWork.FinishWorkController;
import bhtech.com.cabbydriver.MyProfile.MyProfileController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.ReportIncident.ReportIncidentController;
import bhtech.com.cabbydriver.Sales.SalesController;
import bhtech.com.cabbydriver.TermsOfUse.TermOfUseInterface;
import bhtech.com.cabbydriver.TermsOfUse.TermOfUseView;
import bhtech.com.cabbydriver.TermsOfUse.TermsOfUseController;
import bhtech.com.cabbydriver.TripHistory.TripHistoryController;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.DriverCompanyObj;
import bhtech.com.cabbydriver.object.DriverObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class SlidingMenuController extends BaseActivity implements View.OnClickListener, TermOfUseInterface {
    public static final int ALERT_MENU_INDEX = 5;

    private Context context;
    private SlidingMenuModel model;
    protected DrawerLayout drawer;
    protected ListView mDrawerList;
    protected SlidingMenuAdapter adapter;
    protected ArrayList<NavDrawerItem> navDrawerItems;
    protected ArrayList<TaxiRequestObj> futureBookings;
    protected ActionBarDrawerToggle mDrawerToggle;

    protected TypedArray navMenuIcons;
    protected String[] navMenuTitles;

    protected LinearLayout mLinearSlidingMenu;
    private FrameLayout toolbar;
    protected FrameLayout mFrameSlidingMenu, ivToolBarMenu;

    private TextView tvToolBarTitle;
    private TextView tvBadgeNumber;

    private TermOfUseView termOfUserDialog;

    protected void setView(Fragment view, String title) {
        addFragment(R.id.content_frame, view);
        updateToolbarTitle(title);
    }

    protected void setView(android.support.v4.app.Fragment view, String title) {
        addFragmentv4(R.id.map_container, view);
        updateToolbarTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_menu_list);
        context = SlidingMenuController.this;

        toolbar = (FrameLayout) findViewById(R.id.toolbar);
        tvBadgeNumber = (TextView) toolbar.findViewById(R.id.tvBadgeNumber);
        model = new SlidingMenuModel(context);
        ivToolBarMenu = (FrameLayout) findViewById(R.id.ivToolBarMenu);
        tvToolBarTitle = (TextView) findViewById(R.id.tvToolBarTitle);
        mLinearSlidingMenu = (LinearLayout) findViewById(R.id.content_linear);
        mFrameSlidingMenu = (FrameLayout) findViewById(R.id.content_frame);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // Set the adapter for the list view
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.right_drawer);

        // enabling action bar app icon and behaving it as toggle button
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.icon_menu, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
//                getActionBar().setTitle("");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle("");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        if (savedInstanceState == null) {
            // on first time display view for first nav item
//            displayView(0);
        }

        ivToolBarMenu.setOnClickListener(this);
        getLayoutInflater().inflate(R.layout.sliding_menu_list, mLinearSlidingMenu);

        try {
            requestGetFutureBookingList(new GetFutureBookingList() {
                @Override
                public void success() {
                    navDrawerItems = new ArrayList<>();

                    // adding nav drawer items to array
                    // Back To Request
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
                    // My Profile
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
                    // Trip History
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
                    // Car Status
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
                    // Sales
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
                    // Alert - will add a counter here
                    String futureBookingSize = String.valueOf(futureBookings.size());
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, futureBookingSize));
                    // Report Incident
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
                    // Finish Work
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
                    // Call Taxi Company
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
                    // About Cabby
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));
                    // Terms Of Use
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));
                    // Dev Info
                    navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(11, -1)));


                    // Recycle the typed array
                    navMenuIcons.recycle();

                    // setting the nav drawer list adapter
                    adapter = new SlidingMenuAdapter(getApplicationContext(), navDrawerItems);
                    mDrawerList.setAdapter(adapter);
                }

                @Override
                public void failure(ErrorObj errorObj) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawer.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        updateToolbarTitle(title.toString());
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected void updateToolbarTitle(String title) {
        tvToolBarTitle.setText(title);
    }

    private void updateBadgeNumber(int menuIndex, int badgeNumber) {
        if (badgeNumber > 0) {
            if (badgeNumber <= 9) {
                tvBadgeNumber.setText("" + badgeNumber);
            } else {
                tvBadgeNumber.setText("9+");
            }
            tvBadgeNumber.setVisibility(View.VISIBLE);
        } else {
            tvBadgeNumber.setVisibility(View.INVISIBLE);
        }

    }

    protected void updateBadgeNumberOfAlert() {
        navDrawerItems.get(ALERT_MENU_INDEX).setCount(String.valueOf(futureBookings.size()));
        adapter.notifyDataSetChanged();
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    Intent intent = null;

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if (CarDriverObj.getInstance().getCar() != null) {
            switch (position) {
                case 0:
                    model.getCurrentRequest(new SlidingMenuModel.OnGetCurrentRequest() {
                        @Override
                        public void Success() {
                            checkDriverCarStatus();
                        }

                        @Override
                        public void Failure(ErrorObj error) {
                            intent = new Intent(context, FindCustomerController.class);
                            startActivity(intent);
                            finishActivity();
                        }
                    });
                    break;
                case 1:
                    intent = new Intent(context, MyProfileController.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(context, TripHistoryController.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(context, CarStatusController.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(context, SalesController.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(context, AlertController.class);
                    startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(context, ReportIncidentController.class);
                    startActivity(intent);
                    break;
                case 7:
                    model.getCurrentRequest(new SlidingMenuModel.OnGetCurrentRequest() {
                        @Override
                        public void Success() {
                            if (TaxiRequestObj.getInstance().getStatus() >=
                                    ContantValuesObject.TaxiRequestStatusUserConfirmed &&
                                    TaxiRequestObj.getInstance().getStatus() <
                                            ContantValuesObject.TaxiRequestStatusCharged) {
                                //Cant go to FinishWorkScreen when Driver have Request
                            } else {
                                intent = new Intent(context, FinishWorkController.class);
                                startActivity(intent);
                                finishActivity();
                            }
                        }

                        @Override
                        public void Failure(ErrorObj error) {
                            if (error.errorCode == ErrorObj.REQUEST_ID_NULL) {
                                intent = new Intent(context, FinishWorkController.class);
                                startActivity(intent);
                                finishActivity();
                            } else {
                                Log.w("Error Code", String.valueOf(error.errorCode));
                                showAlertDialog(error.errorMessage).show();
                            }
                        }
                    });
                    break;
                case 8:
                    String phoneNumber = DriverCompanyObj.getInstance().getPhone_number();
                    if (phoneNumber != null) {
                        intent = PhoneObject.makePhoneCall(phoneNumber);
                        startActivity(intent);
                        finishActivity();
                    } else {
                        showAlertDialog(context.getString(R.string.can_not_find_taxi_company_phone_number)).show();
                    }
                    break;
                case 9:
                    intent = new Intent(context, AboutCabbyController.class);
                    startActivity(intent);
                    break;
                case 10:
                    intent = new Intent(context, TermsOfUseController.class);
                    startActivity(intent);
                    break;
                case 11:
                    intent = new Intent(context, DeveloperInformationController.class);
                    startActivity(intent);
                    break;
                default:
                    intent = new Intent(context, ChooseCarController.class);
                    startActivity(intent);
                    finishActivity();
                    break;
            }
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            drawer.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivToolBarMenu:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                break;
        }
    }

    @Override
    public void OnButtonOkTermOfUseClick() {

    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (CarDriverObj.getInstance().getObjectID() != ContantValuesObject.CAR_DRIVER_NULL) {
                displayView(position);
            }
        }
    }

    public interface GetFutureBookingList extends BaseModelInterface {

    }

    protected void requestGetFutureBookingList(final GetFutureBookingList onFinish) {
        DriverObj driver = CarDriverObj.getInstance().getDriver();
        driver.getFutureBookings(context, "2016-01-01", "2016-12-31", new DriverObj.OnGetFutureBookingFinish() {
            @Override
            public void success(JSONArray jsonArray) {
                futureBookings = new ArrayList<>();
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            TaxiRequestObj requestObj = new TaxiRequestObj();
                            requestObj.parseJsonToObject(object);
                            if (requestObj.getStatus() == ContantValuesObject.TaxiRequestStatusFutureBooking) {
                                futureBookings.add(requestObj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    updateBadgeNumber(0, futureBookings.size());
                }
                onFinish.success();
            }

            @Override
            public void success() {
            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }
}
