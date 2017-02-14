package bhtech.com.cabbydriver.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class FavDriverObj extends BaseObject {
    private UserObj owner;
    private DriverObj driver;

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
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setEmpty(boolean empty) {
        super.setEmpty(empty);
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
            UserObj userObj = new UserObj();
            userObj.parseJsonToObject(jsonObject.getJSONObject("owner"));
            setOwner(userObj);
            DriverObj driverObj = new DriverObj();
            driverObj.parseJsonToObject(jsonObject.getJSONObject("driver"));
            setDriver(driverObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner", getOwner().parseObjectToJson());
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

    public void setOwner(UserObj owner) {
        this.owner = owner;
    }

    public void setDriver(DriverObj driver) {
        this.driver = driver;
    }

    public DriverObj getDriver() {
        return driver;
    }

    public UserObj getOwner() {
        return owner;
    }

    public void userGetListFavouriteDriverSuccessB() {
    }

    public void userUpdateFavouriteDriverSuccessB() {
    }

    public void userDeleteFavouriteDriverSuccessB() {
    }
}
