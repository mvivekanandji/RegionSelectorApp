package com.enggvam.countryselectorapp.fragment.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.enggvam.countryselectorapp.fragment.MainFragment;
import com.enggvam.countryselectorapp.helper.UrlHelper;
import com.enggvam.countryselectorapp.data.db.entity.Region;
import com.enggvam.countryselectorapp.data.remote.BackendError;
import com.enggvam.countryselectorapp.data.repository.RegionRepository;

import java.util.List;

/**
 * <h1>{@link AndroidViewModel} class for {@link MainFragment}</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class MainFragmentViewModel extends AndroidViewModel {

    private final RegionRepository regionRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        regionRepository = RegionRepository.getInstance(application);
    }

    public LiveData<List<Region>> getLatestRegionList() {
        return regionRepository.getLatestRegions();
    }

    public LiveData<BackendError> getRegionBackendError() {
        return regionRepository.getBackendError();
    }

    public String getSelectedRegionFlagUri(@NonNull final String regionCode) {
        return UrlHelper.getFlagUrl(regionCode);
    }

    public void saveRegions(@NonNull final List<Region> regionList){
        regionRepository.insertMany(regionList);
    }
}
