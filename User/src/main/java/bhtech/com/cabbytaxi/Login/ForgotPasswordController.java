package bhtech.com.cabbytaxi.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.BaseActivity;

public class ForgotPasswordController extends BaseActivity {
    Context context;
    EditText etForgotPassword;
    Button btnSendNewPassword;
    TextView tvEmail;
    FrameLayout tvBackToLogin;
    LinearLayout layout_send, layout_success;
    ForgotPasswordModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        model = new ForgotPasswordModel(context);

        setContentView(R.layout.activity_forgot_password_controller);
        layout_send = (LinearLayout) findViewById(R.id.layout_send);
        layout_success = (LinearLayout) findViewById(R.id.layout_success);
        etForgotPassword = (EditText) findViewById(R.id.etForgotPassword);
        btnSendNewPassword = (Button) findViewById(R.id.btnSendNewPassword);
        tvBackToLogin = (FrameLayout) findViewById(R.id.tvBackToLogin);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        btnSendNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.email = String.valueOf(etForgotPassword.getText());

                model.forgotPassword(new ForgotPasswordModel.OnForgotPassword() {
                    @Override
                    public void Success() {
                        String d = context.getString(R.string.a_new_password_has_been_send_to_your_email_address);
                        String html1 = "<font color='#ba3707'>";
                        String html2 = "</font>";
                        tvEmail.setText(Html.fromHtml(d + " " + html1 + model.email + html2));
                        layout_send.setVisibility(View.INVISIBLE);
                        layout_success.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                        Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, LoginController.class));
        finishActivity();
    }
}
