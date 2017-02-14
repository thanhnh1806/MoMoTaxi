package bhtech.com.cabbydriver.object;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class UserObj extends BaseUserObject {
    private static UserObj mInstance = null;
    private UserCompanyObj company;
    private float rate;

    public static UserObj getInstance() {
        if (mInstance == null) {
            mInstance = new UserObj();
        }
        return mInstance;
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

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("id") && jsonObject.has("id")) {
                setObjectID(jsonObject.getInt("id"));
            }

            if (!jsonObject.isNull("username") && jsonObject.has("username")) {
                setUsername(jsonObject.getString("username"));
            }

            if (!jsonObject.isNull("phone_number") && jsonObject.has("phone_number")) {
                setPhoneNumber(jsonObject.getString("phone_number"));
            }
            if (!jsonObject.isNull("email") && jsonObject.has("email")) {
                setEmail(jsonObject.getString("email"));
            }

            setPassword(jsonObject.getString("password"));
            setCreatedDate(parseDate(jsonObject.getString("create_date"), "yyyy-MM-dd hh:mm:ss"));
            setUpdatedDate(parseDate(jsonObject.getString("update_date"), "yyyy-MM-dd hh:mm:ss"));

            Double latitude = Double.valueOf(jsonObject.getString("latitude"));
            Double longitude = Double.valueOf(jsonObject.getString("longitude"));
            setLocation(new LatLng(latitude, longitude));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", getUsername());
            jsonObject.put("type", getType());
            jsonObject.put("email", getEmail());
            jsonObject.put("address", getAddress());
            jsonObject.put("password", getPassword());
            jsonObject.put("phoneNumber", getPhoneNumber());
            jsonObject.put("birthday", getBirthday());
            jsonObject.put("photo", getPhoto());
            jsonObject.put("lat", getLocation().latitude);
            jsonObject.put("lng", getLocation().longitude);
            jsonObject.put("user_token", getUser_token());
            jsonObject.put("company", getCompany().parseObjectToJson());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public void setType(BaseEnums.UserType type) {
        super.setType(type);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public void setBirthday(Date birthday) {
        super.setBirthday(birthday);
    }

    @Override
    public void setPhoto(String photo) {
        super.setPhoto(photo);
    }

    @Override
    public void setLocation(LatLng location) {
        super.setLocation(location);
    }

    @Override
    public void setUser_token(String user_token) {
        super.setUser_token(user_token);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public BaseEnums.UserType getType() {
        return super.getType();
    }

    @Override
    public String getUser_token() {
        return super.getUser_token();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public Date getBirthday() {
        return super.getBirthday();
    }

    @Override
    public String getPhoto() {
        return super.getPhoto();
    }

    @Override
    public LatLng getLocation() {
        return super.getLocation();
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public UserCompanyObj getCompany() {
        return company;
    }

    public void setCompany(UserCompanyObj company) {
        this.company = company;
    }


    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void register() {
    }


//    public void login(Context context, final LoginInterface.OnFinishExecute onFinishExecute) {
//        NetworkServices.login(context, getUsername(), getPassword(), new NetworkServices.OnFinishExecute() {
//            @Override
//            public void onFinish(JSONObject jsonObject) {
//                if (jsonObject != null) {
//                    parseJsonToObject(jsonObject);
//                    Log.d("parseJsonToObject", "Success");
//                    onFinishExecute.onFinish(true);
//                } else {
//                    Log.d("parseJsonToObject", "False");
//                    onFinishExecute.onFinish(false);
//                }
//            }
//        });
//    }

    public void getProfile() {
    }

    public void updateProfile() {
    }
}
