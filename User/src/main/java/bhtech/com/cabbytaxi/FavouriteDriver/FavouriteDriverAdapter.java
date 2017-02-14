package bhtech.com.cabbytaxi.FavouriteDriver;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.services.NetworkServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tuanla on 3/3/2016.
 */
public class FavouriteDriverAdapter extends BaseSwipeAdapter {
    private Context context;
    private ArrayList<CarDriverObj> list = new ArrayList<>();
    private FavouriteDriverInterface.Listener listener;
    private FavouriteDriverInterface.Datasource datasource;

    public FavouriteDriverAdapter(Context context, ArrayList<CarDriverObj> list,
                                  FavouriteDriverInterface.Listener listener,
                                  FavouriteDriverInterface.Datasource datasource) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.datasource = datasource;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    TextView tvNameDriver1, tvNameDriver2;
    TextView tvCarNumber1, tvCarNumber2;
    CircleImageView imgDriver;
    RatingBar rateBar;
    Button btnCall, btnMail, btnDel;
    LinearLayout layout_item_one, layout_item_two;

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.favourite_driver_item, null);

        tvNameDriver1 = (TextView) v.findViewById(R.id.tv_nameDriver1);
        tvNameDriver2 = (TextView) v.findViewById(R.id.tv_nameDriver2);
        tvCarNumber1 = (TextView) v.findViewById(R.id.tv_carmuner1);
        tvCarNumber2 = (TextView) v.findViewById(R.id.tv_carmuner2);
        imgDriver = (CircleImageView) v.findViewById(R.id.img_driver);
        btnCall = (Button) v.findViewById(R.id.btn_phone_favourite_driver);
        btnMail = (Button) v.findViewById(R.id.btn_mail_favourite_driver);
        btnDel = (Button) v.findViewById(R.id.btn_del_favourite_driver);
        rateBar = (RatingBar) v.findViewById(R.id.rate_driver);
        layout_item_one = (LinearLayout) v.findViewById(R.id.layout_item_one);
        layout_item_two = (LinearLayout) v.findViewById(R.id.layout_item_two);

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
        CarDriverObj obj = list.get(position);
        if (obj != null) {
            tvNameDriver1.setText(obj.getDriver().getFullName());
            tvNameDriver2.setText(obj.getDriver().getFullName());
            tvCarNumber1.setText("Taxi " + obj.getCar().getNumber());
            tvCarNumber2.setText("Taxi " + obj.getCar().getNumber());
            rateBar.setRating(obj.getDriver().getRate());
            if (obj.getDriver().getPhoto() != null) {
                NetworkServices.imageRequest(context, ContantValuesObject.DOMAIN_IMAGE + obj.getDriver().getPhoto(),
                        new NetworkServices.MakeImageRequestFinish() {
                            @Override
                            public void Success(Bitmap bitmap) {
                                imgDriver.setImageBitmap(bitmap);
                            }

                            @Override
                            public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                            }
                        });
            }
        }

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListViewPositionClick(position);
                listener.onListViewIconPhoneClick();
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListViewPositionClick(position);
                listener.onListViewIconEmailClick();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.setListViewPositionClick(position);
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