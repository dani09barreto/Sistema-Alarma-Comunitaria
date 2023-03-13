package com.edu.alarmsystem.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityLoginBinding;
import com.edu.alarmsystem.utils.AlertsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signUpButton.setOnClickListener(v->startActivity(new Intent(this, SignUpActivity.class)));
        binding.buttonLogin.setOnClickListener(v->{
            try {
                loginUser();
            } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        });
    }

    private void loginUser() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
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

        //Poner dirección IP del Endpoint donde se aloja el backend - Quitar localhost///
        String url = "https://192.168.80.16:8443/api/auth/login";

        if(binding.user.getEditText().getText().toString().isEmpty() && binding.user.getEditText().getText().toString().isEmpty()){
            alertsHelper.shortToast(getApplicationContext(),"Ingresa todos los datos");
        } else {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, response -> {
                startActivity(new Intent(this, HomeActivity.class));
                alertsHelper.shortToast(getApplicationContext(), response.toString());
            }, error -> {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                        new AlertsHelper().shortToast(getApplicationContext(), message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertsHelper().shortToast(getApplicationContext(), error.toString());
                }
            }) {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String encodedCredentials = encodeCredentialsToBase64(binding.user.getEditText().getText().toString(),
                            binding.password.getEditText().getText().toString());
                    headers.put("Authorization", "Basic " + encodedCredentials);
                    return headers;
                }
            };


            HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
            RequestQueue requestQueue = Volley.newRequestQueue(this, hurlStack);
            requestQueue.add(postRequest);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeCredentialsToBase64(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }



}