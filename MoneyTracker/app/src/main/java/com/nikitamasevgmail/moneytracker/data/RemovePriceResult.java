package com.nikitamasevgmail.moneytracker.data;

public class RemovePriceResult {
    private String status;
    private int id;

    public RemovePriceResult(String status, int id) {
        this.status = status;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
