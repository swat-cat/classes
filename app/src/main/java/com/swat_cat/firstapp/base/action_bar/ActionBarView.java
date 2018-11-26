package com.swat_cat.firstapp.base.action_bar;

import android.animation.LayoutTransition;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;

import io.reactivex.Observable;

public class ActionBarView implements ActionBarContract.View {

    private View root;
    private ViewGroup leftContainer;
    private ViewGroup rightContainer;
    private View centerContainer;
    private TextView title;
    private TextView subtitle;

    public ActionBarView(View root) {
        this.root = root;
        init();
    }

    private void init() {
        leftContainer = root.findViewById(R.id.left_container);
        rightContainer = root.findViewById(R.id.right_container);
        centerContainer = root.findViewById(R.id.center_container);
        title = root.findViewById(R.id.center_text);
        subtitle = root.findViewById(R.id.bottom_text);
    }

    @Override
    public void showAB(boolean show) {
        root.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void showLeftButton(boolean show) {
        leftContainer.setVisibility(show?View.VISIBLE:View.INVISIBLE);
        leftContainer.setEnabled(show);
    }

    @Override
    public void setupLeftButton(View view) {
        addViewSafe(leftContainer,view,null,null);
    }

    @Override
    public void showRightButton(boolean show) {
        rightContainer.setVisibility(show?View.VISIBLE:View.INVISIBLE);
        rightContainer.setEnabled(show);
    }

    @Override
    public void setupRightButton(View view) {
        addViewSafe(rightContainer,view,null,null);
    }

    @Override
    public void showCenterText(boolean show) {
        title.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void setupCenterText(int res) {
        title.setText(res);
    }

    @Override
    public void setupCenterText(String string) {
        title.setText(string);
    }

    @Override
    public void setupBottomText(int res) {
        subtitle.setText(res);
    }

    @Override
    public void setupBottomText(String string) {
        subtitle.setText(string);
    }

    @Override
    public void showBottomText(boolean show) {
        subtitle.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public View getAB() {
        return root;
    }

    @Override
    public Observable<Object> leftButtonAction() {
        return RxView.clicks(leftContainer);
    }

    @Override
    public Observable<Object> rightButtonAction() {
        return RxView.clicks(rightContainer);
    }

    public static void addViewSafe(@Nullable ViewGroup parentNew, @Nullable View view, @Nullable Integer index, @Nullable ViewGroup.LayoutParams layoutParams) {
        if (parentNew == null || view == null)
            return;

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            LayoutTransition transition = parent.getLayoutTransition();

            parent.setLayoutTransition(null);
            parent.removeView(view);
            parent.setLayoutTransition(transition);
        }

        if (index != null) {
            if (layoutParams != null) {
                parentNew.addView(view, index, layoutParams);
            } else {
                parentNew.addView(view, index);
            }
        } else {
            if (layoutParams != null) {
                parentNew.addView(view, layoutParams);
            } else {
                parentNew.addView(view);
            }
        }
    }
}
