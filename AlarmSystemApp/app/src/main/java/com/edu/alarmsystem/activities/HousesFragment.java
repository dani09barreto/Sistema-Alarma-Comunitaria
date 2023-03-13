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
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentHousesBinding;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.utils.MultiSelectionSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class HousesFragment extends Fragment implements MultiSelectionSpinner.MultiSpinnerListener{


    private FragmentHousesBinding binding;

    MultiSelectionSpinner spinner;
    List<String> items = Arrays.asList("Sensor Lumínico", "Sensor de Presión","Sensor de Movimiento");
    boolean[] selectedItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHousesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = binding.spinner;
        spinner.setItems(items, getString(R.string.select_items), this);
        selectedItems = new boolean[items.size()];
        Arrays.fill(selectedItems, false);

        binding.btnBack.setOnClickListener(v ->{
            startActivity(new Intent(getContext(),HomeActivity.class));
        });

    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        selectedItems = selected;
        String selectedItemsCount = String.valueOf(getSelectedItems().size()) + " elementos seleccionados";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList(selectedItemsCount));
        spinner.setAdapter(adapter);
    }

    private List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (this.selectedItems[i]) {
                selectedItems.add(items.get(i));
            }
        }
        return selectedItems;
    }


}