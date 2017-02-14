package bhtech.com.cabbydriver.ChooseRouteToCustomer;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.RouteObject;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 06/04/2016.
 */
public class ChooseRouteToCustomerModel implements ChooseRouteToCustomerInterface.Datasource {
    Context context;
    LatLng fromLatLng = CarDriverObj.getInstance().getLocation();
    LatLng toLatLng = TaxiRequestObj.getInstance().getFromLocation();
    LatLng customerLatLng = null;
    int polylineUserClick = 0;
    boolean enableButtonGo = false;

    public ChooseRouteToCustomerModel(Context context) {
        this.context = context;
    }

    ArrayList<RouteObject> listRoute;
    ArrayList<PolylineOptions> listPolylineOptions;
    ArrayList<Polyline> listPolyline;

    public LatLng getFromLatLng() {
        return fromLatLng;
    }

    public LatLng getToLatLng() {
        return toLatLng;
    }

    public LatLng getCustomerLatLng() {
        return customerLatLng;
    }

    public int getDuration() {
        return (int) (listRoute.get(polylineUserClick).legs.get(0).durationValue / 60);
    }

    public int getDistance() {
        return (int) (listRoute.get(polylineUserClick).legs.get(0).distanceValue / 1000);
    }

    public String getGoVia() {
        return listRoute.get(polylineUserClick).summary;
    }

    public boolean isEnableButtonGo() {
        return enableButtonGo;
    }

    public void setEnableButtonGo(boolean enableButtonGo) {
        this.enableButtonGo = enableButtonGo;
    }

    @Override
    public String getUsername() {
        try {
            return TaxiRequestObj.getInstance().getRequestUser().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getStartAddress() {
        try {
            return TaxiRequestObj.getInstance().getFromLocationAddress();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public MarkerOptions getCustomerMarkerOptions() {
        if (customerLatLng != null) {
            return MarkerOptionsObject.addMarker(customerLatLng, R.drawable.icon_people);
        } else {
            return null;
        }
    }

    public ArrayList<PolylineOptions> getListPolylineOptions() {
        return listPolylineOptions;
    }

    public void setPolylineUserClick(int polylineUserClick) {
        this.polylineUserClick = polylineUserClick;
    }

    public int getPolylineUserClick() {
        return polylineUserClick;
    }

    public void setListPolyline(ArrayList<Polyline> listPolyline) {
        this.listPolyline = listPolyline;
    }

    @Override
    public MarkerOptions getFromMarkerOptions() {
        return MarkerOptionsObject.addMarker(fromLatLng, R.drawable.taxi_icon);
    }

    @Override
    public MarkerOptions getToMarkerOptions() {
        return MarkerOptionsObject.addMarker(toLatLng, R.drawable.des_location);
    }

    public void setListPolylineOptions() {
        listPolylineOptions = new ArrayList<>();
        PolylineOptions options;
        for (int i = 0; i < listRoute.size(); i++) {
            if (i == polylineUserClick) {
                options = new PolylineOptions().color((context.getResources().getColor(R.color.polyline_choose)))
                        .width(10).geodesic(true).zIndex(1);
            } else {
                options = new PolylineOptions().color((context.getResources().getColor(R.color.polyline_unchoose)))
                        .width(10).geodesic(true).zIndex(0);
            }

            options.addAll(PolyUtil.decode(listRoute.get(i).overview_polyline));
            listPolylineOptions.add(options);
        }
    }

    public String getCustomerPhoneNumber() {
        return TaxiRequestObj.getInstance().getRequestUser().getPhoneNumber();
    }

    public void setCloseNavigation(boolean b) {
        SharedPreference.set(context, ContantValuesObject.IS_CLOSE_NAVI, String.valueOf(b));
    }

    public int getCustomerStatus(String data) {
        try {
            JSONObject statusObject = new JSONObject(data);
            int status = statusObject.getInt("status");
            if (status == ContantValuesObject.TaxiRequestStatusUserConfirmed) {
                setEnableButtonGo(true);
                return ContantValuesObject.TaxiRequestStatusUserConfirmed;
            } else {
                return ContantValuesObject.TaxiRequestStatusCancelled;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return ContantValuesObject.TaxiRequestStatusCancelled;
        }
    }

    public interface OnGetRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getRequest(final OnGetRequest onFinish) {
        int requestId;
        if (TaxiRequestObj.getInstance().getRequestId() > 0) {
            requestId = TaxiRequestObj.getInstance().getRequestId();
        } else {
            requestId = Integer.parseInt(SharedPreference.get(context, ContantValuesObject.REQUEST_ID, "0"));
        }

        TaxiRequestObj.getInstance().getRequest(context, requestId, new TaxiRequestObj.onGetRequest() {
            @Override
            public void Success() {
                fromLatLng = CarDriverObj.getInstance().getLocation();

                if (TaxiRequestObj.getInstance().getFromLocation() != null) {
                    toLatLng = TaxiRequestObj.getInstance().getFromLocation();
                }

                if (TaxiRequestObj.getInstance().getRequestUser().getLocation() != null) {
                    customerLatLng = TaxiRequestObj.getInstance().getRequestUser().getLocation();
                }

                onFinish.Success();
            }

            @Override
            public void Failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }

    public interface OnDriverStartGoToPickUp {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverStartGoToPickUp(final OnDriverStartGoToPickUp onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(ContantValuesObject.TaxiRequestStatusDrivingToPassenger)));

        final String request_id = String.valueOf(TaxiRequestObj.getInstance().getRequestId());
        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + request_id, NetworkObject.PUT,
                "DriverChooseCustomer", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                onFinish.Success();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface OnGetDirections {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getDirections(final OnGetDirections onFinish) {
        final ErrorObj error = new ErrorObj();
        if (fromLatLng != null && toLatLng != null) {
            String fromLat = String.valueOf(fromLatLng.latitude);
            String fromLng = String.valueOf(fromLatLng.longitude);
            String toLat = String.valueOf(toLatLng.latitude);
            String toLng = String.valueOf(toLatLng.longitude);
            String language = "vi";
            String mode = "driving";
            String alternatives = String.valueOf(true);

            final String url = ContantValuesObject.GET_DIRECTIONS_ON_GOOGLE_MAP
                    + "?origin=" + fromLat + "," + fromLng
                    + "&destination=" + toLat + "," + toLng
                    + "&language=" + language
                    + "&mode=" + mode
                    + "&alternatives=" + alternatives;

            NetworkObject.callAPI(context, url, "Directions", new NetworkObject.onCallApi() {
                @Override
                public void Success(JSONObject jsonObject) {
                    if (jsonObject.has("routes")) {
                        listRoute = new ArrayList<>();
                        try {
                            JSONArray routesJSONArray = jsonObject.getJSONArray("routes");
                            for (int i = 0; i < routesJSONArray.length(); i++) {
                                RouteObject route = new RouteObject();
                                route.parseJsonToObject(routesJSONArray.getJSONObject(i));
                                listRoute.add(route);
                            }
                            int status = TaxiRequestObj.getInstance().getStatus();
                            if (status == ContantValuesObject.TaxiRequestStatusUserConfirmed) {
                                setEnableButtonGo(true);
                            } else {
                                setEnableButtonGo(false);
                            }
                            onFinish.Success();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.setError(ErrorObj.DATA_NULL);
                            error.errorMessage = e.toString();
                            onFinish.Failure(error);
                        }
                    }
                }

                @Override
                public void Failure(ErrorObj error) {
                    onFinish.Failure(error);
                }
            });
        } else {
            Log.w("getDirections", "From To null");
        }
    }
}
