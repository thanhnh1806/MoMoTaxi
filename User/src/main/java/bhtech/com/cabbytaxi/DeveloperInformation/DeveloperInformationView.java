package bhtech.com.cabbytaxi.DeveloperInformation;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import bhtech.com.cabbytaxi.R;

public class DeveloperInformationView extends Fragment {
    WebView wvDevInfo;
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_developer_information_view, container, false);
        wvDevInfo = (WebView) v.findViewById(R.id.wvDevInfo);

        WebSettings webSettings = wvDevInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvDevInfo.setWebViewClient(new WebViewClient() {

        });

        wvDevInfo.loadUrl(container.getResources().getString(R.string.dev_info_link));
        return v;
    }
}
