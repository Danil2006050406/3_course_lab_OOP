package com.example.sirovarweather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.model.HourlyForecast;
import com.example.sirovarweather.utils.WeatherCodeMapper;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourViewHolder> {
    private final List<HourlyForecast> items = new ArrayList<>();
    private final String temperatureUnit;
    public HourlyForecastAdapter(String temperatureUnit) { this.temperatureUnit = temperatureUnit; }
    public void submit(List<HourlyForecast> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return new HourViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_forecast, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        HourlyForecast item = items.get(position);
        holder.time.setText(item.time);
        holder.temperature.setText(holder.itemView.getContext().getString(R.string.temperature_value, Math.round(item.temperature), temperatureUnit));
        holder.icon.setImageResource(WeatherCodeMapper.iconResource(item.weatherCode, item.isDay));
        holder.precipitation.setText(item.precipitationProbability == null ? "—"
                : holder.itemView.getContext().getString(R.string.precipitation_percent, item.precipitationProbability));
    }
    @Override public int getItemCount() { return items.size(); }
    static class HourViewHolder extends RecyclerView.ViewHolder {
        TextView time, temperature, precipitation; ImageView icon;
        HourViewHolder(View view) { super(view); time=view.findViewById(R.id.hour_time); temperature=view.findViewById(R.id.hour_temperature); icon=view.findViewById(R.id.hour_icon); precipitation=view.findViewById(R.id.hour_precipitation); }
    }
}
