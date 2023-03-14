package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.models.CasaResponse;
import com.edu.alarmsystem.models.UserResponse;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.Gson;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class SensorsFragment extends Fragment {

    private FragmentSensorsBinding binding;
    private String token;
    private String username;
    private AlertsHelper alertsHelper = new AlertsHelper();
    public UserResponse userResponse;
    public CasaResponse casaResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSensorsBinding.inflate(inflater);
        return binding.getRoot();

    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        token = requireArguments().getString("token");
        username = requireArguments().getString("currentUsername");

        InputStream caInput = getResources().openRawResource(R.raw.server);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(caInput, "Alarma123.".toCharArray());

        // crea un administrador de confianza de SSL personalizado que confía en el certificado
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        String url = String.format("https://192.168.1.105:8443/api/get/user=%s", username);

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Gson gson = new Gson();
                    userResponse = gson.fromJson(response, UserResponse.class);
                    getInfoHouse(userResponse);
                },
                error -> {
                    alertsHelper.shortToast(getContext(), error.getMessage());
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext(), hurlStack);
        requestQueue.add(getRequest);
        binding.btnBack.setOnClickListener(v ->{
            getActivity().onBackPressed();
        });
    }

    @SneakyThrows
    private void getInfoHouse(UserResponse userResponse) {
        InputStream caInput = getResources().openRawResource(R.raw.server);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(caInput, "Alarma123.".toCharArray());

        // crea un administrador de confianza de SSL personalizado que confía en el certificado
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        @SuppressLint("DefaultLocale")
        String urlget = String.format("https://localhost:8443/api/get/user=%d/house", userResponse.getId());
        StringRequest getRequest = new StringRequest(Request.Method.GET, urlget,
                response2 -> {
                    Gson gson2 = new Gson();
                    casaResponse = gson2.fromJson(response2, CasaResponse.class);
                    binding.textaddresshouse.setText(String.format("Casa: %s", casaResponse.getDireccion()));
                    binding.myButton.setEnabled(true);
                    binding.spinnerSensors.setEnabled(true);
                    binding.cantSensor.setEnabled(true);
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 204) {
                        binding.textError.setText("No tienes una casa asignada, Primero debes crear una!");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext(), hurlStack);
        requestQueue.add(getRequest);
    }

}