package admin.example.foodie.ServiceClass;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.foodie.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import admin.example.foodie.FragmentClass.AddFoodFragment;
import admin.example.foodie.FragmentClass.OrdersFragment;
import admin.example.foodie.MainActivity;
import admin.example.foodie.models.Order;
import admin.example.foodie.models.OrderFood;
import admin.example.foodie.org.example.foodie.apifetch.FoodieClient;
import admin.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BackgroundService extends Service {
    private static final String CHANNEL_ID = "1";
    Retrofit retrofit;
    public Order order;
    private Handler mHandler;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 5*1000;//30 sec

    String json;
    MainActivity activity=new MainActivity();
    Gson gson = new Gson();

    Context context;


    public List<Order> viewList = new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            //order=new Order(new User("8840102246",""),)
            syncData(AddFoodFragment.token);
            createNotificationChannel();

            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    private synchronized void syncData(String token) {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("org.example.foodie", MODE_PRIVATE);

        Call<List<Order>> call = foodieClient.getNotified(token);
        getPrefernce(sharedPreferences);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                if (response.body()!=null)
                if (response.body().size() != 0) {
                    Log.i("ResponseUser", response.body().get(0).getUser().getName());
                    viewList.add(response.body().get(0));
                    saveData(sharedPreferences);
                    sendNotification(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.i("ResponseMessage", t.getMessage());
            }
        });


     //   Toast.makeText(this, "I am running in background", Toast.LENGTH_SHORT).show();
        Log.d("service: ", "running");
        // call your rest service here
    }

    public void sendNotification(Order order) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, OrdersFragment.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder;
        if (order != null) {
            builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("New Order")
                    .setContentText(order.getUser().getName() + "\n" + "Total:" + getTotal(order.getFoodList()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("New Order")
                    .setContentText("Fuck get the damn order")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Name";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
    public void saveData (SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(viewList);
        editor.putString("orderItems", json);
        //editor.putString("rest_id", .rest_id);
        editor.commit();
    }


    public int getTotal(List<OrderFood> foods){
        int sum=0;
        for(int i=0;i<foods.size();i++){
            sum+=foods.get(i).getCount()*foods.get(i).getPrice();
        }
        return sum;
    }


}











