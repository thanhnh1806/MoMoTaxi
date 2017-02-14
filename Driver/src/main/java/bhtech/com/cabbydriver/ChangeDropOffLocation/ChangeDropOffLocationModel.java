package bhtech.com.cabbydriver.ChangeDropOffLocation;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 13/04/2016.
 */
public class ChangeDropOffLocationModel implements ChangeDropOffLocationInterface.Datasource {
    private Context context;
    private LatLng pickUpLatLng = TaxiRequestObj.getInstance().getFromLocation();
    private LatLng dropOffLatLng = TaxiRequestObj.getInstance().getToLocation();
    private String dropOffAddress;

    public ChangeDropOffLocationModel(Context context) {
        this.context = context;
    }

    @Override
    public void setDropOffLocation(LatLng latLng) {
        dropOffLatLng = latLng;
    }

    @Override
    public MarkerOptions getDropOffMarkerOptions() {
        return MarkerOptionsObject.addMarker(pickUpLatLng, R.drawable.icon_location2);
    }

    @Override
    public MarkerOptions getPickUpMarkerOptions() {
        return MarkerOptionsObject.addMarker(dropOffLatLng, R.drawable.icon_people);
    }

    @Override
    public ArrayList<LatLng> getListLatLng() {
        ArrayList<LatLng> list = new ArrayList<>();
        if (pickUpLatLng != null) {
            list.add(pickUpLatLng);
        }
        if (dropOffLatLng != null) {
            list.add(dropOffLatLng);
        }

        return list;
    }

    @Override
    public void setDropOffAddress(String s) {
        dropOffAddress = s;
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

    public interface ChangeDropOffLocation {
        void Success();

        void Failure(ErrorObj error);
    }

    public void changeDropOffLocation(final ChangeDropOffLocation onFinish) {
        TaxiRequestObj.getInstance().setToLocation(dropOffLatLng);
        TaxiRequestObj.getInstance().setToLocationAddress(dropOffAddress);
        TaxiRequestObj.getInstance().driverChangeDropOffLocation(context,
                new TaxiRequestObj.DriverChangeDropOffLocation() {
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
}
