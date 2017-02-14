package bhtech.com.cabbytaxi.object;

/**
 * Created by thanh_nguyen on 24/12/2015.
 */
public class ContantValuesObject {
    public static boolean isBuildTest = false;
    public static final int NO_ERROR = 0;
    public static int ERROR_NULL = 1;
    public static int ERROR_LENGH = 2;
    public static int ERROR_SPACE = 3;
    public static int ERROR_SPECIAL = 4;

    public static String CODE = "Code";
    public static String MESSAGE = "Message";
    public static String RESULTS = "Results";
    public static String DATA = "data";

    public static String USERNAME = "Username";
    public static String PASSWORD = "Password";
    public static String AUTHTOKEN = "AuthToken";
    public static String REMEMBER_ME = "RememberMe";
    public static String CURRENT_REQUEST_ID = "currentRequestId";
    public static String REQUEST_ID_NULL = "-1";
    public static String LIST_FROM_HISTORY_LOCATION = "list_from_history_location";
    public static String LIST_TO_HISTORY_LOCATION = "list_to_history_location";

    public static String GCM_DEVICE_TOKEN = "GCM_DEVICE_TOKEN";
    public static String GCM_INTENT_FILTER_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    public static String CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public static String DOMAIN_IMAGE_DEV = "http://117.7.238.88:8240/taxiapp_dev/";
    public static String DOMAIN_IMAGE_KH = "http://117.7.238.88:8300/taxiapp/";
    public static String DOMAIN_IMAGE = DOMAIN_IMAGE_DEV;

    public static String DOMAIN = DOMAIN_IMAGE + "index.php/";

    public static String LOGIN_ENDPOINT = "apis/user/login/customers";
    public static String LOGIN_FACEBOOK_ENDPOINT = "apis/user/LoginWithFaceBook";
    public static String LOGIN_TWITTER_ENDPOINT = "apis/user/LoginWithTwitter";
    public static String LOGIN_GOOGLE_PLUS_ENDPOINT = "apis/user/LoginWithGooglePlus";

    public static String SETTING_FACEBOOK_ENDPOINT = "apis/user/SettingWithFaceBook";
    public static String SETTING_TWITTER_ENDPOINT = "apis/user/SettingWithTwitter";
    public static String SETTING_GOOGLE_PLUS_ENDPOINT = "apis/user/SettingWithGooglePlus";

    public static String RE_LOGIN = "apis/user/relogin";
    public static String LOGOUT = "apis/user/logoutAccount";
    public static String FORGOT_PASSWORD = "apis/user/forgotPassword";
    public static String REGISTER_ENDPOINT = "apis/user/register/customers";
    public static String REGISTER_COMPANY_ENDPOINT = "apis/user/register/customers";
    public static String ADD_FAVOURITE_DRIVER_ENDPOINT = "apis/user/addFavouriteDriver";
    public static String GET_LIST_FAVOURITE_DRIVER_ENDPOINT = "apis/user/getDriverFavorite";
    public static String DELETE_FAVOURITE_DRIVER_ENDPOINT = "api/favourite_driver/";
    public static String UPDATE_PROFILE_ENDPOINT = "apis/user/updateProfile/";
    public static String GET_LIST_FAVOURITE_LOCATION_ENDPOINT = "api/favourite_location/list?customers_id=";
    public static String LIST_VEHICLE_ENDPOINT = "api/vehicles/list?status=1";
    public static String GET_CURRENT_REQUEST_ENDPOINT = "apis/user/getcurrentrequest";
    public static String UPDATE_REQUEST_PUSH = "apis/user/updateRequestPush/";
    public static String CANCEL_REQUEST_FOUND_TAXI = "api/requests/";
    public static String CANCEL_DRIVER = "apis/user/canceldriver/";
    public static String DELETE_RECEIPT = "api/requests/";
    public static String CUSTOMER_CONFIRM_ENDPOINT = "apis/user/customerConfirm";
    public static String FIND_TAXI_ENDPOINT = "apis/user/requestDriver";
    public static String CARS_BY_LOCATION_ENDPOINT = "apis/user/carsByLocation?";
    public static String WAIT_TAXI_ENDPOINT = "apis/user/waitTaxi?requests_id=";
    public static String RATE_TAXI_ENDPOINT = "api/requests/";
    public static String FAVOURITE_LOCATION_ENDPOINT = "api/favourite_location";
    public static String HISTORY_ENDPOINT = "apis/user/userMenuHistory";
    public static String SEND_FEEDBACK_ENDPOINT = "apis/user/sendfeedback";
    public static String NEARBY_LOCATION_API = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    public static String COMPLETE_ADDRESS_API = "https://maps.googleapis.com/maps/api/geocode/json";
    public static String UPDATE_USER_LOCATION = "api/customers/";
    public static String UPLOAD_AVATAR = "apis/user/upload";

    //"https://maps.googleapis.com/maps/api/place/search/json?";
    public static final int TaxiRequestStatusPending = 0;
    public static final int TaxiRequestStatusCancelled = 1;
    public static final int TaxiRequestStatusDriverSelected = 2;
    public static final int TaxiRequestStatusUserConfirmed = 3;
    public static final int TaxiRequestStatusDrivingToPassenger = 4;
    public static final int TaxiRequestStatusWaitingPickupPassenger = 5;
    public static final int TaxiRequestStatusWithPassenger = 6;
    public static final int TaxiRequestStatusChooseRoute = 7;
    public static final int TaxiRequestStatusDrivingToDestination = 8;
    public static final int TaxiRequestStatusCharged = 9;
    public static final int TaxiRequestStatusPaid = 10;
    public static final int TaxiRequestStatusFutureBooking = 11;

    public static final int FoundView = 0;
    public static final int OptionView = 1;
    public static final int DriverAcceiptView = 2;

    public static final int PayByCreditCard = 1;
    public static final int PayByCash = 0;

    public static final int RegisterForPersonal = 0;
    public static final int RegisterForCompany = 1;

    public static final int CardUserTypePersonal = 0;
    public static final int CardUserTypeCompany = 1;

    public static final int CardTypeVISA = 0;
    public static final int CardTypeMasterCard = 1;

    public static final int PaymentTypeOnce = 0;
    public static final int PaymentTypeMonthly = 1;

    public static final int FeedbackNegative = 0;
    public static final int FeedbackPositive = 1;

    public static final int PickupTimeNow = 0;
    public static final int PickupTimeDateTime = 1;

    public static final int Speed = 40; //(km/h)

    public static final int CALENDAR_REMINDERS_FIRSTTIME = 30;
    public static final int CALENDAR_REMINDERS_SECONDTIME = 5;
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 999;
}
