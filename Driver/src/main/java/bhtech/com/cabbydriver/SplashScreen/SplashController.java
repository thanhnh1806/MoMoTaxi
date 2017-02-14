package bhtech.com.cabbydriver.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.EnterPinCode.EnterCodeController;
import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.services.GetLocationService;
import bhtech.com.cabbydriver.services.RegistrationIntentService;

public class SplashController extends BaseActivity {
    private DriverSplashView splashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_splash_controller);
        splashView = new DriverSplashView();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.driver_splash_container, splashView).commit();
        } else {
            //Do nothing
        }

        startService(new Intent(this, RegistrationIntentService.class));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashController.this, EnterCodeController.class));
                finishActivity();
            }
        }, 3000);
    }
}
