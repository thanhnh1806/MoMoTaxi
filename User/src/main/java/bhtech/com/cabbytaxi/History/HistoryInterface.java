package bhtech.com.cabbytaxi.History;

import java.text.DateFormat;
import java.util.ArrayList;

import bhtech.com.cabbytaxi.object.ReceiptObject;

/**
 * Created by thanh_nguyen on 22/03/2016.
 */
public class HistoryInterface {
    public interface Listener {
        void onCreateViewFinish();

        void onListSortByItemClick();

        void onListMonthItemClick();
    }

    public interface Datasource {
        void setListSortByItemPositionClick(int p);

        void setListMonthItemPositionClick(int p);

        String getDate();

        String getTime();

        String getFromAddress();

        String getToAddress();

        String getDriverName();

        String getCarNumber();

        String getPrice();

        ArrayList<String> getListSortBy();

        ArrayList<String> getListMonth();

        ArrayList<ReceiptObject> getListReceipt();

        DateFormat getDateFormat();

        DateFormat getTimeFormat();

        String getCurrentMonth();
    }
}
