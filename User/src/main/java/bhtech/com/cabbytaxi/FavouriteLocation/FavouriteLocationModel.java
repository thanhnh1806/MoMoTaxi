package bhtech.com.cabbytaxi.FavouriteLocation;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.MarkerOptionsObject;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 03/02/2016.
 */
public class FavouriteLocationModel implements FavouriteLocationInterface.Datasource {
    private Context context;
    private ArrayList<LocationObject> listFavouriteLocation;
    private ArrayList<LocationObject> listFavouriteLocationTemp;
    private LatLng myLocation;
    private LatLng latLngMarker;
    private int listViewPositionClick = 0;
    private int monthUserChoose = TimeObject.getCurrentMonth();
    private String completeAddress;
    boolean addViewVisible = false;
    private LatLng southwest, northeast;

    public FavouriteLocationModel(Context context) {
        this.context = context;
    }

    @Override
    public boolean isAddViewVisible() {
        return addViewVisible;
    }

    @Override
    public void setAddViewVisible(boolean b) {
        addViewVisible = b;
    }

    @Override
    public ArrayList<LocationObject> getListFavouriteLocation() {
        return this.listFavouriteLocation;
    }

    public LatLng getSouthwest() {
        return southwest;
    }

    public LatLng getNortheast() {
        return northeast;
    }

    @Override
    public ArrayList<String> getListMonth() {
        return TimeObject.getListMonth();
    }

    @Override
    public void setListItemPositionClick(int position) {
        listViewPositionClick = position;
    }

    @Override
    public int getListItemPositionClick() {
        return listViewPositionClick;
    }


    @Override
    public int getMonthUserChoose() {
        return monthUserChoose;
    }

    @Override
    public void setMonthUserChoose(int position) {
        monthUserChoose = position;
    }

    private void setListFavouriteLocation(ArrayList<LocationObject> list) {
        listFavouriteLocation = list;
    }

    public LatLng getLatLngMarker() {
        return latLngMarker;
    }

    @Override
    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public void setMyLocation(LatLng location) {
        myLocation = location;
    }

    public void setLatLngMarker(LatLng latLngMarker) {
        this.latLngMarker = latLngMarker;
    }

    @Override
    public void setLatLngMapClick(LatLng latLng) {
        setLatLngMarker(latLng);
    }

    public LatLng getMyLocation() {
        return myLocation;
    }

    public void sortListByMonth() {
        ArrayList<LocationObject> list = new ArrayList<>();
        for (int i = 0; i < listFavouriteLocationTemp.size(); i++) {
            Date date = listFavouriteLocationTemp.get(i).getCreatedDate();
            if (monthUserChoose == date.getMonth()) {
                list.add(listFavouriteLocationTemp.get(i));
            }
        }
        listFavouriteLocation = list;
    }

    public interface OnGetCompleteAddress {
        void Success();

        void Failure(Error error);
    }

    public void getCompleteAddress(final OnGetCompleteAddress onFinish) {
        LocationObject.getCompleteAddressString(context, latLngMarker, new LocationObject.onGetCompleteAddress() {
            @Override
            public void Success(String address) {
                completeAddress = address;
                onFinish.Success();
            }

            @Override
            public void Failure(Error error) {
                onFinish.Failure(error);
            }
        });
    }

    @Override
    public MarkerOptions getMarkerOptionsAddView(Context context) {
        LatLng latLng = getLatLngMarker();
        if (latLng != null) {
            View markerLayout = MarkerOptionsObject.customLayoutMarker(context,
                    R.layout.marker_layout_favourite_location);
            TextView tvAddress = (TextView) markerLayout.findViewById(R.id.tvAddress);
            tvAddress.setText(completeAddress);

            MarkerOptions markerOptions = MarkerOptionsObject.addMarker2(context, markerLayout,
                    latLng, completeAddress);
            return markerOptions;
        } else {
            return null;
        }
    }

    @Override
    public MarkerOptions getMarkerOptionsEditView(Context context) {
        return MarkerOptionsObject.addMarker(getLatLngMarker(), R.drawable.ic_dealer_red);
    }

    public interface OnDeleteFavouriteLocation {
        void Success();

        void Failure(Error error);
    }

    public void deleteFavouriteLocation(Context context, final OnDeleteFavouriteLocation onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
            headers.put("User-Agent", Arrays.asList("Android"));

            NetworkServices.callAPI(context, ContantValuesObject.FAVOURITE_LOCATION_ENDPOINT + "/"
                            + listFavouriteLocation.get(listViewPositionClick).getId(),
                    NetworkServices.DELETE, headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == Error.NO_ERROR) {
                                    onFinish.Success();
                                } else {
                                    error.errorMessage = jsonObject.getString(ContantValuesObject.MESSAGE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface OnEditFavouriteLocation {
        void Success();

        void Failure(Error error);
    }

    public void editFavouriteLocation(Context context, final OnEditFavouriteLocation onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
            headers.put("User-Agent", Arrays.asList("Android"));

            String location_address = completeAddress;
            String location_latitude = String.valueOf(getLatLngMarker().latitude);
            String location_longitude = String.valueOf(getLatLngMarker().longitude);

            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("location_address", Arrays.asList(location_address));
            parameters.put("location_latitude", Arrays.asList(location_latitude));
            parameters.put("location_longitude", Arrays.asList(location_longitude));

            NetworkServices.callAPI(context, ContantValuesObject.FAVOURITE_LOCATION_ENDPOINT + "/"
                            + getListFavouriteLocation().get(getListItemPositionClick()).getId(),
                    NetworkServices.PUT, headers, parameters, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt("Code") == 0) {
                                    onFinish.Success();
                                } else {
                                    error.errorMessage = jsonObject.getString("Message");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onAddFavouriteLocation {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void addFavouriteLocation(final Context context, final onAddFavouriteLocation onFinish) {
        final Error error = new Error();
        String location_address = completeAddress;
        boolean duplicateLocationAddress = false;
        for (int i = 0; i < listFavouriteLocation.size(); i++) {
            if (location_address.equalsIgnoreCase(listFavouriteLocation.get(i).getAddress())) {
                duplicateLocationAddress = true;
            }
        }
        if (duplicateLocationAddress) {
            error.setError(Error.DATA_DUPLICATE);
            error.errorMessage = context.getString(R.string.favourite_location_already_exits);
            onFinish.Failure(error);
        } else {
            if (NetworkObject.isNetworkConnect(context)) {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));
                headers.put("User-Agent", Arrays.asList("Android"));


                String location_latitude = String.valueOf(getLatLngMarker().latitude);
                String location_longitude = String.valueOf(getLatLngMarker().longitude);

                Map<String, List<String>> parameters = new HashMap<>();
                parameters.put("location_address", Arrays.asList(location_address));
                parameters.put("location_latitude", Arrays.asList(location_latitude));
                parameters.put("location_longitude", Arrays.asList(location_longitude));
                parameters.put("customers_id", Arrays.asList(String.valueOf(UserObj.getInstance().getObjectID())));

                NetworkServices.callAPI(context, ContantValuesObject.FAVOURITE_LOCATION_ENDPOINT,
                        NetworkServices.POST, headers, parameters, new NetworkServices.onCallApi() {
                            @Override
                            public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getInt("Code") == 0) {
                                        onFinish.Success();
                                    } else {
                                        error.errorMessage = jsonObject.getString("Message");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                error.setError(Error.NETWORK_DISCONNECT);
                onFinish.Failure(error);
            }
        }
    }

    public interface onGetListFavouriteLocation {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getListFavouriteLocation(Context context, final onGetListFavouriteLocation onFinish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.GET_LIST_FAVOURITE_LOCATION_ENDPOINT +
                            UserObj.getInstance().getObjectID(), NetworkServices.GET, headers,
                    null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == Error.NO_ERROR) {
                                    ArrayList<LocationObject> list = new ArrayList<>();
                                    JSONArray jsonArray = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        LocationObject locationObject = new LocationObject();
                                        locationObject.parseJsonToObject(object);
                                        list.add(locationObject);
                                    }

                                    listFavouriteLocationTemp = list;
                                    listFavouriteLocation = list;
                                    onFinish.Success();
                                } else {
                                    error.setError(Error.UNKNOWN_ERROR);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface onGetGeometryBounds {
        void Success();

        void Failure(Error error);
    }

    public void getGeometryBounds(final onGetGeometryBounds onFinish) {
        LocationObject.getCountryFromLatLng(context, UserObj.getInstance().getLocation(),
                new LocationObject.onGetCountryFromLatLng() {
                    @Override
                    public void Success(String address) {
                        LocationObject.getGeometryBounds(context, address, new LocationObject.onGetGeometryBounds() {
                            @Override
                            public void Success(LatLng southwest, LatLng northeast) {
                                FavouriteLocationModel.this.southwest = southwest;
                                FavouriteLocationModel.this.northeast = northeast;
                                onFinish.Success();
                            }

                            @Override
                            public void Failure(Error error) {
                                onFinish.Failure(error);
                            }
                        });
                    }

                    @Override
                    public void Failure(Error error) {
                        onFinish.Failure(error);
                    }
                });
    }
}
