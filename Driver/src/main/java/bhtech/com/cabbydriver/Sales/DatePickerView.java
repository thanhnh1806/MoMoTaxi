package bhtech.com.cabbydriver.Sales;

import android.app.Fragment;
import android.os.Bundle;

import com.android.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class DatePickerView extends Fragment implements DatePickerDialog.OnDateSetListener {
    private SalesInterface.Database database;
    private SalesInterface.Listener listener;

    public void setDatabase(SalesInterface.Database database) {
        this.database = database;
    }

    public void setListener(SalesInterface.Listener listener) {
        this.listener = listener;
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
        database = null;
    }

    public void showDateTimePicker() {
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();
        if (database.isChooseRangeDate()) {
            database.setDateForViewSaleReport(null);
            database.setPositionWeekToGetReport(-1);
            database.setPositionMonthToGetReport(-1);
            if (database.getDateStartForViewReport() == null) {
                database.setDateStartForViewReport(date);
            } else {
                database.setDateEndForViewReport(date);
            }
            listener.chooseDateForRangeFinish();
        } else {
            database.setDateStartForViewReport(null);
            database.setDateEndForViewReport(null);
            database.setPositionWeekToGetReport(-1);
            database.setPositionMonthToGetReport(-1);
            database.setDateForViewSaleReport(date);
            listener.chooseDateFinish();
        }
    }
}
