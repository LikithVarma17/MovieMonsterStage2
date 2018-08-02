package com.example.acer.moviemonsters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyTrailer extends RecyclerView.Adapter<MyTrailer.MyHolder> {
    Context context;
    ArrayList<JsonTdata> jsonTdataList;

    public MyTrailer(Context filmDescription, ArrayList<JsonTdata> jsonTdataList) {
        this.jsonTdataList = jsonTdataList;
        this.context = filmDescription;
    }

    @NonNull
    @Override
    public MyTrailer.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.recycler_trailer;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View trailerview = inflater.inflate(id, parent, false);
        MyTrailer.MyHolder holder = new MyTrailer.MyHolder(trailerview);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyTrailer.MyHolder holder, int position) {
        holder.trview.setText(jsonTdataList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return jsonTdataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView trview;
        ImageView imageview;

        public MyHolder(View itemView) {
            super(itemView);
            trview = (TextView) itemView.findViewById(R.id.rv);
            trview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String url = jsonTdataList.get(position).getVideo_url();
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
            imageview = (ImageView) itemView.findViewById(R.id.id_you);
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String url = jsonTdataList.get(position).getVideo_url();
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }
    }
}
