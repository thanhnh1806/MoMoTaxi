package bhtech.com.cabbydriver.object;

/**
 * Created by thanh_nguyen on 24/12/2015.
 */
public class ContantValuesObject {
    public static String CODE = "Code";
    public static String MESSAGE = "Message";
    public static String RESULTS = "Results";
    public static String DATA = "data";

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final String USER_AGENT = "Android";

    public static final int CAR_DRIVER_NULL = 0;
    public static final int CUSTOMER_NULL = 0;
    public static final int DRIVER_AVAILABLE = 0;
    public static final int DRIVER_NOT_AVAILABLE = 1;
    public static String CURRENT_REQUEST_ID = "currentRequestId";
    public static String REQUEST_ID = "RequestId";
    public static String IS_CLOSE_NAVI = "isCloseNavi";
    public static String DISTANCE = "distance";
    public static String PIN_CODE = "pin_code";
    public static final String PICK_UP_TIME = "pick_up_time";

    public static String DOMAIN_IMAGE_DEV = "http://117.7.238.88:8240/taxiapp_dev/";
    public static String DOMAIN_IMAGE_KH = "http://117.7.238.88:8300/taxiapp/";
    public static String DOMAIN_IMAGE = DOMAIN_IMAGE_DEV;

    public static String DOMAIN = DOMAIN_IMAGE + "index.php/";

    public static final String CHECK_PIN_CODE_ENDPOINT = "apis/drivers/checkPinCode";
    public static final String FIND_CUSTOMER_ENDPOINT = "apis/drivers/findcustomers";
    public static final String ACCEPT_REQUEST_ENDPOINT = "apis/drivers/acceptRequest";
    public static final String CHOOSE_CAR_ENDPOINT = "apis/drivers/chooseCar";
    public static final String GET_LIST_CAR_ENDPOINT = "apis/drivers/listCars";
    public static final String GET_LIST_VEHICLE_TYPE_ENDPOINT = "api/vehicles/list";
    public static final String DRIVER_FINISH_WORK_ENDPOINT = "apis/drivers/finishJobUpdate";
    public static final String DRIVER_GET_TODAY_WORKING_RESULT_ENDPOINT = "apis/drivers/finishJobinfo";
    public static final String UPDATE_REQUEST_PUSH = "apis/user/updateRequestPush/";
    public static final String FIND_TAXI_ENDPOINT = "apis/user/requestDriver";
    public static final String GET_DRIVER_TRIP_HISTORY_ENDPOINT = "apis/drivers/triphistory";
    public static final String GET_CAR_STATUS_ENDPOINT = "apis/drivers/carstatus";
    public static final String GET_CURRENT_REQUEST_ENDPOINT = "apis/user/getcurrentrequest";
    public static final String GET_ALERT_LIST_ENDPOINT = "apis/drivers/alert";
    public static final String GET_FUTURE_BOOKING_ENDPOINT = "apis/drivers/futurebooking";
    public static final String UPDATE_DRIVER_LOCATION = "api/driver_car/";
    public static final String SALE_REPORT = "apis/drivers/salesreport";
    public static final String DRIVER_PROFILE = "apis/drivers/driverProfile";
    public static final String WORKING_RESULT = "apis/drivers/getWorkingResult";
    public static final String REPORT_INCIDENT = "apis/drivers/sendReportIncident";

    public static String COMPLETE_ADDRESS_API = "https://maps.googleapis.com/maps/api/geocode/json";
    public static final String GET_DIRECTIONS_ON_GOOGLE_MAP = "http://maps.googleapis.com/maps/api/directions/json";

    public static String GCM_DEVICE_TOKEN = "GCM_DEVICE_TOKEN";
    public static String GCM_INTENT_FILTER_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    public static String CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

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
    public static final int FutureBookingUserConfirmed = 11;


    public static final int PayByCreditCard = 1;
    public static final int PayByCash = 0;

    public static final int FIRST_LOGIN = 1;
    public static final int CALENDAR_REMINDERS_FIRSTTIME = 30;
    public static final int CALENDAR_REMINDERS_SECONDTIME = 5;
}
