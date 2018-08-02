package com.example.acer.moviemonsters;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {
    public static String POSITION = "scrollPosition";
    RecyclerView recyclerView;
    MyFilmAdapter filmAdapter;
    ConnectivityManager manager;
    JsonAsync jsonAsync;
    ProgressDialog progressDialog;
    NetworkInfo activeNetwork;
    GridLayoutManager gridLayoutManager;
    ArrayList<JsonMovie> JSONlist;
    String KEY = "lk";
    Cursor cursor;
    int position = 0;
    private static final String API=BuildConfig.API_KEY;
    String s1 = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API;



    String value = "popular";
    DataBase dataBase = new DataBase(this);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (value == "favourites") {
            JSONlist = new ArrayList<JsonMovie>();
            SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
            cursor = getContentResolver().query(ContentProviders.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                JsonMovie jsonMovie = new JsonMovie(cursor.getInt(6), cursor.getInt(0), cursor.getDouble(5), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(8), cursor.getString(7));
                JSONlist.add(jsonMovie);

            }
            cursor.close();
            filmAdapter = new MyFilmAdapter(MovieActivity.this, JSONlist);
            recyclerView.setLayoutManager(new GridLayoutManager(MovieActivity.this, 2));
            recyclerView.setAdapter(filmAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        recyclerView = findViewById(R.id.id_recyclerview);
        progressDialog = new ProgressDialog(this);
        gridLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
        String s = "https://api.themoviedb.org/3/movie/popular?api_key="+API;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (savedInstanceState != null)
            position = savedInstanceState.getInt(POSITION);
        if (savedInstanceState != null) {
            if (isConnected) {
                if (savedInstanceState.getString(KEY) == "popular") {
                    value = savedInstanceState.getString(KEY);
                    gridLayoutManager.removeAndRecycleAllViews(recyclerView.new Recycler());
                    jsonAsync = new JsonAsync(s);
                    jsonAsync.execute();
                } else if (savedInstanceState.getString(KEY) == "top_rated") {
                    gridLayoutManager.removeAndRecycleAllViews(recyclerView.new Recycler());
                    value = savedInstanceState.getString(KEY);
                    jsonAsync = new JsonAsync(s1);
                    jsonAsync.execute();
                } else if (savedInstanceState.getString(KEY) != "favourites") {
                    jsonAsync = new JsonAsync(s);
                    jsonAsync.execute();
                }

            } else
                Toast.makeText(getApplicationContext(), "No internet You can see Favourites", Toast.LENGTH_SHORT).show();
            if (savedInstanceState.getString(KEY) == "favourites") {
                value = savedInstanceState.getString(KEY);
                JSONlist = new ArrayList<JsonMovie>();
                SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
                cursor = getContentResolver().query(ContentProviders.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    JsonMovie jsonMovie = new JsonMovie(cursor.getInt(6), cursor.getInt(0), cursor.getDouble(5), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(8), cursor.getString(7));
                    JSONlist.add(jsonMovie);

                }
                cursor.close();
                filmAdapter = new MyFilmAdapter(MovieActivity.this, JSONlist);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(filmAdapter);
                recyclerView.scrollToPosition(position);
            }
        } else {
            if (isConnected) {
                jsonAsync = new JsonAsync(s);
                jsonAsync.execute();
            } else
                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, value);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            if (!progressDialog.isShowing()) {
                position = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                outState.putInt(POSITION, position);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();

        if (option == R.id.action_sort) {
            value = "top_rated";
            position = 0;
            String s1 = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API;
            filmAdapter = new MyFilmAdapter(MovieActivity.this, new ArrayList<JsonMovie>());
            gridLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(filmAdapter);
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                jsonAsync = new JsonAsync(s1);
                jsonAsync.execute();
            } else {
                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        if (option == R.id.action_popular) {
            value = "popular";
            position = 0;
            String s1 = "https://api.themoviedb.org/3/movie/popular?api_key="+API;
            filmAdapter = new MyFilmAdapter(MovieActivity.this, new ArrayList<JsonMovie>());
            gridLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(filmAdapter);
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                jsonAsync = new JsonAsync(s1);
                jsonAsync.execute();
            } else {
                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }
        }
        if (option == R.id.ref) {
            finish();
            startActivity(getIntent());
        }
        if (option == R.id.action_favourities) {
            value = "favourites";
            position = 0;
            JSONlist = new ArrayList<JsonMovie>();
            SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
            cursor = getContentResolver().query(ContentProviders.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                JsonMovie jsonMovie = new JsonMovie(cursor.getInt(6), cursor.getInt(0), cursor.getDouble(5), cursor.getString(3), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(8), cursor.getString(7));
                JSONlist.add(jsonMovie);

            }
            cursor.close();
            filmAdapter = new MyFilmAdapter(MovieActivity.this, JSONlist);
            recyclerView.setLayoutManager(new GridLayoutManager(MovieActivity.this, 2));
            recyclerView.setAdapter(filmAdapter);
        }
        return super.onOptionsItemSelected(item);

    }

    public class JsonAsync extends AsyncTask<String, Void, String> {
        String response;
        ArrayList<JsonMovie> JsonMovies;
        String myurl = "";

        public JsonAsync(String s) {
            this.myurl = s;
        }

        public JsonAsync() {

        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading data");
            progressDialog.show();
            JsonMovies = new ArrayList<JsonMovie>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {
            Http connection = new Http();
            URL url = connection.buildUrl(myurl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.optJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject movie = jsonArray.optJSONObject(i);
                    int vote_count = movie.optInt("vote_count");
                    int id = movie.optInt("id");
                    boolean video = movie.optBoolean("video");
                    long vote_average = movie.optLong("vote_average");
                    String title = movie.optString("title");
                    long popularity = movie.optLong("popularity");
                    String poster_path = "https://image.tmdb.org/t/p/w300" + "" + movie.optString("poster_path");
                    Log.i("pp", poster_path);
                    String original_language = movie.optString("original_language");
                    String original_title = movie.optString("original_title");
                    String backdrop_path = movie.optString("backdrop_path");
                    boolean adult = movie.optBoolean("adult");
                    String overview = movie.optString("overview");
                    String release_date = movie.optString("release_date");
                    JsonMovie JsonMovie = new JsonMovie(vote_count, id, vote_average, poster_path,
                            original_language, original_title, backdrop_path, overview, release_date);
                    JsonMovies.add(JsonMovie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            filmAdapter = new MyFilmAdapter(MovieActivity.this, JsonMovies);
            gridLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(filmAdapter);
            recyclerView.scrollToPosition(position);
        }
    }
}


