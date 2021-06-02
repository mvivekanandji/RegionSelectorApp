package com.enggvam.countryselectorapp.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.enggvam.countryselectorapp.data.repository.PreferenceRepository;

/**
 * <h1>{@link AndroidViewModel} class for to access {@link PreferenceRepository} in shared mode as
 * live data.</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class PreferenceSharedViewModel extends AndroidViewModel {

    private final PreferenceRepository preferenceRepository;
    private MutableLiveData<Integer> selectedRegionPosition;
    private MutableLiveData<String> selectedRegionName;
    private MutableLiveData<String> selectedRegionCode;

    public PreferenceSharedViewModel(@NonNull Application application) {
        super(application);
        preferenceRepository = PreferenceRepository.getInstance(application);
        initPreferences();
    }

    private void initPreferences() {
        selectedRegionName = new MutableLiveData<>(preferenceRepository.getSelectedRegionName());
        selectedRegionCode = new MutableLiveData<>(preferenceRepository.getSelectedRegionCode());
        selectedRegionPosition = new MutableLiveData<>(preferenceRepository.getSelectedRegionPosition());
    }

    public MutableLiveData<String> getSelectedRegionName() {
        return selectedRegionName;
    }

    public void setSelectedRegionName(@NonNull final String name) {
        preferenceRepository.setSelectedRegionName(name);
        selectedRegionName.setValue(name);
    }

    public MutableLiveData<String> getSelectedRegionCode() {
        return selectedRegionCode;
    }

    public void setSelectedRegionCode(@NonNull final String code) {
        preferenceRepository.setSelectedRegionCode(code);
        selectedRegionCode.setValue(code);
    }

    public MutableLiveData<Integer> getSelectedRegionPosition() {
        return selectedRegionPosition;
    }

    public void setSelectedRegionPosition(final int position) {
        preferenceRepository.setSelectedRegionPosition(position);
        selectedRegionPosition.setValue(position);
    }

    public int getLastRegionPosition() {
        return preferenceRepository.getSelectedRegionPosition();
    }

    public int getRegionHash() {
        return preferenceRepository.getSelectedRegionHash();
    }

    public void setRegionHash(final int position) {
        preferenceRepository.getSelectedRegionHash(position);
    }
}
