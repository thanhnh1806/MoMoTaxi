package bhtech.com.cabbydriver.Sales;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.SaleReportObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by thanh_nguyen on 04/05/2016.
 */
public class SalesModel implements SalesInterface.Database {
    private Context context;
    private Date dateForViewReport;
    private boolean chooseRangeDate = false;
    private boolean chooseDateFromWeekView = true;
    private Date dateStartForViewReport = null;
    private Date dateEndForViewReport = null;
    private int positionMonthToGetReport = 0;
    private int positionWeekToGetReport = getCurrentWeekOfYear();
    private SaleReportObj saleReportByDay;
    private SaleReportObj saleReportByWeek;
    private SaleReportObj saleReportByMonth;
    private SaleReportObj saleReportByDayRange;

    public SalesModel(Context context) {
        this.context = context;
    }

    @Override
    public String getDriverName() {
        return CarDriverObj.getInstance().getDriver().getFullName();
    }

    @Override
    public void setDateForViewSaleReport(Date date) {
        dateForViewReport = date;
    }

    public Date getDateForViewReport() {
        return dateForViewReport;
    }

    public boolean isChooseRangeDate() {
        return chooseRangeDate;
    }

    public void setChooseRangeDate(boolean chooseRangeDate) {
        this.chooseRangeDate = chooseRangeDate;
    }

    @Override
    public void setChooseDateFromWeekView(boolean b) {
        chooseDateFromWeekView = b;
    }

    public boolean isChooseDateFromWeekView() {
        return chooseDateFromWeekView;
    }

    public Date getDateStartForViewReport() {
        return dateStartForViewReport;
    }

    public void setDateStartForViewReport(Date dateStartForViewReport) {
        this.dateStartForViewReport = dateStartForViewReport;
    }

    public Date getDateEndForViewReport() {
        return dateEndForViewReport;
    }

    public void setDateEndForViewReport(Date dateEndForViewReport) {
        this.dateEndForViewReport = dateEndForViewReport;
    }

    @Override
    public String[] getArrayMonth() {
        return TimeObject.getArrayMonth();
    }

    public SaleReportObj getSaleReportByDay() {
        return saleReportByDay;
    }

    public SaleReportObj getSaleReportByWeek() {
        return saleReportByWeek;
    }

    @Override
    public void setPositionMonthToGetReport(int i) {
        positionMonthToGetReport = i;
    }

    @Override
    public int getPositionMonthToGetReport() {
        return positionMonthToGetReport;
    }

    public SaleReportObj getSaleReportByMonth() {
        return saleReportByMonth;
    }

    public SaleReportObj getSaleReportByDayRange() {
        return saleReportByDayRange;
    }

    @Override
    public void setPositionWeekToGetReport(int i) {
        positionWeekToGetReport = i;
    }

    public int getPositionWeekToGetReport() {
        return positionWeekToGetReport;
    }

    @Override
    public int getCurrentWeekOfYear() {
        return TimeObject.getCurrentWeekOfYear();
    }

    public int getCurrentYear() {
        return TimeObject.getCurrentYear();
    }

    public boolean sortDateRange() {
        if (dateStartForViewReport.after(dateEndForViewReport)) {
            Date temp = dateEndForViewReport;
            dateEndForViewReport = dateStartForViewReport;
            dateStartForViewReport = temp;
            return true;
        } else if (dateStartForViewReport.before(dateEndForViewReport)) {
            return true;
        } else {
            return false;
        }
    }

    public interface onGetReportByMonth {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getReportByMonth(final onGetReportByMonth onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("type", Arrays.asList("Month"));
        params.put("month_num", Arrays.asList(String.valueOf(positionMonthToGetReport + 1)));
        params.put("month_year", Arrays.asList(String.valueOf(getCurrentYear())));

        NetworkObject.callAPI(context, ContantValuesObject.SALE_REPORT, NetworkObject.POST,
                "Sale_Report", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject results = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            saleReportByMonth = new SaleReportObj();
                            saleReportByMonth.parseJsonToObject(results);
                            onFinish.Success();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorObj errorObj = new ErrorObj();
                            errorObj.errorMessage = "Error when get ReportByMonth";
                            onFinish.Failure(errorObj);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
    }

    public interface onGetReportByDate {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getReportByDate(final onGetReportByDate onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        int year = 1900 + dateForViewReport.getYear();
        int month = dateForViewReport.getMonth() + 1;

        String monthStr = TimeObject.add0ToDayMonth(month);

        int day = dateForViewReport.getDate();
        String dayStr = TimeObject.add0ToDayMonth(day);

        String date = year + "-" + monthStr + "-" + dayStr;
        Log.w("GetReportByDate", date);
        Map<String, List<String>> params = new HashMap<>();
        params.put("type", Arrays.asList("Day"));
        params.put("date", Arrays.asList(date));

        NetworkObject.callAPI(context, ContantValuesObject.SALE_REPORT, NetworkObject.POST,
                "Sale_Report", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            saleReportByDay = new SaleReportObj();
                            saleReportByDay.parseJsonToObject(result);
                            onFinish.Success();
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

    public interface onGetReportByDayRange {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getReportByDayRange(final onGetReportByDayRange onFinish) {
        int startDay, endDay;
        Log.w("ReportByDayRange", String.valueOf(positionWeekToGetReport));
        if (positionWeekToGetReport == 1) {
            startDay = 1;
            endDay = 7;
        } else if (positionWeekToGetReport == 2) {
            startDay = 8;
            endDay = 15;
        } else if (positionWeekToGetReport == 3) {
            startDay = 16;
            endDay = 23;
        } else {
            startDay = 24;
            if (positionMonthToGetReport == 1) {
                if (TimeObject.isLeapYear(TimeObject.getCurrentYear())) {
                    endDay = 29;
                } else {
                    endDay = 28;
                }
            } else if (TimeObject.monthHas31Days(positionMonthToGetReport + 1)) {
                endDay = 31;
            } else {
                endDay = 30;
            }
        }

        String startDayStr = TimeObject.add0ToDayMonth(startDay);
        String endDayStr = TimeObject.add0ToDayMonth(endDay);

        int month = positionMonthToGetReport + 1;
        int year = getCurrentYear();

        String monthStr = TimeObject.add0ToDayMonth(month);

        String start_date = year + "-" + monthStr + "-" + startDayStr;
        String end_date = year + "-" + monthStr + "-" + endDayStr;

        Log.w("ReportByDayRange", start_date);
        Log.w("ReportByDayRange", end_date);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("type", Arrays.asList("DayRange"));
        params.put("start_date", Arrays.asList(start_date));
        params.put("end_date", Arrays.asList(end_date));

        NetworkObject.callAPI(context, ContantValuesObject.SALE_REPORT, NetworkObject.POST,
                "Sale_Report", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            saleReportByDayRange = new SaleReportObj();
                            saleReportByDayRange.parseJsonToObject(result);
                            onFinish.Success();
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

    public interface onGetReportByWeek {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getReportByWeek(final onGetReportByWeek onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("type", Arrays.asList("Week"));
        params.put("week_num", Arrays.asList(String.valueOf(positionWeekToGetReport)));
        params.put("week_year", Arrays.asList(String.valueOf(getCurrentYear())));

        NetworkObject.callAPI(context, ContantValuesObject.SALE_REPORT, NetworkObject.POST,
                "Sale_Report", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            saleReportByWeek = new SaleReportObj();
                            saleReportByWeek.parseJsonToObject(result);
                            onFinish.Success();
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

    public void getReportByDayRange(Context context, final onGetReportByDayRange onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        String start_date = dateStartForViewReport.getYear() + 1900 + "-" +
                TimeObject.add0ToDayMonth(dateStartForViewReport.getMonth() + 1)
                + "-" + TimeObject.add0ToDayMonth(dateStartForViewReport.getDate());

        String end_date = dateEndForViewReport.getYear() + 1900 + "-" +
                TimeObject.add0ToDayMonth(dateEndForViewReport.getMonth() + 1)
                + "-" + TimeObject.add0ToDayMonth(dateEndForViewReport.getDate());

        Log.w("ReportByDayRange", start_date);
        Log.w("ReportByDayRange", end_date);

        Map<String, List<String>> params = new HashMap<>();
        params.put("type", Arrays.asList("DayRange"));
        params.put("start_date", Arrays.asList(start_date));
        params.put("end_date", Arrays.asList(end_date));

        NetworkObject.callAPI(context, ContantValuesObject.SALE_REPORT, NetworkObject.POST,
                "Sale_Report", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                            saleReportByDayRange = new SaleReportObj();
                            saleReportByDayRange.parseJsonToObject(result);
                            onFinish.Success();
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
}
