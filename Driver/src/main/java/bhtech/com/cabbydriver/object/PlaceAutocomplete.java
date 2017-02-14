package bhtech.com.cabbydriver.object;

/**
 * Created by thanh_nguyen on 08/03/2016.
 */
public class PlaceAutocomplete {
    public CharSequence placeId;
    public CharSequence description;

    public PlaceAutocomplete(CharSequence placeId, CharSequence description) {
        this.placeId = placeId;
        this.description = description;
    }

    @Override
    public String toString() {
        return description.toString();
    }
}
