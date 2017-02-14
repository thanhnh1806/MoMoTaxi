package bhtech.com.cabbydriver.ChooseCar;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.SupportClasses.CircleImageView;
import bhtech.com.cabbydriver.object.CarObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;

/**
 * Created by Le Anh Tuan on 1/21/2016.
 */
public class ChooseCarView extends Fragment {
    private ChooseCarInterface.Delegate _delegate;
    private ChooseCarInterface.Datasource _datasource;
    private int result = 0;
    private String _good = "";

    public void setDelegate(ChooseCarInterface.Delegate delegate) {
        this._delegate = delegate;
    }

    public void setDatasource(ChooseCarInterface.Datasource datasource) {
        this._datasource = datasource;
    }

    public ChooseCarView() {

    }

    TextView txtDay, txtTime, txtDateMounth, txtCarsellected, txtgood, txtNameDriver,
            txtgood_chosen, txtNameDriverChosen, txtCarMileagePrevious, txtPopup;
    Button btnStartWork;
    ListView lstCar;
    ImageView img_avartar, img_chosen_car;
    LinearLayout lnChooseCar_Header, lnChosenCar_Header, lnChoosencar_mile, lnChooseCar_mile, layoutChooseCar;
    EditText edtEnter_mile;
    CircleImageView cricleImageView;
    ChooseCarAdapter adapter;
    ArrayList<CarObj> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmemt_driver_choose_car_view, container, false);
        txtDay = (TextView) v.findViewById(R.id.tv_day);
        txtTime = (TextView) v.findViewById(R.id.tv_time);
        txtDateMounth = (TextView) v.findViewById(R.id.tv_datemounth);
        txtCarsellected = (TextView) v.findViewById(R.id.tv_carid);
        txtgood = (TextView) v.findViewById(R.id.tv_good_choose);
        txtgood_chosen = (TextView) v.findViewById(R.id.tv_good_chosen);
        txtNameDriver = (TextView) v.findViewById(R.id.tv_name_choose);
        txtNameDriverChosen = (TextView) v.findViewById(R.id.tv_name_chosen);
        txtCarMileagePrevious = (TextView) v.findViewById(R.id.tv_previous_mile);
        txtPopup = (TextView) v.findViewById(R.id.tv_odometer);
        btnStartWork = (Button) v.findViewById(R.id.btnStartWork);
        edtEnter_mile = (EditText) v.findViewById(R.id.edt_enter_previus_mile);
        lstCar = (ListView) v.findViewById(R.id.lst_listcar);

        lnChooseCar_Header = (LinearLayout) v.findViewById(R.id.ln_choosecar_header);
        lnChosenCar_Header = (LinearLayout) v.findViewById(R.id.ln_chosencar_header);
        lnChoosencar_mile = (LinearLayout) v.findViewById(R.id.ln_chosencar_mile);
        lnChooseCar_mile = (LinearLayout) v.findViewById(R.id.ln_choosecar_mile);
        lnChooseCar_Header.setVisibility(View.VISIBLE);
        lnChooseCar_mile.setVisibility(View.VISIBLE);
        lnChoosencar_mile.setVisibility(View.INVISIBLE);
        lnChosenCar_Header.setVisibility(View.INVISIBLE);
        layoutChooseCar = (LinearLayout) v.findViewById(R.id.layoutChooseCar);
        img_avartar = (ImageView) v.findViewById(R.id.img_avatar);
        img_chosen_car = (ImageView) v.findViewById(R.id.img_chosen_car);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
        cricleImageView = new CircleImageView(bm);
        img_avartar.setImageDrawable(cricleImageView);

        txtPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.showPopup();
            }
        });
        layoutChooseCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.changeCar();
            }
        });
        btnStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.onButtonStartClick();
            }
        });

        edtEnter_mile.addTextChangedListener(new TextWatcher() {
            boolean isManualChange = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }

                try {
                    String value = s.toString().replace(",", "");
                    String reverseValue = new StringBuilder(value).reverse()
                            .toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    isManualChange = true;
                    edtEnter_mile.setText(finalValue.reverse());
                    edtEnter_mile.setSelection(finalValue.length());
                } catch (Exception e) {
                    // Do nothing since not a number
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _delegate = null;
        _datasource = null;
    }

    public void reloadView() {
        txtTime.setText(_datasource.getCurrentTime());
        txtDay.setText(_datasource.getCurrentDay());
        txtDateMounth.setText(_datasource.getCurrentDateMonth());
        txtNameDriver.setText(_datasource.getDriverName());
        txtNameDriverChosen.setText(_datasource.getDriverName());
        result = _datasource.getFirstText();
        switch (result) {
            case 0:
                _good = getResources().getString(R.string.good_morning);
                break;
            case 1:
                _good = getResources().getString(R.string.good_afternoon);
                break;
            case 2:
                _good = getResources().getString(R.string.good_evening);
                break;
        }
        txtgood.setText(_good);
        txtgood_chosen.setText(_good);

        list = _datasource.getListCar();
        adapter = new ChooseCarAdapter(getActivity(), list);
        lstCar.setAdapter(adapter);
        lstCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _datasource.setPosition(position);
                _delegate.onItemClick();
            }
        });
    }

    public void loadVisiable() {
        lnChooseCar_Header.setVisibility(View.VISIBLE);
        lnChooseCar_mile.setVisibility(View.VISIBLE);
        lnChoosencar_mile.setVisibility(View.INVISIBLE);
        lnChosenCar_Header.setVisibility(View.INVISIBLE);
        edtEnter_mile.setText(null);
    }

    public void loadVisiableAfterClick() {
        int position = _datasource.getPosition();
        lnChooseCar_Header.setVisibility(View.INVISIBLE);
        lnChooseCar_mile.setVisibility(View.INVISIBLE);
        lnChoosencar_mile.setVisibility(View.VISIBLE);
        lnChosenCar_Header.setVisibility(View.VISIBLE);
        txtCarsellected.setTextColor(Color.BLACK);
        txtCarsellected.setText(_datasource.getListCar().get(position).getNumber());
        txtCarMileagePrevious.setText(_datasource.getCarMileagePrevious(position) + "");
        _datasource.setCarDriverObj_Car(_datasource.getListCar().get(position));
        Ion.with(this).load(ContantValuesObject.DOMAIN_IMAGE + _datasource.getListCar().get(position).vehicleIconUrl).intoImageView(img_chosen_car);
    }

    public void setTxtCarMunber(String carMunber) {
        txtCarsellected.setText(carMunber);
    }

    public void setTxtCarMileagePrevious(double mileage) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(mileage);
        txtCarMileagePrevious.setText(yourFormattedString);
    }

    public float getEnterMileage() {
        if (edtEnter_mile.getText().toString().equals("")) {
            return 0;
        } else {
            return Float.parseFloat(edtEnter_mile.getText().toString().replaceAll(",", ""));
        }
    }
}
