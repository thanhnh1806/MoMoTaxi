package bhtech.com.cabbytaxi.MyProfile;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.services.NetworkServices;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileView extends Fragment implements View.OnClickListener, TextWatcher {
    private MyProfileInterface.Listener listener;
    private MyProfileInterface.Database database;

    public void setListener(MyProfileInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(MyProfileInterface.Database database) {
        this.database = database;
    }

    private FrameLayout btnLogOut, btnDelete, layoutAvatar, btnEditScreenName, btnEditPhoneNumber;
    private FrameLayout btnEditEmail, btnEditCountry, btnEditUsername, btnEditPassword;
    private FrameLayout ivFacebook, ivGooglePlus, ivTwitter;
    private EditText etUserScreenName, etYourPassword, etPhoneNumber, etEmail, etCountry, etUserName;
    private TextView tvUserScreenName, tvPhoneNumber, tvEmail, tvCountry;
    private TextView tvUserName, tvYourPassword, btnSave;
    private ImageView btnBack, ivAvatarPlaceHolder;
    private CircleImageView ivAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile_view, container, false);
        btnLogOut = (FrameLayout) v.findViewById(R.id.btnLogOut);
        btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);
        layoutAvatar = (FrameLayout) v.findViewById(R.id.layoutAvatar);
        ivAvatar = (CircleImageView) v.findViewById(R.id.ivAvatar);
        ivAvatarPlaceHolder = (ImageView) v.findViewById(R.id.ivAvatarPlaceHolder);
        btnEditScreenName = (FrameLayout) v.findViewById(R.id.btnEditScreenName);
        btnEditPhoneNumber = (FrameLayout) v.findViewById(R.id.btnEditPhoneNumber);
        btnEditEmail = (FrameLayout) v.findViewById(R.id.btnEditEmail);
        btnEditCountry = (FrameLayout) v.findViewById(R.id.btnEditCountry);
        btnEditUsername = (FrameLayout) v.findViewById(R.id.btnEditUsername);
        btnEditPassword = (FrameLayout) v.findViewById(R.id.btnEditPassword);

        ivFacebook = (FrameLayout) v.findViewById(R.id.ivFacebook);
        ivGooglePlus = (FrameLayout) v.findViewById(R.id.ivGooglePlus);
        ivTwitter = (FrameLayout) v.findViewById(R.id.ivTwitter);

        etUserScreenName = (EditText) v.findViewById(R.id.etUserScreenName);
        etYourPassword = (EditText) v.findViewById(R.id.etYourPassword);
        etPhoneNumber = (EditText) v.findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etCountry = (EditText) v.findViewById(R.id.etCountry);
        etUserName = (EditText) v.findViewById(R.id.etUserName);

        etUserScreenName.addTextChangedListener(this);
        etYourPassword.addTextChangedListener(this);
        etPhoneNumber.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etCountry.addTextChangedListener(this);
        etUserName.addTextChangedListener(this);

        tvUserScreenName = (TextView) v.findViewById(R.id.tvUserScreenName);
        tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhoneNumber);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        tvCountry = (TextView) v.findViewById(R.id.tvCountry);
        tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        tvYourPassword = (TextView) v.findViewById(R.id.tvYourPassword);

        btnSave = (TextView) v.findViewById(R.id.btnSave);
        btnBack = (ImageView) v.findViewById(R.id.btnBack);

        btnLogOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        layoutAvatar.setOnClickListener(this);
        btnEditScreenName.setOnClickListener(this);
        btnEditPhoneNumber.setOnClickListener(this);
        btnEditEmail.setOnClickListener(this);
        btnEditCountry.setOnClickListener(this);
        btnEditUsername.setOnClickListener(this);
        btnEditPassword.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return v;
    }

    public void reloadView() {
        if (database.getUserPhoto() != null) {
            NetworkServices.imageRequest(getActivity(), ContantValuesObject.DOMAIN_IMAGE + database.getUserPhoto(),
                    new NetworkServices.MakeImageRequestFinish() {
                        @Override
                        public void Success(Bitmap bitmap) {
                            ivAvatar.setVisibility(View.VISIBLE);
                            ivAvatarPlaceHolder.setVisibility(View.GONE);
                            ivAvatar.setImageBitmap(bitmap);
                        }

                        @Override
                        public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                            Log.e("Load_Avatar", error.errorMessage);
                            ivAvatar.setVisibility(View.GONE);
                            ivAvatarPlaceHolder.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            ivAvatar.setVisibility(View.GONE);
            ivAvatarPlaceHolder.setVisibility(View.VISIBLE);
        }

        etUserScreenName.setText(String.valueOf(database.getScreenName()));
        //etYourPassword.setText(String.valueOf(database.getPassword()));
        etPhoneNumber.setText(String.valueOf(database.getPhoneNumber()));
        etEmail.setText(String.valueOf(database.getEmail()));
        etCountry.setText(String.valueOf(database.getCountry()));

        tvUserScreenName.setText(String.valueOf(database.getScreenName()));
        tvPhoneNumber.setText(String.valueOf(database.getPhoneNumber()));
        tvEmail.setText(String.valueOf(database.getEmail()));
        tvCountry.setText(String.valueOf(database.getCountry()));
        tvUserName.setText(String.valueOf(database.getUsername()));
        tvYourPassword.setText(String.valueOf(database.getPassword()));

        etUserScreenName.setVisibility(View.GONE);
        etYourPassword.setVisibility(View.GONE);
        etPhoneNumber.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        etCountry.setVisibility(View.GONE);

        tvUserScreenName.setVisibility(View.VISIBLE);
        tvPhoneNumber.setVisibility(View.VISIBLE);
        tvEmail.setVisibility(View.VISIBLE);
        tvCountry.setVisibility(View.VISIBLE);
        tvYourPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onCreateViewFinish();
    }

    @Override
    public void onClick(View v) {
        btnSave.setEnabled(true);
        btnSave.setTextColor(getResources().getColor(android.R.color.black));

        switch (v.getId()) {
            case R.id.btnLogOut:
                listener.onButtonLogoutClick();
                break;
            case R.id.btnDelete:
                listener.onButtonDeleteClick();
                break;
            case R.id.layoutAvatar:
                listener.onAvatarClick();
                break;
            case R.id.btnEditScreenName:
                listener.onButtonEditScreenNameClick();
                tvUserScreenName.setVisibility(View.GONE);
                etUserScreenName.setVisibility(View.VISIBLE);
                etUserScreenName.requestFocus();
                break;
            case R.id.btnEditPhoneNumber:
                listener.onButtonEditPhoneNumberClick();
                tvPhoneNumber.setVisibility(View.GONE);
                etPhoneNumber.setVisibility(View.VISIBLE);
                etPhoneNumber.requestFocus();
                break;
            case R.id.btnEditEmail:
                listener.onButtonEditEmailClick();
                tvEmail.setVisibility(View.GONE);
                etEmail.setVisibility(View.VISIBLE);
                etEmail.requestFocus();
                break;
            case R.id.btnEditCountry:
                listener.onButtonEditCountryClick();
                tvCountry.setVisibility(View.GONE);
                etCountry.setVisibility(View.VISIBLE);
                etCountry.requestFocus();
                break;
            case R.id.btnEditUsername:
                listener.onButtonEditUsernameClick();
                etUserName.setVisibility(View.VISIBLE);
                tvUserName.setVisibility(View.GONE);
                etUserName.requestFocus();
                break;
            case R.id.btnEditPassword:
                listener.onButtonEditPasswordClick();
                database.userChangePassword(true);
                etYourPassword.setVisibility(View.VISIBLE);
                etYourPassword.setText(null);
                tvYourPassword.setVisibility(View.GONE);
                etYourPassword.requestFocus();
                break;
            case R.id.ivFacebook:
                listener.onButtonFacebookClick();
                break;
            case R.id.ivGooglePlus:
                listener.onButtonGooglePlusClick();
                break;
            case R.id.ivTwitter:
                listener.onButtonTwitterClick();
                break;
            case R.id.btnSave:
                database.setScreenName(String.valueOf(etUserScreenName.getText()));
                database.setPhoneNumber(String.valueOf(etPhoneNumber.getText()));
                database.setEmail(String.valueOf(etEmail.getText()));
                database.setPassword(String.valueOf(etYourPassword.getText()));
                database.setCountry(String.valueOf(etCountry.getText()));
                database.setUsername(String.valueOf(etUserName.getText()));
                listener.onButtonSaveClick();
                break;
            case R.id.btnBack:
                listener.onButtonBackClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }

    public void setAvatarImage(Bitmap bitmap) {
        ivAvatar.setImageBitmap(bitmap);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == etUserScreenName.getEditableText()) {
            database.setScreenName(String.valueOf(etUserScreenName.getText()));
        } else if (s == etPhoneNumber.getEditableText()) {
            database.setPhoneNumber(String.valueOf(etPhoneNumber.getText()));
        } else if (s == etEmail.getEditableText()) {
            database.setEmail(String.valueOf(etEmail.getText()));
        } else if (s == etYourPassword.getEditableText()) {
            database.setPassword(String.valueOf(etYourPassword.getText()));
        } else if (s == etCountry.getEditableText()) {
            database.setCountry(String.valueOf(etCountry.getText()));
        } else if (s == etUserName.getEditableText()) {
            database.setUsername(String.valueOf(etUserName.getText()));
        }
    }
}
