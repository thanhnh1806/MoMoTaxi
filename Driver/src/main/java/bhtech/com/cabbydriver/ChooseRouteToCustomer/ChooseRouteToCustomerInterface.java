package bhtech.com.cabbydriver.ChooseRouteToCustomer;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 06/04/2016.
 */
public class ChooseRouteToCustomerInterface {
    public interface Listener {
        void onPolylineClick();

        void onButtonCallClick();

        void onButtonMessageClick();

        void onButtonReloadLocationClick();

        void onButtonGoClick();

        void onButtonChangeLocationClick();

        void onButtonCloseNavi();

        void otherviewCreateFinish();
    }

    public interface Datasource {
        ArrayList<PolylineOptions> getListPolylineOptions();

        void setPolylineUserClick(int polylineUserClick);

        int getPolylineUserClick();

        void setListPolyline(ArrayList<Polyline> listPolyline);

        MarkerOptions getFromMarkerOptions();

        MarkerOptions getToMarkerOptions();

        LatLng getFromLatLng();

        LatLng getToLatLng();

        int getDuration();

        int getDistance();

        String getGoVia();

        String getUsername();

        String getStartAddress();

        MarkerOptions getCustomerMarkerOptions();

        LatLng getCustomerLatLng();
    }
}
