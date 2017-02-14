package bhtech.com.cabbydriver.TermsOfUse;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.TermsOfUse.TermOfUseInterface;


/**
 * Created by thanh_nguyen on 25/03/2016.
 */
public class TermOfUseView extends Fragment {
    private TermOfUseInterface listener;

    public void setListener(TermOfUseInterface listener) {
        this.listener = listener;
    }

    public TermOfUseView() {
    }

    private TextView btnOk;
    private WebView wvTermOfUse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_term_of_user, container, false);
        btnOk = (TextView) v.findViewById(R.id.btnOk);

        wvTermOfUse = (WebView) v.findViewById(R.id.wvTermOfUse);
        WebSettings webSettings = wvTermOfUse.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wvTermOfUse.loadUrl("file:///android_asset/terms_of_use_driver.html");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnButtonOkTermOfUseClick();
            }
        });
        return v;
    }
}
