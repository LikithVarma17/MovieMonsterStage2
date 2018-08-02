package com.example.acer.moviemonsters;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FilmDescription extends AppCompatActivity {
    TextView tv_data;
    ImageView iv_image;
    RecyclerView recycler, recyclerView;
    CheckBox fav;
    ArrayList<JsonTdata> jsonTdataList;
    ArrayList<JsonReview> jsonReviewArrayList;
    int id;
    JsonMovie j;
    SQLiteDatabase db;
    DataBase dataBase = new DataBase(FilmDescription.this);

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_description);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_review);
        jsonTdataList = new ArrayList<>();
        jsonReviewArrayList = new ArrayList<>();
        j = (JsonMovie) getIntent().getExtras().get("x");
        iv_image = (ImageView) findViewById(R.id.front_poster);
        String s = j.getPoster_path();
        String s2 = j.getBackdrop_path();
        id = j.getId();
        Picasso.with(FilmDescription.this).load(s).into(iv_image);
        ImageView iv_image2 = (ImageView) findViewById(R.id.back_poster);
        Picasso.with(FilmDescription.this).load("http://image.tmdb.org/t/p/w500" + s2 + "?api_key=78bdf0295b5959b8b7fc3b150628fcef").into(iv_image2);
        tv_data = (TextView) findViewById(R.id.id_title);
        tv_data.setText(j.getOriginal_title());
        tv_data = (TextView) findViewById(R.id.rating);
        tv_data.setText("Rating : " + j.getVote_average());
        tv_data = (TextView) findViewById(R.id.votes);
        tv_data.setText("Public Votes : " + j.getVote_count());
        tv_data = (TextView) findViewById(R.id.release);
        tv_data.setText("Date Of Release : " + j.getRelease_date());
        tv_data = (TextView) findViewById(R.id.language);
        tv_data.setText(j.getOverview());
        db = dataBase.getWritableDatabase();
        int height = tv_data.getMeasuredHeight();
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ll.setBackgroundResource(R.drawable.mv);
        fav = (CheckBox) findViewById(R.id.fav);
        if (dataBase.search(j.getId())) {
            fav.setChecked(true);
            fav.setButtonDrawable(R.drawable.checked);
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav.isChecked()) {
                    Toast.makeText(getApplicationContext(), "selected", Toast.LENGTH_SHORT).show();
                    fav.setButtonDrawable(R.drawable.checked);
                    SQLiteDatabase db = dataBase.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("Bgposter", j.getBackdrop_path());
                    cv.put("Frposter", j.getPoster_path());
                    cv.put("title", j.getOriginal_title());
                    cv.put("releasedate", j.getRelease_date());
                    cv.put("rating", j.getVote_average());
                    cv.put("votes", j.getVote_count());
                    cv.put("language", j.getOriginal_language());
                    cv.put("id", id);
                    cv.put("overview", j.getOverview());
                    Uri uri = getContentResolver().insert(ContentProviders.CONTENT_URI, cv);

                } else {
                    Toast.makeText(getApplicationContext(), "removed", Toast.LENGTH_SHORT).show();
                    fav.setButtonDrawable(R.drawable.unchecked);
                    String stringId = Integer.toString(id);
                    int uri = getContentResolver().delete(ContentProviders.CONTENT_URI, stringId, null);
                }
            }
        });

        new AsyncTaskTrailer().execute();

    }

    public class AsyncTaskTrailer extends AsyncTask<String, Void, String> {
        String myurl;
        String response;

        @Override
        protected String doInBackground(String... strings) {
            myurl = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=78bdf0295b5959b8b7fc3b150628fcef";
            Http connection = new Http();
            URL url = connection.buildUrl(myurl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject trailer = jsonArray.getJSONObject(i);
                    String video_url = "https://www.youtube.com/watch?v=" + "" + trailer.getString("key");
                    String language = trailer.getString("iso_639_1");
                    String name = trailer.getString("name");
                    String type = trailer.getString("type");
                    String country = trailer.getString("iso_3166_1");
                    String site = trailer.getString("site");
                    long size = trailer.getLong("size");
                    String id = trailer.getString("id");
                    JsonTdata jsonTdata = new JsonTdata(video_url, language, name, type, country, site, size, id);
                    jsonTdataList.add(jsonTdata);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myurl = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=78bdf0295b5959b8b7fc3b150628fcef";
            connection = new Http();
            url = connection.buildUrl(myurl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject review = jsonArray.getJSONObject(i);
                    String link = review.getString("url");
                    String author = review.getString("author");
                    String content = review.getString("content");
                    String id = review.getString("id");
                    JsonReview jsonReview = new JsonReview(author, content, link, id);
                    jsonReviewArrayList.add(jsonReview);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //tv.setText(s);
            MyTrailer trailer = new MyTrailer(FilmDescription.this, jsonTdataList);
            recycler.setLayoutManager(new LinearLayoutManager(FilmDescription.this));
            recycler.setAdapter(trailer);
            Review review = new Review(FilmDescription.this, jsonReviewArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(FilmDescription.this));
            recyclerView.setAdapter(review);

        }
    }

}
