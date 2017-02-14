package bhtech.com.cabbytaxi.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import bhtech.com.cabbytaxi.SupportClass.DriverChargeCustomerInterface;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.TimeObject;

/**
 * Created by thanh_nguyen02 on 27/01/2016.
 */
public class DriverChargeCustomerModel implements DriverChargeCustomerInterface.DataSource {
    public static final DateFormat timeFormatAMPM = new SimpleDateFormat("hh:mm a", Locale.US);
    public static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    @Override
    public String getDate() {
        return TimeObject.getCurrentDate(dateFormat);
    }

    @Override
    public String getTime() {
        TimeObject.getCurrentHour();
        return TimeObject.getCurrentTime(timeFormatAMPM);
    }

    @Override
    public String getCustomerName() {
        try {
            //TODO
            return TaxiRequestObj.getInstance().getRequestUser().getUsername();
        } catch (Exception e) {
            return "Mr Zaid";
        }
    }

    @Override
    public String getStartAddress() {
        try {
            //TODO
            if (TaxiRequestObj.getInstance().getFromLocationAddress() != null) {
                return TaxiRequestObj.getInstance().getFromLocationAddress();
            } else {
                return "Persiaran KLCC";
            }
        } catch (Exception e) {
            return "Persiaran KLCC";
        }
    }

    @Override
    public String getStopAddress() {
        try {
            //TODO
            if (TaxiRequestObj.getInstance().getToLocationAddress() != null) {
                return TaxiRequestObj.getInstance().getToLocationAddress();
            } else {
                return "Menara 1MK";
            }

        } catch (Exception e) {
            return "Menara 1MK";
        }
    }

    @Override
    public String getMinutes() {
        try {
            //TODO
            return "23";
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getKm() {
        try {
            //TODO
            return String.valueOf(TaxiRequestObj.getInstance().getEnd_mileage());
        } catch (Exception e) {
            return "10.4";
        }
    }

    @Override
    public String getCharge() {
        try {
            //TODO
            return String.valueOf(TaxiRequestObj.getInstance().getPrice());
        } catch (Exception e) {
            return "15.00";
        }
    }
}
