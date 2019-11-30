package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.sandpeople.starwarsml.SearchActivity.DEV_MODE;

public class TransitionActivity extends AppCompatActivity {

    private final int TRANSITION_DELAY = 1000;
    private ProgressBar progressBar;
    private int progress;
    private final long period = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        progressBar = findViewById(R.id.transition_progress_bar);
//        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgressDrawable(getDrawable(R.drawable.progress_bar_custom_style));
        progressBar.setProgress(0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progress < 100) {
                    progressBar.setProgress(progress);
                    progress++;
                } else {
                    System.out.println("Timer finished");
                    timer.cancel();
                    if (DEV_MODE) System.out.println("GOING TO RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("predictedCharacter", getIntent().getStringExtra("predictedCharacter"));
                    startActivity(intent);
                }
            }
        }, 0, period);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    private void transition() {
//        new Timer().schedule(
//            new TimerTask() {
//                @Override
//                public void run() {
//                    if (DEV_MODE) System.out.println("GOING TO RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");
//                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
//                    intent.putExtra("predictedCharacter", getIntent().getStringExtra("predictedCharacter"));
//                    startActivity(intent);
//                }
//            },
//            TRANSITION_DELAY
//        );
//    }

}
