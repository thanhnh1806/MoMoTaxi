package bhtech.com.cabbydriver.object;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarObj extends BaseObject {
    private String photo;
    private String vehicle_name;    // Vehicle Type ID
    private String number;
    private String firstUse;
    private float mileage;
    private float totalCustomer;
    private String next_time_maintenance;
    private float maintenanMileage;
    private int status;

    public float totalDistance;
    public int service_every;
    public String vehicleIconUrl;   //  Vehicle Available Icon
    public String vehicleUnavailableIconUrl;
    public String producedDate;
    public int numberOfBreakDown;
    public int numberOfAccident;
    public int vehicleId;
    public String vehicleTypeName;

    public String getVehicleIconUrl() {
        return this.vehicleIconUrl;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            setObjectID(jsonObject.getInt("id"));
            setNumber(jsonObject.getString("number"));
            setVehicle_name(String.valueOf(jsonObject.getInt("vericle_name")));
            setMileage((float) jsonObject.getDouble("mileage"));

            next_time_maintenance = jsonObject.getString("next_time_maintenance");
            if (!jsonObject.isNull("photo")) {
                setPhoto(jsonObject.getString("photo"));
            }
            setStatus(jsonObject.getInt("status"));
            producedDate = jsonObject.getString("age");

            if (jsonObject.has("numberOfAccidentReport")) {
                numberOfAccident = jsonObject.getInt("numberOfAccidentReport");
            }
            if (jsonObject.has("numberOfBreakDown")) {
                numberOfBreakDown = jsonObject.getInt("numberOfBreakDown");
            }
            if (jsonObject.has("totalDistance")) {
                totalDistance = (float) jsonObject.getDouble("totalDistance");
            }
            if (jsonObject.has("service_every")) {
                service_every = jsonObject.getInt("service_every");
            }

            if (jsonObject.has("vehicle") && !jsonObject.isNull("vehicle")) {
                JSONObject vehicleJson = jsonObject.getJSONObject("vehicle");
                vehicleId = vehicleJson.getInt("id");
                vehicleTypeName = vehicleJson.getString("name");

                if (!vehicleJson.isNull("icon_choose")) {
                    vehicleIconUrl = vehicleJson.getString("icon_choose");
                }
                if (!vehicleJson.isNull("icon_choose_select")) {
                    vehicleUnavailableIconUrl = vehicleJson.getString("icon_choose_select");
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
        try {
            return new SimpleDateFormat(dateFormat).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
        int vehicleType = Integer.parseInt(this.vehicle_name);
        switch (vehicleType) {
            case 0:
                break;
            case 1:
                break;
            default:
                break;
        }
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void getCarInfo() {
    }

    public void getCarHistory() {
    }

    public interface OnGetCarStatusFinish {
        void success();

        void failure(ErrorObj errorObj);
    }

    public void getStatus(Context context, final OnGetCarStatusFinish onFinish) {
        final ErrorObj error = new ErrorObj();

        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        NetworkObject.callAPI(context, ContantValuesObject.GET_CAR_STATUS_ENDPOINT, NetworkObject.GET,
                "Get Car Status", headers, null, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            parseJsonToObject(jsonObject.getJSONObject(ContantValuesObject.RESULTS));
                            onFinish.success();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.errorMessage = e.getMessage();
                            onFinish.failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }
}
