package admin.example.foodie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.foodie.R;

import admin.example.foodie.models.ResponseUser;
import admin.example.foodie.models.RestaurantCreate.RestaurantCreate;
import admin.example.foodie.models.RestaurantCreate.RestaurantUser;
import admin.example.foodie.models.RestaurantCreate.SuperAdminUser;
import admin.example.foodie.models.User;
import admin.example.foodie.org.example.foodie.apifetch.FoodieClient;
import admin.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static User user = new User("", "");
    public Button CreateAccountButton;
    private ProgressBar progressBar;
    public EditText InputName, InputPhoneNumber, InputPassword, InputAddress, InputEmail,RestaurantIdInput;
    final static String username="admin";
    final static  String password="password";
    SuperAdminUser superAdminUser;
    RestaurantUser restaurantUser;
    RestaurantCreate restaurantCreate;
    List<String> contactNos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        initWidgets();
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                     CreateRestaturantUser();
            }
        });
        if (user.getToken() != null) {
            Log.i("ok", user.getToken());
            WelcomeActvity.getInstance().finish();
        }


    }
    public void initWidgets() {

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputAddress = (EditText) findViewById(R.id.register_address_input);
        RestaurantIdInput=(EditText)findViewById(R.id.register_restaurantId_input);
    }
    public void CreateUser(String name, String email, String password, String address, String phone) {
        user = new User(name, email, password, address, phone);
    }

   public  void CreateRestaturantUser()
   {

       FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
       superAdminUser=new SuperAdminUser(username,password);
       restaurantUser=new RestaurantUser(InputName.getText().toString(),
               RestaurantIdInput.getText().toString(),
               InputAddress.getText().toString(),
               InputPassword.getText().toString(),
               contactNos);
       RestaurantCreate restaurantCreate=new RestaurantCreate(superAdminUser,restaurantUser);
       Call<ResponseUser> call2=foodieClient.createRestaurant(restaurantCreate);//just post::Response class for this should be made;
       progressBar.setVisibility(View.VISIBLE);
       call2.enqueue(new Callback<ResponseUser>() {
           @Override
           public void onResponse(Call<ResponseUser> call , Response<ResponseUser> response) {
               if (response.code() == 201) {
                   Toast.makeText(getApplicationContext() , "Success!" , Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                   intent.putExtra("token", response.body().getToken());//for further functionality need this token id
                   intent.putExtra("name",InputName.getText().toString());
                   intent.putExtra("restId",RestaurantIdInput.getText().toString());
                   intent.putExtra("address",InputAddress.getText().toString());
                   startActivity(intent);
                   WelcomeActvity.token=response.body().getToken();
                   SharedPreferences sharedPreferences = getSharedPreferences("admin.example.foodie", Context.MODE_PRIVATE);

                   SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token",WelcomeActvity.token);
//                   token = sharedPreferences.getString("token", null);
                   progressBar.setVisibility(View.GONE);
                   WelcomeActvity.getInstance().finish();
                   finish();
               } else {
                   Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseUser> call , Throwable t) {
               Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });

   }
}
