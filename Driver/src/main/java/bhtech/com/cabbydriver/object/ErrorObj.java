package bhtech.com.cabbydriver.object;

/**
 * Created by duongpv on 2/12/16.
 */
public class ErrorObj {
    public static final int NO_ERROR = 0;
    public static final int UNKNOWN_ERROR = 1;
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
    public static final int INVALID_EMAIL_FORMAT = 515;
    public static final int USERNAME_NOT_EXISTED = 521;
    public static final int EMAIL_NOT_EXISTED = 522;
    public static final int VOLLEY_IMAGE_REQUEST_ERROR = 1011;
    public static final int VOLLEY_JSON_REQUEST_ERROR = 1011;
    public static final int VOLLEY_TIMEOUT_ERROR = 1012;
    public static final int USER_CANCELED = 66;

    public int errorCode = NO_ERROR;
    public String errorMessage = "";
    public String errorDomain = "";

    public ErrorObj() {
    }

    public ErrorObj(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorObj(int errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorObj(ErrorObj error) {
        this.errorCode = error.errorCode;
        this.errorDomain = error.errorDomain;
        this.errorMessage = error.errorMessage;
    }

    public void setError(int errorCode) {
        this.errorCode = errorCode;
        this.errorDomain = "Cabby Error";
    }

    public void setError(int errorCode, String errorDomain) {
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
    }


    public void setError(int errorCode, String errorDomain, String errorMsg) {
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
        this.errorMessage = errorMsg;
    }
}
