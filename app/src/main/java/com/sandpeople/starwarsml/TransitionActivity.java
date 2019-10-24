package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.sandpeople.starwarsml.SearchActivity.DEV_MODE;

public class TransitionActivity extends AppCompatActivity {

    private final int TRANSITION_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        // Temporary implementation: can switch to check for full download of data and immediately switch.
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (DEV_MODE) System.out.println("GOING TO RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");
                        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                        startActivity(intent);
                    }
                },
                TRANSITION_DELAY
        );

    }

}
