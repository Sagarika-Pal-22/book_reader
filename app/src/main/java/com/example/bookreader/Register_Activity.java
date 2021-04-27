package com.example.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookreader.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    MyInterface myInterface;
    User user;
    String user_id;
    Button btn_continue;
    TextView login;
    EditText edt_name,edt_number,edt_email,edt_pswrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user=new User(this);
        user_id=user.getUser_id();

        btn_continue = findViewById(R.id.btn_continue);
        login = findViewById(R.id.login);
        edt_name = findViewById(R.id.edt_name);
        edt_number = findViewById(R.id.edt_number);
        edt_email = findViewById(R.id.edt_email);
        edt_pswrd = findViewById(R.id.edt_pswrd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_Activity.this,Login_Activity.class));
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_name.getText().toString().equals("")){
                    edt_name.setError("Enter Your Name");
                } else if (edt_number.getText().toString().equals("")){
                    edt_number.setError("Enter Phone Number");
                }else if (edt_email.getText().toString().equals("")){
                    edt_email.setError("Enter Email Address");
                }else if (edt_pswrd.getText().toString().equals("")){
                    edt_pswrd.setError("Registered password id required");
                }else {
                    register();
                }

//                startActivity(new Intent(Register_Activity.this,Home_Activity.class));
            }
        });
        if (!user_id.equals("")){
            startActivity(new Intent(Register_Activity.this,Home_Activity.class));
            finish();
        }
    }

    private void register() {
        String name = edt_name.getText().toString();
        String number = edt_number.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_pswrd.getText().toString();

        Call<String> call=myInterface.register(name,number,email,password);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res==null){
                    Toast.makeText(Register_Activity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("rec").equals("2")){
                            Toast.makeText(Register_Activity.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        }else if (jsonObject.getString("rec").equals("1")){
                            user.setUser_id(jsonObject.getString("user_id"));
                            Toast.makeText(Register_Activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register_Activity.this,Home_Activity.class));
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Register_Activity.this, "Check Details", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Register_Activity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Register_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }
}