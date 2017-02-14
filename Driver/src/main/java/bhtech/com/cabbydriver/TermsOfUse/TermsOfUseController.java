package bhtech.com.cabbydriver.TermsOfUse;

import android.os.Bundle;

import bhtech.com.cabbydriver.BaseActivity;
import bhtech.com.cabbydriver.R;

/**
 * Created by duongpv on 4/6/16.
 */
public class TermsOfUseController extends BaseActivity implements TermOfUseInterface {
    TermOfUseView view = new TermOfUseView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        view.setListener(this);
        if (savedInstanceBundle == null) {
            getFragmentManager().beginTransaction().add(R.id.base_activity_container, view).commit();
        }
    }

    @Override
    public void OnButtonOkTermOfUseClick() {
        finish();
    }
}
