package bhtech.com.cabbydriver.object;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverCompanyObj extends BaseObject {
    private String company_name;
    private String email;
    private String phone_number;
    private String tax_code;
    private String address;
    private DriverObj driver;
    public ArrayList<VehicleTypeObj> listVehicleType;
    public ArrayList<CarObj> listCar;

    private static DriverCompanyObj mInstance = null;

    public static DriverCompanyObj getInstance() {
        if (mInstance == null) {
            mInstance = new DriverCompanyObj();
        }
        return mInstance;
    }

    public DriverCompanyObj() {
        super();
        listVehicleType = new ArrayList<VehicleTypeObj>();
        listCar = new ArrayList<CarObj>();
    }

    @Override
    public int getObjectID() {
        return super.getObjectID();
    }

    @Override
    public void setObjectID(int objectID) {
        super.setObjectID(objectID);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void setEmpty(boolean empty) {
        super.setEmpty(empty);
    }

    @Override
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public Date getUpdatedDate() {
        return super.getUpdatedDate();
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        super.setUpdatedDate(updatedDate);
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getTax_code() {
        return tax_code;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            setCompany_name(jsonObject.getString("company_name"));
            phone_number = jsonObject.getString("phone_number");
            email = jsonObject.getString("email");
            tax_code = jsonObject.getString("tax_code");
            address = jsonObject.getString("address");
            DriverObj driverObj = new DriverObj();
            if (!jsonObject.isNull("driver")) {
                driverObj.parseJsonToObject(jsonObject.getJSONObject("driver"));
                setDriver(driverObj);
            } else {
                //  Skip driver
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("company_name", getCompany_name());
            jsonObject.put("driver", getDriver().parseObjectToJson());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date parseDate(String date, String dateFormat) {
        return super.parseDate(date, dateFormat);
    }

    public DriverObj getDriver() {
        return driver;
    }

    public void setDriver(DriverObj driver) {
        this.driver = driver;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void driverCompanyGetProfileSuccessB() {
    }

    public interface OnGetListVehicleTypeFinish {
        void success();

        void failure(ErrorObj error);
    }

    public interface OnGetListCarFinish {
        void success();

        void failure(ErrorObj error);
    }

    public void getListVehicleType(Context context, final OnGetListVehicleTypeFinish response) {
        final ErrorObj error = new ErrorObj();
        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));
        headers.put("User-Agent", Arrays.asList(ContantValuesObject.USER_AGENT));

        String URLString = ContantValuesObject.GET_LIST_VEHICLE_TYPE_ENDPOINT + "?status=1";

        NetworkObject.callAPI(context, URLString,
                NetworkObject.GET, "Vehicle List",
                headers, null, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                            if (jsonArray != null && jsonArray.length() > 0) {
                                listVehicleType = new ArrayList<VehicleTypeObj>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    VehicleTypeObj vehicle = new VehicleTypeObj();
                                    vehicle.parseJsonToObject(obj);
                                    listVehicleType.add(vehicle);
                                }
                            } else {
                                //  Do nothing
                            }
                            response.success();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error.errorMessage = e.getMessage();
                            response.failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        response.failure(error);
                    }
                });
    }

    public void getListCar(Context context, final OnGetListCarFinish onFinish) {
        final ErrorObj error = new ErrorObj();
        String authToken = TaxiRequestObj.getInstance().getAuthToken();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("authToken", Arrays.asList(authToken));

        Map<String, List<String>> params = new HashMap<>();
        params.put("fields", Arrays.asList("*"));

        NetworkObject.callAPI(context, ContantValuesObject.GET_LIST_CAR_ENDPOINT, NetworkObject.GET,
                "ChooseCar_getListCar", headers, null, new NetworkObject.onCallApi() {
                    @Override
                    public void Success(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt(ContantValuesObject.CODE) == ContantValuesObject.SUCCESS) {
                                JSONArray results = jsonObject.getJSONArray(ContantValuesObject.RESULTS);
                                listCar = new ArrayList<CarObj>();
                                for (int i = 0; i < results.length(); i++) {
                                    CarObj car = new CarObj();
                                    JSONObject jsonCar = results.getJSONObject(i);
                                    car.parseJsonToObject(jsonCar);
                                    for (int j = 0; j < listVehicleType.size(); j++) {
                                        VehicleTypeObj vehicleType = listVehicleType.get(j);
                                        if (Integer.parseInt(car.getVehicle_name()) == vehicleType.getObjectID()) {
                                            car.vehicleIconUrl = vehicleType.getVehicleIcon();
                                            car.vehicleUnavailableIconUrl = vehicleType.getVehicleUnavailableIcon();
                                            break;
                                        }
                                    }
                                    listCar.add(car);
                                }
                                onFinish.success();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            error.setError(ErrorObj.UNKNOWN_ERROR);
                            onFinish.failure(error);
                        }
                    }

                    @Override
                    public void Failure(ErrorObj error) {
                        onFinish.failure(error);
                    }
                });
    }
}
