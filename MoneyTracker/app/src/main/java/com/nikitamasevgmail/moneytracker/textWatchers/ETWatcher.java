package com.nikitamasevgmail.moneytracker.textWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class ETWatcher implements TextWatcher {

    private Button btnAdd;
    private EditText editTextName;
    private EditText editTextPrice;

    public ETWatcher(EditText editTextName, EditText editTextPrice,Button btnAdd) {
        this.editTextName = editTextName;
        this.editTextPrice = editTextPrice;
        this.btnAdd = btnAdd;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        btnAdd.setEnabled((!editTextName.getText().toString().isEmpty()) && (!editTextPrice.getText().toString().isEmpty()));
    }
}
