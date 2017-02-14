package bhtech.com.cabbydriver.AboutUs;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;

/**
 * Created by duongpv on 4/6/16.
 */
public class AboutCabbyController extends SlidingMenuController {

    AboutCabbyView view = new AboutCabbyView();

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        view.setContext(context);
        setView(view, "About Cabby");
    }
}
