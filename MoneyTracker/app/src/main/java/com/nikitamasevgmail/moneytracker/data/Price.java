package com.nikitamasevgmail.moneytracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Price implements Parcelable{

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";

    private int id;
    private String name;
    private int price;
    private String type;

    public Price(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    protected Price(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        type = in.readString();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeString(type);
    }
}
