package com.example.sirovarweather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.model.DailyForecast;
import com.example.sirovarweather.utils.LocaleUtils;
import com.example.sirovarweather.utils.WeatherCodeMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DayViewHolder> {
    private final List<DailyForecast> items = new ArrayList<>();
    private final String temperatureUnit;
    public DailyForecastAdapter(String temperatureUnit) { this.temperatureUnit = temperatureUnit; }
    public void submit(List<DailyForecast> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_forecast, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DailyForecast item = items.get(position);
        Date date = parseDate(item.date);
        if (date != null) {
            Locale locale = LocaleUtils.getAppLocale(holder.itemView.getResources());
            holder.day.setText(new SimpleDateFormat("EEE", locale).format(date));
            holder.date.setText(new SimpleDateFormat("d MMM", locale).format(date));
        } else { holder.day.setText(item.date); holder.date.setText(""); }
        holder.description.setText(WeatherCodeMapper.descriptionResource(item.weatherCode));
        holder.icon.setImageResource(WeatherCodeMapper.iconResource(item.weatherCode, true));
        holder.temperatures.setText(holder.itemView.getContext().getString(R.string.min_max_temperature,
                Math.round(item.minimum), Math.round(item.maximum), temperatureUnit));
    }
    private Date parseDate(String text) {
        try { return new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(text); }
        catch (ParseException exception) { return null; }
    }
    @Override public int getItemCount() { return items.size(); }
    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView day,date,description,temperatures; ImageView icon;
        DayViewHolder(View view) { super(view); day=view.findViewById(R.id.day_name); date=view.findViewById(R.id.day_date); description=view.findViewById(R.id.day_description); temperatures=view.findViewById(R.id.day_temperatures); icon=view.findViewById(R.id.day_icon); }
    }
}
