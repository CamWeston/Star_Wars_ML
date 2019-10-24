package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

import static com.sandpeople.starwarsml.MainActivity.DEV_MODE;

public class TransitionActivity extends AppCompatActivity {

    private final int TRANSITION_DELAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Temporary implementation: can switch to check for full download of data and immediately switch.
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (DEV_MODE) System.out.println("SWITCHING TO MAIN_ACTIVITY");
                        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                        startActivity(intent);
                    }
                },
                TRANSITION_DELAY
        );


    }

}
