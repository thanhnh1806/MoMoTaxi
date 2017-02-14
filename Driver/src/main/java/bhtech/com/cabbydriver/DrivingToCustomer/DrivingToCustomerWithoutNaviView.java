package bhtech.com.cabbydriver.DrivingToCustomer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

/**
 * Created by duongpv on 4/3/16.
 */
public class DrivingToCustomerWithoutNaviView extends Fragment implements View.OnClickListener {
    DrivingToCustomerInterface.Listener listener;
    DrivingToCustomerInterface.Datasource datasource;

    public void setListener(DrivingToCustomerInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(DrivingToCustomerInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    LinearLayout layoutIconCar;
    TextView tvCustomerName, tvStartAddress, tvDrivingToCustomer;
    Button btnPickUpUser;
    FrameLayout layoutPressStart, btnShowNavi, btnCall, btnMessage, btnChangeLocation, btnStartEnable;
    ImageView btnStartDisable;
    View layoutChangeDropOff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driving_to_customer_without_navi_view, container, false);
        layoutIconCar = (LinearLayout) v.findViewById(R.id.layoutIconCar);
        layoutPressStart = (FrameLayout) v.findViewById(R.id.layoutPressStart);
        tvCustomerName = (TextView) v.findViewById(R.id.tvCustomerName);
        tvStartAddress = (TextView) v.findViewById(R.id.tvStartAddress);
        tvDrivingToCustomer = (TextView) v.findViewById(R.id.tvDrivingToCustomer);
        btnPickUpUser = (Button) v.findViewById(R.id.btnPickUpUser);
        btnShowNavi = (FrameLayout) v.findViewById(R.id.btnShowNavi);
        btnCall = (FrameLayout) v.findViewById(R.id.btnCall);
        btnMessage = (FrameLayout) v.findViewById(R.id.btnMessage);
        btnChangeLocation = (FrameLayout) v.findViewById(R.id.btnChangeLocation);
        btnStartEnable = (FrameLayout) v.findViewById(R.id.btnStartEnable);
        btnStartDisable = (ImageView) v.findViewById(R.id.btnStartDisable);
        layoutChangeDropOff = v.findViewById(R.id.layoutChangeDropOff);

        btnPickUpUser.setOnClickListener(this);
        btnShowNavi.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnChangeLocation.setOnClickListener(this);
        btnStartEnable.setOnClickListener(this);
        layoutChangeDropOff.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPickUpUser:
                tvDrivingToCustomer.setText(getString(R.string.reaching_customer));
                listener.buttonPickUpUserClick();
                break;
            case R.id.btnShowNavi:
                listener.buttonShowNaviClick();
                break;
            case R.id.btnCall:
                listener.buttonCallClick();
                break;
            case R.id.btnMessage:
                listener.buttonMessageClick();
                break;
            case R.id.btnChangeLocation:
                listener.buttonChangeLocation();
                break;
            case R.id.btnStartEnable:
                listener.buttonStartClick();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.withoutNaviViewCreated();
    }

    public void reloadView() {
        tvCustomerName.setText(datasource.getCustomerName());
        tvStartAddress.setText(datasource.getStartAddress());
    }

    public void setEnableButtonStart() {
        layoutChangeDropOff.setVisibility(View.GONE);
        btnStartDisable.setVisibility(View.GONE);
        btnStartEnable.setVisibility(View.VISIBLE);
        btnPickUpUser.setVisibility(View.GONE);
        layoutIconCar.setVisibility(View.INVISIBLE);
        layoutPressStart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
