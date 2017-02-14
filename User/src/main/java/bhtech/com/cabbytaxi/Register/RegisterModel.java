package bhtech.com.cabbytaxi.Register;

import android.content.Context;

import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.SharedPreference;

/**
 * Created by thanh_nguyen02 on 16/12/2015.
 */
public class RegisterModel implements RegisterInterface.Datasource {
    private Context context;
    private int userChooseProfile = ContantValuesObject.RegisterForPersonal;

    public RegisterModel(Context context) {
        this.context = context;
        SharedPreference.set(context, "PositionListCountryUserChoose", "-1");
        SharedPreference.set(context, "UserChooseProfile", String.valueOf(ContantValuesObject.RegisterForPersonal));

    }

    @Override
    public void setUserChooseProfile(int profile) {
        userChooseProfile = profile;
        SharedPreference.set(context, "UserChooseProfile", String.valueOf(profile));
    }

    @Override
    public int getUserChooseProfile() {
        return userChooseProfile;
    }
}
