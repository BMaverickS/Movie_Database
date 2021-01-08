package com.example.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    search_movie_adapter search_movie_adapter;
    ArrayList<Movie_items> inputmovies = new ArrayList<>();
    String name;
    String year;
    String id;
    String type;
    String image;
    String srch = "avengers";       //default view

    RequestQueue requestqueue;
    Button savebtn;
    Button searchbtn;
    EditText searchbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchbox = findViewById(R.id.searchbox);
        savebtn = findViewById(R.id.savepage);
        searchbtn = findViewById(R.id.searchbutton);

        requestqueue = Volley.newRequestQueue(this);

        RecyclerView rview;
        rview = findViewById(R.id.recycler);
        rview.setLayoutManager(new GridLayoutManager(this, 2));
        search_movie_adapter = new search_movie_adapter(this, inputmovies);
        rview.setAdapter(search_movie_adapter);

        getdata();      //dummy data for test
    /*    inputmovies.add(new Movie_items("test", "https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-260nw-1029171697.jpg", "2020", "qwer10"));
        inputmovies.add(new Movie_items("test 1", "https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-260nw-1029171697.jpg", "2020", "qwer11"));
        inputmovies.add(new Movie_items("test 2", "https://p.bigstockphoto.com/GeFvQkBbSLaMdpKXF1Zv_bigstock-Aerial-View-Of-Blue-Lakes-And--227291596.jpg", "2020", "qwer12"));
        inputmovies.add(new Movie_items("test 3", "https://www.w3schools.com/w3css/img_lights.jpg", "2020", "qwer13"));
        inputmovies.add(new Movie_items("test 4", "https://www.w3schools.com/w3css/img_lights.jpg", "2020", "qwer14"));
        inputmovies.add(new Movie_items("test 5", "https://www.w3schools.com/w3css/img_lights.jpg", "2020", "qwer15"));
*/
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saved = new Intent(MainActivity.this, SaveMovies.class);     //go to saved page
                startActivity(saved);
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                srch = searchbox.getText().toString();      //search box input
                //Toast.makeText(getApplicationContext(), srch, Toast.LENGTH_LONG).show();
                getdata();
            }
        });
    }

    void getdata()
    {
        String url = "http://www.omdbapi.com/?apikey=29bef6e2&s=" + srch;//JSON service test is not working, app worked with data dummy test
        //Toast.makeText(getApplicationContext(), srch, Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray searcharray =  response.getJSONArray("Search");
                            for (int i = 0; i < searcharray.length(); i++)
                            {
                                JSONObject search = searcharray.getJSONObject(i);
                                name = search.getString("Title");
                                year = search.getString("Year");
                                id = search.getString("imdbID");
                                type = search.getString("Type");
                                image = search.getString("Poster");

                                inputmovies.add(new Movie_items(name, image, year, id));
                                //Toast.makeText(getApplicationContext(), "image url " + image + "\n\nname " + name + "\n\nyear " + year + "\n\nid " + id, Toast.LENGTH_LONG).show();
                                //search_movie_adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "image url " + image + "\n\nname " + name + "\n\nyear " + year + "\n\nid " + id, Toast.LENGTH_LONG).show();
            }
        });
        requestqueue.add(jsonObjectRequest);
    }
}