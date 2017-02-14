package bhtech.com.cabbydriver.TripHistory;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.DriverObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TimeObject;
import bhtech.com.cabbydriver.object.TripObject;

/**
 * Created by duongpv on 4/12/16.
 */
public class TripHistoryModel implements TripHistoryInterface.DataSource {
    Context context;
    ArrayList<TripObject> listTripHistory = new ArrayList<>();
    int sortedBy = 0;   //  0 = sort by Asc, 1 = sort by Desc
    int filterByMonth;  //  0 = all months
    int filterByDate;   //  0 = all days
    int thisYear = 2016;
    String fromDate = "2016-01-01";
    String toDate = "2016-12-31";
    Calendar calendar;
    int expandedGroupPosition = -1;         //  no group expended
    int groupNeedCollapsedPosition = -1;    //  normal, this group is the last expanded group
    ArrayList<String> listSortBy = new ArrayList<>();
    ArrayList<String> listMonth = new ArrayList<>();
    ArrayList<String> listDate = new ArrayList<>();

    public TripHistoryModel(Context context) {
        this.context = context;
        calendar = Calendar.getInstance();
        Date dateFromDate = calendar.getTime();
        thisYear = calendar.get(Calendar.YEAR);
        filterByMonth = Integer.parseInt(TimeObject.getStringFromDate(dateFromDate, "M")) - 1;
        filterByDate = Integer.parseInt(TimeObject.getStringFromDate(dateFromDate, "d")) - 1;

        listSortBy.add(context.getString(R.string.triphistory_sortby_date));
        listSortBy.add(context.getString(R.string.triphistory_sortby_price));

        for (int i = 0; i < 12; i++) {
            listMonth.add(TimeObject.getStringMonth(i));
        }
    }

    @Override
    public ArrayList<TripObject> getListOfTripHistory() {
        return listTripHistory;
    }

    public void setSortedBy(int sortedBy) {
        this.sortedBy = sortedBy;
    }

    public void setFilterByMonth(int filterByMonth) {
        this.filterByMonth = filterByMonth;
        calendar.set(Calendar.MONTH, filterByMonth);
    }

    public void setFilterByDate(int filterByDate) {
        this.filterByDate = filterByDate;
        calendar.set(Calendar.DAY_OF_MONTH, filterByDate);
    }

    public void setGroupExpandAtPosition(int groupPosition) {
        groupNeedCollapsedPosition = expandedGroupPosition;
        expandedGroupPosition = groupPosition;
    }

    public void setMonth(int month) {
        filterByMonth = month;
        Calendar mycal = new GregorianCalendar(thisYear, month, 1);

        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        fromDate = thisYear + "-" + (filterByMonth + 1) + "-" + 1;
        toDate = thisYear + "-" + (filterByMonth + 1) + "-" + daysInMonth;

        listDate.clear();

        for (int i = 0; i < daysInMonth; i++) {
            listDate.add("" + (i + 1));
        }
    }

    public void setDay(int day) {
        Calendar mycal = new GregorianCalendar(thisYear, filterByMonth, 1);

        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (day < 0) {
            filterByDate = 0;
        } else if (day >= daysInMonth) {
            filterByDate = daysInMonth - 1;
        } else {
            filterByDate = day;
        }

        fromDate = thisYear + "-" + (filterByMonth + 1) + "-" + (filterByDate + 1);
        toDate = thisYear + "-" + (filterByMonth + 1) + "-" + (filterByDate + 1);
    }

    public ArrayList<String> getListSortBy() {
        return listSortBy;
    }

    public ArrayList<String> getListMonth() {
        return listMonth;
    }

    public ArrayList<String> getListDate() {
        return listDate;
    }

    @Override
    public String selectedMonth() {
        return "" + (filterByMonth + 1);
    }

    public String selectedDay() {
        return "" + (filterByDate + 1);
    }

    @Override
    public String getCurrentDate() {
        return String.valueOf(TimeObject.getCurrentDateofMonth());
    }

    @Override
    public String getCurrentMonth() {
        return TimeObject.getArrayMonth()[TimeObject.getCurrentMonth()];
    }

    public int getExpandedGroupPosition() {
        return expandedGroupPosition;
    }

    public int getGroupNeedCollapsedPosition() {
        return groupNeedCollapsedPosition;
    }

    public void clearGroupNeedToCollapse() {
        groupNeedCollapsedPosition = -1;
    }

    public void clearGroupNeedToExpand() {
        expandedGroupPosition = -1;
    }

    public void sortByDate() {
        Collections.sort(listTripHistory, new Comparator<TripObject>() {

            @Override
            public int compare(TripObject arg0, TripObject arg1) {
                Date date1 = arg0.requestObj.getUpdatedDate();
                Date date2 = arg1.requestObj.getUpdatedDate();

                if (date1.equals(date2)) {
                    return 0;
                } else if (date1.before(date2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public void sortByCost() {
        Collections.sort(listTripHistory, new Comparator<TripObject>() {

            @Override
            public int compare(TripObject arg0, TripObject arg1) {
                float date1 = arg0.requestObj.getPrice();
                float date2 = arg1.requestObj.getPrice();

                if (date1 == date2) {
                    return 0;
                } else if (date1 > date2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public interface OnGetListOfTripHistoryFinish {
        void success();

        void failure(ErrorObj errorObj);
    }

    public void getListOfHistory(final TripHistoryModel.OnGetListOfTripHistoryFinish onFinish) {
        final ErrorObj errorObj = new ErrorObj();
        DriverObj driver = new DriverObj();
        driver.getTripHistory(context, fromDate, toDate, new DriverObj.OnGetTripHistoryFinish() {
            @Override
            public void success(JSONArray array) {
                listTripHistory = new ArrayList<>();
                try {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        TripObject trip = new TripObject();
                        trip.parseJsonToObject(jsonObject);
                        listTripHistory.add(trip);
                    }
                    onFinish.success();
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorObj.errorCode = e.hashCode();
                    errorObj.errorMessage = e.getLocalizedMessage();
                    onFinish.failure(errorObj);
                }
            }

            @Override
            public void success() {

            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }
}
