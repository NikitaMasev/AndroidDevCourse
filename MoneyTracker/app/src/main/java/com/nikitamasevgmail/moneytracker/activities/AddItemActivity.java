package com.nikitamasevgmail.moneytracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nikitamasevgmail.moneytracker.data.Price;
import com.nikitamasevgmail.moneytracker.textWatchers.ETWatcher;
import com.nikitamasevgmail.moneytracker.R;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AddItemActivity";

    public static final String TYPE_KEY_DATA = "data";
    public static final String TYPE_KEY = "type";
    private String type;

    private EditText editTextName;
    private EditText editTextPrice;
    private Button btnAdd;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        type = getIntent().getStringExtra(TYPE_KEY);

        editTextName = findViewById(R.id.name_addItem);
        editTextPrice = findViewById(R.id.price_addItem);
        btnAdd = findViewById(R.id.btn_addItem);
        toolbar = findViewById(R.id.toolBar_add_item);

        btnAdd.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_item_activity_header);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ETWatcher etWatcher = new ETWatcher(editTextName,editTextPrice,btnAdd);

        editTextPrice.addTextChangedListener(etWatcher);
        editTextName.addTextChangedListener(etWatcher);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_addItem) {
            Intent intent = new Intent();
            intent.putExtra(TYPE_KEY_DATA, new Price(editTextName.getText().toString(),Integer.valueOf(editTextPrice.getText().toString()),type));

            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
