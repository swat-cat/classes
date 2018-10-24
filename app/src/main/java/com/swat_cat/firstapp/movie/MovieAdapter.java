package com.swat_cat.firstapp.movie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.swat_cat.firstapp.models.ShoppingItem;
import com.swat_cat.firstapp.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MovieAdapter extends BaseAdapter {

    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public int getCount() {
        return images().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85,85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView = (ImageView) convertView;
        }
        List<File> files = images();
        Picasso.get()
                .load(files.get(position))
                .resize(100, 100)
                .centerCrop()
                .into(imageView);
        return null;
    }

    private List<File> images() {
        List<ShoppingItem> items = Paper.book().read(Constants.ITEMS, new ArrayList<>());
        List<File> files = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            files.add(new File(items.get(i).getImage()));
        }
        return files;
    }
}
