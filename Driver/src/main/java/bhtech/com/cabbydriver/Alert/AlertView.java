package bhtech.com.cabbydriver.Alert;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;


public class AlertView extends Fragment {
    Context context;
    ExpandableListView listAlerts;
    ArrayList<String> listSortBy = new ArrayList<>();

    //  Top bar
    AutoCompleteTextView atvSortBy;
    AutoCompleteTextView atvMonth;
    AutoCompleteTextView atvCalendar;

    TextView tvSortBy;
    TextView tvFilterMonth;
    LinearLayout layoutCalendar;
    TextView tvCalendar;
    ImageView imgSortByArrow;

    AlertInterface.Delegate delegate;
    AlertInterface.DataSource dataSource;

    AlertAdapter adapter;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDelegate(AlertInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    public void setDataSource(AlertInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trip_history_view, container, false);   //  The list view has the same view as Trip History, so reuse it
        listAlerts = (ExpandableListView) v.findViewById(R.id.listTripHistory);                       //  The list view has the same view as Trip History, so reuse it
        atvMonth = (AutoCompleteTextView) v.findViewById(R.id.atvFilterMonth);
        atvCalendar = (AutoCompleteTextView) v.findViewById(R.id.atvCalendar);
        atvSortBy = (AutoCompleteTextView) v.findViewById(R.id.atvSortBy);
        imgSortByArrow = (ImageView) v.findViewById(R.id.imgSortByArrow);

        tvSortBy = (TextView) v.findViewById(R.id.tvSortBy);
        tvFilterMonth = (TextView) v.findViewById(R.id.tvFilterMonth);
        tvCalendar = (TextView) v.findViewById(R.id.tvCalendar);
        layoutCalendar = (LinearLayout) v.findViewById(R.id.layoutCalendar);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        imgSortByArrow.setVisibility(View.INVISIBLE);

        atvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atvMonth.isPopupShowing()) {
                    atvMonth.dismissDropDown();
                } else {
                    atvMonth.showDropDown();
                }
            }
        });

        layoutCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atvCalendar.isPopupShowing()) {
                    atvCalendar.dismissDropDown();
                } else {
                    atvCalendar.showDropDown();
                }
            }
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, dataSource.getListMonth());
        atvMonth.setAdapter(monthAdapter);
        atvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvFilterMonth.setText(dataSource.getListMonth().get(position));
                delegate.filterByMonth(position);
            }
        });

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, dataSource.getListDate());
        atvCalendar.setAdapter(dayAdapter);
        atvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvCalendar.setText(dataSource.getListDate().get(position));
                delegate.filterByDay(position);
            }
        });

        adapter = new AlertAdapter(context);
        adapter.setDelegate(delegate);
        adapter.setDataSource(dataSource);

        listAlerts.setAdapter(adapter);

        listAlerts.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                delegate.groupOnClickAtPosition(groupPosition);
                return false;
            }
        });

        listAlerts.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                delegate.groupExpandedAtPosition(groupPosition);
            }
        });

        listAlerts.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                delegate.groupCollapseAtPosition(groupPosition);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCalendar.setText(dataSource.getCurrentDate());
        tvFilterMonth.setText(dataSource.getCurrentMonth());
    }

    public void collapseGroupAtPosition(int groupPosition) {
        listAlerts.collapseGroup(groupPosition);
    }

    public void reloadView() {
        adapter.notifyDataSetChanged();
    }
}
