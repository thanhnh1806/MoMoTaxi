package bhtech.com.cabbydriver.ChooseCar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class ChooseCarDialogError extends Dialog {
    private Context mContext;
    private ChooseCarInterface.Delegate _delegate;
    TextView txt_carnumber;

    public ChooseCarDialogError(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setDelegate(ChooseCarInterface.Delegate delegate) {
        this._delegate = delegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.black_tran);
        setContentView(R.layout.dialog_error_choose_car);
        Button btnOK = (Button) findViewById(R.id.btn_choosecar_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.closePopup();
            }
        });
        txt_carnumber = (TextView) findViewById(R.id.tv_dialog_carnumber);

    }

    @Override
    protected void onStart() {
        super.onStart();
        _delegate.onDialogViewFinish();
    }

    public void setErrorCarNumber(String carNumber) {
        txt_carnumber.setText(carNumber);
    }

}
