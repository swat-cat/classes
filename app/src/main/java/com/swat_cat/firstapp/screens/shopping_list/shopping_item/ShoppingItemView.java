package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.picasso.Picasso;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.utils.ImagePickChoiceView;

import java.io.File;

import io.reactivex.Observable;

public class ShoppingItemView implements ShoppingItemContract.View {

    private View root;

    private ImageView itemImage;
    private EditText titleInput;
    private EditText descriptionInput;
    private View saveBtn;
    private FrameLayout modalFrame;

    ShoppingItemView(View root){
        this.root = root;
        initView();
    }


    public void initView(){
        itemImage = root.findViewById(R.id.item_image);
        titleInput = root.findViewById(R.id.title);
        descriptionInput = root.findViewById(R.id.subtitle);
        saveBtn = root.findViewById(R.id.save_btn);
        modalFrame = root.findViewById(R.id.modal_frame);
    }


    @Override
    public Observable<Object> itemImageAction() {
        return RxView.clicks(itemImage);
    }

    @Override
    public void setItemImage(File imageFile) {
        Picasso.get()
                .load(imageFile)
                .placeholder(android.R.drawable.ic_menu_camera)
                .fit()
                .centerCrop()
                .into(itemImage);
    }

    @Override
    public Observable<CharSequence> titleInputChanged() {
        return RxTextView.textChanges(titleInput);
    }

    @Override
    public String getTitleText() {
        return titleInput.getText().toString().trim();
    }

    @Override
    public Observable<CharSequence> descriptionInputChanged() {
        return RxTextView.textChanges(descriptionInput);
    }

    @Override
    public String getDescriptionText() {
        return descriptionInput.getText().toString().trim();
    }

    @Override
    public Observable<Object> saveBtnAction() {
        return RxView.clicks(saveBtn);
    }

    @Override
    public void setSaveButtonEnabled(boolean enabled) {
        saveBtn.setEnabled(enabled);
    }

    @Override
    public void showImageChoiceModal(boolean show, ImagePickChoiceView.ImagePickViewCallback callback) {
        if (show){
            modalFrame.setVisibility(View.VISIBLE);
            modalFrame.findViewById(R.id.gallery_btn).setOnClickListener((v)->{
                callback.gallery();
            });
            modalFrame.findViewById(R.id.cancel_btn).setOnClickListener((v)->{
                callback.cancel();
            });
            modalFrame.findViewById(R.id.camera_btn).setOnClickListener((v)->{
                callback.camera();
            });
        }else {
            modalFrame.setVisibility(View.GONE);
        }
    }

    @Override
    public Observable<Object> backAction() {
        return null;
    }
}
