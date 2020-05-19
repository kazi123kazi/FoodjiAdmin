package admin.example.foodie.AdapterClass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.R;

import admin.example.foodie.FragmentClass.AllFoodsFragment;
import admin.example.foodie.models.Food;
import admin.example.foodie.models.Foodid;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CustomViewHolder> {

    private static int pos;

    List<Food> items;
    private OnItemCLickListener mOnItemCLickListener;
    private FragmentManager f_manager;
    private Context context;
    public SharedPreferences sharedPreferences;
    AllFoodsFragment instance;


    public AlertDialog.Builder builder;


    public FoodAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.foods_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Log.i("afnan", String.valueOf(items.get(position).getFoodid()));
        Foodid foodid = items.get(position).getFoodid();
        holder.foodName.setText(foodid.getName());

        String encodedImage = items.get(position).getFoodid().getImage();

        if(encodedImage!=null) {
            byte[] image = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap i = BitmapFactory.decodeByteArray(image, 0, image.length);
           holder.imageView.setImageBitmap(i);
        }



        holder.price.setText("₹ "+items.get(position).getPrice());
        holder.deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder=new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));

            builder.setTitle("Are You sure You Want To Delete This Item?");

            builder.setMessage("Press Yes To Continue").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {



                    instance=new AllFoodsFragment();
                    instance.deleteFood(foodid.get_id());
                    items.remove(items.get(position));
                    notifyDataSetChanged();


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alert=builder.create();
            alert.show();


            }});

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFood(List<Food> foods) {
        this.items = foods;
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }

    public Boolean isPresent(List<Food> foods, String name) {

        for (int i = 0; i < foods.size(); i++) {
            if (name.equals(foods.get(i).getFoodid().getName())) {
                pos = i;
                return true;
            }
        }
        return false;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView foodName;
        private TextView price;
      private  ImageView imageView;
        private ImageView deleteFood;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.food_image);
            deleteFood=itemView.findViewById(R.id.deleteFood);
            price=itemView.findViewById(R.id.priceOfFood);
            foodName = itemView.findViewById(R.id.food_name);

        }
    }



}
