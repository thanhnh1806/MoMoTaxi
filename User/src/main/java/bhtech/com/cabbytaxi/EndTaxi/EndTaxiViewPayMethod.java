package bhtech.com.cabbytaxi.EndTaxi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.StringObject;

public class EndTaxiViewPayMethod extends Fragment {
    private EndTaxiInterface.Listener listener;
    private EndTaxiInterface.Datasource datasource;

    public void setListener(EndTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(EndTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private TextView tvDriverName, tvNumber, tvStartAddress, tvStopAddress, tvDate, tvTime,
            tvMinutes, tvKm, tvHighWayPrice, tvOK;
    private TextView tvCharge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end_taxi_pay_method_view, container, false);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvNumber = (TextView) v.findViewById(R.id.tvNumber);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);
        tvMinutes = (TextView) v.findViewById(R.id.tvMinutes);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        tvCharge = (TextView) v.findViewById(R.id.tvCharge);
        tvHighWayPrice = (TextView) v.findViewById(R.id.tvHighWayPrice);
        tvOK = (TextView) v.findViewById(R.id.tvOK);

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonOkClick();
            }
        });
        tvCharge.setSelected(true);
        return v;
    }

    public void reloadView() {
        tvDate.setText(datasource.getDate());
        tvTime.setText(datasource.getTime());
        tvDriverName.setText(datasource.getDriverName());
        tvNumber.setText(datasource.getCarNumber());
        tvStartAddress.setText(datasource.getStartAddress());
        tvStopAddress.setText(datasource.getStopAddress());
        tvMinutes.setText(StringObject.getDecimalFormat(1).format(datasource.getMinutes(getActivity())));
        tvKm.setText(StringObject.getDecimalFormat(1).format(datasource.getKm(getActivity())));
        tvCharge.setText(StringObject.getDecimalFormat(2).format(datasource.getCharge(getActivity())));
        tvHighWayPrice.setText(StringObject.getDecimalFormat(2).format(datasource.getExtraPrice(getActivity())));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
