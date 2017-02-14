package bhtech.com.cabbydriver.object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen on 12/05/2016.
 */
public class SaleReportObj extends BaseObject {
    private String type;
    private ArrayList<Location> locations;
    private ArrayList<Sale> sales;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("type")) {
                setType(jsonObject.getString("type"));
            }

            if (!jsonObject.isNull("locations")) {
                JSONArray jsonArrayLocation = jsonObject.getJSONArray("locations");
                locations = new ArrayList<>();
                for (int i = 0; i < jsonArrayLocation.length(); i++) {
                    float sale = (float) jsonArrayLocation.getJSONObject(i).getDouble("location_sale");
                    String name = jsonArrayLocation.getJSONObject(i).getString("location_name");
                    locations.add(new Location(sale, name));
                }
            }

            if (!jsonObject.isNull("details")) {
                JSONArray detailsJSONArray = jsonObject.getJSONArray("details");
                sales = new ArrayList<>();
                for (int i = 0; i < detailsJSONArray.length(); i++) {
                    float detail_sale = (float) detailsJSONArray.getJSONObject(i).getDouble("detail_sale");
                    if (detailsJSONArray.getJSONObject(i).has("detail_date")) {
                        String detail_date = detailsJSONArray.getJSONObject(i).getString("detail_date");
                        sales.add(new Sale(detail_sale, detail_date));
                    } else if (detailsJSONArray.getJSONObject(i).has("detail_index")) {
                        sales.add(new Sale(detail_sale, String.valueOf(i)));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class Location {
        private float sale;
        private String name;

        public Location(float sale, String name) {
            this.sale = sale;
            this.name = name;
        }

        public float getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Sale {
        private float sale;
        private String date;

        public Sale(float sale, String date) {
            this.sale = sale;
            this.date = date;
        }

        public float getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
