package admin.example.foodie.models.RestaurantLogIn;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseRestaurant {
    String rest_id;

    @SerializedName("_id")
    public String _id;

    @SerializedName("name")
    public String name;

    @SerializedName("contactNos")
    public List<String> contactNos;

    @SerializedName("foods")
    public List<perFoodClass> foods = new ArrayList<>();

    @SerializedName("address")
    String address;
    @SerializedName("orders")
    public List<String> orders;

    public ResponseRestaurant(String rest_id , String _id , String name , List<String> contactNos , List<perFoodClass> foods , String address , List<String> orders) {
        this.rest_id = rest_id;
        this._id = _id;
        this.name = name;
        this.contactNos = contactNos;
        this.foods = foods;
        this.address = address;
        this.orders = orders;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContactNos() {
        return contactNos;
    }

    public void setContactNos(List<String> contactNos) {
        this.contactNos = contactNos;
    }

    public List<perFoodClass> getFoods() {
        return foods;
    }

    public void setFoods(List<perFoodClass> foods) {
        this.foods = foods;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getOrders() {
        return orders;
    }


    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
