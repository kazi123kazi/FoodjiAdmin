package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Restaurant {
    @SerializedName("id")
    public String id;

    @SerializedName("_id")
    String _id;
    @SerializedName("name")
    public String name;
    @SerializedName("contactNos")
    public List<String> contactNos;
    @SerializedName("foods")
    public List<Food> foods = new ArrayList<>();
    @SerializedName("address")
    String address;
    Map<Long, List<Food>> orderList = new HashMap<>();


    //Constructor

    Restaurant(String name) {
        this.name = name;
    }


    public Restaurant(List<String> contactNos, String address) {
        this.contactNos = contactNos;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public Map<Long, List<Food>> getOrderList() {
        return orderList;
    }






    public String getName() {
        return name;
    }

    public List<Food> getFoods() {
        return foods;
    }
/*    public int hashCode() {
        return Long.hashCode(id);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Restaurant) {
            Restaurant r = (Restaurant) o;
            return r.id == this.id;
        }
        return false;
    }

    public synchronized void trackOrder(Order order) {
        for (Food food: orderList.get(order.id)) {
            System.out.println("Food: " + food.name + " -- Status: " + food.status);
        }
    }*/
}