package com.enggvam.countryselectorapp.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.enggvam.countryselectorapp.data.db.dao.RegionDao;
import com.enggvam.countryselectorapp.data.db.entity.Region;


/**
 * <h1>Room DB provider for the whats_reply_db</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
@Database(entities = {Region.class}, exportSchema = false, version = 1)
public abstract class RootDb extends RoomDatabase {
    public abstract RegionDao regionDao();

    private static final String DB_NAME = "app_db";
    private static volatile RootDb instance = null;

    public static RootDb getInstance(@NonNull final Context context) {
        if (instance == null) {
            synchronized (RootDb.class) {
                if (instance == null)
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            RootDb.class,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
            }
        }
        return instance;
    }
}
