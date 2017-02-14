package bhtech.com.cabbydriver.EnterPinCode;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import bhtech.com.cabbydriver.R;

/**
 * Created by thanh_nguyen02 on 21/01/2016.
 */
public class EnterWrongCodeDialog extends Dialog {

    private EnterCodeInterface.Listener listener;

    public void setListener(EnterCodeInterface.Listener listener) {
        this.listener = listener;
    }

    private FrameLayout btnClose;

    public EnterWrongCodeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_driver_enter_wrong_code);
        btnClose = (FrameLayout) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnButtonClosePinCodeWrongDialogClick();
            }
        });
    }
}
