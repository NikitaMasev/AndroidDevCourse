package com.nikitamasevgmail.moneytracker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikitamasevgmail.moneytracker.view.DiagramView;
import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.data.BalanceResult;
import com.nikitamasevgmail.moneytracker.retrofit.Api;
import com.nikitamasevgmail.moneytracker.retrofit.App;
import com.nikitamasevgmail.moneytracker.retrofit.ServerStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceFragment extends Fragment {

    private static final String TAG = "BalanceFragment";

    private TextView tvTotal;
    private TextView tvExpenses;
    private TextView tvIncome;
    private DiagramView diagramView;

    private Api api;
    private App app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (App) getActivity().getApplication();
        api = app.getApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotal = view.findViewById(R.id.tv_balance_total);
        tvExpenses = view.findViewById(R.id.tv_balance_expenses);
        tvIncome = view.findViewById(R.id.tv_balance_income);
        diagramView = view.findViewById(R.id.balance_diagram);

        loadData();
    }

    private void loadData() {
        Call<BalanceResult> call = api.getBalance();

        call.enqueue(new Callback<BalanceResult>() {
            @Override
            public void onResponse(Call<BalanceResult> call, Response<BalanceResult> response) {
                BalanceResult result = response.body();
                Log.d(TAG, result.getStatus());

                if (result.getStatus().equals(ServerStatus.SERVER_OK)) {
                    updateUI(result);
                } else {
                    showError(getString(R.string.status_balance_server_connection_err));
                }
            }

            @Override
            public void onFailure(Call<BalanceResult> call, Throwable t) {
                showError(getString(R.string.status_balance_server_connection_err)+" "+t.getMessage());
            }
        });
    }

    private void updateUI(BalanceResult result) {
        tvTotal.setText(getString(R.string.pattern_format_balance, result.getIncome() - result.getExpense()));
        tvExpenses.setText(getString(R.string.pattern_format_balance, result.getExpense()));
        tvIncome.setText(getString(R.string.pattern_format_balance, result.getIncome()));
        diagramView.update(result.getIncome(), result.getExpense());
    }

    private void showError(String err) {
        Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
    }
}
