package bhtech.com.cabbytaxi.History;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;


public class HistoryView extends Fragment implements View.OnClickListener {
    private HistoryInterface.Listener listener;
    private HistoryInterface.Datasource datasource;

    public void setListener(HistoryInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(HistoryInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Context context;
    private AutoCompleteTextView acSortBy, actMonth;
    private TextView tvSortby, tvMonth;
    private ListView listReceipt;
    private ArrayList<String> listSortBy;
    private HistoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_view, container, false);
        tvSortby = (TextView) v.findViewById(R.id.tvSortby);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);
        acSortBy = (AutoCompleteTextView) v.findViewById(R.id.acSortBy);
        actMonth = (AutoCompleteTextView) v.findViewById(R.id.actMonth);
        listReceipt = (ListView) v.findViewById(R.id.listReceipt);

        tvMonth.setText(datasource.getCurrentMonth().substring(0, 3).toUpperCase());

        tvMonth.setOnClickListener(this);
        tvSortby.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<String> listMonth = datasource.getListMonth();
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listMonth);
        actMonth.setAdapter(adapterMonth);
        actMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvMonth.setText(listMonth.get(position).substring(0, 3).toUpperCase());
                datasource.setListMonthItemPositionClick(position);
                listener.onListMonthItemClick();
            }
        });

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
                    tvSortby.setText(listSortBy.get(position));
                    datasource.setListSortByItemPositionClick(position);
                    listener.onListSortByItemClick();
                }
            });
        }

        adapter = new HistoryAdapter(context, datasource.getListReceipt(), datasource);
        listReceipt.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMonth:
                actMonth.showDropDown();
                break;

            case R.id.tvSortby:
                acSortBy.showDropDown();
                break;

        }
    }
}
