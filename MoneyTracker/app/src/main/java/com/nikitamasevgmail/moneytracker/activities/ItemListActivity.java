package com.nikitamasevgmail.moneytracker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.adapters.PriceAdapter;
import com.nikitamasevgmail.moneytracker.data.Price;
import com.nikitamasevgmail.moneytracker.decorators.PriceDividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView rvAccBudget;
    private List<Price> priceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setTitle(R.string.item_list_activity_header);

        priceList = new ArrayList<>();
        createDataList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAccBudget = findViewById(R.id.rv_item_list_activity);
        rvAccBudget.setLayoutManager(linearLayoutManager);
        rvAccBudget.setAdapter(new PriceAdapter(this,priceList));

        rvAccBudget.addItemDecoration(new PriceDividerItemDecorator(this,linearLayoutManager.getOrientation()));
    }

    private void createDataList() {
        priceList.add(new Price("Milk",70));
        priceList.add(new Price("Bread",30));
        priceList.add(new Price("Cheese",325));
        priceList.add(new Price("Courses",12000));
        priceList.add(new Price("",0));
        priceList.add(new Price("Lamborgini Aventador",23500000));
        priceList.add(new Price("Smartphone",45000));
        priceList.add(new Price("Cake",305));
        priceList.add(new Price("CPU",35000));
        priceList.add(new Price("Lime",530));
        priceList.add(new Price("GPU",250000));
        priceList.add(new Price("Eggs",275));
        priceList.add(new Price("House",589100014));
    }
}
