package com.sandpeople.starwarsml;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    protected static Boolean DEV_MODE = true;
    private TextInputEditText twitterHandle;
    private static String userName;
    private String predictedCharacter;
    private JsonObject tweetsLambdaResponse = new JsonObject();
    private String mlLambdaResponse;

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

        if (DEV_MODE) userName = "elonmusk";

        new Lambda(this).execute();
//        new MlLambda(this, tweetsLambdaResponse).execute();


//        if (lambdaResponse.get("error") != null) {
//            Snackbar.make(view, "Enter a valid Twitter handle, you must", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        } else {
//            if(DEV_MODE) System.out.println("GOING TO TRANSITION_ACTIVITY FROM MAIN_ACTIVITY");
//            Intent intent = new Intent(this, TransitionActivity.class);
//            startActivity(intent);
//        }

    }

    private final TextWatcher handleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userName = s.toString();
        }
    };

    // TODO: Correctly implement error handling to present errors cleanly.
    private static class Lambda extends AsyncTask<Void, Void, JsonObject> {

        private WeakReference<SearchActivity> activityReference;

        Lambda(SearchActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected JsonObject doInBackground(Void... params) {

            JsonObject lambdaResponse = LambdaClient.execute(userName);

            if (DEV_MODE) {
                Type type = new TypeToken<List<String>>(){}.getType();
                String data = lambdaResponse.get("tweets").toString();
                List<String> tweets = LambdaClient.gson.fromJson(data, type);
                System.out.println("Received tweets: " + tweets);
            }

            return lambdaResponse;

        }

        // MAYBE CREATE A BOOLEAN???????
        @Override
        protected void onPostExecute(JsonObject result) {
            activityReference.get().tweetsLambdaResponse = result;
            if (result != null) {
                try {
                    result.remove("prof_image"); result.remove("user_exists");
                    String prediction = LambdaClient.predictCharacter(result);
                    if (DEV_MODE) System.out.println("Predicted: " + prediction);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

