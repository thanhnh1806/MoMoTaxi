package bhtech.com.cabbydriver.Alert;

import java.util.ArrayList;

import bhtech.com.cabbydriver.object.AlertObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by duongpv on 4/21/16.
 */
public class AlertInterface {
    public interface Delegate {
        void alertOnClickAtIndex(int i);

        void futureBookingOnCancelAtIndex(int i);

        void futureBookingOnCallAtIndex(String i);

        void futureBookingOnClickAtIndex(int i);

        void filterByMonth(int position);

        void filterByDay(int position);

        void groupOnClickAtPosition(int groupPosition);

        void groupExpandedAtPosition(int groupPosition);

        void groupCollapseAtPosition(int groupPosition);

        void futureBookingOnMessageAtIndex(String i);
    }

    public interface DataSource {
        ArrayList<AlertObject> getAlerts();

        ArrayList<TaxiRequestObj> getFutureBookings();

        ArrayList<String> getListMonth();

        ArrayList<String> getListDate();

        int getExpandedGroupPosition();

        int getCountOfAllItems();

        String getCurrentMonth();

        String getCurrentDate();
    }
}
