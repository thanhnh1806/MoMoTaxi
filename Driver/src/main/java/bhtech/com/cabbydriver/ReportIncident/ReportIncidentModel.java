package bhtech.com.cabbydriver.ReportIncident;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bhtech.com.cabbydriver.BaseModelInterface;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by thanh on 6/15/2016.
 */
public class ReportIncidentModel implements ReportIncidentInterface.Datasource {
    private Context context;

    public ReportIncidentModel(Context context) {
        this.context = context;
    }

    private LatLng driverLatLng;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setDriverLatLng(LatLng latLng) {
        driverLatLng = latLng;
    }

    public LatLng getDriverLatLng() {
        return driverLatLng;
    }

    @Override
    public String getCurrentDayInWeek() {
        return TimeObject.getStringDayOfWeek();
    }

    @Override
    public String getCurrentTime() {
        return TimeObject.getCurrentTime(new SimpleDateFormat("hh:mm a", Locale.US));
    }

    @Override
    public String getCurrentDay() {
        return TimeObject.getCurrentDate(new SimpleDateFormat("dd MMM", Locale.US));
    }

    @Override
    public MarkerOptions getMarkerOptions() {
        return MarkerOptionsObject.addMarker(driverLatLng, R.drawable.icon_car_arrived_small);
    }

    private String getCurrentDateTime() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return TimeObject.getCurrentDateTime(dateFormat, timeFormat);
    }

    public interface OnSendReportIncident extends BaseModelInterface {

    }

    public void sendReportIncident(final OnSendReportIncident onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("driver_car_id", Arrays.asList(String.valueOf(CarDriverObj.getInstance().getObjectID())));
        params.put("incidentStatus", Arrays.asList(String.valueOf(1)));
        params.put("locationAddressIncident", Arrays.asList(address));
        params.put("latitude", Arrays.asList(String.valueOf(driverLatLng.latitude)));
        params.put("longitude", Arrays.asList(String.valueOf(driverLatLng.longitude)));
        params.put("timeIncident", Arrays.asList(getCurrentDateTime()));

        NetworkObject.callAPI(context, ContantValuesObject.REPORT_INCIDENT, NetworkObject.POST,
                "ReportIncident", headers, params, new NetworkObject.onCallApi() {
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

    public interface OnGetCurrentAddress extends BaseModelInterface {

    }

    public void getCurrentAddress(final OnGetCurrentAddress onFinish) {
        LocationObject.getCompleteAddressString(context, driverLatLng, new LocationObject.onGetCompleteAddress() {
            @Override
            public void Success(String address) {
                setAddress(address);
                onFinish.success();
            }

            @Override
            public void Failure(ErrorObj error) {
                Log.e("getCurrentAddress", error.errorMessage);
            }
        });
    }
}
