package bhtech.com.cabbydriver.Sales;

import android.content.Context;
import android.os.Bundle;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/6/16.
 */
public class SalesController extends SlidingMenuController implements SalesInterface.Listener {
    Context context = this;
    SalesModel model = new SalesModel(context);
    SaleReportByWeekView weekView = new SaleReportByWeekView();
    DatePickerView dateTimePickerView = new DatePickerView();
    SaleReportByDayView dayView = new SaleReportByDayView();
    SaleReportByMonthView monthView = new SaleReportByMonthView();

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setView(weekView, getString(R.string.sales));
        setView(dayView, getString(R.string.sales));
        setView(monthView, getString(R.string.sales));
        setView(dateTimePickerView, getString(R.string.sales));

        weekView.setListener(this);
        weekView.setDatabase(model);
        dateTimePickerView.setListener(this);
        dateTimePickerView.setDatabase(model);
        dayView.setListener(this);
        dayView.setDatabase(model);
        monthView.setListener(this);
        monthView.setDatabase(model);

        hideAllFragment();
        showFragment(weekView);

        onChooseWeekToGetReport();
    }

    private void hideAllFragment() {
        hideFragment(weekView);
        hideFragment(monthView);
        hideFragment(dateTimePickerView);
        hideFragment(dayView);
    }

    @Override
    public void onButtonBackSaleReportViewClick() {
        onBackPressed();
    }

    @Override
    public void onButtonDaySaleReportViewClick() {
        dateTimePickerView.showDateTimePicker();
    }

    @Override
    public void chooseDateFinish() {
        if (!model.isChooseDateFromWeekView()) {
            model.getReportByDate(new SalesModel.onGetReportByDate() {
                @Override
                public void Success() {
                    hideAllFragment();
                    showFragment(dayView);
                    dayView.reloadView();
                }

                @Override
                public void Failure(ErrorObj error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        }
    }

    @Override
    public void onChooseMonthToGetReport() {
        model.getReportByMonth(new SalesModel.onGetReportByMonth() {
            @Override
            public void Success() {
                model.getReportByDayRange(new SalesModel.onGetReportByDayRange() {
                    @Override
                    public void Success() {
                        hideAllFragment();
                        showFragment(monthView);
                        monthView.reloadView();
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonBackBestSalesByMonthViewClick() {
        hideAllFragment();
        showFragment(weekView);
    }

    @Override
    public void onChooseDayRangeGetReport() {
        model.getReportByDayRange(new SalesModel.onGetReportByDayRange() {
            @Override
            public void Success() {
                monthView.reloadView();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onChooseWeekToGetReport() {
        model.getReportByWeek(new SalesModel.onGetReportByWeek() {
            @Override
            public void Success() {
                weekView.reloadView();
            }

            @Override
            public void Failure(ErrorObj error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void chooseDateForRangeFinish() {
        if (model.getDateEndForViewReport() == null) {
            dateTimePickerView.showDateTimePicker();
        } else {
            if (model.sortDateRange()) {
                model.getReportByDayRange(context, new SalesModel.onGetReportByDayRange() {
                    @Override
                    public void Success() {
                        weekView.reloadView();
                    }

                    @Override
                    public void Failure(ErrorObj error) {

                    }
                });
            } else {
                showAlertDialog(getString(R.string.from_date_must_be_different_than_to_date)).show();
            }
        }
    }

    @Override
    public void showBestSale() {
        if (model.getDateForViewReport() != null) {
            model.setChooseDateFromWeekView(false);
            chooseDateFinish();
        } else if (model.getDateStartForViewReport() != null && model.getDateEndForViewReport() != null) {
            chooseDateForRangeFinish();
        } else if (model.getPositionMonthToGetReport() != -1) {
            onChooseMonthToGetReport();
        } else if (model.getPositionWeekToGetReport() != -1) {
            onChooseWeekToGetReport();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
