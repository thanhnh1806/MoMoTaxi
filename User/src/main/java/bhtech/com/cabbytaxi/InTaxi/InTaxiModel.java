package bhtech.com.cabbytaxi.InTaxi;

import android.content.Context;
import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;

public class InTaxiModel {
    private Context context;
    private int statusRequest;

    public InTaxiModel(Context context) {
        this.context = context;
    }

    public int getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(int statusRequest) {
        this.statusRequest = statusRequest;
    }

    public void setStatusRequest(Context context, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            setStatusRequest(Integer.parseInt(jsonObject.getString("status")));
            if (jsonObject.has("hours")) {
                SharedPreference.set(context, "hours", jsonObject.getString("hours"));
            }
            if (jsonObject.has("mileage")) {
                SharedPreference.set(context, "mileage", jsonObject.getString("mileage"));
            }
            if (jsonObject.has("price")) {
                SharedPreference.set(context, "price", jsonObject.getString("price"));
            }
            if (jsonObject.has("extra_price")) {
                SharedPreference.set(context, "extra_price", jsonObject.getString("extra_price"));
            }
            if (jsonObject.has("payby")) {
                SharedPreference.set(context, "payby", jsonObject.getString("payby"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public float getDistance() {
//        Double from_lat = Double.valueOf(SharedPreference.get(context, "from_lat", ""));
//        Double from_lng = Double.valueOf(SharedPreference.get(context, "from_lng", ""));
//
//        Double to_lat = Double.valueOf(SharedPreference.get(context, "to_lat", ""));
//        Double to_lng = Double.valueOf(SharedPreference.get(context, "to_lng", ""));

        Double from_lat = TaxiRequestObj.getInstance().getFromLocation().latitude;
        Double from_lng = TaxiRequestObj.getInstance().getFromLocation().longitude;

        Double to_lat = TaxiRequestObj.getInstance().getToLocation().latitude;
        Double to_lng = TaxiRequestObj.getInstance().getToLocation().longitude;

        float[] results = new float[1];
        Location.distanceBetween(from_lat, from_lng, to_lat, to_lng, results);

        return results[0] / 1000;
    }

    public Date getEstimatedTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) (getDistance() * 60 / ContantValuesObject.Speed));
        return calendar.getTime();
    }

    public String getAddress() {
        return TaxiRequestObj.getInstance().getToLocationAddress();
    }

    public interface onGetCurrentRequest {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getCurrentRequest(final onGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                onFinish.Failure(error);
            }
        });
    }
}
