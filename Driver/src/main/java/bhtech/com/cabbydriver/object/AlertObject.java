package bhtech.com.cabbydriver.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by duongpv on 4/21/16.
 */
public class AlertObject extends BaseObject {
    public String alertAddress;
    public Date alertDateTime;
    public String alertMessageTitle;
    public String alertMessageBody;
    public int alertType;

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        super.parseJsonToObject(jsonObject);
        try {
            if (jsonObject.has("type") && !jsonObject.isNull("type")) {
                alertType = jsonObject.getInt("type");
            } else {
                alertType = -1;
            }

            if (jsonObject.has("message_title") && !jsonObject.isNull("message_title")) {
                alertMessageTitle = jsonObject.getString("message_title");
            } else {
                alertMessageTitle = "";
            }

            if (jsonObject.has("message_body") && !jsonObject.isNull("message_body")) {
                alertMessageBody = jsonObject.getString("message_body");
            } else {
                alertMessageBody = "";
            }

            if (jsonObject.has("location") && !jsonObject.isNull("location")) {
                alertAddress = jsonObject.getString("location");
            } else {
                alertAddress = "";
            }

            alertDateTime = new Date();
            if (jsonObject.has("create_date") && !jsonObject.isNull("create_date")) {
                String dateTimeString = jsonObject.getString("create_date");
                SimpleDateFormat dateFormat = new SimpleDateFormat(this.someShittyDateTimeFormat);
                try {
                    alertDateTime = dateFormat.parse(dateTimeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    alertDateTime = null;
                }
            } else {
                alertDateTime = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
