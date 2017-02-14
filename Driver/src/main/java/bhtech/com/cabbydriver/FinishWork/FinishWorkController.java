package bhtech.com.cabbydriver.FinishWork;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.ThankYouDriver.ThankYouDriverController;
import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/5/16.
 */
public class FinishWorkController extends SlidingMenuController implements FinishWorkInterface.Delegate {
    FinishWorkView view;
    Context context = this;
    FinishWorkModel model = new FinishWorkModel(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new FinishWorkView(context);
        super.setView(view, getResources().getString(R.string.finish_work_title));
        view.setDataSource(model);
        view.setDelegate(this);

        model.getTodayResult(this, new FinishWorkModel.GetTodayResultFinish() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.get_result_failed_msg + " " + errorObj.errorMessage);
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });
    }

    @Override
    public void finishButtonOnClick(String mileage) {
        Float mileageFloat;
        try {
            mileageFloat = Float.parseFloat(mileage);
        } catch (Exception e) {
            mileageFloat = Float.valueOf(0);
        }
        model.carDriverObj.setMileage(mileageFloat);
        model.finishWorkAndLogOut(this, new FinishWorkModel.FinishWorkModelInterface() {
            @Override
            public void success() {
                startActivity(new Intent(context, ThankYouDriverController.class));
                finishActivity();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
