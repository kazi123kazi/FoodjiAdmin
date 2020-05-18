package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {


    @SerializedName("image")
    String image;
    @SerializedName("name")
    String name;
    @SerializedName("token")

    String token;

    @SerializedName("id")
    String id;
    @SerializedName("address")
    String address;


    public ResponseUser(String token ) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    public String getImage() {
        return image;
    }
}
