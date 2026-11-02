package com.example.sirovarweather.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.repository.WeatherRepository;
import com.example.sirovarweather.ui.adapter.CitySearchAdapter;
import com.example.sirovarweather.widget.WeatherWidgetUpdater;

import java.util.List;

import timber.log.Timber;

public class SearchActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable pendingSearch;
    private TextView status;
    private CitySearchAdapter adapter;
    private WeatherRepository repository;
    private PreferencesManager preferences;
    private int searchVersion;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        repository=WeatherRepository.getInstance(); preferences=new PreferencesManager(this);
        status=findViewById(R.id.search_status);
        RecyclerView results=findViewById(R.id.city_results);
        results.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CitySearchAdapter(city -> {
            preferences.saveCity(city);
            Timber.i("City selected");
            WeatherWidgetUpdater.updateAll(getApplicationContext());
            setResult(RESULT_OK);
            finish();
        });
        results.setAdapter(adapter);
        ((com.google.android.material.textfield.TextInputEditText)findViewById(R.id.city_query)).addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence value,int start,int count,int after) { }
            @Override public void onTextChanged(CharSequence value,int start,int before,int count) {
                if (pendingSearch != null) handler.removeCallbacks(pendingSearch);
                String query=value.toString().trim();
                int version=++searchVersion;
                if (query.length()<2) { adapter.submit(java.util.Collections.emptyList()); status.setVisibility(View.GONE); return; }
                pendingSearch=()->search(query, version);
                handler.postDelayed(pendingSearch,350);
            }
            @Override public void afterTextChanged(Editable value) { }
        });
    }

    private void search(String query, int version) {
        status.setText(R.string.searching); status.setVisibility(View.VISIBLE);
        repository.searchCities(query, preferences.getLanguage(), new WeatherRepository.CitiesCallback() {
            @Override public void onSuccess(List<City> cities) {
                if (version != searchVersion || isFinishing()) return;
                adapter.submit(cities);
                if (cities.isEmpty()) {
                    status.setText(R.string.city_not_found);
                    status.setVisibility(View.VISIBLE);
                } else status.setVisibility(View.GONE);
            }
            @Override public void onError(Throwable error) {
                if (version != searchVersion || isFinishing()) return;
                Timber.w(error, "City search failed");
                adapter.submit(java.util.Collections.emptyList());
                status.setText(R.string.search_error); status.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override protected void onDestroy() { if (pendingSearch != null) handler.removeCallbacks(pendingSearch); super.onDestroy(); }
}
