package bhtech.com.cabbytaxi.CreditCardSetting;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;

public class CreditCardSettingView extends Fragment {
    private CreditCardInterface.Listener listener;

    public void setListener(CreditCardInterface.Listener listener) {
        this.listener = listener;
    }

    private TextView btnOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credit_card_setting_view, container, false);
        btnOK = (TextView) v.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonOkClick();
            }
        });
        return v;
    }
}
