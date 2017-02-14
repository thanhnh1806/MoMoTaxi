package bhtech.com.cabbytaxi.EndTaxi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.RateDriver.RateTaxiController;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.CardObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;

public class EndTaxiController extends SlidingMenuController implements EndTaxiPayByCashDialog.OnPayByCashDialogListener,
        EndTaxiCreditCardInformationDialog.OnCreditCardInformationListener,
        EndTaxiPayByCreaditCardDialog.OnPayByCreditCardDialogListener, EndTaxiInterface.Listener {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(ContantValuesObject.GCM_INTENT_FILTER_ACTION)) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                String messageType = gcm.getMessageType(intent);
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                    Log.e("GoogleCloudMessaging", "MESSAGE_TYPE_SEND_ERROR");
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                    Log.e("GoogleCloudMessaging", "MESSAGE_TYPE_DELETED");
                } else {
                    String msg = intent.getStringExtra("message");
                    String data = intent.getStringExtra("data");
                    Log.e("msg", msg);
                    Log.e("data", data);
                    model.setStatusRequest(context, data);
                    payMethodView.reloadView();
                }
            }
        }
    };
    private Context context;
    private EndTaxiViewPayMethod payMethodView;
    private EndTaxiPayByCashDialog payByCashDialog;
    private EndTaxiCreditCardInformationDialog creditCardInformationDialog;
    private EndTaxiPayByCreaditCardDialog payByCreaditCardDialog;
    private EndTaxiModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = EndTaxiController.this;
        getLayoutInflater().inflate(R.layout.activity_end_taxi_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.taxi_charge));
        payMethodView = new EndTaxiViewPayMethod();
        payByCashDialog = new EndTaxiPayByCashDialog(context);
        creditCardInformationDialog = new EndTaxiCreditCardInformationDialog(context);
        payByCreaditCardDialog = new EndTaxiPayByCreaditCardDialog(context);
        model = new EndTaxiModel(context);

        payMethodView.setListener(this);
        payMethodView.setDatasource(model);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.end_taxi_container, payMethodView).commit();
        }

        model.getCurrentRequest(new EndTaxiModel.onGetCurrentRequest() {
            @Override
            public void Success() {
                payMethodView.reloadView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContantValuesObject.GCM_INTENT_FILTER_ACTION);
        filter.addAction(ContantValuesObject.CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION);
        this.registerReceiver(receiver, filter);
    }

    @Override
    public void onButtonOkClick() {
        payByCashDialog.show();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPayByCashDialogCreateViewFinish() {
        payByCashDialog.updateMoney(model.getCharge(context));
    }

    @Override
    public void onPayByCashButtonOkClick() {
        payByCashDialog.dismiss();
        SharedPreference.set(context, "extra_price", "");
        SharedPreference.set(context, "price", "");
        startActivity(new Intent(context, RateTaxiController.class));
        finish();
    }

    @Override
    public void onCreditCardInformationDialogCreateViewFinish() {
        creditCardInformationDialog.updateMoneyCharge(model.getCharge(context));
        CardObj cardObj = model.getCardInformation();
        creditCardInformationDialog.setCardNumber(cardObj.getCard_number());
        creditCardInformationDialog.setCVVCode(cardObj.getCvv_code());
        creditCardInformationDialog.setName(cardObj.getCard_holder_name());
        creditCardInformationDialog.setPhoneNumber(cardObj.getOwner().getPhoneNumber());
    }

    @Override
    public void onButtonCloseCreditCardInformationDialogClick() {
        creditCardInformationDialog.dismiss();
    }

    @Override
    public void onButtonSubmitCreditCardInformationDialogClick() {
        creditCardInformationDialog.dismiss();
        updateToolbarTitle(getString(R.string.payment));
        payByCreaditCardDialog.show();
        payByCreaditCardDialog.updateUserId(String.valueOf(TaxiRequestObj.getInstance().
                getRequestUser().getObjectID()));
        payByCreaditCardDialog.updatePrice(model.getCharge(context));
    }

    @Override
    public void visaCreditCardInformationIsChecked(boolean isChecked) {
        //TODO get VisaCard Information
        if (isChecked) {

        }
    }

    @Override
    public void masterCardCreditCardInformationIsChecked(boolean isChecked) {
        //TODO get MasterCard Information
    }

    @Override
    public void addTextChangedCardNumber(String s) {
        //TODO set card number to Card Infor
    }

    @Override
    public void addTextChangedCVVCode(String s) {
        //TODO set CVV code to Card Infor
    }

    @Override
    public void addTextChangedName(String s) {
        //TODO set name to Card Infor
    }

    @Override
    public void addTextChangedPhoneNumber(String s) {
        //TODO set phone number to Card Infor
    }

    @Override
    public void onPayByCreditCardButtonOkClick() {
        payByCreaditCardDialog.dismiss();
        startActivity(new Intent(context, RateTaxiController.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
