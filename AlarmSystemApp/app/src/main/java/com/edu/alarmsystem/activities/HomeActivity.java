package com.edu.alarmsystem.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityHomeBinding;
import com.edu.alarmsystem.databinding.ActivityMainBinding;

public class HomeActivity extends Activity {

    private ActivityHomeBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SensorsFragment sensorsFragment = new SensorsFragment();
    HousesFragment housesFragment = new HousesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();
                    return true;
                case R.id.sensors:
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