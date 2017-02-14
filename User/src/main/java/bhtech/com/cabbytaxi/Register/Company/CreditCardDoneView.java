package bhtech.com.cabbytaxi.Register.Company;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.TimeObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class CreditCardDoneView extends Fragment {
    private CompanySettingInterface.Listener listener;
    private CompanySettingInterface.Database datasoucre;
    private boolean finishButtonEnable;

    public void setListener(CompanySettingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasoucre(CompanySettingInterface.Database datasoucre) {
        this.datasoucre = datasoucre;
    }

    private ImageView ivPersonalCreditCard, ivVisa, ivMasterCard;
    private TextView tvCompanyName, tvCardNumber, tvCardHolderName, tvCardExDate, tvAmountLimit;
    private TextView tvPaymentType, tvCompanyPhone, tvCompanyEmail, tvUsageDetailsEmail;
    private FrameLayout btnFinish;
    private LinearLayout ivComapanyBag, layoutCompany, layout_Personal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credit_card_done_view, container, false);
        ivPersonalCreditCard = (ImageView) v.findViewById(R.id.ivPersonalCreditCard);
        ivComapanyBag = (LinearLayout) v.findViewById(R.id.ivComapanyBag);
        ivVisa = (ImageView) v.findViewById(R.id.ivVisa);
        ivMasterCard = (ImageView) v.findViewById(R.id.ivMasterCard);
        tvCompanyName = (TextView) v.findViewById(R.id.tvCompanyName);
        tvAmountLimit = (TextView) v.findViewById(R.id.tvAmountLimit);
        tvCardNumber = (TextView) v.findViewById(R.id.tvCardNumber);
        tvCardHolderName = (TextView) v.findViewById(R.id.tvCardHolderName);
        tvCardExDate = (TextView) v.findViewById(R.id.tvCardExDate);
        tvPaymentType = (TextView) v.findViewById(R.id.tvPaymentType);
        tvCompanyPhone = (TextView) v.findViewById(R.id.tvCompanyPhone);
        tvCompanyEmail = (TextView) v.findViewById(R.id.tvCompanyEmail);
        tvUsageDetailsEmail = (TextView) v.findViewById(R.id.tvUsageDetailsEmail);

        btnFinish = (FrameLayout) v.findViewById(R.id.btnFinish);
        layoutCompany = (LinearLayout) v.findViewById(R.id.layoutCompany);
        layout_Personal = (LinearLayout) v.findViewById(R.id.layout_Personal);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFinishButtonEnable(false);
                listener.onButtonFinishClick();
            }
        });
        return v;
    }

    public void reloadView() {
        if (UserObj.getInstance().getCompany() != null) {
            ivPersonalCreditCard.setVisibility(View.GONE);
            ivComapanyBag.setVisibility(View.VISIBLE);
            layoutCompany.setVisibility(View.VISIBLE);
            layout_Personal.setVisibility(View.GONE);
            tvCompanyPhone.setText(datasoucre.getCompanyPhone());
            tvCompanyName.setText(datasoucre.getCompanyName());
            tvCompanyEmail.setText(datasoucre.getCompanyEmail());
            tvUsageDetailsEmail.setText(datasoucre.getUsageDetailsEmail());
        } else {
            layoutCompany.setVisibility(View.GONE);
            layout_Personal.setVisibility(View.VISIBLE);
            ivPersonalCreditCard.setVisibility(View.VISIBLE);
            ivComapanyBag.setVisibility(View.GONE);
            tvAmountLimit.setText(getString(R.string.rm) + " " + datasoucre.getAmountOfLimit());
            if (datasoucre.getPaymentType() == ContantValuesObject.PaymentTypeOnce) {
                tvPaymentType.setText(" (" + getString(R.string.once) + ")");
            } else {
                tvPaymentType.setText(" (" + getString(R.string.monthly) + ")");
            }

            if (datasoucre.getCardType() == ContantValuesObject.CardTypeVISA) {
                ivVisa.setVisibility(View.VISIBLE);
                ivMasterCard.setVisibility(View.INVISIBLE);
            } else {
                ivVisa.setVisibility(View.INVISIBLE);
                ivMasterCard.setVisibility(View.VISIBLE);
            }

            try {
                String cardNumber = "";
                for (int i = 0; i < datasoucre.getCardNumber().length(); i++) {
                    cardNumber += datasoucre.getCardNumber().charAt(i);
                    if (i == 3 || i == 7 || i == 11) {
                        cardNumber += "-";
                    }
                }
                tvCardNumber.setText(cardNumber);

                tvCardHolderName.setText(datasoucre.getCardHolderName());
                String[] cardExDate = datasoucre.getCardExpirationDate().split("/");

                String month = TimeObject.getListMonth().get(Integer.parseInt(cardExDate[0]) - 1).substring(0, 3).toUpperCase();
                tvCardExDate.setText(month + " 20" + cardExDate[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasoucre = null;
    }

    public void setFinishButtonEnable(boolean b) {
        btnFinish.setEnabled(b);
    }
}
