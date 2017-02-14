package bhtech.com.cabbytaxi.FavouriteDriver;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.FavouriteDriver.FavouriteDriverAdapter;
import bhtech.com.cabbytaxi.FavouriteDriver.FavouriteDriverInterface;

public class FavouriteDriverView extends Fragment {
    private FavouriteDriverInterface.Listener listener;
    private FavouriteDriverInterface.Datasource datasource;

    public void setListener(FavouriteDriverInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(FavouriteDriverInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public FavouriteDriverView() {

    }

    private ListView listFavouriteDriver;
    private FavouriteDriverAdapter favouriteDriverAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite_driver_view, container, false);
        listFavouriteDriver = (ListView) v.findViewById(R.id.lst_favouriteDriver);
        return v;
    }

    public void reloadView() {
        favouriteDriverAdapter = new FavouriteDriverAdapter(getActivity(),
                datasource.getListCarDriver(), listener, datasource);
        listFavouriteDriver.setAdapter(favouriteDriverAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
