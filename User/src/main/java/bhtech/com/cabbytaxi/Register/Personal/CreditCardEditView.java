package bhtech.com.cabbytaxi.Register.Personal;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;

public class CreditCardEditView extends Fragment {
    private ProfileSettingInterface.Listener listener;
    private ProfileSettingInterface.Database datasoucre;

    public void setListener(ProfileSettingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasoucre(ProfileSettingInterface.Database datasoucre) {
        this.datasoucre = datasoucre;
    }

    private EditText etAmountLimit, etCardNumber, etCardHolderName, etCVVCode, etCardExDate;
    private RadioButton rbOnce, rbMonthly, rbVisa, rbMasterCard;
    private TextView btnOK;
    private FrameLayout btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_card_edit_view, container, false);
        etAmountLimit = (EditText) view.findViewById(R.id.etAmountLimit);
        etCardNumber = (EditText) view.findViewById(R.id.etCardNumber);
        etCardHolderName = (EditText) view.findViewById(R.id.etCardHolderName);
        etCVVCode = (EditText) view.findViewById(R.id.etCVVCode);
        etCardExDate = (EditText) view.findViewById(R.id.etCardExDate);
        btnBack = (FrameLayout) view.findViewById(R.id.btnBack);
        btnOK = (TextView) view.findViewById(R.id.btnOK);
        rbOnce = (RadioButton) view.findViewById(R.id.rbOnce);
        rbMonthly = (RadioButton) view.findViewById(R.id.rbMonthly);
        rbVisa = (RadioButton) view.findViewById(R.id.rbVisa);
        rbMasterCard = (RadioButton) view.findViewById(R.id.rbMasterCard);

        etAmountLimit.setFilters(new InputFilter[]{new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
            int beforeDecimal = 50, afterDecimal = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String temp = etAmountLimit.getText() + source.toString();

                if (temp.equals(".")) {
                    return "0.";
                } else if (temp.toString().indexOf(".") == -1) {
                    // no decimal point placed yet
                    if (temp.length() > beforeDecimal) {
                        return "";
                    }
                } else {
                    temp = temp.substring(temp.indexOf(".") + 1);
                    if (temp.length() > afterDecimal) {
                        return "";
                    }
                }

                return super.filter(source, start, end, dest, dstart, dend);
            }
        }});

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasoucre.setAmountOfLimit(etAmountLimit.getText().toString());
                datasoucre.setCardNumber(String.valueOf(etCardNumber.getText()));
                datasoucre.setCardHolderName(String.valueOf(etCardHolderName.getText()));
                datasoucre.setCvvCode(String.valueOf(etCVVCode.getText()));
                datasoucre.setCardExpirationDate(String.valueOf(etCardExDate.getText()));
                if (rbOnce.isChecked()) {
                    datasoucre.setPaymentType(ContantValuesObject.PaymentTypeOnce);
                } else if (rbMonthly.isChecked()) {
                    datasoucre.setPaymentType(ContantValuesObject.PaymentTypeMonthly);
                }
                if (rbVisa.isChecked()) {
                    datasoucre.setCardType(ContantValuesObject.CardTypeVISA);
                } else if (rbMasterCard.isChecked()) {
                    datasoucre.setCardType(ContantValuesObject.CardTypeMasterCard);
                }
                listener.onCreditCardButtonOKClick();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCreditCardButtonBackClick();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasoucre = null;
    }
}
