package com.enggvam.countryselectorapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.enggvam.countryselectorapp.R;
import com.enggvam.countryselectorapp.databinding.ActivityMainBinding;

/**
 * <h1>Activity representing the entry point of the application</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding rootBinding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(rootBinding.getRoot());
        initConfigs();
    }

    private void initConfigs() {
        navController = Navigation.findNavController(this, R.id.navHost);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}