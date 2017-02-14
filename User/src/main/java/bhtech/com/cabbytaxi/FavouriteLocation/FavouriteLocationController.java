package bhtech.com.cabbytaxi.FavouriteLocation;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.maps.model.Marker;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.SupportClass.OnMapViewListener;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.UserObj;

public class FavouriteLocationController extends SlidingMenuController implements
        OnMapViewListener, FavouriteLocationInterface.Listener {
    private Context context = this;
    private FavouriteLocationAddView addView;
    private FavouriteLocationModel model = new FavouriteLocationModel(context);
    private FavouriteLocationMapView mapView = new FavouriteLocationMapView();
    private FavouriteLocationListView listView = new FavouriteLocationListView();
    private FavouriteLocationEditView editView = new FavouriteLocationEditView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favourite_location_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.favourite_location));

        if (UserObj.getInstance().getLocation() != null) {
            model.getGeometryBounds(new FavouriteLocationModel.onGetGeometryBounds() {
                @Override
                public void Success() {
                    addView = new FavouriteLocationAddView();
                    addView.setListener(FavouriteLocationController.this);
                    addView.setDatasource(model);
                    addView.setFragmentActivity(FavouriteLocationController.this);
                    addFragment(R.id.view_container, addView);
                    hideFragment(addView);
                }

                @Override
                public void Failure(Error error) {
                    showAlertDialog(error.errorMessage);
                }
            });
        } else {

        }

        listView.setListener(this);
        listView.setDatasource(model);

        mapView.setListener(this);
        mapView.setDatasource(model);

        editView.setListener(this);
        editView.setDatasource(model);

        addFragmentv4(R.id.map_container, mapView);
        addFragment(R.id.view_container, listView);
        addFragment(R.id.view_container, editView);
        hideAllFragment();
        showFragment(listView);
    }

    private void hideAllFragment() {
        if (listView != null) {
            hideFragment(listView);
        }
        if (addView != null) {
            hideFragment(addView);
        }
        if (editView != null) {
            hideFragment(editView);
        }
    }

    @Override
    public void onListViewCreateViewFinish() {
        model.getListFavouriteLocation(context, new FavouriteLocationModel.onGetListFavouriteLocation() {
            @Override
            public void Success() {
                listView.reloadView();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onListMonthItemClick() {

    }

    @Override
    public void onButtonAddClick() {
        hideAllFragment();
        showFragment(addView);
        model.setAddViewVisible(true);
        if (model.getMyLocation() != null) {
            model.setLatLngMarker(model.getMyLocation());
            model.getCompleteAddress(new FavouriteLocationModel.OnGetCompleteAddress() {
                @Override
                public void Success() {
                    mapView.reloadView();
                }

                @Override
                public void Failure(Error error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });
        } else {

        }
    }

    @Override
    public void onListItemClick() {
        listView.reloadView();
    }

    @Override
    public void onListViewButtonEditClick() {
        hideAllFragment();
        showFragment(editView);
        model.setLatLngMarker(model.getListFavouriteLocation()
                .get(model.getListItemPositionClick()).getLatLng());
        model.setAddViewVisible(false);
        editView.reloadView();
        mapView.reloadView();
    }

    @Override
    public void onListViewButtonDeleteClick() {
        showAlertDialog(getString(R.string.are_you_sure_delete_favourite_location))
                .setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        model.deleteFavouriteLocation(context, new FavouriteLocationModel.OnDeleteFavouriteLocation() {
                            @Override
                            public void Success() {
                                onListViewCreateViewFinish();
                            }

                            @Override
                            public void Failure(bhtech.com.cabbytaxi.object.Error error) {

                            }
                        });
                    }
                })
                .setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onSpinnerItemClick() {
        model.sortListByMonth();
        listView.reloadView();
    }

    @Override
    public void onButtonAddFavouriteLocationClick() {
        model.addFavouriteLocation(context, new FavouriteLocationModel.onAddFavouriteLocation() {
            @Override
            public void Success() {
                onBackPressed();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onMapClick() {
        model.getCompleteAddress(new FavouriteLocationModel.OnGetCompleteAddress() {
            @Override
            public void Success() {
                mapView.reloadView();
                editView.reloadView();
            }

            @Override
            public void Failure(Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onEditViewButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void onEditViewButtonEditClick() {
        model.editFavouriteLocation(context, new FavouriteLocationModel.OnEditFavouriteLocation() {
            @Override
            public void Success() {
                onBackPressed();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onChooseLocation() {
        mapView.reloadView();
    }

    @Override
    public void onGoogleMapReady() {

    }

    @Override
    public void onMyLocationChange() {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onBackPressed() {
        if (listView.isVisible()) {
            super.onBackPressed();
        } else {
            hideAllFragment();
            showFragment(listView);
            onListViewCreateViewFinish();
        }
    }
}
