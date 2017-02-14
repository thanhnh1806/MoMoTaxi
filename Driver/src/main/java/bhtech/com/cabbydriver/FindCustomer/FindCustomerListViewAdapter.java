package bhtech.com.cabbydriver.FindCustomer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CustomerRequestObj;

/**
 * Created by thanh_nguyen02 on 21/01/2016.
 */
public class FindCustomerListViewAdapter extends ArrayAdapter<CustomerRequestObj> {
    private Context context;
    private ArrayList<CustomerRequestObj> list;
    private ArrayList<Integer> listDistance;

    public FindCustomerListViewAdapter(Context context, ArrayList<CustomerRequestObj> list,
                                       ArrayList<Integer> listDistance) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.listDistance = listDistance;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = LayoutInflater.from(context).inflate(R.layout.driver_find_customer_list_item, null);

        FrameLayout layoutNormal = (FrameLayout) v.findViewById(R.id.layoutNormal);
        FrameLayout layoutBookingFurture = (FrameLayout) v.findViewById(R.id.layoutBookingFurture);

        TextView tvBookingFurtureTime = (TextView) v.findViewById(R.id.tvBookingFurtureTime);
        TextView tvBookingFurtureDate = (TextView) v.findViewById(R.id.tvBookingFurtureDate);
        TextView tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        TextView tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        TextView tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);

        tvDistance.setText(String.valueOf((listDistance.get(position))) + "m");
        tvStartAddress.setText(list.get(position).getFrom_address());
        tvStopAddress.setText(list.get(position).getTo_address());

        try {
            Date pickUpTime = list.get(position).getPickup_time();
            Log.w("PickUp Time", pickUpTime.toString() + pickUpTime.toGMTString() + pickUpTime.toLocaleString());
            if (pickUpTime != null) {
                layoutBookingFurture.setVisibility(View.VISIBLE);
                layoutNormal.setVisibility(View.GONE);
            } else {
                layoutBookingFurture.setVisibility(View.GONE);
                layoutNormal.setVisibility(View.VISIBLE);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            tvBookingFurtureDate.setText(dateFormat.format(pickUpTime));
            tvBookingFurtureTime.setText(timeFormat.format(pickUpTime));
        } catch (Exception e) {
            layoutBookingFurture.setVisibility(View.GONE);
            layoutNormal.setVisibility(View.VISIBLE);
        }
        return v;
    }
}
