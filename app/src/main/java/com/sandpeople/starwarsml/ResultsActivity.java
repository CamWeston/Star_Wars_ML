package com.sandpeople.starwarsml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import static com.sandpeople.starwarsml.SearchActivity.DEV_MODE;

@SuppressWarnings("JavaDoc")
public class ResultsActivity extends AppCompatActivity {

    String predictedCharacter = "";
    TextView characterName;
    ImageView characterImage;
    ImageView forceSide;
    HashMap<String,String> charToForce = new HashMap<String, String>(){{
        put("BEN", "r");
        put("HAN", "r");
        put("LANDO", "r");
        put("LEIA", "r");
        put("LUKE", "r");
        put("THREEPIO", "r");
        put("VADER", "e");
        put("YODA", "r");
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        characterName = findViewById(R.id.character_name);
        characterImage = findViewById(R.id.character_profile);
        forceSide = findViewById(R.id.force_side);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEV_MODE) System.out.println("RESUMED RESULTS_ACTIVITY FROM TRANSITION_ACTIVITY");

        // Collect delivered data and setup the UI
        predictedCharacter = getIntent().getStringExtra("predictedCharacter");
        setupActivityUi();
    }


    private void setupActivityUi() {

        // Display the predicted character's name
        characterName.setText(getCharacterStringId(predictedCharacter));
        characterImage.setImageResource(R.drawable.threepio_profile);

        // Set the side of the Force the predicted character belongs to
        if (predictedCharacter.equals("VADER")) {
            forceSide.setImageResource(R.drawable.empire_profile);
        } else {
            forceSide.setImageResource(R.drawable.rebellion_profile);
        }

        // Set the image to the predicted character
        Character character = Character.valueOf(predictedCharacter);
        switch (character) {
            case BEN:
                setCharacterImage(R.drawable.ben_profile);
                break;
            case HAN:
                setCharacterImage(R.drawable.han_profile);
                break;
            case LANDO:
                setCharacterImage(R.drawable.lando_profile);
                break;
            case LEIA:
                setCharacterImage(R.drawable.leia_profile);
                break;
            case LUKE:
                setCharacterImage(R.drawable.luke_profile);
                break;
            case THREEPIO:
                setCharacterImage(R.drawable.threepio_profile);
                break;
            case VADER:
                setCharacterImage(R.drawable.vader_profile);
                break;
            case YODA:
                setCharacterImage(R.drawable.yoda_profile);
                break;
        }

    }

    private void setCharacterImage(int resource) {
        characterImage.setImageResource(resource);
    }

    public void close(View view) {
        if (DEV_MODE) System.out.println("GOING TO MAIN_ACTIVITY FROM RESULTS_ACTIVITY");
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * Determines the correct Character and find its display name in the String resources.
     * @param analysisResult
     * @return
     */
    private int getCharacterStringId(String analysisResult) {

        Character character = Character.valueOf(analysisResult);
        switch (character) {
            case BEN:
                return R.string.ben_display_name;
            case HAN:
                return R.string.han_display_name;
            case LANDO:
                return R.string.lando_display_name;
            case LEIA:
                return R.string.leia_display_name;
            case LUKE:
                return R.string.luke_display_name;
            case THREEPIO:
                return R.string.threepio_display_name;
            case VADER:
                return R.string.vader_display_name;
            case YODA:
                return R.string.yoda_display_name;
            default:
                return -1;

        }
    }

    private String getStringResource(int id) {
        return getResources().getString(id);
    }

}
