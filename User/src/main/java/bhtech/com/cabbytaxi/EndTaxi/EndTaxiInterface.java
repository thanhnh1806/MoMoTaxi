package bhtech.com.cabbytaxi.EndTaxi;

import android.content.Context;

import bhtech.com.cabbytaxi.object.BaseEnums;
import bhtech.com.cabbytaxi.object.CardObj;

/**
 * Created by thanh_nguyen02 on 27/01/2016.
 */
public class EndTaxiInterface {
    public interface Listener {
        void onButtonOkClick();
    }

    public interface Datasource {
        String getDate();

        String getTime();

        String getDriverName();

        String getCarNumber();

        String getStartAddress();

        String getStopAddress();

        float getMinutes(Context context);

        float getKm(Context context);

        float getCharge(Context context);

        float getExtraPrice(Context context);

        void setPayMethod(BaseEnums.PaymentMode paymentMode);

        BaseEnums.PaymentMode getPayMethod(Context context);

        CardObj getCardInformation();
    }
}
