package bhtech.com.cabbydriver.CarStatus;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/6/16.
 */
public class CarStatusController extends SlidingMenuController {
    Context context = this;
    CarStatusModel model = new CarStatusModel(this.context);
    CarStatusView view;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        view = new CarStatusView();
        view.setContext(context);
        super.setView(view, getResources().getString(R.string.car_status));
        view.setDataSource(model);

        model.getCarStatus(new CarStatusModel.CarStatusModelGetCarStatusFinish() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
            }
        });
    }
}
