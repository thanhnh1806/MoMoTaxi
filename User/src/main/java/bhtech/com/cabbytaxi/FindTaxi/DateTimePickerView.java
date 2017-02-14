package bhtech.com.cabbytaxi.FindTaxi;

import android.app.Fragment;
import android.os.Bundle;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class DateTimePickerView extends Fragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;

    public void setListener(FindTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FindTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            datasource.setPickupTime(null);
        } catch (Exception e) {

        }
    }

    private Calendar calendar;

    public void showDateTimePicker() {
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        TimePickerDialog timePickerDialog;
        if (datasource.getPickupTime() != null) {
            calendar.setTime(datasource.getPickupTime());
            timePickerDialog = TimePickerDialog.newInstance(this,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        } else {
            calendar.set(year, monthOfYear, dayOfMonth);
            timePickerDialog = TimePickerDialog.newInstance(this,
                    calendar.get(Calendar.HOUR_OF_DAY) + 1, calendar.get(Calendar.MINUTE) + 1, true);
        }

        dialog.setCancelable(false);
        timePickerDialog.setCancelable(false);
        timePickerDialog.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        Date pickupTime = calendar.getTime();
        datasource.setPickupTime(pickupTime);
        listener.chooseDateTimePickUpFinish();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
