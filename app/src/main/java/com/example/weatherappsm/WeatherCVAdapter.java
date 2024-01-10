package com.example.weatherappsm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.Settings;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherCVAdapter extends RecyclerView.Adapter<WeatherCVAdapter.ViewHolder>{
    private Context context;
    private ArrayList<WeatherCV> weatherCV;

    private Settings settings = UserManager.getInstance().getCurrentUser().getSettings();

    public WeatherCVAdapter(Context context, ArrayList<WeatherCV> weatherCV) {
        this.context = context;
        this.weatherCV = weatherCV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherCV weatherCV = this.weatherCV.get(position);
        String tempUnit = settings.getTemperatureUnit().getUnit();
        String windUnit = settings.getWindSpeedUnit().getUnit();

        holder.temp.setText(weatherCV.getTemp()+tempUnit);
        holder.windSpeed.setText(weatherCV.getWindSpeed()+windUnit);
        Picasso.get().load("https:".concat(weatherCV.getIcon())).into(holder.icon);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm aa");
        try{
            Date d = input.parse(weatherCV.getTime());
            holder.time.setText(output.format(d));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherCV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView time, temp, windSpeed;
        private ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.idTVtime);
            temp = itemView.findViewById(R.id.idTVtemperature);
            windSpeed = itemView.findViewById(R.id.idTVWindSpeed);
            icon = itemView.findViewById(R.id.idIVweatherIcon);
        }
    }
}
