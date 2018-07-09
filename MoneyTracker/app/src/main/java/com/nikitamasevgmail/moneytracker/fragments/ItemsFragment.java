package com.nikitamasevgmail.moneytracker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.adapters.PriceAdapter;
import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.ArrayList;
import java.util.List;

public class ItemsFragment extends Fragment {

    private static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_INCOMES = 1;
    public static final int TYPE_EXPENSES = 2;

    public static final String TYPE_KEY = "type";

    private int type = TYPE_INCOMES;

    private RecyclerView rvAccBudget;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        type = bundle.getInt(TYPE_KEY,TYPE_UNKNOWN);

        if (type == TYPE_UNKNOWN) {
            throw new IllegalArgumentException("UNKNOWN TYPE OF FRAGMENT");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvAccBudget = view.findViewById(R.id.rv_item_list_activity);
        rvAccBudget.setLayoutManager(linearLayoutManager);
        rvAccBudget.setAdapter(new PriceAdapter(getContext()));
        //rvAccBudget.addItemDecoration(new PriceDividerItemDecorator(getContext(),linearLayoutManager.getOrientation()));
    }

    public static ItemsFragment createItemsFragment (int type) {
        Fragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ItemsFragment.TYPE_KEY,type);
        fragment.setArguments(bundle);
        return (ItemsFragment) fragment;
    }

}
