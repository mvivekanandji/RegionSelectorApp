package com.enggvam.countryselectorapp.data.db.dao;

import androidx.annotation.NonNull;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

/**
 * <h1>Base Dao for other {@link androidx.room.Dao}</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public interface BaseDao<T> {
    //create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertOne(@NonNull final T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertMany(@NonNull final List<T> objList);

    //update
    @Update
    public abstract int update(@NonNull final T obj);

    //deletion
    @Delete
    public abstract int deleteOne(@NonNull final T obj);

    @Delete
    public abstract int deleteMany(@NonNull final List<T> objList);
}
