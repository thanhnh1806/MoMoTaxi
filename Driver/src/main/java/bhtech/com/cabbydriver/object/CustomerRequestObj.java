package bhtech.com.cabbydriver.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by thanh_nguyen on 23/02/2016.
 */
public class CustomerRequestObj extends BaseObject {
    private String username;
    private int status;
    private String from_address;
    private String from_latitude;
    private String from_longitude;
    private String from_name;
    private String to_address;
    private String to_latitude;
    private String to_longitude;
    private String to_name;
    private String mileage;
    private String price;
    private String start_time;
    private String hours;
    private String rate;
    private int customers_id;
    private int driver_car_id;
    private Date pickup_time;
    private int car_class;
    private String extra_price;
    private String payby;
    private int distance;

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
            setUsername(jsonObject.getString("username"));
            setObjectID(jsonObject.getInt("id"));
            setStatus(jsonObject.getInt("status"));
            setFrom_address(jsonObject.getString("from_address"));
            setFrom_latitude(jsonObject.getString("from_latitude"));
            setFrom_longitude(jsonObject.getString("from_longitude"));

            String[] fromName = getFrom_address().split(",");
            setFrom_name(fromName[0]);

            setTo_address(jsonObject.getString("to_address"));
            setTo_latitude(jsonObject.getString("to_latitude"));
            setTo_longitude(jsonObject.getString("to_longitude"));

            String[] toName = getTo_address().split(",");
            setTo_name(toName[0]);

            setCustomers_id(jsonObject.getInt("customers_id"));
            setDriver_car_id(jsonObject.getInt("driver_car_id"));

            if (!jsonObject.isNull("pickup_time")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("pickup_time"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setPickup_time(calendar.getTime());
            } else {
                setPickup_time(null);
            }

            setCar_class(jsonObject.getInt("car_class"));
            setDistance(jsonObject.getInt("distance"));
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(String from_latitude) {
        this.from_latitude = from_latitude;
    }

    public String getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(String from_longitude) {
        this.from_longitude = from_longitude;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(String to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getTo_longitude() {
        return to_longitude;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public void setTo_longitude(String to_longitude) {
        this.to_longitude = to_longitude;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(int customers_id) {
        this.customers_id = customers_id;
    }

    public int getDriver_car_id() {
        return driver_car_id;
    }

    public void setDriver_car_id(int driver_car_id) {
        this.driver_car_id = driver_car_id;
    }

    public Date getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(Date pickup_time) {
        this.pickup_time = pickup_time;
    }

    public int getCar_class() {
        return car_class;
    }

    public void setCar_class(int car_class) {
        this.car_class = car_class;
    }

    public String getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(String extra_price) {
        this.extra_price = extra_price;
    }

    public String getPayby() {
        return payby;
    }

    public void setPayby(String payby) {
        this.payby = payby;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
