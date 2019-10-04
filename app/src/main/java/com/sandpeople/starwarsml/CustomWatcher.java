package com.sandpeople.starwarsml;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CustomWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //nothing has been entered
        s = "";

        //if a character has been entered

    }

    /**
     * onTextChanged will change the color
     * of the border around the editText
     * to alternate between blue and red
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() == 0){

        }
    }
}
