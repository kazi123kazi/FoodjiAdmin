package admin.example.foodie.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.example.foodie.R;

import java.util.List;

import admin.example.foodie.models.Order;
import admin.example.foodie.models.OrderFood;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.CustomViewHolder> {
    private Context context;

    FoodListAdapter adapter;
    private List<Order> items;
    //  private ArrayList<NEWS> subjects;

    public OrdersAdapter(Context context) {
        this.context = context;
        // this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.orderview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.total.setText(String.valueOf(getTotal(items.get(position).getFoodList())));

        holder.restaurantName.setText(items.get(position).getRestaurant().getName());


        adapter= new FoodListAdapter(context);
        adapter.setFood(items.get(position).getFoodList());

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        holder.recyclerView.setAdapter(adapter);



        //set Elements here

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOrders(List<Order> orders) {
        this.items = orders;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

            private TextView restaurantName;
            private TextView total;
            private RecyclerView recyclerView;


        public CustomViewHolder(View view) {
            super(view);

            restaurantName=view.findViewById(R.id.restaurantName);
            total=view.findViewById(R.id.total);
            recyclerView=view.findViewById(R.id.foodList);



        }
    }




    public int getTotal(List<OrderFood> foods){
        int sum=0;
        for(int i=0;i<foods.size();i++){
            sum+=foods.get(i).getCount()*foods.get(i).getPrice();
        }
        return sum;
    }



}