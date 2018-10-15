package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public interface ShoppingItemContract {
    interface View{
        Observable<Object> itemImageInputChanged();
        ImageView getImageView();
        Observable<CharSequence> titleInputChanged();
        String getTitleText();
        void setTitleInputError(@Nullable String error);
        Observable<CharSequence> descriptionInputChanged();
        String getDescriptionText();
        void setDescriptionInputError(@Nullable String error);
        Observable<Object> saveBtnAction();
    }
    interface Presenter{
        void start(View view);
        void stop();
        void saveItem();

    }
}
