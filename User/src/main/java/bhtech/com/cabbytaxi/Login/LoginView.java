package bhtech.com.cabbytaxi.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import bhtech.com.cabbytaxi.R;

public class LoginView extends Fragment implements View.OnClickListener {

    private LoginInterface.Listener listener;
    private LoginInterface.Datasource datasource;

    public void setListener(LoginInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(LoginInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private EditText etUsername, etPassword;
    private TextView tvForgotPassword;
    private Button btnLogin;
    private LinearLayout cbRememberMe;
    private ImageView ivUnCheckBox;
    private FrameLayout tvNewUser, tvSignUp, ivFacebook, ivTwitter, ivGooglePlus;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_login_view, container, false);
        etUsername = (EditText) v.findViewById(R.id.etUsername);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        tvNewUser = (FrameLayout) v.findViewById(R.id.tvNewUser);
        tvSignUp = (FrameLayout) v.findViewById(R.id.tvSignUp);
        btnLogin = (Button) v.findViewById(R.id.btnLogin);
        cbRememberMe = (LinearLayout) v.findViewById(R.id.cbRememberMe);
        ivUnCheckBox = (ImageView) v.findViewById(R.id.ivUnCheckBox);
        tvForgotPassword = (TextView) v.findViewById(R.id.tvForgotPassword);
        ivFacebook = (FrameLayout) v.findViewById(R.id.ivFacebook);
        ivTwitter = (FrameLayout) v.findViewById(R.id.ivTwitter);
        ivGooglePlus = (FrameLayout) v.findViewById(R.id.ivGooglePlus);

        tvNewUser.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        cbRememberMe.setOnClickListener(this);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onButtonLoginClick();
                    return true;
                }
                return false;
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                datasource.setFacebookLoginResult(loginResult);
                listener.onButtonLoginFacebookClick();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        SpannableString content = new SpannableString(getResources().getString(R.string.forgot_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvForgotPassword.setText(content);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                onButtonLoginClick();
                break;
            case R.id.cbRememberMe:
                if (ivUnCheckBox.isShown()) {
                    ivUnCheckBox.setVisibility(View.GONE);
                } else {
                    ivUnCheckBox.setVisibility(View.VISIBLE);
                }
                datasource.setRememberMe(getActivity(), ivUnCheckBox.isShown());
                break;
            case R.id.tvForgotPassword:
                listener.onForgotPasswordClick();
                break;
            case R.id.ivFacebook:
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("public_profile", "user_friends"));
                break;
            case R.id.ivTwitter:
                listener.onButtonLoginTwitterClick();
                break;
            case R.id.ivGooglePlus:
                listener.onButtonLoginGooglePlusClick();
                break;
            case R.id.tvSignUp:
                listener.onButtonSignUpClick();
                break;
            case R.id.tvNewUser:
                listener.onButtonSignUpClick();
                break;
        }
    }

    private void onButtonLoginClick() {
        datasource.setUsername(String.valueOf(etUsername.getText()));
        datasource.setPassword(String.valueOf(etPassword.getText()));
        listener.onButtonLoginClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
