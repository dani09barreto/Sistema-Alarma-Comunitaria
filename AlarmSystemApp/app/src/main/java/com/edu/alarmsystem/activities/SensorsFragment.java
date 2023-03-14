package com.edu.alarmsystem.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.FragmentSensorsBinding;

import java.util.Objects;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorsFragment extends Fragment {

    private FragmentSensorsBinding binding;
    private String token;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSensorsBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        token = requireArguments().getString("token");
        username = requireArguments().getString("currentUsername");

        System.out.println(username);
        binding.btnBack.setOnClickListener(v ->{
            getActivity().onBackPressed();
        });
    }

}