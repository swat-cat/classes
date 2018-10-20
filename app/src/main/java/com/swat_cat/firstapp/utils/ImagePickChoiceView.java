package com.swat_cat.firstapp.utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;

import timber.log.Timber;

public class ImagePickChoiceView extends ConstraintLayout {

    private View camera;
    private View gallery;
    private View cancel;

    public ImagePickChoiceView(Context context) {
        super(context);
        init();
    }

    public ImagePickChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImagePickChoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.image_pick_choice_layout,null);
    }

    public View getCamera() {
        return findViewById(R.id.camera_btn);
    }

    public View getGallery() {
        return findViewById(R.id.gallery_btn);
    }

    public View getCancel() {
        return findViewById(R.id.cancel_btn);
    }

    public interface ImagePickViewCallback{
        void camera();
        void gallery();
        void cancel();
    }
}
