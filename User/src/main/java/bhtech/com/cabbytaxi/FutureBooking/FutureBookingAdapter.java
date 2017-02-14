package bhtech.com.cabbytaxi.FutureBooking;

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
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.TimeObject;

/**
 * Created by thanh_nguyen on 22/03/2016.
 */
public class FutureBookingAdapter extends ArrayAdapter<ReceiptObject> {
    private Context context;
    private ArrayList<ReceiptObject> list;
    private FutureBookingInterface.Listener listener;
    private FutureBookingInterface.Datasource datasource;

    public FutureBookingAdapter(Context context, ArrayList<ReceiptObject> listReceipt,
                                FutureBookingInterface.Listener listener,
                                FutureBookingInterface.Datasource datasource) {
        super(context, 0, listReceipt);
        this.context = context;
        this.list = listReceipt;
        this.listener = listener;
        this.datasource = datasource;
    }

    private TextView tvDate, tvTime, tvFromAddress, tvToAddress, tvDriverName, tvCardNumber,
            tvPrice, tvFutureBookingDate, tvFutureBookingTime, btnCancel;
    private LinearLayout layoutFutureBooking;
    private FrameLayout btnCall, btnMessage;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.future_booking_list_item, null);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvFromAddress = (TextView) v.findViewById(R.id.tvFromAddress);
        tvToAddress = (TextView) v.findViewById(R.id.tvToAddress);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvCardNumber = (TextView) v.findViewById(R.id.tvCardNumber);
        tvPrice = (TextView) v.findViewById(R.id.tvPrice);
        layoutFutureBooking = (LinearLayout) v.findViewById(R.id.layoutFutureBooking);
        tvFutureBookingDate = (TextView) v.findViewById(R.id.tvFutureBookingDate);
        tvFutureBookingTime = (TextView) v.findViewById(R.id.tvFutureBookingTime);
        btnCancel = (TextView) v.findViewById(R.id.btnCancel);
        btnCall = (FrameLayout) v.findViewById(R.id.btnCall);
        btnMessage = (FrameLayout) v.findViewById(R.id.btnMessage);

        if (list.get(position).getUpdatedDate() != null) {
            tvDate.setText(TimeObject.parseDate(list.get(position).getUpdatedDate(), datasource.getDateFormat()));
            tvTime.setText(TimeObject.parseDate(list.get(position).getUpdatedDate(), datasource.getTimeFormat()));
        }

        if (list.get(position).getPickup_time() != null) {
            layoutFutureBooking.setVisibility(View.VISIBLE);
            tvFutureBookingDate.setText(TimeObject.parseDate(list.get(position).getPickup_time(),
                    datasource.getDateFormat()));
            tvFutureBookingTime.setText(TimeObject.parseDate(list.get(position).getPickup_time(),
                    datasource.getTimeFormat()));
        } else {
            layoutFutureBooking.setVisibility(View.GONE);
        }

        tvFromAddress.setText(list.get(position).getFromAddress());
        tvToAddress.setText(list.get(position).getToAddress());

        if (list.get(position).getResponseDriver() != null) {
            tvDriverName.setText(list.get(position).getResponseDriver().getDriver().getFullName());
            tvCardNumber.setText(list.get(position).getResponseDriver().getCar().getNumber());
        }

        tvPrice.setText(String.valueOf(list.get(position).getPrice()));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onItemButtonCancelClick();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onItemButtonCallClick();
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onItemButtonMessageClick();
            }
        });
        return v;
    }
}
