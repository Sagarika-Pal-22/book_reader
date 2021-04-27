package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.Order_Adapter;
import com.example.bookreader.Adapter.TopE_Book_Adapter;
import com.example.bookreader.Fragment.Home_Fragment;
import com.example.bookreader.Model.Order_Model;
import com.example.bookreader.Model.TopE_Book_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Activity extends AppCompatActivity {

    MyInterface myInterface;
    RecyclerView recycler_order;
    List<Order_Model> order_models;
    Order_Adapter order_adapter;
    LinearLayout lin_no_product;
    Button btn_add;
    User user;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();

        btn_add = findViewById(R.id.btn_add);
        lin_no_product = findViewById(R.id.lin_no_product);

        order_models = new ArrayList<>();

        recycler_order = findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        recycler_order.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fetch_my_order();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loadFragment(new Home_Fragment());
                startActivity(new Intent(Order_Activity.this,Home_Activity.class));
            }
        });

    }

    private void fetch_my_order() {
        Call<String> call = myInterface.fetch_order_list(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(Order_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            order_models.clear();
                            recycler_order.setVisibility(View.GONE);
                            lin_no_product.setVisibility(View.VISIBLE);
                            Toast.makeText(Order_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            recycler_order.setVisibility(View.VISIBLE);
                            lin_no_product.setVisibility(View.GONE);
                            order_models.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    order_models.add(new Order_Model(jsonObject.getString("id"),
                                            jsonObject.getString("image_1"),
                                            jsonObject.getString("image_2"),
                                            jsonObject.getString("product_name"),
                                            jsonObject.getString("invoice"),
                                            jsonObject.getString("total_price"),
                                            jsonObject.getString("date")));
                                }
                                order_adapter = new Order_Adapter(Order_Activity.this, order_models);
                                recycler_order.setAdapter(order_adapter);
                                progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Order_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Order_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Home_Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_order,fragment,fragment.getTag()).commit();
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