package com.sandpeople.starwarsml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class SearchActivity extends AppCompatActivity {

    public static boolean DEV_MODE = true;
    private TextInputEditText twitterHandle;
    private final int USERNAMELIMIT = 15;

    //holds the twitter username entered
    //private static String twitterHandle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*Initializing views*/
        twitterHandle = findViewById(R.id.TwitterHandle);

        twitterHandle.addTextChangedListener(handleWatcher);
        //twitterHandle.addTextChangedListener(watcher);

        setThreadPolicy();

    }

    private void setThreadPolicy(){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEV_MODE) System.out.println("MAIN_ACTIVITY RESUMED FROM RESULTS_ACTIVITY");
    }

    /**
     * OnClick action for 'launch_button'.
     * @param view Context current view.
     */
    public void goToTransitionActivity(View view) {
        if (DEV_MODE) System.out.println("CURRENTLY SKIPPING TWITTER HANDLE VERIFICATION");

        if (enteredTwitterHandleIsValid()) {
            if (DEV_MODE) System.out.println("GOING TO TRANSITION_ACTIVITY FROM MAIN_ACTIVITY");
            Intent intent = new Intent(this, TransitionActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(view, "Enter a valid Twitter handle, you must", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    /**
     * The method to verify if a user-entered Twitter handle is a valid, existing handle.
     * Currently hard-coded for development.
     * @return Whether or not the Twitter handle the user entered exists.
     */
    private boolean enteredTwitterHandleIsValid() {

        // Needs implementation
        return true;

    }

    private final TextWatcher handleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String username = s.toString();
            if(username.length() == 0 && start == 0 && count == 0 && after == 0){
                twitterHandle.setBackgroundColor(Color.YELLOW);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           

            String username = s.toString();
            //no text has been entered
            if(username.length() == 0){
                twitterHandle.setBackgroundColor(Color.YELLOW);
                twitterHandle.getText();
            }
            //the background will change to red if the
            //number of characters is odd and not 0

            else if(username.length() % 2 != 0) {
                twitterHandle.setBackgroundColor(Color.RED);
                twitterHandle.getText();

            }
            //the background will change to blue if the
            //number of characters is even and not 0
            else{
                twitterHandle.setBackgroundColor(Color.BLUE);
                twitterHandle.getText();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
                String username = s.toString();
                //if the character count is 15 and there is
            //no more input from the keyboard
            if(username.length() == USERNAMELIMIT){
                twitterHandle.getText();
                System.out.println(username);
            }

        }
    };

}

