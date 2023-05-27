package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.adapters.TypeSensorAdapter;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.models.GetRequest;
import com.edu.alarmsystem.models.PostRequest;
import com.edu.alarmsystem.models.TypeSensors;
import com.edu.alarmsystem.models.UserResponse;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorsFragment extends Fragment {

    private FragmentSensorsBinding binding;
    private final HomeFragment homeFragment = new HomeFragment();
    private String token;
    public UserResponse userResponse;
    private final GetRequest getRequest = new GetRequest();
    private final PostRequest postRequest = new PostRequest();
    private TypeSensorAdapter adapter;
    private boolean isFragmentAttached = false;
    Map<String, Integer> direccionIdMap = new HashMap<>();
    Map<String, Long> sensorIdMap = new HashMap<>();
    private Long selectedSensorId;
    private Integer selectedHouseId;


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
        String username = requireArguments().getString("currentUsername");

        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("currentUsername", username);
        homeFragment.setArguments(args);

        if (isFragmentAttached && getContext() != null) {
            try {
                getRequest.sendRequest(token, String.format("/api/get/user=%s", username), getContext(), response -> {
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
                try {
                    addSensor();
                } catch (CertificateException | KeyStoreException | IOException |
                         KeyManagementException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            });
            binding.btnBack.setOnClickListener(v -> {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, homeFragment).commit();
            });
        }

        binding.spinnerHouses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDireccion = adapterView.getItemAtPosition(i).toString();
                selectedHouseId = direccionIdMap.get(selectedDireccion);
                binding.spinnerSensors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedSensorName = ((TypeSensors) adapterView.getItemAtPosition(i)).getNombre();
                        selectedSensorId = sensorIdMap.get(selectedSensorName);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void addSensor() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject bodyHouse = new JSONObject();

        List<Long> sensorList = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(binding.cantSensor.getEditText().getText().toString()); i++) {
            sensorList.add(selectedSensorId);
        }

        JSONArray sensorsId = new JSONArray(sensorList);

        try {
            bodyHouse.put("idCasa", selectedHouseId);
            bodyHouse.put("idTipoSensor", sensorsId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            postRequest.sendRequest(token, "/api/post/add/sensors", getContext(), bodyHouse, response -> {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, homeFragment).commit();
                new AlertsHelper().shortToast(getContext(), "Sensores Añadidos Exitosamente");
            });
    }


    private void getTypeSensors() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {

        getRequest.sendRequest(token,"/api/get/all/sensortypes",getContext(),response -> {
            Gson gson = new Gson();
            Type sensorListType = new TypeToken<ArrayList<TypeSensors>>(){}.getType();
            List<TypeSensors> sensorList = gson.fromJson(response, sensorListType);
            for (TypeSensors sensor : sensorList) {
                String sensorName = sensor.getNombre();
                Long sensorId = sensor.getId();
                sensorIdMap.put(sensorName, sensorId);
            }

            if(isContextValid()) {
                adapter = new TypeSensorAdapter(Objects.requireNonNull(getContext()), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sensorList);
                binding.spinnerSensors.setAdapter(adapter);
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void getInfoHouse(UserResponse userResponse) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        getRequest.sendRequest(token,String.format("/api/get/client=%d/houses", userResponse.getId()),getContext(),response -> {
            if(!response.contains("direccion")) {
                binding.textError.setVisibility(View.VISIBLE);
            } else {
                List<Map<String, String>> houses = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                List<String> housesBar = new ArrayList<>();
                for (Map<String, String> cit : houses) {
                    String direccion = cit.get("direccion");
                    housesBar.add(direccion);
                    int idCasa = Integer.parseInt(Objects.requireNonNull(cit.get("casaId")));
                    direccionIdMap.put(direccion, idCasa);
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