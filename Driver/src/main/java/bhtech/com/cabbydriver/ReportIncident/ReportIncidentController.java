package bhtech.com.cabbydriver.ReportIncident;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/6/16.
 */
public class ReportIncidentController extends SlidingMenuController implements ReportIncidentInterface.Listener {
    private Context context = this;
    private ReportIncidentModel model = new ReportIncidentModel(context);
    private ReportIncidentView view = new ReportIncidentView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setView(view, getString(R.string.map_view));
        view.setListener(this);
        view.setDatasource(model);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMapReady() {
        model.getCurrentAddress(new ReportIncidentModel.OnGetCurrentAddress() {
            @Override
            public void success() {
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void sendReportIncident() {
        model.sendReportIncident(new ReportIncidentModel.OnSendReportIncident() {
            @Override
            public void success() {
                showAlertDialog(getString(R.string.send_report_incident_successfuly)).show();
            }

            @Override
            public void failure(ErrorObj errorObj) {
                showAlertDialog(errorObj.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void onButtonCallCompanyClick() {
        //TODO
    }

    @Override
    public void onButtonCallPoliceClick() {
        //TODO
    }
}
