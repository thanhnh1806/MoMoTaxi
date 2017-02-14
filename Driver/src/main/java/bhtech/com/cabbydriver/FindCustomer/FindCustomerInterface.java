package bhtech.com.cabbydriver.FindCustomer;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbydriver.object.CustomerRequestObj;
import bhtech.com.cabbydriver.object.LocationObject;

/**
 * Created by thanh_nguyen02 on 21/01/2016.
 */
public class FindCustomerInterface {
    public interface Listener {
        void onGoogleMapReady();

        void OnMarkerClick();

        void OnListViewItemClick();

        void OnButtonNonAppClick();

        void OnButtonListViewClick();

        void OnButtonMapViewClick();

        void OnButtonAcceptClick();

        void OnButtonRefreshClick();

        void OnButtonCloseDialogClick();

        void OnButtonStartNonAppClick();

        void onIconLocationClick();

        void zoomToDriverLoation();

        void setCurrentAddress();
    }

    public interface DataSource {
        ArrayList<CustomerRequestObj> getListRequest();

        ArrayList<MarkerOptions> getListMarkerOptions();

        ArrayList<Integer> getListDistance();

        String getCurrentDayOfWeek();

        String getCurrentTime();

        String getCurrentDate();

        void setListViewPositionClick(int position);

        int getListViewPositionClick();

        GoogleMap.InfoWindowAdapter getGoogleMapInfoWindowAdapter(Context context);

        LatLng getLatLngDriver();

        void setLatLngDriver(LatLng latLngDriver);

        String getCurrentAddress();

        void setToAddress(String s);

        void setToLocation(LatLng latLng);

        void setListNearbyLocation(ArrayList<LocationObject> listNearbyLocation);

        ArrayList<LocationObject> getListNearbyLocation();

        void setFromAddress(String address);

        void setFromLocation(LatLng latLng);

        void setFirstZoom(boolean firstZoom);
    }
}
