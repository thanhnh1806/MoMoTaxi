package bhtech.com.cabbytaxi.SendFeedback;

/**
 * Created by thanh_nguyen on 24/03/2016.
 */
public class SendFeedbackInterface {
    public interface Listener {
        void onFeedbackPositiveClick();

        void onFeedbackNegativeClick();

        void onButtonDoneSendFeedbackClick();

        void onButtonDoneSendFeedbackNegativeClick();

        void onButtonDoneSendFeedbackPositiveClick();

        void onFeedbackNegativeCreateViewFinish();

        void onFeedbackPositiveCreateViewFinish();
    }

    public interface Database {
        void setFeedback(String s);

        void setFeedbackNegative(String s);

        void setFeedbackPositive(String s);

        void setFeedbackPositiveCheckbox1(boolean isChecked);

        void setFeedbackPositiveCheckbox2(boolean isChecked);

        void setFeedbackPositiveCheckbox3(boolean isChecked);

        void setFeedbackPositiveCheckbox4(boolean isChecked);

        void setFeedbackPositiveCheckbox5(boolean isChecked);

        void setFeedbackNegativeCheckbox1(boolean isChecked);

        void setFeedbackNegativeCheckbox2(boolean isChecked);

        void setFeedbackNegativeCheckbox3(boolean isChecked);

        void setFeedbackNegativeCheckbox4(boolean isChecked);

        void setFeedbackNegativeCheckbox5(boolean isChecked);

        String getFeedbackNegative1();

        String getFeedbackNegative2();

        String getFeedbackNegative3();

        String getFeedbackNegative4();

        String getFeedbackPositive1();

        String getFeedbackPositive2();

        String getFeedbackPositive3();

        String getFeedbackPositive4();

    }
}
