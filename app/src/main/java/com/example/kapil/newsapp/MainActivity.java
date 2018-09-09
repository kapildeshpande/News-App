package com.example.kapil.newsapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList <JSONData> dataList;
    private RecyclerView recyclerView;
    private String initialUrl = "https://newsapi.org/v2/top-headlines?country=in&";
    private String API = "apiKey=5887e4b8b5394465b9130b70184c3b80";
    private String category = "";
    private DrawerLayout drawer;
    private LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (LinearLayout) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        initDrawerAndToolbar();
        initRecyclerView();

        callAPI();
    }

    private void initRecyclerView () {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void callAPI () {
        String url = initialUrl + category + API;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataList = new ArrayList<JSONData>();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        dataList.add(new JSONData("Check your internet connection",null,null));
                        RecyclerView.Adapter adapter = new MyAdapter(MainActivity.this,dataList);
                        recyclerView.setAdapter(adapter);
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void parseJSON(String response) {
        try {
            JSONObject objResponse = new JSONObject(response);
            JSONArray arrHeadlines = objResponse.getJSONArray("articles");
            dataList = new ArrayList<JSONData>();

            for (int i=0;i<arrHeadlines.length();i++) {
                JSONObject obj = arrHeadlines.getJSONObject(i);
                String title = obj.getString("title");
                String description = obj.getString("description");
                String imgUrl = obj.getString("urlToImage");
                dataList.add(new JSONData(title,description,imgUrl));
            }
            RecyclerView.Adapter adapter = new MyAdapter(MainActivity.this,dataList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDrawerAndToolbar () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.technology) {
            category = "category=technology&";
        } else if (id == R.id.sports) {
            category = "category=sports&";
        } else if (id == R.id.health) {
            category = "category=health&";
        } else if (id == R.id.business) {
            category = "category=business&";
        } else if (id == R.id.home) {
            category = "";
        }
        dataList.clear();
        drawer.closeDrawer(GravityCompat.START);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        callAPI();

        return true;
    }
}
