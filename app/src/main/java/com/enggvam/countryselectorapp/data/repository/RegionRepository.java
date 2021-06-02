package com.enggvam.countryselectorapp.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enggvam.countryselectorapp.data.db.RootDb;
import com.enggvam.countryselectorapp.data.db.dao.RegionDao;
import com.enggvam.countryselectorapp.data.db.entity.Region;
import com.enggvam.countryselectorapp.data.remote.BackendError;
import com.enggvam.countryselectorapp.data.remote.RetrofitApiInterface;
import com.enggvam.countryselectorapp.data.remote.RetrofitHelper;
import com.enggvam.countryselectorapp.data.remote.call.BackEndRequestCall;
import com.enggvam.countryselectorapp.data.remote.response.RegionResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * <h1>Repository class to get regions</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RegionRepository extends BaseDbRepository<Region> {

    private static final String TAG = RegionRepository.class.getSimpleName();

    private static RegionRepository instance;
    private final RetrofitApiInterface regionInterface;
    private final RegionDao regionDao;
    private MutableLiveData<List<Region>> regionsMutableLiveData;
    private MutableLiveData<BackendError> backendErrorMutableLiveData;

    private RegionRepository(@NonNull final Application application) {
        super(application, Executors.newSingleThreadExecutor(), RootDb.getInstance(application).regionDao());
        regionInterface = RetrofitHelper.getRegionInterface();
        regionDao = (RegionDao) baseDao;
    }

    public static RegionRepository getInstance(@NonNull final Application application) {
        if (instance == null)
            synchronized (RegionRepository.class) {
                if (instance == null)
                    instance = new RegionRepository(application);
            }
        return instance;
    }

    public LiveData<List<Region>> getRegions() {
        return regionDao.getAll();
    }

    public LiveData<List<Region>> getLatestRegions() {
        if (regionsMutableLiveData == null) {
            regionsMutableLiveData = new MutableLiveData<>();
        }
        loadLatestRegions();
        return regionsMutableLiveData;
    }

    public void loadLatestRegions() {
        BackEndRequestCall.enqueue(regionInterface.getAllRegions(), TAG,
                new BackEndRequestCall.BackendRequestListener() {
                    @Override
                    public void onSuccess(String tag, @NonNull @NotNull Object responseBody) {
                        regionsMutableLiveData.postValue(((RegionResponse) responseBody).getRegionList());
                    }

                    @Override
                    public void onError(String tag, @NonNull @NotNull BackendError backendError) {
                        if (backendErrorMutableLiveData == null)
                            backendErrorMutableLiveData = new MutableLiveData<>();
                        backendErrorMutableLiveData.postValue(backendError);
                    }
                });
    }

    public LiveData<BackendError> getBackendError() {
        if (backendErrorMutableLiveData == null)
            backendErrorMutableLiveData = new MutableLiveData<>();
        return backendErrorMutableLiveData;
    }
}
