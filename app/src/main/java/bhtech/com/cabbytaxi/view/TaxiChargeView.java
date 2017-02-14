package bhtech.com.cabbytaxi.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.DriverChargeCustomerInterface;

public class TaxiChargeView extends Fragment implements View.OnClickListener {
    private DriverChargeCustomerInterface.Listener listener;
    private DriverChargeCustomerInterface.DataSource dataSource;

    public void setListener(DriverChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(DriverChargeCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TaxiChargeView(){

    }

    public TextView txtCustomerName, txtStartAdress, txtStopAdress, txtMinutes, txtKilomet, txtCharge , txtTime , txtDate;
    public LinearLayout btnPayCreditCard, btnPayCard;
    public Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_taxi_charge_view, container, false);
        txtCustomerName = (TextView) v.findViewById(R.id.tvCustomerName);
        txtStartAdress = (TextView) v.findViewById(R.id.tvStartAddress);
        txtStopAdress = (TextView) v.findViewById(R.id.tvStopAddress);
        txtMinutes = (TextView) v.findViewById(R.id.tvMinutes);
        txtKilomet = (TextView) v.findViewById(R.id.tvKm);
        txtCharge = (TextView) v.findViewById(R.id.tvCharge);
        txtTime = (TextView) v.findViewById(R.id.tvTime);
        txtDate = (TextView) v.findViewById(R.id.tvDate);
        btnNext = (Button) v.findViewById(R.id.btnNext);
        btnPayCreditCard = (LinearLayout) v.findViewById(R.id.btnCreditCard);
        btnPayCard = (LinearLayout) v.findViewById(R.id.btnCash);

        btnPayCard.setOnClickListener(this);
        btnPayCreditCard.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onTaxiChargeCreateViewFinish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCash:
                listener.onButtonPayByCashClick();
                break;
            case  R.id.btnCreditCard:
                listener.onButtonPayByCreditCardClick();
                break;
            case  R.id.btnNext:
                listener.onButtonNextClick();

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        dataSource = null;
    }

    public void updateDate(String date){
        txtDate.setText(date);
    }

    public void updateTime(String time){
        txtTime.setText(time);
    }

    public void updateCustomerName(String name){
        txtCustomerName.setText(name);
    }

    public void upadateCharge(String charge){
        txtCharge.setText(charge);
    }

    public void updateMinues(String minues){
        txtMinutes.setText(minues);
    }

    public void updateKm(String km){
        txtKilomet.setText(km);
    }

    public void updateStart(String start){
        txtStartAdress.setText(start);
    }

    public void updateStop(String stop){
        txtStopAdress.setText(stop);
    }

}
