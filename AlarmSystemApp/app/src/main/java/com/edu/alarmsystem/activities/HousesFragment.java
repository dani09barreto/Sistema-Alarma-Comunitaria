package com.edu.alarmsystem.activities;

import android.app.AlertDialog;
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
import com.edu.alarmsystem.databinding.FragmentHousesBinding;
import com.edu.alarmsystem.models.GetRequest;
import com.edu.alarmsystem.models.PostRequest;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


public class HousesFragment extends Fragment {

    private FragmentHousesBinding binding;
    private HomeFragment homeFragment = new HomeFragment();
    private String token;
    private String username;
    private List<Map<String, String>> paises = new ArrayList<>();
    private List<Map<String, String>> departamentos = new ArrayList<>();
    private List<Map<String, String>> ciudades = new ArrayList<>();
    private List<Map<String, String>> barrios = new ArrayList<>();
    private List<Map<String, String>> emergencies = new ArrayList<>();

    private final ArrayList<Integer> selectedIds = new ArrayList<>();

    private final GetRequest request = new GetRequest();
    private final PostRequest postRequest = new PostRequest();
    private boolean isFragmentAttached = false;


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

        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("currentUsername", username);
        homeFragment.setArguments(args);

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

        try {
            request.sendRequest(token,"/api/get/typeEmergency",getContext(),resp -> {
                emergencies = new Gson().fromJson(resp, new TypeToken<List<Map<String, String>>>() {
                }.getType());

                String[] emergenciesArray = new String[emergencies.size()];
                boolean[] selectedEmergencie = new boolean[emergenciesArray.length];
                ArrayList<Integer> emergenciesList = new ArrayList<>();

                int index = 0;
                for (Map<String, String> cit : emergencies) {
                    String nombre = cit.get("nombre");
                    emergenciesArray[index] = nombre;
                    selectedEmergencie[index] = false;
                    index++;
                }

                binding.selectCard.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Selecciona los tipos de emergencia");
                    builder.setCancelable(false);

                    builder.setMultiChoiceItems(emergenciesArray, selectedEmergencie, (dialogInterface, i, b) -> {
                        if (b){
                            emergenciesList.add(i);
                        }else{
                            emergenciesList.remove(i);
                        }
                    });

                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < emergenciesList.size() ; j++) {
                            stringBuilder.append(emergenciesArray[emergenciesList.get(j)]);
                            if(j != emergenciesList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        binding.textCard.setText(stringBuilder.toString());

                        List<String> selectedEmergencies = new ArrayList<>();

                        for (int j = 0; j < emergenciesList.size(); j++) {
                            int selectedIndex = emergenciesList.get(j);
                            selectedEmergencies.add(emergenciesArray[selectedIndex]);
                        }


                        for (Map<String, String> cit : emergencies) {
                            String nombre = cit.get("nombre");
                            int id = Integer.parseInt(cit.get("id"));

                            if (selectedEmergencies.contains(nombre)) {
                                selectedIds.add(id);
                            }
                        }

                        for (int id : selectedIds) {
                            System.out.println("ID seleccionado: " + id);
                        }

                    });

                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                });


            });
        } catch (CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }


    }





    private void addHouse(int identificacion, String idbarrio) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, JSONException {

        JSONObject bodyHouse = new JSONObject();

        try {
            bodyHouse.put("identificacionCliente", identificacion);
            bodyHouse.put("barrioId", idbarrio);
            bodyHouse.put("direccion", Objects.requireNonNull(binding.houseAddress.getEditText()).getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<Long> casaIdFuture = new CompletableFuture<>();

            postRequest.sendRequest(token, "/api/post/add/house", getContext(), bodyHouse, response -> {
                JSONObject jsonObject = new JSONObject(response);
                long casaId = jsonObject.getLong("casaId");
                casaIdFuture.complete(casaId);

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, homeFragment).commit();
                new AlertsHelper().shortToast(getContext(), "Casa Añadida Exitosamente");
            });

            casaIdFuture.thenAcceptAsync(casaId -> {
                JSONObject bodyEmergency = new JSONObject();
                JSONArray tiposEmergenciasId = new JSONArray(selectedIds);
                try {
                    bodyEmergency.put("idCasa", casaId);
                    bodyEmergency.put("tiposEmergenciasId", tiposEmergenciasId);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                try {
                    postRequest.sendRequest(token, "/api/post/add/typeEmergencies", requireContext(), bodyEmergency, resp -> {
                        Log.d("Respuesta Exitosa" , resp);
                    });
                } catch (CertificateException | KeyStoreException | IOException |
                         KeyManagementException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });


        }

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