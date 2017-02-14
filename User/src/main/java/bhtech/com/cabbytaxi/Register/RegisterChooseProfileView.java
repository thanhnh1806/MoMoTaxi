package bhtech.com.cabbytaxi.Register;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.RegisterInterface;
import bhtech.com.cabbytaxi.object.ContantValuesObject;

public class RegisterChooseProfileView extends Fragment implements View.OnClickListener {
    private RegisterInterface.Listener listener;
    private RegisterInterface.Datasource datasource;

    public void setListener(RegisterInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(RegisterInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public RegisterChooseProfileView() {

    }

    private FrameLayout btnForPersonal, btnForCompany;
    private Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_choose_profile_view, container, false);
        btnForPersonal = (FrameLayout) v.findViewById(R.id.btnForPersonal);
        btnForCompany = (FrameLayout) v.findViewById(R.id.btnForCompany);
        btnNext = (Button) v.findViewById(R.id.btnNext);

        btnForPersonal.setOnClickListener(this);
        btnForCompany.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onCreateViewFinish();
    }

    public void reloadView() {
        int userChoose = datasource.getUserChooseProfile();
        if (userChoose == ContantValuesObject.RegisterForPersonal) {
            btnForPersonal.setBackgroundColor(getResources().getColor(R.color.blue_sky_light));
            btnForCompany.setBackgroundColor(getResources().getColor(android.R.color.white));
        } else if (userChoose == ContantValuesObject.RegisterForCompany) {
            btnForCompany.setBackgroundColor(getResources().getColor(R.color.blue_sky_light));
            btnForPersonal.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnForPersonal:
                datasource.setUserChooseProfile(ContantValuesObject.RegisterForPersonal);
                listener.onForPersonalClick();
                break;
            case R.id.btnForCompany:
                datasource.setUserChooseProfile(ContantValuesObject.RegisterForCompany);
                listener.onForCompanyClick();
                break;
            case R.id.btnNext:
                listener.onChooseProfileNextClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
