package bhtech.com.cabbydriver.SplashScreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class DriverSplashView extends Fragment implements Animation.AnimationListener {

    public DriverSplashView() {
    }


    private ImageView ivLoading;
    private TextView tvHere;
    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_splash_view, container, false);
        tvHere = (TextView) v.findViewById(R.id.tvHere);
        ivLoading = (ImageView) v.findViewById(R.id.ivLoading);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        animation.setAnimationListener(this);
        ivLoading.startAnimation(animation);
        return v;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        tvHere.setVisibility(View.VISIBLE);
        ivLoading.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
