package bhtech.com.cabbydriver.ChooseCar;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.CarObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.DriverCompanyObj;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class ChooseCarModel implements ChooseCarInterface.Datasource {
    TimeObject timeObject = new TimeObject();
    public static final String NULL = "";
    public static final int MORNING = 0;
    public static final int AFTERNOON = 1;
    public static final int EVENING = 2;
    public static final int i = 0;
    final DriverCompanyObj companyObj = new DriverCompanyObj();
    int listViewPosition;

    private ArrayList<CarObj> listCar;
    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.US);

    @Override
    public String getDriverName() {
        return CarDriverObj.getInstance().getDriver().getUsername();
    }

    @Override
    public String getCurrentTime() {
        return timeObject.getCurrentTime(dateFormat);
    }

    @Override
    public String getCurrentDay() {
        int i = timeObject.getCurrentDay();
        String day = NULL;
        switch (i) {
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
            case 1:
                day = "Sunday";
                break;
        }
        return day;
    }

    @Override
    public String getCurrentDateMonth() {
        String month = NULL;
        int i = timeObject.getCurrentMonth();
        switch (i) {
            case 0:
                month = "Jan";
                break;
            case 1:
                month = "Feb";
                break;
            case 2:
                month = "Mar";
                break;
            case 3:
                month = "Apr";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "Jun";
                break;
            case 6:
                month = "Jul";
                break;
            case 7:
                month = "Aug";
                break;
            case 8:
                month = "Sep";
                break;
            case 9:
                month = "Oct";
                break;
            case 10:
                month = "Nov";
                break;
            case 11:
                month = "Dec";
                break;
        }
        return timeObject.getCurrentDateofMonth() + " " + month;
    }

    @Override
    public int getFirstText() {
        int result = MORNING;
        String _time = NULL;
        String[] array1 = new String[10];
        String[] array2 = new String[10];
        _time = timeObject.getCurrentTime(dateFormat);
        array1 = _time.split(" ");
        if (array1[1].toString().compareTo("AM") == 0 || array1[1].toString().compareTo("am") == 0) {
            result = MORNING;
        } else {
            array2 = array1[0].split(":");
            if (Integer.parseInt(array2[0].toString()) > 5) {
                result = EVENING;
            } else {
                result = AFTERNOON;
            }
        }
        return result;
    }

    @Override
    public ArrayList<CarObj> getListCar() {
        return listCar;
    }

    public void setListCar(ArrayList<CarObj> listCar) {
        this.listCar = listCar;
    }

    @Override
    public String getDriverCarNumber() {
        if (CarDriverObj.getInstance().getCar() != null) {
            return CarDriverObj.getInstance().getCar().getNumber();
        } else {
            return null;
        }
    }

    @Override
    public int getCarStatus(int position) {
        return listCar.get(position).getStatus();
    }

    @Override
    public void setCarDriverObj_Car(CarObj carObj) {
        CarDriverObj.getInstance().setCar(carObj);
    }

    @Override
    public float getCarMileagePrevious(int position) {
        return 5000;
    }

    @Override
    public void setCarDriverMileage(float mileage) {
        CarDriverObj.getInstance().setMileage(mileage);
    }

    @Override
    public float getCarDriverMileage() {
        return CarDriverObj.getInstance().getMileage();
    }

    @Override
    public void setPosition(int position) {
        listViewPosition = position;
    }

    @Override
    public int getPosition() {
        return listViewPosition;
    }

    @Override
    public boolean checkCarStatus() {
        boolean carStatus = false;
        if (listCar.get(listViewPosition).getStatus() == ContantValuesObject.DRIVER_NOT_AVAILABLE) {
            carStatus = false;
        } else {
            if (listCar.get(listViewPosition).getStatus() == ContantValuesObject.DRIVER_AVAILABLE)
                carStatus = true;
        }
        return carStatus;
    }

    public interface OnChooseCar {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getListCar(final Context context, final OnChooseCar onFinish) {
        companyObj.getListVehicleType(context, new DriverCompanyObj.OnGetListVehicleTypeFinish() {
            @Override
            public void success() {
                companyObj.getListCar(context, new DriverCompanyObj.OnGetListCarFinish() {
                    @Override
                    public void success() {
                        setListCar(companyObj.listCar);
                        onFinish.Success();
                    }

                    @Override
                    public void failure(ErrorObj error) {
                        onFinish.Failure(error);
                    }
                });
            }

            @Override
            public void failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }

    public interface OnChooseCarFinish {
        void success();

        void failure(ErrorObj error);
    }

    public void chooseCar(final Context context, final OnChooseCarFinish onFinish) {
        CarDriverObj.getInstance().chooseCar(context, new CarDriverObj.OnDriverChooseCar() {
            @Override
            public void success() {
                onFinish.success();
            }

            @Override
            public void failure(ErrorObj error) {
                onFinish.failure(error);
            }
        });
    }
}
