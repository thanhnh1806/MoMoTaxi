package bhtech.com.cabbydriver.DrivingToDestination;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class DrivingToDestinationWithoutNaviView extends Fragment {
    DrivingToDestinationInterface.Listener listener;
    DrivingToDestinationInterface.Datasource datasource;

    public void setListener(DrivingToDestinationInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(DrivingToDestinationInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    TextView tvCustomerName, tvAddress;
    FrameLayout btnShowNavi, btnFinish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driving_to_destination_without_navi_view, container, false);
        tvCustomerName = (TextView) v.findViewById(R.id.tvCustomerName);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        btnShowNavi = (FrameLayout) v.findViewById(R.id.btnShowNavi);
        btnFinish = (FrameLayout) v.findViewById(R.id.btnFinish);

        btnShowNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showNaviClick();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonFinishClick();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCustomerName.setText(datasource.getCustomerName());
        tvAddress.setText(datasource.getEndAddress());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
