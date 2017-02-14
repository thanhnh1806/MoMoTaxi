package bhtech.com.cabbydriver.AboutUs;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.BaseUserObject;

/**
 * Created by duongpv on 4/6/16.
 */
public class AboutCabbyView extends Fragment {

    private Context context;
    private Button btnAboutApp;
    private Button btnAboutTaxi;
    private Button btnAboutUser;
    private Button btnAboutDismiss;

    private LinearLayout layoutAboutDetail;
    private ImageView imgAboutIcon;
    private TextView tvAboutDesc;

    public void setContext (Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_cabby_view, container, false);

        btnAboutApp = (Button)v.findViewById(R.id.btnAboutApp);
        btnAboutTaxi = (Button)v.findViewById(R.id.btnAboutTaxi);
        btnAboutUser = (Button)v.findViewById(R.id.btnAboutUser);
        btnAboutDismiss = (Button)v.findViewById(R.id.btnAboutDismiss);
        tvAboutDesc = (TextView)v.findViewById(R.id.tvAboutDesc);

        layoutAboutDetail = (LinearLayout)v.findViewById(R.id.layoutAboutDetail);
        imgAboutIcon = (ImageView)v.findViewById(R.id.imgAboutIcon);

        btnAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAboutDismiss.setBackgroundResource(R.drawable.aboutcabby_app);
                btnAboutDismiss.setVisibility(View.VISIBLE);
                layoutAboutDetail.setVisibility(View.VISIBLE);
                imgAboutIcon.setBackgroundResource(R.drawable.about_app_icon);
                tvAboutDesc.setText(R.string.about_app);
            }
        });

        btnAboutTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAboutDismiss.setBackgroundResource(R.drawable.aboutcabby_driver);
                btnAboutDismiss.setVisibility(View.VISIBLE);
                layoutAboutDetail.setVisibility(View.VISIBLE);
                imgAboutIcon.setBackgroundResource(R.drawable.about_taxi_icon);
                tvAboutDesc.setText(R.string.about_taxi);
            }
        });

        btnAboutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAboutDismiss.setBackgroundResource(R.drawable.aboutcabby_user);
                btnAboutDismiss.setVisibility(View.VISIBLE);
                layoutAboutDetail.setVisibility(View.VISIBLE);
                imgAboutIcon.setBackgroundResource(R.drawable.about_user_icon);
                tvAboutDesc.setText(R.string.about_user);
            }
        });

        btnAboutDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAboutDismiss.setVisibility(View.INVISIBLE);
                layoutAboutDetail.setVisibility(View.INVISIBLE);
                tvAboutDesc.setText(R.string.about_cabby_);
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void reloadView () {

    }
}
