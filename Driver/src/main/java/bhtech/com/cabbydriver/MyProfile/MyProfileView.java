package bhtech.com.cabbydriver.MyProfile;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.object.ErrorObj;
import bhtech.com.cabbydriver.object.NetworkObject;
import bhtech.com.cabbydriver.object.TimeObject;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileView extends Fragment implements View.OnClickListener {
    private Context context;
    private MyProfileInterface.Listener listener;
    private MyProfileInterface.Datasource datasource;

    public void setListener(MyProfileInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(MyProfileInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private CircleImageView ivDriverAvatar;
    private TextView tvDriverName, tvPhoneNumber, tvMail, tvCompany, tvLocation,
            tvWorkingHourDate, tvWorkingHour, tvMileageDate, tvMileage, btnWorkingHourDay,
            btnWorkingHourWeek, btnWorkingHourMonth, btnMileageDay, btnMileageWeek, btnMileageMonth;
    private FrameLayout btnBack;
    private AutoCompleteTextView acMonthWorkingHour, acMonthMileage, acWeekWorkingHour, acWeekMileage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile_view, container, false);
        ivDriverAvatar = (CircleImageView) v.findViewById(R.id.ivDriverAvatar);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhoneNumber);
        tvMail = (TextView) v.findViewById(R.id.tvMail);
        tvCompany = (TextView) v.findViewById(R.id.tvCompany);
        tvLocation = (TextView) v.findViewById(R.id.tvLocation);

        tvWorkingHourDate = (TextView) v.findViewById(R.id.tvWorkingHourDate);
        tvWorkingHour = (TextView) v.findViewById(R.id.tvWorkingHour);
        tvMileageDate = (TextView) v.findViewById(R.id.tvMileageDate);
        tvMileage = (TextView) v.findViewById(R.id.tvMileage);
        btnWorkingHourDay = (TextView) v.findViewById(R.id.btnWorkingHourDay);
        btnWorkingHourWeek = (TextView) v.findViewById(R.id.btnWorkingHourWeek);
        btnWorkingHourMonth = (TextView) v.findViewById(R.id.btnWorkingHourMonth);
        btnMileageDay = (TextView) v.findViewById(R.id.btnMileageDay);
        btnMileageWeek = (TextView) v.findViewById(R.id.btnMileageWeek);
        btnMileageMonth = (TextView) v.findViewById(R.id.btnMileageMonth);
        btnBack = (FrameLayout) v.findViewById(R.id.btnBack);
        acMonthWorkingHour = (AutoCompleteTextView) v.findViewById(R.id.acMonthWorkingHour);
        acMonthMileage = (AutoCompleteTextView) v.findViewById(R.id.acMonthMileage);
        acWeekWorkingHour = (AutoCompleteTextView) v.findViewById(R.id.acWeekWorkingHour);
        acWeekMileage = (AutoCompleteTextView) v.findViewById(R.id.acWeekMileage);

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, datasource.getArrayMonth());

        acMonthWorkingHour.setAdapter(adapterMonth);
        acMonthWorkingHour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.setChooseListMonthPosition(position);
                listener.onWorkingHourListMonthItemClick();
            }
        });

        acMonthMileage.setAdapter(adapterMonth);
        acMonthMileage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.setChooseListMonthPosition(position);
                listener.onMileageListMonthItemClick();
            }
        });

        ArrayList<String> listWeek = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            listWeek.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapterWeek = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, listWeek);

        acWeekWorkingHour.setAdapter(adapterWeek);
        acWeekWorkingHour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.setChooseListWeekPosition(position + 1);
                listener.onWorkingHourListWeekItemClick();
            }
        });

        acWeekMileage.setAdapter(adapterWeek);
        acWeekMileage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.setChooseListWeekPosition(position + 1);
                listener.onMileageListWeekItemClick();
            }
        });

        btnWorkingHourDay.setOnClickListener(this);
        btnWorkingHourWeek.setOnClickListener(this);
        btnWorkingHourMonth.setOnClickListener(this);
        btnMileageDay.setOnClickListener(this);
        btnMileageWeek.setOnClickListener(this);
        btnMileageMonth.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        return v;
    }

    public void reloadView() {
        if (datasource.getDriverPhoto() != null) {
            NetworkObject.imageRequest(context, datasource.getDriverPhoto(),
                    new NetworkObject.MakeImageRequestFinish() {
                        @Override
                        public void Success(Bitmap bitmap) {
                            ivDriverAvatar.setImageBitmap(bitmap);
                        }

                        @Override
                        public void Failure(ErrorObj error) {

                        }
                    });
        }

        tvDriverName.setText(datasource.getDriverName());
        tvPhoneNumber.setText(datasource.getDriverPhoneNumber());
        tvMail.setText(datasource.getDriverMail());
        tvCompany.setText(datasource.getDriverCompany());
        tvLocation.setText(datasource.getDriverLocation());

        tvWorkingHour.setText(datasource.getWorkingHour() + " " + getString(R.string.hours).toLowerCase());
        tvMileage.setText(datasource.getMileage() + " " + getString(R.string.km).toLowerCase());

        Date workingHourStartDate = datasource.getWorkingHourStartDate();
        Date workingHourEndDate = datasource.getWorkingHourEndDate();

        if (workingHourStartDate.getMonth() == workingHourEndDate.getMonth()) {
            tvWorkingHourDate.setText(workingHourStartDate.getDate() + " - " + workingHourEndDate.getDate()
                    + " " + TimeObject.getArrayMonth()[workingHourEndDate.getMonth()]);
        } else {
            tvWorkingHourDate.setText(workingHourStartDate.getDate()
                    + " " + TimeObject.getArrayMonth()[workingHourStartDate.getMonth()]
                    + " - " + workingHourEndDate.getDate()
                    + " " + TimeObject.getArrayMonth()[workingHourEndDate.getMonth()]);
        }


        Date mileageStartDate = datasource.getMileageStartDate();
        Date mileageEndDate = datasource.getMileageEndDate();

        if (mileageStartDate.getMonth() == mileageEndDate.getMonth()) {
            tvMileageDate.setText(mileageStartDate.getDate() + " - " + mileageEndDate.getDate()
                    + " " + TimeObject.getArrayMonth()[mileageEndDate.getMonth()]);
        } else {
            tvMileageDate.setText(mileageStartDate.getDate()
                    + " " + TimeObject.getArrayMonth()[mileageStartDate.getMonth()]
                    + " - " + mileageEndDate.getDate()
                    + " " + TimeObject.getArrayMonth()[mileageEndDate.getMonth()]);
        }

        datasource.setFromDate(null);
        datasource.setToDate(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                listener.onButtonBackClick();
                break;
            case R.id.btnWorkingHourDay:
                datasource.setChooseDateRangeForWorkingHour(true);
                listener.onButtonChooseDateRangeClick();
                break;
            case R.id.btnMileageDay:
                datasource.setChooseDateRangeForWorkingHour(false);
                listener.onButtonChooseDateRangeClick();
                break;
            case R.id.btnWorkingHourMonth:
                acMonthWorkingHour.showDropDown();
                break;
            case R.id.btnMileageMonth:
                acMonthMileage.showDropDown();
                break;
            case R.id.btnWorkingHourWeek:
                acWeekWorkingHour.showDropDown();
                break;
            case R.id.btnMileageWeek:
                acWeekMileage.showDropDown();
                break;

        }
    }
}
