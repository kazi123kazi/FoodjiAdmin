package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Foodid {

    @SerializedName("name")
    public String name;
    @SerializedName("resaturants")
    List<String> restaurants;
    @SerializedName("_id")
    String _id;
    @SerializedName("__v")
    String __v;

    public Foodid(String name, List<String> restaurants, String _id, String __v) {
        this.name = name;
        this.restaurants = restaurants;
        this._id = _id;
        this.__v = __v;
    }

    public Foodid(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String get_id() {
        return this._id;
    }
}
