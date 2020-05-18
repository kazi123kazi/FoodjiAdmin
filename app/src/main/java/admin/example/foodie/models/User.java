package admin.example.foodie.models;


/*{
        "name": "test",
        "email": "test@test.com",
        "password": "testtest",
        "address": "test address",
        "phone": "+918602313604"
        }*/

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("address")
    String address;
    @SerializedName("phone")
    String phone;
    @SerializedName("token")
    String token;
    @SerializedName("orders")
    List<Order> orders;
    @SerializedName("_id")
    String _id;

    public User(String name, String email, String password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
