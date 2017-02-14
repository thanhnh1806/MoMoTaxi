package bhtech.com.cabbydriver.Sales;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.SaleReportObj;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TimeObject;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class SaleReportByDayView extends Fragment {
    private SalesInterface.Listener listener;
    private SalesInterface.Database database;

    public void setListener(SalesInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(SalesInterface.Database database) {
        this.database = database;
    }

    FrameLayout btnBack, layoutChooseDay;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6;
    TextView tvDay, tvMoney1, tvMoney2, tvMoney3, tvMoney4, tvMoney5, tvMoney6;
    PieChartView pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_best_sales_by_day, container, false);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        layoutChooseDay = (FrameLayout) v.findViewById(R.id.layoutChooseDay);
        progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) v.findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) v.findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar) v.findViewById(R.id.progressBar5);
        progressBar6 = (ProgressBar) v.findViewById(R.id.progressBar6);

        tvDay = (TextView) v.findViewById(R.id.tvDay);
        tvMoney1 = (TextView) v.findViewById(R.id.tvMoney1);
        tvMoney2 = (TextView) v.findViewById(R.id.tvMoney2);
        tvMoney3 = (TextView) v.findViewById(R.id.tvMoney3);
        tvMoney4 = (TextView) v.findViewById(R.id.tvMoney4);
        tvMoney5 = (TextView) v.findViewById(R.id.tvMoney5);
        tvMoney6 = (TextView) v.findViewById(R.id.tvMoney6);

        tvMoney1.setSelected(true);
        tvMoney2.setSelected(true);
        tvMoney3.setSelected(true);
        tvMoney4.setSelected(true);
        tvMoney5.setSelected(true);
        tvMoney6.setSelected(true);

        pieChart = (PieChartView) v.findViewById(R.id.pieChart);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonBackBestSalesByMonthViewClick();
            }
        });

        layoutChooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.setChooseDateFromWeekView(false);
                listener.onButtonDaySaleReportViewClick();
            }
        });
        return v;
    }

    public void reloadView() {
        Date date = database.getDateForViewReport();
        tvDay.setText(date.getDate() + " " + TimeObject.getArrayMonth()[date.getMonth()]);

        SaleReportObj saleReport = database.getSaleReportByDay();

        ArrayList<SaleReportObj.Sale> salesHour0812 = new ArrayList<>();
        ArrayList<SaleReportObj.Sale> salesHour1216 = new ArrayList<>();
        ArrayList<SaleReportObj.Sale> salesHour1620 = new ArrayList<>();
        ArrayList<SaleReportObj.Sale> salesHour2024 = new ArrayList<>();
        ArrayList<SaleReportObj.Sale> salesHour2404 = new ArrayList<>();
        ArrayList<SaleReportObj.Sale> salesHour0408 = new ArrayList<>();

        for (int i = 0; i < saleReport.getSales().size(); i++) {
            if (i < 4) {
                salesHour2404.add(saleReport.getSales().get(i));
            } else if (i < 8) {
                salesHour0408.add(saleReport.getSales().get(i));
            } else if (i < 12) {
                salesHour0812.add(saleReport.getSales().get(i));
            } else if (i < 16) {
                salesHour1216.add(saleReport.getSales().get(i));
            } else if (i < 20) {
                salesHour1620.add(saleReport.getSales().get(i));
            } else {
                salesHour2024.add(saleReport.getSales().get(i));
            }
        }

        float total0812 = 0, total1216 = 0, total1620 = 0, total2024 = 0, total2404 = 0, total0408 = 0;
        for (int i = 0; i < salesHour0812.size(); i++) {
            total0812 += salesHour0812.get(i).getSale();
        }
        for (int i = 0; i < salesHour1216.size(); i++) {
            total1216 += salesHour1216.get(i).getSale();
        }
        for (int i = 0; i < salesHour1620.size(); i++) {
            total1620 += salesHour1620.get(i).getSale();
        }
        for (int i = 0; i < salesHour2024.size(); i++) {
            total2024 += salesHour2024.get(i).getSale();
        }
        for (int i = 0; i < salesHour2404.size(); i++) {
            total2404 += salesHour2404.get(i).getSale();
        }
        for (int i = 0; i < salesHour0408.size(); i++) {
            total0408 += salesHour0408.get(i).getSale();
        }

        tvMoney1.setText("$ " + StringObject.getDecimalFormat(0).format(total0812));
        tvMoney2.setText("$ " + StringObject.getDecimalFormat(0).format(total1216));
        tvMoney3.setText("$ " + StringObject.getDecimalFormat(0).format(total1620));
        tvMoney4.setText("$ " + StringObject.getDecimalFormat(0).format(total2024));
        tvMoney5.setText("$ " + StringObject.getDecimalFormat(0).format(total2404));
        tvMoney6.setText("$ " + StringObject.getDecimalFormat(0).format(total0408));

        float max = total0812;
        if (total1216 > max) {
            max = total1216;
        } else if (total1620 > max) {
            max = total1620;
        } else if (total2024 > max) {
            max = total2024;
        } else if (total2404 > max) {
            max = total2404;
        } else if (total0408 > max) {
            max = total0408;
        }
        if (max > 0) {
            progressBar1.setProgress((int) (total0812 * 100 / max));
            progressBar2.setProgress((int) (total1216 * 100 / max));
            progressBar3.setProgress((int) (total1620 * 100 / max));
            progressBar4.setProgress((int) (total2024 * 100 / max));
            progressBar5.setProgress((int) (total2404 * 100 / max));
            progressBar6.setProgress((int) (total0408 * 100 / max));
        } else {
            progressBar1.setProgress(0);
            progressBar2.setProgress(0);
            progressBar3.setProgress(0);
            progressBar4.setProgress(0);
            progressBar5.setProgress(0);
            progressBar6.setProgress(0);
        }

        List<SliceValue> sliceValues = new ArrayList<>();
        for (int i = 0; i < saleReport.getLocations().size(); i++) {
            SliceValue value = new SliceValue();
            value.setLabel(saleReport.getLocations().get(i).getName());
            value.setValue(saleReport.getLocations().get(i).getSale());
            value.setColor(ChartUtils.pickColor());
            sliceValues.add(value);
        }

        PieChartData pieChartData = new PieChartData();
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(false);
        pieChartData.setHasLabelsOutside(true);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setCenterCircleScale(0.5F);
        pieChartData.setCenterCircleColor(Color.WHITE);
        pieChartData.setValueLabelsTextColor(Color.BLACK);
        pieChartData.setValueLabelBackgroundAuto(false);
        pieChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        pieChartData.setSlicesSpacing(3);
        pieChartData.setValueLabelTextSize(13);
        pieChartData.setValues(sliceValues);

        pieChart.setPieChartData(pieChartData);
        pieChart.setValueSelectionEnabled(true);
        pieChart.setChartRotationEnabled(true);
        pieChart.setCircleFillRatio(1F);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }
}
