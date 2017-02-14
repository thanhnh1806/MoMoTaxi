package bhtech.com.cabbydriver.CarStatus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.StringObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarStatusView extends Fragment {
    ImageView imgCarPhoto;
    TextView tvCarNumber;
    TextView tvVehicleTypeName;

    TextView tvTotalDrove;
    TextView tvCarAge;
    TextView tvNextMaintenanceDate;
    TextView tvRemainingKm;
    TextView tvCarBreakDown;
    TextView tvCarAccident;

    CarStatusInterface.DataSource dataSource;

    Context context;

    public CarStatusView() {
        // Required empty public constructor
    }

    public void setDataSource(CarStatusInterface.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_car_status_view, container, false);

        imgCarPhoto = (ImageView) v.findViewById(R.id.imgCarPhoto);
        tvCarNumber = (TextView) v.findViewById(R.id.tvCarNumber);
        tvVehicleTypeName = (TextView) v.findViewById(R.id.tvVehicleType);
        tvTotalDrove = (TextView) v.findViewById(R.id.tvTotalDrove);
        tvCarAge = (TextView) v.findViewById(R.id.tvCarAgeInMonth);
        tvNextMaintenanceDate = (TextView) v.findViewById(R.id.tvNextMaintenanceDate);
        tvRemainingKm = (TextView) v.findViewById(R.id.tvRemainingKm);
        tvCarBreakDown = (TextView) v.findViewById(R.id.tvBreakDown);
        tvCarAccident = (TextView) v.findViewById(R.id.tvAccident);

        return v;
    }

    public void reloadView() {
        if (!StringObject.isNullOrEmpty(dataSource.getCarImageUrl())) {
            Ion.with(context).load(ContantValuesObject.DOMAIN_IMAGE + dataSource.getCarImageUrl());
        }
        tvCarNumber.setText(dataSource.getCarNumber());
        tvVehicleTypeName.setText(dataSource.getCarType());
        DecimalFormat df = new DecimalFormat("#.0");
        tvTotalDrove.setText(String.valueOf((int) dataSource.getTotalDrove()));
        tvCarAge.setText(dataSource.getCarAge() + "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.US);
        if (dataSource.getNextMaintenanceDate() != null) {
            tvNextMaintenanceDate.setText(dateFormat.format(dataSource.getNextMaintenanceDate()));
        } else {
            tvNextMaintenanceDate.setText("");
        }
        if (dataSource.getRemainingKm() > 0) {
            tvRemainingKm.setText(df.format(dataSource.getRemainingKm()));
        } else {
            tvRemainingKm.setText(0);
        }
        tvCarBreakDown.setText(dataSource.getNumberOfBreakDown() + "");
        tvCarAccident.setText(dataSource.getNumberOfAccident() + "");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataSource = null;
    }
}
