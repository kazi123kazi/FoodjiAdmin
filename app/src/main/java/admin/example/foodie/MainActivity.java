package admin.example.foodie;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.example.foodie.R;

import admin.example.foodie.FragmentClass.AddFoodFragment;
import admin.example.foodie.FragmentClass.AllFoodsFragment;
import admin.example.foodie.FragmentClass.ContactUsFragments;
import admin.example.foodie.FragmentClass.OrdersFragment;
import admin.example.foodie.FragmentClass.UpdateFragments;
import admin.example.foodie.ServiceClass.BackgroundService;
import admin.example.foodie.org.example.foodie.apifetch.FoodieClient;
import admin.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ProgressBar progressBar;
    //DrawerLayout drawer;
    public static String token;
    //NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    //Toolbar toolbar;
    public static SearchView searchView;
    public static String user;
    static MainActivity mainActiivity;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedId;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//              progressBar = findViewById(R.id.progressBar2);

//              progressBar.setVisibility(View.GONE);
//
        Intent i = getIntent();
        token = i.getStringExtra("token");
        user = i.getStringExtra("name");

        if (token != null) {
            Log.i("TOKEN", token);
            if (WelcomeActvity.getInstance() != null)
                WelcomeActvity.getInstance().finish();
        }



//        if (user == null) {
//            SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            user = sharedPreferences.getString("name", null);
//        }
//
//
        if (token != null) {
            Log.d("TOKEN", token);
            if (WelcomeActvity.getInstance() != null)
                WelcomeActvity.getInstance().finish();
        }
       Log.d("Token",token);
        //BackgroundService service= new BackgroundService(getApplication());


        startService(new Intent(this, BackgroundService.class));

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);


        View headerView = nvDrawer.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.userName);
        userName.setText("USER: " + String.valueOf(user));


        // Setup drawer view
        setupDrawerContent(nvDrawer);
        //FragmentManager fragmentManager=new F;

        frameLayout = (FrameLayout) findViewById(R.id.flContent);
        //View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

       fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.flContent, AllFoodsFragment.class.newInstance(), "Home");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //set default fragment
        loadFragment(new AllFoodsFragment());


        // We can now look up items within the header if needed

        // ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.


        switch (item.getItemId()) {
            case android.R.id.home :
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    public MainActivity getInstance(){
        return MainActivity.this;
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        boolean id = false;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {

            /*case R.id.childDetails:
                fragmentClass = ChildDetails.class;
                break;*/

            case R.id.home:    //default will show  all foods given id as home
                id = true;
                fragmentClass = AllFoodsFragment.class;
                loadFragment(new AllFoodsFragment());
                break;
            case R.id.update:
                id = true;
                fragmentClass = UpdateFragments.class;
                loadFragment(new UpdateFragments()t());
                break;
            case R.id.view_orders:
                id = true;
                fragmentClass = OrdersFragment.class;
                loadFragment(new OrdersFragment());
                break;
           case R.id.addFood:
                id = true;
                fragmentClass = AddFoodFragment.class;
                loadFragment(new AddFoodFragment());
                break;
            case R.id.contactus:
                id = true;
                fragmentClass = ContactUsFragments.class;
                loadFragment(new ContactUsFragments());
                break;
            case R.id.logout:
                LogoutUser();
                return;



        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(false);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_activity_bar, menu);


//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setSubmitButtonEnabled(false);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                Home.adapter.getFilter().filter(query);
//                return false;
//            }
//        });
        return true;


    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }
//    @Override
    public void onBackPressed() {

//
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//            return;
//        }
        super.onBackPressed();
    }

    public void LogoutUser() {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        Call<Void> call = foodieClient.Logout(token);
        //   progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, WelcomeActvity.class);
                    startActivity(intent);


                    deleteToken();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //  progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //delete Token on logging out
    public void deleteToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("admin.example.foodie", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.clear();
        editor.apply();

        WelcomeActvity.token=null;
        editor.commit();
    }

}
