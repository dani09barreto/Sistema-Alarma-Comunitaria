package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.edu.alarmsystem.App;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentHomeBinding;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONObject;

import java.util.Objects;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentHomeBinding binding;
    private String token;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        binding.navMenu.setNavigationItemSelectedListener(this);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonMenu.setOnClickListener(v-> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        token = requireArguments().getString("token");
        username = requireArguments().getString("currentUsername");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), binding.drawerLayout, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navMenu.setNavigationItemSelectedListener(this);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Cerrar Sesión");
                builder.setMessage(R.string.textSignOut);
                builder.setPositiveButton("SI", (dialog, which) ->   startActivity(new Intent(getContext(),LoginActivity.class)));
                builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                builder.create();
                builder.show();
                return true;
            case R.id.toggle:
                Intent intent = new Intent(getContext(), AlarmActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("token", token);
                startActivity(intent);
        }
        return false;
    }

}