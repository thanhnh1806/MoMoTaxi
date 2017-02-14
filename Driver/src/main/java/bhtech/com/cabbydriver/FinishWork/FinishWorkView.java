package bhtech.com.cabbydriver.FinishWork;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import bhtech.com.cabbydriver.R;

public class FinishWorkView extends Fragment {

    private Button finishButton;
    private EditText mileageText;
    private ListView listView;
    private TextView tvTodayDate;

    FinishWorkAdapter adapter;

    private Context context;

    private FinishWorkInterface.DataSource dataSource;
    private FinishWorkInterface.Delegate delegate;

    public void setDelegate(FinishWorkInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    public void setDataSource(FinishWorkInterface.DataSource dataSource) {
        this.dataSource = dataSource;
        adapter = new FinishWorkAdapter(context);
        adapter.dataSource = dataSource;
    }

//    public void setListViewDataSource (FinishWorkInterface.ListViewDataSource dataSource) {
//        adapter.dataSource = dataSource;
//    }

    private OnFragmentInteractionListener mListener;

    public FinishWorkView() {

    }

    public FinishWorkView(Context context) {
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
        View v = inflater.inflate(R.layout.fragment_finish_work_view, container, false);
        finishButton = (Button)v.findViewById(R.id.btnFinishWork);
        mileageText = (EditText)v.findViewById(R.id.etTodayMileage);
        listView = (ListView)v.findViewById(R.id.work_summary);

        listView.setAdapter(adapter);
        tvTodayDate = (TextView)v.findViewById(R.id.tvTodayDate);
        tvTodayDate.setText(dataSource.getTodayDate());
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mileageString = mileageText.getText().toString();
                delegate.finishButtonOnClick(mileageString);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        delegate = null;
        dataSource = null;
    }

    public void reloadView () {
        tvTodayDate.setText(dataSource.getTodayDate());
        adapter.notifyDataSetInvalidated();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
