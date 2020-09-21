package com.smartbizz.Util;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class GenericTextWatcher implements TextWatcher {

    public GenericTextWatcher() {
       // editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
