package com.sandpeople.starwarsml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    //private TextInputEditText twitterHandle = findViewById(R.id.TwitterHandle);


    //holds the twitter username entered
    //private static String twitterHandle = "";

    //create an instance of custom text watcher class
    CustomWatcher watcher = new CustomWatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    Button launchButton = findViewById(R.id.launch_button);
    launchButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //System.out.println(twitterHandle.toString());
        }

    });


}
}

