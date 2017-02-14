package bhtech.com.cabbytaxi.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by thanh_nguyen on 16/02/2016.
 */
public class VehicleObj extends BaseObject {
    private String name;
    private int status;
    private String icon_select;
    private String icon_deselect;
    private String icon_marker;
    private String icon_choose;
    private String icon_choose_select;

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
            setObjectID(jsonObject.getInt("id"));
            setName(jsonObject.getString("name"));
            setStatus(jsonObject.getInt("status"));
            setIcon_select(jsonObject.getString("icon_select"));
            setIcon_deselect(jsonObject.getString("icon_deselect"));
            setIcon_marker(jsonObject.getString("icon_marker"));
            icon_choose = jsonObject.getString("icon_choose");
            icon_choose_select = jsonObject.getString("icon_choose_select");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        return super.parseObjectToJson();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIcon_select() {
        return icon_select;
    }

    public void setIcon_select(String icon_select) {
        this.icon_select = icon_select;
    }

    public String getIcon_deselect() {
        return icon_deselect;
    }

    public void setIcon_deselect(String icon_deselect) {
        this.icon_deselect = icon_deselect;
    }

    public String getIcon_marker() {
        return icon_marker;
    }

    public void setIcon_marker(String icon_marker) {
        this.icon_marker = icon_marker;
    }
}
