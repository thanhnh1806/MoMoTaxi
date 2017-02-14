package bhtech.com.cabbytaxi.Register.Company;

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
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.UserCompanyObj;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by Le Anh Tuan on 2/4/2016.
 */
public class CompanySettingModel implements CompanySettingInterface.Database {
    private Context context;
    private String avatarUrl, companyName, personStaffName, phoneNumber, email, country, username,
            password, confirmedPassword, usageDetail;
    int payMode = ContantValuesObject.PayByCreditCard;
    boolean accepted = false;
    private ArrayList<String> listCountry;
    private Bitmap avatarBitmap;
    private String amountOfLimit;
    private String cardNumber;
    private String cardHolderName;
    private String cvvCode;
    private String cardExpirationDate;
    private int paymentType;
    private int cardType;

    public CompanySettingModel(Context context) {
        this.context = context;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getCompanyEmail() {
        return email;
    }

    @Override
    public String getUsageDetailsEmail() {
        return usageDetail;
    }

    @Override
    public String getAmountOfLimit() {
        return amountOfLimit;
    }

    @Override
    public int getPaymentType() {
        return paymentType;
    }

    @Override
    public int getCardType() {
        return cardType;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String getCardHolderName() {
        return cardHolderName;
    }

    @Override
    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    @Override
    public void setAmountOfLimit(String s) {
        this.amountOfLimit = s;
    }

    @Override
    public void setCardNumber(String s) {
        this.cardNumber = s;
    }

    @Override
    public void setCardHolderName(String s) {
        this.cardHolderName = s;
    }

    @Override
    public void setCvvCode(String s) {
        this.cvvCode = s;
    }

    @Override
    public void setCardExpirationDate(String s) {
        this.cardExpirationDate = s;
    }

    @Override
    public void setPaymentType(int paymentTypeOnce) {
        this.paymentType = paymentTypeOnce;
    }

    @Override
    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPersonStaffName() {
        return personStaffName;
    }

    public void setPersonStaffName(String personStaffName) {
        this.personStaffName = personStaffName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getUsageDetail() {
        return usageDetail;
    }

    public void setUsageDetail(String usageDetail) {
        this.usageDetail = usageDetail;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public ArrayList<String> getListCountry() {
        return listCountry;
    }

    @Override
    public String getCompanyPhone() {
        return UserObj.getInstance().getCompany().getCompanyPhone();
    }

    public void setListCountry(ArrayList<String> listCountry) {
        this.listCountry = listCountry;
    }

    public void getListCountry(Context context) {
        setListCountry(LocationObject.getListCountry());
    }

    private Error checkNotNull(Context context) {
        Error error = new Error();
        error.setError(Error.INVALID_INPUTS);
        if (StringObject.isNullOrEmpty(companyName)) {
            error.errorMessage = context.getString(R.string.please_input_your_company_name);
        } else if (StringObject.isNullOrEmpty(personStaffName)) {
            error.errorMessage = context.getString(R.string.please_input_person_staff_name);
        } else if (StringObject.isNullOrEmpty(phoneNumber)) {
            error.errorMessage = context.getString(R.string.please_input_your_phone_number);
        } else if (StringObject.isNullOrEmpty(email)) {
            error.errorMessage = context.getString(R.string.please_input_your_email);
        } else if (country.equalsIgnoreCase(context.getString(R.string.country))) {
            error.errorMessage = context.getString(R.string.please_choose_your_country);
        } else if (StringObject.isNullOrEmpty(username)) {
            error.errorMessage = context.getString(R.string.please_input_your_usename);
        } else if (StringObject.isNullOrEmpty(password)) {
            error.errorMessage = context.getString(R.string.please_input_your_password);
        } else if (StringObject.isNullOrEmpty(confirmedPassword)) {
            error.errorMessage = context.getString(R.string.please_input_confirm_password);
        } else if (payMode != ContantValuesObject.PayByCash && payMode != ContantValuesObject.PayByCreditCard) {
            error.errorMessage = context.getString(R.string.please_choose_pay_mode);
        } else if (StringObject.isNullOrEmpty(usageDetail)) {
            error.errorMessage = context.getString(R.string.please_input_usage_detail);
        } else if (!accepted) {
            error.errorMessage = context.getString(R.string.please_check_term_of_use);
        } else {
            error.setError(Error.NO_ERROR);
        }
        return error;
    }

    public Error checkAllValidate(Context context) {
        Error error = checkNotNull(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (EmailObject.validateEmail(email) && EmailObject.validateEmail(usageDetail)) {
                if (password.equals(confirmedPassword)) {
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

    public void saveDataToUserObj() {
        UserCompanyObj userCompanyObj = new UserCompanyObj();
        userCompanyObj.setFullName(personStaffName);
        userCompanyObj.setCompanyName(companyName);
        userCompanyObj.setCompanyEmail(email);
        userCompanyObj.setCompanyPhone(phoneNumber);
        userCompanyObj.setCompanyUsageDetail(usageDetail);
        userCompanyObj.setCompanyPassword(password);
        userCompanyObj.setCompanyPayMode(payMode);
        userCompanyObj.setCompanyUserName(username);
        userCompanyObj.setCountry(country);
        UserObj.getInstance().setCompany(userCompanyObj);
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

    public void setAvatarBitmap(Bitmap bitmap) {
        avatarBitmap = bitmap;
    }

    public Bitmap getAvatarBitmap() {
        return avatarBitmap;
    }


    public void registerCreditCard(Context context, final OnRegister onFinish) {
        Error error = checkCreditCardValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (NetworkObject.isNetworkConnect(context)) {
                UserObj.getInstance().registerCompany(context, new UserObj.OnRegisterCompany() {
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
        }
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

    public interface OnRegister {
        void success();

        void failure(Error error);
    }

    public void register(Context context, final OnRegister onFinish) {
        saveDataToUserObj();
        UserObj.getInstance().registerCompany(context, new UserObj.OnRegisterCompany() {
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
}
