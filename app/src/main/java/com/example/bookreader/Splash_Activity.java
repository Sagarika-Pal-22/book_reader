package com.example.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Activity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView =findViewById(R.id.imageView);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    startActivity(new Intent(Splash_Activity.this,Register_Activity.class));
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