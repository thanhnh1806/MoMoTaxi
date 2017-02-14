package bhtech.com.cabbytaxi.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.DriverChargeCustomerInterface;

public class PaymentView extends Fragment {
    private DriverChargeCustomerInterface.Listener listener;
    private DriverChargeCustomerInterface.DataSource dataSource;

    public void setListener(DriverChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(DriverChargeCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Button btnCancel;
    Button btnuser_pay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_view, container, false);
        btnCancel = (Button) v.findViewById(R.id.btn_cancel_payment);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonCancelPaymentViaCreditCardClick();
            }
        });

        btnuser_pay = (Button) v.findViewById(R.id.btnPay);
        btnuser_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonOkClick();
            }
        });
        return v;
    }

}
