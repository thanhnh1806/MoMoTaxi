package bhtech.com.cabbytaxi.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DriverCompanyObj extends BaseObject {
    private String name;
    private String phoneNumber;
    private String address;
    private String managerName;
    private String email;
    private DriverObj driver;
    private String tax_code;
    private String phoneNumber_Police;

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
            if (jsonObject.has("id")) {
                setObjectID(jsonObject.getInt("id"));
            }
            if (jsonObject.has("company_name")) {
                setName(jsonObject.getString("company_name"));
            }
            if (jsonObject.has("phone_number")) {
                setPhoneNumber(jsonObject.getString("phone_number"));
            }
            if (jsonObject.has("email")) {
                setEmail(jsonObject.getString("email"));
            }
            if (jsonObject.has("address")) {
                setAddress(jsonObject.getString("address"));
            }
            if (jsonObject.has("tax_code") && !jsonObject.isNull("tax_code")) {
                tax_code = jsonObject.getString("tax_code");
            }
            if (jsonObject.has("phoneNumber_Police") && !jsonObject.isNull("phoneNumber_Police")) {
                phoneNumber_Police = jsonObject.getString("phoneNumber_Police");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("company_name", getName());
            jsonObject.put("driver", getDriver().parseObjectToJson());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public DriverObj getDriver() {
        return driver;
    }

    public void setDriver(DriverObj driver) {
        this.driver = driver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
