package bhtech.com.cabbytaxi.Register.Personal;


import android.app.Fragment;
import android.content.Context;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingView extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private ProfileSettingInterface.Listener listener;
    private ProfileSettingInterface.Database database;

    public void setListener(ProfileSettingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(ProfileSettingInterface.Database database) {
        this.database = database;
    }

    private Context context;
    private FrameLayout btnBack, layoutAvatar, ivFacebook, ivGooglePlus, ivTwitter;
    private CircleImageView ivAvatar;
    private ImageView ivAvatarPlaceHolder;
    private EditText etYourName, etYourPhoneNumber, etYourEmail, etUsername;
    private EditText etYourPassword, etConfirmPassword;
    private Spinner spCountry;
    private LinearLayout layoutRadioButtonCreaditCard, layoutRadioButtonCash, layoutCheckBox;
    private RadioButton rbCreaditCard, rbCash;
    private CheckBox cbAgree;
    private Button btnDone;
    private ArrayAdapter adapter;
    private View lineYourName, lineYourPhoneNumber, lineYourEmail, lineYourUsername;
    private View lineCountry, lineYourPassword, lineConfirmPassword;
    private TextView tvTermOfUse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_profile_setting_view, container, false);
        layoutAvatar = (FrameLayout) v.findViewById(R.id.layoutAvatar);
        ivAvatar = (CircleImageView) v.findViewById(R.id.ivAvatar);
        ivAvatarPlaceHolder = (ImageView) v.findViewById(R.id.ivAvatarPlaceHolder);
        ivFacebook = (FrameLayout) v.findViewById(R.id.ivFacebook);
        ivGooglePlus = (FrameLayout) v.findViewById(R.id.ivGooglePlus);
        ivTwitter = (FrameLayout) v.findViewById(R.id.ivTwitter);

        etYourName = (EditText) v.findViewById(R.id.etYourName);
        etYourPhoneNumber = (EditText) v.findViewById(R.id.etYourPhoneNumber);
        etYourEmail = (EditText) v.findViewById(R.id.etYourEmail);
        etUsername = (EditText) v.findViewById(R.id.etUsername);
        etYourPassword = (EditText) v.findViewById(R.id.etYourPassword);
        etConfirmPassword = (EditText) v.findViewById(R.id.etConfirmPassword);
        etYourPassword.setTransformationMethod(new PasswordTransformationMethod());
        etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        spCountry = (Spinner) v.findViewById(R.id.spCountry);
        spCountry.setFocusable(true);
        spCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lineYourName.setBackgroundColor(context.getResources().getColor(R.color.line_gray));
                lineYourPhoneNumber.setBackgroundColor(context.getResources().getColor(R.color.line_gray));
                lineYourEmail.setBackgroundColor(context.getResources().getColor(R.color.line_gray));
                lineYourUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineYourPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineConfirmPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
                lineCountry.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                return false;
            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list = database.getListCountry();
                database.setPositionListCountryUserChoose(position);
                database.setCountry(String.valueOf(list.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> list = database.getListCountry();
                database.setCountry(String.valueOf(list.get(0)));
            }
        });
        layoutRadioButtonCreaditCard = (LinearLayout) v.findViewById(R.id.layoutRadioButtonCreaditCard);
        layoutRadioButtonCash = (LinearLayout) v.findViewById(R.id.layoutRadioButtonCash);
        layoutCheckBox = (LinearLayout) v.findViewById(R.id.layoutCheckBox);
        rbCreaditCard = (RadioButton) v.findViewById(R.id.rbCreaditCard);
        rbCash = (RadioButton) v.findViewById(R.id.rbCash);

        cbAgree = (CheckBox) v.findViewById(R.id.cbAgree);
        tvTermOfUse = (TextView) v.findViewById(R.id.tvTermOfUse);
        btnDone = (Button) v.findViewById(R.id.btnDone);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);

        lineYourName = v.findViewById(R.id.lineYourName);
        lineYourPhoneNumber = v.findViewById(R.id.lineYourPhoneNumber);
        lineYourEmail = v.findViewById(R.id.lineYourEmail);
        lineYourUsername = v.findViewById(R.id.lineYourUsername);
        lineCountry = v.findViewById(R.id.lineCountry);
        lineYourPassword = v.findViewById(R.id.lineYourPassword);
        lineConfirmPassword = v.findViewById(R.id.lineConfirmPassword);

        layoutAvatar.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        layoutCheckBox.setOnClickListener(this);
        layoutRadioButtonCreaditCard.setOnClickListener(this);
        layoutRadioButtonCash.setOnClickListener(this);
        rbCreaditCard.setOnClickListener(this);
        rbCash.setOnClickListener(this);
        tvTermOfUse.setOnClickListener(this);

        etYourName.setOnFocusChangeListener(this);
        etYourPhoneNumber.setOnFocusChangeListener(this);
        etYourEmail.setOnFocusChangeListener(this);
        etUsername.setOnFocusChangeListener(this);
        etYourPassword.setOnFocusChangeListener(this);
        etConfirmPassword.setOnFocusChangeListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onCreateViewFinish();
    }

    int positionCountry;

    public void reloadView() {
        final ArrayList<String> listCountry = database.getListCountry();
        positionCountry = database.getListCountry().size() - 1;
        adapter = new CountryAdapter(getActivity(), listCountry);
        spCountry.setAdapter(adapter);
        if (database.getPositionListCountryUserChoose() == -1) {
//            if (UserObj.getInstance().getLocation() != null) {
//                LocationObject.getCountryFromLatLng(context, UserObj.getInstance().getLocation(),
//                        new LocationObject.onGetCountryFromLatLng() {
//                            @Override
//                            public void Success(String address) {
//                                for (int i = 0; i < listCountry.size(); i++) {
//                                    if (address.equalsIgnoreCase(listCountry.get(i))) {
//                                        positionCountry = i;
//                                        break;
//                                    }
//                                }
//                                spCountry.setSelection(positionCountry);
//                            }
//
//                            @Override
//                            public void Failure(Error error) {
//                                spCountry.setSelection(database.getListCountry().size() - 1);
//                            }
//                        });
//            } else {
//                spCountry.setSelection(database.getListCountry().size() - 1);
//            }
            spCountry.setSelection(database.getListCountry().size() - 1);
        } else {
            spCountry.setSelection(database.getPositionListCountryUserChoose());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAvatar:
                listener.onImageAvatarClick();
                break;
            case R.id.layoutCheckBox:
                cbAgree.setChecked(!cbAgree.isChecked());
                break;
            case R.id.ivFacebook:
                listener.onImageFacebookClick();
                break;
            case R.id.ivGooglePlus:
                listener.onImageGooglePlusClick();
                break;
            case R.id.ivTwitter:
                listener.onImageTwitterClick();
                break;
            case R.id.btnDone:
                database.setYourName(String.valueOf(etYourName.getText()));
                database.setYourPhoneNumber(String.valueOf(etYourPhoneNumber.getText()));
                database.setYourEmail(String.valueOf(etYourEmail.getText()));
                database.setUsername(String.valueOf(etUsername.getText()));
                database.setPassword(String.valueOf(etYourPassword.getText()));
                database.setConfirmPassword(String.valueOf(etConfirmPassword.getText()));
                database.setCheckBoxAgree(cbAgree.isChecked());
                database.setRadioButtonCreaditCard(rbCreaditCard.isChecked());
                database.setRadioButtonCash(rbCash.isChecked());
                listener.onButtonDoneClick();
                break;
            case R.id.btnBack:
                listener.onProfileButtonBackClick();
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
            case R.id.tvTermOfUse:
                listener.termOfUseClick();
                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        lineYourName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineYourPhoneNumber.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineYourEmail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineCountry.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineYourUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineYourPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));
        lineConfirmPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_gray));

        switch (v.getId()) {
            case R.id.etYourName:
                if (hasFocus) {
                    lineYourName.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etYourPhoneNumber:
                if (hasFocus) {
                    lineYourPhoneNumber.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etYourEmail:
                if (hasFocus) {
                    lineYourEmail.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etUsername:
                if (hasFocus) {
                    lineYourUsername.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etYourPassword:
                if (hasFocus) {
                    lineYourPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
            case R.id.etConfirmPassword:
                if (hasFocus) {
                    lineConfirmPassword.setBackgroundColor(getActivity().getResources().getColor(R.color.line_focus));
                }
                break;
        }
    }

    public void setAvatarImage(Bitmap bitmap) {
        ivAvatarPlaceHolder.setVisibility(View.GONE);
        ivAvatar.setImageBitmap(bitmap);
    }
}
