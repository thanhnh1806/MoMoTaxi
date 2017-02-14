package bhtech.com.cabbytaxi.InTaxi;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;

public class InTaxiView extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private InTaxiInterface.Listener listener;

    public void setListener(InTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    private TextView tvDistance, tvTimeETA, tvAddress;
    private ImageView ivNavigationView;
    private LinearLayout btnShareTaxi;
    private Button btnEndTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_in_taxi_view, container, false);
        tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        tvTimeETA = (TextView) v.findViewById(R.id.tvTimeETA);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        ivNavigationView = (ImageView) v.findViewById(R.id.ivNavigationView);
        btnShareTaxi = (LinearLayout) v.findViewById(R.id.btnShareTaxi);
        btnEndTrip = (Button) v.findViewById(R.id.btnEndTrip);

        ivNavigationView.setOnClickListener(this);
        btnShareTaxi.setOnClickListener(this);
        btnEndTrip.setOnClickListener(this);

        //For build test
        if (ContantValuesObject.isBuildTest) {
            btnEndTrip.setVisibility(View.VISIBLE);
        } else {
            btnEndTrip.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.OnCreateViewDone();
    }

    public void updateDistance(float mDistance) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        tvDistance.setText(decimalFormat.format(mDistance) + " " + getActivity().getString(R.string.kilometer));
    }

    public void updateTimeETA(Date time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        tvTimeETA.setText(timeFormat.format(time));
    }

    public void updateAddress(String address) {
        tvAddress.setText(address);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNavigationView:
                mListener.OnButtonNavigationViewClick();
                break;
            case R.id.btnShareTaxi:
                mListener.OnButtonShareTaxiClick();
                break;
            case R.id.btnEndTrip:
                listener.onButtonEndTripClick();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void OnCreateViewDone();

        void OnButtonNavigationViewClick();

        void OnButtonShareTaxiClick();
    }
}
