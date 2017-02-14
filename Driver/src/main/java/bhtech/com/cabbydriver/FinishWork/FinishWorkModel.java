package bhtech.com.cabbydriver.FinishWork;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.SharedPreference;

/**
 * Created by duongpv on 4/5/16.
 */
public class FinishWorkModel implements FinishWorkInterface.DataSource {

    private Context context;
    public CarDriverObj carDriverObj = new CarDriverObj();

    public FinishWorkModel(Context context) {
        this.context = context;
    }

    void setMileage(String mileage) {
        Float fMileage = Float.parseFloat(mileage);
        carDriverObj.setMileage(fMileage);
    }

    public void getTodayResult(Context context, final GetTodayResultFinish onFinish) {
        carDriverObj.getTodayWorkingResult(context, new CarDriverObj.FinishWorkAndGetTodayResult() {
            @Override
            public void success() {
                onFinish.success();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }

    public void finishWorkAndLogOut(final Context context, final FinishWorkModelInterface onFinish) {
        carDriverObj.finishWorkAndLogOut(context, new CarDriverObj.FinishWorkAndLogOutCompleted() {
            @Override
            public void success() {
                SharedPreference.set(context, ContantValuesObject.PIN_CODE, null);
                onFinish.success();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                onFinish.failure(errorObj);
            }
        });
    }

    @Override
    public String getNumberOfPassengers() {
        return "" + carDriverObj.getTotalPassengers();
    }

    @Override
    public String getTotalDistance() {
        return (carDriverObj.getTotalDistance() + " Km");
    }

    @Override
    public String getTotalHours() {
        return (carDriverObj.getTotalHour() + " Hours");
    }

    @Override
    public String getSales() {
        return ("$ " + carDriverObj.getTotalSales());
    }

    @Override
    public String getTodayDate() {
        Calendar today = Calendar.getInstance();
        String formatStyle = context.getResources().getString(R.string.date_format);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStyle, Locale.US);

        return dateFormat.format(today.getTime());
    }

    public interface GetTodayResultFinish {
        void success();

        void failure(ErrorObj errorObj);
    }

    public interface FinishWorkModelInterface {
        void success();

        void failure(ErrorObj errorObj);
    }
}
