package com.nikitamasevgmail.moneytracker.data;

public class Price {

    private String title;
    private int price;
    private String comment;

    public Price(String title, int price) {
        this.title = title;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
