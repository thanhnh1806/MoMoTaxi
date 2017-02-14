package bhtech.com.cabbydriver.ThankYouDriver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.EnterPinCode.EnterCodeController;
import bhtech.com.cabbydriver.R;

/**
 * Created by duongpv on 4/7/16.
 */
public class ThankYouDriverController extends BaseActivity implements ThankYouDriverView.Delegate {
    Context context = this;
    ThankYouDriverView view = new ThankYouDriverView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view.setContext(context);
        view.setDelegate(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.base_activity_container, view).commit();
        }
    }

    @Override
    public void frameThankYouOnClick () {
        Intent intent = new Intent(getApplicationContext(), EnterCodeController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
