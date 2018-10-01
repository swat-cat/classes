package com.swat_cat.firstapp.models;

/**
 * Created by max_ermakov on 10/1/18.
 */

public class ShoppingItem {
    private int image;
    private String title;
    private String subTitle;
    private boolean bought;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
