package bhtech.com.cabbytaxi.Receipt;

import java.text.DateFormat;
import java.util.ArrayList;

import bhtech.com.cabbytaxi.object.ReceiptObject;

/**
 * Created by Le Anh Tuan on 2/16/2016.
 */
public class ReceiptInterface {
    public interface Listener {

        void onSortByItemClick();

        void onListItemClick();

        void onButtonDeleteClick();

        void onButtonMailClick();

        void onButtonBackClick();

        void onButtonPrintClick();

        void onListViewCreateViewFinish();

        void onListMonthItemClick();
    }

    public interface Datasource {
        ArrayList<ReceiptObject> getReceiptList();

        ArrayList<String> getListMonth();

        ArrayList<String> getListSortBy();

        void setListItemPositionClick(int position);

        int getListItemPositionClick();

        int getSortByUserChoose();

        void setSortByUserChoose(int position);

        DateFormat getDateFormat();

        String getDate();

        String getHours();

        String getCompanyManager();

        String getCompanyPhoneNumber();

        String getCompanyAddress();

        String getDriverName();

        String getCarNumber();

        String getStartAddress();

        String getEndAddress();

        String getMinutes();

        String getKm();

        String getCharge();

        String getPayMode();

        String getCurrentMonth();

        void setListMonthItemPositionClick(int position);
    }
}
