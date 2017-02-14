package bhtech.com.cabbydriver.Sales;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.SaleReportObj;
import bhtech.com.cabbydriver.object.StringObject;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class SaleReportByWeekView extends Fragment implements View.OnClickListener {
    private SalesInterface.Listener listener;
    private SalesInterface.Database database;

    public void setListener(SalesInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatabase(SalesInterface.Database database) {
        this.database = database;
    }

    TextView tvDriverName, tvTotalType, tvTotal, tvTotalMoney1, tvTotalMoney2, tvTotalMoney3,
            tvTotalMoney4, tvTotalMoney5, tvTotalMoney6, tvTotalMoney7, tvChooseWeek, btnShowBestSale;
    FrameLayout btnBack, btnDay, btnWeek, btnMonth, btnDateRange;
    AutoCompleteTextView autoCompleteWeek, autoCompleteMonth;
    PieChartView pieChart;
    LinearLayout layoutProgressBar;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7;
    ListView listProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_report_view, container, false);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvDriverName.setText(database.getDriverName());

        tvTotal = (TextView) v.findViewById(R.id.tvTotal);
        tvTotalType = (TextView) v.findViewById(R.id.tvTotalType);

        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        btnDay = (FrameLayout) v.findViewById(R.id.btnDay);
        btnMonth = (FrameLayout) v.findViewById(R.id.btnMonth);
        btnWeek = (FrameLayout) v.findViewById(R.id.btnWeek);
        tvChooseWeek = (TextView) v.findViewById(R.id.tvChooseWeek);
        btnDateRange = (FrameLayout) v.findViewById(R.id.btnDateRange);
        listProgressBar = (ListView) v.findViewById(R.id.listProgressBar);
        btnShowBestSale = (TextView) v.findViewById(R.id.btnShowBestSale);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, database.getArrayMonth());

        autoCompleteMonth = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteMonth);
        autoCompleteMonth.setAdapter(adapterMonth);
        autoCompleteMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database.setDateStartForViewReport(null);
                database.setDateEndForViewReport(null);
                database.setDateForViewSaleReport(null);
                database.setPositionMonthToGetReport(position);
                database.setPositionWeekToGetReport(1);
                //listener.onChooseMonthToGetReport();
            }
        });

        ArrayList<String> listWeek = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            listWeek.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapterWeek = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, listWeek);
        autoCompleteWeek = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteWeek);
        autoCompleteWeek.setAdapter(adapterWeek);
        autoCompleteWeek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database.setDateStartForViewReport(null);
                database.setDateEndForViewReport(null);
                database.setPositionMonthToGetReport(-1);
                database.setDateForViewSaleReport(null);
                database.setPositionWeekToGetReport(position + 1);
                tvChooseWeek.setText(String.valueOf(position + 1));
                //listener.onChooseWeekToGetReport();
            }
        });

        pieChart = (PieChartView) v.findViewById(R.id.pieChart);
        layoutProgressBar = (LinearLayout) v.findViewById(R.id.layoutProgressBar);
        listProgressBar = (ListView) v.findViewById(R.id.listProgressBar);

        progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) v.findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) v.findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar) v.findViewById(R.id.progressBar5);
        progressBar6 = (ProgressBar) v.findViewById(R.id.progressBar6);
        progressBar7 = (ProgressBar) v.findViewById(R.id.progressBar7);

        tvTotalMoney1 = (TextView) v.findViewById(R.id.tvTotalMoney1);
        tvTotalMoney2 = (TextView) v.findViewById(R.id.tvTotalMoney2);
        tvTotalMoney3 = (TextView) v.findViewById(R.id.tvTotalMoney3);
        tvTotalMoney4 = (TextView) v.findViewById(R.id.tvTotalMoney4);
        tvTotalMoney5 = (TextView) v.findViewById(R.id.tvTotalMoney5);
        tvTotalMoney6 = (TextView) v.findViewById(R.id.tvTotalMoney6);
        tvTotalMoney7 = (TextView) v.findViewById(R.id.tvTotalMoney7);

        tvTotalMoney1.setSelected(true);
        tvTotalMoney2.setSelected(true);
        tvTotalMoney3.setSelected(true);
        tvTotalMoney4.setSelected(true);
        tvTotalMoney5.setSelected(true);
        tvTotalMoney6.setSelected(true);
        tvTotalMoney7.setSelected(true);

        btnBack.setOnClickListener(this);
        btnDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
        btnWeek.setOnClickListener(this);
        btnDateRange.setOnClickListener(this);
        btnShowBestSale.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        database.setPositionWeekToGetReport(database.getCurrentWeekOfYear() - 1);
        tvChooseWeek.setText(String.valueOf(database.getCurrentWeekOfYear() - 1));
        listener.onChooseWeekToGetReport();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                listener.onButtonBackSaleReportViewClick();
                break;
            case R.id.btnDay:
                database.setChooseDateFromWeekView(true);
                database.setChooseRangeDate(false);
                listener.onButtonDaySaleReportViewClick();
                break;
            case R.id.btnMonth:
                database.setChooseRangeDate(false);
                autoCompleteMonth.showDropDown();
                break;
            case R.id.btnWeek:
                tvTotalType.setText(getString(R.string.total_week));
                database.setChooseRangeDate(false);
                autoCompleteWeek.showDropDown();
                break;
            case R.id.btnDateRange:
                tvTotalType.setText(getString(R.string.total));
                database.setChooseRangeDate(true);
                database.setDateStartForViewReport(null);
                database.setDateEndForViewReport(null);
                listener.onButtonDaySaleReportViewClick();
                break;
            case R.id.btnShowBestSale:
                listener.showBestSale();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        database = null;
    }

    public void reloadView() {
        SaleReportObj saleReport;

        if (database.isChooseRangeDate()) {
            saleReport = database.getSaleReportByDayRange();
            reloadListProgressBar(saleReport);
            listProgressBar.setVisibility(View.VISIBLE);
            layoutProgressBar.setVisibility(View.GONE);
        } else {
            saleReport = database.getSaleReportByWeek();
            reloadProgressBar(saleReport);
            listProgressBar.setVisibility(View.GONE);
            layoutProgressBar.setVisibility(View.VISIBLE);
        }
        int total = 0;
        for (int i = 0; i < saleReport.getSales().size(); i++) {
            total += saleReport.getSales().get(i).getSale();
        }
        tvTotal.setText("$ " + StringObject.getDecimalFormat(1).format(total));

        reloadPieChart(saleReport);
    }

    private void reloadListProgressBar(SaleReportObj saleReport) {
        ListProgressBarAdapter adapter = new ListProgressBarAdapter(getActivity(), saleReport.getSales());
        listProgressBar.setAdapter(adapter);
        listProgressBar.deferNotifyDataSetChanged();
    }

    private void reloadProgressBar(SaleReportObj saleReport) {
        ArrayList<SaleReportObj.Sale> sales = saleReport.getSales();
        float max = sales.get(0).getSale();
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getSale() > max) {
                max = sales.get(i).getSale();
            }
        }
        if (max > 0) {
            progressBar1.setProgress((int) (sales.get(0).getSale() * 100 / max));
            progressBar2.setProgress((int) (sales.get(1).getSale() * 100 / max));
            progressBar3.setProgress((int) (sales.get(2).getSale() * 100 / max));
            progressBar4.setProgress((int) (sales.get(3).getSale() * 100 / max));
            progressBar5.setProgress((int) (sales.get(4).getSale() * 100 / max));
            progressBar6.setProgress((int) (sales.get(5).getSale() * 100 / max));
            progressBar7.setProgress((int) (sales.get(6).getSale() * 100 / max));
        } else {
            progressBar1.setProgress(0);
            progressBar2.setProgress(0);
            progressBar3.setProgress(0);
            progressBar4.setProgress(0);
            progressBar5.setProgress(0);
            progressBar6.setProgress(0);
            progressBar7.setProgress(0);
        }

        tvTotalMoney1.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(0).getSale()));
        tvTotalMoney2.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(1).getSale()));
        tvTotalMoney3.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(2).getSale()));
        tvTotalMoney4.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(3).getSale()));
        tvTotalMoney5.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(4).getSale()));
        tvTotalMoney6.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(5).getSale()));
        tvTotalMoney7.setText("$ " + StringObject.getDecimalFormat(1).format(sales.get(6).getSale()));
    }

    private void reloadPieChart(SaleReportObj saleReport) {
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
}