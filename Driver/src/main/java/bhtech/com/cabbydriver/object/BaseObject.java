package bhtech.com.cabbydriver.object;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseObject {
    protected String UtcDateTimeFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    protected String someShittyDateTimeFormat = "yyyy-MM-dd hh:mm:ss";
    private int objectID;
    private boolean empty; //empty object only has objectID
    private Date createdDate; //create_date
    private Date updatedDate; //update_date

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void parseJsonToObject(JSONObject jsonObject) {
    }

    public JSONObject parseObjectToJson() {
        return null;
    }

    public Date parseDate(String date, String dateFormat) {
        String dateFormat2 = "yyyy-MM-dd'T'hh:mm:ss'Z'";
        try {
            return new SimpleDateFormat(dateFormat2).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}