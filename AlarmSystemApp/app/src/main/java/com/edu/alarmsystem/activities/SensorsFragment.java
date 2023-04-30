package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.adapters.TypeSensorAdapter;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.models.GetRequest;
import com.edu.alarmsystem.models.TypeSensors;
import com.edu.alarmsystem.models.UserResponse;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorsFragment extends Fragment {

    private FragmentSensorsBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private String token;
    private String username;
    public UserResponse userResponse;
    private final GetRequest request = new GetRequest();
    private TypeSensorAdapter adapter;
    private boolean isFragmentAttached = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSensorsBinding.inflate(inflater);
        return binding.getRoot();

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        token = requireArguments().getString("token");
        username = requireArguments().getString("currentUsername");

        if (isFragmentAttached && getContext() != null) {
            try {
                request.sendRequest(token, String.format("/api/get/user=%s", username), getContext(), response -> {
                    Gson gson = new Gson();
                    userResponse = gson.fromJson(response, UserResponse.class);
                    if(isContextValid()) {
                        getInfoHouse(userResponse);
                    }
                });
            } catch (CertificateException | KeyStoreException | IOException |
                     NoSuchAlgorithmException |
                     KeyManagementException e) {
                throw new RuntimeException(e);
            }

            binding.addBtn.setOnClickListener(v-> {
                addSensor();
            });
            binding.btnBack.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, homeFragment).commit();
            });
        }

    }

    private void addSensor() {
        // TODO: Añadir sensor a una casa - En el backend hay que colocar un campo que sea cantidad de sensores
    }


    private void getTypeSensors() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {

        request.sendRequest(token,"/api/get/all/sensortypes",getContext(),response -> {
            Gson gson = new Gson();
            Type sensorListType = new TypeToken<ArrayList<TypeSensors>>(){}.getType();
            List<TypeSensors> sensorList = gson.fromJson(response, sensorListType);

            if(isContextValid()) {
                adapter = new TypeSensorAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sensorList);
                binding.spinnerSensors.setAdapter(adapter);
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void getInfoHouse(UserResponse userResponse) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        request.sendRequest(token,String.format("/api/get/client=%d/houses", userResponse.getId()),getContext(),response -> {
            if(!response.contains("direccion")) {
                binding.textError.setVisibility(View.VISIBLE);
            } else {
                List<Map<String, String>> houses = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                List<String> housesBar = new ArrayList<>();
                for (Map<String, String> cit : houses) {
                    housesBar.add(cit.get("direccion"));
                }
                if(isContextValid()) {
                    ArrayAdapter<String> adapterBar = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, housesBar);
                    binding.spinnerHouses.setAdapter(adapterBar);
                    binding.addBtn.setEnabled(true);
                    binding.cantSensor.setEnabled(true);
                    getTypeSensors();
                }
            }
        });
    }



        @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isFragmentAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isFragmentAttached = false;
    }

    private boolean isContextValid() {
        return isFragmentAttached && getContext() != null;
    }
}