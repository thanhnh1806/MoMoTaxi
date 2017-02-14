package bhtech.com.cabbytaxi.FavouriteLocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.LocationObject;

/**
 * Created by thanh_nguyen on 02/02/2016.
 */
public class FavouriteLocationAdapter extends BaseSwipeAdapter {
    private ArrayList<LocationObject> list = new ArrayList<>();
    private FavouriteLocationInterface.Listener listener;
    private FavouriteLocationInterface.Datasource datasource;

    public FavouriteLocationAdapter(ArrayList<LocationObject> objects,
                                    FavouriteLocationInterface.Listener listener,
                                    FavouriteLocationInterface.Datasource datasource) {
        this.list = objects;
        this.listener = listener;
        this.datasource = datasource;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private TextView tvLocation;
    private LinearLayout layoutEdit, layout_item;
    private FrameLayout btnEdit, btnDelete;

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_location_list_item, parent, false);

        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        layoutEdit = (LinearLayout) v.findViewById(R.id.layoutEdit);
        layout_item = (LinearLayout) v.findViewById(R.id.layout_item);
        btnEdit = (FrameLayout) v.findViewById(R.id.btnEdit);
        btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                super.onStartOpen(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                super.onStartClose(layout);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                super.onUpdate(layout, leftOffset, topOffset);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                super.onHandRelease(layout, xvel, yvel);
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        tvLocation.setText(list.get(position).getAddress());
        if (position == datasource.getListItemPositionClick()) {
            layoutEdit.setVisibility(View.VISIBLE);
        } else {
            layoutEdit.setVisibility(View.GONE);
        }

        layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onListItemClick();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onListViewButtonEditClick();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);

                listener.onListViewButtonDeleteClick();
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
