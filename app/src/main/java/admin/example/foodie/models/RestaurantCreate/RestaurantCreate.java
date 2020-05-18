package admin.example.foodie.models.RestaurantCreate;

import com.google.gson.annotations.SerializedName;

public class RestaurantCreate {
    @SerializedName("super")
    private SuperAdminUser superAdminUser;
    private RestaurantUser restaurant;

    public RestaurantCreate(SuperAdminUser superAdminUser , RestaurantUser restaurant) {
        this.superAdminUser = superAdminUser;
        this.restaurant = restaurant;
    }

    public SuperAdminUser getSuperAdminUser() {
        return superAdminUser;
    }

    public void setSuperAdminUser(SuperAdminUser superAdminUser) {
        this.superAdminUser = superAdminUser;
    }

    public RestaurantUser getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantUser restaurant) {
        this.restaurant = restaurant;
    }
}
