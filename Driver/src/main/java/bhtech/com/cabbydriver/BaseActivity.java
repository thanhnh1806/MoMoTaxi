package bhtech.com.cabbydriver;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import bhtech.com.cabbydriver.ChargeCustomer.ChargeCustomerController;
import bhtech.com.cabbydriver.ChooseRouteToCustomer.ChooseRouteToCustomerController;
import bhtech.com.cabbydriver.ChooseRouteToDestination.ChooseRouteToDestinationController;
import bhtech.com.cabbydriver.DrivingToCustomer.DrivingToCustomerController;
import bhtech.com.cabbydriver.DrivingToDestination.DrivingToDestinationController;
import bhtech.com.cabbydriver.FindCustomer.FindCustomerController;
import bhtech.com.cabbydriver.object.CarDriverObj;
import bhtech.com.cabbydriver.object.ContantValuesObject;
import bhtech.com.cabbydriver.object.PhoneObject;
import bhtech.com.cabbydriver.object.TaxiRequestObj;

public class BaseActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                PhoneObject.hiddenSofwareKeyboard(this, v);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void finishActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }

    protected AlertDialog.Builder showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        return builder;
    }

    protected void addFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    protected void addFragmentv4(int container, android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    protected void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().show(fragment).commit();
    }

    protected void showFragmentv4(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    protected void hideFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().hide(fragment).commit();
    }

    protected void hideFragmentv4(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    protected void checkDriverCarStatus() {
        switch (TaxiRequestObj.getInstance().getStatus()) {
            case ContantValuesObject.TaxiRequestStatusPending:
                startActivity(new Intent(context, FindCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusCancelled:
                startActivity(new Intent(context, FindCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusDriverSelected:
                if (TaxiRequestObj.getInstance().getRequestUser() == null) {
                    startActivity(new Intent(context, ChooseRouteToDestinationController.class));
                } else {
                    startActivity(new Intent(context, ChooseRouteToCustomerController.class));
                }
                break;
            case ContantValuesObject.TaxiRequestStatusUserConfirmed:
                startActivity(new Intent(context, ChooseRouteToCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusDrivingToPassenger:
                startActivity(new Intent(context, DrivingToCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusWaitingPickupPassenger:
                startActivity(new Intent(context, DrivingToCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusWithPassenger:
                startActivity(new Intent(context, DrivingToCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusChooseRoute:
                startActivity(new Intent(context, ChooseRouteToDestinationController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusDrivingToDestination:
                startActivity(new Intent(context, DrivingToDestinationController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusCharged:
                startActivity(new Intent(context, ChargeCustomerController.class));
                break;
            case ContantValuesObject.TaxiRequestStatusPaid:
                startActivity(new Intent(context, FindCustomerController.class));
                break;
            default:
                Log.w("Driver_car_status", String.valueOf(CarDriverObj.getInstance().getStatus()));
        }
        finishActivity();
    }
}
