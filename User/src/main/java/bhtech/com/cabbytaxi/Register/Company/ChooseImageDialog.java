package bhtech.com.cabbytaxi.Register.Company;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.Personal.ProfileSettingInterface;

/**
 * Created by Thanh on 16/06/2016.
 */
public class ChooseImageDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private CompanySettingInterface.Listener listener;

    public ChooseImageDialog(Context context, CompanySettingInterface.Listener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    private TextView tvCancel, tvTakePicktureOnCamera, tvChoosePicktureFromGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_image);
        setTitle(context.getString(R.string.choose_your_image));

        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvTakePicktureOnCamera = (TextView) findViewById(R.id.tvTakePicktureOnCamera);
        tvChoosePicktureFromGallery = (TextView) findViewById(R.id.tvChoosePicktureFromGallery);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvCancel.setOnClickListener(this);
        tvTakePicktureOnCamera.setOnClickListener(this);
        tvChoosePicktureFromGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;

            case R.id.tvTakePicktureOnCamera:
                dismiss();
                listener.onDialogButtonTakePicktureOnCameraClick();
                break;

            case R.id.tvChoosePicktureFromGallery:
                dismiss();
                listener.onDialogButtonChoosePicktureFromGallery();
                break;
        }
    }
}