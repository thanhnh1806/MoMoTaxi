package bhtech.com.cabbytaxi.SNSSetting;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import bhtech.com.cabbytaxi.R;

public class SNSSettingView extends Fragment implements View.OnClickListener {

    private SNSSettingInterface.Listener listener;
    private SNSSettingInterface.Datasource datasource;

    public void setListenner(SNSSettingInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(SNSSettingInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private Button snssetting_btnBack;
    private ImageView btnGooglePlus, btnTwitter, btnLine;
    private LoginButton FB_LoginButton;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_user_snssetting_view, container, false);

        btnGooglePlus = (ImageView) v.findViewById(R.id.btnGooglePlus);
        btnTwitter = (ImageView) v.findViewById(R.id.btnTwitter);
        btnLine = (ImageView) v.findViewById(R.id.btnLine);
        FB_LoginButton = (LoginButton) v.findViewById(R.id.FB_LoginButton);
        snssetting_btnBack = (Button) v.findViewById(R.id.snssetting_btnBack);
        btnGooglePlus.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnLine.setOnClickListener(this);
        snssetting_btnBack.setOnClickListener(this);
        FB_LoginButton.setFragment(this);
        FB_LoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                datasource.setFacebookLoginResult(loginResult);
                listener.onButtonFacebookClick();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGooglePlus:
                listener.onButtonGoogleClick();
                break;
            case R.id.btnTwitter:
                listener.onButtonTwitterClick();
                break;
            case R.id.btnLine:
                listener.onButtonLineClick();
                break;
            case R.id.snssetting_btnBack:
                listener.onButtonBackClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
