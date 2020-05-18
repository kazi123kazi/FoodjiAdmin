package admin.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import org.example.foodie.R;

import admin.example.foodie.models.Food;
import admin.example.foodie.models.OrderFood;
import admin.example.foodie.org.example.foodie.apifetch.FoodieClient;
import admin.example.foodie.org.example.foodie.apifetch.ServiceGenerator;
import admin.example.foodie.viewmodels.FoodsViewModel;
import admin.example.foodie.viewmodels.RestaurantsViewModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFoods extends AppCompatActivity {

    private RestaurantsViewModel restaurantsViewModel;
    private static FoodAdapter adapter;
    private static RecyclerView recyclerView;
    FoodsViewModel mViewModel;
    Toolbar toolbar;
    public static ProgressBar loadFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_foods);
        recyclerView = findViewById(R.id.food_recycler_view);
        loadFood=findViewById(R.id.loadFood);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFood.setVisibility(View.VISIBLE);
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        showInfo();

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        setupRecyclerView();


    }





    public void showInfo() {

        // recyclerView = findViewById(R.id.food_recycler_view);
        mViewModel = ViewModelProviders.of(AllFoods.this).get(FoodsViewModel.class);
        //  foodsViewModel=ViewModelProviders.of(this).get(FoodsViewModel.class);

        mViewModel.init();

        mViewModel.getFoodRepository().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null) {
                    //List<Food> list=new ArrayList<>();

                    //Collections.copy(foods,restaurant.getFoods());
                    adapter = new FoodAdapter(AllFoods.this);
                    adapter.setFood(foods);
                    loadFood.setVisibility(View.GONE);

                  //  recyclerView.setLayoutManager(new GridLayoutManager(AllFoods.this, 1));
                    recyclerView.setAdapter(adapter);

                    Log.i("foods", String.valueOf("hi"));


                }
            }

        });
    }

    public void setupRecyclerView() {
        //      Log.i("foods", String.valueOf("hi"));

        if (adapter == null) {
            //   adapter.setFood(foods);
            recyclerView.setLayoutManager(new GridLayoutManager(AllFoods.this, 1));
            recyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    public AllFoods getInstace(){return AllFoods.this;}

public void refreshActivity(){
    finish();
    startActivity(getIntent());
}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


    public void deleteFood(String id){
        FoodieClient foodieClient= ServiceGenerator.createService(FoodieClient.class);
        //loadFood.setVisibility(View.VISIBLE);

        OrderFood food=new OrderFood(id);

        Call<ResponseBody> call= foodieClient.deleteFood(MainActivity.token,food);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

              /*  finish();
                startActivity(getIntent());
*/
              //  Toast.makeText(getApplicationContext(),"DELETED",Toast.LENGTH_SHORT).show();
          //      loadFood.setVisibility(View.GONE);
                Log.i("DELETED", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

/*
        if(item.getItemId()==R.id.home)onBackPressed();
*/

        return super.onOptionsItemSelected(item);
    }
}