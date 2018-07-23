package com.nikitamasevgmail.moneytracker.data;

import com.google.gson.annotations.SerializedName;

public class AuthResult {
    private String status;
    private int id;

    @SerializedName("auth_token")
    private String token;

    public AuthResult(String status, int id, String token) {
        this.status = status;
        this.id = id;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
//{"status":"success","id":105,"auth_token":"$2y$10$64MZj4khUDGkdirrZbISleb2LbRsH7zheLvPlhl4i6aFPUuX7QIi6"}