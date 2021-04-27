package com.example.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class Order_Successful_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__successful_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(Order_Successful_Activity.this,Home_Activity.class));
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}