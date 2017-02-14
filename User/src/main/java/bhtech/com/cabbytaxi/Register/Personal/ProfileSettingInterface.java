package bhtech.com.cabbytaxi.Register.Personal;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 15/02/2016.
 */
public class ProfileSettingInterface {
    public interface Listener {
        void onCreateViewFinish();

        void onButtonDoneClick();

        void onProfileButtonBackClick();

        void onImageAvatarClick();

        void onImageFacebookClick();

        void onImageGooglePlusClick();

        void onImageTwitterClick();

        void termOfUseClick();

        void onButtonFinishClick();

        void onCreditCardButtonBackClick();

        void onCreditCardButtonOKClick();

        void onDialogButtonTakePicktureOnCameraClick();

        void onDialogButtonChoosePicktureFromGallery();
    }

    public interface Database {

        void setYourName(String s);

        void setYourPhoneNumber(String s);

        void setYourEmail(String s);

        void setCountry(String country);

        void setUsername(String username);

        void setPassword(String password);

        void setConfirmPassword(String confirmPassword);

        void setCheckBoxAgree(boolean checkBoxAgree);

        void setRadioButtonCreaditCard(boolean radioButtonCreaditCard);

        void setRadioButtonCash(boolean radioButtonCash);

        String getYourName();

        String getYourPhoneNumber();

        String getYourEmail();

        String getCountry();

        String getUsername();

        String getPassword();

        String getConfirmPassword();

        boolean isCheckBoxAgree();

        boolean isRadioButtonCreaditCard();

        boolean isRadioButtonCash();

        ArrayList<String> getListCountry();

        void setPositionListCountryUserChoose(int position);

        int getPositionListCountryUserChoose();

        void setAmountOfLimit(String amountOfLimit);

        void setCardNumber(String cardNumber);

        void setCardHolderName(String cardHolderName);

        void setCvvCode(String cvvCode);

        void setCardExpirationDate(String cardExpirationDate);

        void setPaymentType(int paymentType);

        void setCardType(int cardType);

        String getAmountOfLimit();

        String getCardNumber();

        String getCardHolderName();

        String getCardExpirationDate();

        String getCvvCode();

        int getPaymentType();

        int getCardType();

        String getCompanyPhone();

        String getCompanyName();

        String getCompanyEmail();

        String getUsageDetailsEmail();
    }
}
