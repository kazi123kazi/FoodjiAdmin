package admin.example.foodie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.example.foodie.R;

public class WelcomeActvity extends AppCompatActivity {

    public static String token;


    private Button loginButton, joinNowButton;

    static WelcomeActvity welcomeActvity;

    public static WelcomeActvity getInstance() {
        return welcomeActvity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_actvity);
        welcomeActvity = this;


        if (token == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("admin.example.foodie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            token = sharedPreferences.getString("token", null);
        }

        //If a token is set we will start main activity on startin our app
        if (token != null) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("token", token);
            // welcomeActvity.finish();
            Log.i("token:", String.valueOf(token));


            startActivity(i);
        }
        joinNowButton=(Button)findViewById(R.id.main_join_now_btn);
        loginButton=(Button)findViewById(R.id.main_login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(WelcomeActvity.this,LoginActivity.class);
                startActivity(u);
            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(WelcomeActvity.this,RegisterActivity.class);
                startActivity(u);
            }
        });
    }
}
