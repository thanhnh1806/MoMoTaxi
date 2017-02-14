package bhtech.com.cabbydriver.About;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bhtech.com.cabbydriver.R;

public class AboutCabbyView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_cabby_view, container, false);
        return v;
    }
}
