package bhtech.com.cabbydriver.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CardObj extends BaseObject {
    private float amount_limit;
    private BaseEnums.PaymentType payment_type;// false -> Monthly
    private BaseEnums.CardType card_type;
    private String card_number;
    private String card_holder_name;
    private String cvv_code;
    private Date card_expiration_date;
    private UserObj owner;
    private BaseEnums.CardUserType card_user_type; // card for personal or company

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

    @Override
    public void parseJsonToObject(JSONObject jsonObject) {
        try {
            setAmount_limit((float) jsonObject.getDouble("amount_limit"));
            setPayment_type(BaseEnums.PaymentType.values()[jsonObject.getInt("payment_type")]);
            setCard_type(BaseEnums.CardType.values()[jsonObject.getInt("card_type")]);
            setCard_number(jsonObject.getString("card_number"));
            setCard_holder_name(jsonObject.getString("card_holder_name"));
            setCvv_code(jsonObject.getString("cvv_code"));
            setCard_expiration_date(parseDate(jsonObject.getString("card_expiration_date"), "yyyy-MM-dd"));

            UserObj user = new UserObj();
            user.parseJsonToObject(jsonObject.getJSONObject("owner"));
            setOwner(user);

            setCard_user_type(BaseEnums.CardUserType.values()[jsonObject.getInt("payment_type")]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject parseObjectToJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amount_limit", getAmount_limit());
            jsonObject.put("payment_type", getPayment_type());
            jsonObject.put("card_type", getCard_type());
            jsonObject.put("card_number", getCard_number());
            jsonObject.put("card_holder_name", getCard_holder_name());
            jsonObject.put("cvv_code", getCvv_code());
            jsonObject.put("card_expiration_date", getCard_expiration_date());
            jsonObject.put("owner", getOwner().parseObjectToJson());
            jsonObject.put("card_user_type", getCard_user_type());
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

    // customer id
    public void setCard_type(BaseEnums.CardType card_type) {
        this.card_type = card_type;
    }

    public void setAmount_limit(float amount_limit) {
        this.amount_limit = amount_limit;
    }

    public void setPayment_type(BaseEnums.PaymentType payment_type) {
        this.payment_type = payment_type;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public void setCvv_code(String cvv_code) {
        this.cvv_code = cvv_code;
    }

    public void setCard_expiration_date(Date card_expiration_date) {
        this.card_expiration_date = card_expiration_date;
    }

    public void setOwner(UserObj owner) {
        this.owner = owner;
    }

    public void setCard_user_type(BaseEnums.CardUserType card_user_type) {
        this.card_user_type = card_user_type;
    }

    public BaseEnums.CardType getCard_type() {
        return card_type;
    }

    public float getAmount_limit() {
        return amount_limit;
    }

    public BaseEnums.PaymentType getPayment_type() {
        return payment_type;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public String getCvv_code() {
        return cvv_code;
    }

    public Date getCard_expiration_date() {
        return card_expiration_date;
    }

    public UserObj getOwner() {
        return owner;
    }

    public BaseEnums.CardUserType getCard_user_type() {
        return card_user_type;
    }


    private void getCardInfoSuccess() {
    }

    private void addNew() {
    }

    private void updateCard() {
    }
}
