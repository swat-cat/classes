package com.swat_cat.firstapp.base.dialogs.events;

/**
 * Created by max_ermakov on 1/18/17.
 */

public class ShowInfoDialog {
    public String title;
    public String infoMessage;

    public ShowInfoDialog(String title, String infoMessage) {
        this.title = title;
        this.infoMessage = infoMessage;
    }
}
