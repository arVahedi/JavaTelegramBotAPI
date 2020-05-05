package telegram.bot.api.entity;

import java.io.Serializable;

public class Contact implements Serializable {
    //region Fields
    private String phone_number;
    private String first_name;
    private String last_name;
    private int user_id;
    //endregion

    //region Constructor
    public Contact(){}

    public Contact(String phone_number, String first_name){
        this.phone_number = phone_number;
        this.first_name = first_name;
    }

    public Contact(String phone_number, String first_name, String last_name){
        this(phone_number, first_name);
        this.last_name = last_name;
    }

    public Contact(String phone_number, String first_name, String last_name, int user_id){
        this(phone_number, first_name);
        this.last_name = last_name;
        this.user_id = user_id;
    }

    public Contact(String phone_number, String first_name, String last_name, User user){
        this(phone_number, first_name);
        this.last_name = last_name;
        this.user_id = user.getId();
    }
    //endregion

    //region Getter and Setter
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }
    //endregion
}
