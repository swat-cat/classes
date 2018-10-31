package com.swat_cat.firstapp.services;

import android.os.Bundle;

import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;

public interface Navigator {
    void navigateTo(Screen screen, ScreenType type);
    void navigateTo(Screen screen, ScreenType type, Bundle args);
}
