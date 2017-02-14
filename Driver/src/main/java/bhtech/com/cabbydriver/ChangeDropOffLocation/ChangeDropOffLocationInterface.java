package bhtech.com.cabbydriver.ChangeDropOffLocation;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbydriver.SupportClasses.Map.OnMapViewListener;

/**
 * Created by thanh_nguyen on 13/04/2016.
 */
public class ChangeDropOffLocationInterface {
    public interface Listener extends OnMapViewListener {
        void onButtonCancelClick();

        void onButtonOkClick();

        void listGooglePlacesItemClick();
    }

    public interface Datasource {

        void setDropOffLocation(LatLng latLng);

        MarkerOptions getDropOffMarkerOptions();

        MarkerOptions getPickUpMarkerOptions();

        ArrayList<LatLng> getListLatLng();

        void setDropOffAddress(String s);
    }
}
