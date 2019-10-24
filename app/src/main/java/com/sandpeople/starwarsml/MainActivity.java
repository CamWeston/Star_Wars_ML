package com.sandpeople.starwarsml;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

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
            }

        });

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

                }
        };
}

