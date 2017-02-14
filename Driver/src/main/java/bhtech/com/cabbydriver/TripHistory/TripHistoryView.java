package bhtech.com.cabbydriver.TripHistory;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class TripHistoryView extends Fragment {
    private Context context;
    private TripHistoryInterface.DataSource dataSource;
    private TripHistoryInterface.Delegate delegate;

    public void setDataSource(TripHistoryInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDelegate(TripHistoryInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    //  Top bar
    private AutoCompleteTextView atvSortBy, atvMonth, atvCalendar;
    private TextView tvSortBy, tvFilterMonth, tvCalendar;
    private LinearLayout layoutCalendar, layoutSortBy;

    //  List
    private ExpandableListView listTripHistory;
    private TripHistoryAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_trip_history_view, container, false);
        atvSortBy = (AutoCompleteTextView) v.findViewById(R.id.atvSortBy);
        atvMonth = (AutoCompleteTextView) v.findViewById(R.id.atvFilterMonth);
        atvCalendar = (AutoCompleteTextView) v.findViewById(R.id.atvCalendar);
        layoutSortBy = (LinearLayout) v.findViewById(R.id.layoutSortBy);
        tvSortBy = (TextView) v.findViewById(R.id.tvSortBy);
        tvFilterMonth = (TextView) v.findViewById(R.id.tvFilterMonth);
        layoutCalendar = (LinearLayout) v.findViewById(R.id.layoutCalendar);
        tvCalendar = (TextView) v.findViewById(R.id.tvCalendar);
        listTripHistory = (ExpandableListView) v.findViewById(R.id.listTripHistory);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        tvCalendar.setText(dataSource.getCurrentDate());
        tvFilterMonth.setText(dataSource.getCurrentMonth().substring(0, 3));

        layoutSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atvSortBy.isPopupShowing()) {
                    atvSortBy.dismissDropDown();
                } else {
                    atvSortBy.showDropDown();
                }
            }
        });

        tvFilterMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvMonth.showDropDown();
            }
        });

        layoutCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvCalendar.showDropDown();
            }
        });

        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, dataSource.getListSortBy());

        atvSortBy.setAdapter(sortByAdapter);
        atvSortBy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                collapseAllGroup();
                if (position == 0) {
                    delegate.sortByDate();
                } else {
                    delegate.sortByCost();
                }
            }
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, dataSource.getListMonth());

        atvMonth.setAdapter(monthAdapter);
        atvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                collapseAllGroup();
                tvFilterMonth.setText(dataSource.getListMonth().get(position).substring(0, 3));
                delegate.filterByMonth(position);
            }
        });

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, dataSource.getListDate());
        atvCalendar.setAdapter(dayAdapter);
        atvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                collapseAllGroup();
                tvCalendar.setText(dataSource.getListDate().get(position));
                delegate.filterByDay(position);
            }
        });

        adapter = new TripHistoryAdapter(context);
        adapter.setDataSource(dataSource);

        listTripHistory.setAdapter(adapter);

        listTripHistory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                delegate.groupOnClickAtPosition(groupPosition);
                return false;
            }
        });

        listTripHistory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                delegate.groupExpandedAtPosition(groupPosition);
            }
        });

        listTripHistory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                delegate.groupCollapseAtPosition(groupPosition);
            }
        });
    }

    private void collapseAllGroup() {
        for (int i = 0; i < dataSource.getListOfTripHistory().size(); i++) {
            listTripHistory.collapseGroup(i);
        }
    }

    public void reloadView() {
        adapter.notifyDataSetChanged();
    }

    public void collapseGroupAtPosition(int groupPosition) {
        listTripHistory.collapseGroup(groupPosition);
    }
}
