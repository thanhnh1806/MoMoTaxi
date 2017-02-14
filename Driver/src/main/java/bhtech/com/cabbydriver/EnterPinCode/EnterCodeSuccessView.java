package bhtech.com.cabbydriver.EnterPinCode;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.PhoneObject;

public class EnterCodeSuccessView extends Fragment {

    private EnterCodeInterface.Listener listener;

    public void setListener(EnterCodeInterface.Listener listener) {
        this.listener = listener;
    }

    public Button btnOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_enter_code_success_view, container, false);
        btnOk = (Button) v.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnButtonOkPinCodeSuccessClick();
            }
        });

        PhoneObject.hiddenSofwareKeyboard(getActivity(), btnOk);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
