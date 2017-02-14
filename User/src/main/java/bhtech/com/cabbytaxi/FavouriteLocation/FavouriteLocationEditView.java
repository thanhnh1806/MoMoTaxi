package bhtech.com.cabbytaxi.FavouriteLocation;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.FavouriteLocation.FavouriteLocationInterface;

public class FavouriteLocationEditView extends Fragment implements View.OnClickListener {
    private Context context;
    private FavouriteLocationInterface.Listener listener;
    private FavouriteLocationInterface.Datasource datasource;

    public void setListener(FavouriteLocationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FavouriteLocationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private TextView tvAddress;
    private TextView btnEditLocation;
    private TextView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_edit_favourite_location_view, container, false);
        tvAddress = (TextView) v.findViewById(R.id.tv_location);
        btnEditLocation = (TextView) v.findViewById(R.id.btnEditLocation);
        btnBack = (TextView) v.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);
        btnEditLocation.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        tvAddress.setText(datasource.getCompleteAddress());
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
            case R.id.btnBack:
                listener.onEditViewButtonBackClick();
                break;
            case R.id.btnEditLocation:
                listener.onEditViewButtonEditClick();
                break;
        }
    }
}
