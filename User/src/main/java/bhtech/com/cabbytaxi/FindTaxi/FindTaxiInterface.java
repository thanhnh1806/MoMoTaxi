package bhtech.com.cabbytaxi.FindTaxi;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.LocationObject;
import bhtech.com.cabbytaxi.object.VehicleObj;

/**
 * Created by thanh_nguyen on 28/01/2016.
 */
public class FindTaxiInterface {
    public interface Listener {
        void onEnterDestinationClick();

        void onIconLocationClick();

        void onButtonDateTimeClick();

        void onButtonFindTaxiClick();

        void onButtonCancelRequestTaxiClick();

        void onListVehicleClick();

        void onChooseFromLocation();

        void onChooseToLocation();

        void dropPinClick();

        void dropPinViewButtonBackClick();

        void onMapLongClick();

        void chooseDateTimePickUpFinish();

        void optionsViewMapLocationChange();

        void onFoundATaxiButtonOkClick();

        void onFoundATaxiButtonCancelClick();

        void onDrawDriverMaker(CarDriverObj carDriverObj);

        void clickFavouriteLocation();

        void onCreateOptionsViewFinish();
    }

    public interface Datasource {
        void setFromAddress(String fromAddress);

        void setFromLatitude(Double fromLatitude);

        void setFromLongitude(Double fromLongitude);

        void setToAddress(String toAddress);

        void setToLatitude(Double toLatitude);

        void setToLongitude(Double toLongitude);

        void setPickupTime(Date pickupTime);

        void setHowtoChoosePickupTime(int method);

        Date getCurrentTime();

        void setCarClass(int carClass);

        ArrayList<LocationObject> getListFavouriteLocation();

        void setMyLocation(Location location);

        Location getMyLocation();

        void setMyLocationAddress(Context context);

        String getMyLocationAddress();

        ArrayList<CarDriverObj> getListDriversAround();

        void setListDriversAround(ArrayList<CarDriverObj> list);

        ArrayList<VehicleObj> getListVehicle();

        int getPositionListVehicleClick();

        String getEstimatedTime();

        int getEstimatedTimeFoundATaxi();

        String getDriverName();

        String getCarNumber();

        Double getFromLatitude();

        Double getFromLongitude();

        FragmentActivity getFragmentActivity();

        ArrayList<LocationObject> getListFromHistoryLocation();

        ArrayList<LocationObject> getListToHistoryLocation();

        ArrayList<LocationObject> getListNearbyLocation();

        void setListNearbyLocation(ArrayList<LocationObject> listNearbyLocation);

        void setPickupTimeString(String s);

        String getPickupTimeString();

        Date getPickupTime();

        ArrayList<LatLng> getListLatLng();

        MarkerOptions getDropPinMarkerOptions();

        String getFromAddress();

        MarkerOptions getDestinationMarkerOptions();

        MarkerOptions getFromMarkerOptions();

        MarkerOptions getFromMarkerOptionsOptionView();

        MarkerOptions getToMarkerOptionsOptionView();

        Double getToLatitude();

        Double getToLongitude();

        void setZoomMapFirstTime(boolean b);

        GoogleMap.InfoWindowAdapter getGoogleMapInfoWindowAdapter();

        String getDriverPhoto();

        float getEstMileage();

        float getEstPrice();

        LatLng getSouthwest();

        LatLng getNortheast();
    }
}
