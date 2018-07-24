package com.nikitamasevgmail.moneytracker.data;

import com.google.gson.annotations.SerializedName;

public class BalanceResult {

    private String status;

    @SerializedName("total_expenses")
    private int expense;

    @SerializedName("total_income")
    private int income;

    public BalanceResult(String status, int expense, int income) {
        this.status = status;
        this.expense = expense;
        this.income = income;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
