package com.sandpeople.starwarsml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int progressPercent = 0;
    TextView progress;
    TextView title;
    final long period = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        title = findViewById(R.id.AppName);
        progressBar= findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(0);
        progress = findViewById(R.id.ProgressPercentage);



        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (progressPercent<100){
                    runOnUiThread(() -> progress.setText(progressPercent +"%"));
                    progressBar.setProgress(progressPercent);
                    progressPercent++;
                }else{
                    //closing the timer
                    timer.cancel();
                    Intent intent = new Intent(SplashScreenActivity.this,SearchActivity.class);
                    // close this activity
                    startActivity(intent);
                    finish();
                }
            }
        }, 0, period);

    }

}
