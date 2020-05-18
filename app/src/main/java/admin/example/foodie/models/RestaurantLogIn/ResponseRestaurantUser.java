package admin.example.foodie.models.RestaurantLogIn;

import com.google.gson.annotations.SerializedName;

public class ResponseRestaurantUser {
    @SerializedName("token")
    String token;
    @SerializedName("restaurant")
    ResponseRestaurant restaurant;

//    public ResponseRestaurantUser() {
//    }


    public ResponseRestaurantUser(String token , ResponseRestaurant restaurant) {
        this.token = token;
        this.restaurant = restaurant;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(ResponseRestaurant restaurant) {
        this.restaurant = restaurant;
    }
}
