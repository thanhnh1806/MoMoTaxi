package bhtech.com.cabbydriver.object;

import org.json.JSONObject;

import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.TripHistory.TripHistoryAdapter;

/**
 * Created by duongpv on 4/12/16.
 */
public class TripObject extends BaseObject {
    public String fromAddress;
    public String toAddress;
    public double distance;
    public double hours;
    public UserObj customer;
    public DriverObj driver;
    public CarObj car;
    public TaxiRequestObj requestObj;

    public TripObject () {
        customer = new UserObj();
        driver = new DriverObj();
        car = new CarObj();
        requestObj = new TaxiRequestObj();
    }

    public void parseJsonToObject (JSONObject object) {
        super.parseJsonToObject(object);
        requestObj.parseJsonToObject(object);
        distance = requestObj.getEnd_mileage() - requestObj.getStart_mileage();
        hours = requestObj.getHours()/60;
        toAddress = requestObj.getToLocationAddress();
        fromAddress = requestObj.getFromLocationAddress();
    }
}
