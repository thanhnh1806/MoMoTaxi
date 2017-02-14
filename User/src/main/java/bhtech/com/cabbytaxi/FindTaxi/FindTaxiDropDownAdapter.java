package bhtech.com.cabbytaxi.FindTaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.LocationObject;

/**
 * Created by thanh_nguyen02 on 18/12/2015.
 */
public class FindTaxiDropDownAdapter extends ArrayAdapter<LocationObject> {
    private Context context;
    private ArrayList<LocationObject> list = new ArrayList<>();
    private String header;

    public FindTaxiDropDownAdapter(Context context, ArrayList<LocationObject> objects, String header) {
        super(context, 0, objects);
        this.context = context;
        list = objects;
        this.header = header;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_spinner_dropdown, null);

        TextView tvHeader = (TextView) v.findViewById(R.id.tvHeader);
        if (position == 0) {
            tvHeader.setText(header);
            tvHeader.setVisibility(View.VISIBLE);
        } else {
            tvHeader.setVisibility(View.GONE);
        }
        ((TextView) v.findViewById(R.id.tvLocation)).setText(list.get(position).getAddress());
        return v;
    }
}
