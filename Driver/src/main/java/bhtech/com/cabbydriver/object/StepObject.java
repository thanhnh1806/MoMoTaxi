package bhtech.com.cabbydriver.object;

import android.media.Image;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by thanh_nguyen on 06/04/2016.
 */
public class StepObject extends BaseObject {
    public Float distanceValue;
    public String distanceText;
    public Float durationValue;
    public String durationText;
    public LatLng end_location;
    public String instructions;
    public String maneuver;
    public Image maneuverImage;
    public String polyline;
    public LatLng start_location;

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        String LOG = "StepObject";
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

            if (jsonObject.has("end_location")) {
                Double lat = jsonObject.getJSONObject("end_location").getDouble("lat");
                Double lng = jsonObject.getJSONObject("end_location").getDouble("lng");
                this.end_location = new LatLng(lat, lng);
            } else {
                Log.e(LOG, "Dont has end_location");
            }

            if (jsonObject.has("html_instructions")) {
                this.instructions = jsonObject.getString("html_instructions");
            } else {
                Log.e(LOG, "Dont has instructions");
            }

            if (jsonObject.has("start_location")) {
                Double lat = jsonObject.getJSONObject("start_location").getDouble("lat");
                Double lng = jsonObject.getJSONObject("start_location").getDouble("lng");
                this.start_location = new LatLng(lat, lng);
            } else {
                Log.e(LOG, "Dont has end_location");
            }

            if (jsonObject.has("maneuver")) {
                this.maneuver = jsonObject.getString("maneuver");
            } else {
                Log.e(LOG, "Dont has maneuver");
            }

            if (jsonObject.has("polyline")) {
                this.polyline = jsonObject.getJSONObject("polyline").getString("points");
            } else {
                Log.e(LOG, "Dont has polyline");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
