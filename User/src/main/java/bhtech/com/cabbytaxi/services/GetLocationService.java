package bhtech.com.cabbytaxi.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.UserObj;


/**
 * Created by thanh_nguyen on 20/04/2016.
 */
public class GetLocationService extends Service {
    Context context = this;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationObject.getMyLocation(this, 0, new LocationObject.OnGetLocation() {
            @Override
            public void Success(Location location) {
                UserObj.getInstance().setLocation(LocationObject.locationToLatLng(location));
                Log.w("GetLocationService", "Call API Update Location");

                if (UserObj.getInstance().getUser_token() != null) {
                    Map<String, List<String>> headers = new HashMap<>();
                    headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

                    Map<String, List<String>> params = new HashMap<>();
                    params.put("latitude", Arrays.asList(String.valueOf(location.getLatitude())));
                    params.put("longitude", Arrays.asList(String.valueOf(location.getLongitude())));

                    NetworkServices.callAPI(context, ContantValuesObject.UPDATE_USER_LOCATION +
                                    UserObj.getInstance().getObjectID(), NetworkServices.PUT,
                            headers, params, new NetworkServices.onCallApi() {
                                @Override
                                public void onFinish(boolean isSuccess, JSONObject jsonObject) {

                                }
                            });
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
