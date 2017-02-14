package bhtech.com.cabbytaxi.FavouriteLocation;


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

import bhtech.com.cabbytaxi.R;


public class FavouriteLocationListView extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context context;
    private FavouriteLocationInterface.Listener listener;
    private FavouriteLocationInterface.Datasource datasource;

    public void setListener(FavouriteLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FavouriteLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_favourite_location_list_view, container, false);
        initView(v);
        initRecyclerView(v);
        return v;
    }

    private FrameLayout ivAdd;
    private AutoCompleteTextView spMonth;
    private TextView tvMonth;
    private ListView rvFavouriteLocation;
    private ArrayAdapter<String> adapterSpinner;
    private FavouriteLocationAdapter adapterRecyclerView;

    private void initView(View v) {
        ivAdd = (FrameLayout) v.findViewById(R.id.ivAdd);
        spMonth = (AutoCompleteTextView) v.findViewById(R.id.spMonth);
        adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                datasource.getListMonth());
        spMonth.setAdapter(adapterSpinner);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);

        ivAdd.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        spMonth.setOnItemClickListener(this);
    }

    private void initRecyclerView(View v) {
        rvFavouriteLocation = (ListView) v.findViewById(R.id.rvFavouriteLocation);
    }

    public void reloadView() {
        adapterRecyclerView = new FavouriteLocationAdapter(datasource.getListFavouriteLocation(),
                listener, datasource);
        rvFavouriteLocation.setAdapter(adapterRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMonth.setText(datasource.getListMonth().get(datasource.getMonthUserChoose()).substring(0, 3));
        listener.onListViewCreateViewFinish();
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
            case R.id.ivAdd:
                listener.onButtonAddClick();
                break;
            case R.id.tvMonth:
                spMonth.showDropDown();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tvMonth.setText(datasource.getListMonth().get(position).substring(0, 3).toUpperCase());
        datasource.setMonthUserChoose(position);
        listener.onSpinnerItemClick();
    }
}
