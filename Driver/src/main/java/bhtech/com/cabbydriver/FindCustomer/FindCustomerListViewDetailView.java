package bhtech.com.cabbydriver.FindCustomer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CustomerRequestObj;

public class FindCustomerListViewDetailView extends Fragment implements View.OnClickListener {
    FindCustomerInterface.Listener listener;
    FindCustomerInterface.DataSource dataSource;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FindCustomerListViewDetailView() {

    }

    private TextView tvStartPlace, tvStartAddress, tvStopPlace;
    private TextView tvStopAddress, tvUserId, tvPickUpLocation;
    private FrameLayout btnCancel, btnAccept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_find_customer_list_view_detail_view, container, false);
        tvStartPlace = (TextView) v.findViewById(R.id.tvStartPlace);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvStopPlace = (TextView) v.findViewById(R.id.tvStopPlace);
        tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);
        tvUserId = (TextView) v.findViewById(R.id.tvUserId);
        tvPickUpLocation = (TextView) v.findViewById(R.id.tvPickUpLocation);
        btnCancel = (FrameLayout) v.findViewById(R.id.btnCancel);
        btnAccept = (FrameLayout) v.findViewById(R.id.btnAccept);

        btnCancel.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        int position = dataSource.getListViewPositionClick();
        CustomerRequestObj obj = dataSource.getListRequest().get(position);
        tvStartPlace.setText(String.valueOf(obj.getFrom_name()));
        tvStartAddress.setText(String.valueOf(obj.getFrom_address()));
        tvStopPlace.setText(String.valueOf(obj.getTo_name()));
        tvStopAddress.setText(String.valueOf(obj.getTo_address()));
        tvUserId.setText(String.valueOf(obj.getCustomers_id()));
        //TODO get pickup Address
        //tvPickUpLocation.setText(String.valueOf(obj.get));
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }
}
