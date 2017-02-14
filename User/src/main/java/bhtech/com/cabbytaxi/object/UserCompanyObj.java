package bhtech.com.cabbytaxi.object;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by new on 1/4/2016.
 */
public class UserCompanyObj extends BaseObject {
    private String companyName;
    private String fullName;
    private String companyUserName;
    private String companyPassword;
    private String companyPhone;
    private String companyEmail;
    private String country;
    private String companyUsageDetail;
    private int companyPayMode;
    private LatLng location;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public int getObjectID() {
        return super.getObjectID();
    }

    @Override
    public void setObjectID(int objectID) {
        super.setObjectID(objectID);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void setEmpty(boolean empty) {
        super.setEmpty(empty);
    }

    @Override
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public Date getUpdatedDate() {
        return super.getUpdatedDate();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCompanyUsageDetail() {
        return companyUsageDetail;
    }

    public void setCompanyUsageDetail(String companyUsageDetail) {
        this.companyUsageDetail = companyUsageDetail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCompanyPayMode() {
        return companyPayMode;
    }

    public void setCompanyPayMode(int companyPayMode) {
        this.companyPayMode = companyPayMode;
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                setObjectID(jsonObject.getInt("id"));
            }
            if (jsonObject.has("username") && !jsonObject.isNull("username")) {
                setCompanyUserName(jsonObject.getString("username"));
            }
            if (jsonObject.has("phone_number") && !jsonObject.isNull("phone_number")) {
                setCompanyPhone(jsonObject.getString("phone_number"));
            }
            if (jsonObject.has("email") && !jsonObject.isNull("email")) {
                setCompanyEmail(jsonObject.getString("email"));
            }
            if (jsonObject.has("country") && !jsonObject.isNull("country")) {
                setCountry(jsonObject.getString("country"));
            }
            if (jsonObject.has("full_name") && !jsonObject.isNull("full_name")) {
                setFullName(jsonObject.getString("full_name"));
            }
            if (jsonObject.has("company_email") && !jsonObject.isNull("company_email")) {
                setCompanyUsageDetail(jsonObject.getString("company_email"));
            }
            if (jsonObject.has("company_name") && !jsonObject.isNull("company_name")) {
                setCompanyName(jsonObject.getString("company_name"));
            }
            if (jsonObject.has("pay_mode") && !jsonObject.isNull("pay_mode")) {
                setCompanyPayMode(jsonObject.getInt("pay_mode"));
            }

            if (jsonObject.has("latitude") && jsonObject.has("longitude")) {
                if (!jsonObject.isNull("latitude") && !jsonObject.isNull("longitude")) {
                    Double lat = jsonObject.getDouble("latitude");
                    Double lng = jsonObject.getDouble("longitude");
                    setLocation(new LatLng(lat, lng));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("company_name", getCompanyName());
            jsonObject.put("company_phone", getCompanyPhone());
            jsonObject.put("company_email", getCompanyEmail());
            jsonObject.put("company_password", getCompanyPassword());
            jsonObject.put("company_usage_detail", getCompanyUsageDetail());
            jsonObject.put("company_paymode", String.valueOf(companyPayMode));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public interface OnApiResponse {
        void success();

        void failure(Error error);
    }

    public void registerProfile(Context context, final OnApiResponse apiResponse) {
        NetworkServices.callAPI(context, "registerCompany", NetworkServices.POST, null, null,
                new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            parseJsonToObject(jsonObject);
                            apiResponse.success();
                        } else {
                            Error error = new Error();
                            error.setError(1003, "Register Company Profile", "Cannot register company profile");
                            apiResponse.failure(new Error());
                        }
                    }
                });
    }

    public void getProfile() {

    }

    public void updateProfile() {

    }
}
