package bhtech.com.cabbydriver.ChooseRouteToCustomer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class ChooseRouteToCustomerOtherView extends Fragment implements View.OnClickListener {
    private ChooseRouteToCustomerInterface.Listener listener;
    private ChooseRouteToCustomerInterface.Datasource datasource;

    public void setListener(ChooseRouteToCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChooseRouteToCustomerInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    FrameLayout btnCall, btnMessage, btnReloadLocation, btnGo;
    TextView btnCloseNavi, tvKm, tvMins, tvGoVia, tvUsername, tvStartAddress, tvChangeLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_route_to_customer_other_view, container, false);
        btnCall = (FrameLayout) v.findViewById(R.id.btnCall);
        btnMessage = (FrameLayout) v.findViewById(R.id.btnMessage);
        btnReloadLocation = (FrameLayout) v.findViewById(R.id.btnReloadLocation);
        btnGo = (FrameLayout) v.findViewById(R.id.btnGo);
        btnCloseNavi = (TextView) v.findViewById(R.id.btnCloseNavi);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        tvMins = (TextView) v.findViewById(R.id.tvMins);
        tvGoVia = (TextView) v.findViewById(R.id.tvGoVia);
        tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);

        btnCall.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnReloadLocation.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        btnCloseNavi.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.otherviewCreateFinish();
    }

    public void reloadRequestInformation() {
        tvUsername.setText(datasource.getUsername());
        tvStartAddress.setText(datasource.getStartAddress());
    }

    public void reloadRouteInformation() {
        tvKm.setText("..." + datasource.getDistance() + " " + getString(R.string.km));
        tvMins.setText(datasource.getDuration() + " " + getString(R.string.mins));
        tvGoVia.setText(getString(R.string.via) + " " + datasource.getGoVia());
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
            case R.id.btnCall:
                listener.onButtonCallClick();
                break;
            case R.id.btnMessage:
                listener.onButtonMessageClick();
                break;
            case R.id.btnReloadLocation:
                listener.onButtonReloadLocationClick();
                break;
            case R.id.btnGo:
                listener.onButtonGoClick();
                break;
            case R.id.btnCloseNavi:
                listener.onButtonCloseNavi();
                break;
        }
    }
}
