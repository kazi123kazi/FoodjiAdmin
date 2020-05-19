package admin.example.foodie.org.example.foodie.apifetch;


import java.util.List;

import admin.example.foodie.models.Food;
import admin.example.foodie.models.Foodid;
import admin.example.foodie.models.Order;
import admin.example.foodie.models.OrderFood;
import admin.example.foodie.models.ResponseRestaurant;
import admin.example.foodie.models.ResponseUser;
import admin.example.foodie.models.Restaurant;
import admin.example.foodie.models.RestaurantCreate.RestaurantCreate;
import admin.example.foodie.models.RestaurantLogIn.ResponseRestaurantUser;
import admin.example.foodie.models.RestaurantLogIn.RestaurantUser;

import admin.example.foodie.models.UpdateInfo;
import admin.example.foodie.models.UpdateResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FoodieClient {

    //get all user info
    @GET("restaurant/me")
    Call<ResponseUser> getData(@Header("Authorization") String token);

    @GET("restaurant/{id}")
    Call<ResponseRestaurant> getFood(@Path("id") String id);
    //post food to restaurant
    @POST("food")
    Call<Foodid> postFood(@Header("Authorization") String token, @Body Food food);
    //create restaurant
    @POST("restaurant")
    Call<ResponseUser> createRestaurant(@Body RestaurantCreate restaurantCreate);
    //log in user
    @POST("restaurant/login")
    Call<ResponseRestaurantUser> logInRestaurant(@Body RestaurantUser restaurantUser);
    //Logout restaurant
    @POST("restaurant/logout")
    Call<Void> Logout(@Header("Authorization") String token);

    @Multipart
    @POST("restaurant/image")
    Call<ResponseBody> postImage(@Header("Authorization") String token, @Part MultipartBody.Part image);


    @GET("restaurant/notify")
    Call<List<Order>> getNotified(@Header("Authorization") String token);


    @Multipart
    @POST("food/image/{id}")
    Call<ResponseBody> postFoodImage(@Path("id") String id,@Header("Authorization") String token,@Part MultipartBody.Part image);


    @HTTP(method = "DELETE", path = "restaurant/food", hasBody = true)
    Call<ResponseBody> deleteFood(@Header("Authorization") String token, @Body OrderFood food);


    //update user info
    @PATCH("restaurant")
    Call<UpdateResponse> updateInfo(@Header("Authorization") String token, UpdateInfo updateInfo);



}