package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.EBook_Adapter;
import com.example.bookreader.Adapter.Genre_Adapter;
import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.SliderAdapter;
import com.example.bookreader.Adapter.TopE_Book_Adapter;
import com.example.bookreader.Adapter.Top_Hard_Adapter;
import com.example.bookreader.Model.EBook_Model;
import com.example.bookreader.Model.Genre_Model;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.SliderItem;
import com.example.bookreader.Model.TopE_Book_Model;
import com.example.bookreader.Model.Top_Hard_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_Ebooks_Activity extends AppCompatActivity {

    MyInterface myInterface;
    RecyclerView rv_ebooks;
    List<TopE_Book_Model> eBook_models,book_models;;
    EBook_Adapter eBook_adapter;
    String type;
    public ImageView cart_img,notification;
    public TextView cart_count,noti_count;
    User user;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__ebooks_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("heading"));
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();


        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        book_models = new ArrayList<>();
//        eBook_models = new ArrayList<>();
//        sliderItems = new ArrayList<>();
//        newRelease_models = new ArrayList<>();

        type=getIntent().getExtras().getString("type");
        if(type.equals("best_seller")){
            fetch_bestSeller();
        }else if(type.equals("publishers")){
            fetch_publisherChoice();
        }else if(type.equals("most_liked")){
            fetch_mostLiked();
        }else if(type.equals("top_hard")){
            fetch_topHard();
        }else if(type.equals("top_audio")){
            fetch_topAudio();
        }else if(type.equals("top_digital")){
            fetch_topDigital();
        }else if(type.equals("genre")){
            String genre_id = getIntent().getExtras().getString("id");
            fetch_genre_books(genre_id);
        }else {
            fetch_new_release();
        }


        rv_ebooks = findViewById(R.id.rv_ebooks);
        rv_ebooks.setHasFixedSize(true);
        rv_ebooks.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    private void fetch_topHard() {
        Call<String> call = myInterface.fetch_top_hard();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();
//                            SliderAdapter sliderAdapter = new SliderAdapter(All_Ebooks_Activity.this,sliderItems);
//                            rv_ebooks.setAdapter(sliderAdapter);
//                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_topAudio(){
        Call<String> call = myInterface.fetch_top_audio();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_topDigital() {
        Call<String> call = myInterface.fetch_top_digital();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }

                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_mostLiked() {
        Call<String> call = myInterface.fetch_most_liked();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();
//                            SliderAdapter sliderAdapter = new SliderAdapter(All_Ebooks_Activity.this,sliderItems);
//                            rv_ebooks.setAdapter(sliderAdapter);
//                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_genre_books(String genre_id) {
        Call<String> call = myInterface.fetch_genre_byGenreIds(genre_id);
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this, "loading", "Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_bestSeller() {
        Call<String> call = myInterface.fetch_best_seller();
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_publisherChoice() {
        Call<String> call = myInterface.fetch_publisher_choice();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();
//                            SliderAdapter sliderAdapter = new SliderAdapter(All_Ebooks_Activity.this,sliderItems);
//                            rv_ebooks.setAdapter(sliderAdapter);
//                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_new_release() {
        Call<String> call = myInterface.fetch_top_rated();
        final ProgressDialog progressDialog = ProgressDialog.show(All_Ebooks_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            book_models.clear();
                            rv_ebooks.setVisibility(View.GONE);
                            Toast.makeText(All_Ebooks_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_ebooks.setVisibility(View.VISIBLE);
                            book_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(All_Ebooks_Activity.this,book_models);
                            rv_ebooks.setAdapter(adapter);
                            progressDialog.dismiss();
//                            NewRelease_Adapter newRelease_adapter = new NewRelease_Adapter(All_Ebooks_Activity.this,newRelease_models);
//                            rv_ebooks.setAdapter(newRelease_adapter);
//                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
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
                startActivity(new Intent(All_Ebooks_Activity.this,CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        cart_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(All_Ebooks_Activity.this,CartActivity.class).putExtras(bundle));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(All_Ebooks_Activity.this, Notification_Activity.class).putExtras(bundle));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        noti_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(All_Ebooks_Activity.this, Home_Activity.class).putExtras(bundle));
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
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(All_Ebooks_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(All_Ebooks_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(All_Ebooks_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
        else if (id == R.id.search) {
            Toast.makeText(this, "Search your neccessity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Search_Activity.class));
            return true;
        }
//        else if(id == R.id.notification) {
////            startActivity(new Intent(this,Cart_Activity.class));
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


}