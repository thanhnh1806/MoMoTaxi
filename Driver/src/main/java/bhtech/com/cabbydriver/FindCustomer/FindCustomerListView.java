package bhtech.com.cabbydriver.FindCustomer;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CustomerRequestObj;
import bhtech.com.cabbydriver.SupportClasses.OnSwipeTouchListener;

public class FindCustomerListView extends Fragment {
    FindCustomerInterface.Listener listener;
    FindCustomerInterface.DataSource dataSource;

    public void setListener(FindCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(FindCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FindCustomerListView() {

    }

    private Context context;
    private TextView tvScroll;
    private ListView lvCustomer;
    private FindCustomerListViewAdapter adapter;
    private ArrayList<Integer> listDistance;
    private ArrayList<CustomerRequestObj> listRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_driver_find_customer_list_view, container, false);

        tvScroll = (TextView) v.findViewById(R.id.tvScroll);
        lvCustomer = (ListView) v.findViewById(R.id.lvCustomer);

        return v;
    }

    public void reloadView() {
        listDistance = dataSource.getListDistance();
        listRequest = dataSource.getListRequest();
        if (listRequest != null && listDistance != null) {
            adapter = new FindCustomerListViewAdapter(context, listRequest, listDistance);
            lvCustomer.setAdapter(adapter);
            lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dataSource.setListViewPositionClick(position);
                    listener.OnListViewItemClick();
                }
            });

            lvCustomer.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onSwipeTop() {
                    tvScroll.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSwipeBottom() {
                    tvScroll.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }
}
