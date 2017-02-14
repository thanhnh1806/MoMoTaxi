package bhtech.com.cabbydriver.ThankYouDriver;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import bhtech.com.cabbydriver.R;

/**
 * Created by duongpv on 4/7/16.
 */
public class ThankYouDriverView extends Fragment {
    FrameLayout frameThankYou;
    Context context;

    Delegate delegate;

    public void setDelegate (Delegate delegate) {
        this.delegate = delegate;
    }

    public void setContext (Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish_thank_you_driver, container, false);
        frameThankYou = (FrameLayout)v.findViewById(R.id.frameThankYou);
        frameThankYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.frameThankYouOnClick();
            }
        });
        return v;
    }

    public interface Delegate {
        void frameThankYouOnClick ();
    }
}
