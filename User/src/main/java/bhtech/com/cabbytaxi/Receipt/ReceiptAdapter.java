package bhtech.com.cabbytaxi.Receipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ReceiptObject;
import bhtech.com.cabbytaxi.object.TimeObject;

/**
 * Created by Le Anh Tuan on 2/16/2016.
 */
public class ReceiptAdapter extends BaseSwipeAdapter {
    private Context context;
    private ArrayList<ReceiptObject> list = new ArrayList<>();
    private ReceiptInterface.Listener listener;
    private ReceiptInterface.Datasource datasource;

    public ReceiptAdapter(Context context, ArrayList<ReceiptObject> list,
                          ReceiptInterface.Listener listener, ReceiptInterface.Datasource datasource) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.datasource = datasource;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private TextView tvReceiptId, tvDate, tvLocation, tvReceiptId2;
    private LinearLayout btnDelete, btnMail;
    private SwipeLayout swipeLayout;

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.receipt_item, null);

        tvReceiptId = (TextView) v.findViewById(R.id.tvReceiptId);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        tvReceiptId2 = (TextView) v.findViewById(R.id.tvReceiptId2);
        btnDelete = (LinearLayout) v.findViewById(R.id.btnDelete);
        btnMail = (LinearLayout) v.findViewById(R.id.btnMail);

        swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
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
        tvReceiptId.setText(context.getString(R.string.receipt) + " #" + list.get(position).getId());
        tvReceiptId2.setText(context.getString(R.string.receipt) + " #" + list.get(position).getId());
        tvLocation.setText(list.get(position).getFromAddress());

        tvDate.setText(TimeObject.parseDate(list.get(position).getUpdatedDate(), datasource.getDateFormat()));


        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onButtonMailClick();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListItemPositionClick(position);
                listener.onButtonDeleteClick();
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
