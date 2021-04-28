package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.Review_Adapter;
import com.example.bookreader.Adapter.Similar_Adapter;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.Review_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

public class BookDetails_Activity extends AppCompatActivity {

    MyInterface myInterface;
    ImageView img_book;
    TextView price,book_name,rating,genre,text_details,text_reviews,description,author,already_added,add_to_cart;
    View view_details,view_reviews;
    LinearLayout linear_info,linear_reviews,lin_genre,linear_similar,lin_quantity;
    LikeButton fab_btn;
    RatingBar ratingBar;
    EditText edit_review;
    Button btn_submit;
    public ImageView cart_img,notification;
    public TextView cart_count,noti_count;
    ElegantNumberButton elegantnumberbutton;
    RecyclerView rv_similar,rv_reviews;
    List<NewRelease_Model> model;
    List<Review_Model> review_models;
    CardView card_preview;
    User user;
    String user_id,genre_id,pro_id,book_type;
    String preview_url = "";
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("book_name"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user=new User(this);
        user_id=user.getUser_id();

        pro_id=getIntent().getExtras().getString("id");
        genre_id=getIntent().getExtras().getString("genre_id");
        book_type=getIntent().getExtras().getString("book_type");

        img_book = findViewById(R.id.img_book);
        book_name = findViewById(R.id.book_name);
        rating = findViewById(R.id.rating);
        genre = findViewById(R.id.genre);
        lin_genre = findViewById(R.id.lin_genre);
        fab_btn = findViewById(R.id.fab_btn);
        text_details = findViewById(R.id.text_details);
        view_details = findViewById(R.id.view_details);
        text_reviews = findViewById(R.id.text_reviews);
        view_reviews = findViewById(R.id.view_reviews);
        card_preview = findViewById(R.id.card_preview);
        description = findViewById(R.id.description);
        edit_review = findViewById(R.id.edit_review);
        ratingBar = findViewById(R.id.ratingBar);
        btn_submit = findViewById(R.id.btn_submit);
        elegantnumberbutton = findViewById(R.id.elegantnumberbutton);
        linear_similar = findViewById(R.id.linear_similar);
        author = findViewById(R.id.author);
        linear_info = findViewById(R.id.linear_info);
        linear_reviews = findViewById(R.id.linear_reviews);
        add_to_cart = findViewById(R.id.add_to_cart);
        already_added = findViewById(R.id.already_added);
        price = findViewById(R.id.price);
        lin_quantity = findViewById(R.id.lin_quantity);

        model = new ArrayList<>();
        review_models = new ArrayList<>();

        text_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_info.setVisibility(View.VISIBLE);
                linear_reviews.setVisibility(View.GONE);
                view_details.setVisibility(View.VISIBLE);
                view_reviews.setVisibility(View.GONE);
            }
        });
        text_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_info.setVisibility(View.GONE);
                linear_reviews.setVisibility(View.VISIBLE);
                view_details.setVisibility(View.GONE);
                view_reviews.setVisibility(View.VISIBLE);
            }
        });
        card_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", pro_id);
                bundle.putString("type", "preview_pdf");
                bundle.putString("book_name", book_name.getText().toString());
                bundle.putString("preview_url", preview_url);
                startActivity(new Intent(BookDetails_Activity.this,PDF_Reader_Activity.class).putExtras(bundle));
            }
        });

        rv_similar = findViewById(R.id.rv_similar);
        rv_similar.setHasFixedSize(true);
        rv_similar.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        rv_reviews = findViewById(R.id.rv_reviews);
        rv_reviews.setHasFixedSize(true);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

         add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cart_add();
            }
        });

        elegantnumberbutton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
              if(oldValue<newValue){
                  cart_add();
                  Toast.makeText(BookDetails_Activity.this, "added"+newValue, Toast.LENGTH_SHORT).show();
              }else if(oldValue>newValue) {
                  remove_from_cart();
                  Toast.makeText(BookDetails_Activity.this, "removed"+newValue, Toast.LENGTH_SHORT).show();
              }

            }
        });


        already_added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDetails_Activity.this,CartActivity.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_review.getText().toString().equals("")){
                    edit_review.setError("Write Your Review");
                }else if(ratingBar.getRating()==0.0f){
                    Toast.makeText(BookDetails_Activity.this, "plz rate the book", Toast.LENGTH_SHORT).show();
                }else {
                    review_add();
                }
            }
        });

        fab_btn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (user_id.equals("")) {
//                    startActivity(new Intent(SingleProduct.this,LoginActivity.class));
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    send_favourite("fav");
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                send_favourite("unfav");
            }
        });

        fetch_details();
        check_favourite();
        fetch_reviews();
        fetch_similar();

    }


    private void cart_add() {
        String selling_price = price.getText().toString();
        Call<String> call=myInterface.add_cart(user_id,pro_id,selling_price);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(BookDetails_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(BookDetails_Activity.this, "Added to Cart Succesfully", Toast.LENGTH_SHORT).show();
                            fetch_details();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(BookDetails_Activity.this, "Not added to cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BookDetails_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetch_similar() {
        Call<String> call = myInterface.fetch_similar(genre_id,pro_id,book_type);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
//        Toast.makeText(EBook_Details_Activity.this, ""+genre_id+" "+pro_id, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            model.clear();
                            linear_similar.setVisibility(View.GONE);
//                            Toast.makeText(BookDetails_Activity.this, "No Similar Books found", Toast.LENGTH_SHORT).show();
                        } else {
                            linear_similar.setVisibility(View.VISIBLE);
//                            rv_similar.setVisibility(View.VISIBLE);
                            model.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                model.add(new NewRelease_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("book_type_category"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate")));
                            }
                            NewRelease_Adapter adapter = new NewRelease_Adapter(BookDetails_Activity.this,model);
                            rv_similar.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(BookDetails_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_details() {
        Call<String> call = myInterface.fetch_single_page(pro_id,user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(BookDetails_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
//

                if (res!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if(jsonObject.getString("rec").equals("1")){

                            book_name.setText(jsonObject.getString("product_name"));
                        if(jsonObject.getString("selling_price").equals("")){
                            price.setText("0");
                        }else{
                            price.setText(jsonObject.getString("selling_price"));
                        }
                            rating.setText(jsonObject.getString("rate"));
                            if(!jsonObject.getString("genre").toString().equals("")){
                                genre.setText(jsonObject.getString("genre"));
                            }else {
                                lin_genre.setVisibility(View.GONE);
                            }

                            preview_url = jsonObject.getString("preview_pdf");

                        if(jsonObject.getString("cart").toString().equals("")){

                            add_to_cart.setVisibility(View.VISIBLE);
                            already_added.setVisibility(View.GONE);

                        }else if(jsonObject.getString("cart").toString().equals("Exist in Cart")){
                            add_to_cart.setVisibility(View.GONE);
                            already_added.setVisibility(View.VISIBLE);
                        }
                            genre_id=jsonObject.getString("genre_id");
                            description.setText(jsonObject.getString("description"));
                            if(!jsonObject.getString("author_name").toString().equals("")){
                                author.setText(jsonObject.getString("author_name"));
                            }else {
                                author.setText("Unknown");
                            }

                            if(jsonObject.getString("quantity").equals("0")){
                                elegantnumberbutton.setNumber("0");
                            }else {
                                elegantnumberbutton.setNumber(jsonObject.getString("quantity"));
                            }


                            image = String.valueOf(jsonObject.getString("p_image"));
                            Glide.with(BookDetails_Activity.this)
                                    .load(image)
                                    .into(img_book);

                            checkCart();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(BookDetails_Activity.this, "Response null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Slow network find", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void review_add() {
        String rating_bar = String.valueOf(ratingBar.getRating());
        String review = edit_review.getText().toString();

        Call<String> call=myInterface.review_add(user_id,pro_id,review,rating_bar);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(BookDetails_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(BookDetails_Activity.this, "Review Submitted Successfully", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", pro_id);
                            startActivity(new Intent(BookDetails_Activity.this,BookDetails_Activity.class).putExtras(bundle));
                            finish();
                            progressDialog.dismiss();
                        }else if(jsonObject.getString("rec").equals("2")){
                            Toast.makeText(BookDetails_Activity.this, "Review Updated", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", pro_id);
                            startActivity(new Intent(BookDetails_Activity.this,BookDetails_Activity.class).putExtras(bundle));
                            finish();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(BookDetails_Activity.this, "Review not Submitted", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BookDetails_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetch_reviews() {
        Call<String> call = myInterface.fetch_review(pro_id);
        final ProgressDialog progressDialog = ProgressDialog.show(BookDetails_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            review_models.clear();
                            rv_reviews.setVisibility(View.GONE);
//                            Toast.makeText(EBook_Details_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_reviews.setVisibility(View.VISIBLE);
                            review_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                review_models.add(new Review_Model(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("comment"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("date")));
                            }
                            Review_Adapter adapter = new Review_Adapter(BookDetails_Activity.this,review_models);
                            rv_reviews.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(BookDetails_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send_favourite(String status){
        Call<String> call = myInterface.add_favourite(pro_id,user_id,status);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    if (res==null){
//                        Toast.makeText(EBook_Details_Activity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            JSONObject jsonObject=new JSONObject(res);
                            if (jsonObject.getString("rec").equals("1")){
                                check_favourite();
                            }else {

                            }


                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetails_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void check_favourite() {
        Call<String> call = myInterface.check_favourite(pro_id,user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    if (res==null){
//                        Toast.makeText(EBook_Details_Activity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        try {
                            JSONObject jsonObject=new JSONObject(res);
                            if (jsonObject.getString("rec").equals("1")){
                                fab_btn.setLiked(true);
                                progressDialog.dismiss();
                            }else {
                                fab_btn.setLiked(false);
                                progressDialog.dismiss();
                            }


                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetails_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void remove_from_cart() {
        String selling_price = price.getText().toString();
        Call<String> call=myInterface.remove_cart(user_id,pro_id,selling_price);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(BookDetails_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(BookDetails_Activity.this, "Removed from Cart", Toast.LENGTH_SHORT).show();
                            fetch_details();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(BookDetails_Activity.this, "not Removed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BookDetails_Activity.this, "Something Went Wrong"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookDetails_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
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
                startActivity(new Intent(BookDetails_Activity.this,CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        cart_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(BookDetails_Activity.this,CartActivity.class).putExtras(bundle));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(BookDetails_Activity.this, Notification_Activity.class).putExtras(bundle));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        noti_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("page_name","MainActivity");
                startActivity(new Intent(BookDetails_Activity.this, Home_Activity.class).putExtras(bundle));
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
                        Toast.makeText(BookDetails_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BookDetails_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookDetails_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookDetails_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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