package bhtech.com.cabbytaxi.FutureBooking;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;

public class FutureBookingView extends Fragment {
    private FutureBookingInterface.Listener listener;
    private FutureBookingInterface.Datasource datasource;

    public void setListener(FutureBookingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FutureBookingInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Context context;
    private FrameLayout layout_sort;
    private TextView tvSortby, tvMonth;
    private AutoCompleteTextView acSortBy;
    private ListView listReceipt;
    private boolean clickSortBy = false;
    private ArrayList<String> listSortBy;
    private FutureBookingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_future_booking_view, container, false);
        layout_sort = (FrameLayout) v.findViewById(R.id.layout_sort);
        tvSortby = (TextView) v.findViewById(R.id.tvSortby);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);
        acSortBy = (AutoCompleteTextView) v.findViewById(R.id.acSortBy);
        listReceipt = (ListView) v.findViewById(R.id.listReceipt);

        tvSortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickSortBy) {
                    clickSortBy = false;
                    acSortBy.dismissDropDown();
                } else {
                    clickSortBy = true;
                    acSortBy.showDropDown();
                }
            }
        });

        layout_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSortBy = false;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onCreateViewFinish();
    }

    public void reloadView() {
        listSortBy = datasource.getListSortBy();
        if (listSortBy != null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_1, listSortBy);

            acSortBy.setAdapter(arrayAdapter);
            acSortBy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickSortBy = false;
                    tvSortby.setText(listSortBy.get(position));
                    datasource.setListSortByItemPositionClick(position);
                    listener.onListSortByItemClick();
                }
            });

            adapter = new FutureBookingAdapter(context, datasource.getListReceipt(), listener, datasource);
            listReceipt.setAdapter(adapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
