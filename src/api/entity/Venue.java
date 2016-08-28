package api.entity;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class Venue implements Serializable {
    private Location location;
    private String title;
    private String address;
    private String foursquare_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoursquare_id() {
        return foursquare_id;
    }

    public void setFoursquare_id(String foursquare_id) {
        this.foursquare_id = foursquare_id;
    }
}
