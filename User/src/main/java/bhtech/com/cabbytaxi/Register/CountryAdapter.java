package bhtech.com.cabbytaxi.Register;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 29/03/2016.
 */
public class CountryAdapter extends ArrayAdapter<String> {
    private ArrayList<String> lists;

    public CountryAdapter(Context context, ArrayList<String> lists) {
        super(context, android.R.layout.simple_list_item_1, lists);
        this.lists = lists;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        if (count > 0) {
            return count - 1;
        } else {
            return count;
        }
    }
}
