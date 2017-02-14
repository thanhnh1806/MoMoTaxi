package bhtech.com.cabbydriver.TripHistory;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by duongpv on 4/6/16.
 */
public class TripHistoryController extends SlidingMenuController implements TripHistoryInterface.Delegate {
    private Context context = this;
    private TripHistoryModel model;
    private TripHistoryView view;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        model = new TripHistoryModel(context);
        view = new TripHistoryView();
        setView(view, getString(R.string.trip_history_title));

        view.setDataSource(model);
        view.setDelegate(this);

        model.setMonth(TimeObject.getCurrentMonth());
        model.getListOfHistory(new TripHistoryModel.OnGetListOfTripHistoryFinish() {
            @Override
            public void success() {
                model.sortByDate();
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {

            }
        });
    }

    @Override
    public void groupOnClickAtPosition(int groupPosition) {

    }

    @Override
    public void groupExpandedAtPosition(int groupPosition) {
        model.setGroupExpandAtPosition(groupPosition);
        if (model.getGroupNeedCollapsedPosition() != groupPosition && model.getGroupNeedCollapsedPosition() != -1) {
            view.collapseGroupAtPosition(model.getGroupNeedCollapsedPosition());
//            model.clearGroupNeedToCollapse();
        } else {
            //  Do nothing
            model.clearGroupNeedToCollapse();
        }
        view.reloadView();
    }

    @Override
    public void groupCollapseAtPosition(int groupPosition) {
        if (model.getExpandedGroupPosition() == groupPosition) {
            model.clearGroupNeedToExpand();
        }
        view.reloadView();
    }

    @Override
    public void sortByDate() {
        model.sortByDate();
        view.reloadView();
    }

    @Override
    public void sortByCost() {
        model.sortByCost();
        view.reloadView();
    }

    @Override
    public void filterByMonth(int position) {
        model.setMonth(position);
        model.getListOfHistory(new TripHistoryModel.OnGetListOfTripHistoryFinish() {
            @Override
            public void success() {
                model.sortByDate();
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {

            }
        });
    }

    @Override
    public void filterByDay(int position) {
        model.setDay(position);
        model.getListOfHistory(new TripHistoryModel.OnGetListOfTripHistoryFinish() {
            @Override
            public void success() {
                model.sortByDate();
                view.reloadView();
            }

            @Override
            public void failure(ErrorObj errorObj) {

            }
        });
    }
}
