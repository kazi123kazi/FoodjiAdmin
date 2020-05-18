package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class ResponseRestaurant {

    @SerializedName("restaurant")
    Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
