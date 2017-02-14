package bhtech.com.cabbydriver.ChangePickUpLocation;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbydriver.SupportClasses.Map.OnMapViewListener;

/**
 * Created by thanh_nguyen on 12/04/2016.
 */
public class ChangePickUpLocationInterface {
    public interface Listener extends OnMapViewListener {
        void onButtonCancelClick();

        void onButtonOkClick();

        void listGooglePlacesItemClick();
    }

    public interface Datasource {

        void setPickUpLocation(LatLng latLng);

        MarkerOptions getCustomerMarkerOptions();

        MarkerOptions getDriverMarkerOptions();

        ArrayList<LatLng> getListLatLng();

        void setPickUpAddress(String address);
    }
}
