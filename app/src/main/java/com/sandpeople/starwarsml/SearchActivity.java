package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

public class SearchActivity extends AppCompatActivity {

    protected static Boolean DEV_MODE;
    private TextInputEditText twitterHandle;
    private static String userName;

    //holds the twitter username entered
    //private static String twitterHandle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        /*Initializing views*/
        twitterHandle = findViewById(R.id.TwitterHandle);
        twitterHandle.addTextChangedListener(handleWatcher);

        setThreadPolicy();

    }

    private void setThreadPolicy() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MAIN_ACTIVITY RESUMED FROM RESULTS_ACTIVITY");
    }

    /**
     * OnClick action for 'launch_button'.
     *
     * @param view Context current view.
     */
    public void goToTransitionActivity(View view) {

        JsonObject lambdaResponse = LambdaClient.execute(userName);

        if (lambdaResponse.get("error") != null) {
            Snackbar.make(view, "Enter a valid Twitter handle, you must", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            System.out.println("GOING TO TRANSITION_ACTIVITY FROM MAIN_ACTIVITY");
            Intent intent = new Intent(this, TransitionActivity.class);
            startActivity(intent);
        }
    }


        private final TextWatcher handleWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                twitterHandle.getText();

            }

            @Override
            public void afterTextChanged(Editable s) {
                userName = s.toString();
                twitterHandle.getText();
                System.out.println(userName);


            }
        };
    }

