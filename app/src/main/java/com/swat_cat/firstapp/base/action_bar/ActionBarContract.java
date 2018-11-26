package com.swat_cat.firstapp.base.action_bar;


import io.reactivex.Observable;

public interface ActionBarContract {
    interface View{
        void showAB(boolean show);
        void showLeftButton(boolean show);
        void setupLeftButton(android.view.View view);
        void showRightButton(boolean show);
        void setupRightButton(android.view.View view);
        void showCenterText(boolean show);
        void setupCenterText(int res);
        void setupCenterText(String string);

        void setupBottomText(int res);
        void setupBottomText(String string);
        void showBottomText(boolean show);

        android.view.View getAB();
        Observable<Object> leftButtonAction();
        Observable<Object> rightButtonAction();
    }
    interface Presenter{
        void setupView();
        void setupActions();
        void leftButtonAction();
        void rightButtonAction();
    }
}
