package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.edu.alarmsystem.databinding.ActivityAlarmBinding;
import com.edu.alarmsystem.models.CasaResponse;
import com.edu.alarmsystem.models.GetRequest;
import com.edu.alarmsystem.models.UserResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlarmActivity extends Activity {

    private ActivityAlarmBinding binding;
    private String token;
    private String username;
    private  UserResponse userResponse;
    private CasaResponse casaResponse;
    private Map<String, Integer> direccionIdMap = new HashMap<>();
    private final GetRequest request = new GetRequest();
    public String selectedDireccion;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        token = getIntent().getStringExtra("token");
        username = getIntent().getStringExtra("username");

        binding.spinnerHouses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDireccion = adapterView.getItemAtPosition(i).toString();

                try {
                    request.sendRequest(token, String.format("/api/get/house/id=%s",direccionIdMap.get(selectedDireccion)), getApplicationContext(), response -> {
                        casaResponse = new Gson().fromJson(response, CasaResponse.class);
                        Log.d("OJOOO",casaResponse.getOcupada().toString());
                        if (casaResponse.getOcupada()){
                            binding.textAlerts.setText("Desactivadas");
                            binding.textAlerts.setTextColor(Color.RED);
                        }else{
                            binding.textAlerts.setText("Activadas");
                            binding.textAlerts.setTextColor(Color.GREEN);
                        }

                    });
                } catch (CertificateException | KeyStoreException | IOException |
                         NoSuchAlgorithmException | KeyManagementException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            request.sendRequest(token, String.format("/api/get/user=%s", username), getApplicationContext(), response -> {
                Gson gson = new Gson();
                userResponse = gson.fromJson(response, UserResponse.class);
                    getInfoHouse(userResponse);
            });
        } catch (CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException |
                 KeyManagementException e) {
            throw new RuntimeException(e);
        }


        binding.btnBack.setOnClickListener(v->finish());
    }

    @SuppressLint("DefaultLocale")
    private void getInfoHouse(UserResponse userResponse) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        request.sendRequest(token,String.format("/api/get/client=%d/houses", userResponse.getId()),getApplicationContext(),response -> {
            if(!response.contains("direccion")) {
                binding.textError.setVisibility(View.VISIBLE);
            } else {
                List<Map<String, String>> houses = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                List<String> housesBar = new ArrayList<>();
                for (Map<String, String> cit : houses) {
                    String direccion = cit.get("direccion");
                    housesBar.add(direccion);
                    Integer idCasa = Integer.parseInt(Objects.requireNonNull(cit.get("casaId")));
                    direccionIdMap.put(direccion, idCasa);
                }
                    ArrayAdapter<String> adapterBar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, housesBar);
                    binding.spinnerHouses.setAdapter(adapterBar);
            }
        });
    }
}