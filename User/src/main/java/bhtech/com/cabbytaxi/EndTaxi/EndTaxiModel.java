package bhtech.com.cabbytaxi.EndTaxi;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import bhtech.com.cabbytaxi.object.BaseEnums;
import bhtech.com.cabbytaxi.object.CardObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;

/**
 * Created by thanh_nguyen on 14/01/2016.
 */
public class EndTaxiModel implements EndTaxiInterface.Datasource {
    private Context context;
    private int statusRequest;
    private String minutes;
    public static final DateFormat timeFormatAMPM = new SimpleDateFormat("hh:mm a", Locale.US);
    public static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    public EndTaxiModel(Context context) {
        this.context = context;
    }

    @Override
    public String getDate() {
        return TimeObject.getCurrentDate(dateFormat);
    }

    @Override
    public String getTime() {
        return TimeObject.getCurrentTime(timeFormatAMPM);
    }

    @Override
    public String getDriverName() {
        return TaxiRequestObj.getInstance().getResponseDriver().getDriver().getUsername();
    }

    @Override
    public String getCarNumber() {
        return TaxiRequestObj.getInstance().getResponseDriver().getCar().getNumber();
    }

    @Override
    public String getStartAddress() {
        return TaxiRequestObj.getInstance().getFromLocationAddress();
    }

    @Override
    public String getStopAddress() {
        return TaxiRequestObj.getInstance().getToLocationAddress();
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    @Override
    public float getMinutes(Context context) {
        try {
            return Float.parseFloat(SharedPreference.get(context, "hours", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public float getKm(Context context) {
        try {
            return Float.parseFloat(SharedPreference.get(context, "mileage", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public float getCharge(Context context) {
        try {
            float price = Float.parseFloat(SharedPreference.get(context, "price", ""));
            float extra_price = Float.parseFloat(SharedPreference.get(context, "extra_price", ""));
            return price + extra_price;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public float getExtraPrice(Context context) {
        try {
            return Float.parseFloat(SharedPreference.get(context, "extra_price", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private BaseEnums.PaymentMode paymentMode;

    @Override
    public void setPayMethod(BaseEnums.PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public BaseEnums.PaymentMode getPayMethod(Context context) {
        if (SharedPreference.get(context, "payby", String.valueOf(ContantValuesObject.PayByCash))
                .equals(String.valueOf(ContantValuesObject.PayByCreditCard))) {
            return BaseEnums.PaymentMode.PaymentByCreditCard;
        } else {
            return BaseEnums.PaymentMode.PaymentByCash;
        }
    }

    @Override
    public CardObj getCardInformation() {
        try {
            if (TaxiRequestObj.getInstance().getCardInfor() != null) {
                return TaxiRequestObj.getInstance().getCardInfor();
            } else {
                CardObj cardObj = new CardObj();
                cardObj.setCard_number("12345678");
                cardObj.setCvv_code("8888");
                cardObj.setCard_holder_name("Thanh");
                UserObj userObj = new UserObj();
                userObj.setPhoneNumber("0973349443");
                cardObj.setOwner(userObj);
                return cardObj;
            }
        } catch (Exception e) {
            CardObj cardObj = new CardObj();
            cardObj.setCard_number("12345678");
            cardObj.setCvv_code("8888");
            cardObj.setCard_holder_name("Thanh");
            UserObj userObj = new UserObj();
            userObj.setPhoneNumber("0973349443");
            cardObj.setOwner(userObj);
            return cardObj;
        }
    }

    public int getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(int statusRequest) {
        this.statusRequest = statusRequest;
    }

    public void setStatusRequest(Context context, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            setStatusRequest(jsonObject.getInt("status"));
            if (jsonObject.has("hours")) {
                SharedPreference.set(context, "hours", jsonObject.getString("hours"));
            }
            if (jsonObject.has("mileage")) {
                SharedPreference.set(context, "mileage", jsonObject.getString("mileage"));
            }
            if (jsonObject.has("price")) {
                SharedPreference.set(context, "price", jsonObject.getString("price"));
            }
            if (jsonObject.has("extra_price")) {
                SharedPreference.set(context, "extra_price", jsonObject.getString("extra_price"));
            }
            if (jsonObject.has("payby")) {
                SharedPreference.set(context, "payby", jsonObject.getString("payby"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface onGetCurrentRequest {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getCurrentRequest(final onGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                onFinish.Failure(error);
            }
        });
    }
}
