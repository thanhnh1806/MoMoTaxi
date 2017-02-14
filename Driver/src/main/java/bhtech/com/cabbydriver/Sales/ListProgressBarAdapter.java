package bhtech.com.cabbydriver.Sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.SaleReportObj;

/**
 * Created by thanh_nguyen on 13/05/2016.
 */
public class ListProgressBarAdapter extends ArrayAdapter<SaleReportObj.Sale> {
    private Context context;
    private ArrayList<SaleReportObj.Sale> sales;
    private float max;
    private DecimalFormat df2;

    public ListProgressBarAdapter(Context context, ArrayList<SaleReportObj.Sale> objects) {
        super(context, 0, objects);
        this.context = context;
        this.sales = objects;

        max = sales.get(0).getSale();
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getSale() > max) {
                max = sales.get(i).getSale();
            }
        }

        df2 = new DecimalFormat("#.00");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_progress_bar_item, null);

        TextView tvDayRange = (TextView) v.findViewById(R.id.tvDayRange);
        TextView tvSale = (TextView) v.findViewById(R.id.tvSale);
        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        if (sales.size() > 0) {
            tvDayRange.setText(sales.get(position).getDate().substring(5, sales.get(position).getDate().length()));
            tvSale.setText("$ " + df2.format(sales.get(position).getSale()));
            tvSale.setSelected(true);
            if (max > 0) {
                progressBar.setProgress((int) (sales.get(position).getSale() * 100 / max));
            } else {
                progressBar.setProgress(0);
            }
        }
        return v;
    }
}
