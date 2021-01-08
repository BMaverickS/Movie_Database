package com.example.moviedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class save_movies_adapter extends RecyclerView.Adapter<save_movies_adapter.Movieholder> {

    Context ctx;
    Cursor csr;

    public save_movies_adapter(Context ctx, Cursor csr) {
        this.ctx = ctx;
        this.csr = csr;
    }

    @NonNull
    @Override
    public Movieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.search_movie_adapter, parent, false);

        Movieholder movieholder = new Movieholder(view);
        return movieholder;
    }

    @Override
    public void onBindViewHolder(@NonNull save_movies_adapter.Movieholder holder, int position) {
        if (!csr.moveToPosition(position))
        {
            return;
        }
        final String movimage = csr.getString(csr.getColumnIndex(DBHelper.movieimage));
        final String movname = csr.getString(csr.getColumnIndex(DBHelper.moviename));
        final String movyear = csr.getString(csr.getColumnIndex(DBHelper.movieyear));
        final String movid = csr.getString(csr.getColumnIndex(DBHelper.movieid));

        Glide.with(ctx).load(movimage).into(holder.imageview);// not done
        holder.titleview.setText(movname);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsearch = new Intent(view.getContext(), SaveDetails.class);   //to class save detail
                intentsearch.putExtra("saveimage", movimage);
                intentsearch.putExtra("savename", movname);
                intentsearch.putExtra("saveyear", movyear);
                intentsearch.putExtra("saveid", movid);
                ctx.startActivity(intentsearch);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {     //delete shortcut by long press
                DBHelper DBHelper;
                SQLiteDatabase sqdb;

                DBHelper = new DBHelper(ctx);
                sqdb = DBHelper.getWritableDatabase();

                if (DBHelper.deletemov(movid) > 0)
                {
                    Toast.makeText(ctx, "Success", Toast.LENGTH_LONG).show();
                    Intent deletead = new Intent(view.getContext(), SaveMovies.class);
                    ctx.startActivity(deletead);
                }

                Intent intentlong = new Intent(view.getContext(), SaveMovies.class);
                ctx.startActivity(intentlong);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return csr.getCount();
    }

    class Movieholder extends RecyclerView.ViewHolder
    {
        ImageView imageview;
        TextView titleview;

        public Movieholder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            titleview = itemView.findViewById(R.id.titleview);
        }
    }
}
