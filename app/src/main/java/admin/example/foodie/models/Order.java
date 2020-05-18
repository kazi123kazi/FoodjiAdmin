package admin.example.foodie.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import admin.example.foodie.models.OrderFood;
import admin.example.foodie.models.Payment;
import admin.example.foodie.models.Restaurant;
import admin.example.foodie.models.User;


public class Order {


    public double totalPrice;
    @SerializedName("user")
    public User user;
    @SerializedName("restaurant")
    Restaurant restaurant;
    @SerializedName("payment")
    Payment payment;
    @SerializedName("_id")
    String _id;
    @SerializedName("foods")
    List<OrderFood> foodList = new ArrayList<>();

    @SerializedName("status")
    String status;
    @SerializedName("restaurantId")
    String restaurantId;

    List<Restaurant> restaurantList;

    public Order(String restaurantId, List<OrderFood> foodList, Payment payment) {
        this.payment = payment;
        this.foodList = foodList;
        this.restaurantId = restaurantId;
    }


    public Payment getPayment() {
        return payment;
    }


    public User getUser() {
        return user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderFood> getFoodList() {
        return foodList;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    /*
    foods	[...]
    restaurant	{...}
    user	{...}
    deliveryGuy	{...}
    status	string
    Status of the order- RECIEVED/ LEFT/ DELIVERED/ CANCELLED

    payment*	{
        description:
        Details of payment

        method	string
        Mode of payment- COD/ UPI/ CARD

        status	string
        Payment status- PAID/ UNPAID

        total	string
        Total amount to be paid

    }
}*/

    public String getStatus() {
        return status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String get_id() {
        return _id;
    }
}


