package com.enggvam.countryselectorapp.bottomSheet.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.enggvam.countryselectorapp.bottomSheet.RegionBottomSheet;
import com.enggvam.countryselectorapp.data.db.entity.Region;
import com.enggvam.countryselectorapp.data.remote.BackendError;
import com.enggvam.countryselectorapp.data.repository.RegionRepository;

import java.util.List;

/**
 * <h1>{@link AndroidViewModel} class for {@link RegionBottomSheet}</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RegionBottomSheetViewModel extends AndroidViewModel {

    private final RegionRepository regionRepository;

    public RegionBottomSheetViewModel(@NonNull Application application) {
        super(application);
        regionRepository = RegionRepository.getInstance(application);
    }

    public LiveData<List<Region>> getRegionList() {
        return Transformations.distinctUntilChanged(regionRepository.getRegions());
    }

    public void reloadRegions(){
        regionRepository.loadLatestRegions();
    }
}
