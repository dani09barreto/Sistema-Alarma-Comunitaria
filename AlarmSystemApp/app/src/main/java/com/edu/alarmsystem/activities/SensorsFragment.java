package com.edu.alarmsystem.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;

import java.util.Objects;

public class SensorsFragment extends Fragment {

    private FragmentSensorsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSensorsBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String [] options = {"Lumínico","Movimiento","Temperatura","Presión"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, options);
        binding.spinnerSensors.setAdapter(adapter);

        binding.btnBack.setOnClickListener(v ->{
            getActivity().onBackPressed();
        });
    }

}