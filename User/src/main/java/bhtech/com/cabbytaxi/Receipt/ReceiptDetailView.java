package bhtech.com.cabbytaxi.Receipt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;

public class ReceiptDetailView extends Fragment implements View.OnClickListener {

    private ReceiptInterface.Listener listener;
    private ReceiptInterface.Datasource datasource;

    public void setListener(ReceiptInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ReceiptInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private TextView tvDate, tvHours, tvPhoneNumber, tvAddressCompany, tvUserName, tvDriverName,
            tvCarNumber, tvStartAddress, tvEndAddress, tvMinutes, tvKm, tvPaymentType, tvCharge;
    private Button btnBack, btnDelete, btnMail, btnPrint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipt_detail, container, false);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvHours = (TextView) v.findViewById(R.id.tvHours);
        tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhoneNumber);
        tvAddressCompany = (TextView) v.findViewById(R.id.tvAddressCompany);
        tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvCarNumber = (TextView) v.findViewById(R.id.tvCarNumber);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvEndAddress = (TextView) v.findViewById(R.id.tvEndAddress);
        tvMinutes = (TextView) v.findViewById(R.id.tvMinutes);
        tvKm = (TextView) v.findViewById(R.id.tvKm);
        tvPaymentType = (TextView) v.findViewById(R.id.tvPaymentType);
        tvCharge = (TextView) v.findViewById(R.id.tvCharge);
        tvCharge.setSelected(true);
        btnBack = (Button) v.findViewById(R.id.btnBack);
        btnDelete = (Button) v.findViewById(R.id.btnDelete);
        btnMail = (Button) v.findViewById(R.id.btnMail);
        btnPrint = (Button) v.findViewById(R.id.btnPrint);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnMail.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        return v;
    }


    public void reloadView() {
        tvDate.setText(datasource.getDate());
        tvHours.setText(datasource.getHours());
        tvPhoneNumber.setText(datasource.getCompanyPhoneNumber());
        tvAddressCompany.setText(datasource.getCompanyAddress());
        tvUserName.setText(datasource.getCompanyManager());
        tvDriverName.setText(datasource.getDriverName());
        tvCarNumber.setText(datasource.getCarNumber());
        tvStartAddress.setText(datasource.getStartAddress());
        tvEndAddress.setText(datasource.getEndAddress());
        tvMinutes.setText(datasource.getMinutes());
        tvKm.setText(datasource.getKm());
        tvPaymentType.setText(datasource.getPayMode());
        tvCharge.setText(datasource.getCharge());
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
                listener.onButtonBackClick();
                break;
            case R.id.btnDelete:
                listener.onButtonDeleteClick();
                break;
            case R.id.btnMail:
                listener.onButtonMailClick();
                break;
            case R.id.btnPrint:
                listener.onButtonPrintClick();
                break;
        }
    }
}
