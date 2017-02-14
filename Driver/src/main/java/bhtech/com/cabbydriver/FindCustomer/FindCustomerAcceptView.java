package bhtech.com.cabbydriver.FindCustomer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CustomerRequestObj;

/**
 * Created by thanh_nguyen02 on 22/01/2016.
 */
public class FindCustomerAcceptView extends Fragment implements View.OnClickListener {
    private FindCustomerInterface.Listener listener;
    private FindCustomerInterface.DataSource dataSource;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private ImageView ivClose;
    private TextView tvStartPlace, tvStartAddress, tvStopPlace, tvStopAddress, tvUserId,
            tvBookingFurtureDate, tvBookingFurtureTime;
    private FrameLayout layoutBookingFurture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_find_customer_accept_view, container, false);
        layoutBookingFurture = (FrameLayout) v.findViewById(R.id.layoutBookingFurture);
        tvBookingFurtureDate = (TextView) v.findViewById(R.id.tvBookingFurtureDate);
        tvBookingFurtureTime = (TextView) v.findViewById(R.id.tvBookingFurtureTime);

        ivClose = (ImageView) v.findViewById(R.id.ivClose);
        tvStartPlace = (TextView) v.findViewById(R.id.tvStartPlace);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvStopPlace = (TextView) v.findViewById(R.id.tvStopPlace);
        tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);
        tvUserId = (TextView) v.findViewById(R.id.tvUserId);

        ivClose.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        ArrayList<CustomerRequestObj> list = dataSource.getListRequest();
        CustomerRequestObj requestObj = list.get(dataSource.getListViewPositionClick());
        tvStartPlace.setText(String.valueOf(requestObj.getFrom_name()));
        tvStartAddress.setText(String.valueOf(requestObj.getFrom_address()));
        tvStopPlace.setText(String.valueOf(requestObj.getTo_name()));
        tvStopAddress.setText(String.valueOf(requestObj.getTo_address()));
        tvUserId.setText(String.valueOf(requestObj.getCustomers_id()));

        try {
            Date pickUpTime = requestObj.getPickup_time();
            if (pickUpTime != null) {
                layoutBookingFurture.setVisibility(View.VISIBLE);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.US);
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                tvBookingFurtureDate.setText(dateFormat.format(pickUpTime));
                tvBookingFurtureTime.setText(timeFormat.format(pickUpTime) + " ");
            } else {
                layoutBookingFurture.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            layoutBookingFurture.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                listener.OnButtonCloseDialogClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }
}
