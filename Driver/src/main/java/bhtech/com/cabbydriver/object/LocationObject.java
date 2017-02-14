package bhtech.com.cabbydriver.object;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import bhtech.com.cabbydriver.R;

/**
 * Created by thanh_nguyen02 on 22/12/2015.
 */
public class LocationObject extends BaseObject {
    private String id;
    private String name;
    private String address;
    private LatLng latLng;

    public LocationObject() {
    }

    public LocationObject(String address, LatLng latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (jsonObject.has("geometry")) {
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                if (geometry.has("location")) {
                    JSONObject location = geometry.getJSONObject("location");
                    int lat = location.getInt("lat");
                    int lng = location.getInt("lng");
                    setLatLng(new LatLng(lat, lng));
                }
            }

            if (jsonObject.has("id")) {
                setId(jsonObject.getString("id"));
            }

            if (jsonObject.has("name")) {
                setName(jsonObject.getString("name"));
            }

            if (jsonObject.has("vicinity")) {
                setAddress(jsonObject.getString("vicinity"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface onGetCompleteAddress {
        void Success(String address);

        void Failure(ErrorObj error);
    }

    public static void getCompleteAddressString(Context context, LatLng latLng, final onGetCompleteAddress onFinish) {
        final ErrorObj error = new ErrorObj();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                onFinish.Success(strReturnedAddress.toString());
            } else {
                error.setError(ErrorObj.DATA_NULL);
                onFinish.Failure(error);
            }
        } catch (Exception e) {
            String url = ContantValuesObject.COMPLETE_ADDRESS_API
                    + "?latlng=" + latLng.latitude + "," + latLng.longitude
                    + "&key=" + context.getString(R.string.google_geocoding_key);
            NetworkObject.callAPI(context, url, "CompleteAddress", new NetworkObject.onCallApi() {
                @Override
                public void Success(JSONObject jsonObject) {
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        onFinish.Success(results.getJSONObject(1).getString("formatted_address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error.setError(ErrorObj.UNKNOWN_ERROR);
                        onFinish.Failure(error);
                    }
                }

                @Override
                public void Failure(ErrorObj error) {
                    error.setError(ErrorObj.UNKNOWN_ERROR);
                    onFinish.Failure(error);
                }
            });
        }
    }

    public static LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static Location latlngToLocation(LatLng latLng) {
        if (latLng != null) {
            Location location = new Location("");
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            location.setTime(new Date().getTime());
            return location;
        } else {
            return null;
        }
    }

    public static double calculateDistance(LatLng to, LatLng from) {
        double earthRadius = 3958.75;
        Double lat_a, lat_b, lng_a, lng_b;
        lat_a = to.latitude;
        lat_b = from.latitude;
        lng_a = to.longitude;
        lng_b = from.longitude;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        int meterConversion = 1609;
        return (distance * meterConversion); // results met
    }

    public interface OnGetLocation {
        void Success(Location location);
    }

    public static void getMyLocation(Context context, int milliseconds, final OnGetLocation OnFinish) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                milliseconds, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            OnFinish.Success(location);
                        } else {
                            Log.w("GetMyLocation", "Location null");
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.w("GetMyLocation", "onStatusChanged");
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Log.w("GetMyLocation", "onProviderEnabled");
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Log.w("GetMyLocation", "onProviderDisabled");
                    }
                });
    }

    public static Intent showNavigationOnGoogleMap(LatLng latLng) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLng.toString().replace("lat/lng: (", "").replace(")", ""));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }
}
