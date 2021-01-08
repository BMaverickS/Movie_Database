package com.example.moviedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class search_movie_adapter extends RecyclerView.Adapter<search_movie_adapter.Movieholder> {

    Context ctx;
    ArrayList<Movie_items> movie_items;

    public search_movie_adapter(Context ctx, ArrayList<Movie_items> movie_items) {
        this.ctx = ctx;
        this.movie_items = movie_items;
    }

    @NonNull
    @Override
    public Movieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.search_movie_adapter, parent, false);

        Movieholder movieholder = new Movieholder(view);
        return movieholder;
    }

    @Override
    public void onBindViewHolder(@NonNull search_movie_adapter.Movieholder holder, int position) {
        final Movie_items curr = movie_items.get(position);

        final String image = curr.getMovieimage();
        final String name = curr.getMoviename();
        final String year = curr.getMovieyear();
        final String id = curr.getMovieid();

        Glide.with(ctx).load(image).into(holder.movieimage);
        holder.movietitle.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsearch = new Intent(view.getContext(), Movie_details.class);
                intentsearch.putExtra("searchimage", image);
                intentsearch.putExtra("searchname", name);
                intentsearch.putExtra("searchyear", year);
                intentsearch.putExtra("searchid", id);
                ctx.startActivity(intentsearch);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ContentValues contentValues = new ContentValues();      //save shortcut by long press
                contentValues.put(com.example.moviedatabase.DBHelper.moviename, name);
                contentValues.put(com.example.moviedatabase.DBHelper.movieyear, year);
                contentValues.put(com.example.moviedatabase.DBHelper.movieid, id);
                contentValues.put(com.example.moviedatabase.DBHelper.movieimage, image);

                DBHelper DBHelper;
                SQLiteDatabase sqdb;
                DBHelper = new DBHelper(ctx);
                sqdb = DBHelper.getWritableDatabase();

                sqdb.insert(com.example.moviedatabase.DBHelper.tablename, null, contentValues);

                Intent intentlong = new Intent(view.getContext(), SaveMovies.class);   //to class save detail
                ctx.startActivity(intentlong);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie_items.size();
    }

    class Movieholder extends RecyclerView.ViewHolder
    {
        ImageView movieimage;
        TextView movietitle;

        public Movieholder(@NonNull View itemView) {
            super(itemView);
            movieimage = itemView.findViewById(R.id.imageview);
            movietitle = itemView.findViewById(R.id.titleview);
        }
    }
}
