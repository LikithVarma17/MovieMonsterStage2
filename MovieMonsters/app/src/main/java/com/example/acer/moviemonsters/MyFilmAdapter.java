package com.example.acer.moviemonsters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyFilmAdapter extends RecyclerView.Adapter<MyFilmAdapter.MyFilmHOlder> {
    private final ArrayList<JsonMovie> jsonMovies;
    int id, position;
    Context context;

    public MyFilmAdapter(MovieActivity movieActivity, ArrayList<JsonMovie> jsonMovies) {
        context = movieActivity;
        this.jsonMovies = jsonMovies;
    }

    @NonNull
    @Override
    public MyFilmAdapter.MyFilmHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        id = R.layout.activity_movie;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.movie_list, parent, false);
        MyFilmHOlder film = new MyFilmHOlder(v);
        return film;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFilmHOlder holder, final int position) {


        Picasso.with(context).load(jsonMovies.get(position).getPoster_path()).placeholder(R.mipmap.ic_launcher).into(holder.iv);
        final JsonMovie j = jsonMovies.get(position);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmDescription.class);
                intent.putExtra("x", (JsonMovie) j);
                context.startActivity(intent);
                ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return jsonMovies.size();
    }

    public class MyFilmHOlder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyFilmHOlder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.poster);

        }
    }
}

