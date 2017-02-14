package bhtech.com.cabbydriver.object;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.Date;

public class BaseUserObject extends BaseObject {
    private BaseEnums.UserType type;
    private String username;
    private String fullName;
    private String email;
    private String address;
    private String password;
    private String phoneNumber;
    private Date birthday;
    private String photo;
    private LatLng location;
    private String user_token;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public JSONObject parseObjectToJson() {
        return null;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        super.parseJsonToObject(jsonObject);
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(BaseEnums.UserType type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUsername() {
        return username;
    }

    public BaseEnums.UserType getType() {
        return type;
    }

    public String getUser_token() {
        return user_token;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public LatLng getLocation() {
        return location;
    }
}
