package com.sandpeople.starwarsml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.sandpeople.starwarsml.SearchActivity.DEV_MODE;

public class TransitionActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progress;
    final private long period = 45; // Update to 45 when development is complete.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        progressBar = findViewById(R.id.transition_progress_bar);
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progress < 100) {
                    progressBar.setProgress(progress);
                    progress++;
                } else {
                    timer.cancel();
                    goToResultsActivity();
                }
        }}, 0, period);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void goToResultsActivity() {
        if (DEV_MODE) System.out.println("GOING TO RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("predictedCharacter", getIntent().getStringExtra("predictedCharacter"));
        startActivity(intent);
    }

}
