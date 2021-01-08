package com.example.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Movie_details extends AppCompatActivity {

    ImageView moviedetail;
    TextView titledetail;
    TextView yeardetail;
    TextView iddetail;
    Button savedetail;

    String namesavedetail;
    String yearsavedetail;
    String idsavedetail;
    String imagesavedetail;

    DBHelper DBHelper;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        moviedetail = findViewById(R.id.moviedetail);
        titledetail = findViewById(R.id.titledetail);
        yeardetail = findViewById(R.id.prodyeardetail);
        iddetail = findViewById(R.id.imdbdetail);
        savedetail = findViewById(R.id.savedetail);

        getSupportActionBar().setTitle("Movie Details");        //search movie detail
        fetch();
        DBHelper = new DBHelper(this);
        sqdb = DBHelper.getWritableDatabase();

        Boolean svchk = DBHelper.checksaved(idsavedetail);      //change button if movie is saved
        if (svchk == true)
        {
            savedetail.setText("Delete");
        }

        savedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedetail.getText() == "Delete")       //delete
                {
                    if (DBHelper.deletemov(idsavedetail) > 0)
                    {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        Intent delete = new Intent(Movie_details.this, SaveMovies.class);
                        startActivity(delete);
                    }
                }
                else        //save
                {
                    ContentValues contentValues = new ContentValues();      //save booking data
                    contentValues.put(com.example.moviedatabase.DBHelper.moviename, namesavedetail);
                    contentValues.put(com.example.moviedatabase.DBHelper.movieyear, yearsavedetail);
                    contentValues.put(com.example.moviedatabase.DBHelper.movieid, idsavedetail);
                    contentValues.put(com.example.moviedatabase.DBHelper.movieimage, imagesavedetail);

                    sqdb.insert(com.example.moviedatabase.DBHelper.tablename, null, contentValues);
                    //intent ke save
                    Intent save = new Intent(Movie_details.this, SaveMovies.class);
                    startActivity(save);
                    //finish();
                }
            }
        });
    }

    void fetch()
    {
        Intent detail = getIntent();

        namesavedetail = detail.getStringExtra("searchname");       //get data
        yearsavedetail = detail.getStringExtra("searchyear");
        idsavedetail = detail.getStringExtra("searchid");
        imagesavedetail = detail.getStringExtra("searchimage");

        Glide.with(this).load(imagesavedetail).into(moviedetail);       //show data
        titledetail.setText(namesavedetail);
        yeardetail.setText(yearsavedetail);
        iddetail.setText(idsavedetail);
    }
}