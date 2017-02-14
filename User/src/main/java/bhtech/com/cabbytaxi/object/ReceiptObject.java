package bhtech.com.cabbytaxi.object;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Le Anh Tuan on 2/16/2016.
 */
public class ReceiptObject extends BaseObject {
    private int id;
    private int status;
    private String fromAddress;
    private LatLng fromLocation;
    private String toAddress;
    private LatLng toLocation;
    private float mileage;
    private float price;
    private float extra_price;
    private int hours;
    private Date start_time;
    private float rate;
    private int payby;
    private String state;
    private VehicleObj vehicle;
    private UserObj requestUser;
    private CarDriverObj responseDriver;
    private String from_name;
    private Date pickup_time;

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                setId(jsonObject.getInt("id"));
            }
            if (jsonObject.has("status")) {
                setStatus(jsonObject.getInt("status"));
            }

            if (jsonObject.has("from_address")) {
                setFromAddress(jsonObject.getString("from_address"));
            }

            if (jsonObject.has("from_latitude") && (jsonObject.has("from_longitude"))) {
                setFromLocation(new LatLng(jsonObject.getDouble("from_latitude"),
                        jsonObject.getDouble("from_longitude")));
            }

            if (jsonObject.has("to_latitude") && (jsonObject.has("to_longitude"))) {
                setToLocation(new LatLng(jsonObject.getDouble("to_latitude"), jsonObject.getDouble("to_longitude")));
            }

            if (jsonObject.has("to_address") && !jsonObject.isNull("to_address")) {
                setToAddress(jsonObject.getString("to_address"));
            }

            if (jsonObject.has("mileage") && !jsonObject.isNull("mileage")) {
                setMileage(jsonObject.getInt("mileage"));
            }

            if (jsonObject.has("price") && !jsonObject.isNull("price")) {
                setPrice((float) jsonObject.getDouble("price"));
            } else {
                setPrice(0);
            }

            if (jsonObject.has("extra_price") && !jsonObject.isNull("extra_price")) {
                setExtra_price((float) jsonObject.getDouble("extra_price"));
            } else {
                setExtra_price(0);
            }

            if (jsonObject.has("start_time") && !jsonObject.isNull("start_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("start_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setStart_time(calendar.getTime());
            } else {
                setStart_time(null);
            }

            if (jsonObject.has("update_date") && !jsonObject.isNull("update_date")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("update_date"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setUpdatedDate(calendar.getTime());
            } else {
                setUpdatedDate(null);
            }

            if (jsonObject.has("pickup_time") && !jsonObject.isNull("pickup_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("pickup_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setPickup_time(calendar.getTime());
            } else {
                setPickup_time(null);
            }

            if (jsonObject.has("hours")) {
                setHours(jsonObject.getInt("hours"));
            }
            if (jsonObject.has("payby")) {
                if (jsonObject.getInt("payby") == ContantValuesObject.PayByCash) {
                    setPayby(ContantValuesObject.PayByCash);
                } else {
                    setPayby(ContantValuesObject.PayByCreditCard);
                }
            }

            if (jsonObject.has("state")) {
                setState(jsonObject.getString("state"));
            }

            if (jsonObject.has("user")) {
                try {
                    UserObj userObj = new UserObj();
                    JSONObject userJsonObject = jsonObject.getJSONObject("user");
                    userObj.parseJsonToObject(userJsonObject);
                    setRequestUser(userObj);
                } catch (Exception e) {
                    Log.e("User", "Null");
                }
            }

            CarDriverObj carDriverObj = new CarDriverObj();

            DriverObj driverObj = new DriverObj();
            if (jsonObject.has("driver") && !jsonObject.isNull("driver")) {
                JSONObject driverJsonObject = jsonObject.getJSONObject("driver");
                if (driverJsonObject.has("id") && !driverJsonObject.isNull("id")) {
                    driverObj.setObjectID(driverJsonObject.getInt("id"));
                }
                if (driverJsonObject.has("username") && !driverJsonObject.isNull("username")) {
                    driverObj.setUsername(driverJsonObject.getString("username"));
                }

                if (driverJsonObject.has("full_name") && !driverJsonObject.isNull("full_name")) {
                    driverObj.setFullName(driverJsonObject.getString("full_name"));
                }

                if (driverJsonObject.has("email") && !driverJsonObject.isNull("email")) {
                    driverObj.setEmail(driverJsonObject.getString("email"));
                }
                if (driverJsonObject.has("phone_number") && !driverJsonObject.isNull("phone_number")) {
                    driverObj.setPhoneNumber(driverJsonObject.getString("phone_number"));
                }
                if (driverJsonObject.has("latitude") && driverJsonObject.has("longitude")) {
                    try {
                        carDriverObj.setLocation(new LatLng(Double.parseDouble(driverJsonObject.getString("latitude")),
                                Double.parseDouble(driverJsonObject.getString("longitude"))));
                    } catch (Exception e) {
                        Log.e("Driver_Location", "Null");
                    }
                }
            }

            DriverCompanyObj driverCompany = new DriverCompanyObj();
            if (jsonObject.has("driver_company") && !jsonObject.isNull("driver_company")) {
                driverCompany.parseJsonToObject(jsonObject.getJSONObject("driver_company"));
                driverObj.setDriver_company(driverCompany);
            }

            CarObj carObj = new CarObj();
            if (jsonObject.has("car") && !jsonObject.isNull("car")) {
                JSONObject carJsonObject = jsonObject.getJSONObject("car");
                if (carJsonObject.has("id")) {
                    carObj.setObjectID(carJsonObject.getInt("id"));
                }
                if (carJsonObject.has("number")) {
                    carObj.setNumber(carJsonObject.getString("number"));
                }
            }

            carDriverObj.setCar(carObj);
            carDriverObj.setDriver(driverObj);

            if (jsonObject.has("driver_car_id") && !jsonObject.isNull("driver_car_id")) {
                carDriverObj.setObjectID(jsonObject.getInt("driver_car_id"));
            }

            setResponseDriver(carDriverObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public LatLng getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(LatLng fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public LatLng getToLocation() {
        return toLocation;
    }

    public void setToLocation(LatLng toLocation) {
        this.toLocation = toLocation;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getPayby() {
        return payby;
    }

    public void setPayby(int payby) {
        this.payby = payby;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public VehicleObj getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleObj vehicle) {
        this.vehicle = vehicle;
    }

    public UserObj getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(UserObj requestUser) {
        this.requestUser = requestUser;
    }

    public CarDriverObj getResponseDriver() {
        return responseDriver;
    }

    public void setResponseDriver(CarDriverObj responseDriver) {
        this.responseDriver = responseDriver;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public Date getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(Date pickup_time) {
        this.pickup_time = pickup_time;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public float getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(float extra_price) {
        this.extra_price = extra_price;
    }
}
