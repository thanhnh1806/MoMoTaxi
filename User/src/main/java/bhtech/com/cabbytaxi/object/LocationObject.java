package bhtech.com.cabbytaxi.object;

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

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.services.NetworkServices;

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

    @Override
    public int getObjectID() {
        return super.getObjectID();
    }

    @Override
    public void setObjectID(int objectID) {
        super.setObjectID(objectID);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void setEmpty(boolean empty) {
        super.setEmpty(empty);
    }

    @Override
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public Date getUpdatedDate() {
        return super.getUpdatedDate();
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    @Override
    public JSONObject parseObjectToJson() {
        return super.parseObjectToJson();
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
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
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                setId(jsonObject.getString("id"));
            }

            if (jsonObject.has("location_address")) {
                setAddress(jsonObject.getString("location_address"));
            }
            if (jsonObject.has("location_latitude") && jsonObject.has("location_longitude")) {
                double lat = Double.parseDouble(jsonObject.getString("location_latitude"));
                double lng = Double.parseDouble(jsonObject.getString("location_longitude"));
                setLatLng(new LatLng(lat, lng));
            }

            if (jsonObject.has("create_date") && !jsonObject.isNull("create_date")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("update_date"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setCreatedDate(calendar.getTime());
            }

            if (jsonObject.has("update_date") && !jsonObject.isNull("update_date")) {
                Date date = TimeObject.getDateFromTimeUTC(jsonObject.getString("update_date"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                setUpdatedDate(calendar.getTime());
            }

            if (jsonObject.has("geometry")) {
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                if (geometry.has("location")) {
                    JSONObject location = geometry.getJSONObject("location");
                    int lat = location.getInt("lat");
                    int lng = location.getInt("lng");
                    setLatLng(new LatLng(lat, lng));
                }
            }


            if (jsonObject.has("name")) {
                setName(jsonObject.getString("name"));
            }

            if (jsonObject.has("vicinity")) {
                setAddress(name + " - " + jsonObject.getString("vicinity"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Intent showNavigationOnGoogleMap(LatLng latLng) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLng.toString().replace("lat/lng: (", "").replace(")", ""));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }

    public interface onGetCompleteAddress {
        void Success(String address);

        void Failure(Error error);
    }

    public static void getCompleteAddressString(Context context, LatLng latLng, final onGetCompleteAddress onFinish) {
        final Error error = new Error();
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
                error.setError(Error.DATA_NULL);
                onFinish.Failure(error);
            }
        } catch (Exception e) {
            String url = ContantValuesObject.COMPLETE_ADDRESS_API
                    + "?latlng=" + latLng.latitude + "," + latLng.longitude
                    + "&key=" + context.getString(R.string.google_server_key);
            if (NetworkObject.isNetworkConnect(context)) {
                NetworkServices.callAPI(context, url, "CompleteAddress", new NetworkServices.onCallApi() {
                    @Override
                    public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                        if (isSuccess) {
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                onFinish.Success(results.getJSONObject(1).getString("formatted_address"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        } else {
                            error.setError(Error.UNKNOWN_ERROR);
                            onFinish.Failure(error);
                        }
                    }
                });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                error.errorMessage = context.getString(R.string.please_check_your_network);
                onFinish.Failure(error);
            }
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

    public static ArrayList<String> getListCountry() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        return countries;
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
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }

    public interface onGetCountryFromLatLng {
        void Success(String address);

        void Failure(Error error);
    }

    public static void getCountryFromLatLng(final Context context, LatLng latLng,
                                            final onGetCountryFromLatLng onFinish) {
        final Error error = new Error();
        String url = ContantValuesObject.COMPLETE_ADDRESS_API
                + "?latlng=" + latLng.latitude + "," + latLng.longitude
                + "&key=" + context.getString(R.string.google_server_key);
        if (NetworkObject.isNetworkConnect(context)) {
            NetworkServices.callAPI(context, url, "Country", new NetworkServices.onCallApi() {
                @Override
                public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                    if (isSuccess) {
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            JSONObject countryNameJsonObj = results.getJSONObject(results.length() - 1);
                            String countryName = countryNameJsonObj.getString("formatted_address");
                            onFinish.Success(countryName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.setError(Error.UNKNOWN_ERROR);
                            onFinish.Failure(error);
                        }
                    } else {
                        error.setError(Error.UNKNOWN_ERROR);
                        onFinish.Failure(error);
                    }
                }
            });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.Failure(error);
        }
    }

    public interface onGetGeometryBounds {
        void Success(LatLng southwest, LatLng northeast);

        void Failure(Error error);
    }

    public static void getGeometryBounds(Context context, String address, final onGetGeometryBounds onFinish) {
        final Error error = new Error();
        String url = ContantValuesObject.COMPLETE_ADDRESS_API + "?address=" + address
                + "&key=" + context.getString(R.string.google_server_key);

        if (NetworkObject.isNetworkConnect(context)) {
            NetworkServices.callAPI(context, url, "GeometryBounds", new NetworkServices.onCallApi() {
                @Override
                public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                    if (isSuccess) {
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            JSONObject results0 = results.getJSONObject(0);
                            JSONObject geometry = results0.getJSONObject("geometry");
                            JSONObject bounds = geometry.getJSONObject("bounds");
                            JSONObject northeast = bounds.getJSONObject("northeast");
                            JSONObject southwest = bounds.getJSONObject("southwest");

                            LatLng northeastLatLng = new LatLng(northeast.getDouble("lat"), northeast.getDouble("lng"));
                            LatLng southwestLatLng = new LatLng(southwest.getDouble("lat"), southwest.getDouble("lng"));
                            onFinish.Success(southwestLatLng, northeastLatLng);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.setError(Error.UNKNOWN_ERROR);
                            onFinish.Failure(error);
                        }
                    } else {
                        error.setError(Error.UNKNOWN_ERROR);
                        onFinish.Failure(error);
                    }
                }
            });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.Failure(error);
        }
    }
}
