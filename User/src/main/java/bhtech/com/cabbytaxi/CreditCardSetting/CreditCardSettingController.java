package bhtech.com.cabbytaxi.CreditCardSetting;

import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;

public class CreditCardSettingController extends SlidingMenuController implements CreditCardInterface.Listener {
    private CreditCardSettingView view = new CreditCardSettingView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_credit_card_setting_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.public_cab_locations));

        view.setListener(this);

        if (savedInstanceState == null) {
            addFragment(R.id.container, view);
        } else {
            //Do nothing
        }
    }

    @Override
    public void onButtonOkClick() {
        onBackPressed();
    }
}
