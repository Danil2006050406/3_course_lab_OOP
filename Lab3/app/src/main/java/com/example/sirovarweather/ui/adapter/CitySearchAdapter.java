package com.example.sirovarweather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.model.City;

import java.util.ArrayList;
import java.util.List;

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.CityViewHolder> {
    public interface OnCityClickListener { void onCityClick(City city); }
    private final List<City> cities = new ArrayList<>();
    private final OnCityClickListener listener;
    public CitySearchAdapter(OnCityClickListener listener) { this.listener = listener; }
    public void submit(List<City> values) { cities.clear(); cities.addAll(values); notifyDataSetChanged(); }
    @NonNull @Override public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = cities.get(position);
        holder.name.setText(city.name);
        String region = city.admin1 == null ? "" : city.admin1;
        String country = city.country == null ? "" : city.country;
        holder.detail.setText(region.isEmpty() ? country : country.isEmpty() ? region : region + ", " + country);
        holder.itemView.setOnClickListener(view -> listener.onCityClick(city));
    }
    @Override public int getItemCount() { return cities.size(); }
    static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView name,detail;
        CityViewHolder(View view) { super(view); name=view.findViewById(R.id.city_name); detail=view.findViewById(R.id.city_detail); }
    }
}
