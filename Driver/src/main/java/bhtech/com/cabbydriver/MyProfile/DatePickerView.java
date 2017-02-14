package bhtech.com.cabbydriver.MyProfile;

import android.app.Fragment;
import android.os.Bundle;

import com.android.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class DatePickerView extends Fragment implements DatePickerDialog.OnDateSetListener {
    private MyProfileInterface.Listener listener;
    private MyProfileInterface.Datasource datasource;

    public void setListener(MyProfileInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(MyProfileInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    public void showDateTimePicker() {
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();
        if (datasource.getFromDate() == null) {
            datasource.setFromDate(date);
            listener.chooseFromDateFinish();
        } else {
            datasource.setToDate(date);
            if (datasource.isChooseDateRangeForWorkingHour()) {
                listener.chooseDateRangeForWorkingHour();
            } else {
                listener.chooseDateRangeForMileage();
            }
        }
    }
}
