package bhtech.com.cabbytaxi.SendFeedback;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.SendFeedback.SendFeedbackInterface;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 24/03/2016.
 */
public class SendFeedbackModel implements SendFeedbackInterface.Database {
    //Presention Data
    private String feedbackNegative1, feedbackNegative2, feedbackNegative3, feedbackNegative4,
            feedbackPositive1, feedbackPositive2, feedbackPositive3, feedbackPositive4;

    //Raw data
    private String feedbackMessage, feedbackNegativeUserType, feedbackPositiveUserType;
    private boolean positiveCheckbox1 = false, positiveCheckbox2 = false, positiveCheckbox3 = false,
            positiveCheckbox4 = false, positiveCheckbox5 = false;
    private boolean negativeCheckbox1 = false, negativeCheckbox2 = false, negativeCheckbox3 = false,
            negativeCheckbox4 = false, negativeCheckbox5 = false;

    @Override
    public void setFeedback(String s) {
        feedbackMessage = s;
    }

    @Override
    public void setFeedbackNegative(String s) {
        feedbackNegativeUserType = s;
    }

    @Override
    public void setFeedbackPositive(String s) {
        feedbackPositiveUserType = s;
    }

    @Override
    public void setFeedbackPositiveCheckbox1(boolean isChecked) {
        positiveCheckbox1 = isChecked;
    }

    @Override
    public void setFeedbackPositiveCheckbox2(boolean isChecked) {
        positiveCheckbox2 = isChecked;
    }

    @Override
    public void setFeedbackPositiveCheckbox3(boolean isChecked) {
        positiveCheckbox3 = isChecked;
    }

    @Override
    public void setFeedbackPositiveCheckbox4(boolean isChecked) {
        positiveCheckbox4 = isChecked;
    }

    @Override
    public void setFeedbackPositiveCheckbox5(boolean isChecked) {
        positiveCheckbox5 = isChecked;
    }

    @Override
    public void setFeedbackNegativeCheckbox1(boolean isChecked) {
        negativeCheckbox1 = isChecked;
    }

    @Override
    public void setFeedbackNegativeCheckbox2(boolean isChecked) {
        negativeCheckbox2 = isChecked;
    }

    @Override
    public void setFeedbackNegativeCheckbox3(boolean isChecked) {
        negativeCheckbox3 = isChecked;
    }

    @Override
    public void setFeedbackNegativeCheckbox4(boolean isChecked) {
        negativeCheckbox4 = isChecked;
    }

    @Override
    public void setFeedbackNegativeCheckbox5(boolean isChecked) {
        negativeCheckbox5 = isChecked;
    }

    @Override
    public String getFeedbackNegative1() {
        return feedbackNegative1;
    }

    @Override
    public String getFeedbackNegative2() {
        return feedbackNegative2;
    }

    @Override
    public String getFeedbackNegative3() {
        return feedbackNegative3;
    }

    @Override
    public String getFeedbackNegative4() {
        return feedbackNegative4;
    }

    @Override
    public String getFeedbackPositive1() {
        return feedbackPositive1;
    }

    @Override
    public String getFeedbackPositive2() {
        return feedbackPositive2;
    }

    @Override
    public String getFeedbackPositive3() {
        return feedbackPositive3;
    }

    @Override
    public String getFeedbackPositive4() {
        return feedbackPositive4;
    }

    public void setFeedbackNegative1(String feedbackNegative1) {
        this.feedbackNegative1 = feedbackNegative1;
    }

    public void setFeedbackNegative2(String feedbackNegative2) {
        this.feedbackNegative2 = feedbackNegative2;
    }

    public void setFeedbackNegative3(String feedbackNegative3) {
        this.feedbackNegative3 = feedbackNegative3;
    }

    public void setFeedbackNegative4(String feedbackNegative4) {
        this.feedbackNegative4 = feedbackNegative4;
    }

    public void setFeedbackPositive1(String feedbackPositive1) {
        this.feedbackPositive1 = feedbackPositive1;
    }

    public void setFeedbackPositive2(String feedbackPositive2) {
        this.feedbackPositive2 = feedbackPositive2;
    }

    public void setFeedbackPositive3(String feedbackPositive3) {
        this.feedbackPositive3 = feedbackPositive3;
    }

    public void setFeedbackPositive4(String feedbackPositive4) {
        this.feedbackPositive4 = feedbackPositive4;
    }

    public interface OnGetListFeedbackSample {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getListFeedbackSample(Context context, OnGetListFeedbackSample onFinish) {
        bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            setFeedbackNegative1("Sample1");
            setFeedbackNegative2("Sample2");
            setFeedbackNegative3("Sample3");
            setFeedbackNegative4("Sample4");

            setFeedbackPositive1("Sample1");
            setFeedbackPositive2("Sample2");
            setFeedbackPositive3("Sample3");
            setFeedbackPositive4("Sample4");
            onFinish.Success();
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface OnSendNegativeFeedback {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void sendNegativeFeedback(Context context, final OnSendNegativeFeedback onFinish) {
        bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Log.w("token", UserObj.getInstance().getUser_token());

            JsonArray reviewJson = new JsonArray();
            if (negativeCheckbox1) {
                reviewJson.add(feedbackNegative1);
            }
            if (negativeCheckbox2) {
                reviewJson.add(feedbackNegative2);
            }
            if (negativeCheckbox3) {
                reviewJson.add(feedbackNegative3);
            }
            if (negativeCheckbox4) {
                reviewJson.add(feedbackNegative4);
            }
            if (negativeCheckbox5) {
                reviewJson.add(feedbackNegativeUserType);
            }

            JsonObject negativeFeedbackJson = new JsonObject();
            negativeFeedbackJson.addProperty("type", String.valueOf(ContantValuesObject.FeedbackNegative));
            negativeFeedbackJson.addProperty("version", "0.1");
            negativeFeedbackJson.add("review", reviewJson);


            NetworkServices.callAPI(context, ContantValuesObject.SEND_FEEDBACK_ENDPOINT,
                    NetworkServices.POST, headers, negativeFeedbackJson, new NetworkServices.onCallApiJson() {
                        @Override
                        public void onFinish(JsonObject jsonObject) {
                            onFinish.Success();
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface OnSendPositiveFeedback {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void sendPositiveFeedback(Context context, final OnSendPositiveFeedback onFinish) {
        bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            Log.w("token", UserObj.getInstance().getUser_token());

            JsonArray reviewJson = new JsonArray();
            if (positiveCheckbox1) {
                reviewJson.add(feedbackPositive1);
            }
            if (positiveCheckbox2) {
                reviewJson.add(feedbackPositive2);
            }
            if (positiveCheckbox3) {
                reviewJson.add(feedbackPositive3);
            }
            if (positiveCheckbox4) {
                reviewJson.add(feedbackPositive4);
            }
            if (positiveCheckbox5) {
                reviewJson.add(feedbackPositiveUserType);
            }

            JsonObject positiveFeedbackJson = new JsonObject();
            positiveFeedbackJson.addProperty("type", String.valueOf(ContantValuesObject.FeedbackPositive));
            positiveFeedbackJson.addProperty("version", "0.1");
            positiveFeedbackJson.add("review", reviewJson);


            NetworkServices.callAPI(context, ContantValuesObject.SEND_FEEDBACK_ENDPOINT,
                    NetworkServices.POST, headers, positiveFeedbackJson, new NetworkServices.onCallApiJson() {
                        @Override
                        public void onFinish(JsonObject jsonObject) {
                            onFinish.Success();
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }
}
