package com.edu.alarmsystem.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentHousesBinding;
import com.edu.alarmsystem.models.GetRequest;
import com.edu.alarmsystem.models.PostRequest;
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
import java.util.Objects;

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
    private List<Map<String, String>> paises = new ArrayList<>();
    private List<Map<String, String>> departamentos = new ArrayList<>();
    private List<Map<String, String>> ciudades = new ArrayList<>();
    private List<Map<String, String>> barrios = new ArrayList<>();

    private GetRequest request = new GetRequest();
    private PostRequest postRequest = new PostRequest();
    private boolean isFragmentAttached = false;
    private static final String IPSERVER = "https://10.0.1.105:8443";


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
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
        });

        token = getArguments().getString("token");
        username = requireArguments().getString("currentUsername");

        if (isFragmentAttached && getContext() != null) {

            try {
                request.sendRequest(token, "/api/get/all/countries", getContext(), response -> {
                    paises = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>() {
                    }.getType());
                    List<String> nombresPaises = new ArrayList<>();
                    for (Map<String, String> pais : paises) {
                        nombresPaises.add(pais.get("nombre"));
                    }
                    if(isContextValid()) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresPaises);
                        binding.houseCountry.setAdapter(adapter);
                    }
                });

            } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException |
                     CertificateException | IOException e) {
                e.printStackTrace();
            }

            binding.houseCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedCountry = parent.getItemAtPosition(position).toString();
                    for (Map<String, String> pais : paises) {
                        if (pais.get("nombre").equals(selectedCountry)) {
                            try {
                                request.sendRequest(token, "/api/get/country=" + pais.get("id") + "/departments", getContext(), response -> {
                                    departamentos = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>() {
                                    }.getType());
                                    List<String> nombresDep = new ArrayList<>();
                                    for (Map<String, String> dep : departamentos) {
                                        nombresDep.add(dep.get("nombre"));
                                    }
                                    if(isContextValid()) {
                                        ArrayAdapter<String> adapterDep = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresDep);
                                        binding.houseDepartment.setAdapter(adapterDep);
                                    }
                                });
                            } catch (KeyStoreException | CertificateException | IOException |
                                     NoSuchAlgorithmException | KeyManagementException e) {
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
                                GetRequest cityRequest = new GetRequest();
                                cityRequest.sendRequest(token, "/api/get/department=" + dep.get("id") + "/cities", getContext(), response -> {
                                    ciudades = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>() {
                                    }.getType());
                                    List<String> nombresDep = new ArrayList<>();
                                    for (Map<String, String> depart : ciudades) {
                                        nombresDep.add(depart.get("nombre"));
                                    }
                                    if(isContextValid()) {
                                        ArrayAdapter<String> adapterDep = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresDep);
                                        binding.houseCity.setAdapter(adapterDep);
                                    }
                                });
                            } catch (KeyStoreException | CertificateException | IOException |
                                     NoSuchAlgorithmException | KeyManagementException e) {
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
                                request.sendRequest(token, "/api/get/city=" + city.get("id") + "/neighborhoods", getContext(), response -> {
                                    barrios = new Gson().fromJson(response, new TypeToken<List<Map<String, String>>>() {
                                    }.getType());
                                    List<String> nombresBar = new ArrayList<>();
                                    for (Map<String, String> cit : barrios) {
                                        nombresBar.add(cit.get("nombre"));
                                    }
                                    if(isContextValid()) {
                                        ArrayAdapter<String> adapterBar = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresBar);
                                        binding.houseDistrict.setAdapter(adapterBar);
                                    }
                                });
                            } catch (KeyStoreException | CertificateException | IOException |
                                     NoSuchAlgorithmException | KeyManagementException e) {
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
                                request.sendRequest(token, String.format("/api/get/user=%s", username), getContext(), response -> {
                                    binding.addHouseBtn.setOnClickListener(v -> {
                                        try {
                                            if (Objects.requireNonNull(binding.houseAddress.getEditText()).getText().toString().isEmpty()) {
                                                new AlertsHelper().shortToast(getContext(), "Debes llenar todos los campos");
                                            } else {
                                                addHouse(new JSONObject(response).getInt("identificacion"), distr.get("id"));
                                            }
                                        } catch (CertificateException | KeyStoreException |
                                                 IOException |
                                                 KeyManagementException | NoSuchAlgorithmException |
                                                 JSONException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                });
                            } catch (CertificateException | KeyStoreException | IOException |
                                     NoSuchAlgorithmException | KeyManagementException e) {
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

    }


    private void addHouse(int identificacion, String idbarrio) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {

        JSONObject body = new JSONObject();

        try {
            body.put("identificacionCliente", identificacion);
            body.put("barrioId", idbarrio);
            body.put("direccion", Objects.requireNonNull(binding.houseAddress.getEditText()).getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest.sendRequest(token,"/api/post/add/house",getContext(),body,response -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment).commit();
            new AlertsHelper().shortToast(getContext(),"Casa Añadida Exitosamente");
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