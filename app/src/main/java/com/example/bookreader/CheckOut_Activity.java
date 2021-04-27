package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.bookreader.Adapter.CheckOut_Book_Adapter;
import com.example.bookreader.Model.Books_Chkout_Model;
import com.example.bookreader.Model.Cart_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class CheckOut_Activity extends AppCompatActivity {

    MyInterface myInterface;
    User user;
    String user_id,total="0",address="";
    RecyclerView rv_book_list;
    List<Cart_Model> cart_models;
    Cart_Adapter cart_adapter;
    TextView text_total;
    TextView text_address;
    RadioButton radio_payment;
    Button btn_order;
    ImageView edit_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();

        radio_payment =findViewById(R.id.radio_payment);
        btn_order =findViewById(R.id.btn_order);
        text_total =findViewById(R.id.text_total);
        text_address =findViewById(R.id.text_address);
        edit_img =findViewById(R.id.edit_img);

        cart_models = new ArrayList<>();
        rv_book_list =findViewById(R.id.rv_book_list);
        rv_book_list.setHasFixedSize(true);
        rv_book_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fetch_cart();

        fetch_address();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.equals("N")){
                    Toast.makeText(CheckOut_Activity.this, "Add Address", Toast.LENGTH_SHORT).show();
                }else{
                    add_order();
                }

            }
        });
        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOut_Activity.this,Address_Activity.class));
            }
        });





    }

    private void fetch_address() {
        Call<String> call = myInterface.fetch_profile(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if(jsonObject.getString("address").equals("null") || jsonObject.getString("pincode").equals("null")
                        || jsonObject.getString("city").equals("null")){
                            address = "N";
                        }else{
                            address = "Y";
                            text_address.setText("Pincode: " + jsonObject.getString("pincode")+"\n"+"Address: " + jsonObject.getString("address")+
                                    "\n"+"Locality: " + jsonObject.getString("locality")+"\n"+"City: " + jsonObject.getString("city")+
                                    "\n"+"State: " + jsonObject.getString("state"));
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(CheckOut_Activity.this, "Response null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CheckOut_Activity.this, "Slow network find", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void add_order() {
        Call<String> call=myInterface.add_order(user_id,total,"Online Payment");
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(CheckOut_Activity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(CheckOut_Activity.this, "Added to Cart Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CheckOut_Activity.this,Order_Successful_Activity.class));
                            finish();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(CheckOut_Activity.this, "Not added to cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CheckOut_Activity.this, "Something Went Wrong"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CheckOut_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetch_cart() {
        Call<String> call = myInterface.fetch_cart(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        text_total.setText(jsonObject.getString("all_total"));
//                        total = jsonObject.getString("all_total");
                        JSONArray jsonArray = jsonObject.getJSONArray("cart_product");
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            cart_models.clear();
                            Toast.makeText(CheckOut_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            cart_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                cart_models.add(new Cart_Model(jsonObject1.getString("cart_id"),
                                        jsonObject1.getString("product_id"),
                                        jsonObject1.getString("quantity"),
                                        jsonObject1.getString("image"),
                                        jsonObject1.getString("product_name"),
                                        jsonObject1.getString("selling_price"),
                                        jsonObject1.getString("per_pro_total")));
                            }
                            cart_adapter = new Cart_Adapter(CheckOut_Activity.this,cart_models);
                            rv_book_list.setAdapter(cart_adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(CheckOut_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CheckOut_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CheckOut_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder> {

        Context context;
        List<Cart_Model> cart_models = new ArrayList<>();

        public Cart_Adapter(Context context, List<Cart_Model> cart_models) {
            this.context = context;
            this.cart_models = cart_models;
        }

        @NonNull
        @Override
        public Cart_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_total_book, parent, false);
            return new Cart_Adapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Cart_Adapter.MyViewHolder holder, final int position) {

            holder.price.setText(cart_models.get(position).getPrice());
            holder.text_books.setText(cart_models.get(position).getProduct_name());
            holder.quantity.setText("x "+ cart_models.get(position).getQuantity() +" =");
            holder.total_price.setText(cart_models.get(position).getTotal_price());


        }
        @Override
        public int getItemCount() {
            return cart_models.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView text_books,price,quantity,total_price;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                price = itemView.findViewById(R.id.price);
                text_books = itemView.findViewById(R.id.text_books);
                quantity = itemView.findViewById(R.id.quantity);
                total_price = itemView.findViewById(R.id.per_pro_total);


            }
        }
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