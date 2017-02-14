package bhtech.com.cabbydriver.FindCustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.LocationObject;

/**
 * Created by thanh_nguyen02 on 18/12/2015.
 */
public class FindCustomerDropDownAdapter extends ArrayAdapter<LocationObject> {
    private Context context;
    private ArrayList<LocationObject> list = new ArrayList<>();

    public FindCustomerDropDownAdapter(Context context, ArrayList<LocationObject> objects) {
        super(context, 0, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_spinner_dropdown, null);
        ((TextView) v.findViewById(R.id.tvLocation)).setText(list.get(position).getAddress());
        return v;
    }
}
