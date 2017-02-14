package bhtech.com.cabbydriver.object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 06/04/2016.
 */
public class RouteObject extends BaseObject {
    public String overview_polyline;
    public String summary;
    public ArrayList<LegObject> legs;

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        String LOG = "RouteObject";
        try {
            if (jsonObject.has("overview_polyline")) {
                JSONObject overview_polyline = jsonObject.getJSONObject("overview_polyline");
                if (overview_polyline.has("points")) {
                    this.overview_polyline = overview_polyline.getString("points");
                } else {
                    Log.e(LOG, "Dont has points");
                }
            } else {
                Log.e(LOG, "Dont has overview_polyline");
            }

            if (jsonObject.has("summary")) {
                this.summary = jsonObject.getString("summary");
            } else {
                Log.e(LOG, "Dont has summary");
            }

            if (jsonObject.has("legs")) {
                legs = new ArrayList<>();
                JSONArray legJSONArray = jsonObject.getJSONArray("legs");
                for (int i = 0; i < legJSONArray.length(); i++) {
                    LegObject leg = new LegObject();
                    leg.parseJsonToObject(legJSONArray.getJSONObject(i));
                    legs.add(leg);
                }
            } else {
                Log.e(LOG, "Dont has legs");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
