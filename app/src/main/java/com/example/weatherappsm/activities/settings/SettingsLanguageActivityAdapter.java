package com.example.weatherappsm.activities.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.R;

public class SettingsLanguageActivityAdapter extends RecyclerView.Adapter<SettingsLanguageActivityAdapter.MyViewHolder> {

    String languageNames[];
    int languageFlags[];
    private OnItemClickListener mListener;

    public SettingsLanguageActivityAdapter(String languageNames[], int languageFlags[]) {
        this.languageNames = languageNames;
        this.languageFlags = languageFlags;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public SettingsLanguageActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.settings_language_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsLanguageActivityAdapter.MyViewHolder holder, int position) {
        holder.languageName.setText(languageNames[position]);
        holder.languageFlag.setImageResource(languageFlags[position]);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView languageName;
        ImageView languageFlag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            languageName = itemView.findViewById(R.id.languageName);
            languageFlag = itemView.findViewById(R.id.languageFlag);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
