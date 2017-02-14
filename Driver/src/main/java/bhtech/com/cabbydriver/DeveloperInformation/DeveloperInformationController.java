package bhtech.com.cabbydriver.DeveloperInformation;

import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;

/**
 * Created by duongpv on 4/6/16.
 */
public class DeveloperInformationController extends SlidingMenuController {
    DeveloperInformationView view;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        view = new DeveloperInformationView();
        setView(view, getResources().getString(R.string.dev_info_title));
    }
}
