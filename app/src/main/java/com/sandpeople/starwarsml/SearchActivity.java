package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.ref.WeakReference;


public class SearchActivity extends AppCompatActivity {

    // TODO: HANDLE_INVALID
    private enum LambdaResponseResult {
        SUCCESS,
        TWEETS_LAMBDA_FAILED,
        ML_LAMBDA_FAILED
    }

    protected static Boolean DEV_MODE = true;
    private TextInputEditText twitterHandleInput;
    private static String twitterHandle;
    private LambdaResponseResult lambdaResponseResult;
    private String predictedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initializing views
        twitterHandleInput = findViewById(R.id.twitter_handle);
        twitterHandleInput.addTextChangedListener(handleWatcher);
        setThreadPolicy();

        // DEV
//        goToTransitionActivity();

    }

    private void setThreadPolicy() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
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
    public void submitHandle(View view) {

        // Get entered twitter handle.
        Editable editable = twitterHandleInput.getText();
        twitterHandle = editable == null ? "" : editable.toString();
        if (DEV_MODE) twitterHandle = "elonmusk";

        // Execute the Lambda.
        new Lambda(this).execute();

        // Begin Lambda-processing animation.

    }

    /**
     * Determines the correct action based on the result of the Lambda thread's execution.
     * @param result
     */
    private void processLambdaResult(LambdaResponseResult result) {
        switch (result) {
            case SUCCESS:
                // Move on to the next activity.
                goToTransitionActivity();
                break;
            case TWEETS_LAMBDA_FAILED:
                // Indicate that the entered Twitter handle was not valid.
                Toast.makeText(getApplicationContext(), "Enter a valid Twitter handle, you must...", Toast.LENGTH_LONG).show();
                break;
            case ML_LAMBDA_FAILED:
                break;
        }
    }

    private void goToTransitionActivity() {
        if(DEV_MODE) System.out.println("GOING TO TRANSITION_ACTIVITY FROM MAIN_ACTIVITY");
        Intent intent = new Intent(this, TransitionActivity.class);
        intent.putExtra("predictedCharacter", predictedCharacter);
        startActivity(intent);
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
            twitterHandle = s.toString();
        }
    };

    // TODO: Correctly implement error handling to present errors cleanly.
    private static class Lambda extends AsyncTask<Void, Void, JsonObject> {

        // Reference to SearchActivity.
        private WeakReference<SearchActivity> activityReference;

        Lambda(SearchActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected JsonObject doInBackground(Void... params) {
            return LambdaClient.execute(twitterHandle);
        }

        @Override
        protected void onPostExecute(JsonObject result) {

            // Define reference to SearchActivity
            SearchActivity activity = activityReference.get();

            if (result.get("error") != null) {
                activity.lambdaResponseResult = LambdaResponseResult.TWEETS_LAMBDA_FAILED;
            } else {
                try {
                    // TODO: Improve code and error handling. Should maybe return an object that contains request response result and data.
                    // Clean 'result', execute MLAPI, and define the predicted character
                    result.remove("prof_image"); result.remove("user_exists");
                    String mlLambdaResponse = LambdaClient.predictCharacter(result);
                    activity.lambdaResponseResult = LambdaResponseResult.SUCCESS;
                    activity.predictedCharacter = mlLambdaResponse;
                    if (DEV_MODE) System.out.println("Predicted: " + mlLambdaResponse + "for " + twitterHandle);
                } catch (IOException e) {
                    activity.lambdaResponseResult = LambdaResponseResult.ML_LAMBDA_FAILED;
                    e.printStackTrace();
                }
            }

            // Callback to process the Lambda execution's result
            System.out.println(activity.lambdaResponseResult);
            activity.processLambdaResult(activity.lambdaResponseResult);

        }

    }

}

