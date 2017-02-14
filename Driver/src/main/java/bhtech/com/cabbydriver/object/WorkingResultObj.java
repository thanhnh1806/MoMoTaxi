package bhtech.com.cabbydriver.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class WorkingResultObj extends BaseObject {
    private String start_day;
    private String end_day;
    private float working_hours;
    private float mileage;
    private String working_type;

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
            setStart_day(jsonObject.getString("start_day"));
            setEnd_day(jsonObject.getString("end_day"));
            setWorking_hours((float) jsonObject.getDouble("working_hours"));
            setMileage((float) jsonObject.getDouble("mileage"));
            setWorking_type(jsonObject.getString("working_type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start_day", getStart_day());
            jsonObject.put("end_day", getEnd_day());
            jsonObject.put("working_hours", getWorking_hours());
            jsonObject.put("mileage", getMileage());
            jsonObject.put("working_type", getWorking_type());
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

    public String getWorking_type() {
        return working_type;
    }

    public String getStart_day() {
        return start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public float getWorking_hours() {
        return working_hours;
    }

    public float getMileage() {
        return mileage;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public void setWorking_type(String working_type) {
        this.working_type = working_type;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public void setWorking_hours(float working_hours) {
        this.working_hours = working_hours;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }
}
