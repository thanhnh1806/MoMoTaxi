package bhtech.com.cabbydriver.services;

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

import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.LocationObject;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

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
                if (CarDriverObj.getInstance().getObjectID() > 0) {
                    CarDriverObj.getInstance().setLocation(LocationObject.locationToLatLng(location));
                    Log.w("GetLocationService", "Call API Update Location");
                    Map<String, List<String>> headers = new HashMap<>();
                    headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

                    Map<String, List<String>> params = new HashMap<>();
                    params.put("latitude", Arrays.asList(String.valueOf(location.getLatitude())));
                    params.put("longitude", Arrays.asList(String.valueOf(location.getLongitude())));

                    NetworkObject.callAPI(context, ContantValuesObject.UPDATE_DRIVER_LOCATION +
                                    CarDriverObj.getInstance().getObjectID(), NetworkObject.PUT,
                            "GetLocationService", headers, params, new NetworkObject.onCallApi() {
                                @Override
                                public void Success(JSONObject jsonObject) {

                                }

                                @Override
                                public void Failure(ErrorObj error) {

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
