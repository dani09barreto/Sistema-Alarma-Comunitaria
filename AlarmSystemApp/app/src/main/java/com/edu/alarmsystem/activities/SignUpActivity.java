package com.edu.alarmsystem.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.activities.Activity;
import com.edu.alarmsystem.databinding.ActivityLoginBinding;
import com.edu.alarmsystem.databinding.ActivitySignUpBinding;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class SignUpActivity extends Activity {

    private ActivitySignUpBinding binding;
    private static final String IPSERVER = "https://10.0.1.105:8443";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButton.setOnClickListener(v->startActivity(new Intent(this, LoginActivity.class)));
        binding.buttonSignUp.setOnClickListener(v-> {
            validateAndRegisterUser();
        });
    }

    private void validateAndRegisterUser() {
        boolean isValid = true;
        String email;

        isValid = isValid && getField(binding.nameUser);
        isValid = isValid && getField(binding.lastNameUser);
        isValid = isValid && getField(binding.idUser);
        isValid = isValid && getField(binding.phoneUser);
        isValid = isValid && getField(binding.userName);
        isValid = isValid && getField(binding.password);



        if (isValid) {
            try {
                registerUser();
            } catch (CertificateException | KeyStoreException | KeyManagementException | IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            alertsHelper.shortSimpleSnackbar(binding.getRoot(), "Por favor complete todos los campos correctamente.");
        }

        try {
            email = Objects.requireNonNull(binding.emailUser.getEditText()).getText().toString();
            if (!isValidEmail(email))  throw new Exception();
        } catch (Exception e) {
            alertsHelper.shortSimpleSnackbar(binding.getRoot(), "Por favor ingrese una dirección de correo electrónico válida.");
        }


    }

    private boolean getField(TextInputLayout field) {
        String text = null;
        boolean isValid = true;
        try {
            text = Objects.requireNonNull(field.getEditText()).getText().toString();
            if (text.isEmpty()) throw new Exception();
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void registerUser() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {



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

        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///
        String url = IPSERVER + "/api/auth/register";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", binding.userName.getEditText().getText());
            jsonBody.put("password", binding.password.getEditText().getText().toString());
            jsonBody.put("name", binding.nameUser.getEditText().getText());
            jsonBody.put("lastName", binding.lastNameUser.getEditText().getText());
            jsonBody.put("email", binding.emailUser.getEditText().getText());
            jsonBody.put("identification", binding.idUser.getEditText().getText());
            jsonBody.put("phone", binding.phoneUser.getEditText().getText());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, response -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            try {
                new AlertsHelper().shortToast(getApplicationContext(),response.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
        RequestQueue requestQueue  = Volley.newRequestQueue(this,hurlStack);
        requestQueue.add(postRequest);

    }

}