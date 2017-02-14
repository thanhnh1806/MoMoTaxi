package bhtech.com.cabbydriver.ChooseCar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.CarObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class ChooseCarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CarObj> list;

    public ChooseCarAdapter(Context _context, ArrayList<CarObj> _list) {
        this.context = _context;
        this.list = _list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.choose_car_item, null);

            viewHolder = new ViewHolder();
            viewHolder.img_carStatus = (ImageView) convertView.findViewById(R.id.img_carStatus);
            viewHolder.txt_carNumber = (TextView) convertView.findViewById(R.id.txt_carId);
            viewHolder.img_cricleStatus = (ImageView) convertView.findViewById(R.id.img_cricleStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CarObj obj = list.get(position);
        if (obj.getStatus() == ContantValuesObject.DRIVER_NOT_AVAILABLE) {
            viewHolder.txt_carNumber.setText(obj.getNumber());
            viewHolder.txt_carNumber.setTextColor(Color.parseColor("#b9babb"));
            viewHolder.img_cricleStatus.setImageResource(R.drawable.cricle_disable);
            Ion.with(context).load(ContantValuesObject.DOMAIN_IMAGE + obj.vehicleUnavailableIconUrl).intoImageView(viewHolder.img_carStatus);
        } else {
            viewHolder.txt_carNumber.setText(obj.getNumber());
            viewHolder.txt_carNumber.setTextColor(Color.parseColor("#000000"));
            viewHolder.img_cricleStatus.setImageResource(R.drawable.cricle_enable);
            Ion.with(context).load(ContantValuesObject.DOMAIN_IMAGE + obj.vehicleIconUrl).intoImageView(viewHolder.img_carStatus);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView img_carStatus;
        TextView txt_carNumber;
        ImageView img_cricleStatus;
    }
}
