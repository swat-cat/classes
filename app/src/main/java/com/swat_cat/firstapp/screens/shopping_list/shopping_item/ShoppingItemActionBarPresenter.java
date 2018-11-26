package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.view.View;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.screens.shopping_list.GeneralBackActionBarPresenter;

public class ShoppingItemActionBarPresenter extends GeneralBackActionBarPresenter {

    public ShoppingItemActionBarPresenter(BaseActivity activity, ActionBarContract.View view, int titleRes) {
        super(activity, view, titleRes);
    }

    @Override
    public void setupView() {
        super.setupView();
        View iconRight = activity.getLayoutInflater().inflate(R.layout.ab_info,null);
        view.setupRightButton(iconRight);
        view.showRightButton(true);
    }

    @Override
    public void rightButtonAction() {
        super.rightButtonAction();
        activity.getBus().post(new InfoUserActionEvent());
    }
}
