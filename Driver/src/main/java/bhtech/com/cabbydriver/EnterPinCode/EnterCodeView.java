package bhtech.com.cabbydriver.EnterPinCode;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class EnterCodeView extends Fragment {
    private EnterCodeInterface.Listener listener;
    private EnterCodeInterface.Datasource datasource;

    public void setListener(EnterCodeInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(EnterCodeInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public EditText etEnterCode;
    private LinearLayout cbRememberMe;
    private ImageView ivUnCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_enter_code_view, container, false);
        etEnterCode = (EditText) v.findViewById(R.id.etEnterCode);
        cbRememberMe = (LinearLayout) v.findViewById(R.id.cbRememberMe);
        ivUnCheckBox = (ImageView) v.findViewById(R.id.ivUnCheckBox);

        cbRememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivUnCheckBox.isShown()) {
                    ivUnCheckBox.setVisibility(View.GONE);
                } else {
                    ivUnCheckBox.setVisibility(View.VISIBLE);
                }
                datasource.setRememberMe(ivUnCheckBox.isShown());
            }
        });

        etEnterCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    datasource.setPinCode(etEnterCode.getText().toString());
                    listener.checkPinCode();
                    return true;
                }
                return false;
            }
        });

        etEnterCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                datasource.setPinCode(etEnterCode.getText().toString());
                listener.checkPinCode();
                return true;
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
