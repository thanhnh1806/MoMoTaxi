package bhtech.com.cabbytaxi.SendFeedback;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SendFeedback.SendFeedbackInterface;

public class SendFeedbackNegativeView extends Fragment implements View.OnClickListener {
    private SendFeedbackInterface.Listener listener;
    private SendFeedbackInterface.Database database;

    public void setListener(SendFeedbackInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(SendFeedbackInterface.Database database) {
        this.database = database;
    }

    private ImageView ivUncheck1, ivCheck1, ivUncheck2, ivCheck2, ivUncheck3, ivCheck3,
            ivUncheck4, ivCheck4, ivUncheck5, ivCheck5;
    private EditText etWriteFeedback;
    private TextView btnDone, tvFeedback1, tvFeedback2, tvFeedback3, tvFeedback4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_feedback_negative_view, container, false);
        ivCheck1 = (ImageView) v.findViewById(R.id.ivCheck1);
        ivCheck2 = (ImageView) v.findViewById(R.id.ivCheck2);
        ivCheck3 = (ImageView) v.findViewById(R.id.ivCheck3);
        ivCheck4 = (ImageView) v.findViewById(R.id.ivCheck4);
        ivCheck5 = (ImageView) v.findViewById(R.id.ivCheck5);

        ivUncheck1 = (ImageView) v.findViewById(R.id.ivUncheck1);
        ivUncheck2 = (ImageView) v.findViewById(R.id.ivUncheck2);
        ivUncheck3 = (ImageView) v.findViewById(R.id.ivUncheck3);
        ivUncheck4 = (ImageView) v.findViewById(R.id.ivUncheck4);
        ivUncheck5 = (ImageView) v.findViewById(R.id.ivUncheck5);

        etWriteFeedback = (EditText) v.findViewById(R.id.etWriteFeedback);
        btnDone = (TextView) v.findViewById(R.id.btnDone);
        tvFeedback1 = (TextView) v.findViewById(R.id.tvFeedback1);
        tvFeedback2 = (TextView) v.findViewById(R.id.tvFeedback2);
        tvFeedback3 = (TextView) v.findViewById(R.id.tvFeedback3);
        tvFeedback4 = (TextView) v.findViewById(R.id.tvFeedback4);

        ivCheck1.setOnClickListener(this);
        ivCheck2.setOnClickListener(this);
        ivCheck3.setOnClickListener(this);
        ivCheck4.setOnClickListener(this);
        ivCheck5.setOnClickListener(this);

        ivUncheck1.setOnClickListener(this);
        ivUncheck2.setOnClickListener(this);
        ivUncheck3.setOnClickListener(this);
        ivUncheck4.setOnClickListener(this);
        ivUncheck5.setOnClickListener(this);

        btnDone.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onFeedbackNegativeCreateViewFinish();
    }

    public void reloadView() {
        tvFeedback1.setText(database.getFeedbackNegative1());
        tvFeedback2.setText(database.getFeedbackNegative2());
        tvFeedback3.setText(database.getFeedbackNegative3());
        tvFeedback4.setText(database.getFeedbackNegative4());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCheck1:
                ivCheck1.setVisibility(View.GONE);
                ivUncheck1.setVisibility(View.VISIBLE);
                database.setFeedbackNegativeCheckbox1(false);
                break;
            case R.id.ivCheck2:
                ivCheck2.setVisibility(View.GONE);
                ivUncheck2.setVisibility(View.VISIBLE);
                database.setFeedbackNegativeCheckbox2(false);
                break;
            case R.id.ivCheck3:
                ivCheck3.setVisibility(View.GONE);
                ivUncheck3.setVisibility(View.VISIBLE);
                database.setFeedbackNegativeCheckbox3(false);
                break;
            case R.id.ivCheck4:
                ivCheck4.setVisibility(View.GONE);
                ivUncheck4.setVisibility(View.VISIBLE);
                database.setFeedbackNegativeCheckbox4(false);
                break;
            case R.id.ivCheck5:
                ivCheck5.setVisibility(View.GONE);
                ivUncheck5.setVisibility(View.VISIBLE);
                database.setFeedbackNegativeCheckbox5(false);
                break;
            case R.id.ivUncheck1:
                ivCheck1.setVisibility(View.VISIBLE);
                ivUncheck1.setVisibility(View.GONE);
                database.setFeedbackNegativeCheckbox1(true);
                break;
            case R.id.ivUncheck2:
                ivCheck2.setVisibility(View.VISIBLE);
                ivUncheck2.setVisibility(View.GONE);
                database.setFeedbackNegativeCheckbox2(true);
                break;
            case R.id.ivUncheck3:
                ivCheck3.setVisibility(View.VISIBLE);
                ivUncheck3.setVisibility(View.GONE);
                database.setFeedbackNegativeCheckbox3(true);
                break;
            case R.id.ivUncheck4:
                ivCheck4.setVisibility(View.VISIBLE);
                ivUncheck4.setVisibility(View.GONE);
                database.setFeedbackNegativeCheckbox4(true);
                break;
            case R.id.ivUncheck5:
                ivCheck5.setVisibility(View.VISIBLE);
                ivUncheck5.setVisibility(View.GONE);
                database.setFeedbackNegativeCheckbox5(true);
                break;
            case R.id.btnDone:
                database.setFeedbackNegative(etWriteFeedback.getText().toString());
                listener.onButtonDoneSendFeedbackNegativeClick();
                break;
        }
    }
}
