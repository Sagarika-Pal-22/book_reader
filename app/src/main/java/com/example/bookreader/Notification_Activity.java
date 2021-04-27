package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.Notification_Adapter;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.Notification_Model;
import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

public class Notification_Activity extends AppCompatActivity {

    MyInterface myInterface;
    User user;
    String user_id;
    RecyclerView rv_notification;
    List<Notification_Model> notification_models;
    Notification_Adapter notification_adapter;
    TextView text_back;
    LinearLayout linear_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();

        linear_empty = findViewById(R.id.linear_empty);
        text_back = findViewById(R.id.text_back);

        notification_models = new ArrayList<>();
        rv_notification = findViewById(R.id.rv_notification);
        rv_notification.setHasFixedSize(true);
        rv_notification.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fetch_notification();
        change_notification_status();
//        rv_notification.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        notification_models.add(new Notification_Model("","Delivered: The Spy Chronicles: RAW, ISI and the Illusion of Peace.","8 days ago"));
//        notification_models.add(new Notification_Model("","Delivered: The Spy Chronicles: RAW, ISI and the Illusion of Peace.","8 days ago"));
//        notification_models.add(new Notification_Model("","Delivered: The Spy Chronicles: RAW, ISI and the Illusion of Peace.","8 days ago"));
//        notification_adapter = new Notification_Adapter(this,notification_models);
//        rv_notification.setAdapter(notification_adapter);

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notification_Activity.this,Home_Activity.class));
            }
        });

    }

    private void change_notification_status() {
        Call<String> call= myInterface.change_notification_status(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(Notification_Activity.this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    String res=response.body();
                    try {
                        JSONObject object=new JSONObject(res);
                        if (object.getString("rec").equals("1")){
                            progressDialog.dismiss();
                            fetch_notification();

                        }else {
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Notification_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Notification_Activity.this, "Slow network find", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_notification() {
        Call<String> call = myInterface.fetch_notification(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
//                    Toast.makeText(Notification_Activity.this, ""+res, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject_1 = new JSONObject(res);
                        if(jsonObject_1.getString("count").equals("0")){
                            linear_empty.setVisibility(View.VISIBLE);
                            rv_notification.setVisibility(View.GONE);
                        }else{
                            linear_empty.setVisibility(View.GONE);
                            rv_notification.setVisibility(View.VISIBLE);
                        }
                        JSONArray jsonArray = jsonObject_1.getJSONArray("notification");
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            notification_models.clear();
                            rv_notification.setVisibility(View.GONE);
                            Toast.makeText(Notification_Activity.this, "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_notification.setVisibility(View.VISIBLE);
                            linear_empty.setVisibility(View.GONE);
                            notification_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                notification_models.add(new Notification_Model(jsonObject.getString("id"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("message"),
                                        jsonObject.getString("date"),
                                        jsonObject.getString("time")));
                            }
                            Notification_Adapter adapter = new Notification_Adapter(Notification_Activity.this,notification_models);
                            rv_notification.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Notification_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Notification_Activity.this, "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Notification_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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