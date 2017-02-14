package bhtech.com.cabbydriver.CarStatus;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by duongpv on 4/7/16.
 */
public class CarStatusModel implements CarStatusInterface.DataSource {
    Context context;
    CarObj carObj = new CarObj();
    int carAgeInMonth;
    Date producedDate;
    String maintenanceDate;
    Date nextMaintenanceDate;
    double remainKm;

    public CarStatusModel(Context context) {
        this.context = context;
    }

    public interface CarStatusModelGetCarStatusFinish {
        void success();

        void failure(ErrorObj errorObj);
    }

    public void getCarStatus(final CarStatusModelGetCarStatusFinish onFinish) {
        carObj.getStatus(context, new CarObj.OnGetCarStatusFinish() {
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

    @Override
    public String getCarImageUrl() {
        return this.carObj.getPhoto();
    }

    @Override
    public String getCarNumber() {
        return "Taxi " + this.carObj.getNumber();
    }

    @Override
    public String getCarType() {
        return this.carObj.vehicleTypeName;
    }

    @Override
    public double getTotalDrove() {
        return this.carObj.totalDistance;
    }

    @Override
    public int getCarAge() {
        Calendar calendar = Calendar.getInstance();
        producedDate = this.carObj.parseDate(this.carObj.producedDate,
                context.getResources().getString(R.string.date_format_UTC));
        Date today = calendar.getTime();

        calendar.setTimeInMillis(TimeObject.diffTimeInCalendar(today, producedDate).getTimeInMillis());

        carAgeInMonth = calendar.get(Calendar.MONTH) + ((calendar.get(Calendar.YEAR) - 1970) * 12);
        return carAgeInMonth;
    }

    @Override
    public Date getNextMaintenanceDate() {
        maintenanceDate = this.carObj.getNext_time_maintenance();
        try {
            nextMaintenanceDate = this.carObj.parseDate(maintenanceDate,
                    context.getResources().getString(R.string.date_format_UTC));
            return nextMaintenanceDate;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public double getRemainingKm() {
        remainKm = this.carObj.service_every - (this.carObj.getMileage() % this.carObj.service_every);
        return remainKm;
    }

    @Override
    public int getNumberOfBreakDown() {
        return this.carObj.numberOfBreakDown;
    }

    @Override
    public int getNumberOfAccident() {
        return this.carObj.numberOfAccident;
    }
}
