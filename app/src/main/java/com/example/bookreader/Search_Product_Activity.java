package com.example.bookreader;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.bookreader.Adapter.EBook_Adapter;
import com.example.bookreader.Model.SearchModel;
import com.example.bookreader.Model.TopE_Book_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Product_Activity extends AppCompatActivity {

    MyInterface myInterface;
    RecyclerView rv_search;
    List<TopE_Book_Model> models;
    EBook_Adapter adapter;
    String id, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__product_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        id=getIntent().getExtras().getString("id");
        type=getIntent().getExtras().getString("type");

        models =new ArrayList<>();
        rv_search = findViewById(R.id.rv_search);
        rv_search.setHasFixedSize(true);
        rv_search.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new EBook_Adapter(this,models);
        rv_search.setAdapter(adapter);
        search_each_books();
    }

    private void search_each_books() {
        Call<String> call = myInterface.search_each_books(id,type);
        final ProgressDialog progressDialog = ProgressDialog.show(Search_Product_Activity.this, "loading", "Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            models.clear();
                            rv_search.setVisibility(View.GONE);
                            Toast.makeText(Search_Product_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_search.setVisibility(View.VISIBLE);
                            models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("genre")));
                            }
                            EBook_Adapter adapter = new EBook_Adapter(Search_Product_Activity.this,models);
                            rv_search.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Search_Product_Activity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Search_Product_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Search_Product_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }
}