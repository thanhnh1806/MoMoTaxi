package bhtech.com.cabbydriver.ChooseCar;

import java.util.ArrayList;

import bhtech.com.cabbydriver.object.CarObj;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class ChooseCarInterface {
    public interface Delegate {

        void onDialogViewFinish();

        void onButtonStartClick();

        void onItemClick();

        void showPopup();

        void closePopup();

        void changeCar();
    }

    public interface Datasource {

        String getDriverName();

        String getCurrentTime();

        String getCurrentDay();

        String getCurrentDateMonth();

        int getFirstText();

        ArrayList<CarObj> getListCar();

        String getDriverCarNumber();

        int getCarStatus(int position);

        void setCarDriverObj_Car(CarObj carObj);

        float getCarMileagePrevious(int position);

        void setCarDriverMileage(float mileage);

        float getCarDriverMileage();

        void setPosition(int position);

        int getPosition();

        boolean checkCarStatus();
    }
}
