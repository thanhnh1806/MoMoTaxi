package bhtech.com.cabbytaxi.EndTaxi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import bhtech.com.cabbytaxi.R;

/**
 * Created by thanh_nguyen on 15/01/2016.
 */
public class EndTaxiPayByCashDialog extends Dialog {
    private Context context;
    private OnPayByCashDialogListener mListener;
    private TextView tvMoney;
    private ImageView ivOk;

    public EndTaxiPayByCashDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_endtaxi_pay_by_cash);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mListener = (OnPayByCashDialogListener) context;
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        ivOk = (ImageView) findViewById(R.id.ivOk);
        tvMoney.setSelected(true);
        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPayByCashButtonOkClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListener.onPayByCashDialogCreateViewFinish();
    }

    DecimalFormat df = new DecimalFormat("#.00");

    public void updateMoney(float money) {
        tvMoney.setText("$ " + df.format(money));
    }

    public interface OnPayByCashDialogListener {
        void onPayByCashDialogCreateViewFinish();

        void onPayByCashButtonOkClick();
    }
}
