package bhtech.com.cabbytaxi.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TimeObject;

/**
 * Created by thanh_nguyen on 22/03/2016.
 */
public class HistoryAdapter extends ArrayAdapter<ReceiptObject> {
    private Context context;
    private ArrayList<ReceiptObject> list;
    private HistoryInterface.Datasource datasource;

    public HistoryAdapter(Context context, ArrayList<ReceiptObject> listReceipt,
                          HistoryInterface.Datasource datasource) {
        super(context, 0, listReceipt);
        this.context = context;
        this.list = listReceipt;
        this.datasource = datasource;
    }

    private TextView tvDate, tvTime, tvFromAddress, tvToAddress, tvDriverName, tvCardNumber,
            tvPrice, tvFutureBooking, tvFutureBookingDate, tvFutureBookingTime;
    private LinearLayout layoutNormal, layoutFutureBooking;
    private FrameLayout layoutFutureBookingDateTime;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_list_item, null);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvFromAddress = (TextView) v.findViewById(R.id.tvFromAddress);
        tvToAddress = (TextView) v.findViewById(R.id.tvToAddress);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvCardNumber = (TextView) v.findViewById(R.id.tvCardNumber);
        tvPrice = (TextView) v.findViewById(R.id.tvPrice);
        layoutNormal = (LinearLayout) v.findViewById(R.id.layoutNormal);
        tvFutureBooking = (TextView) v.findViewById(R.id.tvFutureBooking);
        layoutFutureBooking = (LinearLayout) v.findViewById(R.id.layoutFutureBooking);
        tvFutureBookingDate = (TextView) v.findViewById(R.id.tvFutureBookingDate);
        tvFutureBookingTime = (TextView) v.findViewById(R.id.tvFutureBookingTime);
        layoutFutureBookingDateTime = (FrameLayout) v.findViewById(R.id.layoutFutureBookingDateTime);

        tvFromAddress.setText(list.get(position).getFromAddress());
        tvToAddress.setText(list.get(position).getToAddress());

        if (list.get(position).getResponseDriver() != null) {
            tvDriverName.setText(list.get(position).getResponseDriver().getDriver().getFullName());
            tvCardNumber.setText(list.get(position).getResponseDriver().getCar().getNumber());
        }

        float totalPrice = list.get(position).getPrice() + list.get(position).getExtra_price();
        if (totalPrice > 0) {
            tvPrice.setText(" " + StringObject.getDecimalFormat(2).format(totalPrice));
        } else {
            tvPrice.setText(" 0");
        }

        if (list.get(position).getPickup_time() != null) {
            layoutFutureBooking.setVisibility(View.VISIBLE);
            tvFutureBookingDate.setText(TimeObject.parseDate(list.get(position).getPickup_time(), datasource.getDateFormat()));
            tvFutureBookingTime.setText(TimeObject.parseDate(list.get(position).getPickup_time(), datasource.getTimeFormat()));

            if (list.get(position).getStatus() == ContantValuesObject.TaxiRequestStatusCancelled) {
                String date = TimeObject.parseDate(list.get(position).getUpdatedDate(), datasource.getDateFormat());
                String time = TimeObject.parseDate(list.get(position).getUpdatedDate(), datasource.getTimeFormat());
                layoutNormal.setVisibility(View.GONE);
                tvFutureBooking.setVisibility(View.VISIBLE);
                tvFutureBooking.setText(context.getString(R.string.this_booking_was_cancelled_on) + " " +
                        date + " " + context.getString(R.string.at) + " " + time);
                layoutFutureBookingDateTime.setBackgroundResource(R.drawable.rectangle_background_gray_radius_10);
            } else {
                String startDate = TimeObject.parseDate(list.get(position).getStart_time(), datasource.getDateFormat());
                String startTime = TimeObject.parseDate(list.get(position).getStart_time(), datasource.getTimeFormat());
                tvDate.setText(startDate);
                tvTime.setText(startTime);
                layoutNormal.setVisibility(View.VISIBLE);
                tvFutureBooking.setVisibility(View.GONE);
                layoutFutureBooking.setVisibility(View.GONE);
                layoutFutureBookingDateTime.setBackgroundResource(R.drawable.rectangle_background_blue_radius_10);
            }
        } else {
            String startDate = TimeObject.parseDate(list.get(position).getStart_time(), datasource.getDateFormat());
            String startTime = TimeObject.parseDate(list.get(position).getStart_time(), datasource.getTimeFormat());
            tvDate.setText(startDate);
            tvTime.setText(startTime);
            layoutFutureBooking.setVisibility(View.GONE);
            layoutNormal.setVisibility(View.VISIBLE);
            tvFutureBooking.setVisibility(View.GONE);
        }

        return v;
    }
}
