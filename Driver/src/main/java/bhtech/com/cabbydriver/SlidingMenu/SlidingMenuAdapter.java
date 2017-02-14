package bhtech.com.cabbydriver.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;


/**
 * Created by duongpv on 4/3/16.
 */
public class SlidingMenuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public SlidingMenuAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItemArrayList) {
        this.context = context;
        this.navDrawerItems = navDrawerItemArrayList;
    }

    @Override
    public int getCount() {
        return this.navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_slidingmenu, null);
        }
        LinearLayout nav_item = (LinearLayout) convertView.findViewById(R.id.nav_item);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        // displaying count
        // check whether it set visible or not
        if (navDrawerItems.get(position).getCounterVisibility()) {
            txtCount.setText(navDrawerItems.get(position).getCount());
        } else {
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }

        if (position == navDrawerItems.size() - 1 || position == navDrawerItems.size() - 2
                || position == navDrawerItems.size() - 3 || position == navDrawerItems.size() - 4) {
            nav_item.setBackgroundColor(context.getResources().getColor(R.color.list_background_end_item));
        }
        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
