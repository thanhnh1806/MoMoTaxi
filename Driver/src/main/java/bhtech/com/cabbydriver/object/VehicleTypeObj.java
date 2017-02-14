package bhtech.com.cabbydriver.object;

import android.webkit.URLUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duongpv on 4/1/16.
 */
public class VehicleTypeObj extends BaseObject {
    private String vehicleName;
    private Integer status;
    private String vehicleIconSelected;
    private String vehicleIconDeselected;
    private String vehicleIconMarker;
    private String vehicleIcon;
    private String vehicleUnavailableIcon;

    public VehicleTypeObj () {
        super();
        vehicleName = "";
        status = 0;
    }

    public VehicleTypeObj (JSONObject jsonObj) {
        super();
        this.parseJsonToObject(jsonObj);
    }

    @Override
    public void parseJsonToObject (JSONObject jsonObject) {
        try {
            setObjectID(jsonObject.getInt("id"));
            this.status = jsonObject.getInt("status");
            this.vehicleName = jsonObject.getString("name");
            vehicleIconSelected = jsonObject.getString("icon_select");
            vehicleIconDeselected = jsonObject.getString("icon_deselect");
            vehicleIconMarker = jsonObject.getString("icon_marker");
            vehicleIcon = jsonObject.getString("icon_choose");
            vehicleUnavailableIcon = jsonObject.getString("icon_choose_select");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getVehicleName() {
        return this.vehicleName;
    }

    public int getStatus() {
        return this.status;
    }

    public String getVehicleIconSelected() {
        return this.vehicleIconSelected;
    }

    public String getVehicleIconDeselected() {
        return this.vehicleIconDeselected;
    }
    public String getVehicleIconMarker() {
        return this.vehicleIconMarker;
    }

    public String getVehicleIcon() {
        return this.vehicleIcon;
    }

    public String getVehicleUnavailableIcon() {
        return vehicleUnavailableIcon;
    }
}
