package bhtech.com.cabbytaxi.RateDriver;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.RateDriver.RateTaxiInterface;

/**
 * Created by thanh_nguyen on 20/01/2016.
 */
public class RateTaxiShareFacebookDialog extends Dialog implements View.OnClickListener {

    private RateTaxiInterface.Delegate delegate;
    private RateTaxiInterface.Datasource datasource;

    private TextView tvUserName, btnCancel, btnPost;
    private EditText etComment;
    private FrameLayout btnDownload;

    public void setDelegate(RateTaxiInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    public void setDatasource(RateTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public RateTaxiShareFacebookDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_ratetaxi_share_facebook);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        btnCancel = (TextView) findViewById(R.id.btnCancel);
        btnPost = (TextView) findViewById(R.id.btnPost);
        etComment = (EditText) findViewById(R.id.etComment);
        btnDownload = (FrameLayout) findViewById(R.id.btnDownload);

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                delegate.onUserAddComment(String.valueOf(s.hashCode()));
            }
        });
        btnDownload.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnPost.setOnClickListener(this);
    }

    public void reloadView() {
        tvUserName.setText(datasource.getUserFacebookName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        delegate.onShareFacebookDialogCreateViewFinish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                delegate.onButtonCancelShareFacebookClick();
                break;
            case R.id.btnPost:
                delegate.onButtonPostClick();
                break;
            case R.id.btnDownload:
                delegate.onButtonDownloadAppClick();
                break;
        }
    }
}
