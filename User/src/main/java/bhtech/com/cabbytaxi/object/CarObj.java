package bhtech.com.cabbytaxi.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CarObj extends BaseObject {
    private BaseEnums.CarClass class_;
    private String photo;
    private String vehicle_name;
    private String number;
    private String firstUse;
    private float mileage;
    private float totalCustomer;
    private String next_time_maintenance;
    private float maintenanMileage;
    private BaseEnums.CarStatus status;

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
            setClass_(BaseEnums.CarClass.values()[jsonObject.getInt("class_")]);
            setPhoto(jsonObject.getString("photo"));
            setVehicle_name(jsonObject.getString("vehicle_name"));
            setNumber(jsonObject.getString("number"));
            setFirstUse(jsonObject.getString("firstUse"));
            setMileage((float) jsonObject.getDouble("mileage"));
            setTotalCustomer((float) jsonObject.getDouble("totalCustomer"));
            setNext_time_maintenance(jsonObject.getString("next_time_maintenance"));
            setMaintenanMileage((float) jsonObject.getDouble("maintenanMileage"));
            setStatus(BaseEnums.CarStatus.values()[jsonObject.getInt("status")]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class_", getClass_());
            jsonObject.put("photo", getPhoto());
            jsonObject.put("vehicle_name", getVehicle_name());
            jsonObject.put("number", getNumber());
            jsonObject.put("firstUse", getFirstUse());
            jsonObject.put("mileage", getMileage());
            jsonObject.put("totalCustomer", getTotalCustomer());
            jsonObject.put("next_time_maintenance", getNext_time_maintenance());
            jsonObject.put("maintenanMileage", getMaintenanMileage());
            jsonObject.put("status", getStatus());
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setClass_(BaseEnums.CarClass class_) {
        this.class_ = class_;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFirstUse(String firstUse) {
        this.firstUse = firstUse;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public void setTotalCustomer(float totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public void setNext_time_maintenance(String next_time_maintenance) {
        this.next_time_maintenance = next_time_maintenance;
    }

    public void setMaintenanMileage(float maintenanMileage) {
        this.maintenanMileage = maintenanMileage;
    }

    public void setStatus(BaseEnums.CarStatus status) {
        this.status = status;
    }

    public BaseEnums.CarClass getClass_() {
        return class_;
    }

    public String getPhoto() {
        return photo;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstUse() {
        return firstUse;
    }

    public float getMileage() {
        return mileage;
    }

    public float getTotalCustomer() {
        return totalCustomer;
    }

    public String getNext_time_maintenance() {
        return next_time_maintenance;
    }

    public float getMaintenanMileage() {
        return maintenanMileage;
    }

    public BaseEnums.CarStatus getStatus() {
        return status;
    }

    public void getCarInfo() {
    }

    public void getCarHistory() {
    }
}
