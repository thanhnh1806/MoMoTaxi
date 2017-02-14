package bhtech.com.cabbydriver.SlidingMenu;

import android.content.Context;

import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

/**
 * Created by thanh_nguyen on 17/05/2016.
 */
public class SlidingMenuModel {
    private Context context;

    public SlidingMenuModel(Context context) {
        this.context = context;
    }

    public interface OnGetCurrentRequest {
        void Success();

        void Failure(ErrorObj error);
    }

    public void getCurrentRequest(final OnGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(ErrorObj error) {
                onFinish.Failure(error);
            }
        });
    }
}
