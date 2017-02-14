package bhtech.com.cabbytaxi.Register.Personal;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.Login.LoginInterface;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.CardObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.EmailObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 15/02/2016.
 */
public class ProfileSettingModel implements ProfileSettingInterface.Database {
    private Context context;
    private String yourName, yourPhoneNumber, yourEmail, country, username, password, confirmPassword;
    private boolean checkBoxAgree = false, radioButtonCreaditCard = false, radioButtonCash = false;
    private ArrayList<String> listCountry;
    private Bitmap avatarBitmap;

    public ProfileSettingModel(Context context) {
        this.context = context;
    }

    @Override
    public String getYourName() {
        return yourName;
    }

    @Override
    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    @Override
    public String getYourPhoneNumber() {
        return yourPhoneNumber;
    }

    @Override
    public void setYourPhoneNumber(String yourPhoneNumber) {
        this.yourPhoneNumber = yourPhoneNumber;
    }

    @Override
    public String getYourEmail() {
        return yourEmail;
    }

    @Override
    public void setYourEmail(String yourEmail) {
        this.yourEmail = yourEmail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isCheckBoxAgree() {
        return checkBoxAgree;
    }

    public void setCheckBoxAgree(boolean checkBoxAgree) {
        this.checkBoxAgree = checkBoxAgree;
    }

    public boolean isRadioButtonCreaditCard() {
        return radioButtonCreaditCard;
    }

    public void setRadioButtonCreaditCard(boolean radioButtonCreaditCard) {
        this.radioButtonCreaditCard = radioButtonCreaditCard;
    }

    public boolean isRadioButtonCash() {
        return radioButtonCash;
    }

    public void setRadioButtonCash(boolean radioButtonCash) {
        this.radioButtonCash = radioButtonCash;
    }

    public ArrayList<String> getListCountry() {
        return listCountry;
    }

    @Override
    public void setPositionListCountryUserChoose(int position) {
        SharedPreference.set(context, "PositionListCountryUserChoose", String.valueOf(position));
    }

    public int getPositionListCountryUserChoose() {
        return Integer.parseInt(SharedPreference.get(context, "PositionListCountryUserChoose", "-1"));
    }

    public void setListCountry(ArrayList<String> listCountry) {
        this.listCountry = listCountry;
    }

    public void getListCountry(Context context) {
        ArrayList listCountry = LocationObject.getListCountry();
        listCountry.add(context.getString(R.string.country));
        setListCountry(listCountry);
    }

    public Error checkProfileValidate(Context context) {
        Error error = checkProfileNotNull(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (EmailObject.validateEmail(yourEmail)) {
                if (password.equals(confirmPassword)) {
                    if (NetworkObject.isNetworkConnect(context)) {
                        error.setError(Error.NO_ERROR);
                    } else {
                        error.setError(Error.NETWORK_DISCONNECT);
                    }
                } else {
                    error.setError(Error.PASSWORDS_NOT_MATCHED);
                    error.errorMessage = context.getString(R.string.password_not_match_confirm_password);
                }
            } else {
                error.setError(Error.EMAIL_WRONG_FORMAT);
                error.errorMessage = context.getString(R.string.email_wrong_format);
            }
            return error;
        } else {
            return error;
        }
    }

    private Error checkProfileNotNull(Context context) {
        Error error = new Error();
        error.setError(Error.INVALID_INPUTS);
        if (StringObject.isNullOrEmpty(yourName)) {
            error.errorMessage = context.getString(R.string.please_input_your_name);
        } else if (StringObject.isNullOrEmpty(yourPhoneNumber)) {
            error.errorMessage = context.getString(R.string.please_input_your_phone_number);
        } else if (StringObject.isNullOrEmpty(yourEmail)) {
            error.errorMessage = context.getString(R.string.please_input_your_email);
        } else if (country.equalsIgnoreCase(context.getString(R.string.country))) {
            error.errorMessage = context.getString(R.string.please_choose_your_country);
        } else if (StringObject.isNullOrEmpty(username)) {
            error.errorMessage = context.getString(R.string.please_input_your_usename);
        } else if (StringObject.isNullOrEmpty(password)) {
            error.errorMessage = context.getString(R.string.please_input_your_password);
        } else if (StringObject.isNullOrEmpty(confirmPassword)) {
            error.errorMessage = context.getString(R.string.please_input_confirm_password);
        } else if (!radioButtonCash && !radioButtonCreaditCard) {
            error.errorMessage = context.getString(R.string.please_choose_pay_mode);
        } else if (!checkBoxAgree) {
            error.errorMessage = context.getString(R.string.please_check_term_of_use);
        } else {
            error.setError(Error.NO_ERROR);
        }
        return error;
    }

    public void saveDataToUserObj() {
        UserObj.getInstance().setCompany(null);
        UserObj.getInstance().setUsername(username);
        UserObj.getInstance().setPhoneNumber(yourPhoneNumber);
        UserObj.getInstance().setEmail(yourEmail);
        UserObj.getInstance().setPassword(password);
        UserObj.getInstance().setCountry(country);
    }

    public void setAvatarBitmap(Bitmap bitmap) {
        avatarBitmap = bitmap;
    }

    public Bitmap getAvatarBitmap() {
        return avatarBitmap;
    }

    public interface OnUploadAvatar {
        void Success();

        void Failure(Error error);
    }

    public void uploadAvatar(final OnUploadAvatar onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
        File file = PhoneObject.getFileFromBitmap(context, avatarBitmap);
        NetworkServices.uploadImage(context, ContantValuesObject.UPLOAD_AVATAR,
                "Upload Avatar", headers, file, new NetworkServices.OnUploadImage() {
                    @Override
                    public void onFinish(JSONObject jsonObject) {
                        try {
                            switch (jsonObject.getInt(ContantValuesObject.CODE)) {
                                case Error.NO_ERROR:
                                    JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    UserObj.getInstance().setPhoto(result.getString("url_image"));
                                    onFinish.Success();
                                    break;
                                case Error.WRONG_TYPE_FILE:
                                    onFinish.Failure(new Error(Error.WRONG_TYPE_FILE,
                                            jsonObject.getString(ContantValuesObject.MESSAGE)));
                                    break;
                                case Error.FILE_ALREADY_EXIST:
                                    onFinish.Failure(new Error(Error.FILE_ALREADY_EXIST,
                                            jsonObject.getString(ContantValuesObject.MESSAGE)));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public interface OnRegisterUserPayByCash {
        void success();

        void failure(Error error);
    }

    public void register(Context context, final OnRegisterUserPayByCash onFinish) {
        saveDataToUserObj();
        UserObj.getInstance().register(context, new UserObj.OnRegister() {
            @Override
            public void success() {
                onFinish.success();
            }

            @Override
            public void failure(Error error) {
                onFinish.failure(error);
            }
        });
    }

    private String amountOfLimit, cardNumber, cardHolderName, cvvCode, cardExpirationDate;
    private int paymentType, cardType;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public int getCardType() {
        return cardType;
    }

    @Override
    public String getCompanyPhone() {
        return UserObj.getInstance().getCompany().getCompanyPhone();
    }

    @Override
    public String getCompanyName() {
        return UserObj.getInstance().getCompany().getCompanyName();
    }

    @Override
    public String getCompanyEmail() {
        return UserObj.getInstance().getCompany().getCompanyEmail();
    }

    @Override
    public String getUsageDetailsEmail() {
        return UserObj.getInstance().getCompany().getCompanyUsageDetail();
    }

    public String getAmountOfLimit() {
        return amountOfLimit;
    }

    @Override
    public void setAmountOfLimit(String amountOfLimit) {
        this.amountOfLimit = amountOfLimit;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public void saveDataToCardObj() {
        try {
            CardObj.getInstance().setAmount_limit(Float.parseFloat(amountOfLimit));
            CardObj.getInstance().setPayment_type(paymentType);
            CardObj.getInstance().setCard_type(cardType);
            CardObj.getInstance().setCard_number(cardNumber);
            CardObj.getInstance().setCard_holder_name(cardHolderName);
            CardObj.getInstance().setCard_expiration_date(cardExpirationDate);
            CardObj.getInstance().setOwner(UserObj.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Error checkNull(Context context) {
        Error error = new Error();
        error.setError(Error.INVALID_INPUTS);
        if (StringObject.isNullOrEmpty(String.valueOf(amountOfLimit))) {
            error.errorMessage = context.getString(R.string.please_input_amount_of_limit);
        } else if (StringObject.isNullOrEmpty(String.valueOf(paymentType))) {
            error.errorMessage = context.getString(R.string.please_input_payment_type);
        } else if (StringObject.isNullOrEmpty(String.valueOf(cardType))) {
            error.errorMessage = context.getString(R.string.please_input_card_type);
        } else if (StringObject.isNullOrEmpty(String.valueOf(cardNumber))) {
            error.errorMessage = context.getString(R.string.please_input_card_number);
        } else if (StringObject.isNullOrEmpty(String.valueOf(cardHolderName))) {
            error.errorMessage = context.getString(R.string.please_input_card_holder_name);
        } else if (StringObject.isNullOrEmpty(String.valueOf(cvvCode))) {
            error.errorMessage = context.getString(R.string.please_input_cvv_code);
        } else if (StringObject.isNullOrEmpty(String.valueOf(cardExpirationDate))) {
            error.errorMessage = context.getString(R.string.please_input_card_expiration_date);
        } else {
            error.setError(Error.NO_ERROR);
            return error;
        }
        return error;
    }

    public interface OnUserLogin {
        void success();

        void failure(Error error);
    }

    public void userLogin(Context context, final OnUserLogin onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            UserObj.getInstance().login(context, new LoginInterface.OnFinishExecute() {
                @Override
                public void onFinish(boolean result) {
                    if (result) {
                        onFinish.success();
                    } else {
                        error.errorCode = Error.UNKNOWN_ERROR;
                        onFinish.failure(error);
                    }
                }
            });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.failure(error);
        }
    }

    public interface OnRegisterUserPayByCreditCard {
        void success();

        void failure(Error error);
    }

    public void registerUserPayByCreditCard(Context context, final OnRegisterUserPayByCreditCard onFinish) {
        Error error = checkCreditCardValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (NetworkObject.isNetworkConnect(context)) {
                saveDataToCardObj();
                UserObj.getInstance().register(context, new UserObj.OnRegister() {
                    @Override
                    public void success() {
                        onFinish.success();
                    }

                    @Override
                    public void failure(Error error) {
                        onFinish.failure(error);
                    }
                });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                error.errorMessage = context.getString(R.string.please_check_your_network);
                onFinish.failure(error);
            }
        } else {
            onFinish.failure(error);
        }
    }

    public Error checkCreditCardValidate(Context context) {
        Error error = checkCreditCardNotNull(context);

        try {
            String[] arr = cardExpirationDate.split("/");
            int month = Integer.parseInt(arr[0]);
            int year = Integer.parseInt(arr[1]);

            if (month <= 0 || month > 12 || year < 0 || year > 99) {
                error.setError(Error.INVALID_INPUTS);
                error.errorMessage = context.getString(R.string.please_input_card_expiration_date_again);
            } else {
                if (NetworkObject.isNetworkConnect(context)) {
                    error.setError(Error.NO_ERROR);
                } else {
                    error.setError(Error.NETWORK_DISCONNECT);
                    error.errorMessage = context.getString(R.string.please_check_your_network);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error.setError(Error.INVALID_INPUTS);
            error.errorMessage = context.getString(R.string.please_input_card_expiration_date_again);
        }

        return error;
    }

    private Error checkCreditCardNotNull(Context context) {
        return checkNull(context);
    }
}
