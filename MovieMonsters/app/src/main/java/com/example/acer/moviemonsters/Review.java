package com.example.acer.moviemonsters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Review extends RecyclerView.Adapter<Review.Holder> {
    Context context;
    ArrayList<JsonReview> jsonReviews;

    public Review(Context filmDescription, ArrayList<JsonReview> jsonReviewArrayList) {
        this.jsonReviews = jsonReviewArrayList;
        this.context = filmDescription;

    }

    @NonNull
    @Override
    public Review.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.review;
        Context context = parent.getContext();
        LayoutInflater Inflater = LayoutInflater.from(context);
        View review = Inflater.inflate(id, parent, false);
        Holder holder = new Holder(review);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Review.Holder holder, int position) {
        holder.author.setText(jsonReviews.get(position).getAuthor());
        holder.content.setText(jsonReviews.get(position).getContent());
        holder.link.setText(jsonReviews.get(position).getLink());

    }

    @Override
    public int getItemCount() {
        return jsonReviews.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;
        TextView link;

        public Holder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.id_author);
            content = (TextView) itemView.findViewById(R.id.id_content);
            link = (TextView) itemView.findViewById(R.id.id_link);
        }
    }
}
