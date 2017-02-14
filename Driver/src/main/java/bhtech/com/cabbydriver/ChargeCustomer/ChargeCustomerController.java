package bhtech.com.cabbydriver.ChargeCustomer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class ChargeCustomerController extends SlidingMenuController implements ChargeCustomerInterface.Listener {
    Context context = this;
    ChargeCustomerModel model = new ChargeCustomerModel(context);
    ChargeCustomerView view = new ChargeCustomerView();
    ChargeCustomerCreditCardView creditCardView = new ChargeCustomerCreditCardView();
    ChargeCustomerCashView cashView = new ChargeCustomerCashView();
    ChargeCustomerCreditCardSuccessDialog successDialog;
    ChargeCustomerCreditCardFailureDialog failureDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(cashView, getString(R.string.payment));
        setView(creditCardView, getString(R.string.payment));
        setView(view, getString(R.string.taxi_charge));

        view.setListener(this);
        view.setDatasource(model);
        cashView.setListener(this);
        creditCardView.setListener(this);

        successDialog = new ChargeCustomerCreditCardSuccessDialog(context, this);
        failureDialog = new ChargeCustomerCreditCardFailureDialog(context, this);

        hideAllFragment();
        showFragment(view);
    }

    private void hideAllFragment() {
        hideFragment(view);
        hideFragment(creditCardView);
        hideFragment(cashView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContantValuesObject.GCM_INTENT_FILTER_ACTION);
        filter.addAction(ContantValuesObject.CONNECTIVITY_CHANGE_INTENT_FILTER_ACTION);

        this.registerReceiver(receiver, filter);

        if (TaxiRequestObj.getInstance().getRequestUser() == null) {
            view.reloadNonAppUserView(true);
        } else {
            view.reloadNonAppUserView(false);
        }
        model.getCurrentRequest(new ChargeCustomerModel.OnGetCurrentRequest() {
            @Override
            public void Success() {
                view.reloadView();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void destinationLocationClick() {

    }

    @Override
    public void updateCharge() {
        if (TaxiRequestObj.getInstance().getRequestUser() == null) {
            //For non-app user
            if (model.getPayMode() == ContantValuesObject.PayByCash) {
                model.changeDriverStatus(new ChargeCustomerModel.OnChangeDriverStatus() {
                    @Override
                    public void Success() {
                        startActivity(new Intent(context, FindCustomerController.class));
                        finishActivity();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            }
        } else {
            model.updateCharge(new ChargeCustomerModel.OnUpdateCharge() {
                @Override
                public void Success() {
                    hideAllFragment();
                    TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusPaid);
                    if (model.getPayMode() == ContantValuesObject.PayByCash) {
                        showFragment(cashView);
                    } else {
                        showFragment(creditCardView);
                    }
                }

                @Override
                public void Failure(ErrorObj error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        }
    }

    @Override
    public void paymentByCashButtonOkClick() {
        TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusPending);
        startActivity(new Intent(context, FindCustomerController.class));
        finishActivity();
    }

    @Override
    public void paymentByCreditCardButtonCancelClick() {
        failureDialog.show();
    }

    @Override
    public void creditCardPaymentSuccessful() {
        successDialog.dismiss();
        startActivity(new Intent(context, FindCustomerController.class));
        finishActivity();
    }

    @Override
    public void creditCardPaymentUnsuccessful() {
        failureDialog.dismiss();
        hideAllFragment();
        showFragment(view);
    }

    @Override
    public void buttonNextClick() {
        hideAllFragment();
        showFragment(creditCardView);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(ContantValuesObject.GCM_INTENT_FILTER_ACTION)) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                String messageType = gcm.getMessageType(intent);
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                } else {
                    String data = intent.getStringExtra(ContantValuesObject.DATA);
                    Log.w("ChooseRouteToCustomer", data);
                    if (model.getCustomerStatus(data) == ContantValuesObject.TaxiRequestStatusPaid) {
                        if (model.getPayMode() == ContantValuesObject.PayByCash) {
                            startActivity(new Intent(context, FindCustomerController.class));
                            finishActivity();
                        } else {
                            if (creditCardView.isHidden()) {
                                hideAllFragment();
                                showFragment(creditCardView);
                            }
                            failureDialog.dismiss();
                            successDialog.show();
                        }
                    } else {
                        Log.w("BroadcastReceiver", data);
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
    }
}
