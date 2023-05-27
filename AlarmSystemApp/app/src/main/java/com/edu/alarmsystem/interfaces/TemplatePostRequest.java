package com.edu.alarmsystem.interfaces;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.models.ConfigManager;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public abstract class TemplatePostRequest {
    private static final String IPSERVER = ConfigManager.getServerIP();

    public void sendRequest(String token, String endpoint, Context context, JSONObject body,VolleyCallback callback) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLSocketFactory sslSocketFactory = settingsSecurity(context);

        HostnameVerifier allHostsValid = (hostname, session) -> true;

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        String url = IPSERVER + endpoint;

        JsonObjectRequest postRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, body, response -> {
            try {
                callback.getInfo(response.toString());
            } catch (JSONException | CertificateException | KeyStoreException | IOException |
                     NoSuchAlgorithmException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
        },error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    Log.d("Error",new JSONObject(new String(error.networkResponse.data)).getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
        RequestQueue requestQueue  = Volley.newRequestQueue(context,hurlStack);
        requestQueue.add(postRequest);


    }

    protected SSLSocketFactory settingsSecurity(Context context) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
        InputStream caInput = context.getResources().openRawResource(R.raw.server);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(caInput, "Alarma123.".toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
    }

}
