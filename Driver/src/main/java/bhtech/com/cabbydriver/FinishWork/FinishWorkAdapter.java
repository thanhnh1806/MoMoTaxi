package bhtech.com.cabbydriver.FinishWork;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

/**
 * Created by duongpv on 4/6/16.
 */
public class FinishWorkAdapter extends BaseAdapter {

    private Context context;
    public FinishWorkInterface.DataSource dataSource;

    public FinishWorkAdapter (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return dataSource.getNumberOfPassengers();
        }
        else if (position == 1) {
            return dataSource.getSales();
        }
        else if (position == 2) {
            return dataSource.getTotalDistance();
        }
        else {
            return dataSource.getTotalHours();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.finish_work_item, null);
        }

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTodayResultTitle);
        TextView tvValue = (TextView)convertView.findViewById(R.id.tvTodayResultValue);

        if (position == 0) {
            tvTitle.setText(R.string.passengers);
            tvValue.setText(dataSource.getNumberOfPassengers());
        }
        else if (position == 1) {
            tvTitle.setText(R.string.total_sales);
            tvValue.setText(dataSource.getSales());
        }
        else if (position == 2) {
            tvTitle.setText(R.string.total_distance);
            tvValue.setText(dataSource.getTotalDistance());
        }
        else {
            tvTitle.setText(R.string.total_hours);
            tvValue.setText(dataSource.getTotalHours());
        }

        return convertView;
    }
}
