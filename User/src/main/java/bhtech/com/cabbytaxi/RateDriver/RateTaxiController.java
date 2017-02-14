package bhtech.com.cabbytaxi.RateDriver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import bhtech.com.cabbytaxi.FavouriteDriver.FavouriteDriverController;
import bhtech.com.cabbytaxi.FindTaxi.FindTaxiController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;

public class RateTaxiController extends SlidingMenuController implements RateTaxiInterface.Delegate {
    private Context context;
    private RateTaxiView rateTaxiView;
    private RateTaxiShareFacebookDialog dialog;
    private RateTaxiModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateToolbarTitle(getString(R.string.rate_the_driver));
        context = RateTaxiController.this;
        getLayoutInflater().inflate(R.layout.activity_rate_taxi_controller, mFrameSlidingMenu);
        model = new RateTaxiModel();
        rateTaxiView = new RateTaxiView();
        dialog = new RateTaxiShareFacebookDialog(this);

        rateTaxiView.setDelegate(this);
        rateTaxiView.setDatasource(model);
        dialog.setDelegate(this);
        dialog.setDatasource(model);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.rate_taxi_container, rateTaxiView).commit();
        }
    }

    @Override
    public void onCreateViewFinish() {
        rateTaxiView.reloadView();
    }

    @Override
    public void onShareFacebookDialogCreateViewFinish() {
        dialog.reloadView();
    }

    @Override
    public void onButtonAddAsFavouriteDriverClick() {
        model.addFavouriteDriver(context, new RateTaxiModel.OnAddFavouriteDriver() {
            @Override
            public void Success() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startActivity(new Intent(context, FavouriteDriverController.class));
                        finishActivity();
                    }
                }, 1000);
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                if (error.errorCode != Error.FAVOURITE_DRIVER_ALREADY_EXISTED) {
                    rateTaxiView.reloadViewOnButtonAddClick();
                }
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonDoneClick() {
        SharedPreference.set(context, ContantValuesObject.CURRENT_REQUEST_ID, "");
        model.rateTaxi(context, new RateTaxiModel.onRateTaxi() {
            @Override
            public void Success() {
                TaxiRequestObj.getInstance().setStatus(ContantValuesObject.TaxiRequestStatusCancelled);
                startActivity(new Intent(context, FindTaxiController.class));
                finishActivity();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonShareClick() {
        dialog.show();
    }

    @Override
    public void onUserAddComment(String s) {
        //TODO get string user comment to post FB
    }

    @Override
    public void onButtonCancelShareFacebookClick() {
        dialog.dismiss();
    }

    @Override
    public void onButtonPostClick() {
        //TODO post to FB
        dialog.dismiss();
    }

    @Override
    public void onButtonDownloadAppClick() {
        //TODO go to link download on Google Play
    }
}
