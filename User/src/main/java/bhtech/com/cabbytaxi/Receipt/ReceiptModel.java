package bhtech.com.cabbytaxi.Receipt;

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
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by Le Anh Tuan on 2/16/2016.
 */
public class ReceiptModel implements ReceiptInterface.Datasource {
    private Context context;
    private DateFormat timeFormatAMPM = new SimpleDateFormat("hh:mm a", Locale.US);

    private String date, hours, companyManager, companyPhoneNumber, companyAddress, driverName,
            carNumber, startAddress, endAddress, minutes, km, payMode, charge;

    private ArrayList<ReceiptObject> listReceipt = new ArrayList<>();
    private ArrayList<ReceiptObject> listReceiptTemp = new ArrayList<>();
    private ArrayList<String> listSortBy;
    private int listViewPositionClick = 0;
    private int sortbyUserChoose = 0;
    private int monthItemPosition = TimeObject.getCurrentMonth();

    public ReceiptModel(Context context) {
        this.context = context;
    }


    public DateFormat getDateFormat() {
        return new SimpleDateFormat("dd MMM yyyy", Locale.US);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setCompanyManager(String companyManager) {
        this.companyManager = companyManager;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    @Override
    public ArrayList<ReceiptObject> getReceiptList() {
        return listReceipt;
    }

    @Override
    public ArrayList<String> getListMonth() {
        return TimeObject.getListMonth();
    }

    @Override
    public ArrayList<String> getListSortBy() {
        return listSortBy;
    }

    public void setListSortBy(ArrayList<String> listSortBy) {
        this.listSortBy = listSortBy;
    }

    public void setListSortBy(Context context) {
        ArrayList<String> list = new ArrayList<>();
        list.add(context.getString(R.string.amount));
        list.add(context.getString(R.string.date));
        setListSortBy(list);
    }

    @Override
    public void setListItemPositionClick(int position) {
        listViewPositionClick = position;
    }

    @Override
    public int getListItemPositionClick() {
        return listViewPositionClick;
    }

    @Override
    public int getSortByUserChoose() {
        return sortbyUserChoose;
    }

    @Override
    public void setSortByUserChoose(int position) {
        sortbyUserChoose = position;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getHours() {
        return hours;
    }

    @Override
    public String getCompanyManager() {
        return companyManager;
    }

    @Override
    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    @Override
    public String getCompanyAddress() {
        return companyAddress;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public String getCarNumber() {
        return carNumber;
    }

    @Override
    public String getStartAddress() {
        return startAddress;
    }

    @Override
    public String getEndAddress() {
        return endAddress;
    }

    @Override
    public String getMinutes() {
        return minutes;
    }

    @Override
    public String getKm() {
        return km;
    }

    @Override
    public String getCharge() {
        return charge;
    }

    @Override
    public String getPayMode() {
        return payMode;
    }

    @Override
    public String getCurrentMonth() {
        return TimeObject.getListMonth().get(TimeObject.getCurrentMonth());
    }

    @Override
    public void setListMonthItemPositionClick(int position) {
        monthItemPosition = position;
    }

    public void setDataDetail(Context context) {
        ReceiptObject receipt = listReceipt.get(listViewPositionClick);
        setDate(TimeObject.parseDate(receipt.getUpdatedDate(), getDateFormat()));
        setHours(TimeObject.parseDate(receipt.getUpdatedDate(), timeFormatAMPM));

        if (receipt.getResponseDriver().getDriver().getDriver_company() != null) {
            setCompanyPhoneNumber(receipt.getResponseDriver().getDriver().getDriver_company().getPhoneNumber());
            setCompanyAddress(receipt.getResponseDriver().getDriver().getDriver_company().getAddress());
        }
        setCompanyManager(UserObj.getInstance().getFullName());
        setStartAddress(receipt.getFromAddress());
        setEndAddress(receipt.getToAddress());
        setDriverName(receipt.getResponseDriver().getDriver().getFullName());
        setCarNumber(receipt.getResponseDriver().getCar().getNumber());
        setMinutes(String.valueOf(receipt.getHours()) + " " + context.getString(R.string.mins));
        setKm(String.valueOf(receipt.getMileage()) + " " + context.getString(R.string.km));

        if (receipt.getPayby() == ContantValuesObject.PayByCash) {
            setPayMode(context.getString(R.string.payment_by_cash));
        } else {
            setPayMode(context.getString(R.string.payment_by_creditcard));
        }

        setCharge("$ " + StringObject.getDecimalFormat(2).format(receipt.getPrice() + receipt.getExtra_price()));
    }

    public void sortReceiptList() {
        ArrayList list = getReceiptList();
        if (getSortByUserChoose() == 0) {
            Collections.sort(list, new Comparator<ReceiptObject>() {

                @Override
                public int compare(ReceiptObject arg0, ReceiptObject arg1) {
                    float date1 = arg0.getPrice();
                    float date2 = arg1.getPrice();

                    if (date1 == date2) {
                        return 0;
                    } else if (date1 > date2) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            setListReceipt(list);
        } else {
            Collections.sort(list, new Comparator<ReceiptObject>() {

                @Override
                public int compare(ReceiptObject arg0, ReceiptObject arg1) {
                    Date date1 = arg0.getUpdatedDate();
                    Date date2 = arg1.getUpdatedDate();
                    if (date1 == null) {
                        return -1;
                    } else if (date2 == null) {
                        return 1;
                    } else if (date1.before(date2)) {
                        return 1;
                    } else {
                        return -1;
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

    public String getReceiptDetail() {
        ReceiptObject receiptObject = getReceiptList().get(getListItemPositionClick());
        String total = StringObject.getDecimalFormat(2).format(receiptObject.getPrice() + receiptObject.getExtra_price());
        String payBy = "";
        if (receiptObject.getPayby() == ContantValuesObject.PayByCash) {
            payBy = context.getString(R.string.pay_by_cash);
        } else {
            payBy = context.getString(R.string.pay_by_creadit_card);
        }

        String driverCompany = "";
        if (receiptObject.getResponseDriver().getDriver().getDriver_company() != null) {
            driverCompany = context.getString(R.string.line_break) + context.getString(R.string.line_break)
                    + context.getString(R.string.taxi_company) + context.getString(R.string.line_break)
                    + context.getString(R.string.name) + ": "
                    + receiptObject.getResponseDriver().getDriver().getDriver_company().getName()
                    + context.getString(R.string.line_break) + context.getString(R.string.phone_number) + ": "
                    + receiptObject.getResponseDriver().getDriver().getDriver_company().getPhoneNumber()
                    + context.getString(R.string.line_break) + context.getString(R.string.address) + ": "
                    + receiptObject.getResponseDriver().getDriver().getDriver_company().getAddress();
        }

        String receiptDetail = context.getString(R.string.date) + ": "
                + TimeObject.parseDate(receiptObject.getUpdatedDate(), new SimpleDateFormat(TimeObject.DATE_PATTERN))
                + driverCompany
                + context.getString(R.string.line_break) + context.getString(R.string.line_break)
                + context.getString(R.string.issued_to) + ": " + UserObj.getInstance().getFullName()
                + context.getString(R.string.line_break) + context.getString(R.string.line_break)
                + context.getString(R.string.driver_information) + context.getString(R.string.line_break)
                + context.getString(R.string.name) + ": " + receiptObject.getResponseDriver().getDriver().getFullName()
                + context.getString(R.string.line_break)
                + context.getString(R.string.vehicle) + ": " + receiptObject.getResponseDriver().getCar().getNumber()
                + context.getString(R.string.line_break) + context.getString(R.string.line_break)
                + context.getString(R.string.from) + ": " + receiptObject.getFromAddress()
                + context.getString(R.string.line_break)
                + context.getString(R.string.to) + ": " + receiptObject.getToAddress()
                + context.getString(R.string.line_break) + context.getString(R.string.line_break)
                + context.getString(R.string.time) + ": " + StringObject.getDecimalFormat(2).format(receiptObject.getHours())
                + " " + context.getString(R.string.mins).toLowerCase()
                + context.getString(R.string.line_break) + context.getString(R.string.distance)
                + ": " + StringObject.getDecimalFormat(2).format(receiptObject.getMileage())
                + " " + context.getString(R.string.km).toLowerCase()
                + context.getString(R.string.line_break) + payBy + " " + total + " $";

        return receiptDetail;
    }

    public interface OnDeleteReceipt {
        void onSuccess();

        void onFailure(Error error);
    }

    public void deleteReceipt(Context context, final OnDeleteReceipt OnFinish) {
        Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Map<String, List<String>> params = new HashMap<>();
            params.put("delete_by_customers", Arrays.asList(ContantValuesObject.REQUEST_ID_NULL));

            int receiptId = listReceipt.get(listViewPositionClick).getId();

            NetworkServices.callAPI(context, ContantValuesObject.DELETE_RECEIPT + receiptId,
                    NetworkServices.PUT,
                    headers, params, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            OnFinish.onSuccess();
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            OnFinish.onFailure(error);
        }
    }

    public interface onGetListReceipt {
        void onSuccess();

        void onFailure(Error error);
    }

    private void setListReceipt(ArrayList<ReceiptObject> list) {
        listReceipt = list;
    }


    public void getReceiptList(Context context, final onGetListReceipt onFinish) {
        final bhtech.com.cabbytaxi.object.Error error = new Error();
        listReceipt = new ArrayList<>();
        if (NetworkObject.isNetworkConnect(context)) {
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
                                        if (receipt.getPickup_time() == null) {
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
