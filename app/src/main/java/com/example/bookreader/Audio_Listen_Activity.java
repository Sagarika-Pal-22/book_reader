package com.example.bookreader;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Audio_Listen_Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

     Toolbar toolbar;
     ImageView imageView, img_back, img_frwrd, img_play, img_pause;
     MediaPlayer mPlayer;
     TextView author, genre, run_time, start_time, song_time;
     SeekBar seekBar;
     static int oTime =0, sTime =0, eTime =0, fTime = 5000, bTime = 5000;
     Handler hdlr = new Handler();
    ProgressBar progressBar;
    String pro_id, audio_url, image, author_name;

    private Handler mHandler = new Handler();
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio__listen_);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("book_name"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_frwrd = findViewById(R.id.img_frwrd);
        img_back = findViewById(R.id.img_back);
        img_play = findViewById(R.id.img_play);
        img_pause = findViewById(R.id.img_pause);
        start_time = findViewById(R.id.start_time);
        song_time = findViewById(R.id.song_time);
        seekBar = findViewById(R.id.seekBar);
        imageView = findViewById(R.id.imageView);
        author = findViewById(R.id.author);
        genre = findViewById(R.id.genre);
        run_time = findViewById(R.id.run_time);
        progressBar = findViewById(R.id.progressBar);

        utils = new Utilities();
        mPlayer = new MediaPlayer();

        // Listeners
        seekBar.setOnSeekBarChangeListener(this);
        mPlayer.setOnCompletionListener(this);

        pro_id=getIntent().getExtras().getString("id");
        audio_url=getIntent().getExtras().getString("audio_url");
        image=getIntent().getExtras().getString("img_book");
        author.setText("Author: "+ getIntent().getExtras().getString("author"));
        genre.setText("Genre: "+ getIntent().getExtras().getString("genre"));

        if(image!=null){
            Glide.with(this)
                    .load(image)
                    .into(imageView);
        }
        else {
            Glide.with(this)
                    .load(R.drawable.no_image)
                    .into(imageView);
        }


        seekBar.setClickable(true);

        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check for already playing
                if(mPlayer.isPlaying()){
                    if(mPlayer!=null){
                        mPlayer.pause();
                        // Changing button image to play button
                        img_play.setImageResource(R.drawable.play);
                    }
                }else{
                    // Resume song
                    if(mPlayer!=null){
                        try {
                            mPlayer.reset();
                            mPlayer.setDataSource(audio_url);
                            mPlayer.prepare();
                            mPlayer.start();

                            // Changing Button Image to pause image
                            img_play.setImageResource(R.drawable.pause);

                            // set Progress bar values
                            seekBar.setProgress(0);
                            seekBar.setMax(100);

                            // Updating progress bar
                            updateProgressBar();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        mPlayer.start();
                        // Changing button image to pause button
                        img_play.setImageResource(R.drawable.pause);
                    }
                }

//                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(Audio_Listen_Activity.this, "Playing Audio", Toast.LENGTH_SHORT).show();
//                img_pause.setVisibility(View.VISIBLE);
//                img_play.setVisibility(View.GONE);
//                mPlayer = new MediaPlayer();
//                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                try{
//                    mPlayer.setDataSource(audio_url);
//
//                    mPlayer.prepare();
//                    mPlayer.start();
//                    if(progressBar!=null && progressBar.isShown()){
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                }catch (IOException e){
//                    // Catch the exception
//                    e.printStackTrace();
//                }catch (IllegalArgumentException e){
//                    e.printStackTrace();
//                }catch (SecurityException e){
//                    e.printStackTrace();
//                }catch (IllegalStateException e){
//                    e.printStackTrace();
//                }
//
//                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(Audio_Listen_Activity.this,"End",Toast.LENGTH_SHORT).show();
//                        img_play.setVisibility(View.VISIBLE);
//                        img_pause.setVisibility(View.GONE);
//                    }
//                });
//
//                eTime = mPlayer.getDuration();
//                sTime = mPlayer.getCurrentPosition();
//                if(oTime == 0){
//                    seekBar.setMax(eTime);
//                    oTime =1;
//                }
//                song_time.setText(String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(eTime),
//                        TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(eTime))) );
//                start_time.setText(String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
//                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(sTime))) );
//                seekBar.setProgress(sTime);
//                hdlr.postDelayed(UpdateSongTime, 100);

            }
        });
        img_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                img_play.setVisibility(View.VISIBLE);
                img_pause.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Pausing Audio", Toast.LENGTH_SHORT).show();
            }
        });
        img_frwrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get current song position
                int currentPosition = mPlayer.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mPlayer.getDuration()){
                    // forward song
                    mPlayer.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    mPlayer.seekTo(mPlayer.getDuration());
                }

//                if((sTime + fTime) <= eTime)
//                {
//                    sTime = sTime + fTime;
//                    mPlayer.seekTo(sTime);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get current song position
                int currentPosition = mPlayer.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition - seekBackwardTime >= 0){
                    // forward song
                    mPlayer.seekTo(currentPosition - seekBackwardTime);
                }else{
                    // backward to starting position
                    mPlayer.seekTo(0);
                }

//                if((sTime - bTime) > 0)
//                {
//                    sTime = sTime - bTime;
//                    mPlayer.seekTo(sTime);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mPlayer.getDuration();
            long currentDuration = mPlayer.getCurrentPosition();

            // Displaying Total Duration time
            song_time.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            start_time.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            seekBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };
//    private Runnable UpdateSongTime = new Runnable() {
//        @Override
//        public void run() {
//            sTime = mPlayer.getCurrentPosition();
//            start_time.setText(String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
//                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
//            seekBar.setProgress(sTime);
//            hdlr.postDelayed(this, 100);
//        }
//    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
            mPlayer.reset();
            mPlayer.stop();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
        mPlayer.reset();
        mPlayer.stop();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Toast.makeText(Audio_Listen_Activity.this,"End",Toast.LENGTH_SHORT).show();
                        img_play.setImageResource(R.drawable.pause);
//                        img_pause.setVisibility(View.GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer.release();
    }

}