package com.edu.alarmsystem.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentHousesBinding;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class HousesFragment extends Fragment {

    private FragmentHousesBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private String token;
    private String username;
    List<Map<String, String>> paises = new ArrayList<>();
    List<Map<String, String>> departamentos = new ArrayList<>();
    List<Map<String, String>> ciudades = new ArrayList<>();
    List<Map<String, String>> barrios = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHousesBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        token = getArguments().getString("token");
        username = requireArguments().getString("currentUsername");


        try {
            getCountries(token, response -> {
                paises = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                List<String> nombresPaises = new ArrayList<>();
                for (Map<String, String> pais : paises) {
                    nombresPaises.add(pais.get("nombre"));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresPaises);
                binding.houseCountry.setAdapter(adapter);
            });
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }

        binding.houseCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCountry = parent.getItemAtPosition(position).toString();
                for (Map<String, String> pais : paises) {
                    if (pais.get("nombre").equals(selectedCountry)) {
                        try {
                            getDepartment(token, pais.get("id"), response -> {
                                departamentos = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                                List<String> nombresDep = new ArrayList<>();
                                for (Map<String, String> dep : departamentos) {
                                    nombresDep.add(dep.get("nombre"));
                                }

                                ArrayAdapter<String> adapterDep = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresDep);
                                binding.houseDepartment.setAdapter(adapterDep);
                            });
                        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.houseDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDep = parent.getItemAtPosition(position).toString();
                for (Map<String, String> dep : departamentos) {
                    if (dep.get("nombre").equals(selectedDep)) {
                        try {
                            getCity(token, dep.get("id"), response -> {
                                ciudades = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                                List<String> nombresDep = new ArrayList<>();
                                for (Map<String, String> depart : ciudades) {
                                    nombresDep.add(depart.get("nombre"));
                                }

                                ArrayAdapter<String> adapterDep = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresDep);
                                binding.houseCity.setAdapter(adapterDep);
                            });
                        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.houseCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                for (Map<String, String> city : ciudades) {
                    if (city.get("nombre").equals(selectedCity)) {
                        try {
                            getDistrict(token, city.get("id"), response -> {
                                barrios = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>(){}.getType());
                                List<String> nombresBar = new ArrayList<>();
                                for (Map<String, String> cit : barrios) {
                                    nombresBar.add(cit.get("nombre"));
                                }

                                ArrayAdapter<String> adapterBar = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresBar);
                                binding.houseDistrict.setAdapter(adapterBar);
                            });
                        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.houseDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = parent.getItemAtPosition(position).toString();
                for (Map<String, String> distr : barrios) {
                    if (distr.get("nombre").equals(selectedDistrict)) {
                        try {
                            getIdentificationClient(token, username, response -> {
                               binding.addHouseBtn.setOnClickListener(v -> {
                                   try {
                                       if(binding.houseAddress.getEditText().getText().toString().isEmpty()) {
                                           new AlertsHelper().shortToast(getContext(), "Debes llenar todos los campos");
                                       } else {
                                           addHouse(new JSONObject(response).getInt("identificacion"), distr.get("id"));
                                       }
                                   } catch (CertificateException e) {
                                       e.printStackTrace();
                                   } catch (KeyStoreException e) {
                                       e.printStackTrace();
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   } catch (NoSuchAlgorithmException e) {
                                       e.printStackTrace();
                                   } catch (KeyManagementException e) {
                                       e.printStackTrace();
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               });
                            });
                        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void getCountries(String token, VolleyCallback callback) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        SSLSocketFactory sslSocketFactory = settingsSecurity();

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///
        String url = "https://192.168.80.16:8443/api/get/all/countries";
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            try {
                callback.getInfo(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext(), hurlStack);
        requestQueue.add(getRequest);

    }

    private void getDepartment(String token, String id, VolleyCallback callback) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLSocketFactory sslSocketFactory = settingsSecurity();


        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///

        String url = "https://192.168.80.16:8443/api/get/country="+id+"/departments";

        JsonArrayRequest depRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            try {
                callback.getInfo(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext(), hurlStack);
        requestQueue.add(depRequest);
    }

    private void getCity(String token, String id, VolleyCallback callback) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLSocketFactory sslSocketFactory = settingsSecurity();


        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///

        String url = "https://192.168.80.16:8443/api/get/department="+id+"/cities";

        JsonArrayRequest depRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            try {
                callback.getInfo(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext(), hurlStack);
        requestQueue.add(depRequest);
    }

    private void getDistrict(String token, String id, VolleyCallback callback) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLSocketFactory sslSocketFactory = settingsSecurity();


        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///

        String url = "https://192.168.80.16:8443/api/get/city="+id+"/neighborhoods";

        JsonArrayRequest depRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            try {
                System.out.println("HOLAAAAAA"+response.toString());
                callback.getInfo(response.toString());

            } catch (JSONException | CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext(), hurlStack);
        requestQueue.add(depRequest);
    }


    private void getIdentificationClient(String token, String username, VolleyCallback callback) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLSocketFactory sslSocketFactory = settingsSecurity();


        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        //Poner dirección IP del Eendpoint donde se aloja el backend - Quitar localhost///

        String url = String.format("https://192.168.80.16:8443/api/get/user=%s", username);

        JsonObjectRequest depRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                callback.getInfo(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(),message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje de error
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext(), hurlStack);
        requestQueue.add(depRequest);
    }

    private SSLSocketFactory settingsSecurity() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
        InputStream caInput = getResources().openRawResource(R.raw.server);
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




    private boolean getField(Spinner field) {
        boolean isValid = true;
        try {
            if (!field.isSelected()) throw new Exception();
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }


    public interface VolleyCallback {
        void getInfo(String response) throws JSONException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException;
    }




    private void addHouse(int identificacion, String idbarrio) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {


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
        String url = "https://192.168.80.16:8443/api/post/add/house";
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("identificacionCliente", identificacion);
            jsonBody.put("barrioId", idbarrio);
            jsonBody.put("direccion", binding.houseAddress.getEditText().getText());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, response -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();

            new AlertsHelper().shortToast(getContext(),"Casa Añadida Exitosamente");

        },error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = new JSONObject(new String(error.networkResponse.data)).getString("message");
                    new AlertsHelper().shortToast(getContext(), message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                new AlertsHelper().shortToast(getContext(), error.toString());
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
        RequestQueue requestQueue  = Volley.newRequestQueue(getContext(),hurlStack);
        requestQueue.add(postRequest);

    }
}