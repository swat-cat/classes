package com.swat_cat.firstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;

import io.paperdb.Paper;

public class WelcomeActivity extends BaseActivity {

    private  FirebaseAuth auth;

    ImageView shoppingCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        shoppingCart = (ImageView) findViewById(R.id.shopping_cart);
        auth = FirebaseAuth.getInstance();
        ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(shoppingCart,"scaleX",0f, 1f);
        ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(shoppingCart,"scaleY",0f, 1f);
        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(scaleAnimX,scaleAnimY);
        scaleSet.setDuration(1000);
        ObjectAnimator translationAnimY = ObjectAnimator.ofFloat(shoppingCart, "translationY",200f);
        translationAnimY.setDuration(800);
        ObjectAnimator translationAnimX = ObjectAnimator.ofFloat(shoppingCart, "translationX",-1000f);
        translationAnimY.setDuration(800);

        ObjectAnimator alphaTextAnim = ObjectAnimator.ofFloat(findViewById(R.id.welcome), View.ALPHA, 0f,1f);
        alphaTextAnim.setDuration(1500);
        TextView t = (TextView) findViewById(R.id.welcome);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(scaleSet,translationAnimY, translationAnimX);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(t.getVisibility() == View.INVISIBLE){
                    t.setVisibility(View.VISIBLE);
                    shoppingCart.setVisibility(View.GONE);
                    alphaTextAnim.start();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user == null){
                            getNavigator().navigateTo(Screen.AUTH,ScreenType.ACTIVITY);
                        }else {
                            getNavigator().navigateTo(Screen.SHOPPING, ScreenType.ACTIVITY);
                        }
                    }
                },1000);
            }
        });
        set.start();
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return null;
    }
}
