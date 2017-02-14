package bhtech.com.cabbytaxi.SendFeedback;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;

public class SendFeedbackController extends SlidingMenuController implements SendFeedbackInterface.Listener {
    private Context context;
    private SendFeedbackModel model;
    private SendFeedbackView feedbackView;
    private SendFeedbackNegativeView negativeView;
    private SendFeedbackPositiveView positiveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getLayoutInflater().inflate(R.layout.activity_send_feedback_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.send_feedback));

        model = new SendFeedbackModel();
        feedbackView = new SendFeedbackView();
        negativeView = new SendFeedbackNegativeView();
        positiveView = new SendFeedbackPositiveView();

        feedbackView.setListener(this);
        feedbackView.setDatabase(model);

        negativeView.setListener(this);
        negativeView.setDatabase(model);

        positiveView.setListener(this);
        positiveView.setDatabase(model);

        if (savedInstanceState == null) {
            addFragment(R.id.container, feedbackView);
            addFragment(R.id.container, negativeView);
            addFragment(R.id.container, positiveView);
            hideAllFragment();
            showFragment(feedbackView);
        }
    }

    private void hideAllFragment() {
        hideFragment(feedbackView);
        hideFragment(positiveView);
        hideFragment(negativeView);
    }

    @Override
    public void onFeedbackPositiveClick() {
        hideAllFragment();
        showFragment(positiveView);
    }

    @Override
    public void onFeedbackNegativeClick() {
        hideAllFragment();
        showFragment(negativeView);
    }

    @Override
    public void onButtonDoneSendFeedbackClick() {

    }

    @Override
    public void onButtonDoneSendFeedbackNegativeClick() {
        model.sendNegativeFeedback(context, new SendFeedbackModel.OnSendNegativeFeedback() {
            @Override
            public void Success() {
                Toast.makeText(context, getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                hideAllFragment();
                showFragment(feedbackView);
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onButtonDoneSendFeedbackPositiveClick() {
        model.sendPositiveFeedback(context, new SendFeedbackModel.OnSendPositiveFeedback() {
            @Override
            public void Success() {
                Toast.makeText(context, getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                hideAllFragment();
                showFragment(feedbackView);
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFeedbackNegativeCreateViewFinish() {
        model.getListFeedbackSample(context, new SendFeedbackModel.OnGetListFeedbackSample() {
            @Override
            public void Success() {
                negativeView.reloadView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFeedbackPositiveCreateViewFinish() {
        model.getListFeedbackSample(context, new SendFeedbackModel.OnGetListFeedbackSample() {
            @Override
            public void Success() {
                positiveView.reloadView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (feedbackView.isVisible()) {
            super.onBackPressed();
        } else if (negativeView.isVisible()) {
            hideAllFragment();
            showFragment(feedbackView);
        } else if (positiveView.isVisible()) {
            hideAllFragment();
            showFragment(feedbackView);
        }
    }
}
