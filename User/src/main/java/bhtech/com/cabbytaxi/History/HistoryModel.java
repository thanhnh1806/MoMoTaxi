package bhtech.com.cabbytaxi.History;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 22/03/2016.
 */
public class HistoryModel implements HistoryInterface.Datasource {
    private String date, time, fromAddress, toAddress, driverName, carNumber, price;
    private ArrayList<String> listSortBy;
    private ArrayList<ReceiptObject> listReceipt = new ArrayList<>();
    private ArrayList<ReceiptObject> listReceiptTemp = new ArrayList<>();
    private int sortByItemPosition;
    private int monthItemPosition = TimeObject.getCurrentMonth();

    @Override
    public void setListSortByItemPositionClick(int p) {
        sortByItemPosition = p;
    }

    @Override
    public void setListMonthItemPositionClick(int p) {
        monthItemPosition = p;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getListSortBy() {
        return listSortBy;
    }

    public void setListSortBy(Context context) {
        ArrayList<String> list = new ArrayList<>();
        list.add(context.getString(R.string.amount));
        list.add(context.getString(R.string.date));
        setListSortBy(list);
    }

    public void setListSortBy(ArrayList<String> listSortBy) {
        this.listSortBy = listSortBy;
    }

    public ArrayList<String> getListMonth() {
        return TimeObject.getListMonth();
    }

    public ArrayList<ReceiptObject> getListReceipt() {
        return listReceipt;
    }

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("dd MMM yyyy", Locale.US);
    }

    @Override
    public DateFormat getTimeFormat() {
        return new SimpleDateFormat("hh:mm a", Locale.US);
    }

    @Override
    public String getCurrentMonth() {
        return TimeObject.getListMonth().get(TimeObject.getCurrentMonth());
    }

    public void setListReceipt(ArrayList<ReceiptObject> listReceipt) {
        this.listReceipt = listReceipt;
    }

    public void sortReceiptList() {
        ArrayList list = getListReceipt();
        if (sortByItemPosition == 0) {
            Collections.sort(list, new Comparator<ReceiptObject>() {

                @Override
                public int compare(ReceiptObject arg0, ReceiptObject arg1) {
                    float date1 = arg0.getPrice();
                    float date2 = arg1.getPrice();

                    if (date1 == date2) {
                        return 0;
                    } else if (date1 > date2) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            setListReceipt(list);
        } else {
            Collections.sort(list, new Comparator<ReceiptObject>() {

                @Override
                public int compare(ReceiptObject arg0, ReceiptObject arg1) {
                    Date date1, date2;

                    if (arg0.getStart_time() != null) {
                        date1 = arg0.getStart_time();
                    } else {
                        date1 = arg0.getUpdatedDate();
                    }

                    if (arg1.getStart_time() != null) {
                        date2 = arg1.getStart_time();
                    } else {
                        date2 = arg1.getUpdatedDate();
                    }

                    if (date1.before(date2)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            setListReceipt(list);
        }
    }

    public void listByMonth() {
        listReceipt = new ArrayList<>();
        for (int i = 0; i < listReceiptTemp.size(); i++) {
            if (listReceiptTemp.get(i).getUpdatedDate().getMonth() == monthItemPosition) {
                listReceipt.add(listReceiptTemp.get(i));
            }
        }
    }

    public interface onGetListReceipt {
        void onSuccess();

        void onFailure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void setListReceipt(Context context, final onGetListReceipt onFinish) {
        final bhtech.com.cabbytaxi.object.Error error = new bhtech.com.cabbytaxi.object.Error();
        if (NetworkObject.isNetworkConnect(context)) {
            listReceipt = new ArrayList<>();
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.HISTORY_ENDPOINT, NetworkServices.GET,
                    headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.NO_ERROR) {
                                    JSONArray results = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    for (int i = 0; i < results.length(); i++) {
                                        JSONObject object = results.getJSONObject(i);
                                        ReceiptObject receipt = new ReceiptObject();
                                        receipt.parseJsonToObject(object);
                                        if (receipt.getStatus() != ContantValuesObject.TaxiRequestStatusFutureBooking) {
                                            listReceipt.add(receipt);
                                        }
                                    }

                                    listReceiptTemp.clear();
                                    listReceiptTemp.addAll(listReceipt);
                                    onFinish.onSuccess();
                                } else {
                                    error.setError(Error.INVALID_INPUTS);
                                    onFinish.onFailure(error);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.onFailure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.onFailure(error);
        }
    }
}
