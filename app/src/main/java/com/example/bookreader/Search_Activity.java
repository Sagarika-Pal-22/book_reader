package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.Genre_Adapter;
import com.example.bookreader.Adapter.Search_Adapter;
import com.example.bookreader.Model.Genre_Model;
import com.example.bookreader.Model.SearchModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Activity extends AppCompatActivity {

    MyInterface myInterface;
    AutoCompleteTextView search_books;
    ArrayAdapter arrayAdapter;
    List<SearchModel> searchModelList;

    RecyclerView recycler;
    List<SearchModel> models;
    Search_Adapter adapter;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myInterface= ApiClient.getApiClient().create(MyInterface.class);

        models = new ArrayList<>();

        recycler= findViewById(R.id.recycler);
        search_books = findViewById(R.id.search_books);
        close = findViewById(R.id.close);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new Search_Adapter(Search_Activity.this,models);
        recycler.setAdapter(adapter);
        search_books.setThreshold(1);
        search_books.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

                if(count == 0){
                    recycler.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")){
                    recycler.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);
                }else{
                    filter(editable.toString());
                    close.setVisibility(View.VISIBLE);
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_books.setText("");
            }
        });

        search_all_books();

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SearchModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SearchModel s : models) {
            //if the existing elements contains the search input
            if (s.getBook_name().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
        recycler.setVisibility(View.VISIBLE);
    }

    public void search_all_books() {
        Call<String> call=myInterface.search_all_books();
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                try {
                    JSONArray jsonArray=new JSONArray(res);
                    if(jsonArray.length() == 0){
                        progressDialog.dismiss();
                        models.clear();
                        recycler.setVisibility(View.GONE);
                        Toast.makeText(Search_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                    }else{
                        models.clear();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            models.add(new SearchModel(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("image"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("type")
                            ));
                            progressDialog.dismiss();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Search_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

//    private void search_booklist() {
//        Call<String> call=myInterface.booklist();
//        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                String res=response.body();
//                try {
//                    JSONArray jsonArray=new JSONArray(res);
//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                        searchModelList.add(new SearchModel(
//                                jsonObject.getString("id"),
//                                jsonObject.getString("product_name")
//                        ));
//
//                        arrayAdapter = new ArrayAdapter(Search_Activity.this,android.R.layout.simple_dropdown_item_1line, searchModelList);
//                        search_books.setThreshold(1);
//                        search_books.setAdapter(arrayAdapter);
//                        progressDialog.dismiss();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(Search_Activity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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