package bhtech.com.cabbydriver.TripHistory;

import java.util.ArrayList;

import bhtech.com.cabbydriver.object.TripObject;

/**
 * Created by duongpv on 4/12/16.
 */
public class TripHistoryInterface {
    public interface DataSource {
        ArrayList<TripObject> getListOfTripHistory();

        int getExpandedGroupPosition();

        int getGroupNeedCollapsedPosition();

        ArrayList<String> getListSortBy();

        ArrayList<String> getListMonth();

        ArrayList<String> getListDate();

        String selectedMonth();

        String selectedDay();

        String getCurrentDate();

        String getCurrentMonth();
    }

    public interface Delegate {
        void groupOnClickAtPosition(int groupPosition);

        void groupExpandedAtPosition(int groupPosition);

        void groupCollapseAtPosition(int groupPosition);

        void sortByDate();

        void sortByCost();

        void filterByMonth(int position);

        void filterByDay(int position);
    }
}
