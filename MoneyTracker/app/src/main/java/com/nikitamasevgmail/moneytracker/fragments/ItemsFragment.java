package com.nikitamasevgmail.moneytracker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nikitamasevgmail.moneytracker.activities.MainActivity;
import com.nikitamasevgmail.moneytracker.dialogs.ConfirmationDialog;
import com.nikitamasevgmail.moneytracker.listeners.ConfirmationDialogListener;
import com.nikitamasevgmail.moneytracker.listeners.PriceAdapterListener;
import com.nikitamasevgmail.moneytracker.activities.AddItemActivity;
import com.nikitamasevgmail.moneytracker.retrofit.Api;
import com.nikitamasevgmail.moneytracker.retrofit.App;
import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.adapters.PriceAdapter;
import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.List;

public class ItemsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ItemsFragment";

    public static final int ADD_ITEM_REQUEST_CODE = 123;
    public static final String TYPE_KEY = "type";
    private String type;

    private RecyclerView rvAccBudget;
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
        swipeRefreshLayout = view.findViewById(R.id.swipeRL_fragment_items);

        priceAdapter.setPriceAdapterListener(new PriceAdapterManager());

        rvAccBudget.setAdapter(priceAdapter);
        //rvAccBudget.addItemDecoration(new PriceDividerItemDecorator(getContext(),linearLayoutManager.getOrientation()));

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPrices();
            }
        });

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == ADD_ITEM_REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            Price price = data.getParcelableExtra(AddItemActivity.TYPE_KEY_DATA);

            if (price.getType().equals(type)) {
                priceAdapter.addPrice(price);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void removeSelectedPrices() {
        for (int i = priceAdapter.getSelectedPrices().size() - 1; i>=0; i--) {
            priceAdapter.remove(priceAdapter.getSelectedPrices().get(i));
        }
    }


    /*
        ACTION MODE
     */

    private ActionMode actionMode = null;

    private class PriceAdapterManager implements PriceAdapterListener {

        @Override
        public void onPriceClick(Price price, int pos) {
            if (isInActionMode()) {
                toggleSelection(pos);
            }
        }

        @Override
        public void onPriceLongClick(Price price, int pos) {
            if (isInActionMode()) {
                return;
            }

            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
            toggleSelection(pos);
        }

        private boolean isInActionMode() {
            return actionMode!=null;
        }

        private void toggleSelection(int pos) {
            priceAdapter.toggleSelection(pos);
        }

        private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.prices_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.price_remove_menu:
                        showDialog();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                priceAdapter.clearSelections();
                actionMode = null;
            }
        };

        private void showDialog() {
            ConfirmationDialog confirmationDialog = new ConfirmationDialog();
            confirmationDialog.show(getFragmentManager(),ConfirmationDialog.TAG_ID);
            confirmationDialog.setConfirmationDialogListener(new ConfirmationDialogManager());
        }

        private class ConfirmationDialogManager implements ConfirmationDialogListener {

            @Override
            public void onClickPositive() {
                removeSelectedPrices();
                actionMode.finish();
            }

            @Override
            public void onClickNegative() {
                actionMode.finish();
            }
        }
    }
}
