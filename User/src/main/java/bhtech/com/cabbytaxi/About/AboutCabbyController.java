package bhtech.com.cabbytaxi.About;

import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.ContantValuesObject;

public class AboutCabbyController extends SlidingMenuController {
    private AboutCabbyModel model;
    private AboutCabbyView personalView;
    private AboutCabbyCompanyView companyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about_cabby_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.about_cabby));

        model = new AboutCabbyModel();
        personalView = new AboutCabbyView();
        companyView = new AboutCabbyCompanyView();

        if (savedInstanceState == null) {
            addFragment(R.id.container, personalView);
            addFragment(R.id.container, companyView);
            hideAllFragment();
        }
        if (model.typeUserLogin() == ContantValuesObject.RegisterForPersonal) {
            showFragment(personalView);
        } else {
            showFragment(companyView);
        }
    }

    private void hideAllFragment() {
        hideFragment(personalView);
        hideFragment(companyView);
    }
}
