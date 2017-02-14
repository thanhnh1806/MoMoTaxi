package bhtech.com.cabbytaxi.About;

import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.UserObj;

/**
 * Created by thanh_nguyen on 23/03/2016.
 */
public class AboutCabbyModel {
    public int typeUserLogin() {
        if (UserObj.getInstance().getCompany() == null) {
            return ContantValuesObject.RegisterForPersonal;
        } else {
            return ContantValuesObject.RegisterForCompany;
        }
    }
}
