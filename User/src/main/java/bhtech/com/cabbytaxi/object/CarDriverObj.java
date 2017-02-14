package bhtech.com.cabbytaxi.object;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class CarDriverObj extends BaseObject {
    private CarObj car;
    private DriverObj driver;
    private int status;
    private LatLng location;
    private Date start_date;
    private ArrayList<WorkingResultObj> working_results;
    private float mileage;
    // has_accident // accident_reason

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
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                setObjectID(jsonObject.getInt("id"));
            }
            if (jsonObject.has("number_car")) {
                CarObj carObj = new CarObj();
                carObj.setNumber(jsonObject.getString("number_car"));
                setCar(carObj);
            }
            if (jsonObject.has("latitude") && !jsonObject.isNull("latitude") &&
                    jsonObject.has("longitude") && !jsonObject.isNull("longitude")) {
                Double latitude = Double.valueOf(jsonObject.getString("latitude"));
                Double longitude = Double.valueOf(jsonObject.getString("longitude"));
                setLocation(new LatLng(latitude, longitude));
            }

            if (jsonObject.has("distance") && !jsonObject.isNull("distance")) {
                setMileage((float) jsonObject.getDouble("distance"));
            }

            if (jsonObject.has("driver_car") && !jsonObject.isNull("driver_car")) {
                try {
                    JSONObject driverCarJson = jsonObject.getJSONObject("driver_car");
                    setStatus(driverCarJson.getInt("status"));
                    Double latitude = Double.valueOf(driverCarJson.getString("latitude"));
                    Double longitude = Double.valueOf(driverCarJson.getString("longitude"));
                    LatLng latLng = new LatLng(latitude, longitude);
                    setLocation(latLng);
                } catch (Exception e) {
                    setLocation(null);
                }
            }

            if (jsonObject.has("driver") && !jsonObject.isNull("driver")) {
                JSONObject driverJson = jsonObject.getJSONObject("driver");
                DriverObj driver = new DriverObj();
                driver.setObjectID(driverJson.getInt("id"));
                driver.setUsername(driverJson.getString("username"));
                driver.setFullName(driverJson.getString("full_name"));
                driver.setEmail(driverJson.getString("email"));
                driver.setPhoneNumber(driverJson.getString("phone_number"));
                driver.setPhoto(driverJson.getString("photo"));
                if (jsonObject.has("rate")) {
                    driver.setRate(jsonObject.getInt("rate"));
                }
                setDriver(driver);
            }

            if (jsonObject.has("car") && !jsonObject.isNull("car")) {
                JSONObject carJson = jsonObject.getJSONObject("car");
                CarObj car = new CarObj();
                car.setObjectID(carJson.getInt("id"));
                car.setNumber(carJson.getString("number"));
                setCar(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("car", getCar().parseObjectToJson());
            jsonObject.put("driver", getDriver().parseObjectToJson());
            jsonObject.put("status", getStatus());
            jsonObject.put("start_date", getStart_date());
            ArrayList<WorkingResultObj> workingResultObjs = getWorking_results();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < workingResultObjs.size(); i++) {
                JSONObject jsonObject1 = workingResultObjs.get(i).parseObjectToJson();
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("working_results", jsonArray);
            jsonObject.put("mileage", getMileage());
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

    public CarObj getCar() {
        return car;
    }

    public void setCar(CarObj car) {
        this.car = car;
    }

    public DriverObj getDriver() {
        return driver;
    }

    public void setDriver(DriverObj driver) {
        this.driver = driver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public ArrayList<WorkingResultObj> getWorking_results() {
        return working_results;
    }

    public void setWorking_results(ArrayList<WorkingResultObj> working_results) {
        this.working_results = working_results;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public void getListCar() {

    }

    public void startWithCar() {

    }

    public void getCarHistory(Date fromDate, Date toDate) {

    }

    public int getEstimatetime(Double userLat, Double userLng) {
        float[] results = new float[1];
        try {
            Location.distanceBetween(location.latitude, location.longitude, userLat, userLng, results);
            return (int) results[0] / 1000;
        } catch (Exception e) {
            return 0;
        }
    }
}
