package bhtech.com.cabbydriver.ChargeCustomer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import bhtech.com.cabbydriver.R;

/**
 * Created by thanh_nguyen on 26/04/2016.
 */
public class ChargeCustomerCreditCardSuccessDialog extends Dialog {
    ChargeCustomerInterface.Listener listener;

    public ChargeCustomerCreditCardSuccessDialog(Context context, ChargeCustomerInterface.Listener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.color.black_tran);
        setContentView(R.layout.dialog_payment_success);
        FrameLayout btnOk = (FrameLayout) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.creditCardPaymentSuccessful();
            }
        });
    }
}
