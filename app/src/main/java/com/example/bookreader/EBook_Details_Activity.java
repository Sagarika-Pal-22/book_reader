package com.example.bookreader;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.bookreader.Adapter.Content_Adapter;
import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.Recommendation_Adapter;
import com.example.bookreader.Adapter.Review_Adapter;
import com.example.bookreader.Adapter.Similar_Adapter;
import com.example.bookreader.Fragment.LibraryFragment;
import com.example.bookreader.Fragment.MyCart_Fragment;
import com.example.bookreader.Model.CategoryHome_Model;
import com.example.bookreader.Model.Content_Model;
import com.example.bookreader.Model.Genre_Model;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.Review_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
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

public class EBook_Details_Activity extends AppCompatActivity {

    Toolbar toolbar;
    MyInterface myInterface;
    RecyclerView rv_similar,rv_reviews;
    List<NewRelease_Model> model;
    List<Review_Model> review_models;
    ImageView img_book,img_subscribe,img_read;
    TextView see_more,text_details,text_reviews,book_name,price,rating,genre,
            description,author,text_subscribe,text_read;
    EditText edit_review;
    LinearLayout linear_info,linear_reviews,linear_add_cart,linear_read,linear_similar,lin_genre;
    View view_details,view_reviews;
    LikeButton fab_btn;
    CardView card_listen,card_preview,card_subscribe;
    RatingBar ratingBar;
    Button btn_submit;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    RadioButton radio_1,radio_2,radio_3;
    RadioGroup radioGroup;
    TextView text_1_price,text_2_price,text_3_price;
    User user;
    String image;
    String user_id,genre_id,pro_id,book_type;
    String pdf_url = "", audio_url = "", preview_url = "";
    String subscribe_value="", validity = "", subscription_status ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_book__details_);
        toolbar = findViewById(R.id.toolbar);
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
//        Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        see_more = findViewById(R.id.see_more);
        text_details = findViewById(R.id.text_details);
        text_reviews = findViewById(R.id.text_reviews);
        linear_read = findViewById(R.id.linear_read);
        linear_info = findViewById(R.id.linear_info);
        linear_reviews = findViewById(R.id.linear_reviews);
        linear_add_cart = findViewById(R.id.linear_add_cart);
        card_listen = findViewById(R.id.card_listen);
        card_preview = findViewById(R.id.card_preview);
        card_subscribe = findViewById(R.id.card_subscribe);
        img_book = findViewById(R.id.img_book);
        img_subscribe = findViewById(R.id.img_subscribe);
        img_read = findViewById(R.id.img_read);
        book_name = findViewById(R.id.book_name);
//        price = findViewById(R.id.price);
        rating = findViewById(R.id.rating);
        genre = findViewById(R.id.genre);
        description = findViewById(R.id.description);
        author = findViewById(R.id.author);
        linear_similar = findViewById(R.id.linear_similar);
        lin_genre = findViewById(R.id.lin_genre);
        view_details = findViewById(R.id.view_details);
        view_reviews = findViewById(R.id.view_reviews);
        edit_review = findViewById(R.id.edit_review);
        ratingBar = findViewById(R.id.ratingBar);
        btn_submit = findViewById(R.id.btn_submit);
        fab_btn = findViewById(R.id.fab_btn);
        text_subscribe = findViewById(R.id.text_subscribe);
        text_read = findViewById(R.id.text_read);

//        add_to_cart = findViewById(R.id.add_to_cart);
//        already_added = findViewById(R.id.already_added);



        model = new ArrayList<>();
        review_models = new ArrayList<>();

        rv_similar = findViewById(R.id.rv_similar);
        rv_similar.setHasFixedSize(true);
        rv_similar.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        rv_reviews = findViewById(R.id.rv_reviews);
        rv_reviews.setHasFixedSize(true);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


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

        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EBook_Details_Activity.this,All_Ebooks_Activity.class));
            }
        });
        linear_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subscription_status.equals("Y")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", pro_id);
                    bundle.putString("book_name", book_name.getText().toString());
                    bundle.putString("type", "book_pdf");
                    bundle.putString("pdf_url", pdf_url);
                    startActivity(new Intent(EBook_Details_Activity.this,PDF_Reader_Activity.class).putExtras(bundle));
                }
                else {
                    Toast.makeText(EBook_Details_Activity.this, "Do Subscribe to read this book", Toast.LENGTH_SHORT).show();
                }
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
                startActivity(new Intent(EBook_Details_Activity.this,PDF_Reader_Activity.class).putExtras(bundle));
            }
        });

//        add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cart_add();
//            }
//        });

//        already_added.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(EBook_Details_Activity.this,CartActivity.class));
//            }
//        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_review.getText().toString().equals("")){
                    edit_review.setError("Write Your Review");
                }else if(ratingBar.getRating()==0.0f){
                    Toast.makeText(EBook_Details_Activity.this, "plz rate the book", Toast.LENGTH_SHORT).show();
                }else {
                    review_add();
                }
            }
        });

        card_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("audio_url", audio_url);
                bundle.putString("img_book", image);
                bundle.putString("author", author.getText().toString());
                bundle.putString("genre", genre.getText().toString());
                bundle.putString("book_name", book_name.getText().toString());
                startActivity(new Intent(EBook_Details_Activity.this,Audio_Listen_Activity.class).putExtras(bundle));
            }
        });

        card_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(EBook_Details_Activity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(EBook_Details_Activity.this).inflate(R.layout.dialog_subscribe, viewGroup, false);
                radio_1 = dialogView.findViewById(R.id.radio_1);
                radio_2 = dialogView.findViewById(R.id.radio_2);
                radio_3 = dialogView.findViewById(R.id.radio_3);
                radioGroup = dialogView.findViewById(R.id.radioGroup);
                text_1_price = dialogView.findViewById(R.id.text_1_price);
                text_2_price = dialogView.findViewById(R.id.text_2_price);
                text_3_price = dialogView.findViewById(R.id.text_3_price);

                radio_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            subscribe_value = radio_1.getText().toString();
                            validity = "1 Month";
                            radio_2.setChecked(false);
                            radio_3.setChecked(false);
                        }
                    }
                });

                radio_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            validity = "3 Months";
                            subscribe_value = radio_2.getText().toString();
                            radio_1.setChecked(false);
                            radio_3.setChecked(false);
                        }
                    }
                });

                radio_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){

                            validity = "6 Months";
                            subscribe_value = radio_3.getText().toString();
                            radio_1.setChecked(false);
                            radio_2.setChecked(false);
                        }
                    }
                });

                CardView card_apply = dialogView.findViewById(R.id.card_apply);
                card_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(subscribe_value.equals("") || validity.equals("")){
                           Toast.makeText(EBook_Details_Activity.this, "Please choose any plan", Toast.LENGTH_SHORT).show();
                       }else {
                           apply_plan();
                       }

                    }
                });

                builder.setView(dialogView);
                Call<String> call = myInterface.fetch_subscription_plan(pro_id);
//                final ProgressDialog progressDialog = ProgressDialog.show(EBook_Details_Activity.this,"loading","Please Wait");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res=response.body();
                        if (res!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if(jsonObject.getString("rec").equals("1")){

                                    radio_1.setText("Rs,"+jsonObject.getString("first_month")+"/-");
                                    radio_2.setText("Rs,"+jsonObject.getString("third_month")+"/-");
                                    radio_3.setText("Rs,"+jsonObject.getString("six_month")+"/-");
                                    alertDialog = builder.create();
                                    alertDialog.show();
//                                    progressDialog.dismiss();
                                }
                                else{
                                    Toast.makeText(EBook_Details_Activity.this, "No Plans Available", Toast.LENGTH_LONG).show();
//                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
//                                progressDialog.dismiss();
                            }
                        }else {
//                            progressDialog.dismiss();
                            Toast.makeText(EBook_Details_Activity.this, "Response null", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(EBook_Details_Activity.this, "Slow network find", Toast.LENGTH_LONG).show();
//                      /  progressDialog.dismiss();
                    }
                });
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

    private void apply_plan() {
        Call<String> call=myInterface.add_subscription(user_id,pro_id,validity);
        final ProgressDialog progressDialog = ProgressDialog.show(EBook_Details_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(EBook_Details_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(EBook_Details_Activity.this, "Succesfully Subscribed", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            fetch_details();

                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(EBook_Details_Activity.this, "Not Subscribed", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EBook_Details_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
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
                    Toast.makeText(EBook_Details_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(EBook_Details_Activity.this, "Added to Cart Succesfully", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", pro_id);
                            startActivity(new Intent(EBook_Details_Activity.this,EBook_Details_Activity.class).putExtras(bundle));
                            finish();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(EBook_Details_Activity.this, "Not added to cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EBook_Details_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(EBook_Details_Activity.this, "No Similar Books found", Toast.LENGTH_SHORT).show();
                        } else {
                            linear_similar.setVisibility(View.VISIBLE);
//                            rv_similar.setVisibility(View.VISIBLE);
                            model.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                model.add(new NewRelease_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate")));
                            }
                            NewRelease_Adapter adapter = new NewRelease_Adapter(EBook_Details_Activity.this,model);
                            rv_similar.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(EBook_Details_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EBook_Details_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EBook_Details_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_details() {
        Call<String> call = myInterface.fetch_single_page(pro_id,user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(EBook_Details_Activity.this,"loading","Please Wait");
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
//                        if(jsonObject.getString("selling_price").equals("")){
//                            price.setText("0");
//                        }else{
//                            price.setText(jsonObject.getString("selling_price"));
//                        }
                        rating.setText(jsonObject.getString("rate"));
                        if(!jsonObject.getString("genre").toString().equals("")){
                            genre.setText(jsonObject.getString("genre"));
                        }else {
                            lin_genre.setVisibility(View.GONE);
                        }

                        pdf_url = jsonObject.getString("pdf");
                        audio_url = jsonObject.getString("audio");
                        preview_url = jsonObject.getString("preview_pdf");

//                        if(jsonObject.getString("cart").toString().equals("")){
//
//                            add_to_cart.setVisibility(View.VISIBLE);
//                            already_added.setVisibility(View.GONE);
//
//                        }else if(jsonObject.getString("cart").toString().equals("Exist in Cart")){
//                            add_to_cart.setVisibility(View.GONE);
//                            already_added.setVisibility(View.VISIBLE);
//                        }
                            subscription_status=jsonObject.getString("subscription");
                        if(jsonObject.getString("subscription").equals("N")){
                            text_subscribe.setText("Subscribe");
                            img_subscribe.setVisibility(View.VISIBLE);
                            img_read.setColorFilter(ContextCompat.getColor(EBook_Details_Activity.this, R.color.grey));
                            text_read.setTextColor(getResources().getColor(R.color.grey));
                        }else{
                            text_subscribe.setText("Subscribed");
                            img_subscribe.setVisibility(View.GONE);
                            img_read.setColorFilter(ContextCompat.getColor(EBook_Details_Activity.this, R.color.colorPrimary));
                            text_read.setTextColor(getResources().getColor(R.color.colorPrimary));
                            card_subscribe.setEnabled(false);
                        }

                        genre_id=jsonObject.getString("genre_id");
                        description.setText(jsonObject.getString("description"));
                        if(!jsonObject.getString("author_name").toString().equals("")){
                            author.setText(jsonObject.getString("author_name"));
                        }else {
                            author.setText("Unknown");
                        }

                         image = String.valueOf(jsonObject.getString("p_image"));
                      Glide.with(EBook_Details_Activity.this)
                                .load(image)
                               .into(img_book);



                        progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(EBook_Details_Activity.this, "Response null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Slow network find", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EBook_Details_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(EBook_Details_Activity.this, "Review Submitted Successfully", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", pro_id);
                            startActivity(new Intent(EBook_Details_Activity.this,EBook_Details_Activity.class).putExtras(bundle));
                            finish();
                            progressDialog.dismiss();
                        }else if(jsonObject.getString("rec").equals("2")){
                            Toast.makeText(EBook_Details_Activity.this, "Review Updated", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", pro_id);
                            startActivity(new Intent(EBook_Details_Activity.this,EBook_Details_Activity.class).putExtras(bundle));
                            finish();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(EBook_Details_Activity.this, "Review not Submitted", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EBook_Details_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetch_reviews() {
        Call<String> call = myInterface.fetch_review(pro_id);
        final ProgressDialog progressDialog = ProgressDialog.show(EBook_Details_Activity.this,"loading","Please Wait");
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
                            Review_Adapter adapter = new Review_Adapter(EBook_Details_Activity.this,review_models);
                            rv_reviews.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(EBook_Details_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EBook_Details_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EBook_Details_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(EBook_Details_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(EBook_Details_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(EBook_Details_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }else{
                    Toast.makeText(EBook_Details_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EBook_Details_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void loadFragment(MyCart_Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame,fragment,fragment.getTag()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}