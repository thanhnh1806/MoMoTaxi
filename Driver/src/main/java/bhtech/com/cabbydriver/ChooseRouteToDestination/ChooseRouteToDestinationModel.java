package bhtech.com.cabbydriver.ChooseRouteToDestination;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.MarkerOptionsObject;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.RouteObject;
import bhtech.com.cabbydriver.object.SharedPreference;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 14/04/2016.
 */
public class ChooseRouteToDestinationModel implements ChooseRouteToDestinationInterface.Datasource {
    Context context;
    LatLng fromLatLng = TaxiRequestObj.getInstance().getFromLocation();
    LatLng toLatLng = TaxiRequestObj.getInstance().getToLocation();
    int polylineUserClick = 0;

    boolean enableButtonGo = false;

    ArrayList<RouteObject> listRoute;
    ArrayList<PolylineOptions> listPolylineOptions;
    ArrayList<Polyline> listPolyline;

    public void setFromLatLng(LatLng fromLatLng) {
        this.fromLatLng = fromLatLng;
    }

    public void setToLatLng(LatLng toLatLng) {
        this.toLatLng = toLatLng;
    }

    public ArrayList<RouteObject> getListRoute() {
        return listRoute;
    }

    public void setListRoute(ArrayList<RouteObject> listRoute) {
        this.listRoute = listRoute;
    }

    public void setListPolylineOptions(ArrayList<PolylineOptions> listPolylineOptions) {
        this.listPolylineOptions = listPolylineOptions;
    }

    public ArrayList<Polyline> getListPolyline() {
        return listPolyline;
    }

    public ChooseRouteToDestinationModel(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<PolylineOptions> getListPolylineOptions() {
        return listPolylineOptions;
    }

    @Override
    public void setPolylineUserClick(int polylineUserClick) {
        this.polylineUserClick = polylineUserClick;
    }

    @Override
    public int getPolylineUserClick() {
        return polylineUserClick;
    }

    @Override
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

    @Override
    public LatLng getFromLatLng() {
        return fromLatLng;
    }

    @Override
    public LatLng getToLatLng() {
        return toLatLng;
    }

    @Override
    public int getDuration() {
        return (int) (listRoute.get(polylineUserClick).legs.get(0).durationValue / 60);
    }

    @Override
    public int getDistance() {
        return (int) (listRoute.get(polylineUserClick).legs.get(0).distanceValue / 1000);
    }

    @Override
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

    public void setCloseNavigation(boolean b) {
        SharedPreference.set(context, ContantValuesObject.IS_CLOSE_NAVI, String.valueOf(b));
    }

    public void saveMileage() {
        SharedPreference.set(context, ContantValuesObject.DISTANCE, String.valueOf(getDistance()));
    }

    public interface DriverCancelNonAppUser {
        void Success();

        void Failure(ErrorObj error);
    }

    public void cancelNonAppUser(final DriverCancelNonAppUser onFinish) {
        TaxiRequestObj.getInstance().driverChangeStatusRequest(context,
                ContantValuesObject.TaxiRequestStatusPending,
                new TaxiRequestObj.DriverChangeStatusRequest() {
                    @Override
                    public void Success() {
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface DriverChooseRouteToDestination {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverChooseRouteToDestination(final DriverChooseRouteToDestination onFinish) {
        TaxiRequestObj.getInstance().driverChangeStatusRequest(context,
                ContantValuesObject.TaxiRequestStatusChooseRoute,
                new TaxiRequestObj.DriverChangeStatusRequest() {
                    @Override
                    public void Success() {
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface DrivingToDestination {
        void Success();

        void Failure(ErrorObj error);
    }

    public void drivingToDestination(final DrivingToDestination onFinish) {
        TaxiRequestObj.getInstance().driverChangeStatusRequest(context,
                ContantValuesObject.TaxiRequestStatusDrivingToDestination,
                new TaxiRequestObj.DriverChangeStatusRequest() {
                    @Override
                    public void Success() {
                        onFinish.Success();
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
                            if (listRoute.size() > 0) {
                                setEnableButtonGo(true);
                                onFinish.Success();
                            } else {
                                error.setError(ErrorObj.DATA_NULL);
                                error.errorMessage = context.getString(R.string.can_not_find_direction);
                                onFinish.Failure(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.setError(ErrorObj.UNKNOWN_ERROR);
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
        }
    }

    public interface OnGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getCurrentRequest(final OnGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }
}
