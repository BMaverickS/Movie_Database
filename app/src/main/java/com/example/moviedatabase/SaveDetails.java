package com.example.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SaveDetails extends AppCompatActivity {

    ImageView moviedetail;
    TextView titledetail;
    TextView yeardetail;
    TextView iddetail;
    Button deletedetail;

    String namesavedetail;
    String yearsavedetail;
    String idsavedetail;
    String imagesavedetail;

    DBHelper DBHelper;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_details);

        moviedetail = findViewById(R.id.moviedetail);
        titledetail = findViewById(R.id.titledetail);
        yeardetail = findViewById(R.id.prodyeardetail);
        iddetail = findViewById(R.id.imdbdetail);
        deletedetail = findViewById(R.id.deletedetail);

        getSupportActionBar().setTitle("Movie Details");        //saved movie detail
        fetchsaved();
        DBHelper = new DBHelper(this);
        sqdb = DBHelper.getWritableDatabase();

        deletedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //delete button
                //Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                if (DBHelper.deletemov(idsavedetail) > 0)
                {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    Intent delete = new Intent(SaveDetails.this, SaveMovies.class);
                    startActivity(delete);
                }
            }
        });
    }

    void fetchsaved ()
    {
        Intent detail = getIntent();

        namesavedetail = detail.getStringExtra("savename");     //get data
        yearsavedetail = detail.getStringExtra("saveyear");
        idsavedetail = detail.getStringExtra("saveid");
        imagesavedetail = detail.getStringExtra("saveimage");

        Glide.with(this).load(imagesavedetail).into(moviedetail);       //show data
        titledetail.setText(namesavedetail);
        yeardetail.setText(yearsavedetail);
        iddetail.setText(idsavedetail);
    }
}