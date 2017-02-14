package bhtech.com.cabbytaxi.SNSSetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.SocialNetworkObj;
import bhtech.com.cabbytaxi.object.UserObj;

public class SNSSettingController extends SlidingMenuController implements SNSSettingInterface.Listener {
    private Context context;
    private SNSSettingView view;
    private SNSSettingModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SNSSettingController.this;

        getLayoutInflater().inflate(R.layout.activity_user_snssetting_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.sns_setting));

        model = new SNSSettingModel();
        view = new SNSSettingView();

        view.setListenner(this);
        view.setDatasource(model);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, view).commit();
        }
    }

    @Override
    public void onButtonGoogleClick() {
        Intent signInIntent = SocialNetworkObj.getGoogleSignInIntent(context, this);
        startActivityForResult(signInIntent, SocialNetworkObj.GOOGLE_PLUS_SIGN_IN);
    }

    @Override
    public void onButtonFacebookClick() {
        if (NetworkObject.isNetworkConnect(context)) {
            model.settingFacebook(context, new SNSSettingModel.OnSettingFacebook() {
                @Override
                public void Success() {
                    Toast.makeText(context, getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                }
            });
        } else {
            Toast.makeText(context, getString(R.string.please_check_your_network),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonTwitterClick() {
        if (NetworkObject.isNetworkConnect(context)) {
            model.settingTwitter(context, new SNSSettingModel.OnSettingTwitter() {
                @Override
                public void Success() {
                    Toast.makeText(context, getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                }
            });
        } else {
            Toast.makeText(context, getString(R.string.please_check_your_network),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonLineClick() {

    }

    @Override
    public void onButtonBackClick() {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SocialNetworkObj.GOOGLE_PLUS_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                UserObj.getInstance().setgUserId(acct.getId());
                UserObj.getInstance().setgEmail(acct.getEmail());
                model.settingGooglePlus(context, new SNSSettingModel.OnSettingGooglePlus() {
                    @Override
                    public void Success() {
                        Toast.makeText(context, getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
