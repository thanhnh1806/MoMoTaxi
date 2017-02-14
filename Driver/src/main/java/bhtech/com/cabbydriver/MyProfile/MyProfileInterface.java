package bhtech.com.cabbydriver.MyProfile;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by thanh_nguyen on 13/06/2016.
 */
public class MyProfileInterface {
    public interface Listener {
        void onButtonBackClick();

        void chooseDateRangeForWorkingHour();

        void chooseDateRangeForMileage();

        void chooseFromDateFinish();

        void onButtonChooseDateRangeClick();

        void onWorkingHourListMonthItemClick();

        void onMileageListMonthItemClick();

        void onWorkingHourListWeekItemClick();

        void onMileageListWeekItemClick();
    }

    public interface Datasource {
        String getDriverPhoto();

        String getDriverName();

        String getDriverPhoneNumber();

        String getDriverMail();

        String getDriverCompany();

        String getDriverLocation();

        String getWorkingHour();

        String getMileage();

        Date getWorkingHourStartDate();

        Date getWorkingHourEndDate();

        Date getMileageStartDate();

        Date getMileageEndDate();

        Date getFromDate();

        void setFromDate(Date fromDate);

        Date getToDate();

        void setToDate(Date toDate);

        DateFormat getDateFormat();

        void setChooseDateRangeForWorkingHour(boolean b);

        boolean isChooseDateRangeForWorkingHour();

        String[] getArrayMonth();

        void setChooseListMonthPosition(int position);

        void setChooseListWeekPosition(int position);
    }
}
