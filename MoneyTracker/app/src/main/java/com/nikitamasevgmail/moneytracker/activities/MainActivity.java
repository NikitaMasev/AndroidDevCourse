package com.nikitamasevgmail.moneytracker.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.adapters.MainPagesAdapter;
import com.nikitamasevgmail.moneytracker.fragments.ItemsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "INFO";

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private static FloatingActionButton fab;
    private MainPagesAdapter mainPagesAdapter;
    private String[] fragmentsName;
    private ActionMode actionMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsName = getResources().getStringArray(R.array.fragments_type);

        toolbar = findViewById(R.id.toolBar_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fab = findViewById(R.id.fab_fragment_items);

        fab.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.item_list_activity_header);

        mainPagesAdapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_fragment_items) {
            Intent intent = new Intent(this, AddItemActivity.class);
            intent.putExtra(AddItemActivity.TYPE_KEY, fragmentsName[viewPager.getCurrentItem()]);
            startActivityForResult(intent, ItemsFragment.ADD_ITEM_REQUEST_CODE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MainPagesAdapter.PAGE_EXPENSES:
                fab.show();
                break;
            case MainPagesAdapter.PAGE_INCOMES:
                fab.show();
                break;
            case MainPagesAdapter.PAGE_BALANCE:
                fab.hide();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                fab.setEnabled(true);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                fab.setEnabled(false);
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                if (actionMode!=null) {
                    actionMode.finish();
                }
                fab.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment: getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        fab.hide();
        actionMode = mode;
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        fab.show();
        actionMode = null;
    }
}
