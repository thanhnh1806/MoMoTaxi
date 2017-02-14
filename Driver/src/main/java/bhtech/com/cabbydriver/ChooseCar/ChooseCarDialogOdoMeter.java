package bhtech.com.cabbydriver.ChooseCar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import bhtech.com.cabbydriver.R;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class ChooseCarDialogOdoMeter extends Dialog {
    private Context mContext;
    private ChooseCarInterface.Delegate _delegate;

    public ChooseCarDialogOdoMeter(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setDelegate(ChooseCarInterface.Delegate delegate) {
        this._delegate = delegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.color.black_tran);
        setContentView(R.layout.dialog_odometer);
        Button btnOK = (Button) findViewById(R.id.btn_odometer_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.closePopup();
            }
        });
    }
}
