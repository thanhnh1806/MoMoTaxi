package bhtech.com.cabbytaxi.TermOfUse;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.TermOfUse.TermOfUseInterface;

/**
 * Created by thanh_nguyen on 25/03/2016.
 */
public class TermOfUseDialog extends Dialog {
    private TermOfUseInterface listener;

    public void setListener(TermOfUseInterface listener) {
        this.listener = listener;
    }

    public TermOfUseDialog(Context context, int theme) {
        super(context, theme);
    }

    private TextView btnOk;
    private WebView wvTermOfUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_term_of_user);
        btnOk = (TextView) findViewById(R.id.btnOk);

        wvTermOfUse = (WebView) findViewById(R.id.wvTermOfUse);
        WebSettings webSettings = wvTermOfUse.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wvTermOfUse.loadUrl("file:///android_asset/terms_of_use_passenger.html");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnButtonOkTermOfUseClick();
            }
        });
    }
}
