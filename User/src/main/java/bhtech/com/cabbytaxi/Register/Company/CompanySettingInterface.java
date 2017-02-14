package bhtech.com.cabbytaxi.Register.Company;

import java.util.ArrayList;

/**
 * Created by Le Anh Tuan on 2/4/2016.
 */
public class CompanySettingInterface {
    public interface Listener {
        void onCreateViewFinish();

        void OnAvatarClick();

        void OnButtonBackClick();

        void OnButtonDoneClick();

        void onDialogButtonTakePicktureOnCameraClick();

        void onDialogButtonChoosePicktureFromGallery();

        void onButtonFinishClick();

        void onCreditCardButtonOKClick();

        void onCreditCardButtonBackClick();

        void termOfUseClick();
    }

    public interface Database {
        void setCompanyName(String companyName);

        void setPersonStaffName(String personStaffName);

        void setPhoneNumber(String phoneNumber);

        void setEmail(String email);

        void setCountry(String country);

        void setUsername(String username);

        void setPassword(String password);

        void setConfirmedPassword(String confirmedPassword);

        void setUsageDetail(String usageDetail);

        void setPayMode(int payMode);

        void setAccepted(boolean accepted);

        ArrayList<String> getListCountry();

        String getCompanyPhone();

        String getCompanyName();

        String getCompanyEmail();

        String getUsageDetailsEmail();

        String getAmountOfLimit();

        int getPaymentType();

        int getCardType();

        String getCardNumber();

        String getCardHolderName();

        String getCardExpirationDate();

        void setAmountOfLimit(String s);

        void setCardNumber(String s);

        void setCardHolderName(String s);

        void setCvvCode(String s);

        void setCardExpirationDate(String s);

        void setPaymentType(int paymentTypeOnce);

        void setCardType(int cardTypeVISA);
    }
}
