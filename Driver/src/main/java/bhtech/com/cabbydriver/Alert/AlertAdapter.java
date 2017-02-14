package bhtech.com.cabbydriver.Alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.AlertObject;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;
import bhtech.com.cabbydriver.object.TimeObject;

/**
 * Created by duongpv on 4/29/16.
 */
public class AlertAdapter extends BaseExpandableListAdapter {
    AlertInterface.Delegate delegate;
    AlertInterface.DataSource dataSource;
    private LayoutInflater inflater;
    Context context;

    public AlertAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDelegate(AlertInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    public void setDataSource(AlertInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getGroupCount() {
        return dataSource.getCountOfAllItems();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < dataSource.getAlerts().size()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition < dataSource.getAlerts().size()) {
            return dataSource.getAlerts().get(groupPosition);
        } else {
            return dataSource.getFutureBookings().get(groupPosition - dataSource.getAlerts().size());
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.groupPosition = groupPosition;

        if (groupPosition < dataSource.getAlerts().size()) {
            view = inflater.inflate(R.layout.trip_history_item_parent, viewGroup, false);

            TextView tvTripHistoryItemDate = (TextView) view.findViewById(R.id.tvTripHistoryItemDate);
            TextView tvTripHistoryItemAddress = (TextView) view.findViewById(R.id.tvTripHistoryItemAddress);
            ImageView imgTripHistoryItemLocation = (ImageView) view.findViewById(R.id.imgTripHistoryItemLocation);
            ImageView imgArrowCollapsed = (ImageView) view.findViewById(R.id.imgArrowCollapsed);
            ImageView imgArrowExpanded = (ImageView) view.findViewById(R.id.imgArrowExpanded);

            AlertObject trip = (AlertObject) getGroup(groupPosition);
            Date tripDate = trip.alertDateTime;
            if (groupPosition == dataSource.getExpandedGroupPosition()) {
                imgTripHistoryItemLocation.setVisibility(View.INVISIBLE);
                imgArrowExpanded.setVisibility(View.VISIBLE);
                imgArrowCollapsed.setVisibility(View.INVISIBLE);
            } else {
                imgTripHistoryItemLocation.setVisibility(View.VISIBLE);
                imgArrowExpanded.setVisibility(View.INVISIBLE);
                imgArrowCollapsed.setVisibility(View.VISIBLE);
            }

            String trip_history_dateformat = context.getResources().getString(R.string.trip_history_dateformat);
            SimpleDateFormat dateFormat = new SimpleDateFormat(trip_history_dateformat, Locale.US);
            String titleDate = TimeObject.getStringFromDate(tripDate, dateFormat);
            tvTripHistoryItemDate.setText(titleDate);

            String address = trip.alertAddress;
            tvTripHistoryItemAddress.setText(address);

            view.setTag(holder);
        } else {
            view = inflater.inflate(R.layout.layout_future_booking_item, viewGroup, false);

            TextView tvFutureBookingTitle = (TextView) view.findViewById(R.id.tvFutureBookingTitle);
            TextView tvFutureBookingDateTime = (TextView) view.findViewById(R.id.tvFutureBookingDateTime);
            LinearLayout layoutPickUpTime = (LinearLayout) view.findViewById(R.id.layoutPickUpTime);
            TextView tvPickUpTime = (TextView) view.findViewById(R.id.tvPickUpTime);
            TextView tvFutureBookingFrom = (TextView) view.findViewById(R.id.tvFutureBookingFrom);
            TextView tvFutureBookingTo = (TextView) view.findViewById(R.id.tvFutureBookingTo);
            Button btnPickUp = (Button) view.findViewById(R.id.btnPickUp);
            LinearLayout layoutCall = (LinearLayout) view.findViewById(R.id.layoutCall);
            LinearLayout layoutSMS = (LinearLayout) view.findViewById(R.id.layoutSMS);

            TaxiRequestObj booking = (TaxiRequestObj) getGroup(groupPosition);

            final TaxiRequestObj futureBooking = (TaxiRequestObj) getGroup(groupPosition);
            Date bookingDate = futureBooking.getRequestPickupTime();
            if (bookingDate != null) {
                tvFutureBookingFrom.setText(booking.getFromLocationAddress());
                tvFutureBookingTo.setText(booking.getToLocationAddress());

                SimpleDateFormat pickUpTimeFormat = new SimpleDateFormat("h:mm a dd MMM", Locale.US);
                tvPickUpTime.setText(pickUpTimeFormat.format(bookingDate).toUpperCase());

                if (futureBooking.getStatus() != ContantValuesObject.TaxiRequestStatusCancelled) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.US);

                    tvFutureBookingDateTime.setText(" " + dateFormat.format(bookingDate));
                    tvFutureBookingDateTime.setTextColor(context.getResources().getColor(R.color.black));

                    tvFutureBookingTitle.setText(context.getResources().getString(R.string.future_booking_title));
                    tvFutureBookingTitle.setTextColor(context.getResources().getColor(R.color.black));

                    layoutPickUpTime.setBackgroundResource(R.drawable.rectangle_background_blue_radius_5);

                    btnPickUp.setEnabled(true);
                    layoutCall.setEnabled(true);
                    layoutSMS.setEnabled(true);
                } else {
                    Date updateTime = futureBooking.getUpdatedDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.US);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a", Locale.US);

                    tvFutureBookingDateTime.setText(" " + dateFormat.format(updateTime).toUpperCase()
                            + ", at " + timeFormat.format(updateTime).toUpperCase());
                    tvFutureBookingDateTime.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));

                    tvFutureBookingTitle.setText(context.getResources().getString(R.string.this_booking_was_cancelled_on));
                    tvFutureBookingTitle.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));

                    layoutPickUpTime.setBackgroundResource(R.drawable.rectangle_background_gray_radius_5);

                    btnPickUp.setEnabled(false);
                    layoutCall.setEnabled(false);
                    layoutSMS.setEnabled(false);
                }
            }


            btnPickUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.futureBookingOnClickAtIndex(futureBooking.getRequestId());
                }
            });

            layoutCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.futureBookingOnCallAtIndex(futureBooking.getRequestUser().getPhoneNumber());
                }
            });

            layoutSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.futureBookingOnMessageAtIndex(futureBooking.getRequestUser().getPhoneNumber());
                }
            });
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.childPosition = childPosition;
        holder.groupPosition = groupPosition;

        if (groupPosition >= dataSource.getAlerts().size()) {
            //TODO: this is for future booking section
        } else {

            if (view == null) {
                view = inflater.inflate(R.layout.layout_alert_expanded_item, viewGroup, false);
            }

            TextView tvMessageTitle = (TextView) view.findViewById(R.id.tvMessageTitle);
            ImageView imgAlertIcon = (ImageView) view.findViewById(R.id.imgAlertIcon);
            TextView tvAlertAddr = (TextView) view.findViewById(R.id.tvAlertAddr);
            TextView tvAlertTime = (TextView) view.findViewById(R.id.tvAlertTime);

            AlertObject alertObject = dataSource.getAlerts().get(groupPosition);

            String childTitle = alertObject.alertMessageTitle;
            tvMessageTitle.setText(childTitle);

            switch (alertObject.alertType) {
                case 1:
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_heavy_traffic);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_over_speed);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    //TODO: Contact user
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_user_cancel);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    //TODO: Stop too long
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_general);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    //TODO: Accident Report
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_car_accident);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    //TODO: Received Pick up user request
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_general);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    //TODO: Car maintain
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_car_maintain);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    //TODO: Send car to company
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_general);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    //TODO: Admin message
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_general);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                case 10:
                    //TODO: Over working
                    imgAlertIcon.setBackgroundResource(R.drawable.alert_general);
                    imgAlertIcon.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            tvAlertAddr.setText(alertObject.alertAddress);

            SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
            tvAlertTime.setText(timeFormat.format(alertObject.alertDateTime));

            view.setTag(holder);
        }

        //return the entire view
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected LinearLayout layoutExpand;
    }
}
