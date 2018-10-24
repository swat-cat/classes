package com.swat_cat.firstapp.list_example;

import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.data.models.ShoppingItem;

import java.io.File;
import java.util.List;

/**
 * Created by max_ermakov on 10/1/18.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>{

    private List<ShoppingItem> items;
    private ItemBoughtCallback callback;

    public ShoppingListAdapter(List<ShoppingItem> items, ItemBoughtCallback callback) {
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            ShoppingItem item = items.get(position);


            if (item.isBought()) {
                holder.title.setTextColor(Color.RED);
            } else {
                holder.title.setTextColor(Color.BLACK);
            }

            holder.title.setText(item.getTitle());
            holder.subTitle.setText(item.getSubTitle());
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                File f = new File(item.getImage());
                Picasso.get()
                        .load(f)
                        .resize(100, 100)
                        .centerCrop()
                        .into(holder.image);
            }
            holder.checkBox.setChecked(item.isBought());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    callback.itemBought(item, isChecked,position);
                }
            });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView subTitle;
        AppCompatCheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subtitle);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
