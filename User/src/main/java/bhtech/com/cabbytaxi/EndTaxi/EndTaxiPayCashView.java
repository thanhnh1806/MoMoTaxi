package bhtech.com.cabbytaxi.EndTaxi;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;

public class EndTaxiPayCashView extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private TextView tvDate, tvTime, tvCityCab, tvIssued, tvName, tvDriverName, tvNumber;
    private TextView tvStartAddress, tvStopAddress, tvMinutes, tvKm, tvCharge, btnCancel, btnPayByCard;

    public EndTaxiPayCashView() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end_taxi_pay_cash_view, container, false);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvCityCab = (TextView) v.findViewById(R.id.tvCityCab);
        tvIssued = (TextView) v.findViewById(R.id.tvIssued);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvNumber = (TextView) v.findViewById(R.id.tvNumber);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);
        tvMinutes = (TextView) v.findViewById(R.id.tvMinutes);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        tvCharge = (TextView) v.findViewById(R.id.tvCharge);
        btnCancel = (TextView) v.findViewById(R.id.btnCancel);
        btnPayByCard = (TextView) v.findViewById(R.id.btnPayByCard);

        btnCancel.setOnClickListener(this);
        btnPayByCard.setOnClickListener(this);
        return v;
    }

    public void setDate(String date) {
        tvDate.setText(date);
    }

    public void setTime(String time) {
        tvTime.setText(time);
    }

    public void setCityCab(String cityCab) {
        tvCityCab.setText(cityCab);
    }

    public void setIssued(String issued) {
        tvIssued.setText(issued);
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setDriverName(String driverName) {
        tvDriverName.setText(driverName);
    }

    public void setCarNumber(String carNumber) {
        tvNumber.setText(carNumber);
    }

    public void setStartAddress(String startAddress) {
        tvStartAddress.setText(startAddress);
    }

    public void setStopAddress(String stopAddress) {
        tvStopAddress.setText(stopAddress);
    }

    public void setMinutes(String minutes) {
        tvMinutes.setText(minutes);
    }

    public void setKm(String km) {
        tvKm.setText(km);
    }

    public void setCharge(String charge) {
        tvCharge.setText(charge);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.OnPayCashCreateViewFinish();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                mListener.OnCancelPayByCashClick();
                break;
            case R.id.btnPayByCard:
                mListener.OnPayByCardClick();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void OnPayCashCreateViewFinish();

        void OnCancelPayByCashClick();

        void OnPayByCardClick();
    }
}
