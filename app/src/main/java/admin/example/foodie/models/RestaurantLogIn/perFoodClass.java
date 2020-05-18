package admin.example.foodie.models.RestaurantLogIn;

public class perFoodClass {
    String foodid,_id;
    int price;

    public perFoodClass(String foodid , String _id , int price) {
        this.foodid = foodid;
        this._id = _id;
        this.price = price;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
