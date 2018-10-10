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

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Intent i = new Intent(this,MainActivity.class);
       /* i.putExtra("TEST","TEST_TEST");*/
        ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(findViewById(R.id.welcome),"scaleX",1.0f, 0.5f);
        ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(findViewById(R.id.welcome),"scaleY",1.0f, 0.5f);
        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(scaleAnimX,scaleAnimY);
        scaleSet.setDuration(1000);
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(findViewById(R.id.welcome), "translationY",200f);
        translationAnim.setDuration(800);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(findViewById(R.id.welcome), View.ALPHA,1f,0.5f);
        alphaAnim.setDuration(800);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(scaleSet,translationAnim,alphaAnim);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                    }
                },1000);
            }
        });
        set.start();
    }
}
