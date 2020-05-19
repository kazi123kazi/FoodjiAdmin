package admin.example.foodie.FragmentClass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import org.example.foodie.R;

import admin.example.foodie.AdapterClass.FoodAdapter;
import admin.example.foodie.MainActivity;
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

public class AllFoodsFragment extends Fragment {
    private View rootView;
    private RestaurantsViewModel restaurantsViewModel;
    private static FoodAdapter adapter;
    private static RecyclerView recyclerView;
    private FoodsViewModel mViewModel;
    Toolbar toolbar;
    public static ProgressBar loadFood;

    public AllFoodsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_all_foods, container, false);

        recyclerView = rootView.findViewById(R.id.food_recycler_view);
        loadFood=rootView.findViewById(R.id.loadFood);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        loadFood.setVisibility(View.VISIBLE);
        // This will display an Up icon (<-), we will replace it with hamburger later
        showInfo();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        setupRecyclerView();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FOODJI ADMIN");
    }




    public void showInfo() {

        // recyclerView = findViewById(R.id.food_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(FoodsViewModel.class);
        //  foodsViewModel=ViewModelProviders.of(this).get(FoodsViewModel.class);
        mViewModel.init();
        mViewModel.getFoodRepository().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null) {
                    //List<Food> list=new ArrayList<>();
                    //Collections.copy(foods,restaurant.getFoods());
                    adapter = new FoodAdapter(getActivity());
                    adapter.setFood(foods);
                    loadFood.setVisibility(View.GONE);
                      recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            recyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    public AllFoodsFragment getInstace(){return AllFoodsFragment.this;}

//public void refreshActivity(){
//    finish();
//    startActivity(getIntent());
//}


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//
//    }


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