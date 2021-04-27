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

public class Login_Activity extends AppCompatActivity {

    MyInterface myInterface;
    User user;
    String user_id;

    EditText edt_number,edt_pswrd;
    Button btn_continue;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        myInterface= ApiClient.getApiClient().create(MyInterface.class);
        user=new User(this);
        user_id=user.getUser_id();
        if (!user_id.equals("")){
            startActivity(new Intent(Login_Activity.this,MainActivity.class)); finish();
        }

        edt_number = findViewById(R.id.edt_number);
        edt_pswrd = findViewById(R.id.edt_password);
        btn_continue = findViewById(R.id.btn_continue);
        register = findViewById(R.id.register);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_number.getText().toString().equals("")){
                    edt_number.setError("Enter Phone Number");
                }else if (edt_pswrd.getText().toString().equals("")){
                    edt_pswrd.setError("Registered password id required");
                }else {
                    login();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this,Register_Activity.class));
            }
        });
    }

    private void login() {
        final String number=edt_number.getText().toString();
        String pass=edt_pswrd.getText().toString();
        Call<String> call=myInterface.login(number,pass);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("rec").equals("1")){
                        user.setUser_id(jsonObject.getString("user_id"));
                        startActivity(new Intent(Login_Activity.this,Home_Activity.class));
                        finish();
                        progressDialog.dismiss();
                        Toast.makeText(Login_Activity.this, "Logging In", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login_Activity.this, "Please check again", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Login_Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Login_Activity.this, "Slow Network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}