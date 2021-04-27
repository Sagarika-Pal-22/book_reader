package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address_Activity extends AppCompatActivity {

    MyInterface myInterface;
    User user;
    String user_id;
    EditText edit_pin,edit_address,edit_locality,edit_city,edit_state;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(this);
        user_id = user.getUser_id();

        edit_pin = findViewById(R.id.edit_pin);
        edit_address = findViewById(R.id.edit_address);
        edit_locality = findViewById(R.id.edit_locality);
        edit_city = findViewById(R.id.edit_city);
        edit_state = findViewById(R.id.edit_state);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_pin.getText().toString().equals("")){
                    edit_pin.setError("Enter Pin");
                } else if (edit_address.getText().toString().equals("")){
                    edit_address.setError("Enter Details");
                }else if (edit_locality.getText().toString().equals("")){
                    edit_locality.setError("Enter Locality");
                }else if (edit_city.getText().toString().equals("")){
                    edit_city.setError("Enter City");
                }else if (edit_state.getText().toString().equals("")){
                    edit_state.setError("Enter State");
                }else {
                    update_address();
                }
            }
        });

        fetch_address();

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

                        if(jsonObject.getString("pincode").equals("null")){
                            edit_pin.setHint("Add Pin");
                        }else {
                            edit_pin.setText(jsonObject.getString("pincode"));
                        }

                        if(jsonObject.getString("address").equals("null")){
                            edit_address.setHint("Add address");
                        }else {
                            edit_address.setText(jsonObject.getString("address"));
                        }

                        if(jsonObject.getString("locality").equals("null")){
                            edit_locality.setHint("Add locality");
                        }else {
                            edit_locality.setText(jsonObject.getString("locality"));
                        }

                        if(jsonObject.getString("city").equals("null")){
                            edit_city.setHint("Add city");
                        }else {
                            edit_city.setText(jsonObject.getString("city"));
                        }

                        if(jsonObject.getString("state").equals("null")){
                            edit_state.setHint("Add State");
                        }else {
                            edit_state.setText(jsonObject.getString("state"));
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Address_Activity.this, "Response null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Address_Activity.this, "Slow network find", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void update_address() {
        final String pin=edit_pin.getText().toString();
        String address=edit_address.getText().toString();
        String locality=edit_locality.getText().toString();
        String city=edit_city.getText().toString();
        String state=edit_state.getText().toString();
        Call<String> call=myInterface.add_address(user_id,pin,address,locality,city,state);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("rec").equals("1")){
//                        user.setUser_id(jsonObject.getString("user_id"));
                        startActivity(new Intent(Address_Activity.this,Home_Activity.class));
                        finish();
                        progressDialog.dismiss();
                        Toast.makeText(Address_Activity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Address_Activity.this, "Please check again", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Address_Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Address_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}