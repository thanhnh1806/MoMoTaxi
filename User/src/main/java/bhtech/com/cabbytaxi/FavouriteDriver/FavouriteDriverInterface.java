package bhtech.com.cabbytaxi.FavouriteDriver;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.object.CarDriverObj;

/**
 * Created by thanh_nguyen on 01/03/2016.
 */
public class FavouriteDriverInterface {
    public interface Listener {
        void onListViewButtonDeleteClick();

        void onListViewIconPhoneClick();

        void onListViewIconEmailClick();
    }

    public interface Datasource {
        ArrayList<CarDriverObj> getListCarDriver();

        void setListViewPositionClick(int listViewPositionClick);

        int getListViewPositionClick();
    }
}
