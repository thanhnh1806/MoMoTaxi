package bhtech.com.cabbydriver.Sales;

import java.util.Date;

import bhtech.com.cabbydriver.object.SaleReportObj;

/**
 * Created by thanh_nguyen on 04/05/2016.
 */
public class SalesInterface {
    public interface Listener {
        void onButtonBackSaleReportViewClick();

        void onButtonDaySaleReportViewClick();

        void chooseDateFinish();

        void onChooseMonthToGetReport();

        void onButtonBackBestSalesByMonthViewClick();

        void onChooseDayRangeGetReport();

        void onChooseWeekToGetReport();

        void chooseDateForRangeFinish();

        void showBestSale();
    }

    public interface Database {
        String getDriverName();

        void setDateForViewSaleReport(Date date);

        String[] getArrayMonth();

        void setPositionMonthToGetReport(int i);

        int getPositionMonthToGetReport();

        SaleReportObj getSaleReportByMonth();

        SaleReportObj getSaleReportByDayRange();

        void setPositionWeekToGetReport(int i);

        int getPositionWeekToGetReport();

        int getCurrentWeekOfYear();

        SaleReportObj getSaleReportByWeek();

        SaleReportObj getSaleReportByDay();

        Date getDateForViewReport();

        Date getDateStartForViewReport();

        void setDateStartForViewReport(Date date);

        Date getDateEndForViewReport();

        void setDateEndForViewReport(Date date);

        boolean isChooseRangeDate();

        void setChooseRangeDate(boolean chooseRangeDate);

        void setChooseDateFromWeekView(boolean b);
    }
}
