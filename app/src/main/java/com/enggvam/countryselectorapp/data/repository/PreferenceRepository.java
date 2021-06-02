package com.enggvam.countryselectorapp.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.enggvam.countryselectorapp.util.PreferenceUtil;

/**
 * <h1>Preference repository class to store and retrieve globally shared preferences</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class PreferenceRepository extends PreferenceUtil {
    private static PreferenceRepository instance;

    private static final String PREF_FIRST_LAUNCH = "firstLaunch";
    private static final String PREF_SELECTED_REGION_NAME = "selectedRegionName";
    private static final String PREF_SELECTED_REGION_CODE = "selectedRegionCode";
    private static final String PREF_SELECTED_REGION_POSITION = "selectedRegionPosition";
    private static final String PREF_REGION_HASH = "regionHash";

    private PreferenceRepository(@NonNull final Context context) {
        super(context);
    }

    public static PreferenceRepository getInstance(@NonNull final Context context) {
        if (instance == null)
            synchronized (PreferenceRepository.class) {
                if (instance == null)
                    instance = new PreferenceRepository(context.getApplicationContext());
            }
        return instance;
    }

    @Override
    protected void initSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstLaunch() {
        return getBoolean(PREF_FIRST_LAUNCH, true);
    }

    public void setFirstLaunchComplete() {
        putBoolean(PREF_FIRST_LAUNCH, false);
    }

    public String getSelectedRegionName() {
        return getString(PREF_SELECTED_REGION_NAME, "");
    }

    public void setSelectedRegionName(@NonNull final String name) {
        putString(PREF_SELECTED_REGION_NAME, name);
    }

    public String getSelectedRegionCode() {
        return getString(PREF_SELECTED_REGION_CODE, "");
    }

    public void setSelectedRegionCode(@NonNull final String code) {
        putString(PREF_SELECTED_REGION_CODE, code);
    }

    public int getSelectedRegionPosition() {
        return getInt(PREF_SELECTED_REGION_POSITION, -1);
    }

    public void setSelectedRegionPosition(final int position) {
        putInt(PREF_SELECTED_REGION_POSITION, position);
    }

    public int getSelectedRegionHash() {
        return getInt(PREF_REGION_HASH, 0);
    }

    public void getSelectedRegionHash(final int hash) {
        putInt(PREF_REGION_HASH, hash);
    }

}
