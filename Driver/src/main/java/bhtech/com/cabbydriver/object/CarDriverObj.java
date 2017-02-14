package bhtech.com.cabbydriver.object;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.R;

public class CarDriverObj extends BaseObject {
    private static CarDriverObj mInstance = null;
    private CarObj car;
    private DriverObj driver;
    private int status;
    private LatLng location;
    private Date start_date;
    private ArrayList<WorkingResultObj> working_results;
    private float mileage;
    private Integer totalPassengers;
    private Float totalSales;
    private Float totalDistance;
    private Float totalHour;

    public static CarDriverObj getInstance() {
        if (mInstance == null) {
            mInstance = new CarDriverObj();
        }
        return mInstance;
    }


    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public Float getTotalSales() {
        return totalSales;
    }

    public Float getTotalDistance() {
        return totalDistance;
    }

    public Float getTotalHour() {
        return totalHour;
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
            TaxiRequestObj.getInstance().setAuthToken(jsonObject.getString("authToken"));
            DriverObj driverObj = new DriverObj();
            driverObj.setObjectID(jsonObject.getInt("id"));
            driverObj.setUsername(jsonObject.getString("username"));
            if (jsonObject.has("full_name") && !jsonObject.isNull("full_name")) {
                driverObj.setFullName(jsonObject.getString("full_name"));
            }
            driverObj.setEmail(jsonObject.getString("email"));
            driverObj.setPin_code(jsonObject.getString("pin_code"));
            driverObj.setPhoneNumber(jsonObject.getString("phone_number"));
            driverObj.setFirst_login(jsonObject.getInt("first_login"));
            setDriver(driverObj);

            if (!jsonObject.isNull("driver_car")) {
                JSONObject object = jsonObject.getJSONObject("driver_car");
                setObjectID(object.getInt("id"));
                setStatus(object.getInt("status"));

                try {
                    Double lat = Double.valueOf(object.getString("latitude"));
                    Double lng = Double.valueOf(object.getString("longitude"));
                    setLocation(new LatLng(lat, lng));
                } catch (Exception e) {
                    Log.w("LatLng", "null");
                }

                CarObj car = new CarObj();
                car.setObjectID(object.getInt("cars_id"));
                setCar(car);

                if (!object.isNull("request")) {
                    TaxiRequestObj.getInstance().parseJsonToObject(object.getJSONObject("request"));
                }
            } else {
                setObjectID(ContantValuesObject.CAR_DRIVER_NULL);
            }

            if (!jsonObject.isNull("driver_company")) {
                //DriverCompanyObj.getInstance().parseJsonToObject(jsonObject.getJSONArray("driver_company"));
            }
        } catch (JSONException e) {
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

    public interface OnDriverChooseCar {
        void success();

        void failure(ErrorObj error);
    }

    public void chooseCar(final Context context, final OnDriverChooseCar finish) {
        final ErrorObj error = new ErrorObj();
        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));
        headers.put("User-Agent", Arrays.asList(ContantValuesObject.USER_AGENT));

        Map<String, List<String>> params = new HashMap<>();
        params.put("car_number", Arrays.asList(this.car.getNumber()));

        NetworkObject.callAPI(context, ContantValuesObject.CHOOSE_CAR_ENDPOINT,
                NetworkObject.POST, "Choose Car", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                setObjectID(result.getInt("id"));
                                setStatus(result.getInt("status"));
                                finish.success();
                            } else {
                                error.errorMessage = context.getString(R.string.something_wrong_when_choose_car);
                                finish.failure(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.errorMessage = e.getMessage();
                            finish.failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        finish.failure(error);
                    }
                });
    }

    public void getTodayWorkingResult(Context context, final FinishWorkAndGetTodayResult onFinish) {
        final ErrorObj error = new ErrorObj();
        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        NetworkObject.callAPI(context, ContantValuesObject.DRIVER_GET_TODAY_WORKING_RESULT_ENDPOINT,
                NetworkObject.GET, "Finish Job and Get Today Result", headers, null,
                new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            totalPassengers = result.getInt("passengers");
                            totalSales = (float) result.getDouble("total_sales");
                            totalDistance = (float) result.getDouble("total_distances");
                            totalHour = (float) result.getDouble("driving_hours");
                            onFinish.success();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorObj errorObj = new ErrorObj();
                            errorObj.errorMessage = "Cannot parse JSON parameters: " + e.getMessage();
                            onFinish.failure(errorObj);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }

    public void finishWorkAndLogOut(Context context, final FinishWorkAndLogOutCompleted onFinish) {
        final ErrorObj error = new ErrorObj();
        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("mileage", Arrays.asList("" + this.getMileage()));

        NetworkObject.callAPI(context, ContantValuesObject.DRIVER_FINISH_WORK_ENDPOINT, NetworkObject.PUT,
                "Finish Job and Log Out", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        onFinish.success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }

    public interface FinishWorkAndGetTodayResult {
        void success();

        void failure(ErrorObj errorObj);
    }

    public interface FinishWorkAndLogOutCompleted {
        void success();

        void failure(ErrorObj errorObj);
    }
}
