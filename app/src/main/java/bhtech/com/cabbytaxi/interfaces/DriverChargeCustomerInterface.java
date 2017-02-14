package bhtech.com.cabbytaxi.SupportClass;

/**
 * Created by thanh_nguyen02 on 27/01/2016.
 */
public class DriverChargeCustomerInterface {
    public interface Listener {
        void onButtonCancelPaymentViaCreditCardClick();

        void onButtonOkClick();

        void onButtonPayByCreditCardClick();

        void onButtonPayByCashClick();

        void onButtonNextClick();

        void onButtonYesClick();

        void onButtonNoClick();

        void onDialogCreateViewFinish();

        void onPayByCashReceiptSentCreateViewFinish();

        void onCreditCardInfoSentToCustomerCreateViewFinish();

        void onTaxiChargeCreateViewFinish();
    }

    public interface DataSource {
        String getDate();

        String getTime();

        String getCustomerName();

        String getStartAddress();

        String getStopAddress();

        String getMinutes();

        String getKm();

        String getCharge();
    }
}
