package bhtech.com.cabbydriver.ChargeCustomer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class ChargeCustomerCashView extends Fragment {
    ChargeCustomerInterface.Listener listener;

    public void setListener(ChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    TextView btnReceiptOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_charge_customer_cash_view, container, false);
        btnReceiptOk = (TextView) v.findViewById(R.id.btnReceiptOk);
        btnReceiptOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.paymentByCashButtonOkClick();
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
