package bhtech.com.cabbytaxi.WaitTaxi;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thanh_nguyen on 04/02/2016.
 */
public class WaitTaxiInterface {
    public interface Listener {
        void onButtonTaxiArrivedClick();

        void onButtonInTaxiClick();

        void onButtonCancelClick();

        void onButtonCameraClick();

        void onButtonMessagesClick();

        void onButtonCallClick();

        void onGoogleMapReady();
    }

    public interface Database {
        String getEstimateTime();

        String getDriverName();

        String getCarNumber();

        int getStatusRequest();

        MarkerOptions getUserMarkerOptions();

        MarkerOptions getDriverMarkerOptions();

        LatLng getUserLatLng();

        LatLng getDriverLatLng();

        String getDriverPhoto();
    }
}
