package bhtech.com.cabbytaxi.FavouriteDriver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.PhoneObject;

public class FavouriteDriverController extends SlidingMenuController implements FavouriteDriverInterface.Listener {
    private Context context = this;
    private FavouriteDriverModel model;
    private FavouriteDriverView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favourite_driver_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.favourite_driver));

        model = new FavouriteDriverModel();
        view = new FavouriteDriverView();

        view.setListener(this);
        view.setDatasource(model);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, view).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFavouriteDriver();
    }

    private void getListFavouriteDriver() {
        model.getListFavouriteDriver(context, new FavouriteDriverModel.OnGetListFavouriteDriver() {
            @Override
            public void Success() {
                view.reloadView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                if (error.errorCode == Error.DATA_NULL) {
                    view.reloadView();
                } else {
                    showAlertDialog(error.errorMessage).show();
                }
            }
        });
    }

    @Override
    public void onListViewButtonDeleteClick() {
        showAlertDialog(getString(R.string.are_you_sure_you_want_to_delete_your_account))
                .setPositiveButton(getString(R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.deleteFavouriteDriver(context, new FavouriteDriverModel.OnDeleteFavouriteDriver() {
                            @Override
                            public void Success() {
                                getListFavouriteDriver();
                            }

                            @Override
                            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                                showAlertDialog(error.errorMessage).show();
                            }
                        });
                    }
                })
                .setNegativeButton(getString(R.string.no).toUpperCase(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onListViewIconPhoneClick() {
        int pos = model.getListViewPositionClick();
        String phoneNumber = model.getListCarDriver().get(pos).getDriver().getPhoneNumber();
        Intent i = PhoneObject.makePhoneCall(phoneNumber);
        startActivity(i, null);
    }

    @Override
    public void onListViewIconEmailClick() {
        int pos = model.getListViewPositionClick();
        String email = model.getListCarDriver().get(pos).getDriver().getEmail();
        Intent i = PhoneObject.sendEmail(email);
        startActivity(Intent.createChooser(i, context.getString(R.string.send_email)));
    }
}
