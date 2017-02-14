package bhtech.com.cabbydriver.object;

public class ShareTaxiLocationObject {
    private String address;
    private String mEstimatedTime;
    private String mDistance;

    public void setAddress(String address) {
        address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEstimatedTime(String mEstimatedTime) {
        mEstimatedTime = mEstimatedTime;
    }

    public String getEstimatedTime() {
        return mEstimatedTime;
    }

    public void setDistance(String mDistance) {
        mDistance = mDistance;
    }

    public String getDistance() {
        return mDistance;
    }
}
