package bhtech.com.cabbydriver.Alert;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.BaseModelInterface;
import bhtech.com.cabbydriver.object.AlertObject;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.DriverObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by duongpv on 4/21/16.
 */
public class AlertModel implements AlertInterface.DataSource {
    private Context context;
    private ArrayList<AlertObject> alerts;
    private ArrayList<TaxiRequestObj> futureBookings;
    private int filterByMonth;  //  0 = all months
    private int filterByDate;   //  0 = all days
    private int thisYear = Calendar.getInstance().get(Calendar.YEAR);
    private String fromDateString = "2016-01-01";
    private String toDateString = "2016-12-31";
    private Calendar calendar;
    private int expandedGroupPosition = -1;         //  no group expended
    private int groupNeedCollapsedPosition = -1;    //  normal, this group is the last expanded group
    private Date fromDate, toDate;
    private int numberOfAlerts, numberOfNewAlerts;

    ArrayList<String> listMonth = new ArrayList<>();
    ArrayList<String> listDate = new ArrayList<>();

    public AlertModel(Context context) {
        this.context = context;
        alerts = new ArrayList<>();
        futureBookings = new ArrayList<>();
        listMonth = TimeObject.getArrayListMonth();
    }

    public void setNumberOfNewAlerts(int numberOfNewAlerts) {
        this.numberOfNewAlerts = numberOfNewAlerts;
    }

    public void setNumberOfAlerts(int numberOfAlerts) {
        this.numberOfAlerts = numberOfAlerts;
    }

    public void setExpandedGroupPosition(int expandedGroupPosition) {
        this.expandedGroupPosition = expandedGroupPosition;
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

    public void setToDateString(String toDateString) {
        this.toDateString = toDateString;
    }

    public void setFromDateString(String fromDateString) {
        this.fromDateString = fromDateString;
    }

    public int getThisYear() {
        return thisYear;
    }

    @Override
    public ArrayList<AlertObject> getAlerts() {
        return alerts;
    }

    @Override
    public ArrayList<TaxiRequestObj> getFutureBookings() {
        return futureBookings;
    }

    public interface OnDriverCancelFutureBooking {
        void Success();

        void Failure(ErrorObj error);
    }

    public void driverCancelFutureBooking(int requestId, final OnDriverCancelFutureBooking onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(
                ContantValuesObject.TaxiRequestStatusCancelled)));

        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + requestId,
                NetworkObject.PUT, "DrivingToPassenger", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                onFinish.Success();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface OnStartPickUp {
        void Success();

        void Failure(ErrorObj error);
    }

    public void startPickUp(int requestId, final OnStartPickUp onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("status", Arrays.asList(String.valueOf(
                ContantValuesObject.TaxiRequestStatusDrivingToPassenger)));

        NetworkObject.callAPI(context, ContantValuesObject.UPDATE_REQUEST_PUSH + requestId,
                NetworkObject.PUT, "DrivingToPassenger", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ErrorObj.NO_ERROR) {
                                onFinish.Success();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface OnGetAlertListFinish extends BaseModelInterface {
    }

    public void requestGetAlertList(final OnGetAlertListFinish onFinish) {
        DriverObj driver = new DriverObj();
        driver = CarDriverObj.getInstance().getDriver();

        driver.getAlertList(context, fromDateString, toDateString, new DriverObj.OnGetAlertListFinish() {
            @Override
            public void success(JSONArray jsonArray) {
                alerts = new ArrayList<AlertObject>();
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            AlertObject alertObj = new AlertObject();
                            alertObj.parseJsonToObject(object);
                            alerts.add(alertObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                onFinish.success();
            }

            @Override
            public void success() {
                alerts = new ArrayList<AlertObject>();
                onFinish.success();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }

    public interface OnGetFutureBookingListFinish extends BaseModelInterface {
    }

    public void requestGetFutureBookingList(final OnGetFutureBookingListFinish onFinish) {
        DriverObj driver = CarDriverObj.getInstance().getDriver();
        driver.getFutureBookings(context, fromDateString, toDateString, new DriverObj.OnGetFutureBookingFinish() {
            @Override
            public void success(JSONArray jsonArray) {
                futureBookings = new ArrayList<TaxiRequestObj>();
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            TaxiRequestObj requestObj = new TaxiRequestObj();
                            requestObj.parseJsonToObject(object);
                            futureBookings.add(requestObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                onFinish.success();
            }

            @Override
            public void success() {
                futureBookings = new ArrayList<>();
                onFinish.success();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }

    public void setMonth(int month) {
        filterByMonth = month;
        Calendar mycal = new GregorianCalendar(thisYear, month, 1);

        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        fromDateString = thisYear + "-" + (filterByMonth + 1) + "-" + 1;
        toDateString = thisYear + "-" + (filterByMonth + 1) + "-" + daysInMonth;

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

        fromDateString = thisYear + "-" + (filterByMonth + 1) + "-" + (filterByDate + 1);
        toDateString = thisYear + "-" + (filterByMonth + 1) + "-" + (filterByDate + 1);
    }

    public ArrayList<String> getListMonth() {
        return listMonth;
    }

    public ArrayList<String> getListDate() {
        return listDate;
    }

    @Override
    public int getExpandedGroupPosition() {
        return expandedGroupPosition;
    }

    @Override
    public int getCountOfAllItems() {
        return getAlerts().size() + getFutureBookings().size();
    }

    @Override
    public String getCurrentMonth() {
        return TimeObject.getArrayMonth()[TimeObject.getCurrentMonth()];
    }

    @Override
    public String getCurrentDate() {
        return String.valueOf(TimeObject.getCurrentDateofMonth());
    }
}
