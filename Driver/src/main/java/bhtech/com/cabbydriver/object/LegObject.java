package bhtech.com.cabbydriver.object;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 06/04/2016.
 */
public class LegObject extends BaseObject {
    public Float distanceValue;
    public String distanceText;

    public Float durationValue;
    public String durationText;

    public String end_address;
    public LatLng end_location;

    public String start_address;
    public LatLng start_location;
    public ArrayList<StepObject> steps;

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        String LOG = "LegObject";
        try {
            if (jsonObject.has("distance")) {
                this.distanceValue = (float) jsonObject.getJSONObject("distance").getDouble("value");
                this.distanceText = jsonObject.getJSONObject("distance").getString("text");
            } else {
                Log.e(LOG, "Dont has distance");
            }

            if (jsonObject.has("duration")) {
                this.durationValue = (float) jsonObject.getJSONObject("duration").getDouble("value");
                this.durationText = jsonObject.getJSONObject("duration").getString("text");
            } else {
                Log.e(LOG, "Dont has duration");
            }

            if (jsonObject.has("end_address")) {
                this.end_address = jsonObject.getString("end_address");
            } else {
                Log.e(LOG, "Dont has end_address");
            }

            if (jsonObject.has("end_location")) {
                Double lat = jsonObject.getJSONObject("end_location").getDouble("lat");
                Double lng = jsonObject.getJSONObject("end_location").getDouble("lng");
                this.end_location = new LatLng(lat, lng);
            } else {
                Log.e(LOG, "Dont has end_location");
            }

            if (jsonObject.has("start_address")) {
                this.start_address = jsonObject.getString("start_address");
            } else {
                Log.e(LOG, "Dont has start_address");
            }

            if (jsonObject.has("start_location")) {
                Double lat = jsonObject.getJSONObject("start_location").getDouble("lat");
                Double lng = jsonObject.getJSONObject("start_location").getDouble("lng");
                this.start_location = new LatLng(lat, lng);
            } else {
                Log.e(LOG, "Dont has end_location");
            }

            if (jsonObject.has("steps")) {
                JSONArray stepsJSONArray = jsonObject.getJSONArray("steps");
                steps = new ArrayList<>();
                for (int i = 0; i < stepsJSONArray.length(); i++) {
                    StepObject step = new StepObject();
                    step.parseJsonToObject(stepsJSONArray.getJSONObject(i));
                    steps.add(step);
                }
            } else {
                Log.e(LOG, "Dont has steps");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
