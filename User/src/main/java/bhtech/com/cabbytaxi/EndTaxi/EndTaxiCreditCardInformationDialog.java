package bhtech.com.cabbytaxi.EndTaxi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

import bhtech.com.cabbytaxi.R;

/**
 * Created by thanh_nguyen on 15/01/2016.
 */
public class EndTaxiCreditCardInformationDialog extends Dialog implements View.OnClickListener, TextWatcher {
    private OnCreditCardInformationListener listener;
    private Context context;
    private TextView tvMoney;
    private FrameLayout btnCloseDialog, btnSubmit;
    private RadioButton rbVisa, rbMasterCard;
    private EditText etCardNumber, etCVVCode, etName, etPhoneNumber;

    public EndTaxiCreditCardInformationDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_endtaxi_creditcard_information);
        listener = (OnCreditCardInformationListener) context;
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        btnCloseDialog = (FrameLayout) findViewById(R.id.btnCloseDialog);
        btnSubmit = (FrameLayout) findViewById(R.id.btnSubmit);
        rbVisa = (RadioButton) findViewById(R.id.rbVisa);
        rbMasterCard = (RadioButton) findViewById(R.id.rbMasterCard);
        etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        etCVVCode = (EditText) findViewById(R.id.etCVVCode);
        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        btnCloseDialog.setOnClickListener(this);
        rbVisa.setOnClickListener(this);
        rbMasterCard.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        etCardNumber.addTextChangedListener(this);
        etCVVCode.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        etPhoneNumber.addTextChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listener.onCreditCardInformationDialogCreateViewFinish();
    }

    DecimalFormat df = new DecimalFormat("#.00");

    public void updateMoneyCharge(float money) {
        tvMoney.setText("$ " + df.format(money));
    }

    public void setCardNumber(String cardNumber) {
        etCardNumber.setText(cardNumber);
    }

    public void setCVVCode(String cvvCode) {
        etCVVCode.setText(cvvCode);
    }

    public void setName(String name) {
        etName.setText(name);
    }

    public void setPhoneNumber(String phoneNumber) {
        etPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCloseDialog:
                listener.onButtonCloseCreditCardInformationDialogClick();
                break;
            case R.id.btnSubmit:
                listener.onButtonSubmitCreditCardInformationDialogClick();
                break;
            case R.id.rbVisa:
                listener.visaCreditCardInformationIsChecked(rbVisa.isChecked());
                break;
            case R.id.rbMasterCard:
                listener.masterCardCreditCardInformationIsChecked(rbMasterCard.isChecked());
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (etCardNumber.getText().hashCode() == s.hashCode()) {
            listener.addTextChangedCardNumber(String.valueOf(etCardNumber.getText()));
        } else if (etCVVCode.getText().hashCode() == s.hashCode()) {
            listener.addTextChangedCVVCode(String.valueOf(etCVVCode.getText()));
        } else if (etName.getText().hashCode() == s.hashCode()) {
            listener.addTextChangedName(String.valueOf(etName.getText()));
        } else if (etPhoneNumber.getText().hashCode() == s.hashCode()) {
            listener.addTextChangedPhoneNumber(String.valueOf(etPhoneNumber.getText()));
        }
    }

    public interface OnCreditCardInformationListener {
        void onCreditCardInformationDialogCreateViewFinish();

        void onButtonCloseCreditCardInformationDialogClick();

        void onButtonSubmitCreditCardInformationDialogClick();

        void visaCreditCardInformationIsChecked(boolean isChecked);

        void masterCardCreditCardInformationIsChecked(boolean isChecked);

        void addTextChangedCardNumber(String s);

        void addTextChangedCVVCode(String s);

        void addTextChangedName(String s);

        void addTextChangedPhoneNumber(String s);
    }
}
