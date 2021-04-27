package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookreader.Fragment.Home_Fragment;
import com.example.bookreader.Model.Cart_Model;
import com.example.bookreader.Model.User;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CartActivity extends AppCompatActivity {

    MyInterface myInterface;
    RelativeLayout relative;
    LinearLayout lin_no_product;
    RecyclerView recycler_cart;
    List<Cart_Model> cart_models;
    Cart_Adapter cart_adapter;
    TextView text_total;
    Button btn_buy,btn_find;
    User user;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user=new User(this);
        user_id=user.getUser_id();

        btn_buy = findViewById(R.id.btn_buy);
        btn_find = findViewById(R.id.btn_find);
        text_total = findViewById(R.id.text_total);
        relative = findViewById(R.id.relative);
        lin_no_product = findViewById(R.id.lin_no_product);

        cart_models = new ArrayList<>();
        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fetch_cart();

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,CheckOut_Activity.class));
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loadFragment(new Home_Fragment());
                startActivity(new Intent(CartActivity.this,Home_Activity.class));
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
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_cart, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

            holder.price.setText(cart_models.get(position).getTotal_price());
            holder.product_name.setText(cart_models.get(position).getProduct_name());
            holder.elegantnumberbutton.setNumber(cart_models.get(position).getQuantity());
            Glide.with(context)
                    .load(cart_models.get(position).getImage())
                    .into(holder.img_cart_1);

            holder.elegantnumberbutton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    if(oldValue<newValue){
                        cart_add(cart_models.get(position).getPrice(),cart_models.get(position).getPro_id());
//                        Toast.makeText(CartActivity.this, "added"+cart_models.get(position).getPrice(), Toast.LENGTH_SHORT).show();
                    }else if(oldValue>newValue) {
                        remove_from_cart(cart_models.get(position).getPro_id(),cart_models.get(position).getPrice());
//                        Toast.makeText(CartActivity.this, "removed"+cart_models.get(position).getPrice(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.linear_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", cart_models.get(position).getPro_id());
                    context.startActivity(new Intent(context, BookDetails_Activity.class).putExtras(bundle));
                }
            });
            holder.img_cart_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure to delete this item ?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            remove_from_cart(cart_models.get(position).getId());

                        }
                    }).setNegativeButton("Cancel",null);

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            });

        }
        @Override
        public int getItemCount() {
            return cart_models.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
             TextView price,product_name;
            ImageView img_cart_1,img_cart_delete;
            ElegantNumberButton elegantnumberbutton;
            LinearLayout linear_cart;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                price = itemView.findViewById(R.id.text_price);
                product_name = itemView.findViewById(R.id.text_title_cart);
                img_cart_delete = itemView.findViewById(R.id.img_cart_delete);
                img_cart_1 = itemView.findViewById(R.id.img_cart_1);
                elegantnumberbutton = itemView.findViewById(R.id.elegantnumberbutton);
                linear_cart = itemView.findViewById(R.id.linear_cart);

            }
        }
    }

    private void cart_add(String selling_price, String pro_id ) {
        Call<String> call=myInterface.add_cart(user_id,pro_id,selling_price);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(CartActivity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(CartActivity.this, "Added to Cart Succesfully", Toast.LENGTH_SHORT).show();
                            fetch_cart();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(CartActivity.this, "Not added to cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CartActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Slow Network", Toast.LENGTH_LONG).show();
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
                        if(jsonObject.getString("count").equals("0")){
                            relative.setVisibility(View.GONE);
                            lin_no_product.setVisibility(View.VISIBLE);
                        }
                        else {
                            relative.setVisibility(View.VISIBLE);
                            lin_no_product.setVisibility(View.GONE);
                        }
                        text_total.setText(jsonObject.getString("all_total"));
                        JSONArray jsonArray = jsonObject.getJSONArray("cart_product");
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            cart_models.clear();
                            relative.setVisibility(View.GONE);
                            lin_no_product.setVisibility(View.VISIBLE);
                            Toast.makeText(CartActivity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            relative.setVisibility(View.VISIBLE);
                            lin_no_product.setVisibility(View.GONE);
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
                            cart_adapter = new Cart_Adapter(CartActivity.this,cart_models);
                            recycler_cart.setAdapter(cart_adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
//                        Toast.makeText(CartActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CartActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void remove_from_cart(String pro_id,String selling_price) {
        Call<String> call=myInterface.remove_cart(user_id,pro_id,selling_price);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(CartActivity.this, "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            Toast.makeText(CartActivity.this, "Removed from Cart", Toast.LENGTH_SHORT).show();
                            fetch_cart();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(CartActivity.this, "not Removed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CartActivity.this, "Something Went Wrong"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void loadFragment(Home_Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_cart,fragment,fragment.getTag()).commit();
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