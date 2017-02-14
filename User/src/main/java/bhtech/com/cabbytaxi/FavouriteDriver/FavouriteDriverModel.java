package bhtech.com.cabbytaxi.FavouriteDriver;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.CarDriverObj;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.UserObj;
import bhtech.com.cabbytaxi.services.NetworkServices;

/**
 * Created by thanh_nguyen on 01/03/2016.
 */
public class FavouriteDriverModel implements FavouriteDriverInterface.Datasource {
    //Presention data
    private ArrayList<CarDriverObj> listCarDriver;
    private int listViewPositionClick = 0;

    public ArrayList<CarDriverObj> getListCarDriver() {
        return listCarDriver;
    }

    public void setListDriver(ArrayList<CarDriverObj> listDriver) {
        this.listCarDriver = listDriver;
    }

    public int getListViewPositionClick() {
        return listViewPositionClick;
    }

    public void setListViewPositionClick(int listViewPositionClick) {
        this.listViewPositionClick = listViewPositionClick;
    }

    public interface OnDeleteFavouriteDriver {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void deleteFavouriteDriver(Context context, final OnDeleteFavouriteDriver onFinish) {
        final bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            String carDriverId = String.valueOf(getListCarDriver().get(getListViewPositionClick()).getObjectID());

            NetworkServices.callAPI(context, ContantValuesObject.DELETE_FAVOURITE_DRIVER_ENDPOINT +
                            carDriverId, NetworkServices.DELETE, headers, null,
                    new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == 0) {
                                    onFinish.Success();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }
                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }

    public interface OnGetListFavouriteDriver {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getListFavouriteDriver(final Context context, final OnGetListFavouriteDriver onFinish) {
        final bhtech.com.cabbytaxi.object.Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("authToken", Arrays.asList(UserObj.getInstance().getUser_token()));

            NetworkServices.callAPI(context, ContantValuesObject.GET_LIST_FAVOURITE_DRIVER_ENDPOINT,
                    NetworkServices.GET, headers, null, new NetworkServices.onCallApi() {
                        @Override
                        public void onFinish(boolean isSuccess, JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt(ContantValuesObject.CODE) == Error.NO_ERROR) {
                                    listCarDriver = new ArrayList<>();
                                    JSONArray results = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                    if (results.length() > 0) {
                                        for (int i = 0; i < results.length(); i++) {
                                            JSONObject favouriteDriverJson = results.getJSONObject(i);
                                            CarDriverObj carDriver = new CarDriverObj();
                                            carDriver.parseJsonToObject(favouriteDriverJson);
                                            listCarDriver.add(carDriver);
                                        }
                                        onFinish.Success();
                                    } else {
                                        error.setError(Error.DATA_NULL);
                                        error.errorMessage = context.getString(R.string.you_have_not_any_favourite_driver);
                                        onFinish.Failure(error);
                                    }
                                } else {
                                    error.setError(Error.WRONG_AUTHTOKEN);
                                    error.errorMessage = context.getString(R.string.wrong_authtoken);
                                    onFinish.Failure(error);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                error.setError(Error.UNKNOWN_ERROR);
                                onFinish.Failure(error);
                            }

                        }
                    });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }
}
