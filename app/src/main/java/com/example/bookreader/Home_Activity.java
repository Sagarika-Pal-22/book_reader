package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.Notification_Adapter;
import com.example.bookreader.Fragment.Genre_Fragment;
import com.example.bookreader.Fragment.Home_Fragment;
import com.example.bookreader.Fragment.LibraryFragment;
import com.example.bookreader.Fragment.MyCart_Fragment;
import com.example.bookreader.Fragment.MyWishlist_Fragment;
import com.example.bookreader.Fragment.Order_Fragment;
import com.example.bookreader.Fragment.Profile_Fragment;
import com.example.bookreader.Model.Notification_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity {

    MyInterface myInterface;
    NavigationView navview;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomnav;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public ImageView cart_img,notification;
    public TextView cart_count,noti_count;
    User user;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book Reader");

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();

        drawer = findViewById(R.id.drawer);
        navview = findViewById(R.id.navview);
        bottomnav = findViewById(R.id.bottomnav);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        }

        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.drawer_home:
                        Home_Fragment home_fragment = new Home_Fragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, home_fragment);
                        fragmentTransaction.commit();
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case R.id.drawer_category:
                        Genre_Fragment genre_fragment = new Genre_Fragment();
                        FragmentTransaction fragmentTransaction_g = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction_g.replace(R.id.framelayout, genre_fragment);
                        fragmentTransaction_g.commit();
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case R.id.drawer_order:
                        startActivity(new Intent(Home_Activity.this, Order_Activity.class));
//                        Order_Fragment order_fragment = new Order_Fragment();
//                        FragmentTransaction fragmentTransaction_o = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction_o.replace(R.id.framelayout, order_fragment);
//                        fragmentTransaction_o.commit();
//                        if (drawer.isDrawerOpen(GravityCompat.START)) {
//                            drawer.closeDrawer(GravityCompat.START);
//                        }
                        break;
                    case R.id.drawer_wishlist:
                        startActivity(new Intent(Home_Activity.this, WishList_Activity.class));
//                        MyWishlist_Fragment wishlist_fragment = new MyWishlist_Fragment();
//                        FragmentTransaction fragmentTransaction_w = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction_w.replace(R.id.framelayout, wishlist_fragment);
//                        fragmentTransaction_w.commit();
//                        if (drawer.isDrawerOpen(GravityCompat.START)) {
//                            drawer.closeDrawer(GravityCompat.START);
//                        }
                        break;
                    case R.id.drawer_cart:
                        startActivity(new Intent(Home_Activity.this, CartActivity.class));
//                        MyCart_Fragment cart_fragment = new MyCart_Fragment();
//                        FragmentTransaction fragmentTransaction_c = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction_c.replace(R.id.framelayout, cart_fragment);
//                        fragmentTransaction_c.commit();
//                        if (drawer.isDrawerOpen(GravityCompat.START)) {
//                            drawer.closeDrawer(GravityCompat.START);
//                        }
                        break;
                    case R.id.drawer_logout:
//                        startActivity(new Intent(MainActivity.this, Feedback_Activity.class));
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

        bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    Home_Fragment home_fragment = new Home_Fragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, home_fragment);
                    fragmentTransaction.commit();
                }else if (id == R.id.menu_category) {
                    Genre_Fragment genre_fragment = new Genre_Fragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, genre_fragment);
                    fragmentTransaction.commit();
                }else if (id == R.id.menu_library) {
                    LibraryFragment libraryFragment = new LibraryFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, libraryFragment);
                    fragmentTransaction.commit();
                }else if (id == R.id.menu_profile) {
                    Profile_Fragment profileFragment = new Profile_Fragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, profileFragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        bottomnav.setSelectedItemId(R.id.menu_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem action_cart = menu.findItem(R.id.action_cart);
        MenuItem action_noti = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(action_cart,R.layout.cart_item);
        MenuItemCompat.setActionView(action_noti,R.layout.noti_item);
        RelativeLayout cart=(RelativeLayout) MenuItemCompat.getActionView(action_cart);
        RelativeLayout noti=(RelativeLayout) MenuItemCompat.getActionView(action_noti);
        cart_img = (ImageView) cart.findViewById(R.id.cart);
        cart_count = (TextView) cart.findViewById(R.id.cart_count);
        notification = (ImageView) noti.findViewById(R.id.notification);
        noti_count = (TextView) noti.findViewById(R.id.noti_count);
        checkCart();

        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle=new Bundle();
//                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(Home_Activity.this,CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        cart_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(Home_Activity.this,CartActivity.class).putExtras(bundle));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_Activity.this, Notification_Activity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        noti_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_Activity.this, Home_Activity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        fetch_notification();
        return true;
    }

    private void fetch_notification() {
        Call<String> call = myInterface.fetch_notification(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONObject jsonObject_1 = new JSONObject(res);
                        if(!jsonObject_1.getString("count").equals("0")){
                            noti_count.setVisibility(View.VISIBLE);
                            noti_count.setText(jsonObject_1.getString("count"));
                            progressDialog.dismiss();
                        }else {
                            noti_count.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Home_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Home_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Home_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkCart() {
        Call<String> call = myInterface.fetch_cart(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONObject jsonObject_1 = new JSONObject(res);
                        if(!jsonObject_1.getString("count").equals("0")){
                            cart_count.setVisibility(View.VISIBLE);
                            cart_count.setText(jsonObject_1.getString("count"));
                            progressDialog.dismiss();
                        }else {
                            cart_count.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Home_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Home_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Home_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.search) {
            Toast.makeText(this, "Search your neccessity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Search_Activity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            this.finish();
        } else {
            Toast.makeText(getBaseContext(),"Press once again to exit!" ,
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}