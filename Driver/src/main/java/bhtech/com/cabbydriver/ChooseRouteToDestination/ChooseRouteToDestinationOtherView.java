package bhtech.com.cabbydriver.ChooseRouteToDestination;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class ChooseRouteToDestinationOtherView extends Fragment implements View.OnClickListener {
    ChooseRouteToDestinationInterface.Listener listener;
    ChooseRouteToDestinationInterface.Datasource datasource;

    public void setListener(ChooseRouteToDestinationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChooseRouteToDestinationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    FrameLayout btnGo, btnBack;
    TextView btnCloseNavi, tvKm, tvMins, tvGoVia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_route_to_destination_other_view, container, false);
        btnGo = (FrameLayout) v.findViewById(R.id.btnGo);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        btnCloseNavi = (TextView) v.findViewById(R.id.btnCloseNavi);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        tvMins = (TextView) v.findViewById(R.id.tvMins);
        tvGoVia = (TextView) v.findViewById(R.id.tvGoVia);

        btnGo.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        tvKm.setText("..." + datasource.getDistance() + " " + getString(R.string.km));
        tvMins.setText(datasource.getDuration() + " " + getString(R.string.mins));
        tvGoVia.setText(getString(R.string.via) + " " + datasource.getGoVia());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGo:
                listener.onButtonGoClick();
                break;

            case R.id.btnBack:
                listener.onButtonBackClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
