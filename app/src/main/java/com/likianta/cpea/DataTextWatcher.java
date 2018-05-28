package com.likianta.cpea;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Likianta_Dodoora on 2018/4/14 0014.
 */
public class DataTextWatcher implements TextWatcher {
    private EditText editText = null;
    private String text;
    
    public DataTextWatcher(EditText editText) {
        this.editText = editText;
    }
    
    public String getText() {
        return text;
    }
    
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    
    }
    
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    
    }
    
    @Override
    public void afterTextChanged(Editable editable) {
//        text = editable.toString();
        text = editText.getText().toString();
    }
}
