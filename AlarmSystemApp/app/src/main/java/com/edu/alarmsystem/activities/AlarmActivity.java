package com.edu.alarmsystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityAlarmBinding;
import com.edu.alarmsystem.databinding.ActivityLoginBinding;
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
import java.util.List;
import java.util.Map;

public class AlarmActivity extends Activity {

    private ActivityAlarmBinding binding;
    private String token;
    private String username;
    public UserResponse userResponse;
    private final GetRequest request = new GetRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        token = getIntent().getStringExtra("token");
        username = getIntent().getStringExtra("username");

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

    private void getInfoHouse(UserResponse userResponse) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        request.sendRequest(token,String.format("/api/get/client=%d/houses", userResponse.getId()),getApplicationContext(),response -> {
            if(!response.contains("direccion")) {
                binding.textError.setVisibility(View.VISIBLE);
            } else {
                List<Map<String, String>> houses = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                List<String> housesBar = new ArrayList<>();
                for (Map<String, String> cit : houses) {
                    housesBar.add(cit.get("direccion"));
                }
                    ArrayAdapter<String> adapterBar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, housesBar);
                    binding.spinnerHouses.setAdapter(adapterBar);
            }
        });
    }
}