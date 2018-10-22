package com.swat_cat.firstapp.services.navigation;

public interface BackNavigator {
    void navigateBack();
    void tryExitActivity();
    void setCouldNavigateBack(boolean couldNavigateBack);
}
