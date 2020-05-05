package telegram.bot.api.entity;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class Venue implements Serializable {
    //region Fields
    private Location location;
    private String title;
    private String address;
    private String foursquare_id;
    //endregion

    //region Constructors
    public Venue() {
    }

    public Venue(Location location) {
        this.location = location;
    }

    public Venue(Location location, String title, String address) {
        this.location = location;
        this.title = title;
        this.address = address;
    }

    public Venue(Location location, String title, String address, String foursquare_id) {
        this(location, title, address);
        this.foursquare_id = foursquare_id;
    }
    //endregion

    //region Getter and Setter
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    //endregion
}
