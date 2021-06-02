package com.enggvam.countryselectorapp.data.repository;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.enggvam.countryselectorapp.data.db.RootDb;
import com.enggvam.countryselectorapp.data.db.dao.BaseDao;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * <h1>Base repository class for repositories of using local database</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public abstract class BaseDbRepository<T> {

    protected final ExecutorService executorService;
    protected final RootDb rootDb;
    protected BaseDao<T> baseDao;

    public BaseDbRepository(@NonNull final Application application,
                            @NonNull final ExecutorService executorService,
                            @NonNull final BaseDao<T> baseDao) {
        this.executorService = executorService;
        this.rootDb = RootDb.getInstance(application.getApplicationContext());
        this.baseDao = baseDao;
    }

    protected void performDbOperation(@NonNull final Runnable runnable){
        if (Looper.getMainLooper().isCurrentThread())
            executorService.execute(runnable);
        else runnable.run();
    }

    //region insert methods
    public void insert(@NonNull final T t) {
        performDbOperation(() -> {
            baseDao.insertOne(t);
        });
    }

    public void insertMany(@NonNull final List<T> objList) {
        performDbOperation(() -> {
            baseDao.insertMany(objList);
        });
    }
    //endregion

    //region insert methods
    public void update(@NonNull final T t){
        performDbOperation(() -> {
            baseDao.update(t);
        });
    }
    //endregion

    //region delete methods
    public void delete(@NonNull final T t) {
        performDbOperation(() -> {
            baseDao.deleteOne(t);
        });
    }

    public void deleteMany(@NonNull final List<T> objList) {
        performDbOperation(() -> {
            baseDao.deleteMany(objList);
        });
    }
    //endregion
}
