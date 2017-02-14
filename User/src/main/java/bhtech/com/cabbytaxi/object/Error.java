package bhtech.com.cabbytaxi.object;

/**
 * Created by duongpv on 2/12/16.
 */
public class Error {
    public static final int NO_ERROR = 0;
    public static final int ERROR_AUTHTOKEN = -1;
    public static final int ACCEPT_REQUIRED = 1000;
    public static final int INVALID_INPUTS = 1001;
    public static final int PASSWORDS_NOT_MATCHED = 1002;
    public static final int EMAIL_WRONG_FORMAT = 1003;
    public static final int NETWORK_DISCONNECT = 1004;
    public static final int DATA_DUPLICATE = 1005;
    public static final int REQUEST_ID_NULL = 1006;
    public static final int DATA_NULL = 1007;
    public static final int PAST_PICKUP_TIME = 1008;
    public static final int WRONG_AUTHTOKEN = 1009;
    public static final int PICKUP_TIME_LESS_THAN_ONE_HOUR = 1010;
    public static final int VOLLEY_IMAGE_REQUEST_ERROR = 1011;
    public static final int VOLLEY_JSON_REQUEST_ERROR = 1011;
    public static final int VOLLEY_TIMEOUT_ERROR = 1012;
    public static final int INVALID_EMAIL_FORMAT = 515;
    public static final int USERNAME_NOT_EXISTED = 521;
    public static final int EMAIL_NOT_EXISTED = 522;
    public static final int USERNAME_EXISTED = 434;
    public static final int REQUEST_ALREADY_EXIST = 438;
    public static final int BOOKED_REQUEST_IN_NEXT_HOUR = 525;
    public static final int FAVOURITE_DRIVER_ALREADY_EXISTED = 437;
    public static final int WRONG_TYPE_FILE = 91;
    public static final int FILE_ALREADY_EXIST = 93;
    public static final int UNKNOWN_ERROR = 1;

    public int errorCode = NO_ERROR;
    public String errorMessage = "";
    public String errorDomain = "";

    public Error() {
    }

    public Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Error(Error error) {
        this.errorCode = error.errorCode;
        this.errorDomain = error.errorDomain;
        this.errorMessage = error.errorMessage;
    }

    public void setError(int errorCode) {
        this.errorCode = errorCode;
        this.errorDomain = "Cabby Error";
    }

    public void setError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
    }

    public void setError(int errorCode, String errorDomain, String errorMsg) {
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
        this.errorMessage = errorMsg;
    }
}
