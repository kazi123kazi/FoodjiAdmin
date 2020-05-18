package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("name")
    public String foodName;

    @SerializedName("foodid")
    public Foodid foodid;
    @SerializedName("price")
    public String price;
    @SerializedName("id")
    String _id;
    @SerializedName("quantity")
    int count;



    public Food(String foodName , String price) {
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Food(Foodid foodid, String _id, String price) {
        this.foodid = foodid;
        this._id = _id;
        this.price = price;
        this.count = 0;
    }

    public Food(String _id, int count) {
        this._id = _id;
        this.count = count;
    }

    public Food(String price) {
        //      this.name = name;
        this.price = price;
    }


    public Food(Foodid foodid) {
        this.foodid = foodid;
    }

    public String getPrice() {
        return price;
    }

    public Foodid getFoodid() {
        return foodid;
    }


    public void addCount() {
        this.count = count + 1;
//setCount(this.count);
    }


    public void decreaseCount() {
        this.count = count - 1;
        setCount(this.count);
    }

    public String get_id() {
        return _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
