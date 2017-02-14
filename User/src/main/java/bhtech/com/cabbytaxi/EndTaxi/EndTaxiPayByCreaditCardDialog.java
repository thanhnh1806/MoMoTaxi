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
public class EndTaxiPayByCreaditCardDialog extends Dialog {
    private OnPayByCreditCardDialogListener mListener;
    private Context context;
    private TextView tvUserId, tvPrice;
    private ImageView ivOK;

    public EndTaxiPayByCreaditCardDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_endtaxi_pay_by_creaditcard);
        mListener = (OnPayByCreditCardDialogListener) context;
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        ivOK = (ImageView) findViewById(R.id.ivOK);

        ivOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPayByCreditCardButtonOkClick();
            }
        });
    }

    public void updateUserId(String userId) {
        tvUserId.setText(userId);
    }

    DecimalFormat df = new DecimalFormat("#.00");

    public void updatePrice(float price) {
        tvPrice.setText(df.format(price));
    }

    public interface OnPayByCreditCardDialogListener {
        void onPayByCreditCardButtonOkClick();
    }
}
