package bhtech.com.cabbytaxi.DeveloperInformation;

import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;

/**
 * Created by duongpv on 4/6/16.
 */
public class DeveloperInformationController extends SlidingMenuController {
    private DeveloperInformationView view = new DeveloperInformationView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_developer_information_controller);
        updateToolbarTitle(getString(R.string.dev_info_title));

        if (savedInstanceBundle == null) {
            addFragment(R.id.container, view);
        } else {
            //Do nothing
        }
    }
}
