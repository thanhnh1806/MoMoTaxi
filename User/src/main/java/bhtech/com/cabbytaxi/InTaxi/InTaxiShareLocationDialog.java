package bhtech.com.cabbytaxi.InTaxi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.InTaxi.OnInTaxiDialogListerner;

public class InTaxiShareLocationDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private OnInTaxiDialogListerner mListener;
    private TextView tvTittle;
    private FrameLayout btnWhatsapp, btnMessaging, btnFacebook, btnTwitter, btnGooglePlus, btnGmail;
    private FrameLayout btnCancel;

    public InTaxiShareLocationDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_share_taxi_location);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        mListener = (OnInTaxiDialogListerner) context;
        btnWhatsapp = (FrameLayout) findViewById(R.id.btnWhatsapp);
        btnMessaging = (FrameLayout) findViewById(R.id.btnMessaging);
        btnFacebook = (FrameLayout) findViewById(R.id.btnFacebook);
        btnTwitter = (FrameLayout) findViewById(R.id.btnTwitter);
        btnGooglePlus = (FrameLayout) findViewById(R.id.btnGooglePlus);
        btnGmail = (FrameLayout) findViewById(R.id.btnGmail);
        btnCancel = (FrameLayout) findViewById(R.id.btnCancel);

        btnWhatsapp.setOnClickListener(this);
        btnMessaging.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnGooglePlus.setOnClickListener(this);
        btnGmail.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWhatsapp:
                if (mListener != null) {
                    mListener.OnClickShareWhatsApp();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnMessaging:
                if (mListener != null) {
                    mListener.OnClickShareMessage();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnFacebook:
                if (mListener != null) {
                    mListener.OnClickShareFacebook();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnTwitter:
                if (mListener != null) {
                    mListener.OnClickShareTwitter();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnGooglePlus:
                if (mListener != null) {
                    mListener.OnClickShareGooglePlus();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnGmail:
                if (mListener != null) {
                    mListener.OnClickShareEmail();
                } else {
                    //DO nothing
                }
                break;
            case R.id.btnCancel:
                if (mListener != null) {
                    mListener.OnClickShareCancel();
                } else {
                    //DO nothing
                }
                break;
        }
    }
}
