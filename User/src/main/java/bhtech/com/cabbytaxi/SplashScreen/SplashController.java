package bhtech.com.cabbytaxi.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import bhtech.com.cabbytaxi.Login.LoginController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.services.GetLocationService;
import bhtech.com.cabbytaxi.services.RegistrationIntentService;

public class SplashController extends AppCompatActivity {
    private Context context;
    private View splash_background;
    private FrameLayout splash_loading;
    private ImageView ivBanhXe1, ivBanhXe2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SplashController.this;
        setContentView(R.layout.activity_splash_controller);

        startService(new Intent(context, GetLocationService.class));
        startService(new Intent(context, RegistrationIntentService.class));

        splash_background = findViewById(R.id.splash_background);
        splash_loading = (FrameLayout) findViewById(R.id.splash_loading);
        ivBanhXe1 = (ImageView) findViewById(R.id.ivBanhXe1);
        ivBanhXe2 = (ImageView) findViewById(R.id.ivBanhXe2);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                showAnimation();
            }
        }, 1000);
    }

    private void showAnimation() {
        splash_background.setVisibility(View.GONE);
        splash_loading.setVisibility(View.VISIBLE);

        ivBanhXe1.setBackgroundResource(R.drawable.spin_animation);
        ivBanhXe2.setBackgroundResource(R.drawable.spin_animation);

        AnimationDrawable frameAnimation1 = (AnimationDrawable) ivBanhXe1.getBackground();
        AnimationDrawable frameAnimation2 = (AnimationDrawable) ivBanhXe2.getBackground();
        frameAnimation1.start();
        frameAnimation2.start();

        goToLoginScreen();
    }

    private void goToLoginScreen() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(context, LoginController.class));
                finish();
            }
        }, 2500);
    }
}
