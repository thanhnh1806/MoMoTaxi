package bhtech.com.cabbytaxi.controller;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.DriverChargeCustomerInterface;
import bhtech.com.cabbytaxi.model.DriverChargeCustomerModel;
import bhtech.com.cabbytaxi.view.Dialog_cancel_payment;
import bhtech.com.cabbytaxi.view.DriverReceiptView;
import bhtech.com.cabbytaxi.view.PaymentView;
import bhtech.com.cabbytaxi.view.TaxiChargeView;

public class DriverChargeCustomerController extends AppCompatActivity implements DriverChargeCustomerInterface.Listener {

    private Context context;
    private TaxiChargeView taxiChargeView;
    private DriverReceiptView driverReceiptView;
    private PaymentView paymentView;
    private Dialog_cancel_payment dialogCancelPay;
    private DriverChargeCustomerModel chargeCustomerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_charge_customer_controller);
        context = DriverChargeCustomerController.this;
        taxiChargeView = new TaxiChargeView();
        chargeCustomerModel = new DriverChargeCustomerModel();
        taxiChargeView.setListener(this);
        taxiChargeView.setDataSource(chargeCustomerModel);
        driverReceiptView = new DriverReceiptView();
        paymentView = new PaymentView();

        paymentView.setListener(this);
        paymentView.setDataSource(chargeCustomerModel);
        dialogCancelPay = new Dialog_cancel_payment(context);

        dialogCancelPay.setListener(this);
        dialogCancelPay.setDataSource(chargeCustomerModel);
        if (savedInstanceState == null) {
            addFragment(R.id.taxi_charge_container, taxiChargeView);
            addFragment(R.id.taxi_charge_container, paymentView);
            addFragment(R.id.taxi_charge_container, driverReceiptView);
            hideAllFragment();
            showFragment(taxiChargeView);
        }
    }

    private void addFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void addFragmentv4(int container, android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void hideAllFragment() {
        hideFragment(taxiChargeView);
        hideFragment(paymentView);
        hideFragment(driverReceiptView);
        //hideFragment(foundATaxiView);
    }

    public void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().show(fragment).commit();
    }

    public void hideFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().hide(fragment).commit();
    }


    @Override
    public void onButtonCancelPaymentViaCreditCardClick() {
        dialogCancelPay.show();
    }

    @Override
    public void onButtonOkClick() {
        hideAllFragment();
        showFragment(driverReceiptView);
    }

    @Override
    public void onButtonPayByCreditCardClick() {
        hideAllFragment();
        showFragment(paymentView);
    }

    @Override
    public void onButtonPayByCashClick() {

    }

    @Override
    public void onButtonNextClick() {

    }

    @Override
    public void onButtonYesClick() {
        dialogCancelPay.dismiss();
        hideAllFragment();
        showFragment(taxiChargeView);
    }

    @Override
    public void onButtonNoClick() {
        dialogCancelPay.dismiss();
    }

    @Override
    public void onDialogCreateViewFinish() {

    }

    @Override
    public void onPayByCashReceiptSentCreateViewFinish() {

    }

    @Override
    public void onCreditCardInfoSentToCustomerCreateViewFinish() {

    }

    @Override
    public void onTaxiChargeCreateViewFinish() {
        taxiChargeView.updateTime(chargeCustomerModel.getTime());
        taxiChargeView.updateDate(chargeCustomerModel.getDate());
        taxiChargeView.updateCustomerName(chargeCustomerModel.getCustomerName());
        taxiChargeView.updateStart(chargeCustomerModel.getStartAddress());
        taxiChargeView.updateStop(chargeCustomerModel.getStopAddress());
        taxiChargeView.updateMinues(chargeCustomerModel.getMinutes());
        taxiChargeView.updateKm(chargeCustomerModel.getKm());
        taxiChargeView.upadateCharge(chargeCustomerModel.getCharge());
    }
}
