package com.nikitamasevgmail.moneytracker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitamasevgmail.moneytracker.activities.AddItemActivity;
import com.nikitamasevgmail.moneytracker.retrofit.Api;
import com.nikitamasevgmail.moneytracker.retrofit.App;
import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.adapters.PriceAdapter;
import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.List;

public class ItemsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ItemsFragment";

    private static final int ADD_ITEM_REQUEST_CODE = 123;
    public static final String TYPE_KEY = "type";
    private String type;

    private RecyclerView rvAccBudget;
    private FloatingActionButton fab;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PriceAdapter priceAdapter;
    private Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Price.TYPE_EXPENSES);

        if (type.equals(Price.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("UNKNOWN TYPE OF FRAGMENT");
        }

        api = ((App) getActivity().getApplication()).getApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvAccBudget = view.findViewById(R.id.rv_item_list_activity);
        rvAccBudget.setLayoutManager(linearLayoutManager);
        priceAdapter = new PriceAdapter(getContext());
        rvAccBudget.setAdapter(priceAdapter);
        //rvAccBudget.addItemDecoration(new PriceDividerItemDecorator(getContext(),linearLayoutManager.getOrientation()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRL_fragment_items);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPrices();
            }
        });

        fab = view.findViewById(R.id.fab_fragment_items);
        fab.setOnClickListener(this);

        loadPrices();
    }

    public static ItemsFragment createItemsFragment(String type) {
        Fragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.TYPE_KEY, type);
        fragment.setArguments(bundle);
        return (ItemsFragment) fragment;
    }

    private void loadPrices() {
        Call<List<Price>> call = api.getPrice(type);

        call.enqueue(new Callback<List<Price>>() {
            @Override
            public void onResponse(Call<List<Price>> call, Response<List<Price>> response) {
                priceAdapter.setData(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Price>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_fragment_items) {
            Intent intent = new Intent(getContext(), AddItemActivity.class);
            intent.putExtra(AddItemActivity.TYPE_KEY,type);
            startActivityForResult(intent,ADD_ITEM_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == ADD_ITEM_REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            Price price = data.getParcelableExtra(AddItemActivity.TYPE_KEY_DATA);
            priceAdapter.addPrice(price);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
