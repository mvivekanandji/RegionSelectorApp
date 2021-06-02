package com.enggvam.countryselectorapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.enggvam.countryselectorapp.data.db.entity.Region;

import java.util.List;

/**
 * <h1>Dao for other {@link Region} entity</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
@Dao
public abstract class RegionDao implements BaseDao<Region> {

    @Query("SELECT * FROM region ORDER BY name ASC")
    public abstract LiveData<List<Region>> getAll();
}
