package com.swat_cat.firstapp.screens.movie_search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;

import java.util.List;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<SearchItemDTO> list;

    public MovieAdapter(List<SearchItemDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchItemDTO movie = list.get(position);
        Picasso.get()
                .load(movie.getPoster())
                .into(holder.poster);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            year = itemView.findViewById(R.id.year);
        }
    }
}
