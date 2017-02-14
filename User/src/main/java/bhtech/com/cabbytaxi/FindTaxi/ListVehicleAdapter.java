package bhtech.com.cabbytaxi.FindTaxi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.VehicleObj;
import bhtech.com.cabbytaxi.services.VolleyServices;

/**
 * Created by thanh_nguyen on 16/02/2016.
 */
public class ListVehicleAdapter extends RecyclerView.Adapter<ListVehicleAdapter.ViewHolder> {
    private ArrayList<VehicleObj> list = new ArrayList<>();
    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;
    private ImageLoader imageLoader;

    public ListVehicleAdapter(Context context, ArrayList<VehicleObj> list,
                              FindTaxiInterface.Listener listener,
                              FindTaxiInterface.Datasource datasource) {
        this.list = list;
        this.listener = listener;
        this.datasource = datasource;
        imageLoader = VolleyServices.getInstance(context).getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, listener, datasource);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivCar2.setImageUrl(ContantValuesObject.DOMAIN_IMAGE + list.get(position).getIcon_select(), imageLoader);
        holder.ivCar1.setImageUrl(ContantValuesObject.DOMAIN_IMAGE + list.get(position).getIcon_deselect(), imageLoader);

        if (position == datasource.getPositionListVehicleClick()) {
            holder.ivCar2.setVisibility(View.VISIBLE);
            holder.ivCar1.setVisibility(View.INVISIBLE);
        } else {
            holder.ivCar2.setVisibility(View.INVISIBLE);
            holder.ivCar1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout loVehicle;
        private NetworkImageView ivCar1, ivCar2;

        public ViewHolder(View v, final FindTaxiInterface.Listener listener,
                          final FindTaxiInterface.Datasource datasource) {
            super(v);
            loVehicle = (FrameLayout) v.findViewById(R.id.loVehicle);
            ivCar1 = (NetworkImageView) v.findViewById(R.id.ivCar1);
            ivCar2 = (NetworkImageView) v.findViewById(R.id.ivCar2);

            loVehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datasource.setCarClass(getAdapterPosition());
                    listener.onListVehicleClick();
                }
            });
        }
    }
}
