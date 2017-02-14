package bhtech.com.cabbytaxi.Register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbytaxi.Login.LoginController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.Company.CompanySettingController;
import bhtech.com.cabbytaxi.Register.Personal.ProfileSettingController;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.ContantValuesObject;

public class RegisterController extends SlidingMenuController implements RegisterInterface.Listener {
    private Context context;
    private RegisterChooseProfileView chooseProfileView;
    private RegisterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_register_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.profile_setting));
        context = RegisterController.this;
        model = new RegisterModel(context);

        chooseProfileView = new RegisterChooseProfileView();
        chooseProfileView.setListener(this);
        chooseProfileView.setDatasource(model);

        if (savedInstanceState == null) {
            addFragment(R.id.register_container, chooseProfileView);
        }
    }

    @Override
    public void onCreateViewFinish() {
        chooseProfileView.reloadView();
    }

    @Override
    public void onForPersonalClick() {
        chooseProfileView.reloadView();
    }

    @Override
    public void onForCompanyClick() {
        chooseProfileView.reloadView();
    }

    @Override
    public void onChooseProfileNextClick() {
        if (model.getUserChooseProfile() == ContantValuesObject.RegisterForPersonal) {
            startActivity(new Intent(context, ProfileSettingController.class));
        } else if (model.getUserChooseProfile() == ContantValuesObject.RegisterForCompany) {
            startActivity(new Intent(context, CompanySettingController.class));
        }
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, LoginController.class));
        finish();
    }
}
