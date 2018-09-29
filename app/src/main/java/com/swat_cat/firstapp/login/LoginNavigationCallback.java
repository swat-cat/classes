package com.swat_cat.firstapp.login;

import android.os.Bundle;

/**
 * Created by max_ermakov on 9/29/18.
 */

public interface LoginNavigationCallback {
    void navigateToFeed(Bundle args);
    void navigateToForgotPassword(Bundle args);
}
