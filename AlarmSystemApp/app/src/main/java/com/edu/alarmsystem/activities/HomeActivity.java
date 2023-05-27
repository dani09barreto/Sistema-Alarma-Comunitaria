package com.edu.alarmsystem.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityHomeBinding;
import com.edu.alarmsystem.databinding.ActivityMainBinding;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.models.ConfigManager;
import com.edu.alarmsystem.utils.AlertsHelper;

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

public class HomeActivity extends Activity {

    private ActivityHomeBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SensorsFragment sensorsFragment = new SensorsFragment();
    HousesFragment housesFragment = new HousesFragment();
    String token;
    private String currentUsername;
    private static final String IPSERVER = ConfigManager.getServerIP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        currentUsername = bundle.getString("username");
        token = bundle.getString("token");
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("currentUsername", currentUsername);

        housesFragment.setArguments(args);
        homeFragment.setArguments(args);

        try {
            getCountries();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();
                    return true;
                case R.id.sensors:
                    sensorsFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),sensorsFragment).commit();
                    return true;
                case R.id.houses:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),housesFragment).commit();
                    return true;
            }
            return false;
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getCountries() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        InputStream caInput = getResources().openRawResource(R.raw.server);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(caInput, "Alarma123.".toCharArray());

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
        String url = IPSERVER + "/api/get/all/countries";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            /*countries = String.valueOf(response);
            System.out.println("PAISSEEEEES" +countries);*/
            new AlertsHelper().shortToast(this,response.toString());

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getApplicationContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(this, hurlStack);
        requestQueue.add(getRequest);


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cerrar Sesión");
        builder.setMessage(R.string.textSignOut);

        builder.setPositiveButton("SI", (dialog, which) -> startActivity(new Intent(getApplicationContext(),LoginActivity.class)));
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

        builder.create();
        builder.show();
    }

}