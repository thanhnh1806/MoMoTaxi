package bhtech.com.cabbydriver.ChargeCustomer;

/**
 * Created by thanh_nguyen on 15/04/2016.
 */
public class ChargeCustomerInterface {
    public interface Listener {
        void destinationLocationClick();

        void buttonNextClick();

        void updateCharge();

        void paymentByCashButtonOkClick();

        void paymentByCreditCardButtonCancelClick();

        void creditCardPaymentSuccessful();

        void creditCardPaymentUnsuccessful();
    }

    public interface Datasource {

        String getCurrentTime();

        String getCurrentDate();

        String getCustomerName();

        String getStartAddress();

        String getStopAddress();

        String getMinutes();

        String getDistance();

        void setCharge(String s);

        void setNonAppUserCharge(String s);

        void setExtraPrice(String s);

        void setNonAppUserExtraPrice(String s);

        void setNonAppUserCustomerEmail(String s);

        void setPayMode(int payBy);

        int getPayMode();
    }
}
