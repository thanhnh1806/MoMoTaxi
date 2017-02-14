package bhtech.com.cabbydriver.ChangePickUpLocation;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 12/04/2016.
 */
public class ChangePickUpLocationModel implements ChangePickUpLocationInterface.Datasource {
    private Context context;
    private LatLng driverLatLng;
    private LatLng pickUpLatLng = TaxiRequestObj.getInstance().getFromLocation();
    private String pickUpAddress;

    public ChangePickUpLocationModel(Context context) {
        this.context = context;
    }

    @Override
    public void setPickUpLocation(LatLng latLng) {
        pickUpLatLng = latLng;
    }

    @Override
    public MarkerOptions getCustomerMarkerOptions() {
        return MarkerOptionsObject.addMarker(pickUpLatLng, R.drawable.icon_location2);
    }

    @Override
    public MarkerOptions getDriverMarkerOptions() {
        return MarkerOptionsObject.addMarker(driverLatLng, R.drawable.icon_people);
    }

    @Override
    public ArrayList<LatLng> getListLatLng() {
        ArrayList<LatLng> list = new ArrayList<>();
        if (driverLatLng != null) {
            list.add(driverLatLng);
        }
        if (pickUpLatLng != null) {
            list.add(pickUpLatLng);
        }

        return list;
    }

    @Override
    public void setPickUpAddress(String address) {
        pickUpAddress = address;
    }

    public interface ChangePickUpLocation {
        void Success();

        void Failure(ErrorObj error);
    }

    public void changePickUpLocation(final ChangePickUpLocation onFinish) {
        TaxiRequestObj.getInstance().setFromLocation(pickUpLatLng);
        TaxiRequestObj.getInstance().setFromLocationAddress(pickUpAddress);
        TaxiRequestObj.getInstance().driverChangePickUpLocation(context,
                new TaxiRequestObj.DriverChangePickUpLocation() {
                    @Override
                    public void Success() {
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public void getDriverLocation() {
        driverLatLng = CarDriverObj.getInstance().getLocation();
    }

    public int getCustomerStatus(String data) {
        try {
            JSONObject statusObject = new JSONObject(data);
            int status = statusObject.getInt("status");
            if (status == ContantValuesObject.TaxiRequestStatusUserConfirmed) {
                return ContantValuesObject.TaxiRequestStatusUserConfirmed;
            } else {
                return ContantValuesObject.TaxiRequestStatusCancelled;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return ContantValuesObject.TaxiRequestStatusCancelled;
        }
    }
}
