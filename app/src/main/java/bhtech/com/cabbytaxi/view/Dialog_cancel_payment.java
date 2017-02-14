package bhtech.com.cabbytaxi.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.DriverChargeCustomerInterface;

public class Dialog_cancel_payment extends Dialog {

    private DriverChargeCustomerInterface.Listener listener;
    private DriverChargeCustomerInterface.DataSource dataSource;

    public void setListener(DriverChargeCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDataSource(DriverChargeCustomerInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Context mContext;
    Button btnYes, btnNo;

    public Dialog_cancel_payment(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        setContentView(R.layout.dialog_cancel_payment);
        btnYes = (Button) findViewById(R.id.btn_OK_pay_ment);
        btnNo = (Button) findViewById(R.id.btn_NO_pay_ment);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonYesClick();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonNoClick();
            }
        });
    }
}
