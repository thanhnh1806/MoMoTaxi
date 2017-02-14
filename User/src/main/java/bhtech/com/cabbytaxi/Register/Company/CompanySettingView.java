package bhtech.com.cabbytaxi.Register.Company;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.CountryAdapter;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import de.hdodenhof.circleimageview.CircleImageView;


public class CompanySettingView extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {
    private CompanySettingInterface.Listener listener;
    private CompanySettingInterface.Database database;

    public void setListener(CompanySettingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(CompanySettingInterface.Database database) {
        this.database = database;
    }

    public CompanySettingView() {

    }

    private FrameLayout layoutAvatar, btnBack;
    private CircleImageView ivAvatar;
    private ImageView ivAvatarPlaceHolder;
    private EditText etCompanyName, etPersonName, etPhoneNumber, etEmail;
    private EditText etUsername, etPassword, etReType, etUsageDetail;
    private Spinner spCountry;
    private LinearLayout layoutRadioButtonCreaditCard, layoutRadioButtonCash, layoutCheckBox;
    private RadioButton rbCreaditCard, rbCash;
    private CheckBox cbAgree;
    private TextView tvTermOfUse;
    private Button btnDone;
    private View lineCompanyName, linePersonName, linePhoneNumber, lineEmail, lineCountry;
    private View lineUsername, linePassword, lineRetypePassword, lineUsageDetail;
    private ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_company_setting_view, container, false);
        etCompanyName = (EditText) v.findViewById(R.id.etCompanyName);
        etPersonName = (EditText) v.findViewById(R.id.etPersonName);
        etPhoneNumber = (EditText) v.findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etUsername = (EditText) v.findViewById(R.id.etUsername);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        etReType = (EditText) v.findViewById(R.id.etReType);
        etUsageDetail = (EditText) v.findViewById(R.id.etUsageDetail);
        tvTermOfUse = (TextView) v.findViewById(R.id.tvTermOfUse);
        layoutAvatar = (FrameLayout) v.findViewById(R.id.layoutAvatar);
        ivAvatar = (CircleImageView) v.findViewById(R.id.ivAvatar);
        ivAvatarPlaceHolder = (ImageView) v.findViewById(R.id.ivAvatarPlaceHolder);
        spCountry = (Spinner) v.findViewById(R.id.spCountry);
        layoutRadioButtonCreaditCard = (LinearLayout) v.findViewById(R.id.layoutRadioButtonCreaditCard);
        layoutRadioButtonCash = (LinearLayout) v.findViewById(R.id.layoutRadioButtonCash);
        layoutCheckBox = (LinearLayout) v.findViewById(R.id.layoutCheckBox);
        rbCreaditCard = (RadioButton) v.findViewById(R.id.rbCreaditCard);
        rbCash = (RadioButton) v.findViewById(R.id.rbCash);
        cbAgree = (CheckBox) v.findViewById(R.id.cbAgree);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        btnDone = (Button) v.findViewById(R.id.btnDone);

        lineCompanyName = v.findViewById(R.id.lineCompanyName);
        linePersonName = v.findViewById(R.id.linePersonName);
        linePhoneNumber = v.findViewById(R.id.linePhoneNumber);
        lineEmail = v.findViewById(R.id.lineEmail);
        lineCountry = v.findViewById(R.id.lineCountry);
        lineUsername = v.findViewById(R.id.lineUsername);
        linePassword = v.findViewById(R.id.linePassword);
        lineRetypePassword = v.findViewById(R.id.lineRetypePassword);
        lineUsageDetail = v.findViewById(R.id.lineUsageDetail);

        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        etReType.setTransformationMethod(new PasswordTransformationMethod());

        layoutAvatar.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        layoutRadioButtonCreaditCard.setOnClickListener(this);
        layoutRadioButtonCash.setOnClickListener(this);
        layoutCheckBox.setOnClickListener(this);
        rbCreaditCard.setOnClickListener(this);
        rbCash.setOnClickListener(this);
        tvTermOfUse.setOnClickListener(this);
        etCompanyName.setOnFocusChangeListener(this);
        etPersonName.setOnFocusChangeListener(this);
        etPhoneNumber.setOnFocusChangeListener(this);
        etEmail.setOnFocusChangeListener(this);
        etUsername.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etReType.setOnFocusChangeListener(this);
        etUsageDetail.setOnFocusChangeListener(this);

        spCountry.setFocusable(true);
        spCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lineCompanyName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                linePersonName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                linePhoneNumber.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineCountry.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineEmail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                linePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineRetypePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineUsageDetail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineCountry.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                return false;
            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list = database.getListCountry();
                database.setCountry(String.valueOf(list.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> list = database.getListCountry();
                database.setCountry(String.valueOf(list.get(0)));
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onCreateViewFinish();
    }

    public void reloadView() {
        adapter = new CountryAdapter(getActivity(), database.getListCountry());
        spCountry.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        lineCompanyName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        linePersonName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        linePhoneNumber.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineCountry.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineEmail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        linePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineRetypePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineUsageDetail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));

        switch (v.getId()) {
            case R.id.etCompanyName:
                if (hasFocus) {
                    lineCompanyName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etPersonName:
                if (hasFocus) {
                    linePersonName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etPhoneNumber:
                if (hasFocus) {
                    linePhoneNumber.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etEmail:
                if (hasFocus) {
                    lineEmail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etUsername:
                if (hasFocus) {
                    lineUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etPassword:
                if (hasFocus) {
                    linePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etReType:
                if (hasFocus) {
                    lineRetypePassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etUsageDetail:
                if (hasFocus) {
                    lineUsageDetail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAvatar:
                listener.OnAvatarClick();
                break;
            case R.id.btnBack:
                listener.OnButtonBackClick();
                break;
            case R.id.btnDone:
                database.setCompanyName(String.valueOf(etCompanyName.getText()));
                database.setPersonStaffName(String.valueOf(etPersonName.getText()));
                database.setPhoneNumber(String.valueOf(etPhoneNumber.getText()));
                database.setEmail(String.valueOf(etEmail.getText()));
                database.setUsername(String.valueOf(etUsername.getText()));
                database.setPassword(String.valueOf(etPassword.getText()));
                database.setConfirmedPassword(String.valueOf(etReType.getText()));

                if (rbCreaditCard.isChecked()) {
                    database.setPayMode(ContantValuesObject.PayByCreditCard);
                } else if (rbCash.isChecked()) {
                    database.setPayMode(ContantValuesObject.PayByCash);
                } else {
                    database.setPayMode(-1);
                }

                database.setUsageDetail(String.valueOf(etUsageDetail.getText()));
                database.setAccepted(cbAgree.isChecked());
                listener.OnButtonDoneClick();
                break;
            case R.id.layoutRadioButtonCreaditCard:
                rbCreaditCard.setChecked(true);
                rbCash.setChecked(false);
                break;
            case R.id.layoutRadioButtonCash:
                rbCash.setChecked(true);
                rbCreaditCard.setChecked(false);
                break;
            case R.id.rbCreaditCard:
                rbCreaditCard.setChecked(true);
                rbCash.setChecked(false);
                break;
            case R.id.rbCash:
                rbCash.setChecked(true);
                rbCreaditCard.setChecked(false);
                break;
            case R.id.layoutCheckBox:
                if (cbAgree.isChecked()) {
                    cbAgree.setChecked(false);
                } else {
                    cbAgree.setChecked(true);
                }
                break;

            case R.id.tvTermOfUse:
                listener.termOfUseClick();
                break;
        }
    }

    public void setAvatarImage(Bitmap bitmap) {
        ivAvatarPlaceHolder.setVisibility(View.GONE);
        ivAvatar.setImageBitmap(bitmap);
    }
}
