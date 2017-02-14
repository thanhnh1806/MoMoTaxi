package bhtech.com.cabbydriver.TripHistory;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.StringObject;
import bhtech.com.cabbydriver.object.TimeObject;
import bhtech.com.cabbydriver.object.TripObject;

/**
 * Created by duongpv on 4/10/16.
 */
public class TripHistoryAdapter extends BaseExpandableListAdapter {
    private TripHistoryInterface.DataSource dataSource;
    private LayoutInflater inflater;
    private DecimalFormat decimalFormat;
    Context context;

    public TripHistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        decimalFormat = StringObject.getDecimalFormat(1);
    }

    public void setDataSource(TripHistoryInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return dataSource.getListOfTripHistory().size();
    }

    @Override
    //  counts the number of children items so the list knows how many times calls getChildView() method
    //  always 1
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return dataSource.getListOfTripHistory().get(i);
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return dataSource.getListOfTripHistory().get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.groupPosition = groupPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.trip_history_item_parent, viewGroup, false);
        }

        TextView tvTripHistoryItemDate = (TextView) view.findViewById(R.id.tvTripHistoryItemDate);
        TextView tvTripHistoryItemAddress = (TextView) view.findViewById(R.id.tvTripHistoryItemAddress);
        TextView tvUserId = (TextView) view.findViewById(R.id.tvUserId);
        ImageView imgTripHistoryItemLocation = (ImageView) view.findViewById(R.id.imgTripHistoryItemLocation);
        ImageView imgTripHistoryItemLocationBlack = (ImageView) view.findViewById(R.id.imgTripHistoryItemLocationBlack);
        ImageView imgArrowCollapsed = (ImageView) view.findViewById(R.id.imgArrowCollapsed);
        ImageView imgArrowExpanded = (ImageView) view.findViewById(R.id.imgArrowExpanded);

        TripObject trip = (TripObject) getGroup(groupPosition);

        if (groupPosition == dataSource.getExpandedGroupPosition()) {
            tvUserId.setVisibility(View.VISIBLE);
            imgTripHistoryItemLocation.setVisibility(View.INVISIBLE);
            imgTripHistoryItemLocationBlack.setVisibility(View.VISIBLE);
            tvTripHistoryItemAddress.setTextColor(Color.BLACK);
            imgArrowExpanded.setVisibility(View.VISIBLE);
            imgArrowCollapsed.setVisibility(View.INVISIBLE);
        } else {
            tvUserId.setVisibility(View.GONE);
            imgTripHistoryItemLocation.setVisibility(View.VISIBLE);
            imgTripHistoryItemLocationBlack.setVisibility(View.INVISIBLE);
            tvTripHistoryItemAddress.setTextColor(Color.GRAY);
            imgArrowExpanded.setVisibility(View.INVISIBLE);
            imgArrowCollapsed.setVisibility(View.VISIBLE);
        }

        String titleDate = TimeObject.getStringFromDate(trip.requestObj.getUpdatedDate(),
                context.getString(R.string.trip_history_dateformat));
        tvTripHistoryItemDate.setText(titleDate);

        String address = trip.fromAddress;
        tvTripHistoryItemAddress.setText(address);
        try {
            tvUserId.setText(context.getString(R.string.user_id) + " " + trip.requestObj.getRequestUser().getObjectID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setTag(holder);

        //return the entire view
        return view;
    }

    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.childPosition = childPosition;
        holder.groupPosition = groupPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.trip_history_item_child, viewGroup, false);
        }

        TextView tvTripHistoryFrom = (TextView) view.findViewById(R.id.tvTripHistoryFrom);
        TextView tvTripHistoryDistance = (TextView) view.findViewById(R.id.tvTripHistoryDistance);
        TextView tvTripHistoryTime = (TextView) view.findViewById(R.id.tvTripHistoryTime);
        TextView tvTripHistoryCost = (TextView) view.findViewById(R.id.tvTripHistoryCost);

        TripObject trip = dataSource.getListOfTripHistory().get(groupPosition);

        String childTitle = trip.toAddress;
        tvTripHistoryFrom.setText(childTitle);

        if (trip.requestObj.getEnd_mileage() - trip.requestObj.getStart_mileage() != 0) {
            tvTripHistoryDistance.setText(decimalFormat.format(trip.requestObj.getEnd_mileage()
                    - trip.requestObj.getStart_mileage()) + context.getString(R.string.km));
        } else {
            tvTripHistoryDistance.setText("0 " + context.getString(R.string.km));
        }

        String startDate, startTime, endTime;
        try {
            startDate = TimeObject.getStringFromDate(trip.requestObj.getStart_time(),
                    context.getString(R.string.trip_history_dateformat));
        } catch (Exception e) {
            startDate = "";
        }

        try {
            startTime = TimeObject.getStringFromDate(trip.requestObj.getStart_time(),
                    new SimpleDateFormat("hh.mm a", Locale.US));
        } catch (Exception e) {
            startTime = "";
        }

        try {
            endTime = TimeObject.getStringFromDate(trip.requestObj.getEnd_time(),
                    new SimpleDateFormat("hh.mm a", Locale.US));
        } catch (Exception e) {
            endTime = "";
        }

        String historyTime = startDate + " | " + startTime + " - " + endTime;

        tvTripHistoryTime.setText(historyTime);
        if (trip.requestObj.getPrice() + trip.requestObj.getExtra_price() != 0) {
            tvTripHistoryCost.setText("$ " + decimalFormat.format(trip.requestObj.getPrice()
                    + trip.requestObj.getExtra_price()));
        } else {
            tvTripHistoryCost.setText("$ 0");
        }

        view.setTag(holder);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }

// Intentionally put on comment, if you need on click deactivate it
/*  @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder)view.getTag();
        if (view.getId() == holder.button.getId()){

           // DO YOUR ACTION
        }
    }*/


    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected LinearLayout layoutExpand;
    }
}
