package bhtech.com.cabbytaxi.Receipt;

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

/**
 * Created by Le Anh Tuan on 2/16/2016.
 */
public class ReceiptView extends Fragment {
    private Context context;
    private ReceiptInterface.Listener listener;
    private ReceiptInterface.Datasource datasource;

    private TextView tvSortby, tvMonth;
    private ListView listReceipt;
    private ReceiptAdapter adapter;
    private AutoCompleteTextView acSortBy, actMonth;
    private boolean clickSortBy = false;
    private ArrayList<String> listSortBy;

    public void setListener(ReceiptInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ReceiptInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_receipt_view, container, false);
        tvSortby = (TextView) v.findViewById(R.id.tvSortby);
        listReceipt = (ListView) v.findViewById(R.id.listReceipt);
        acSortBy = (AutoCompleteTextView) v.findViewById(R.id.acSortBy);
        actMonth = (AutoCompleteTextView) v.findViewById(R.id.actMonth);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);

        tvMonth.setText(datasource.getCurrentMonth().substring(0, 3).toUpperCase());

        tvSortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acSortBy.showDropDown();

            }
        });

        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actMonth.showDropDown();
            }
        });

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

        listener.onListViewCreateViewFinish();
    }

    public void reloadView() {
        listSortBy = datasource.getListSortBy();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listSortBy);
        acSortBy.setAdapter(arrayAdapter);
        acSortBy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSortBy = false;
                tvSortby.setText(listSortBy.get(position));
                datasource.setSortByUserChoose(position);
                listener.onSortByItemClick();
            }
        });

        adapter = new ReceiptAdapter(context, datasource.getReceiptList(), listener, datasource);
        listReceipt.setAdapter(adapter);
        listReceipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSortBy = false;
                datasource.setListItemPositionClick(position);
                listener.onListItemClick();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
