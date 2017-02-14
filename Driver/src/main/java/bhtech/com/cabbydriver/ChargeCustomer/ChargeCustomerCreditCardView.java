package bhtech.com.cabbydriver.ChargeCustomer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bhtech.com.cabbydriver.R;

public class ChargeCustomerCreditCardView extends Fragment {
    ChargeCustomerInterface.Listener listener;

    public void setListener(ChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_charge_customer_credit_card_view, container, false);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.paymentByCreditCardButtonCancelClick();
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
