package bhtech.com.cabbytaxi.object;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class FavLocationObj extends BaseObject {
    private UserObj owner;
    private String location_name;
    private LatLng location;

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public UserObj getOwner() {
        return owner;
    }

    public void setOwner(UserObj owner) {
        this.owner = owner;
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
            UserObj userObj = new UserObj();
            userObj.parseJsonToObject(jsonObject.getJSONObject("owner"));
            setOwner(userObj);
            setLocation_name(jsonObject.getString("location_name"));
            setLocation(new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner", getOwner().parseObjectToJson());
            jsonObject.put("location_name", getLocation_name());
            jsonObject.put("lat", getLocation().latitude);
            jsonObject.put("lng", getLocation().longitude);
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

    public void userGetListFavouriteLocationSuccessB() {
    }

    public void userUpdateFavouriteLocationSuccessB() {
    }

    public void userDeleteFavouriteLocationSuccessB() {
    }
}
