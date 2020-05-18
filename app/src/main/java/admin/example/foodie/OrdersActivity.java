package admin.example.foodie;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.foodie.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import admin.example.foodie.models.Food;
import admin.example.foodie.models.Order;

public class OrdersActivity extends AppCompatActivity {


    Toolbar toolbar;
    OrdersAdapter adapter;
    RecyclerView orderRecView;
    List<Order> viewList= new ArrayList<>();
    public static Gson gson = new Gson();
    SharedPreferences preferences;
    public  String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_view);
         preferences=OrdersActivity.this.getSharedPreferences("org.example.foodie",MODE_PRIVATE);
            orderRecView=findViewById(R.id.allOrders);


/*
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Orders");
*/

        getPrefernce(preferences);

        if(viewList!=null) {

            orderRecView = findViewById(R.id.allOrders);

            adapter=new OrdersAdapter(OrdersActivity.this);
      //      orderRecView.setLayoutManager(new GridLayoutManager(
        //            OrdersActivity.this, 1));
            //SETTING up recyclerview
            Collections.reverse(viewList);
adapter.setOrders(viewList);
            orderRecView.setLayoutManager(new GridLayoutManager(OrdersActivity.this, 1));

            orderRecView.setAdapter(adapter);
            //    setupRecyclerView();

        }

    }



    public  void getPrefernce(SharedPreferences sharedPreferences) {


        if (viewList.isEmpty()) {

            json = sharedPreferences.getString("orderItems", null);
            String id = sharedPreferences.getString("rest_id", null);
            if (json != null) {
                Type type = new TypeToken<List<Order>>() {
                }.getType();
                viewList = gson.fromJson(json, type);
            }


        }


    }




        public  void saveData(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(viewList);
        editor.putString("orderItems", json);
        //editor.putString("rest_id", .rest_id);
        editor.commit();
    }


    public void setupRecyclerView() {

        if (adapter == null) {


        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
