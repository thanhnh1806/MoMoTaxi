package bhtech.com.cabbytaxi.FindTaxi;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.services.NetworkServices;
import de.hdodenhof.circleimageview.CircleImageView;

public class FindTaxiFoundATaxiView extends Fragment implements View.OnClickListener {
    private TextView tvDriverName, tvNumberPlate;

    private CircleImageView ivAvatar;
    private TextView tvEstimateTime;
    private Button btnOk, btnCancel;

    private FindTaxiInterface.Listener listener;
    private FindTaxiInterface.Datasource datasource;

    public void setListener(FindTaxiInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FindTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_found_a_taxi_view, container, false);

        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvNumberPlate = (TextView) v.findViewById(R.id.tvNumberPlate);
        tvEstimateTime = (TextView) v.findViewById(R.id.tvEstimateTime);
        btnOk = (Button) v.findViewById(R.id.btnOk);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        ivAvatar = (CircleImageView) v.findViewById(R.id.ivAvatar);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return v;
    }

    public void setEnableButtonCancel(boolean b) {
        btnCancel.setEnabled(b);
    }

    public void setEnableButtonOk(boolean b) {
        btnOk.setEnabled(b);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                listener.onFoundATaxiButtonCancelClick();
                break;
            case R.id.btnOk:
                listener.onFoundATaxiButtonOkClick();
                break;
        }
    }

    public void reloadView() {
        if (datasource.getDriverPhoto() != null) {
            NetworkServices.imageRequest(getActivity(), datasource.getDriverPhoto(),
                    new NetworkServices.MakeImageRequestFinish() {
                        @Override
                        public void Success(Bitmap bitmap) {
                            ivAvatar.setImageBitmap(bitmap);
                        }

                        @Override
                        public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                        }
                    });
        }

        if (datasource.getEstimatedTime() != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            tvEstimateTime.setText(decimalFormat.format(datasource.getEstimatedTimeFoundATaxi()));
        }
        tvDriverName.setText(getResources().getString(R.string.mr) + " " + datasource.getDriverName());
        tvNumberPlate.setText(datasource.getCarNumber());
    }
}
