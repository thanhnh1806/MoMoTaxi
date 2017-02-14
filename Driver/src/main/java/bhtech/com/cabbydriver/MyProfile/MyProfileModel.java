package bhtech.com.cabbydriver.MyProfile;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.BaseModelInterface;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.DriverCompanyObj;
import bhtech.com.cabbydriver.object.DriverObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by thanh_nguyen on 13/06/2016.
 */
public class MyProfileModel implements MyProfileInterface.Datasource {
    private Context context;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date workingHourStartDate, workingHourEndDate, mileageStartDate, mileageEndDate;
    private Date fromDate, toDate;
    private double total_hours, total_mileage;
    private boolean chooseDateRangeForWorkingHour;
    private int listMonthPosition, listWeekPosition;


    public MyProfileModel(Context context) {
        this.context = context;
    }

    public void getWorkingHourByMonth(OnGetWorkingHour onFinish) {
        String from_date = TimeObject.getStringFromDate(TimeObject.getFirstDayOfMonth(listMonthPosition), dateFormat);
        String to_date = TimeObject.getStringFromDate(TimeObject.getLastDayOfMonth(listMonthPosition), dateFormat);
        getWorkingHour(from_date, to_date, onFinish);
    }

    public void getMileageByMonth(OnGetMileage onFinish) {
        String from_date = TimeObject.getStringFromDate(TimeObject.getFirstDayOfMonth(listMonthPosition), dateFormat);
        String to_date = TimeObject.getStringFromDate(TimeObject.getLastDayOfMonth(listMonthPosition), dateFormat);
        getMileage(from_date, to_date, onFinish);
    }

    public void getWorkingHourByWeek(OnGetWorkingHour onGetWorkingHour) {
        Date firstDayOfWeek = TimeObject.getFirstDayOfWeek(listWeekPosition);

        String from_date = TimeObject.getStringFromDate(firstDayOfWeek, dateFormat);
        String to_date = TimeObject.getStringFromDate(TimeObject.getLastDayOfWeek(firstDayOfWeek), dateFormat);
        getWorkingHour(from_date, to_date, onGetWorkingHour);
    }

    public void getMileageByWeek(OnGetMileage onGetMileage) {
        Date firstDayOfWeek = TimeObject.getFirstDayOfWeek(listWeekPosition);

        String from_date = TimeObject.getStringFromDate(firstDayOfWeek, dateFormat);
        String to_date = TimeObject.getStringFromDate(TimeObject.getLastDayOfWeek(firstDayOfWeek), dateFormat);
        getMileage(from_date, to_date, onGetMileage);
    }

    public interface OnGetMileage extends BaseModelInterface {
    }

    public void getMileageInDateRange(final OnGetMileage onFinish) {
        if (fromDate != null && toDate != null) {
            if (fromDate.after(toDate)) {
                Date temp = fromDate;
                fromDate = toDate;
                toDate = temp;
            }

            String from_date = TimeObject.getStringFromDate(fromDate, dateFormat);
            String to_date = TimeObject.getStringFromDate(toDate, dateFormat);
            getMileage(from_date, to_date, onFinish);
        }
    }

    private void getMileage(String from_date, String to_date, final OnGetMileage onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("from_date", Arrays.asList(from_date));
        params.put("to_date", Arrays.asList(to_date));

        NetworkObject.callAPI(context, ContantValuesObject.WORKING_RESULT, NetworkObject.POST,
                "WorkingHourInDateRange", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            int code = jsonObject.getInt(ContantValuesObject.CODE);
                            switch (code) {
                                case ErrorObj.NO_ERROR:
                                    JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    total_mileage = result.getDouble("totalMileage");
                                    try {
                                        mileageStartDate = dateFormat.parse(result.getString("from_date"));
                                        mileageEndDate = dateFormat.parse(result.getString("to_date"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    onFinish.success();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }

    public void getWorkingHourInDateRange(final OnGetWorkingHour onFinish) {
        if (fromDate != null && toDate != null) {
            if (fromDate.after(toDate)) {
                Date temp = fromDate;
                fromDate = toDate;
                toDate = temp;
            }

            String from_date = TimeObject.getStringFromDate(fromDate, dateFormat);
            String to_date = TimeObject.getStringFromDate(toDate, dateFormat);
            getWorkingHour(from_date, to_date, onFinish);
        }
    }

    public interface OnGetWorkingHour extends BaseModelInterface {
    }


    private void getWorkingHour(String from_date, String to_date, final OnGetWorkingHour onFinish) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(TaxiRequestObj.getInstance().getAuthToken()));

        Map<String, List<String>> params = new HashMap<>();
        params.put("from_date", Arrays.asList(from_date));
        params.put("to_date", Arrays.asList(to_date));

        NetworkObject.callAPI(context, ContantValuesObject.WORKING_RESULT, NetworkObject.POST,
                "WorkingHourInDateRange", headers, params, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            int code = jsonObject.getInt(ContantValuesObject.CODE);
                            switch (code) {
                                case ErrorObj.NO_ERROR:
                                    JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    total_hours = result.getDouble("totalHours");
                                    try {
                                        workingHourStartDate = dateFormat.parse(result.getString("from_date"));
                                        workingHourEndDate = dateFormat.parse(result.getString("to_date"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    onFinish.success();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }

    public interface OnGetDriverInformation extends BaseModelInterface {

    }

    public void getDriverInformation(final OnGetDriverInformation onFinish) {
        String authToken = TaxiRequestObj.getInstance().getAuthToken();
        Map<String, List<String>> headers = new HashMap<>();

        headers.put("authToken", Arrays.asList(authToken));
        NetworkObject.callAPI(context, ContantValuesObject.DRIVER_PROFILE, NetworkObject.GET,
                "Driver_Profile", headers, null, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            int code = jsonObject.getInt(ContantValuesObject.CODE);
                            switch (code) {
                                case ErrorObj.NO_ERROR:
                                    JSONObject result = jsonObject.getJSONObject(ContantValuesObject.RESULTS);
                                    DriverObj driver = CarDriverObj.getInstance().getDriver();

                                    if (result.has("username") && !result.isNull("username")) {
                                        driver.setUsername(result.getString("username"));
                                    }

                                    if (result.has("phone") && !result.isNull("phone")) {
                                        driver.setPhoneNumber(result.getString("phone"));
                                    }

                                    if (result.has("photo") && !result.isNull("photo")) {
                                        driver.setPhoto(result.getString("photo"));
                                    }

                                    if (result.has("email") && !result.isNull("email")) {
                                        driver.setEmail(result.getString("email"));
                                    }

                                    if (result.has("company_name") && !result.isNull("company_name")) {
                                        DriverCompanyObj driverCompanyObj = new DriverCompanyObj();
                                        driverCompanyObj.setCompany_name(result.getString("company_name"));
                                        driver.setDriver_company(driverCompanyObj);
                                    } else {
                                        driver.setDriver_company(null);
                                    }

                                    JSONObject filter = result.getJSONObject("filter");
                                    JSONObject month = filter.getJSONObject("month");
                                    try {
                                        workingHourStartDate = dateFormat.parse(month.getString("start_date"));
                                        workingHourEndDate = dateFormat.parse(month.getString("end_date"));
                                        mileageStartDate = workingHourStartDate;
                                        mileageEndDate = workingHourEndDate;
                                    } catch (ParseException e) {
                                        workingHourStartDate = null;
                                        workingHourEndDate = null;
                                        mileageStartDate = null;
                                        mileageEndDate = null;
                                    }

                                    total_hours = month.getDouble("total_hours");
                                    total_mileage = month.getDouble("total_mileage");

                                    onFinish.success();
                                    break;
                                default:
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }

    @Override
    public String getDriverPhoto() {
        return CarDriverObj.getInstance().getDriver().getPhoto();
    }

    @Override
    public String getDriverName() {
        return CarDriverObj.getInstance().getDriver().getFullName();
    }

    @Override
    public String getDriverPhoneNumber() {
        return CarDriverObj.getInstance().getDriver().getPhoneNumber();
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public void setChooseDateRangeForWorkingHour(boolean b) {
        chooseDateRangeForWorkingHour = b;
    }

    public boolean isChooseDateRangeForWorkingHour() {
        return chooseDateRangeForWorkingHour;
    }

    @Override
    public String[] getArrayMonth() {
        return TimeObject.getArrayMonth();
    }

    @Override
    public void setChooseListMonthPosition(int position) {
        listMonthPosition = position;
    }

    @Override
    public void setChooseListWeekPosition(int position) {
        listWeekPosition = position;
    }

    @Override
    public String getDriverMail() {
        return CarDriverObj.getInstance().getDriver().getEmail();
    }

    @Override
    public String getDriverCompany() {
        if (CarDriverObj.getInstance().getDriver().getDriver_company() != null) {
            return CarDriverObj.getInstance().getDriver().getDriver_company().getCompany_name();
        } else {
            return "";
        }
    }

    @Override
    public String getDriverLocation() {
        return CarDriverObj.getInstance().getDriver().getAddress();
    }

    @Override
    public String getWorkingHour() {
        if (total_hours > 0) {
            if (total_hours / 60 < 1) {
                return "0" + StringObject.getDecimalFormat(2).format(total_hours / 60);
            } else {
                return StringObject.getDecimalFormat(2).format(total_hours / 60);
            }
        } else {
            return "0";
        }
    }

    @Override
    public String getMileage() {
        if (total_mileage > 0) {
            if (total_mileage < 1) {
                return "0" + StringObject.getDecimalFormat(2).format(total_mileage);
            } else {
                return StringObject.getDecimalFormat(2).format(total_mileage);
            }
        } else {
            return "0";
        }
    }

    @Override
    public Date getWorkingHourStartDate() {
        return workingHourStartDate;
    }

    @Override
    public Date getWorkingHourEndDate() {
        return workingHourEndDate;
    }

    @Override
    public Date getMileageStartDate() {
        return mileageStartDate;
    }

    @Override
    public Date getMileageEndDate() {
        return mileageEndDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
