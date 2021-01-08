package com.example.moviedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SaveMovies extends AppCompatActivity {

    save_movies_adapter save_movies_adapter;
    DBHelper DBHelper;
    SQLiteDatabase sqdb;

    Button searchsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_movies);

        DBHelper = new DBHelper(this);
        sqdb = DBHelper.getWritableDatabase();

        searchsv = findViewById(R.id.searchpage);

        Cursor csr = sqdb.rawQuery("SELECT COUNT (*) FROM moviedata;", null);        //check empty table
        csr.moveToFirst();
        int i = csr.getInt(0);
        //Toast.makeText(getApplicationContext(), i, Toast.LENGTH_LONG).show();
        if (i != 0)
        {
            RecyclerView rview;
            rview = findViewById(R.id.saverecycler);
            rview.setLayoutManager(new GridLayoutManager(this, 2));
            save_movies_adapter = new save_movies_adapter(this, getItems());
            rview.setAdapter(save_movies_adapter);
        }

        searchsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saved = new Intent(SaveMovies.this, MainActivity.class);     //back to main
                startActivity(saved);
            }
        });
    }

    private Cursor getItems ()      //get data
    {
        return sqdb.query(com.example.moviedatabase.DBHelper.tablename, null, null, null, null, null, null);
    }
}