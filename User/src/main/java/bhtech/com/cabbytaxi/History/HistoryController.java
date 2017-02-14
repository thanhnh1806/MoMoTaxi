package bhtech.com.cabbytaxi.History;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;

public class HistoryController extends SlidingMenuController implements HistoryInterface.Listener {
    private Context context;
    private HistoryModel model;
    private HistoryView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = HistoryController.this;
        getLayoutInflater().inflate(R.layout.activity_history_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.history));

        model = new HistoryModel();
        view = new HistoryView();
        view.setListener(this);
        view.setDatasource(model);

        if (savedInstanceState == null) {
            addFragment(R.id.container, view);
        } else {
            //Do nothing
        }
    }

    @Override
    public void onCreateViewFinish() {
        try {
            model.setListSortBy(context);
            model.setListReceipt(context, new HistoryModel.onGetListReceipt() {
                @Override
                public void onSuccess() {
                    model.listByMonth();
                    view.reloadView();
                }

                @Override
                public void onFailure(bhtech.com.cabbytaxi.object.Error error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListSortByItemClick() {
        model.sortReceiptList();
        view.reloadView();
    }

    @Override
    public void onListMonthItemClick() {
        model.listByMonth();
        view.reloadView();
    }
}
