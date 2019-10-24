package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.sandpeople.starwarsml.MainActivity.DEV_MODE;

public class ResultsActivity extends AppCompatActivity {

    Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEV_MODE) System.out.println("RESUMED RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");
    }

    public void closeThis(View view) {
        if (DEV_MODE) System.out.println("GOING TO MAIN_ACTIVITY FROM RESULTS_ACTIVITY");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
