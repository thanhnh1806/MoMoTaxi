package bhtech.com.cabbydriver.ChargeCustomer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ContantValuesObject;

public class ChargeCustomerView extends Fragment implements View.OnClickListener {
    ChargeCustomerInterface.Listener listener;
    ChargeCustomerInterface.Datasource datasource;

    public void setListener(ChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ChargeCustomerInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private TextView tvDate, tvTime, tvCustomerName, tvStartAddress, tvStopAddress,
            tvMinutes, tvKm, btnNext;
    private FrameLayout btnDestinationLocation;
    private EditText etNonAppUserExtraPrice, etNonAppUserCustomerEmail, etExtraPrice,
            etCharge, etChargeNonApp, etCustomerName;
    private LinearLayout btnCreditCard, btnCash, layoutUser, layoutNonApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_charge_customer_view, container, false);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTime = (TextView) v.findViewById(R.id.tvTime);
        tvCustomerName = (TextView) v.findViewById(R.id.tvCustomerName);
        etCustomerName = (EditText) v.findViewById(R.id.etCustomerName);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvStopAddress = (TextView) v.findViewById(R.id.tvStopAddress);
        tvMinutes = (TextView) v.findViewById(R.id.tvMinutes);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        btnNext = (TextView) v.findViewById(R.id.btnNext);

        etChargeNonApp = (EditText) v.findViewById(R.id.etChargeNonApp);
        etCharge = (EditText) v.findViewById(R.id.etCharge);
        etNonAppUserExtraPrice = (EditText) v.findViewById(R.id.etNonAppUserExtraPrice);
        etNonAppUserCustomerEmail = (EditText) v.findViewById(R.id.etNonAppUserCustomerEmail);
        etExtraPrice = (EditText) v.findViewById(R.id.etExtraPrice);

        btnDestinationLocation = (FrameLayout) v.findViewById(R.id.btnDestinationLocation);
        btnCreditCard = (LinearLayout) v.findViewById(R.id.btnCreditCard);
        btnCash = (LinearLayout) v.findViewById(R.id.btnCash);
        layoutUser = (LinearLayout) v.findViewById(R.id.layoutUser);
        layoutNonApp = (LinearLayout) v.findViewById(R.id.layoutNonApp);

        btnDestinationLocation.setOnClickListener(this);
        btnCreditCard.setOnClickListener(this);
        btnCash.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        tvTime.setText(datasource.getCurrentTime());
        tvDate.setText(datasource.getCurrentDate());
        tvCustomerName.setText(datasource.getCustomerName());
        tvStartAddress.setText(datasource.getStartAddress());
        tvStopAddress.setText(datasource.getStopAddress());
        tvMinutes.setText(datasource.getMinutes() + " " + getString(R.string.mins));
        tvKm.setText(datasource.getDistance() + " " + getString(R.string.km));
    }

    public void reloadNonAppUserView(boolean b) {
        if (b) {
            etCustomerName.setVisibility(View.VISIBLE);
            tvCustomerName.setVisibility(View.GONE);
            btnDestinationLocation.setVisibility(View.VISIBLE);
            layoutUser.setVisibility(View.GONE);
            layoutNonApp.setVisibility(View.VISIBLE);
        } else {
            etCustomerName.setVisibility(View.GONE);
            tvCustomerName.setVisibility(View.VISIBLE);
            btnDestinationLocation.setVisibility(View.GONE);
            layoutUser.setVisibility(View.VISIBLE);
            layoutNonApp.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        datasource.setCharge(etCharge.getText().toString());
        datasource.setNonAppUserCharge(etChargeNonApp.getText().toString());
        datasource.setExtraPrice(etExtraPrice.getText().toString());
        datasource.setNonAppUserExtraPrice(etNonAppUserExtraPrice.getText().toString());
        datasource.setNonAppUserCustomerEmail(etNonAppUserCustomerEmail.getText().toString());

        switch (v.getId()) {
            case R.id.btnDestinationLocation:
                listener.destinationLocationClick();
                break;
            case R.id.btnCreditCard:
                btnCreditCard.setBackgroundColor(getResources().getColor(R.color.orangeDark));
                btnCash.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                datasource.setPayMode(ContantValuesObject.PayByCreditCard);
                break;
            case R.id.btnCash:
                btnCreditCard.setBackgroundColor(getResources().getColor(R.color.orangeLight));
                btnCash.setBackgroundColor(getResources().getColor(R.color.orangeDark));
                datasource.setPayMode(ContantValuesObject.PayByCash);
                break;
            case R.id.btnNext:
                listener.updateCharge();
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