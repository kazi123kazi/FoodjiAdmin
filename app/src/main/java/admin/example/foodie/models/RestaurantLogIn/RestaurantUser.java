package admin.example.foodie.models.RestaurantLogIn;

import com.google.gson.annotations.SerializedName;

public class RestaurantUser {
    @SerializedName("rest_id")
    String rest_id;
    @SerializedName("password")
    String password;

    public RestaurantUser(String rest_id , String password) {
        this.rest_id = rest_id;
        this.password = password;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
