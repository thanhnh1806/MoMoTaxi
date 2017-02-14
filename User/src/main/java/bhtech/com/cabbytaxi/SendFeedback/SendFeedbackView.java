package bhtech.com.cabbytaxi.SendFeedback;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SendFeedback.SendFeedbackInterface;

public class SendFeedbackView extends Fragment implements View.OnClickListener {
    private SendFeedbackInterface.Listener listener;
    private SendFeedbackInterface.Database database;

    public void setListener(SendFeedbackInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(SendFeedbackInterface.Database database) {
        this.database = database;
    }

    private View btnFeedbackPositive, btnFeedbackNegative;
    private EditText etWriteFeedback;
    private TextView btnDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_feedback_view, container, false);
        btnFeedbackPositive = v.findViewById(R.id.btnFeedbackPositive);
        btnFeedbackNegative = v.findViewById(R.id.btnFeedbackNegative);
        etWriteFeedback = (EditText) v.findViewById(R.id.etWriteFeedback);
        btnDone = (TextView) v.findViewById(R.id.btnDone);

        btnFeedbackPositive.setOnClickListener(this);
        btnFeedbackNegative.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFeedbackPositive:
                listener.onFeedbackPositiveClick();
                break;
            case R.id.btnFeedbackNegative:
                listener.onFeedbackNegativeClick();
                break;
            case R.id.btnDone:
                database.setFeedback(etWriteFeedback.getText().toString());
                listener.onButtonDoneSendFeedbackClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }
}
