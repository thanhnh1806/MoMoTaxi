package bhtech.com.cabbydriver.ReportIncident;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thanh on 6/15/2016.
 */
public class ReportIncidentInterface {
    public interface Listener {
        void onMapReady();

        void sendReportIncident();

        void onButtonBackClick();

        void onButtonCallCompanyClick();

        void onButtonCallPoliceClick();
    }

    public interface Datasource {
        void setDriverLatLng(LatLng latLng);

        MarkerOptions getMarkerOptions();

        String getAddress();

        LatLng getDriverLatLng();

        String getCurrentDayInWeek();

        String getCurrentTime();

        String getCurrentDay();
    }
}
