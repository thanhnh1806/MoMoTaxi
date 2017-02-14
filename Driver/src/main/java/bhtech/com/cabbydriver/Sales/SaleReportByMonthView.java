package bhtech.com.cabbydriver.Sales;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.SaleReportObj;
import bhtech.com.cabbydriver.object.TimeObject;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class SaleReportByMonthView extends Fragment implements View.OnClickListener {
    private Context context;
    private SalesInterface.Listener listener;
    private SalesInterface.Database database;

    public void setListener(SalesInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(SalesInterface.Database database) {
        this.database = database;
    }

    private FrameLayout btnBack, layoutChooseMonth;
    private AutoCompleteTextView autoCompleteMonth;
    private TextView tvMonth, tvWeek1, tvWeek2, tvWeek3, tvWeek4, tvTotalMoney1, tvTotalMoney2,
            tvTotalMoney3, tvTotalMoney4, tvDayRange, tvDay1, tvDay2, tvDay3, tvDay4, tvDay5,
            tvDay6, tvDay7, tvDay8, tvThu1, tvThu2, tvThu3, tvThu4, tvThu5, tvThu6, tvThu7, tvThu8;
    private View layoutProgressBar1, layoutProgressBar2, layoutProgressBar3, layoutProgressBar4;
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    private LineChartView lineChartView;
    private LinearLayout dayOfWeek1, dayOfWeek2, dayOfWeek3, dayOfWeek4, dayOfWeek5, dayOfWeek6,
            dayOfWeek7, dayOfWeek8, layoutLineChart;
    private PieChartView pieChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_sales_view, container, false);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        layoutChooseMonth = (FrameLayout) v.findViewById(R.id.layoutChooseMonth);
        autoCompleteMonth = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteMonth);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);
        tvWeek1 = (TextView) v.findViewById(R.id.tvWeek1);
        tvWeek2 = (TextView) v.findViewById(R.id.tvWeek2);
        tvWeek3 = (TextView) v.findViewById(R.id.tvWeek3);
        tvWeek4 = (TextView) v.findViewById(R.id.tvWeek4);
        tvTotalMoney1 = (TextView) v.findViewById(R.id.tvTotalMoney1);
        tvTotalMoney2 = (TextView) v.findViewById(R.id.tvTotalMoney2);
        tvTotalMoney3 = (TextView) v.findViewById(R.id.tvTotalMoney3);
        tvTotalMoney4 = (TextView) v.findViewById(R.id.tvTotalMoney4);

        tvTotalMoney1.setSelected(true);
        tvTotalMoney2.setSelected(true);
        tvTotalMoney3.setSelected(true);
        tvTotalMoney4.setSelected(true);

        layoutProgressBar1 = v.findViewById(R.id.layoutProgressBar1);
        layoutProgressBar2 = v.findViewById(R.id.layoutProgressBar2);
        layoutProgressBar3 = v.findViewById(R.id.layoutProgressBar3);
        layoutProgressBar4 = v.findViewById(R.id.layoutProgressBar4);

        progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) v.findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) v.findViewById(R.id.progressBar4);

        tvDayRange = (TextView) v.findViewById(R.id.tvDayRange);
        lineChartView = (LineChartView) v.findViewById(R.id.lineChart);
        tvDay1 = (TextView) v.findViewById(R.id.tvDay1);
        tvDay2 = (TextView) v.findViewById(R.id.tvDay2);
        tvDay3 = (TextView) v.findViewById(R.id.tvDay3);
        tvDay4 = (TextView) v.findViewById(R.id.tvDay4);
        tvDay5 = (TextView) v.findViewById(R.id.tvDay5);
        tvDay6 = (TextView) v.findViewById(R.id.tvDay6);
        tvDay7 = (TextView) v.findViewById(R.id.tvDay7);
        tvDay8 = (TextView) v.findViewById(R.id.tvDay8);
        tvThu1 = (TextView) v.findViewById(R.id.tvThu1);
        tvThu2 = (TextView) v.findViewById(R.id.tvThu2);
        tvThu3 = (TextView) v.findViewById(R.id.tvThu3);
        tvThu4 = (TextView) v.findViewById(R.id.tvThu4);
        tvThu5 = (TextView) v.findViewById(R.id.tvThu5);
        tvThu6 = (TextView) v.findViewById(R.id.tvThu6);
        tvThu7 = (TextView) v.findViewById(R.id.tvThu7);
        tvThu8 = (TextView) v.findViewById(R.id.tvThu8);

        dayOfWeek1 = (LinearLayout) v.findViewById(R.id.dayOfWeek1);
        dayOfWeek2 = (LinearLayout) v.findViewById(R.id.dayOfWeek2);
        dayOfWeek3 = (LinearLayout) v.findViewById(R.id.dayOfWeek3);
        dayOfWeek4 = (LinearLayout) v.findViewById(R.id.dayOfWeek4);
        dayOfWeek5 = (LinearLayout) v.findViewById(R.id.dayOfWeek5);
        dayOfWeek6 = (LinearLayout) v.findViewById(R.id.dayOfWeek6);
        dayOfWeek7 = (LinearLayout) v.findViewById(R.id.dayOfWeek7);
        dayOfWeek8 = (LinearLayout) v.findViewById(R.id.dayOfWeek8);
        layoutLineChart = (LinearLayout) v.findViewById(R.id.layoutLineChart);

        pieChartView = (PieChartView) v.findViewById(R.id.pieChart);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, database.getArrayMonth());
        autoCompleteMonth.setAdapter(adapter);
        autoCompleteMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvMonth.setText(database.getArrayMonth()[position]);
                tvWeek1.setText("01 - 07 " + database.getArrayMonth()[position]);
                tvWeek2.setText("08 - 15 " + database.getArrayMonth()[position]);
                tvWeek3.setText("16 - 23 " + database.getArrayMonth()[position]);

                if (position == 1) {
                    if (TimeObject.isLeapYear(TimeObject.getCurrentYear())) {
                        tvWeek4.setText("24 - 29 " + database.getArrayMonth()[position]);
                    } else {
                        tvWeek4.setText("24 - 28 " + database.getArrayMonth()[position]);
                    }
                } else if (TimeObject.monthHas31Days(position + 1)) {
                    tvWeek4.setText("24 - 31 " + database.getArrayMonth()[position]);
                } else {
                    tvWeek4.setText("24 - 30 " + database.getArrayMonth()[position]);
                }
                tvDayRange.setText(tvWeek1.getText());
                database.setPositionMonthToGetReport(position);
                database.setPositionWeekToGetReport(1);
                listener.onChooseMonthToGetReport();
            }
        });

        btnBack.setOnClickListener(this);
        layoutChooseMonth.setOnClickListener(this);
        layoutProgressBar1.setOnClickListener(this);
        layoutProgressBar2.setOnClickListener(this);
        layoutProgressBar3.setOnClickListener(this);
        layoutProgressBar4.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMonth.setText(database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek1.setText("01 - 07 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek2.setText("08 - 15 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek3.setText("16 - 23 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);

        if (TimeObject.monthHas31Days(database.getPositionMonthToGetReport() + 1)) {
            tvWeek4.setText("24 - 31 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        } else {
            tvWeek4.setText("24 - 30 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        }
        tvDayRange.setText(tvWeek1.getText());
        getDayOfWeek();
    }

    private void getDayOfWeek() {
        tvThu1.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay1.getText().toString())));
        tvThu2.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay2.getText().toString())));
        tvThu3.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay3.getText().toString())));
        tvThu4.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay4.getText().toString())));
        tvThu5.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay5.getText().toString())));
        tvThu6.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay6.getText().toString())));
        tvThu7.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay7.getText().toString())));
        tvThu8.setText(TimeObject.getStringDayOfWeek(TimeObject.getCurrentYear(),
                database.getPositionMonthToGetReport(), Integer.parseInt(tvDay8.getText().toString())));
    }

    public void reloadView() {
        tvMonth.setText(database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek1.setText("01 - 07 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek2.setText("08 - 15 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        tvWeek3.setText("16 - 23 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);

        if (database.getPositionMonthToGetReport() == 1) {
            if (TimeObject.isLeapYear(TimeObject.getCurrentYear())) {
                tvWeek4.setText("24 - 29 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
            } else {
                tvWeek4.setText("24 - 28 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
            }
        } else if (TimeObject.monthHas31Days(database.getPositionMonthToGetReport() + 1)) {
            tvWeek4.setText("24 - 31 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        } else {
            tvWeek4.setText("24 - 30 " + database.getArrayMonth()[database.getPositionMonthToGetReport()]);
        }

        switch (database.getPositionWeekToGetReport()) {
            case 1:
                tvDayRange.setText(tvWeek1.getText());
                break;
            case 2:
                tvDayRange.setText(tvWeek2.getText());
                break;
            case 3:
                tvDayRange.setText(tvWeek3.getText());
                break;
            case 4:
                tvDayRange.setText(tvWeek4.getText());
                break;
        }

        reloadProgressBar();
        reloadLineChart();
        reloadPieChart();
    }

    private void reloadProgressBar() {
        SaleReportObj saleReport = database.getSaleReportByMonth();
        int week0107 = 0;
        int week0815 = 0;
        int week1623 = 0;
        int week2431 = 0;

        for (int i = 0; i < saleReport.getSales().size(); i++) {
            SaleReportObj.Sale sale = saleReport.getSales().get(i);
            if (i <= 7) {
                week0107 += sale.getSale();
            } else if (i <= 15) {
                week0815 += sale.getSale();
            } else if (i <= 23) {
                week1623 += sale.getSale();
            } else {
                week2431 += sale.getSale();
            }
        }

        tvTotalMoney1.setText(" $ " + week0107);
        tvTotalMoney2.setText(" $ " + week0815);
        tvTotalMoney3.setText(" $ " + week1623);
        tvTotalMoney4.setText(" $ " + week2431);

        int max = week0107;
        if (week0815 > max) {
            max = week0815;
        } else if (week1623 > max) {
            max = week1623;
        } else if (week2431 > max) {
            max = week2431;
        }
        if (max > 0) {
            Log.w("ReloadProgressBar", max + " " + week0107 + " " + week0815 + " " + week1623 + " " + week2431);
            progressBar1.setProgress(week0107 * 100 / max);
            progressBar2.setProgress(week0815 * 100 / max);
            progressBar3.setProgress(week1623 * 100 / max);
            progressBar4.setProgress(week2431 * 100 / max);
        } else {
            progressBar1.setProgress(0);
            progressBar2.setProgress(0);
            progressBar3.setProgress(0);
            progressBar4.setProgress(0);
        }
    }

    public void reloadLineChart() {
        SaleReportObj saleReport = database.getSaleReportByDayRange();
        ArrayList<SaleReportObj.Sale> sales = saleReport.getSales();

        int positisonMax = 0;
        float valueMax = sales.get(0).getSale();

        List<PointValue> pointValueList = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getSale() > valueMax) {
                valueMax = sales.get(i).getSale();
                positisonMax = i;
            }
            PointValue pointValue = new PointValue();
            pointValue.set(i, sales.get(i).getSale());
            pointValue.setLabel(" $ " + (int) pointValue.getY() + " ");
            pointValueList.add(pointValue);
        }

        Line line = new Line();
        line.setHasPoints(true);
        line.setHasLines(true);
        line.setColor(Color.parseColor("#fbb03b"));
        //line.setCubic(true); //Line uon luon
        //line.setFilled(true); //To mau den day
        //line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setPointColor(Color.parseColor("#fbb03b"));
        line.setPointRadius(5);
        line.setValues(pointValueList);

        List<Line> lineList = new ArrayList<>();
        lineList.add(line);

        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lineList);
        lineChartData.setValueLabelBackgroundAuto(false);
        lineChartData.setValueLabelBackgroundColor(Color.parseColor("#fbb03b"));
        lineChartData.setValueLabelsTextColor(Color.BLACK);

        lineChartView.setLineChartData(lineChartData);
        lineChartView.setValueSelectionEnabled(true);
        lineChartView.setZoomEnabled(false);

        dayOfWeek1.setBackground(null);
        dayOfWeek2.setBackground(null);
        dayOfWeek3.setBackground(null);
        dayOfWeek4.setBackground(null);
        dayOfWeek5.setBackground(null);
        dayOfWeek6.setBackground(null);
        dayOfWeek7.setBackground(null);
        dayOfWeek8.setBackground(null);

        switch (positisonMax) {
            case 0:
                dayOfWeek1.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 1:
                dayOfWeek2.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 2:
                dayOfWeek3.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 3:
                dayOfWeek4.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 4:
                dayOfWeek5.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 5:
                dayOfWeek6.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 6:
                dayOfWeek7.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
            case 7:
                dayOfWeek8.setBackgroundResource(R.drawable.background_orange_gradient);
                break;
        }
    }

    public void reloadPieChart() {
        SaleReportObj saleReport = database.getSaleReportByDayRange();
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

        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(true);
        pieChartView.setChartRotationEnabled(true);
        pieChartView.setCircleFillRatio(1F);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                database.setPositionWeekToGetReport(database.getCurrentWeekOfYear());
                listener.onButtonBackBestSalesByMonthViewClick();
                break;
            case R.id.layoutChooseMonth:
                autoCompleteMonth.showDropDown();
                break;
            case R.id.layoutProgressBar1:
                layoutLineChart.setWeightSum(7);
                dayOfWeek1.setVisibility(View.VISIBLE);
                dayOfWeek2.setVisibility(View.VISIBLE);
                dayOfWeek3.setVisibility(View.VISIBLE);
                dayOfWeek4.setVisibility(View.VISIBLE);
                dayOfWeek5.setVisibility(View.VISIBLE);
                dayOfWeek6.setVisibility(View.VISIBLE);
                dayOfWeek7.setVisibility(View.VISIBLE);
                dayOfWeek8.setVisibility(View.GONE);

                tvDay1.setText(String.valueOf(1));
                tvDay2.setText(String.valueOf(2));
                tvDay3.setText(String.valueOf(3));
                tvDay4.setText(String.valueOf(4));
                tvDay5.setText(String.valueOf(5));
                tvDay6.setText(String.valueOf(6));
                tvDay7.setText(String.valueOf(7));
                database.setPositionWeekToGetReport(1);
                getDayOfWeek();
                listener.onChooseDayRangeGetReport();
                break;
            case R.id.layoutProgressBar2:
                layoutLineChart.setWeightSum(8);
                dayOfWeek1.setVisibility(View.VISIBLE);
                dayOfWeek2.setVisibility(View.VISIBLE);
                dayOfWeek3.setVisibility(View.VISIBLE);
                dayOfWeek4.setVisibility(View.VISIBLE);
                dayOfWeek5.setVisibility(View.VISIBLE);
                dayOfWeek6.setVisibility(View.VISIBLE);
                dayOfWeek7.setVisibility(View.VISIBLE);
                dayOfWeek8.setVisibility(View.VISIBLE);

                tvDay1.setText(String.valueOf(8));
                tvDay2.setText(String.valueOf(9));
                tvDay3.setText(String.valueOf(10));
                tvDay4.setText(String.valueOf(11));
                tvDay5.setText(String.valueOf(12));
                tvDay6.setText(String.valueOf(13));
                tvDay7.setText(String.valueOf(14));
                tvDay8.setText(String.valueOf(15));
                database.setPositionWeekToGetReport(2);
                getDayOfWeek();
                listener.onChooseDayRangeGetReport();
                break;
            case R.id.layoutProgressBar3:
                layoutLineChart.setWeightSum(8);
                dayOfWeek1.setVisibility(View.VISIBLE);
                dayOfWeek2.setVisibility(View.VISIBLE);
                dayOfWeek3.setVisibility(View.VISIBLE);
                dayOfWeek4.setVisibility(View.VISIBLE);
                dayOfWeek5.setVisibility(View.VISIBLE);
                dayOfWeek6.setVisibility(View.VISIBLE);
                dayOfWeek7.setVisibility(View.VISIBLE);
                dayOfWeek8.setVisibility(View.VISIBLE);

                tvDay1.setText(String.valueOf(16));
                tvDay2.setText(String.valueOf(17));
                tvDay3.setText(String.valueOf(18));
                tvDay4.setText(String.valueOf(19));
                tvDay5.setText(String.valueOf(20));
                tvDay6.setText(String.valueOf(21));
                tvDay7.setText(String.valueOf(22));
                tvDay8.setText(String.valueOf(23));
                database.setPositionWeekToGetReport(3);
                getDayOfWeek();
                listener.onChooseDayRangeGetReport();
                break;
            case R.id.layoutProgressBar4:
                tvDay1.setText(String.valueOf(24));
                tvDay2.setText(String.valueOf(25));
                tvDay3.setText(String.valueOf(26));
                tvDay4.setText(String.valueOf(27));
                tvDay5.setText(String.valueOf(28));
                tvDay6.setText(String.valueOf(29));
                tvDay7.setText(String.valueOf(30));
                tvDay8.setText(String.valueOf(31));

                if (database.getPositionMonthToGetReport() == 1) {
                    if (TimeObject.isLeapYear(TimeObject.getCurrentYear())) {
                        layoutLineChart.setWeightSum(6);
                        dayOfWeek1.setVisibility(View.VISIBLE);
                        dayOfWeek2.setVisibility(View.VISIBLE);
                        dayOfWeek3.setVisibility(View.VISIBLE);
                        dayOfWeek4.setVisibility(View.VISIBLE);
                        dayOfWeek5.setVisibility(View.VISIBLE);
                        dayOfWeek6.setVisibility(View.VISIBLE);
                        dayOfWeek7.setVisibility(View.GONE);
                        dayOfWeek8.setVisibility(View.GONE);
                    } else {
                        layoutLineChart.setWeightSum(5);
                        dayOfWeek1.setVisibility(View.VISIBLE);
                        dayOfWeek2.setVisibility(View.VISIBLE);
                        dayOfWeek3.setVisibility(View.VISIBLE);
                        dayOfWeek4.setVisibility(View.VISIBLE);
                        dayOfWeek5.setVisibility(View.VISIBLE);
                        dayOfWeek6.setVisibility(View.GONE);
                        dayOfWeek7.setVisibility(View.GONE);
                        dayOfWeek8.setVisibility(View.GONE);
                    }
                } else if (TimeObject.monthHas31Days(database.getPositionMonthToGetReport() + 1)) {
                    layoutLineChart.setWeightSum(8);
                    dayOfWeek1.setVisibility(View.VISIBLE);
                    dayOfWeek2.setVisibility(View.VISIBLE);
                    dayOfWeek3.setVisibility(View.VISIBLE);
                    dayOfWeek4.setVisibility(View.VISIBLE);
                    dayOfWeek5.setVisibility(View.VISIBLE);
                    dayOfWeek6.setVisibility(View.VISIBLE);
                    dayOfWeek7.setVisibility(View.VISIBLE);
                    dayOfWeek8.setVisibility(View.VISIBLE);
                } else {
                    layoutLineChart.setWeightSum(7);
                    dayOfWeek1.setVisibility(View.VISIBLE);
                    dayOfWeek2.setVisibility(View.VISIBLE);
                    dayOfWeek3.setVisibility(View.VISIBLE);
                    dayOfWeek4.setVisibility(View.VISIBLE);
                    dayOfWeek5.setVisibility(View.VISIBLE);
                    dayOfWeek6.setVisibility(View.VISIBLE);
                    dayOfWeek7.setVisibility(View.VISIBLE);
                    dayOfWeek8.setVisibility(View.GONE);
                }

                database.setPositionWeekToGetReport(4);
                getDayOfWeek();
                listener.onChooseDayRangeGetReport();
                break;
        }
    }
}
