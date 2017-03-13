package com.havrylyuk.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.havrylyuk.retrofit.adapter.EntityRecyclerViewAdapter;
import com.havrylyuk.retrofit.model.Entities;
import com.havrylyuk.retrofit.model.Entity;
import com.havrylyuk.retrofit.service.EntityApiClient;
import com.havrylyuk.retrofit.service.EntityApiService;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EntityApiService service;
    private EntityRecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        service = EntityApiClient.getClient().create(EntityApiService.class);
        setupRecyclerView();
        loadDataFromNetwork();

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        adapter = new EntityRecyclerViewAdapter(new EntityRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Entity country) {
                Toast.makeText(MainActivity.this, "country " + country.getContinentName(), Toast.LENGTH_SHORT).show();
            }
        }, new ArrayList<Entity>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataFromNetwork() {
        setProgressBarVisible(true);
        Call<Entities> responseCall =
                service.getCountries(Locale.getDefault().getLanguage(),BuildConfig.GEONAME_API_KEY,"FULL");
        responseCall.enqueue(new Callback<Entities>() {
            @Override
            public void onResponse(Call<Entities> call, Response<Entities> response) {
                if (!response.body().getCountries().isEmpty()) {
                    if (adapter != null) {
                        adapter.setCountryList(response.body().getCountries());
                    }
                }
                setProgressBarVisible(false);
            }
            @Override
            public void onFailure(Call<Entities> call, Throwable t) {
                Log.e(LOG_TAG,"error:"+t.getMessage());
                setProgressBarVisible(false);
            }
        });
    }

    private void setProgressBarVisible(boolean visible) {
        if (progressBar == null) {
            return;
        }
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
