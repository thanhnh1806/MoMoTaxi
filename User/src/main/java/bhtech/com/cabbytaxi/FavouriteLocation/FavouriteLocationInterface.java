package bhtech.com.cabbytaxi.FavouriteLocation;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bhtech.com.cabbytaxi.object.LocationObject;

/**
 * Created by thanh_nguyen on 02/02/2016.
 */
public class FavouriteLocationInterface {
    public interface Listener {
        void onListViewCreateViewFinish();

        void onListMonthItemClick();

        void onButtonAddClick();

        void onListItemClick();

        void onListViewButtonEditClick();

        void onListViewButtonDeleteClick();

        void onSpinnerItemClick();

        void onButtonAddFavouriteLocationClick();

        void onMapClick();

        void onEditViewButtonBackClick();

        void onEditViewButtonEditClick();

        void onChooseLocation();
    }

    public interface Datasource {
        ArrayList<LocationObject> getListFavouriteLocation();

        ArrayList<String> getListMonth();

        void setListItemPositionClick(int position);

        int getListItemPositionClick();

        int getMonthUserChoose();

        void setMonthUserChoose(int position);

        void setMyLocation(LatLng location);

        public LatLng getMyLocation();

        void setLatLngMarker(LatLng latLngMarker);

        void setLatLngMapClick(LatLng latLng);

        LatLng getLatLngMarker();

        String getCompleteAddress();

        MarkerOptions getMarkerOptionsAddView(Context context);

        MarkerOptions getMarkerOptionsEditView(Context context);

        boolean isAddViewVisible();

        void setAddViewVisible(boolean b);

        void setCompleteAddress(String completeAddress);

        LatLng getSouthwest();

        LatLng getNortheast();
    }
}
