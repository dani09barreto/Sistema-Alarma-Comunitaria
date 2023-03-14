package com.edu.alarmsystem.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edu.alarmsystem.databinding.TypeSensorAdapterBinding;
import com.edu.alarmsystem.models.TypeSensors;

import java.util.List;

public class TypeSensorAdapter extends ArrayAdapter <TypeSensors> {


    public TypeSensorAdapter(@NonNull Context context, int resource, @NonNull List<TypeSensors> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TypeSensors sensor = getItem(position);
        TypeSensorAdapterBinding binding;
        if (convertView == null) {
            binding = TypeSensorAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            binding = TypeSensorAdapterBinding.bind(convertView);
        }
        binding.text1.setText(sensor.getNombre());
        return binding.getRoot();
    }
}
