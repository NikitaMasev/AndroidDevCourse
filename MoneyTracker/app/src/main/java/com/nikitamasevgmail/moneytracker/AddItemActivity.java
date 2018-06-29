package com.nikitamasevgmail.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    private EditText editTextName;
    private EditText editTextPrice;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextName = findViewById(R.id.name_addItem);
        editTextPrice = findViewById(R.id.price_addItem);
        btnAdd = findViewById(R.id.btn_addItem);

        ETWatcher etWatcher = new ETWatcher(editTextName,editTextPrice,btnAdd);

        editTextPrice.addTextChangedListener(etWatcher);
        editTextName.addTextChangedListener(etWatcher);
    }

}
