package com.example.idonava.androidproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewAdapter extends  RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<MovieModel> mDataSource;
    private OnMovieClickListener movieClickListener;


    public MoviesViewAdapter(List<MovieModel> items,
                             OnMovieClickListener listener,
                             Context context) {
        mDataSource = items;
        movieClickListener = listener;
        mLayoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MoviesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewAdapter.ViewHolder holder, int position) {
        holder.onBindViewHolder(mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView ivImage;
        public final TextView tvTitle;
        public final TextView tvOverview;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.item_movie_iv);
            tvTitle = view.findViewById(R.id.item_movie_tv_title);
            tvOverview = view.findViewById(R.id.item_movie_tv_overview);
            view.setOnClickListener(this);
        }

        public void onBindViewHolder(MovieModel movieModel) {
            ivImage.setImageResource(movieModel.getMainImage());
            tvTitle.setText(movieModel.getMovieTitle());
            tvOverview.setText(movieModel.getOverview());
        }

        @Override
        public void onClick(View view) {
            if (movieClickListener == null) return;
            movieClickListener.onMovieClicked(getAdapterPosition());
        }
    }
}

