package com.sandpeople.starwarsml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText twitterHandle;

    private final int USERNAMELIMIT = 15;

    //holds the twitter username entered
    //private static String twitterHandle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Initializing views*/
        twitterHandle = findViewById(R.id.TwitterHandle);

        twitterHandle.addTextChangedListener(handleWatcher);
        //twitterHandle.addTextChangedListener(watcher);

        Button launchButton = findViewById(R.id.launch_button);
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("MainActivity::" + R.id.launch_button,"Currently skipping username verification.");
                if (enteredTwitterHandleIsValid()) {
                    Intent intent = new Intent(getApplicationContext(), TransitionActivity.class);
                    startActivity(intent);
                }

            }

        });

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

