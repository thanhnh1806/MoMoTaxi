package bhtech.com.cabbydriver.ChargeCustomer;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by thanh_nguyen on 15/04/2016.
 */
public class ChargeCustomerModel implements ChargeCustomerInterface.Datasource {
    private Context context;
    private float nonAppUserCharge, nonAppUserExtraPrice, distance, charge, extra_price;
    private String nonAppUserCustomerEmail;
    private Date start_time;

    public ChargeCustomerModel(Context context) {
        this.context = context;
    }

    @Override
    public String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.time_format_charge_customer));
        return TimeObject.getCurrentTime(dateFormat);
    }

    @Override
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.date_format_charge_customer));
        return TimeObject.getCurrentTime(dateFormat);
    }

    @Override
    public String getCustomerName() {
        try {
            return TaxiRequestObj.getInstance().getRequestUser().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getStartAddress() {
        return TaxiRequestObj.getInstance().getFromLocationAddress();
    }

    @Override
    public String getStopAddress() {
        return TaxiRequestObj.getInstance().getToLocationAddress();
    }

    @Override
    public String getMinutes() {
        Date currentTime = TimeObject.getCurrentDateTime();
        float spendTime = (float) TimeObject.diffTimeInMiliseconds(currentTime, start_time) / 60000;
        TaxiRequestObj.getInstance().setHours(spendTime);
        if (spendTime < 1) {
            return "0" + StringObject.getDecimalFormat(1).format(spendTime);
        } else {
            return StringObject.getDecimalFormat(1).format(spendTime);
        }
    }

    @Override
    public String getDistance() {
        try {
            distance = Float.parseFloat(SharedPreference.get(context, ContantValuesObject.DISTANCE, ""));
            TaxiRequestObj.getInstance().setEnd_mileage(distance);
            return SharedPreference.get(context, ContantValuesObject.DISTANCE, "");
        } catch (Exception e) {
            TaxiRequestObj.getInstance().setEnd_mileage(0);
            return "";
        }
    }

    @Override
    public void setCharge(String s) {
        try {
            charge = Float.parseFloat(s);
        } catch (Exception e) {
            charge = 0;
        }
        TaxiRequestObj.getInstance().setPrice(charge);
    }

    @Override
    public void setNonAppUserCharge(String s) {
        try {
            nonAppUserCharge = Float.parseFloat(s);
        } catch (Exception e) {
        }
    }

    @Override
    public void setExtraPrice(String s) {
        try {
            extra_price = Float.parseFloat(s);
        } catch (Exception e) {
            extra_price = 0;
        }
        TaxiRequestObj.getInstance().extra_price = extra_price;
    }

    @Override
    public void setNonAppUserExtraPrice(String s) {
        try {
            nonAppUserExtraPrice = Float.parseFloat(s);
        } catch (Exception e) {
        }
    }

    @Override
    public void setNonAppUserCustomerEmail(String s) {
        nonAppUserCustomerEmail = s;
    }

    @Override
    public void setPayMode(int payBy) {
        TaxiRequestObj.getInstance().setPay_mode(payBy);
    }

    @Override
    public int getPayMode() {
        return TaxiRequestObj.getInstance().getPay_mode();
    }

    public interface OnChangeDriverStatus {
        void Success();

        void Failure(ErrorObj error);
    }

    public void changeDriverStatus(final OnChangeDriverStatus onFinish) {
        ErrorObj error = checkNullChargeNonApp();
        if (error.errorCode == ErrorObj.NO_ERROR) {
            TaxiRequestObj.getInstance().driverChangeStatusRequest(context,
                    ContantValuesObject.TaxiRequestStatusPaid, new TaxiRequestObj.DriverChangeStatusRequest() {
                        @Override
                        public void Success() {
                            onFinish.Success();
                        }

                        @Override
                        public void Failure(ErrorObj error) {
                            onFinish.Failure(error);
                        }
                    });
        } else {
            error.errorMessage = context.getString(R.string.please_input_again);
            onFinish.Failure(error);
        }

    }

    private ErrorObj checkNullChargeNonApp() {
        ErrorObj error = new ErrorObj();
        if (nonAppUserCharge == 0) {
            error.setError(ErrorObj.DATA_NULL);
        } else {
            error.setError(ErrorObj.NO_ERROR);
        }
        return error;
    }

    public interface OnUpdateCharge {
        void Success();

        void Failure(ErrorObj error);
    }

    public void updateCharge(final OnUpdateCharge onFinish) {
        ErrorObj error = checkNull();
        if (error.errorCode == ErrorObj.NO_ERROR) {
            TaxiRequestObj.getInstance().driverCreatePayment(context, new TaxiRequestObj.DriverCreatePayment() {
                @Override
                public void Success() {
                    onFinish.Success();
                }

                @Override
                public void Failure(ErrorObj error) {
                    onFinish.Failure(error);
                }
            });
        } else {
            error.errorMessage = context.getString(R.string.please_input_again);
            onFinish.Failure(error);
        }
    }

    private ErrorObj checkNull() {
        ErrorObj error = new ErrorObj();
        if (charge == 0) {
            error.setError(ErrorObj.DATA_NULL);
        } else {
            error.setError(ErrorObj.NO_ERROR);
        }
        return error;
    }


    public interface OnGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getCurrentRequest(final OnGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                if (TaxiRequestObj.getInstance().getStart_time() == null) {
                    //For nonapp User
                    start_time = TaxiRequestObj.getInstance().getCreatedDate();
                } else {
                    start_time = TaxiRequestObj.getInstance().getStart_time();
                }
                onFinish.Success();
            }

            @Override
            public void Failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }

    public int getCustomerStatus(String data) {
        int status = 0;
        try {
            JSONObject statusObject = new JSONObject(data);
            status = statusObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
}
