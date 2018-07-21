package com.nikitamasevgmail.moneytracker.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.data.Price;
import com.nikitamasevgmail.moneytracker.fragments.BalanceFragment;
import com.nikitamasevgmail.moneytracker.fragments.ItemsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPagesAdapter extends FragmentPagerAdapter {

    public static final int PAGE_INCOMES = 0;
    public static final int PAGE_EXPENSES = 1;
    public static final int PAGE_BALANCE = 2;

    private String[] titles;
    private List<Fragment> fragmentsActionMode;

    public MainPagesAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.tab_titles);
        fragmentsActionMode = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_INCOMES:
                ItemsFragment incomes = ItemsFragment.createItemsFragment(Price.TYPE_INCOMES);
                fragmentsActionMode.add(incomes);
                return incomes;

            case PAGE_EXPENSES:
                ItemsFragment expenses = ItemsFragment.createItemsFragment(Price.TYPE_EXPENSES);
                fragmentsActionMode.add(expenses);
                return expenses;

            case PAGE_BALANCE:
                return new BalanceFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public List<Fragment> getFragmentsActionMode() {
        return fragmentsActionMode;
    }
}
